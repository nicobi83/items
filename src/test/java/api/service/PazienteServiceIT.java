/*package api.service;

import api.Application;
import api.model.Items;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang.RandomStringUtils;
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
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
*/
/**
 * Created by matthew on 03.05.16.
 */
/*
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(
        classes ={
                Application.class
        }
)
@DirtiesContext
public class PazienteServiceIT {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Inject
    PazienteService service;
    @Inject
    Items items;
    @Inject
    ObjectMapper mapper;
    @Value("${api.items.file}")
    String file;
    Path filePath;

    @Before
    public void setUp() throws Exception {
        for(int i = 1; i <= 10000; i++){
            Paziente item = new Paziente();
            item.setId(Integer.toString(i));
            item.setName(String.format("item-%s", i));
            item.setContent(RandomStringUtils.random(25, true, true));
            item.setDescription(RandomStringUtils.random(150, true, true));
            item.setIndirizzoResidenza(RandomStringUtils.random(150, true, true));
            item.getValues().put("test", "test-todo-" + RandomStringUtils.random(15, true, true));
            for(int j = 0; j < 5; j++){
                item.getValues().put(RandomStringUtils.random(4, true, true), RandomStringUtils.random(25, true, true));
            }
            items.getItems().add(item);
            logger.debug("Added item: " + item.toString());
        }
    }*/
/*
    @After
    public void tearDown() throws Exception {
        filePath = Paths.get(file);
        Files.deleteIfExists(filePath);
    }
*//*
    @Test
    public void add() throws Exception {
        Paziente item = new Paziente();
        item.setId("add-test");
        item.setName(String.format("item-%s", item.getId()));
        item.setContent(RandomStringUtils.random(25, true, true));
        item.setDescription(RandomStringUtils.random(150, true, true));
        item.setIndirizzoResidenza(RandomStringUtils.random(150, true, true));
        item.getValues().put("test", "test-todo-" + RandomStringUtils.random(15, true, true));
        for(int j = 0; j < 5; j++){
            item.getValues().put(RandomStringUtils.random(4), RandomStringUtils.random(25, true, true));
        }
        assertThat(item).isNotIn(items.getItems());
        service.add(item);
        assertThat(item).isIn(items.getItems());
    }

    @Test
    public void update() throws Exception {
        Paziente item_add = new Paziente();
        Paziente item_update = new Paziente();

        item_add.setId("add_update_paziente");
        item_update.setId("update_test_paziente");
        item_add.setName(String.format("add-item-%s", item_add.getId()));
        item_update.setName(String.format("update-item-%s", item_update.getId()));
        item_add.setContent(RandomStringUtils.random(25, true, true));
        item_add.setDescription(RandomStringUtils.random(150, true, true));
        item_add.setIndirizzoResidenza(RandomStringUtils.random(150, true, true));

        service.add(item_add);
        assertThat(item_add).isIn(items.getItems());
        service.update(item_add, item_update);
        boolean temp_bool = service.getById(item_update.getId()).getName().contains(item_update.getName());
        assertThat(temp_bool).isTrue();
        assertThat(item_update).isIn(items.getItems());
    }

    @Test
    public void delete() throws Exception {
        Paziente item = new Paziente();
        item.setId("delete_test");
        service.add(item);
        assertThat(item).isIn(items.getItems());
        service.delete(item.getId());
        assertThat(item).isNotIn(items.getItems());
    }
*/
    /*@Test
    public void items() throws Exception {
        assertThat(service.items()).isEqualTo(this.items);
        Items temp_items = new Items();
        assertThat(service.items()).isNotEqualTo(temp_items);
    }*/
/*
    @Test
    public void getItem() throws Exception {
        String id1 = "get_test";
        String id2 = "get_test";
        Paziente item = new Paziente();

        item.setId(id1);
        service.add(item);
        Paziente temp_item = new Paziente();

        temp_item.setId(id2);
        assertThat(items.getItems().contains(temp_item)).isTrue();
    }

    @Test
    public void findItems() throws Exception {
        List<Paziente> ls = new ArrayList<>();
        Map<String, String> query = new HashMap<>();

        query.put("name", "t");
        ls = service.findItems(query);
        assertThat(ls).isNotEmpty();
    }

}
*/
