package geekbrains.lesson8;

public class HashTableImpl<K, V> implements HashTable<K, V> {
    private final HashTableImpl.Item<K, V>[] data;
    private final HashTableImpl.Item<K, V> emptyEntry;
    private int size;

    public HashTableImpl(int initialCapacity) {
        this.data = new HashTableImpl.Item[initialCapacity * 2];
        this.emptyEntry = new HashTableImpl.Item((Object) null, (Object) null);
    }

    public HashTableImpl() {
        this(16);
    }

    public boolean put(K key, V value) {
        if (this.size() == this.data.length) {
            return false;
        } else {
            int index;
            for (index = this.hashFunc(key); this.data[index] != null; index %= this.data.length) {
                if (this.isKeysEquals(this.data[index], key)) {
                    this.data[index].setValue(value);
                    return true;
                }

                index += this.getStep(key);
            }

            this.data[index] = new HashTableImpl.Item(key, value);
            ++this.size;
            return true;
        }
    }

    private boolean isKeysEquals(HashTableImpl.Item<K, V> item, K key) {
        if (item == this.emptyEntry) {
            return false;
        } else {
            return item.getKey() == null ? key == null : item.getKey().equals(key);
        }
    }

    protected int getStep(K key) {
        return 1;
    }

    private int hashFunc(K key) {
        return Math.abs(key.hashCode()) % this.data.length;
    }

    public V get(K key) {
        int index = this.indexOf(key);
        return index == -1 ? null : this.data[index].getValue();
    }

    private int indexOf(K key) {
        int index = this.hashFunc(key);

        for (int count = 0; count < this.data.length; index %= this.data.length) {
            HashTableImpl.Item<K, V> item = this.data[index];
            if (item == null) {
                break;
            }

            if (this.isKeysEquals(this.data[index], key)) {
                return index;
            }

            ++count;
            index += this.getStep(key);
        }

        return -1;
    }

    public V remove(K key) {
        int index = this.indexOf(key);
        if (index == -1) {
            return null;
        } else {
            HashTableImpl.Item<K, V> temp = this.data[index];
            this.data[index] = this.emptyEntry;
            return temp.getValue();
        }
    }

    public int size() {
        return this.size;
    }

    public boolean isEmpty() {
        return this.size != 0;
    }

    public void display() {
        System.out.println("--------------------------");

        for (int i = 0; i < this.data.length; ++i) {
            System.out.printf("%d = [%s]%n", i, this.data[i]);
        }

        System.out.println("--------------------------");
    }

    static class Item<K, V> implements Entry<K, V> {
        private final K key;
        private V value;

        public Item(K key, V value) {
            this.key = key;
            this.value = value;
        }

        public K getKey() {
            return this.key;
        }

        public V getValue() {
            return this.value;
        }

        public void setValue(V value) {
            this.value = value;
        }

        public String toString() {
            return "Item{key=" + this.key + ", value=" + this.value + "}";
        }
    }
}
