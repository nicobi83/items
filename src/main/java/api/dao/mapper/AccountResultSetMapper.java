package api.dao.mapper;

import api.model.Account;
import api.model.Persona;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Giovanni on 23/05/2016.
 */
public class AccountResultSetMapper implements ResultSetMapper<Account> {

    @Override
    public Account map (int i, ResultSet rs, StatementContext statementContext) throws SQLException {

        Account account = new Account();
        account.setId(rs.getString("id"));

        Persona persona = new Persona();
        persona.setId(rs.getInt("persona"));
        account.setPersona(persona);

        account.setTipo(rs.getString("tipo"));
        account.setValore(rs.getString("valore"));

        return account;
    }
}
