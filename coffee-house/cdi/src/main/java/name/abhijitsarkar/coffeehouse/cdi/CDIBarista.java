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

package name.abhijitsarkar.coffeehouse.cdi;

import name.abhijitsarkar.coffeehouse.Barista;
import name.abhijitsarkar.coffeehouse.Coffee;
import name.abhijitsarkar.coffeehouse.Menu;
import name.abhijitsarkar.coffeehouse.cdi.annotation.CDI;
import name.abhijitsarkar.coffeehouse.cdi.annotation.Operated;

import javax.inject.Inject;

/**
 * @author Abhijit Sarkar
 */

/* http://docs.oracle.com/javaee/7/tutorial/doc/cdi-basic004.htm */

@CDI
public class CDIBarista extends Barista {
    @Inject
    public CDIBarista(final Menu menu) {
        super(menu);
    }

    @Operated
    public Coffee serve(final String blend) {
        return super.serve(blend);
    }

    @Operated
    public Coffee serve(final String blend, final String flavor) {
        return super.serve(blend, flavor);
    }
}
