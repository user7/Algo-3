package geekbrains.lesson8;

public class DoubleHashTable<K, V> extends HashTableImpl<K, V> {
    public DoubleHashTable(int initialCapacity) {
        super(initialCapacity);
    }

    protected int getStep(K key) {
        return this.hashDoubleFunc(key);
    }

    private int hashDoubleFunc(K key) {
        return 5 - key.hashCode() % 5;
    }
}