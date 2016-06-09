package api.dao.mapper;

import api.model.Indirizzo;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Thomas on 17/05/2016.
 */
public class IndirizzoResultSetMapper implements ResultSetMapper<Indirizzo> {

    @Override
    public Indirizzo map(int i, ResultSet rs, StatementContext statementContext) throws SQLException {
        Indirizzo temp = new Indirizzo();
        temp.setId(rs.getString("id"));
        temp.setVia(rs.getString("via"));
        temp.setCap(rs.getString("cap"));
        temp.setCitta(rs.getString("citta"));
        return temp;
    }
}
