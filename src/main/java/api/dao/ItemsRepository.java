package api.dao;

import api.dao.mapper.IndirizzoResultSetMapper;
import api.dao.mapper.ItemResultSetMapper;
import api.model.Indirizzo;
import api.model.Items;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.skife.jdbi.v2.DBI;
import org.skife.jdbi.v2.Handle;
import org.skife.jdbi.v2.Query;
import org.skife.jdbi.v2.sqlobject.CreateSqlObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by matthew on 16.05.16.
 */
public abstract class ItemsRepository {

    //posso gestire più Dao.

    Logger logger = LoggerFactory.getLogger(this.getClass());


    @CreateSqlObject
    public abstract ItemDao itemDao();

    @Inject
    ObjectMapper mapper;
    @Inject
    DBI dbi;

    @PostConstruct
    void init() {

    }

    public Set<Items.Item> find(Map<String, String> query) {
        String sqlFind = "SELECT * FROM items WHERE " +
                " UPPER(id) LIKE UPPER(:id) AND " +
                " UPPER(name) LIKE UPPER(:name) AND " +
                " UPPER(description) LIKE UPPER(:description) AND " +
                " UPPER(content) LIKE UPPER(:content) " +
                " AND date_modified <= :date_modified_before " +
                " AND date_modified >= :date_modified_after " +
                " AND date_created <= :date_created_before " +
                " AND date_created >= :date_created_after";

        String percent = "%";
        String underscore = "_";

        String id = getParamWithDefault(query, "id", percent);
        String name = getParamWithDefault(query, "name", percent);
        String description = getParamWithDefault(query, "description", percent);
        String content = getParamWithDefault(query, "content", percent);
        String href = getParamWithDefault(query,"href",percent);
        Handle h = dbi.open();
        try {
            // formatter:off
            Query<Items.Item> tmp = h.createQuery(sqlFind)
                    .bind("id", id)
                    .map(new ItemResultSetMapper())
            /* serve per mappare l'oggetto nell'oggetto che noi vogliamo in questo caso Item,
             senza questo il risultato sarebbe un Object perchè avrei Query<Map<String,Object>> ma a noi serve Items.Item*/
                    .bind("name", name)
                    .bind("description", description)
                    .bind("content", content);


            handleDateFilter(query, "date_modified_before", tmp, true);
            handleDateFilter(query, "date_modified_after", tmp, false);
            handleDateFilter(query, "date_created_before", tmp, true);
            handleDateFilter(query, "date_created_after", tmp, false);

            Set<Items.Item> out = tmp
                    .list()
                    .stream()
                    .map(item -> {
                        if (StringUtils.isNotBlank(item.getSValues())) {
                            Map<String, Object> m = null;
                            try {
                                m = mapper.readValue(item.getSValues(), new TypeReference<Map<String, Object>>() {
                                });
                                item.setValues(m);
                            } catch (IOException e) {
                                logger.error(e.getLocalizedMessage(), e);
                            }
                        }
                        return item;
                    })
                    .collect(Collectors.toSet());
            // formatter:on
            return out;
        } catch (Exception e) {
            logger.error(e.getLocalizedMessage(), e);
        } finally {
            IOUtils.closeQuietly(h);
        }
        return null;
    }



    private static void handleDateFilter(Map<String, String> query, String param, Query tmp, boolean before) {
        String dmb = query.get(param);
        if (!org.apache.commons.lang.StringUtils.isBlank(dmb)) {
            DateTime dt = DateTime.parse(dmb);
            if (dt != null) {
                tmp.bind(param, dt.toDate());
                return;
            }
        }
        ;
        if (before) {
            tmp.bind(param, DateTime.parse("9999-12-12").toDate());
        } else {
            tmp.bind(param, DateTime.parse("-4000-01-01").toDate());
        }
    }


    private static String getParamWithDefault(Map<String, String> query, String key, String defaultValue) {
        // < Java 7 String result = query.get("id") != null && query.get("id") != "" ? query.get("id") : percent;
        String result = query.getOrDefault(key, defaultValue);
        return result;
    }


    public Items.Item getItem(String id) {
        Items.Item item = this.itemDao().getItem(id);
        if (item != null) {
            //String values = this.itemDao().getValues(id);
            Map<String, Object> m = null;
            if (!org.apache.commons.lang.StringUtils.isBlank(item.getSValues())) {
                try {
                    m = mapper.readValue(item.getSValues(), new TypeReference<Map<String, Object>>() {
                    });
                    item.setValues(m);
                } catch (IOException e) {
                    logger.error(e.getLocalizedMessage(), e);
                }
            }
        }
        return item;
    }

    public int insert(Items.Item item) {
        return this.itemDao().insert(item, toJson(item.getValues()));
    }


    public int update(Items.Item item) {
        return this.itemDao().update(item, toJson(item.getValues()));
    }

    private String toJson(Object obj) {
        String serializedObject = null;
        try {
            serializedObject = mapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            logger.debug(e.getLocalizedMessage());
        }
        return serializedObject;
    }
}
// SPECIFICO PER JDBI
