package pl.edu.agh.kis.pz1;

import java.util.ArrayList;
import java.util.List;

/**
 * A custom implementation of the Map interface using two lists: one for keys and one for values.
 * <p>
 * This class provides a simple key-value data structure where the keys and values are stored in separate lists.
 * It supports operations like adding, removing, retrieving values by key, and checking for the existence of keys.
 * </p>
 *
 * @param <K> the type of keys in this map.
 * @param <V> the type of values in this map.
 */
public class MyMap<K,V> implements Map<K,V>{
    List<K> keys = new ArrayList<>();
    List<V> values = new ArrayList<>();

    /**
     * Adds an element to the map under the specified key.
     * If the provided key already exists, this method will replace the existing value.
     *
     * @param key the key (not null).
     * @param value the value associated with the key (not null).
     * @return true if the element was successfully added or updated, false otherwise.
     */
    @Override
    public boolean put(K key, V value){
        try {
            int i = 0;
            for (K k : keys) {
                if (k.equals(key)) {
                    values.set(i, value);
                    return true;
                }
                ++i;
            }
            keys.add(key);
            values.add(value);
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }

    /**
     * Removes the entry with the specified key and its associated value from the map.
     *
     * @param key the key to be removed.
     * @return true if the key was successfully removed, false otherwise.
     */
    @Override
    public boolean remove(K key){
        if (keys.contains(key)) {
            int i = 0;
            for (K k : keys) {
                if (k.equals(key)) {
                    keys.remove(i);
                    values.remove(i);
                    return true;
                }
                ++i;
            }
        }
        return false;
    }

    /**
     * Returns the value associated with the specified key, or null if the key does not exist.
     *
     * @param key the key (not null).
     * @return the value associated with the key, or null if the key does not exist.
     */
    @Override
    public V get(K key){
        int i = 0;
        for (K k : keys) {
            if (k.equals(key)) {
                return values.get(i);
            }
            ++i;
        }
        return null;
    }

    /**
     * Returns a list of all the keys in the map.
     *
     * @return a List containing all the keys in the map.
     */
    @Override
    public List<K> keys(){
        return keys;
    }

    /**1
     * Checks if the specified key exists in the map.
     *
     * @param key the key to check.
     * @return true if the key exists in the map, false otherwise.
     */
    @Override
    public boolean contains(K key){
        return keys.contains(key);
    }
}