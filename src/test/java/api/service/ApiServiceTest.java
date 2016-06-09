/*package api.service;

import api.Application;
import api.model.Items;
import api.model.Paziente;
import org.apache.commons.lang.RandomStringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.inject.Inject;
import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThat;
*/
/**
 * Created by Luca on 06/05/2016.
 */
/*
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(
  classes ={
    Application.class
  })
  @DirtiesContext
public class ApiServiceTest {

    Logger logger = LoggerFactory.getLogger(this.getClass());
    @Inject
    ApiService service;
    @Inject
    Items items;
    @Value("${api.items.file}")
    String file;
    java.nio.file.Path filePath;

    @Before
    public void setUp() throws Exception {
        for(int i = 1; i <= 10000; i++){
            Paziente paziente = new Paziente();
            paziente.setId(Integer.toString(i));
            paziente.setName(String.format("paziente-%s", i));
            paziente.setContent(RandomStringUtils.random(25, true, true));
            paziente.setDescription(RandomStringUtils.random(150, true, true));
            paziente.setCodiceFiscale(RandomStringUtils.random(25, true, true));
            paziente.getValues().put("test", "test-todo-" + RandomStringUtils.random(15, true, true));
            for(int j = 0; j < 5; j++){
                paziente.getValues().put(RandomStringUtils.random(4, true, true), RandomStringUtils.random(25, true, true));
            }
            items.getItems().add(paziente);
            logger.debug("Added paziente: " + paziente.toString());
        }
    }

    @After
    public void tearDown() throws Exception {
        filePath = Paths.get(file);
       // Files.deleteIfExists(filePath);
    }

    @Test
    public void add() throws Exception {
        Paziente paziente = new Paziente();
        paziente.setId("add-test");
        paziente.setName(String.format("paziente-%s", paziente.getId()));
        paziente.setContent(RandomStringUtils.random(25, true, true));
        paziente.setDescription(RandomStringUtils.random(150, true, true));
        paziente.getValues().put("test", "test-todo-" + RandomStringUtils.random(15, true, true));
        for(int j = 0; j < 5; j++){
            paziente.getValues().put(RandomStringUtils.random(4), RandomStringUtils.random(25, true, true));
        }
        assertThat(paziente).isNotIn(items.getItems());
        service.add(paziente);
        assertThat(paziente).isIn(items.getItems());
    }

    @Test
    public void update() throws Exception {

        Paziente paziente_add = new Paziente();
        Paziente paziente_update = new Paziente();

        paziente_add.setId("add_update");
        paziente_update.setId("update_test");
        paziente_add.setName(String.format("add-item-%s", paziente_add.getId()));
        paziente_update.setName(String.format("update-item-%s", paziente_update.getId()));
        paziente_add.setContent(RandomStringUtils.random(25, true, true));
        paziente_add.setDescription(RandomStringUtils.random(150, true, true));

        service.add(paziente_add);
        assertThat(paziente_add).isIn(items.getItems());
        service.update(paziente_add, paziente_update);
       // boolean temp_bool = ;
        assertThat(service.getById(paziente_update.getId()).getName().contains(paziente_add.getName())).isTrue();
        assertThat(paziente_update).isIn(items.getItems());

    }
    @Test
    public void delete() throws Exception {
        Paziente paziente = new Paziente();
        paziente.setId("delete_test");
        service.add(paziente);
        assertThat(paziente).isIn(items.getItems());
        service.delete(paziente.getId());
        assertThat(paziente).isNotIn(items.getItems());
    }

    @Test
    public void items() throws Exception {
        assertThat(service.items).isEqualTo(this.items);
        Items temp_items = new Items();
        assertThat(service.items).isNotEqualTo(temp_items);
    }

    @Test
    public void getItem() throws Exception {
        String id1 = "get_test";
        String id2 = "get_test";
        Paziente paziente = new Paziente();

        paziente.setId(id1);
        service.add(paziente);
        Paziente paziente_item = new Paziente();

        paziente_item.setId(id2);
        assertThat(items.getItems().contains(paziente_item)).isTrue();
    }


}
*/
