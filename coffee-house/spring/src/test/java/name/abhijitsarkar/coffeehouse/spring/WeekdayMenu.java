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

import name.abhijitsarkar.coffeehouse.Coffee;
import name.abhijitsarkar.coffeehouse.Menu;
import name.abhijitsarkar.coffeehouse.spring.support.ConditionalOnDayOfTheWeek;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * @author Abhijit Sarkar
 */

@Component
@ConditionalOnDayOfTheWeek
public class WeekdayMenu extends Menu {
    private static final Logger LOGGER = LoggerFactory.getLogger(WeekdayMenu.class);

    @PostConstruct
    public void postConstruct() {
        LOGGER.debug("Creating a weekday menu.");

        final List<Coffee.Blend> blendsOnTheMenu = super.getBlends();
        blendsOnTheMenu.remove(Coffee.Blend.DECAF);

        this.setBlends(blendsOnTheMenu);
    }
}
