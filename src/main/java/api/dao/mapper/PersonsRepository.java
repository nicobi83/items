package api.dao.mapper;

import api.dao.*;
import api.dao.PersonaDao;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.skife.jdbi.v2.DBI;
import org.skife.jdbi.v2.sqlobject.CreateSqlObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

/**
 * Created by nicob on 20/05/2016.
 */
public abstract class PersonsRepository {

    Logger logger = LoggerFactory.getLogger(this.getClass());


    @CreateSqlObject
    public abstract PersonaDao personaDao();

    @Inject
    ObjectMapper mapper;
    @Inject
    DBI dbi;

    @PostConstruct
    void init() {
        try {
            this.personaDao().create();
        }
        catch (Exception e){
            logger.info("'Persona' table already exists");
        };
    }

}
