/*
 * Copyright (c) 2014, the original author or authors.
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; version 3 of the License.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * A copy of the GNU General Public License accompanies this software,
 * and is also available at http://www.gnu.org/licenses.
 */

package name.abhijitsarkar.coffeehouse.cdi.instrumentation;

import name.abhijitsarkar.coffeehouse.NotOperationalException;
import name.abhijitsarkar.coffeehouse.cdi.annotation.Operated;
import name.abhijitsarkar.coffeehouse.cdi.event.CoffeeHouseClosingEventListener;
import name.abhijitsarkar.coffeehouse.support.LoggingHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import java.io.Serializable;
import java.lang.reflect.Method;

/**
 * @author Abhijit Sarkar
 */

@Operated
@Interceptor
public class OperatedInterceptor implements Serializable {
    private static final Logger LOGGER = LoggerFactory.getLogger(OperatedInterceptor.class);

    private static final long serialVersionUID = -3316843159950922501L;

    @Inject
    private transient CoffeeHouseClosingEventListener listener;

    @AroundInvoke
    public Object verifyThatOperational(final InvocationContext invocationContext) throws Exception {
        final Method method = invocationContext.getMethod();

        LOGGER.debug("Intercepted {} {}.{}({}).", method.getReturnType().getSimpleName(), method.getDeclaringClass(),
                method.getName(), method.getParameterTypes());

        final Object[] args = invocationContext.getParameters();

        LoggingHelper.logArgs(LOGGER, args);

        if (!listener.isOpen()) {
            throw new NotOperationalException("Sorry, we're currently closed.");
        }

        return invocationContext.proceed();
    }
}
