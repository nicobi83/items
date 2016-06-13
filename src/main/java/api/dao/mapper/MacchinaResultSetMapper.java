package api.dao.mapper;

import api.model.Macchine;
import api.model.TipiMacchina;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by NICOLA on 13/06/2016.
 */
public class MacchinaResultSetMapper implements ResultSetMapper<Macchine.Macchina> {

    @Override
    public Macchine.Macchina map(int i, ResultSet rs, StatementContext statementContext) throws SQLException {

        Macchine.Macchina auto = new Macchine.Macchina();
        auto.setProduttore( rs.getString("Produttore") );
        auto.setModello( rs.getString("Modello") );
        auto.setTarga( rs.getString("Targa") );
        auto.setColor( rs.getString("Colore") );
        auto.setCategoria( rs.getString("Tipo di auto") ); //come potevo creare delle colonne dall'enumerazione TipiMacchina?
        auto.setCilindrata( rs.getString("Cilindrata(cc/lt)") );
        auto.setPotenza( rs.getString("Potenza(CV/HP)") );
        auto.setPotenza_fiscale( rs.getString("Cavalli Fiscali") );

        return auto;
    }
}
