package lab9;

import java.util.*;

/** A HashMap to store pairs of keys and values.
 *  Any key must appear at most once in the dictionary, but values may appear multiple
 *  times. The value associated to a key is the value in the last call to put with that key. */
public class MyHashMap<K, V> implements Map61B<K, V> {

    private int size;              // number of items in the map
    private int m;                 // size of the hashMap
    private double loadFactor;
    private Entry<K, V>[] buckets; // array of linkedList of entries
    private Set<K> keys;

    private class Entry<K, V> implements Map.Entry<K, V> {
        K key;
        V value;
        Entry next;
        public Entry (K key, V value, Entry next) {
            this.key = key;
            this.value = value;
            this.next = next;
        }

        @Override
        public K getKey() {
            return this.key;
        }

        @Override
        public V getValue() {
            return this.value;
        }

        @Override
        public V setValue(V value) {
            V old = this.value;
            this.value = value;
            return old;
        }
    }
    public MyHashMap() {
        this(17);
    }

    public MyHashMap(int initialSize) {
        this(initialSize, 0.75);
    }

    public MyHashMap(int initialSize, double loadFactor) {
        if (initialSize < 1 || loadFactor <= 0.0) {
            throw new IllegalArgumentException("initialSize and loadFactor must be larger than 0.");
        }
        this.m = initialSize;
        buckets = (Entry<K, V>[]) new Entry[this.m];
        this.loadFactor = loadFactor;
        keys = new HashSet<>();
    }

    private void resize(int x) {
        MyHashMap<K, V> newMap = new MyHashMap<>(prime(x), this.loadFactor);

        for (int i = 0; i < m; i++) {
            for (Entry e = buckets[i]; e != null; e = e.next) {
                newMap.put((K) e.key, (V) e.value);
            }
        }
        this.m = newMap.m;
        this.size = newMap.size;
        this.buckets = newMap.buckets;
        this.keys = newMap.keys;
    }

    /** Return true if the int is prime, otherwise false. */
    private boolean isPrime(int x) {
        if (x == 2) {
            return true;
        }
        if (x % 2 == 0) {
            return false;
        }
        for (int i = 3; i * i <= x; i = i + 2) {
            if (x % i == 0) {
                return false;
            }
        }
        return true;
    }

    /** Return the smallest prime that larger than x. */
    private int prime(int x) {
        while (!isPrime(x)) {
            ++x;
        }
        return x;
    }

    @Override
    public void clear() {
        this.size = 0;
        this.buckets = (Entry<K, V>[]) new Entry[this.m];
        this.keys.clear();
    }

    /** hash function to hash key's hashCode into the range of 0 to m - 1.
     * Use hashCode() & 0x7fffffff to convert hashCode to positive.
     */
    private int hash(K key) {
        return (key.hashCode() & 0x7fffffff) % m;
    }

    private void validateKey(K key) {
        if (key == null) {
            throw new IllegalArgumentException("Key cannot be null.");
        }
    }
    @Override
    public boolean containsKey(K key) {
        return keys.contains(key);
    }

    @Override
    public V get(K key) {
        validateKey(key);
        int index = hash(key);
        Entry res = find(key, buckets[index]);
        if (res == null) {
            return null;
        }
        return (V) res.value;
    }

    /** Return the entry with the key from the hash bucket. */
    private Entry<K, V> find(K key, Entry<K, V> bucket) {
        for (Entry i = bucket; i != null; i = i.next) {
            if (key == null && i.key == null || key.equals(i.key)) {
                return i;
            }
        }
        return null;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public void put(K key, V value) {
        validateKey(key);
        if (value == null) {
            throw new IllegalArgumentException("Value argument is null.");
        }

        int index = hash(key);
        Entry res = find(key, buckets[index]);
        if (res == null) {
            buckets[index] = new Entry(key, value, buckets[index]);
            keys.add(key);
            size++;
            if (size / m > loadFactor) {
                resize(m * 2);
            }
            return;
        }
        res.setValue(value);
    }

    @Override
    public Set<K> keySet() {
        return keys;
    }

    @Override
    public V remove(K key) {
        validateKey(key);
        int index = hash(key);
        V val = get(key);
        buckets[index] = delete(buckets[index], key);
        return val;
    }

    private Entry<K, V> delete(Entry bucket, K key) {
        if (bucket == null) {
            return null;
        }

        if (key.equals(bucket.key)) {
            size--;
            keys.remove(key);
            return bucket.next;
        }

        bucket.next = delete(bucket.next, key);
        return bucket;
    }

    @Override
    public V remove(K key, V value) {
        validateKey(key);
        int index = hash(key);
        V val = get(key);
        if (val == null || !val.equals(value)) {
            return null;
        }
        buckets[index] = delete(buckets[index], key);
        return value;
    }

    @Override
    public Iterator<K> iterator() {
        return keys.iterator();
    }
}
