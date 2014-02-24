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

import name.abhijitsarkar.coffeehouse.Barista;
import name.abhijitsarkar.coffeehouse.NotOperationalException;
import name.abhijitsarkar.coffeehouse.cdi.AbstractCDITest;
import name.abhijitsarkar.coffeehouse.cdi.annotation.CDI;
import name.abhijitsarkar.coffeehouse.cdi.event.CoffeeHouseOpeningAndClosingEventPublisher;
import org.junit.Test;

import javax.inject.Inject;

/**
 * @author Abhijit Sarkar
 */
public class CDICloseCoffeeShopTest extends AbstractCDITest {

    @Inject
    private CoffeeHouseOpeningAndClosingEventPublisher publisher;

    @Inject
    @CDI
    private Barista barista;

    @Test(expected = NotOperationalException.class)
    public void testServeWhenShopClosed() throws InterruptedException {
        publisher.closeShop();

        Thread.sleep(5000);

        barista.serve("dark");
    }
}
