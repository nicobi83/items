//package api.service;

/**
 * Created by Luca on 06/05/2016.
 */
/*
@Named
public class ApiService implements Crud<Paziente> {
    @Inject
    Items items;

    @Inject
    ItemsApi service;

    public ItemsApi getService() {
        return service;
    }

    public void setService(ItemsApi service) {
        this.service = service;
    }


    @Override
    public Paziente add(Paziente item) {
        return (Paziente) service.add(item);
    }

    @Override
    public void delete(Paziente item) {
        service.delete(item);
    }

    @Override
    public void delete(String id) {
        service.delete(id);
    }

    @Override
    public void update(String id, Paziente item) {
        service.update(id,item);
    }

    @Override
    public void update(Paziente previus, Paziente update) {
        service.update(previus,update);
    }

    @Override
    public Paziente getById(String id) {
        return (Paziente) service.getItem(id);
    }

    @Override
    public List<Paziente> findItems(Map<String, String> queryParams) {
        List<Items.Item> fromItemsService = service.findItems(queryParams);

        List<Paziente> pazienti = fromItemsService.stream()
          .filter(item -> item instanceof Paziente)
          .map(item -> (Paziente) item)
          .collect(Collectors.toList());
        return pazienti;
    }
}
*/
