package api.dao.mapper;

import api.model.Items;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * Created by matthew on 16.05.16.
 */

public class ItemResultSetMapper implements ResultSetMapper<Items.Item> {
    Map<String, Items.Item> cache;

    @Override
    public Items.Item map(int i, ResultSet rs, StatementContext statementContext) throws SQLException {
        if (i == 0) {
            cache = new ConcurrentHashMap<>();
            //fa da semaforo ai tread più lento ma dato che restituisce più sicuro.
        }
        String id = rs.getString("id");
        if (cache.containsKey(id)) {
            return cache.get(id);
        }
        Items.Item item = new Items.Item();
        item.setId(id);
        cache.put(id, item);
        item.setName(rs.getString("name"));
        item.setContent(rs.getString("content"));
        item.setDescription(rs.getString("description"));
        item.setCreationDate(rs.getTimestamp("date_created"));
        item.setModifiedDate(rs.getTimestamp("date_modified"));
        item.setSValues(rs.getString("values"));
        item.getHref();

        return item;
    }
}
