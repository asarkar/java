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

/**
 * @author Abhijit Sarkar
 */
public class Coffee {
    private final Blend blend;
    private final Flavor flavor;

    public Coffee(final Blend blend) {
        this(blend, Flavor.NONE);
    }

    public Coffee(final Blend blend, final Flavor flavor) {
        this.blend = blend;
        this.flavor = flavor;
    }

    public Blend getBlend() {
        return blend;
    }

    public Flavor getFlavor() {
        return flavor;
    }

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || getClass() != other.getClass()) {
            return false;
        }

        final Coffee coffee = (Coffee) other;

        if (blend != coffee.blend) {
            return false;
        }
        if (flavor != coffee.flavor) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = blend.hashCode();
        result = 31 * result + (flavor != null ? flavor.hashCode() : 0);

        return result;
    }

    @Override
    public String toString() {
        return "Coffee{" +
                "blend=" + blend +
                ", flavor=" + flavor +
                "}";
    }

    public enum Blend {
        DARK, MEDIUM, DECAF, NONE;
    }

    public enum Flavor {
        VANILLA, CARAMEL, MOCHA, NONE;
    }
}

