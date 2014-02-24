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

package name.abhijitsarkar.coffeehouse.cdi.event;

import name.abhijitsarkar.coffeehouse.cdi.annotation.Operational;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Event;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * @author Abhijit Sarkar
 */

@ApplicationScoped
public class CoffeeHouseOpeningAndClosingEventPublisher {
    private static final Logger LOGGER = LoggerFactory.getLogger(CoffeeHouseOpeningAndClosingEventPublisher.class);

    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    @Inject
    Event<OperationalEvent> event;

    @Produces
    /* Qualifier @Operational is optional here but recommended to avoid ambiguity */
    @Operational
    public boolean open() {
        LOGGER.debug("Opening shop.");

        return true;
    }

    void closeShop() {
        final Runnable closer = new Runnable() {
            public void run() {
                final OperationalEvent operationalEvent = new OperationalEvent();

                operationalEvent.setOpen(false);

                LOGGER.debug("Firing notification to close shop.");

                event.fire(operationalEvent);
            }
        };

        final long initialDelay = 1L;
        final long delay = 2L;
        final long duration = 10L;

        final ScheduledFuture<?> closerHandle =
                scheduler.scheduleWithFixedDelay(closer, initialDelay, delay, TimeUnit.SECONDS);
        scheduler.schedule(new Runnable() {
            public void run() {
                closerHandle.cancel(true);
            }
        }, duration, TimeUnit.SECONDS);
    }
}
