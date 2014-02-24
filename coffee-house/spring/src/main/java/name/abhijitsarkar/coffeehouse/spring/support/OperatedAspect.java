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

package name.abhijitsarkar.coffeehouse.spring.support;

import name.abhijitsarkar.coffeehouse.NotOperationalException;
import name.abhijitsarkar.coffeehouse.support.LoggingHelper;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Abhijit Sarkar
 */

@Component
@Aspect
public class OperatedAspect {
    private static final Logger LOGGER = LoggerFactory.getLogger(OperatedAspect.class);

    @Autowired
    private transient CoffeeHouseClosingEventListener listener;

    /* Intercept any method named serve defined in the type Barista.*/
    @Before("execution(* name.abhijitsarkar.coffeehouse.Barista.serve(..))"
    )
    public void verifyThatOperational(final JoinPoint joinPoint) {
        LOGGER.debug("Intercepted {}.", joinPoint.getSignature());

        final Object[] args = joinPoint.getArgs();

        LoggingHelper.logArgs(LOGGER, args);

        if (!listener.isOpen()) {
            throw new NotOperationalException("Sorry, we're currently closed.");
        }
    }
}
