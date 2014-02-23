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

package name.abhijitsarkar.coffeehouse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Abhijit Sarkar
 */
public abstract class Menu {
    private static final Logger LOGGER = LoggerFactory.getLogger(Menu.class);

    private List<Coffee.Blend> blends;
    private List<Coffee.Flavor> flavors;

    public Menu() {
        /* Arrays.asList() returns a barebone List implementation that doesn't have many optional method implemented.
         * Subclasses are going to get an UnsupportedOperationException if they try to invoke any of those methods.
         * Converting the list into an ArrayList avoids that problem.
         */
        final List<Coffee.Blend> blendsOnTheMenu = new ArrayList<>(Arrays.asList(Coffee.Blend.values()));
        final List<Coffee.Flavor> flavorsOnTheMenu = new ArrayList<>(Arrays.asList(Coffee.Flavor.values()));

        setBlends(blendsOnTheMenu);
        setFlavors(flavorsOnTheMenu);
    }

    public List<Coffee.Blend> getBlends() {
        return blends;
    }

    public void setBlends(final List<Coffee.Blend> blends) {
        this.blends = blends;
    }

    public List<Coffee.Flavor> getFlavors() {
        return flavors;
    }

    public void setFlavors(final List<Coffee.Flavor> flavors) {
        this.flavors = flavors;
    }

    public boolean isOnTheMenu(final Coffee.Blend orderedBlend, final Coffee.Flavor orderedFlavor) {
        boolean foundOrder = false;

        outerloop:

        for (final Coffee.Blend blendOnTheMenu : blends) {
            for (final Coffee.Flavor flavorOnTheMenu : flavors) {
                if (orderedBlend.equals(blendOnTheMenu) && orderedFlavor.equals(flavorOnTheMenu)) {
                    foundOrder = true;

                    break outerloop;
                }
            }
        }

        LOGGER.debug("{} the ordered combination of blend: {} and flavor: {} on the menu.",
                foundOrder ? "Found" : "Did not find", orderedBlend, orderedFlavor);

        return foundOrder;
    }
}
