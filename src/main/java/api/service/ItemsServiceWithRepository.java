package api.service;

import api.dao.ItemsRepository;
import api.exception.IdNotSpecifiedException;
import api.exception.NotFoundException;
import api.model.Items;
import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;

import javax.inject.Inject;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by matthew on 16.05.16.
 */
public class ItemsServiceWithRepository implements ItemsApi {
    Items items = new Items();
    @Inject
    ItemsRepository repository;

    public ItemsServiceWithRepository(){
        System.out.println(repository);
    }

    private void refresh() {
        Set<Items.Item> fromDs = repository.itemDao().listAll();
        items().setItems(fromDs);
    }

    public Items.Item add(Items.Item item) {
        this.items.getItems().add(item);
        Date d = DateTime.now().toDate();
        item.setModifiedDate(d);
        item.setCreationDate(d);
        repository.insert(item);
        refresh();
        return item;
    }

    public void update(String id, Items.Item update) {
        Items.Item previous = this.getItem(id);
        this.update(previous, update);
    }

    public void update(Items.Item previous, Items.Item update) {
        refresh();
        if (this.items.getItems().contains(previous)) {
            if (!previous.equals(update)) {
                //ID's are different, just set them to be equal
                update.setId(previous.getId());
            }
            this.items.getItems().remove(previous);
            this.items.getItems().add(update);
            update.setCreationDate(previous.getCreationDate());
            update.setModifiedDate(DateTime.now().toDate());
            repository.update(update);
            refresh();
        }
    }

    public void delete(String id) {
        Items.Item item = this.getItem(id);
        this.delete(item);
    }

    public void delete(Items.Item item) {
        refresh();
        if (this.items.getItems().contains(item)) {
            this.items.getItems().remove(item);
            repository.itemDao().delete(item);
            refresh();
        }
    }

    public Items items() {
        return this.items;
    }

    public Items.Item getItem(String id) {
        if (!StringUtils.isBlank(id)) {
            Items.Item item = repository.getItem(id);
            if (item != null) {

                return item;
            }
            throw new NotFoundException("Item with 'id' == '" + id + "'");
        }
        throw new IdNotSpecifiedException("Missing parameter 'id'");
    }

    public List<Items.Item> findItems(Map<String, String> queryParams) {
        Set<Items.Item> retItemsSet = repository.find(queryParams);

        List<Items.Item> retItems = retItemsSet.stream()
                .filter(item -> {
                    if (queryParams.containsKey("created_before")) {
                        DateTime dt = DateTime.parse(queryParams.get("created_before"));
                        DateTime compare = new DateTime(item.getCreationDate());
                        return compare.isBefore(dt);
                    }
                    return true;
                })
                .filter(item -> {
                    if (queryParams.containsKey("created_after")) {
                        DateTime dt = DateTime.parse(queryParams.get("created_after"));
                        DateTime compare = new DateTime(item.getCreationDate());
                        return compare.isAfter(dt);
                    }
                    return true;
                })
                .filter(item -> {
                    if (queryParams.containsKey("modified_before")) {
                        DateTime dt = DateTime.parse(queryParams.get("modified_before"));
                        DateTime compare = new DateTime(item.getModifiedDate());
                        return compare.isBefore(dt);
                    }
                    return true;
                })
                .filter(item -> {
                    if (queryParams.containsKey("modified_after")) {
                        DateTime dt = DateTime.parse(queryParams.get("modified_after"));
                        DateTime compare = new DateTime(item.getModifiedDate());
                        return compare.isAfter(dt);
                    }
                    return true;
                })
                .collect(Collectors.toList());
        return retItems;
    }
}
