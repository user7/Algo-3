package geekbrains.lesson8;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.ListIterator;

public class HashTableChainingImpl<K, V> implements HashTable<K, V> {
    private static class Item<K, V> {
        Item(K key, V value) {
            this.key = key;
            this.value = value;
        }

        K key;
        V value;
    }

    private static class ItemList<K, V> extends LinkedList<Item<K, V>> {
    }

    private ItemList<K, V>[] data;
    private int size;

    HashTableChainingImpl(int capacity) {
        if (capacity < 1) {
            throw new IllegalArgumentException("capacity must be positive");
        }
        data = new ItemList[capacity];
        size = 0;
    }

    HashTableChainingImpl() {
        this(16);
    }

    @Override
    public boolean put(K key, V value) {
        if (size == data.length) {
            return false;
        }
        var h = hash(key);
        if (data[h] == null)
            data[h] = new ItemList<K, V>();
        for (var item : data[h]) {
            if (item.key.equals(key)) {
                item.value = value;
                return true;
            }
        }
        data[h].addFirst(new Item<K, V>(key, value));
        ++size;
        return true;
    }

    @Override
    public V get(K key) {
        var iter = find(key);
        return iter == null ? null : iter.next().value;
    }

    @Override
    public V remove(K key) {
        var iter = find(key);
        if (iter == null)
            return null;
        var value = iter.next().value;
        iter.remove();
        size--;
        return value;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public void display() {
        System.out.println("--------------------------");
        for (int i = 0; i < data.length; ++i) {
            if (data[i] == null)
                continue;
            System.out.printf("%d:", i);
            for (var item : data[i]) {
                System.out.printf(" - (%s, %s)", item.key, item.value);
            }
            System.out.println();
        }
        System.out.println("--------------------------");
    }

    private int hash(K key) {
        return Math.abs(key.hashCode()) % this.data.length;
    }

    private ListIterator<Item<K, V>> find(K key) {
        var bin = data[hash(key)];
        if (bin == null) {
            return null;
        }
        var iter = bin.listIterator();
        while (iter.hasNext()) {
            if (iter.next().key.equals(key))
                iter.remove();
        }
        return null;
    }
}
