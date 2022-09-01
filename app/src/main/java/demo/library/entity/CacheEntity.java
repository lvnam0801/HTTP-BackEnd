package demo.library.entity;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import demo.library.funtionalInterface.Loader;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class CacheEntity<K, V> extends BaseEntity{

    private Map<K, V> cache = new ConcurrentHashMap<K, V>();

    // put into Cahe
    public V get(K key){
        return cache.get(key);
    }

    public void put(K key, V value){
        cache.put(key, value);
    }

    public boolean containsKey(K key){
        return cache.containsKey(key);
    }

    public void remove(K key){
        cache.remove(key);
    }
    
    public void loadData (Loader load){
        load.loadData();
    }
}
