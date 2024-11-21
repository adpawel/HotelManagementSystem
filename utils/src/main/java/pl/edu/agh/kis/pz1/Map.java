package pl.edu.agh.kis.pz1;

import java.util.List;

/**
 * An interface representing a Map collection with key-value pairs.
 * <p>
 * This interface defines the basic operations for a Map-like data structure,
 * including adding, removing, retrieving values by key, and checking for the existence of keys.
 * </p>
 *
 * @param <K> the type of keys in this map.
 * @param <V> the type of values in this map.
 */
public interface Map<K, V> {
    /**
     * Adds an element to the map under the specified key.
     * If the provided key already exists, this method should replace the existing value.
     *
     * @param key the key (not null).
     * @param value the value associated with the key (not null).
     * @return true if the element was successfully added, false otherwise.
     */
    boolean put(K key, V value);

    /**
     * Removes the entry with the specified key and its associated value from the map.
     *
     * @param key the key to be removed.
     * @return true if the key was successfully removed, false otherwise.
     */
    boolean remove(K key);

    /**
     * Returns the value associated with the specified key, or null if the key does not exist.
     *
     * @param key the key (not null).
     * @return the value associated with the key, or null if the key does not exist.
     */
    V get(K key);

    /**
     * Returns a list of all keys in the map.
     *
     * @return a java.util.List containing all the keys in the map.
     */
    List<K> keys();

    /**
     * Checks if the specified key exists in the map.
     *
     * @param key the key to check.
     * @return true if the key exists in the map, false otherwise.
     */
    boolean contains(K key);
}
