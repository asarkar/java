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

package name.abhijitsarkar.coffeehouse.validation;

import name.abhijitsarkar.coffeehouse.Coffee;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Locale;

/**
 * @author Abhijit Sarkar
 */

public class OrderValidator implements ConstraintValidator<ValidOrder, String> {
    private static final Logger LOGGER = LoggerFactory.getLogger(OrderValidator.class);

    private transient Class<? extends Enum> order;

    @Override
    public void initialize(final ValidOrder constraintAnnotation) {
        this.order = constraintAnnotation.value();
    }

    @Override
    public boolean isValid(final String value, final ConstraintValidatorContext context) {

        return isValidBlend(value) || isValidFlavor(value);
    }

    boolean isValidBlend(final String value) {
        LOGGER.debug("Validating value: {}.", value);

        if (!Coffee.Blend.class.isAssignableFrom(order)) {
            return false;
        }

        try {
            @SuppressWarnings("unchecked")
            final Coffee.Blend blend = (Coffee.Blend) Enum.valueOf(order, value.toUpperCase(Locale.getDefault()));

            LOGGER.debug("Found valid blend: {}.", blend);

            return true;
        } catch (final IllegalArgumentException iae) {
            LOGGER.debug(iae.getMessage());
        }

        return false;
    }

    boolean isValidFlavor(final String value) {
        if (!Coffee.Flavor.class.isAssignableFrom(order)) {
            return false;
        }

        try {
            @SuppressWarnings("unchecked")
            final Coffee.Flavor flavor = (Coffee.Flavor) Enum.valueOf(order, value.toUpperCase(Locale.getDefault()));

            LOGGER.debug("Found valid flavor: {}.", flavor);

            return true;
        } catch (final IllegalArgumentException iae) {
            LOGGER.debug(iae.getMessage());
        }

        return false;
    }
}
