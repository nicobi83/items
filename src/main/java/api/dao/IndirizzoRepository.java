package api.dao;

import api.dao.mapper.IndirizzoResultSetMapper;
import api.model.Indirizzo;
import org.apache.commons.io.IOUtils;
import org.joda.time.DateTime;
import org.skife.jdbi.v2.DBI;
import org.skife.jdbi.v2.Handle;
import org.skife.jdbi.v2.Query;
import org.skife.jdbi.v2.sqlobject.CreateSqlObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by AlbertoBortolato on 20/05/2016.
 */
public abstract class IndirizzoRepository {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    @CreateSqlObject
    public abstract IndirizzoDao indirizzoDao();

    @Inject
    DBI dbi;

    public Set<Indirizzo> findIndirizzo(Map<String, String> query) {
        String sqlFind = "SELECT * FROM indirizzo WHERE " +
          " UPPER(id) LIKE UPPER(:id) AND " +
          " UPPER(via) LIKE UPPER(:via) AND " +
          " UPPER(cap) LIKE UPPER(:cap) AND " +
          " UPPER(citta) LIKE UPPER(:citta)";

        String percent = "%";
        String underscore = "_";

        String id = getParamWithDefault(query, "id", percent);
        String via = getParamWithDefault(query, "via", percent);
        String cap = getParamWithDefault(query, "cap", percent);
        String citta = getParamWithDefault(query, "citta", percent);

        Handle h = dbi.open();
        try {
            // formatter:off
            Query<Indirizzo> tmp = h.createQuery(sqlFind)
              .bind("id", id)
              .map(new IndirizzoResultSetMapper())
              .bind("via", via)
              .bind("cap", cap)
              .bind("citta", citta);
            // formatter:on
            Set<Indirizzo> out = tmp.list().stream().collect(Collectors.toSet());
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


    public Indirizzo getIndirizzo(String id) {
        return this.indirizzoDao().getIndirizzo(id);
    }

    public int update(Indirizzo indirizzo) {
        return this.indirizzoDao().update(indirizzo);
    }

    public int insert(Indirizzo indirizzo) {
        return this.indirizzoDao().insert(indirizzo);
    }

}
