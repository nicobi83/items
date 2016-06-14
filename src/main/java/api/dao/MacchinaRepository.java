package api.dao;

import api.dao.mapper.MacchinaResultSetMapper;
import api.model.Items;
import api.model.Macchine;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.IOUtils;
import org.skife.jdbi.v2.DBI;
import org.skife.jdbi.v2.Handle;
import org.skife.jdbi.v2.Query;
import org.skife.jdbi.v2.sqlobject.CreateSqlObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;


/**
 * Created by NICOLA on 13/06/2016.
 */
public abstract class MacchinaRepository {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @CreateSqlObject
    public abstract MacchinaDao macchinaDao();

    @Inject
    ObjectMapper mapper;
    @Inject
    DBI dbi;

    public Set<Macchine.Macchina> findMacchina(Map<String, String> query) {
        String sqlFind = "SELECT * FROM macchine WHERE " +
                " UPPER(id) LIKE UPPER(:id) AND " +
                " UPPER(produttore) LIKE UPPER(:produttore) AND " +
                " UPPER(modello) LIKE UPPER(:modello) AND " +
                " UPPER(categoria) LIKE UPPER(:categoria)" +
                " UPPER(targa) LIKE UPPER(:targa) AND " +
                " UPPER(cilindrata) LIKE UPPER(:cilindrata) AND " +
                " UPPER(potenza) LIKE UPPER(:potenza) AND " +
                " UPPER(potenza_fiscale) LIKE UPPER(:potenza_fiscale) AND " +
                " UPPER(colore) LIKE UPPER(:colore)";

        String percent = "%";
        String underscore = "_";

        String produttore = getParamWithDefault(query, "produttore", percent);
        String modello = getParamWithDefault(query, "modello", percent);
        String categoria = getParamWithDefault(query, "categoria", percent);
        String targa = getParamWithDefault(query, "targa", percent);
        String cilindrata = getParamWithDefault(query, "cilindrata", percent);
        String potenza = getParamWithDefault(query, "potenza", percent);
        String potenza_fiscale = getParamWithDefault(query, "potenza_fiscale", percent);
        String colore = getParamWithDefault(query, "colore", percent);

        Handle h = dbi.open();

        try {

            Query<Macchine.Macchina> tmp = h.createQuery(sqlFind)
                    .map(new MacchinaResultSetMapper())
                    .bind("produttore", produttore)
                    .bind("modello", modello)
                    .bind("categoria", categoria)
                    .bind("targa", targa)
                    .bind("cilindrata", cilindrata)
                    .bind("potenza", potenza)
                    .bind("potenza_fiscale", potenza_fiscale)
                    .bind("colore", colore);

                Set<Macchine.Macchina> out = tmp
                    .list()
                    .stream()
                    .collect(Collectors.toSet());

                return out;

        } catch (Exception e) {
            logger.error(e.getLocalizedMessage(), e);
        } finally {
            IOUtils.closeQuietly(h);
        }

        return null;

    }

    private static String getParamWithDefault(Map<String, String> query, String key, String defaultValue) {
        // < Java 7 String result = query.get("id") != null && query.get("id") != "" ? query.get("id") : percent;
        String result = query.getOrDefault(key, defaultValue);
        return result;
    }

    public Macchine.Macchina getMacchina(int id) {
        Macchine.Macchina macchina = this.macchinaDao().getMacchina(id);
        if (macchina != null) {
            //String values = this.itemDao().getValues(id);
            Map<String, Object> m = null;
            if ( !org.apache.commons.lang.StringUtils.isBlank(macchina.getsValues()) ){
                try {
                    m = mapper.readValue(macchina.getsValues(), new TypeReference<Map<String, Object>>() {
                    });
                    macchina.setValues(m);
                } catch (IOException e) {
                    logger.error(e.getLocalizedMessage(), e);
                }
            }
        }
        return macchina;
    }

    public int insert(Macchine.Macchina macchina, int id) {
        return this.macchinaDao().insert(macchina, id);
    }


    public int update(Macchine.Macchina macchina, int id) {
        return this.macchinaDao().update(macchina, id);
    }

    public int delete( Macchine.Macchina macchina, int id ) {
        return this.macchinaDao().delete(macchina);
    }

}


