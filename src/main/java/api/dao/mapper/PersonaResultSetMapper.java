package api.dao.mapper;

import api.model.Indirizzo;
import api.model.Persona;
import org.apache.commons.lang.StringUtils;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;
import org.skife.jdbi.v2.tweak.ResultSetMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by EKR on 19/05/2016.
 */

public class PersonaResultSetMapper implements ResultSetMapper<Persona> {

    @Override
    public Persona map(int i, ResultSet rs, StatementContext statementContext) throws SQLException {


        Indirizzo residenza = new Indirizzo();
        Indirizzo domicilio = new Indirizzo();

        Persona persona = new Persona();
        persona.setId(rs.getInt("id"));
        persona.setNome(rs.getString("nome"));
        persona.setCognome(rs.getString("cognome"));
        persona.setDataNascita(rs.getDate("data_nascita"));

        int count = rs.getMetaData().getColumnCount();
        for(int ii = 1; ii <= count; ii++) {
            String col_name = rs.getMetaData().getColumnName(ii);
            if(col_name.toLowerCase().equals("email")) {
                String email = rs.getString("email");
                if (!StringUtils.isBlank(email)) {
                    persona.setEmail(email);
                }
            }
            if(col_name.toLowerCase().equals("numero_telefono")) {
                String numTel = rs.getString("numero_telefono");
                if (!StringUtils.isBlank(numTel)) {
                    persona.setNumTel(numTel);
                }
            }
            if(col_name.toLowerCase().equals("numero_cellulare")) {
                String NumCel = rs.getString("numero_cellulare");
                if (!StringUtils.isBlank(NumCel)) {
                    persona.setNumCel(NumCel);
                }
            }
        }

        String sesso = rs.getString("sesso");
        if (!StringUtils.isBlank(sesso)) {
            persona.setSesso(sesso.charAt(0));
        }
        residenza.setId(rs.getString("indirizzo_residenza"));
        domicilio.setId(rs.getString("indirizzo_domicilio"));
        persona.setIndirizzoDomicilio(domicilio);
        persona.setIndirizzoResidenza(residenza);

        //String joinedCittadinanze = StringUtils.join(persona.getCittadinanza(), ',');
        String joinedCittadinanze = rs.getString("cittadinanza");
        if(joinedCittadinanze != null) {
            String[] cittadinanze = StringUtils.split(joinedCittadinanze, ',');
            persona.setCittadinanza(Arrays.asList(cittadinanze));
        }
        return persona;
    }
}
