package api.model;

import org.apache.tomcat.jni.File;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by NICOLA on 04/06/2016.
 */
public class MacchinaTest {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    Macchina macchina = new Macchina();
    Macchina macchina_test = new Macchina();
    String path = "C:\\Users\\NICOLA\\prova.txt";
    Path filepath = Paths.get(path);
    java.io.File file = new java.io.File(path);

    @Before
    public void setUp() throws Exception {

        macchina.setColor("navy");
        macchina.setTipo(TipiMacchina.BERLINA);
        macchina.setName("opel");

        macchina_test.setColor("white");
        macchina_test.setTipo(TipiMacchina.SUV);
        macchina_test.setName("chrysler");

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testEquals() throws Exception {

        boolean should_be_different = macchina.equals(macchina_test);
        logger.info("Should be different: " + should_be_different);
        assertThat(should_be_different).isFalse();
        //logger.info(String.valueOf(assertThat(should_be_different).isFalse()));
        macchina.setColor(macchina_test.getColor());
        macchina.setTipo(TipiMacchina.SUV);
        //macchina.setId(macchina_test.getId());
        macchina.setName(macchina_test.getName());
        macchina.setColor( macchina_test.getColor() );
        macchina.setTipo( macchina_test.tipoMacchina );
        boolean should_be_same = macchina.equals(macchina_test);
        logger.info("Should be same: " + should_be_same);
        assertThat(should_be_same).isTrue();

    }

    @Test
    public void testHashCode() throws Exception {

        //    persona.hashCode();
        boolean hashcode_should_be_different = (macchina_test.hashCode() == macchina.hashCode());
        assertThat(hashcode_should_be_different).isFalse();
        logger.info("Should be different: " + hashcode_should_be_different);
        //persona.setEmail(persona_test.getEmail());
        //persona.setNome(persona_test.getNome());
        //persona.setCognome(persona_test.getCognome());
        //boolean hashcode_should_be_same = (persona_test.hashCode() == persona.hashCode());
        //assertThat(hashcode_should_be_same).isTrue();

    }

    @Test
    public void deleteFile()
    {
        try
        {
            Files.delete(filepath);
        }
        catch (IOException e)
        {
            logger.info("The file does not exist. I can't delete");
        }
    }

    @Test
    public void createFile(){

        if ( file.exists() )
        {
            deleteFile();
            logger.info("file cancellato");

            try {
                String color = macchina.getColor();
                String color_due = macchina_test.getColor();


                if (!file.exists()) {
                    file.createNewFile();
                    logger.info("file now is created!");
                    FileWriter fw = new FileWriter(file.getAbsoluteFile());
                    BufferedWriter bw = new BufferedWriter(fw);
                    bw.append(color);
                    bw.append(" " + color_due);
                    bw.close();
                    fw.close();
                }
            } catch (Exception e) {
                logger.info("File not created or already exists!");
            }
        }
    }

    @Test
    public void printMacchine()
    {
        do{
            logger.info("auto: ", macchina, macchina_test);
        }while ( macchina!=null && macchina_test!=null );
    }

}
