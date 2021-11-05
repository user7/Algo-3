package geekbrains.lesson8;

import java.io.PrintStream;

public class Main {
    public Main() {
    }

    public static void main(String[] args) {
        HashTable<Object, Object> hashTable = new HashTableChainingImpl<>(10);
        hashTable.put(new Product(1, "Orange"), 150);
        hashTable.put(new Product(77, "Banana"), 100);
        hashTable.put(new Product(67, "Carrot"), 228);
        hashTable.put(new Product(60, "Lemon"), 250);
        hashTable.put(new Product(51, "Milk"), 120);
        hashTable.put(new Product(21, "Potato"), 67);
        System.out.println("Size is " + hashTable.size());
        hashTable.display();
        PrintStream var10000 = System.out;
        Object var10001 = hashTable.get(new Product(21, "Potato"));
        var10000.println("Cost potato is " + var10001);
        var10000 = System.out;
        var10001 = hashTable.get(new Product(77, "Banana"));
        var10000.println("Cost banana is " + var10001);
        var10000 = System.out;
        var10001 = hashTable.get(new Product(67, "Carrot"));
        var10000.println("Cost carrot is " + var10001);
        hashTable.remove(new Product(21, "Potato"));
        hashTable.remove(new Product(77, "Banana"));
        var10000 = System.out;
        var10001 = hashTable.get(new Product(21, "Potato"));
        var10000.println("Cost potato is " + var10001);
        var10000 = System.out;
        var10001 = hashTable.get(new Product(77, "Banana"));
        var10000.println("Cost banana is " + var10001);
        var10000 = System.out;
        var10001 = hashTable.get(new Product(67, "Carrot"));
        var10000.println("Cost carrot is " + var10001);
        hashTable.put(new Product(47, "Pineapple"), 228);
        hashTable.display();
    }
}