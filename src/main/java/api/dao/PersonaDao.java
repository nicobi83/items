package api.dao;

import api.dao.mapper.PersonaResultSetMapper;
import api.model.Indirizzo;
import api.model.Persona;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.BindBean;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

import java.util.Set;

/**
 * Created by Big Johnny on 18/05/2016.
 */
@RegisterMapper(PersonaResultSetMapper.class)
public interface PersonaDao {

    @SqlUpdate("CREATE TABLE persona(id serial primary key, nome varchar, cognome varchar, dataNascita timestamp, ,indirizzoResidenza varchar, indirizzoDomicilio varchar, cittadinanza text, sesso varchar)")
    int create();

    @SqlQuery("SELECT * FROM persona WHERE id = :id")
    Persona getPersona(@Bind("id") Integer id);

    @SqlQuery("SELECT * FROM persona WHERE nome = :i.nome AND cognome = :i.cognome AND sesso = :i.sesso")
    Persona getPersona(@BindBean("i") Persona persona);

    @SqlQuery("SELECT * FROM persona")
    Set<Persona> listAll();

    @SqlUpdate("INSERT INTO persona (nome, cognome, data_nascita, indirizzo_residenza, indirizzo_domicilio, sesso, cittadinanza, email)" +
      " VALUES ( :i.nome, :i.cognome, :i.dataNascita, :r.id, :d.id, :i.sesso, :cittadinanza, :i.email)")
    int insert(@BindBean("i") Persona persona, @BindBean("r") Indirizzo residenza, @BindBean("d") Indirizzo domicilio , @Bind("cittadinanza") String cittadinanza);

    @SqlUpdate("UPDATE persona " +
      " SET nome = :i.nome," +
      " cognome = :i.cognome, " +
      " data_nascita = :i.dataNascita, " +
      " indirizzo_residenza = :r.id, " +
      " indirizzo_domicilio = :d.id, " +
      " cittadinanza = :cittadinanza, " +
      " sesso = :i.sesso " +
      " email = :i.email" +
      " WHERE id = :i.id")
    int update(@BindBean("i") Persona persona,@Bind("cittadinanza") String cittadinanza, @BindBean("r") Indirizzo residenza, @BindBean("d") Indirizzo domicilio );

    @SqlUpdate("DELETE FROM persona " +
      " WHERE id = :i.id")
    int delete(@BindBean("i") Persona persona);

    @SqlUpdate("DELETE FROM persona " +
      " WHERE id = :id")
    int delete(@Bind("id") Integer id);

    @SqlQuery("SELECT * FROM persona WHERE " +
      " UPPER(id) LIKE UPPER(:id) AND " +
      " UPPER(nome) LIKE UPPER(:nome) AND " +
      " UPPER(cognome) LIKE UPPER(:cognome) "
      )
    Persona find(
      @Bind("id") Integer id,
      @Bind("nome") String nome,
      @Bind("cognome") String cognome,
      @Bind("data_nascita") String dataNascita,
      @Bind("indirizzo_residenza") String indirizzoResidenza,
      @Bind("indirizzo_domicilio") String indirizzoDomicilio,
      @Bind("cittadinanza") String cittadinanza,
      @Bind("sesso") String sesso
    );

    @SqlQuery("SELECT * FROM persona WHERE " +
      " UPPER(email) LIKE UPPER(:email)"
    )

    Persona findByEmail(
      @Bind("email") String email
    );
}
