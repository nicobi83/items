package api.model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by AlbertoBortolato on 18/05/2016.
 */
public class IndirizzoTest {
    Logger logger = LoggerFactory.getLogger(this.getClass());


    Indirizzo indirizzo;
    Indirizzo indirizzoTest;

    @Before
    public void setUp() throws Exception {

        indirizzo = new Indirizzo();
        indirizzo.setId("uno");
        indirizzo.setVia("uno");
        indirizzo.setCap("uno");
        indirizzoTest = new Indirizzo();
        indirizzoTest.setId("due");
        indirizzoTest.setVia("due");
        indirizzo.setCap("due");
    }

    @After
    public void tearDown() throws Exception {

    }


    @Test
    public void testEquals() throws Exception {
        boolean should_be_different = indirizzo.equals(indirizzoTest);
        logger.info("Should be different: " + should_be_different);
        assertThat(should_be_different).isFalse();
        indirizzo.setId(indirizzoTest.getId());
        boolean should_be_same = indirizzo.equals(indirizzoTest);
        logger.info("Should be same: " + should_be_same);
        assertThat(should_be_same).isTrue();
    }

    @Test
    public void testHashCode() throws Exception {
        boolean hashcode_should_be_different = (indirizzoTest.hashCode() == indirizzo.hashCode());
        assertThat(hashcode_should_be_different).isFalse();
        indirizzo.setId(indirizzoTest.getId());
        boolean hashcode_should_be_same = (indirizzoTest.hashCode() == indirizzo.hashCode());
        assertThat(hashcode_should_be_same).isTrue();
        String id = indirizzo.getId();
        boolean hashcode_should_be_same_1 = (id.hashCode() == indirizzo.hashCode());
        assertThat(hashcode_should_be_same_1).isTrue();

    }

    @Test
    public void testToString() throws Exception {
        boolean string_should_be_different = (indirizzoTest.toString().equals(indirizzo.toString()));
        logger.info("Should be different: " + string_should_be_different);
        assertThat(string_should_be_different).isFalse();
        indirizzo.setId(indirizzoTest.getId());
        indirizzo.setVia(indirizzoTest.getVia());
        indirizzo.setCap(indirizzoTest.getCap());

        boolean string_should_be_same = (indirizzoTest.toString().equals(indirizzo.toString()));
        logger.info("Should be same: " + string_should_be_same);
        assertThat(string_should_be_same).isTrue();
    }

}
