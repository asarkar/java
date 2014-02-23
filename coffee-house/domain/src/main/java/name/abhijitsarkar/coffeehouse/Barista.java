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

import name.abhijitsarkar.coffeehouse.validation.ValidOrder;

import java.util.Locale;

/**
 * @author Abhijit Sarkar
 */
public abstract class Barista {
    private final transient Menu menu;

    public Barista(final Menu menu) {
        this.menu = menu;
    }

    public Menu menu() {
        return menu;
    }

    public Coffee serve(@ValidOrder(Coffee.Blend.class) final String blend) {
        return serve(blend, Coffee.Flavor.NONE.name());
    }

    public Coffee serve(@ValidOrder(Coffee.Blend.class) final String blend,
                        @ValidOrder(Coffee.Flavor.class) final String flavor) {
        final Coffee.Blend orderedBlend = Coffee.Blend.valueOf(blend.toUpperCase(Locale.getDefault()));
        final Coffee.Flavor orderedFlavor = Coffee.Flavor.valueOf(flavor.toUpperCase(Locale.getDefault()));

        if (menu.isOnTheMenu(orderedBlend, orderedFlavor)) {
            return new Coffee(orderedBlend, orderedFlavor);
        }

        final String message = "Sorry, there's no coffee with blend: " + orderedBlend.name() + " and flavor: "
                + orderedFlavor.name() + " on the menu.";

        throw new NotOnTheMenuException(message);
    }
}
