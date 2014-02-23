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
public class NotOperationalException extends RuntimeException {
    public NotOperationalException() {
        super();
    }

    public NotOperationalException(String message) {
        super(message);
    }

    public NotOperationalException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotOperationalException(Throwable cause) {
        super(cause);
    }

    protected NotOperationalException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
