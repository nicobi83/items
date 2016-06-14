package api.service;

import api.model.Macchine;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

/**
 * Created by NICOLA on 06/06/2016.
 */

@Named
public class MacchineService implements Crud<Macchine.Macchina> {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Inject
    ObjectMapper mapper;

    @Inject
    MacchineService service;


    Macchine macchine = new Macchine();

    Macchine.Macchina macchina = new Macchine.Macchina();
    Macchine.Macchina macchina_test = new Macchine.Macchina();


    String path = "C:\\Users\\NICOLA\\prova.txt";
    Path filepath = Paths.get(path);
    File file = new File(path);


    @Value("${api.items.file}")
    String fileJson;
    Path filePathJson;
    Boolean fileLockJson = false;


    @Override
    public Macchine.Macchina add(Macchine.Macchina item) {

        this.macchine.getMacchine().add(item);
        //Date d = DateTime.now().toDate();
        //item.setModifiedDate(d);
        //item.setCreationDate(d);
        //this.save();
        return item;

    }


    @Override
    public void delete(String id) {

    }

    @Override
    public void delete(Macchine.Macchina item) {

    }


    @Override
    public Macchine.Macchina getById(String id) {
        return null;
    }


    @Override
    public void update(String id, Macchine.Macchina update) {

    }


    @Override
    public void update(Macchine.Macchina previous, Macchine.Macchina update) {

    }


    @Override
    public List<Macchine.Macchina> findItems(Map<String, String> mapItem) {
        return null;
    }


    private synchronized void writeToFileJson(final Object toSave) {
        this.fileLockJson = true;
        String jsonToSave = this.toJson(toSave);
        if (!StringUtils.isBlank(jsonToSave)) {
            //write filePath
            try {
                if (!Files.exists(filePathJson)) {
                    Files.createFile(filePathJson);
                }
            } catch (IOException ex) {
                logger.error(ex.getLocalizedMessage(), ex);
            }
            if (Files.exists(filePathJson)) {
                try (FileOutputStream fos = new FileOutputStream(filePathJson.toFile())) {
                    fos.write(jsonToSave.getBytes("UTF-8"));
                } catch (UnsupportedEncodingException ex) {
                    logger.error(ex.getLocalizedMessage(), ex);
                } catch (IOException ex) {
                    logger.error(ex.getLocalizedMessage(), ex);
                }
            }
        }
        this.fileLockJson = false;
    }


    private String toJson(final Object obj) {
        String serializedObject = null;
        try {
            serializedObject = mapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            logger.debug(e.getLocalizedMessage());
        }
        return serializedObject;
    }


    public void deleteFile() {
        try {
            Files.delete(filepath);
        } catch (IOException e) {
            logger.info("The file does not exist. I can't delete");
        }
    }


    private synchronized void createFileTxt() {
        if (file.exists())
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

}
