package api.service;

import api.model.Items;
import api.model.Paziente;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by Thomas on 06/05/2016.
 */
@Named
public class PazienteService implements Crud<Paziente> {

    @Inject
    Items items;

    @Inject
    ItemsService itemsService;

    public ItemsService getItemsService() {
        return itemsService;
    }

    public void setItemsService(ItemsService itemsService) {
        this.itemsService = itemsService;
    }

    @Override
    public Paziente add(Paziente item) {
        return (Paziente) itemsService.add(item);
    }

    @Override
    public void delete(String id) {
        itemsService.delete(id);
    }

    @Override
    public void delete(Paziente name) {
        itemsService.delete(name);
    }

    @Override
    public Paziente getById(String id) {
        return (Paziente) itemsService.getById(id);
    }

    @Override
    public void update(String id, Paziente update) {
        itemsService.update(id, update);
    }

    @Override
    public void update(Paziente previous, Paziente update) {
        itemsService.update(previous, update);
    }

    @Override
    public List<Paziente> findItems(Map<String, String> mapItem) {
        List<Items.Item> fromItemsService = itemsService.findItems(mapItem);

        List<Paziente> pazienti = fromItemsService.stream()
          .filter(item -> item instanceof Paziente)
          .map(item -> (Paziente) item)
          .collect(Collectors.toList());
        return pazienti;
    }
}

