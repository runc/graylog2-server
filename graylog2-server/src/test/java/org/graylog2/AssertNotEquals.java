/**
 * This file is part of Graylog.
 *
 * Graylog is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Graylog is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Graylog.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.graylog2;

import static org.junit.Assert.assertFalse;

/**
 * @author Dennis Oelkers <dennis@torch.sh>
 */
public class AssertNotEquals {
    public static <T> void assertNotEquals(String msg, T obj1, T obj2) {
        if (obj1 == null)
            assertFalse(msg, obj1 == null && obj2 == null);
        else
            assertFalse(msg, obj1.equals(obj2));
    }
}
