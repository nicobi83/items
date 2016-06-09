package api.service;

import api.dao.IndirizzoRepository;
import api.dao.ItemsRepository;
import api.exception.IdNotSpecifiedException;
import api.exception.NotFoundException;
import api.model.Indirizzo;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by Thomas on 18/05/2016.
 */
public class IndirizzoService implements Crud<Indirizzo> {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    Set<Indirizzo> indirizzi = new HashSet<>();

    @Inject
    ObjectMapper mapper;

    @Inject
    IndirizzoRepository repository;

    private void refresh() {
        indirizzi = repository.indirizzoDao().listAll();
    }

    @Override
    public Indirizzo add(Indirizzo indirizzo) {
        try{
            repository.insert(indirizzo);
            refresh();
            return indirizzo;
        }catch (Exception e){
           logger.info("ERROR!?!?!!??!?");
            return null;
        }

    }

    @Override
    public Indirizzo getById(String id) {
        if (!StringUtils.isBlank(id)) {
            Indirizzo indirizzo = repository.getIndirizzo(id);
            if (indirizzo != null) {
                return indirizzo;
            }
            throw new NotFoundException("Indirizzo with 'id' == '" + id + "'");
        }
        throw new IdNotSpecifiedException("Missing parameter 'id'");
    }

    @Override
    public void update(String id, Indirizzo update) {
        Indirizzo previous = this.getById(id);
        this.update(previous, update);
    }

    @Override
    public void update(Indirizzo previous, Indirizzo update) {
        refresh();
        if (indirizzi.contains(previous)) {
            if (!previous.equals(update)) {
                //ID's are different, just set them to be equal
                update.setId(previous.getId());
            }
            this.indirizzi.remove(previous);
            this.indirizzi.add(update);
            repository.update(update);
            refresh();
        }
    }

    @Override
    public void delete(String id) {
        Indirizzo indirizzo = this.getById(id);
        this.delete(indirizzo);
    }

    @Override
    public void delete(Indirizzo item) {
        refresh();
        if (this.indirizzi.contains(item)) {
            this.indirizzi.remove(item);
            repository.indirizzoDao().delete(item);
            refresh();
        }
    }

    @Override
    public List<Indirizzo> findItems(Map<String, String> mapItem) {
       return repository.findIndirizzo(mapItem).stream().collect(Collectors.toList());
    }
}
