package api.dao;

/**
 * Created by Luca on 23/05/2016.
 */

import api.dao.mapper.AccountResultSetMapper;
import api.model.Account;
import org.skife.jdbi.v2.sqlobject.*;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

import java.util.Set;

@RegisterMapper(AccountResultSetMapper.class)
public interface AccountDao{

    @SqlQuery("SELECT * FROM account")
    Set<Account> listAll();

    @SqlBatch("INSERT INTO account (id, persona, valore, tipo)" +
      " VALUES (:i.id, :persona, :i.valore, :i.tipo)")
    int[] insert(@BindBean("i") Set<Account> accounts, @Bind("persona") Integer id);

    @SqlQuery("SELECT * FROM account WHERE id = :id")
    Account getAccount(@Bind("id") String id);

    @SqlQuery("SELECT * FROM account WHERE valore = :i.valore AND tipo = :i.tipo ")
    Set<Account> getAccounts(@Bind("persona") Integer id);

    @SqlUpdate("INSERT INTO account (id, persona, valore, tipo)" +
      " VALUES (:i.id, :persona, :i.valore, :i.tipo)")
    int insert(@BindBean("i") Account account, @Bind("persona") Integer id);

    @SqlUpdate("UPDATE account " +
      " SET persona = :persona," +
      " valore = :i.valore, " +
      " tipo = :i.tipo " +
      " WHERE id = :i.id")
    int update(@BindBean("i") Account account, @Bind("persona") Integer id);

    @SqlUpdate("DELETE FROM account " +
      " WHERE id = :i.id")
    int delete(@BindBean("i") Account account);

    @SqlUpdate("DELETE FROM account " +
      " WHERE id = :id")
    int delete(@Bind("id") String id);


}
