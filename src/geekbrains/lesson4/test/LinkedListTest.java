package geekbrains.lesson4.test;

import geekbrains.lesson4.*;

import java.util.function.Supplier;

public class LinkedListImplTest {
    private static Supplier<LinkedList<Integer>> supplier = null;
    private static String testSet;

    public static void run(String testSet, Supplier<LinkedList<Integer>> supplier) {
        LinkedListImplTest.supplier = supplier;
        LinkedListImplTest.testSet = testSet;
        testInsertRemove();
        testContains();
        testForeach();
    }

    static void testInsertRemove() {
        LinkedList<Integer> list = supplier.get();

        insertAll(list, 1, 2, 3, 4, 5);
        checkList("insert front 1..5", list, "[5, 4, 3, 2, 1]");

        list.remove(3);
        checkList("5..1 remove 3", list, "[5, 4, 2, 1]");

        list.remove(7);
        checkList("5..1 remove 3, 7", list, "[5, 4, 2, 1]");

        list.remove(5);
        list.remove(1);
        checkList("5..1 remove 3, 7, 5, 1", list, "[4, 2]");

        list.insertFirst(9);
        checkList("5..1 remove 3, 7, 5, 1 insert 9", list, "[9, 4, 2]");
    }

    static void testContains() {
        LinkedList<Integer> list = supplier.get();
        insertAll(list, 5, 4, 3, 2, 1);
        for (int i = 0; i <= 6; ++i) {
            testContainsOne(list, i, i >= 1 && i <= 5);
        }
    }

    static void testForeach() {
        LinkedList<Integer> list = supplier.get();
        insertAll(list, 1, 2, 3, 4, 5);
        Integer v = 0;
        LinkedList<Integer> list2 = supplier.get();
        for (Integer e : list) {
            list2.insertFirst(e);
        }
        checkList("for(e:" + list + ") list2.insertFirst(e)", list2, "[1, 2, 3, 4, 5]");
    }

    static <E> void testContainsOne(LinkedList<E> list, E i, boolean expected) {
        boolean got = list.contains(i);
        String name = String.format("%s contains %d", list.toString(), i);
        if (got != expected) {
            System.out.printf("%s failed %s: expected %b got %b\n", testSet, name, expected, got);
        } else {
            System.out.printf("%s passed %s = %b\n", testSet, name, expected);
        }
    }

    static <E> void insertAll(LinkedList<E> list, E... values) {
        for (int i = 0; i < values.length; ++i)
            list.insertFirst(values[i]);
    }

    static <E> void checkList(String test, LinkedList<E> list, String expected) {
        String got = list.toString();
        if (!got.equals(expected)) {
            System.out.printf("%s failed %s: expected %s, got %s\n", testSet, test, expected, got);
        } else {
            System.out.printf("%s passed %s = %s\n", testSet, test, expected);
        }
    }
}
