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
package org.graylog2.lookup;

import com.fasterxml.jackson.databind.ObjectMapper;

public interface LookupDefaultValue {
    String FIELD_VALUE_STRING = "value_string";
    String FIELD_VALUE_TYPE = "value_type";
    String FIELD_VALUE = "value";

    ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    enum Type {
        STRING, NUMBER, OBJECT, BOOLEAN, NULL
    }

    String valueString();
    LookupDefaultValue.Type valueType();
    Object value();
    boolean isSet();
}
