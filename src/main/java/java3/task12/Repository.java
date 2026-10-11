package java3.task12;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* Generic-репозиторий: хранит объекты типа T по ключу типа ID.
 * save - upsert: если ключ есть, значение обновляется.
 * findAll - возвращает копию.
 * null не принимается ни в id, ни в объекте. */
public class Repository<ID, T> {

    private final Map<ID, T> storage = new HashMap<>();

    public void save(ID id, T entity) {
        if (id == null) {
            throw new IllegalArgumentException("id is null");
        }
        if (entity == null) {
            throw new IllegalArgumentException("entity is null");
        }
        storage.put(id, entity);
    }

    public T findById(ID id) {
        if (id == null) {
            throw new IllegalArgumentException("id is null");
        }
        return storage.get(id);
    }

    public List<T> findAll() {
        return new ArrayList<>(storage.values());
    }

    public void deleteById(ID id) {
        if (id == null) {
            throw new IllegalArgumentException("id is null");
        }
        storage.remove(id);
    }
}