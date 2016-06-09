package api.service;

import api.model.Items;

import java.util.List;
import java.util.Map;

/**
 * Created by matthew on 16.05.16.
 */
public interface ItemsApi {

    Items items() ;
    Items.Item add(Items.Item item) ;
    void update(String id, Items.Item update) ;
    void update(Items.Item previous, Items.Item update) ;
    void delete(String id) ;
    void delete(Items.Item item) ;
    Items.Item getItem(String id) ;
    List<Items.Item> findItems(Map<String, String> queryParams);

}
