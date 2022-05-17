package geekbrains.lesson8;

public interface HashTable<K, V> {
    boolean put(K var1, V var2);

    V get(K var1);

    V remove(K var1);

    int size();

    boolean isEmpty();

    void display();

    public interface Entry<K, V> {
        K getKey();

        V getValue();

        void setValue(V var1);
    }
}
