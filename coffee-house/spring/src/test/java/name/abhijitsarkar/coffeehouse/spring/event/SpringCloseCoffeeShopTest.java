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

import name.abhijitsarkar.coffeehouse.Barista;
import name.abhijitsarkar.coffeehouse.NotOperationalException;
import name.abhijitsarkar.coffeehouse.spring.AbstractSpringContextAwareTest;
import name.abhijitsarkar.coffeehouse.spring.event.CoffeeHouseClosingEventPublisher;
import org.junit.Test;

import javax.annotation.Resource;

/**
 * @author Abhijit Sarkar
 */
public class SpringCloseCoffeeShopTest extends AbstractSpringContextAwareTest {
    @Resource
    private CoffeeHouseClosingEventPublisher publisher;

    @Resource
    private Barista barista;

    @Test(expected = NotOperationalException.class)
    public void testServeWhenShopClosed() throws InterruptedException {
        publisher.closeShop();

        Thread.sleep(5000);

        barista.serve("dark");
    }
}
