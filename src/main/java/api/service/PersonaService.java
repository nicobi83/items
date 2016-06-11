package api.service;

import api.dao.PersonaRepository;
import api.exception.IdNotSpecifiedException;
import api.exception.NotFoundException;
import api.model.Persona;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Named;
import java.rmi.NotBoundException;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by Giovanni on 19/05/2016.
 */
@Named
public class PersonaService implements Crud<Persona> {

    Logger logger = LoggerFactory.getLogger(this.getClass());
    Set<Persona> persone = new HashSet<>();

    @Inject
    ObjectMapper mapper;

    @Inject
    PersonaRepository repository;

    private void refresh() {
        persone = repository.personaDao().listAll();
    }

    @Override
    public Persona add(Persona persona) {
        try {
            persona = repository.insertUpdate(persona);
            refresh();
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("ERRORE NELL'INSERIMENTO");
        }
        return persona;
    }


    public void delete(Integer id) {
        Persona persona = this.getById(Integer.toString(id));
        this.delete(persona);
    }

    @Override
    public void delete(String email) {
        Persona persona = this.getByEmail(email);
        this.delete(persona);
    }

    @Override
    public void delete(Persona item) {
        refresh();
        if (this.persone.contains(item)) {
            this.persone.remove(item);
            repository.personaDao().delete(item);
            refresh();
        }
    }

    public Persona getById(Integer id) {
        Persona persona = repository.getPersona(id);
        if (persona != null) {
            return persona;
        }
        throw new NotFoundException("Persona with 'id' == '" + id + "'");
    }

    @Override
    public Persona getById(String id) {
        if (!StringUtils.isBlank(id)) {
            return this.getById(Integer.parseInt(id));
        }
        throw new IdNotSpecifiedException("Missing parameter 'id'");
    }

    public void update(Integer id, Persona update) {
        Persona previous = this.getById(Integer.toString(id));
        this.update(previous, update);
    }

    @Override
    public void update(String email, Persona update) {
        Persona previous = this.getByEmail(email);
        this.update(previous, update);
    }

    @Override
    public void update(Persona previous, Persona update) {
        refresh();
        if (persone.contains(previous)) {
            if (!previous.equals(update)) {
                //ID's are different, just set them to be equal
                update.setId(previous.getId());
            }
            this.persone.remove(previous);
            this.persone.add(update);
            try {
                repository.insertUpdate(update);
            } catch (NotBoundException e) {
                logger.info("ERROR!");
            }
            refresh();
        }
    }

    @Override
    public List<Persona> findItems(Map<String, String> mapItem) {
        return repository.find(mapItem).stream().collect(Collectors.toList());
    }

    public List<Persona> findByAccount(String tipoAccount) {
        return repository.findByAccount(tipoAccount).stream().collect(Collectors.toList());
    }

    public Persona getByEmail(String email) {
        return repository.findByEmail(email);
    }

    public void deleteByEmail( String email )
    {
        delete( getByEmail(email) );
    }

}
