package java3.task14;

import java.util.LinkedHashMap;
import java.util.Map;

/* LRU-кэш фиксированной ёмкости.
 * accessOrder = true - порядок по последнему доступу.
 * get и put перемещают ключ в конец. Первый в порядке - самый старый.
 * При превышении ёмкости вытесняется самый старый.
 * null не принимается ни в ключ, ни в значение. */
public class LruCache<K, V> extends LinkedHashMap<K, V> {

    private final int capacity;

    public LruCache(int capacity) {
        super(capacity, 0.75f, true);
        if (capacity <= 0) {
            throw new IllegalArgumentException("capacity must be > 0");
        }
        this.capacity = capacity;
    }

    @Override
    public V get(Object key) {
        if (key == null) {
            throw new IllegalArgumentException("key is null");
        }
        return super.get(key);
    }

    @Override
    public V put(K key, V value) {
        if (key == null) {
            throw new IllegalArgumentException("key is null");
        }
        if (value == null) {
            throw new IllegalArgumentException("value is null");
        }
        return super.put(key, value);
    }

    @Override
    protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
        return size() > capacity;
    }
}