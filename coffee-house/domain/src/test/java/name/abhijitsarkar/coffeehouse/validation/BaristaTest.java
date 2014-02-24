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

import name.abhijitsarkar.coffeehouse.Barista;
import name.abhijitsarkar.coffeehouse.Menu;
import name.abhijitsarkar.coffeehouse.validation.ValidOrder;
import org.junit.Assert;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.ValidatorFactory;
import javax.validation.executable.ExecutableValidator;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Set;

/**
 * @author Abhijit Sarkar
 */
public class BaristaTest {
    private Barista barista = new TestBarista(new TestMenu());

    private ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    private ExecutableValidator executableValidator = factory.getValidator().forExecutables();

    @Test
    public void validateServeWhenValidBlend() throws NoSuchMethodException {
        Method serve = Barista.class.getMethod("serve", String.class);
        Object[] args = {"dark"};

        Set<ConstraintViolation<Barista>> violations = executableValidator.validateParameters(
                barista,
                serve,
                args
        );

        assertNoViolations(violations);
    }

    @Test
    public void validateServeWhenInvalidBlend() throws NoSuchMethodException {
        Method serve = Barista.class.getMethod("serve", String.class);
        Object[] args = {"light"};

        Set<ConstraintViolation<Barista>> violations = executableValidator.validateParameters(
                barista,
                serve,
                args
        );

        assertNumViolations(violations, 1);
    }

    @Test
    public void validateServeWhenValidBlendAndFlavor() throws NoSuchMethodException {
        Method serve = Barista.class.getMethod("serve", String.class, String.class);
        Object[] args = {"dark", "mocha"};

        Set<ConstraintViolation<Barista>> violations = executableValidator.validateParameters(
                barista,
                serve,
                args
        );

        assertNoViolations(violations);
    }

    @Test
    public void validateServeWhenInvalidBlendAndValidFlavor() throws NoSuchMethodException {
        Method serve = Barista.class.getMethod("serve", String.class, String.class);
        Object[] args = {"light", "mocha"};

        Set<ConstraintViolation<Barista>> violations = executableValidator.validateParameters(
                barista,
                serve,
                args
        );

        assertNumViolations(violations, 1);
    }

    @Test
    public void validateServeWhenInvalidBlendAndFlavor() throws NoSuchMethodException {
        Method serve = Barista.class.getMethod("serve", String.class, String.class);
        Object[] args = {"light", "cardamom"};

        Set<ConstraintViolation<Barista>> violations = executableValidator.validateParameters(
                barista,
                serve,
                args
        );

        assertNumViolations(violations, 2);
    }

    private void assertNoViolations(Set<ConstraintViolation<Barista>> violations) {
        Assert.assertTrue(violations.isEmpty());

        Assert.assertFalse(violations.iterator().hasNext());
    }

    private static class TestBarista extends Barista {
        TestBarista(Menu menu) {
            super(menu);
        }
    }

    private void assertNumViolations(Set<ConstraintViolation<Barista>> violations, int numViolations) {
        Assert.assertEquals(numViolations, violations.size());

        Class<? extends Annotation> constraintType = null;

        for (ConstraintViolation<Barista> aViolation : violations) {
            constraintType = aViolation.getConstraintDescriptor().getAnnotation().annotationType();

            Assert.assertEquals(ValidOrder.class, constraintType);
        }
    }

    private static class TestMenu extends Menu {

    }
}
