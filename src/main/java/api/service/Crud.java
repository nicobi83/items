package api.service;

import java.util.List;
import java.util.Map;

/**
 * Created by Thomas on 06/05/2016.
 */
public interface Crud<T> {

    T add(T item);
    void delete(String id);
    void delete(T item);
    T getById(String id);
    void update(String id, T update);
    void update(T previous, T update);
    List<T> findItems(Map<String, String> mapItem);

}
