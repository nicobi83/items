package api.service;

import api.model.Items;
import api.model.Macchina;
import api.model.TipiMacchina;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by NICOLA on 06/06/2016.
 */
public class MacchineServiceIT {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Inject
    MacchineService service;
    @Inject
    Macchina macchine;

    /*@Before
    public void setUp() throws Exception {
        for (int i = 1; i <= 4; i++) {
            Macchina macchina = new Macchina();
            macchina.setId("1");
            macchina.setName(String.format("macchina-%s", i));
            macchina.setTarga("BB42" + i + "SL");
            macchina.setColor("white");
            macchina.setTipo(TipiMacchina.BERLINA);
            service.add(macchina);
            logger.debug("Added car: " + macchina.toString());
        }
    }*/

    @Test
    public void add() throws Exception {
        Macchina macchina = new Macchina();
        //macchina.setId("1");
        macchina.setTarga("BB424SL");
        macchina.setName("Seat");
        assertThat(macchina).isNotIn(macchine);
        service.add(macchina);
        assertThat(macchina).isIn(macchine);
    }

    @Test
    public void createPdfFile() throws IOException {

        // Il file viene creato dentro la cartella "items.api"
        String filename = "prova.pdf";
        String message = "Ho creato un pdf";

        PDDocument doc = new PDDocument();
        try {

            PDPage page = new PDPage();
            doc.addPage(page);

            PDFont font = PDType1Font.COURIER_BOLD;
            PDPageContentStream contents = new PDPageContentStream(doc, page);
            contents.beginText();
            contents.setFont(font, 20);
            contents.newLineAtOffset(100, 700);
            contents.showText(message);
            contents.endText();
            contents.close();
            doc.save(filename);

        } catch (IOException e) {
            logger.info("File not created or already exists!");
        } finally {
            if (doc != null) {
                doc.close();
            }

        }


    }


}
