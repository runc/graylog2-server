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

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import org.graylog2.database.MongoConnection;
import org.graylog2.database.PersistedServiceImpl;

/**
 *  @author Lennart Koopmann <lennart@socketfeed.com>
 */
public class SystemSetting extends PersistedServiceImpl {
    
    private static final String COLLECTION_NAME = "system_settings";

    public SystemSetting(MongoConnection mongoConnection) {
        super(mongoConnection);
    }
    
    public boolean getBoolean(String key) {
        DBCollection coll = getCollection();
        
        DBObject query = new BasicDBObject();
        query.put("key", key);
        
        DBObject result = coll.findOne(query);
        if (result == null) {
            return false;
        }
        
        if (result.get("value").equals(true)) {
            return true;
        }
        
        return false;
    }
    
    public BasicDBList getList(String key) {
        DBCollection coll = getCollection();
        DBObject query = new BasicDBObject();
        query.put("key", key);

        DBObject result = coll.findOne(query);

        return (BasicDBList) result.get("value");
    }

    private DBCollection getCollection() {
        return mongoConnection.getDatabase().getCollection(COLLECTION_NAME);
    }
    
}
