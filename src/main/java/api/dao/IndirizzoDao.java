package api.dao;

import api.dao.mapper.IndirizzoResultSetMapper;
import api.model.Indirizzo;
import org.skife.jdbi.v2.sqlobject.*;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

import java.util.Collection;
import java.util.Set;

/**
 * Created by Thomas on 17/05/2016.
 */
@RegisterMapper(IndirizzoResultSetMapper.class)
public interface IndirizzoDao {

    @SqlUpdate("DROP TABLE indirizzo")
    int destroy();

    @SqlBatch("UPDATE indirizzo " +
      " SET via = :i.via," +
      " cap = :i.cap, " +
      " citta = :i.citta " +
      " WHERE id = :i.id")
    int[] update(@BindBean("i") Collection<Indirizzo> indirizzi);

    @SqlBatch("UPDATE indirizzo " +
      " SET via = :i.via," +
      " cap = :i.cap, " +
      " citta = :citta " +
      " WHERE id = :i.id")
    int[] updateCitta(@BindBean("i") Collection<Indirizzo> indirizzi, @Bind("citta") String citta);


    @SqlBatch("INSERT INTO indirizzo (id, via, cap, citta)" +
      " VALUES (:i.id, :i.via, :i.cap, :i.citta)")
    int[] insert(@BindBean("i") Collection<Indirizzo> indirizzi);

    @SqlUpdate("CREATE TABLE indirizzo(id varchar primary key, via varchar, cap varchar, citta varchar)")
    int create();

    @SqlQuery("SELECT * FROM indirizzo WHERE id = :id")
    Indirizzo getIndirizzo(@Bind("id") String id);

    @SqlQuery("SELECT * FROM indirizzo")
    Set<Indirizzo> listAll();

    @SqlUpdate("INSERT INTO indirizzo (id, via, cap, citta)" +
      " VALUES (:i.id, :i.via, :i.cap, :i.citta)")
    int insert(@BindBean("i") Indirizzo indirizzo);

    @SqlUpdate("UPDATE indirizzo " +
      " SET via = :i.via," +
      " cap = :i.cap, " +
      " citta = :i.citta " +
      " WHERE id = :i.id")
    int update(@BindBean("i") Indirizzo indirizzo);

    @SqlUpdate("DELETE FROM indirizzo " +
      " WHERE id = :i.id")
    int delete(@BindBean("i") Indirizzo indirizzo);

    @SqlUpdate("DELETE FROM indirizzo " +
      " WHERE id = :id")
    int delete(@Bind("id") String id);

    @SqlQuery("SELECT * FROM indirizzo WHERE " +
      " UPPER(id) LIKE UPPER(:id) AND " +
      " UPPER(via) LIKE UPPER(:via) AND " +
      " UPPER(cap) LIKE UPPER(:cap) AND " +
      " UPPER(citta) LIKE UPPER(:citta))")
    Indirizzo find(
            @Bind("id") String id,
            @Bind("via") String via,
            @Bind("cap") String cap,
            @Bind("citta") String citta
    );
}
