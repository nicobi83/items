package api.dao.mapper;

import api.model.Indirizzo;
import api.model.Items;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.BindBean;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.Mapper;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

import java.util.Date;
import java.util.Set;

/**
 * Created by nicob on 17/05/2016.
 */
@RegisterMapper(IndirizzoResultSetMapper.class)

public interface IndirizzoDao {

    @SqlQuery("SELECT * FROM indirizzo WHERE id = :id")
    @Mapper(IndirizzoResultSetMapper.class)
    Indirizzo indirizzo(@Bind("id") String id);
    //il bind si va a sostituire a :id

    @SqlQuery("SELECT * FROM indirizzo")
    Set<Indirizzo> listAll();

    @SqlQuery("SELECT * FROM indirizzo WHERE " +
      " UPPER(id) LIKE UPPER(:id) AND " +
      " UPPER(via) LIKE UPPER(:via) AND " +
      " UPPER(citta) LIKE UPPER(:citta) AND " +
      " UPPER(cap) LIKE UPPER(:cap) AND " +
      "")
    Indirizzo find(
      @Bind("id") String id,
      @Bind("via") String via,
      @Bind("città") String citta,
      @Bind("cap") String cap
    );

    @SqlUpdate("INSERT INTO indirizzo (id, via, paese, citta, cap)" +
      " VALUES (:i.id, :i.via, :i.paese, :i.citta, :i.cap)")

    int insert(@BindBean("i")Indirizzo indirizzo);
    //bindbean indica che i sarebbe l'oggetto quindi chiama il getter e setter dell'oggetto
    //bind per il singolo valore, bindbean per prendere tutto l'oggetto

    @SqlUpdate("UPDATE indirizzo " +
      " SET via = :i.via," +
      " citta = :i.citta, " +
      " cap = :i.cap" +
      " WHERE id = :i.id")
    int update(@BindBean("i") Indirizzo indirizzo);


    //sqlupdate se cancello o modifico i dati altrimenti per altre cose sqlquery


    @SqlUpdate("DELETE FROM indirizzo " +
      " WHERE id = :i.id")
    int delete(@BindBean("i") Indirizzo indirizzo);

    @SqlUpdate("DELETE FROM indirizzo  " +
      " WHERE id = :id")
    int delete(@Bind("id") String id);

    @SqlUpdate("DELETE FROM indirizzo " +
      " WHERE via = :via")
    int deleteByName(@Bind("via") String via);

    @SqlQuery("SELECT values FROM indirizzo WHERE id = :id")
    String getValues(@Bind("id") String id);

}
