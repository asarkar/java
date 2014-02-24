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

package name.abhijitsarkar.coffeehouse.spring.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author Abhijit Sarkar
 */
@Component
public class CoffeeHouseClosingEventListener implements ApplicationListener<OperationalEvent> {
    private static final Logger LOGGER = LoggerFactory.getLogger(CoffeeHouseClosingEventListener.class);

    private final AtomicBoolean open;

    public CoffeeHouseClosingEventListener() {
        open = new AtomicBoolean(true);
    }

    @Override
    public void onApplicationEvent(final OperationalEvent event) {
        LOGGER.debug("Received notification to {} shop.", event.isOpen() ? "open" : "close");

        open.set(event.isOpen());
    }

    public boolean isOpen() {
        LOGGER.debug("Shop is {}.", open.get() ? "open" : "closed");

        return open.get();
    }
}
