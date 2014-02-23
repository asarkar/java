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

package name.abhijitsarkar.coffeehouse.spring.support;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

import java.util.Map;

import static name.abhijitsarkar.coffeehouse.spring.support.ConditionalOnDayOfTheWeek.DayOfTheWeek;

/**
 * @author Abhijit Sarkar
 */

public class DayOfTheWeekCondition implements Condition {
    private static final Logger LOGGER = LoggerFactory.getLogger(DayOfTheWeekCondition.class);

    @Override
    public boolean matches(final ConditionContext context, final AnnotatedTypeMetadata metadata) {
        final Map<String, Object> attributes
                = metadata.getAnnotationAttributes(ConditionalOnDayOfTheWeek.class.getName());

        final DayOfTheWeek dayOfTheWeek = (DayOfTheWeek) attributes.get("value");
        final boolean isWeekend = Boolean.valueOf(System.getProperty("isWeekend", "false"));

        LOGGER.debug("Day of the week: {}, isWeekend: {}.", dayOfTheWeek, isWeekend);

        return (dayOfTheWeek.equals(DayOfTheWeek.WEEKEND) && isWeekend) ||
                (dayOfTheWeek.equals(DayOfTheWeek.WEEKDAY) && !isWeekend);
    }
}
