package api.dao;

import api.dao.mapper.MacchinaResultSetMapper;
import api.model.Account;
import api.model.Macchine;
import org.skife.jdbi.v2.sqlobject.*;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

import java.util.Set;

/**
 * Created by NICOLA on 13/06/2016.
 */

@RegisterMapper(MacchinaResultSetMapper.class)
public interface MacchinaDao {

    @SqlUpdate("INSERT INTO macchine (id, produttore, modello, categoria, targa, cilindrata, potenza, potenza_fiscale, colore)" +
            " VALUES (:i.id, :targa, :i.modello, :i.categoria, :i.produttore, :i.cilindrata, :i.potenza, :i.potenza_fiscale, :i.colore)")
    int insert(@BindBean("i") Macchine.Macchina macchina, @Bind("targa") Integer id);

    @SqlUpdate("UPDATE macchine " +
            " SET persona = :persona," +
            " valore = :i.valore, " +
            " tipo = :i.tipo " +
            " WHERE id = :i.id")
    int update(@BindBean("i") Account account, @Bind("persona") Integer id);

    @SqlUpdate("DELETE FROM macchine " +
            " WHERE id = :i.id")
    int delete(@BindBean("i") Macchine.Macchina macchina);

    @SqlUpdate("DELETE FROM macchine " +
            " WHERE id = :id")
    int delete(@Bind("id") String id);

    @SqlQuery("SELECT * FROM macchine")
    Set<Macchine.Macchina> listAll();

    @SqlQuery("SELECT * FROM macchine WHERE produttore = :i.produttore AND modello = :i.modello")
    Set<Macchine.Macchina> getMacchinas(@Bind("targa") Integer id);

    @SqlQuery("SELECT * FROM macchine WHERE id = :id")
    Macchine.Macchina getMacchina(@Bind("id") String id);


}