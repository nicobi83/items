package api.service;

import api.Application;
import api.dao.ItemsRepository;
import api.dao.PersonaRepository;
import api.model.Indirizzo;
import api.model.Items;
import org.apache.commons.lang.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.inject.Inject;
import org.junit.After;

import java.awt.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.in;

/**
 * Created by AlbertoBortolato on 18/05/2016.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(
  classes ={
    Application.class
  }
)

@DirtiesContext
public class IndirizzoServiceIT {

    Logger logger = LoggerFactory.getLogger(this.getClass());
    int n=10;
    Indirizzo indirizzo;

    @Inject
    IndirizzoService service;

    @Inject
    PersonaRepository repository;

    @Before
    public void setUp() throws Exception {
        for(int i = 1; i <= n; i++){

            Indirizzo indirizzoPool = new Indirizzo();
            indirizzoPool.setId("initTest"+Integer.toString(i));
            indirizzoPool.setVia(String.format("indirizzoPool-%s", i+200));
            indirizzoPool.setCitta(RandomStringUtils.random(25, true, true));
            indirizzoPool.setCap(RandomStringUtils.random(150, true, true));
            service.add(indirizzoPool);
            logger.debug("Added indirizzoPool: " + indirizzoPool.toString());
        }
        indirizzo = new Indirizzo();
        indirizzo.setId("add-test");
        indirizzo.setVia(String.format("indirizzo-%s", indirizzo.getId()));
        indirizzo.setCitta(RandomStringUtils.random(25, true, true));
        indirizzo.setCap(RandomStringUtils.random(150, true, true));
    }

    @After
    public void tearDown() throws Exception {
        for(int i = 1; i <= n; i++){
            Indirizzo indirizzoPool = new Indirizzo();
            indirizzoPool.setId("initTest"+Integer.toString(i));
            indirizzoPool.setVia(String.format("indirizzoPool-%s", i+200));
            indirizzoPool.setCitta(RandomStringUtils.random(25, true, true));
            indirizzoPool.setCap(RandomStringUtils.random(150, true, true));
            service.delete(indirizzoPool);
        }
    }

    @Test
    public void add() throws Exception {
        assertThat(indirizzo).isNotIn(repository.indirizzoDao().listAll());
        service.add(indirizzo);
        assertThat(indirizzo).isIn(repository.indirizzoDao().listAll());
        //provo ad riinesrire indirizzo
        service.add(indirizzo);
        service.delete(indirizzo);

    }

    @Test
    public void delete() throws Exception {
        service.add(indirizzo);
        assertThat(indirizzo).isIn(repository.indirizzoDao().listAll());
        service.delete(indirizzo);
        assertThat(indirizzo).isNotIn(repository.indirizzoDao().listAll());
        service.delete(indirizzo);
    }


    @Test
    public void getIndirizzo() throws Exception {
        Indirizzo indirizzo = new Indirizzo();
        Indirizzo prova = new Indirizzo();
        indirizzo.setId("get-address-test");
        indirizzo.setVia(String.format("indirizzo-%s", indirizzo.getId()));
        indirizzo.setCitta(RandomStringUtils.random(25, true, true));
        indirizzo.setCap(RandomStringUtils.random(150, true, true));

        service.add(indirizzo);
        prova.setId(indirizzo.getId());
        assertThat(prova.getId()).isEqualTo(indirizzo.getId());

    }

    @Test
    public void setIndirizzo() throws Exception {

    }


    @Test
    public void update() throws Exception {
        Indirizzo indirizzo_test = new Indirizzo();
        indirizzo_test.setId("add-testnuovo");
        indirizzo_test.setVia(String.format("indirizzo_test-%s", indirizzo_test.getId()));
        indirizzo_test.setCitta(RandomStringUtils.random(25, true, true));
        indirizzo_test.setCap(RandomStringUtils.random(150, true, true));

        service.add(indirizzo);
        service.update(indirizzo,indirizzo_test);
        assertThat(indirizzo_test).isIn(repository.indirizzoDao().listAll());
        service.delete(indirizzo_test);
    }

    @Test
    public void getById() throws Exception {
        service.add(indirizzo);
        assertThat(indirizzo == service.getById(indirizzo.getId()));
        service.delete(indirizzo);
    }

    @Test
    public void findItems() throws Exception {
        Map<String,String> mappa = new HashMap<>();
        mappa.put("id",indirizzo.getId());
        service.add(indirizzo);
        //assertThat(service.findItems(mappa)).isNotEmpty();
        assertThat(indirizzo).isIn(service.findItems(mappa));
        service.delete(indirizzo);
    }
}
