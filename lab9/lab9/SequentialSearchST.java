package lab9;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/** A linked-list symbol table to sequential search every unordered entry (key, value) in the list. */
public class SequentialSearchST<K, V> {
    private int size;          // number of entries in the list
    private Entry first;       // the first entry in the list

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

    /** Initializes an empty symbol table. */
    public SequentialSearchST() {
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public boolean contains(K k) {
        return get(k) != null;
    }

    public V get(K k) {
        validateKey(k);
        for (Entry i = first; i != null; i = i.next) {
            if (k.equals(i.key)) {
                return (V) i.value;
            }
        }
        return null;
    }

    public void put(K k, V val) {
        validateKey(k);
        if (val == null) {
            delete(k);
            return;
        }

        for (Entry i = first; i != null; i = i.next) {
            if (k.equals(i.key)) {
                i.value = val;
                return;
            }
        }

        first = new Entry(k, val, first);
        size++;
    }

    private void validateKey(K k) {
        if (k == null) {
            throw new IllegalArgumentException("key cannot be null.");
        }
    }

    public V delete(K k) {
        validateKey(k);
        V res = get(k);
        first = delete(first, k);
        return res;
    }

    private Entry delete(Entry x, K k) {
        if (x == null) {
            return null;
        }

        if (k.equals(x.key)) {
            size--;
            return x.next;
        }

        x.next = delete(x.next, k);
        return x;
    }

    public Iterable<K> keys() {
        List<K> keyList = new ArrayList<>();
        for (Entry i = first; i != null; i = i.next) {
            keyList.add((K) i.key);
        }
        return keyList;
    }

    public Iterable<Entry<K, V>> entries() {
        List<Entry<K, V>> entryList = new ArrayList<>();
        for (Entry i = first; i != null; i = i.next) {
            entryList.add(i);
        }
        return entryList;
    }

}
