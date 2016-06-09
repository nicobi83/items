package api.dao;

import api.dao.mapper.PersonaResultSetMapper;
import api.exception.NotFoundException;
import api.model.Account;
import api.model.Indirizzo;
import api.model.Persona;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.IOUtils;
import org.joda.time.DateTime;
import org.skife.jdbi.v2.DBI;
import org.skife.jdbi.v2.Handle;
import org.skife.jdbi.v2.Query;
import org.skife.jdbi.v2.sqlobject.CreateSqlObject;
import org.skife.jdbi.v2.sqlobject.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.rmi.NotBoundException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by matthew on 16.05.16.
 */
public abstract class PersonaRepository {

    //posso gestire più Dao.

    Logger logger = LoggerFactory.getLogger(this.getClass());


    @CreateSqlObject
    public abstract PersonaDao personaDao();

    @CreateSqlObject
    public abstract IndirizzoDao indirizzoDao();

    @CreateSqlObject
    public abstract AccountDao accountDao();

    @Inject
    ObjectMapper mapper;
    @Inject
    DBI dbi;

    @PostConstruct
    void init() {
        logger.info("Tabelle esistenti");
    }

    public Set<Persona> find(Map<String, String> query) {
        String sqlFind = "SELECT * FROM persona WHERE " +
                " UPPER(nome) LIKE UPPER(:nome) AND " +
                " UPPER(cognome) LIKE UPPER(:cognome) AND " +
                "( " +
                "  data_nascita IS NULL OR " +
                "  (" +
                "   data_nascita <= :data_nascita_before AND " +
                "   data_nascita >= :data_nascita_after" +
                "   )" +
                ") AND " +
                " ( " +
                " cittadinanza IS NULL OR " +
                " UPPER(cittadinanza) LIKE UPPER(:cittadinanza) " +
                "  ) AND " +
                " UPPER(sesso) = UPPER(:sesso) ";

        String percent = "%";
        String underscore = "_";

        String nome = getParamWithDefault(query, "nome", percent);
        String cognome = getParamWithDefault(query, "cognome", percent);
        String dataNascita = getParamWithDefault(query, "data_nascita", percent);
        String sesso = getParamWithDefault(query, "sesso", "m");
        String cittadinanza = getParamWithDefault(query, "cittadinanza", percent);


        Handle h = dbi.open();
        try {
            // formatter:off
            Query<Persona> tmp = h.createQuery(sqlFind)
                    .map(new PersonaResultSetMapper())
            /* serve per mappare l'oggetto nell'oggetto che noi vogliamo in questo caso Item,
             senza questo il risultato sarebbe un Object perchè avrei Query<Map<String,Object>> ma a noi serve Items.Item*/
                    .bind("nome", nome)
                    .bind("cognome", cognome)
                    .bind("sesso", sesso)
                    .bind("cittadinanza", cittadinanza);

            handleDateFilter(query, "data_nascita_before", tmp, true);
            handleDateFilter(query, "data_nascita_after", tmp, false);

            List<Persona> output = tmp
                    .list();
            Set<Persona> out = output
                    .stream()
                    .map(p -> getPersona(p.getId()))
                    .collect(Collectors.toSet());
            // formatter:on
            return out;
        } catch (Exception e) {
            logger.error(e.getLocalizedMessage(), e);
        } finally {
            IOUtils.closeQuietly(h);
        }
        return null;
    }

    private static void handleDateFilter(Map<String, String> query, String param, Query tmp, boolean before) {
        String dmb = query.get(param);
        if (!org.apache.commons.lang.StringUtils.isBlank(dmb)) {
            DateTime dt = DateTime.parse(dmb);
            if (dt != null) {
                tmp.bind(param, dt.toDate());
                return;
            }
        }
        ;
        if (before) {
            tmp.bind(param, DateTime.parse("9999-12-12").toDate());
        } else {
            tmp.bind(param, DateTime.parse("-4000-01-01").toDate());
        }
    }

    public Set<Persona> findByAccount(String tipoAccount) {
        String sqlFindAccount = "SELECT persona.* FROM persona INNER JOIN account ON account.persona = persona.id"+
          "WHERE UPPER(account.tipo) LIKE UPPER(:tipoAccount)";
        Handle h = dbi.open();
        try {
            Query<Persona> tmp = h.createQuery(sqlFindAccount)
              .map(new PersonaResultSetMapper())
              .bind("tipoAccount", tipoAccount);
            Set<Persona> out = tmp
              .list()
              .stream()
              .collect(Collectors.toSet());
            return out;

        }catch (Exception e){
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

    public Persona getPersona(Integer id) {
        Persona persona = this.personaDao().getPersona(id);
        Indirizzo domicilio = this.indirizzoDao().getIndirizzo(persona.getIndirizzoDomicilio().getId());
        Indirizzo residenza = this.indirizzoDao().getIndirizzo(persona.getIndirizzoResidenza().getId());
        persona.setIndirizzoDomicilio(domicilio);
        persona.setIndirizzoResidenza(residenza);
        return persona;
    }

    private int insert(Persona persona) {
        String cittadinanzaToString = "";
        for (int i = 0; i < persona.getCittadinanza().size(); i++) {
            cittadinanzaToString += persona.getCittadinanza().get(i) + ",";
        }
        return this.personaDao().insert(persona, persona.getIndirizzoResidenza(), persona.getIndirizzoDomicilio(), cittadinanzaToString);
    }


    private int update(Persona persona) {
        String ciTS = cittadinanzaToString(persona);
        return this.personaDao().update(persona, ciTS, persona.getIndirizzoResidenza(), persona.getIndirizzoDomicilio());
    }


    //prendere l'indirizzo dal dao
    //verificare se null o meno
    //se null è da inserire altrimenti è da aggiornare

    private void insertUpdate(Indirizzo indirizzo) {
        Indirizzo indirizzo2 = indirizzoDao().getIndirizzo(indirizzo.getId());
        if (indirizzo2 == null) {
            indirizzoDao().insert(indirizzo);
        } else {
            indirizzoDao().update(indirizzo);
        }
    }

    private void insertUpdate(Account account, Persona persona) throws NotBoundException {
        if (persona != null && persona.getId() != null) {
            Account account2 = accountDao().getAccount(account.getId());
            if (account2 == null) {
                accountDao().insert(account, persona.getId());
            } else {
                accountDao().update(account, persona.getId());
            }
        } else {
            throw new NotBoundException("Persona non passata");
        }
    }

    private void insert(Set<Account> accounts, Persona persona) throws NotBoundException {
        if (persona != null && persona.getId() != null && accounts != null) {
            int[] result = accountDao().insert(accounts, persona.getId());
        }
    }

    //non si mette il @Transaction sul metodo insertUpdate Indirizzo
    //perchè con l' insertUpdate Persona si gestisce tutto, passa tutto da lui
    @Transaction
    public Persona insertUpdate(Persona persona) throws NotBoundException, NotFoundException {
        if (persona.getIndirizzoDomicilio() != null) {
            insertUpdate(persona.getIndirizzoDomicilio());
        }
        if (persona.getIndirizzoResidenza() != null) {
            insertUpdate(persona.getIndirizzoResidenza());
        }


        Persona persona1 = personaDao().getPersona(persona.getId());
        final Persona personaOut;
        int result = -1;
        if (persona1 == null) {

            String ciTS = cittadinanzaToString(persona);

            result = personaDao().insert(persona, persona.getIndirizzoResidenza(), persona.getIndirizzoDomicilio(), ciTS);

            if (result > 0) {
                personaOut = personaDao().getPersona(persona);
                insert(persona.getAccount(), personaOut);
            } else {
                throw new NotFoundException("NON INSERITO!!");
            }
        } else {
            String ciTS = cittadinanzaToString(persona);
            persona.setId(persona1.getId());
            result = personaDao().update(persona, ciTS, persona.getIndirizzoResidenza(), persona.getIndirizzoDomicilio());

            if (result > 0) {
                personaOut = personaDao().getPersona(persona.getId());
                if (persona.getAccount() != null) {
                    persona.getAccount().stream()
                      .forEach(account -> {
                          try {
                              insertUpdate(account, personaOut);
                          } catch (NotBoundException e) {
                              logger.error(e.getLocalizedMessage(), e);
                          }
                      });
                }
            } else {
                throw new NotFoundException("NOT UPDATED!!!!!");
            }
        }
        return personaOut;
    }

    private String cittadinanzaToString(Persona persona) {
        String cittaTS = "";
        for (int i = 0; i < persona.getCittadinanza().size(); i++) {
            cittaTS += persona.getCittadinanza().get(i);
            if (i != (persona.getCittadinanza().size() - 1)) {
                cittaTS += ",";
            }
        }
        return cittaTS;
    }

    public Persona findByEmail(String email)
    {
        return personaDao().findByEmail(email);
    }

}
