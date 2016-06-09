package api.service;

import api.Application;
import api.dao.PersonaRepository;
import api.model.Account;
import api.model.Indirizzo;
import api.model.Persona;
import org.apache.commons.lang.RandomStringUtils;
import org.h2.jdbc.JdbcBatchUpdateException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.inject.Inject;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by Luca on 20/05/2016.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(
  classes ={
    Application.class
  }
)
public class PersonaServiceIT {
    Logger logger = LoggerFactory.getLogger(this.getClass());
    int n=5;
    Persona persona;

    @Inject
    PersonaService service;

    @Inject
    PersonaRepository repository;

    @Before
    public void setUp() throws Exception {
        for(int i = 1; i <= n; i++){

            Persona personaPool = new Persona();
            personaPool.setNome(String.format("indirizzoPool-%s", i+200));
            personaPool.setCognome(RandomStringUtils.random(25, true, true));
            personaPool.setEmail("emailUguale" + i);

            Indirizzo indirizzoPool = new Indirizzo();
            indirizzoPool.setCitta("Napoli" +i);
            indirizzoPool.setCap("30173" +i);
            indirizzoPool.setId("id" + i);

            personaPool.setIndirizzoDomicilio(indirizzoPool);
            personaPool.setIndirizzoResidenza(indirizzoPool);

            service.add(personaPool);
            logger.debug("Added indirizzoPool: " + personaPool.toString());

            Set<Account> accountsPool = new HashSet<>();
            Account accountPool = new Account();

            if (i%2 != 0) {
                accountPool.setTipo("facebook "+i);
                accountPool.setValore("indirizzo facebook " +i);
                accountsPool.add(accountPool);
            }
            if(i%3 == 0)
            {
                accountPool.setTipo("facebook2 " +i);
                accountPool.setValore("indirizzo facebook2 " +i);
                accountsPool.add(accountPool);
            }
            personaPool.setAccount(accountsPool);
        }
        Account account = new Account();
        account.setTipo("facebook" + RandomStringUtils.random(25, true, true));
        account.setValore("indirizzo" + RandomStringUtils.random(25, true, true));
        Set<Account> accounts = new HashSet<>();
        accounts.add(account);
        persona = new Persona();
        persona.setNome("indirizzo");
        persona.setCognome(RandomStringUtils.random(25, true, true));
        persona.setAccount(accounts);
        persona.setEmail("email" + RandomStringUtils.random(25, true, true));

        Indirizzo indirizzo = new Indirizzo();
        indirizzo.setCitta("Venezia");
        indirizzo.setCap("30175");
        indirizzo.setId("idVenezia");

        persona.setIndirizzoDomicilio(indirizzo);
        persona.setIndirizzoResidenza(indirizzo);
    }

    @After
    public void tearDown() throws Exception {
        for(int i = 1; i <= n; i++){
            Persona personaPool = new Persona();
            personaPool.setNome(String.format("indirizzoPool-%s", i+200));
            personaPool.setCognome(RandomStringUtils.random(25, true, true));
            personaPool.setEmail("emailUguale" + i);

            Indirizzo indirizzoPool = new Indirizzo();
            indirizzoPool.setCitta("Napoli" + i);
            indirizzoPool.setCap("30173" +i);
            indirizzoPool.setId("id" + i);

            personaPool.setIndirizzoDomicilio(indirizzoPool);
            personaPool.setIndirizzoResidenza(indirizzoPool);
            logger.debug("Added indirizzoPool: " + personaPool.toString());

            Set<Account> accountsPool = new HashSet<>();
            Account accountPool = new Account();

            if (i%2 != 0) {
                accountPool.setTipo("facebook "+i);
                accountPool.setValore("indirizzo facebook " +i);
                accountsPool.add(accountPool);
            }
            if(i%3 == 0)
            {
                accountPool.setTipo("facebook2 " +i);
                accountPool.setValore("indirizzo facebook2 " +i);
                accountsPool.add(accountPool);
            }
            personaPool.setAccount(accountsPool);
            service.delete(personaPool);
        }
    }

    @Test
    public void add() throws Exception {
        assertThat(persona).isNotIn(repository.personaDao().listAll());
        //Persona toTest = service.add(persona);
        //Persona withCorrectId = repository.personaDao().getPersona(persona);
        try {
            service.add(persona);
            persona.setId(repository.personaDao().getPersona(persona).getId());
            Set<Persona> lst = repository.personaDao().listAll();
            assertThat(persona).isIn(lst);
        }
        catch (Exception e)
        {
            logger.info("errore: account già presente");
        }
        // service.delete(toTest);
        service.delete(persona);
    }

    @Test
    public void delete() throws Exception {
        service.add(persona);
        assertThat(persona).isIn(repository.personaDao().listAll());
        persona.setId(repository.personaDao().getPersona(persona).getId());
        service.delete(persona);
        assertThat(persona).isNotIn(repository.personaDao().listAll());
    }

    @Test
    public void update() throws Exception {
        Persona persona_test = new Persona();
        persona_test.setNome(String.format("persona_test"));
        persona_test.setCognome(RandomStringUtils.random(25, true, true));

        List<String> cittadinanza = new ArrayList<>();
        cittadinanza.add("italia");

        persona_test.setCittadinanza(cittadinanza);

        Account accountTest = new Account();
        accountTest.setTipo("facebook_update" + RandomStringUtils.random(25, true, true));
        accountTest.setValore("indirizzo facebook_update" + RandomStringUtils.random(25, true, true));
        Set<Account> accountsTest = new HashSet<>();
        accountsTest.add(accountTest);
        persona_test.setAccount(accountsTest);
        persona_test.setEmail(RandomStringUtils.random(25, true, true));

        Indirizzo indirizzo_test = new Indirizzo();
        indirizzo_test.setCitta("Venezia_updategra");
        indirizzo_test.setVia("via man nel culo");
        indirizzo_test.setId("idVenezia_updatesfdaf");

        persona_test.setIndirizzoDomicilio(indirizzo_test);
        persona_test.setIndirizzoResidenza(indirizzo_test);

        service.add(persona);
        persona_test.setId(repository.personaDao().getPersona(persona).getId());

        service.update(persona,persona_test);
        //Set<Persona> lst = repository.personaDao().listAll();
        assertThat(persona_test).isIn(repository.personaDao().listAll());
        service.delete(persona_test);
    }

    @Test
    public void getById() throws Exception {
        service.add(persona);
        Persona person = service.repository.personaDao().getPersona(persona);
        Persona withCorrectId = repository.personaDao().getPersona(persona);
        String id = String.valueOf(withCorrectId.getId());
        assertThat(person == service.getById(id));
        service.delete(person);
    }

    @Test
    public void findItems() throws Exception {
        Map<String,String> mappa = new HashMap<>();
        String Persona_id = String.valueOf(persona.getId());
        mappa.put("id",Persona_id);
        service.add(persona);
        //assertThat(service.findItems(mappa)).isNotEmpty();
        assertThat(persona).isIn(service.findItems(mappa));
        service.delete(persona);
    }
}
