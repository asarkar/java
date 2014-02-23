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

package name.abhijitsarkar.coffeehouse.spring;

import name.abhijitsarkar.coffeehouse.spring.support.OperationalEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Component;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * @author Abhijit Sarkar
 */
@Component
public class CoffeeHouseClosingEventPublisher implements ApplicationEventPublisherAware {
    private static final Logger LOGGER = LoggerFactory.getLogger(CoffeeHouseClosingEventPublisher.class);

    private ApplicationEventPublisher publisher;

    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher publisher) {
        this.publisher = publisher;
    }

    void closeShop() {
        final Runnable closer = new Runnable() {
            public void run() {
                final OperationalEvent event = new OperationalEvent(this);

                event.setOpen(false);

                LOGGER.debug("Firing notification to close shop.");

                CoffeeHouseClosingEventPublisher.this.publisher.publishEvent(event);
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
