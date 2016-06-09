package api.model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by Luca on 20/05/2016.
 */
public class PersonaTest {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    Persona persona;
    Persona persona_test;

    @Before
    public void setUp() throws Exception {

        persona = new Persona();
        persona.setId(1);
        persona.setEmail("pinco@pallino.com");
        persona.setNome("nome_uno");
        persona.setCognome("cognome_uno");
        persona.setSesso('M');
        persona_test = new Persona();
        persona_test.setId(2);
        persona_test.setEmail("pisdfdsf@mail.com");
        persona_test.setNome("nome_due");
        persona_test.setCognome("cognome_due");
        persona_test.setSesso('M');
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testEquals() throws Exception {
        boolean should_be_different = persona.equals(persona_test);
        logger.info("Should be different: " + should_be_different);
        assertThat(should_be_different).isFalse();
        persona.setEmail(persona_test.getEmail());
        persona.setNome(persona_test.getNome());
        persona.setCognome(persona_test.getCognome());
        boolean should_be_same = persona.equals(persona_test);
        logger.info("Should be same: " + should_be_same);
        assertThat(should_be_same).isTrue();
    }

    @Test
    public void testHashCode() throws Exception {
  //    persona.hashCode();
        boolean hashcode_should_be_different = (persona_test.hashCode() == persona.hashCode());
        assertThat(hashcode_should_be_different).isFalse();
        persona.setEmail(persona_test.getEmail());
        persona.setNome(persona_test.getNome());
        persona.setCognome(persona_test.getCognome());
        boolean hashcode_should_be_same = (persona_test.hashCode() == persona.hashCode());
        assertThat(hashcode_should_be_same).isTrue();

    }

    @Test
    public void testToString() throws Exception {
        boolean string_should_be_different = (persona_test.toString().equals(persona.toString()));
        logger.info("Should be different: " + string_should_be_different);
        assertThat(string_should_be_different).isFalse();
        persona.setEmail(persona_test.getEmail());
        persona.setNome(persona_test.getNome());
        persona.setCognome(persona_test.getCognome());
        boolean string_should_be_same = (persona_test.toString().equals(persona.toString()));
        logger.info("Should be same: " + string_should_be_same);
        assertThat(string_should_be_same).isTrue();
    }

}
