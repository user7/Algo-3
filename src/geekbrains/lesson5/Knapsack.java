package geekbrains.lesson5;

import java.sql.Array;
import java.util.ArrayList;

public class Knapsack {
    public static record Item(String name, int price, int weight) {
    }

    public static Integer bestCost(ArrayList<Item> items, int maxWeight) {
        ArrayList<Item> result = bestSet(items, maxWeight);
        if (result == null) {
            return null;
        }
        int cost = 0;
        for (var item : result) {
            cost += item.price();
        }
        return cost;
    }

    static ArrayList<Item> bestSet(ArrayList<Item> items, int maxWeight) {
        var state = new State();
        state.currentSet = new boolean[items.size()]; // boolean[] elements default to false
        state.bestSet = new boolean[items.size()]; // for now best is empty set, with zero cost and weight
        state.items = items;
        state.maxWeight = maxWeight;

        findBestSet(state, 0, 0, 0); // сам алгоритм

        if (state.bestWeight > maxWeight) {
            return null;
        }

        var result = new ArrayList<Item>();
        for (int i = 0; i < items.size(); ++i) {
            if (state.bestSet[i]) {
                result.add(items.get(i));
            }
        }
        return result;
    }

    private static class State {
        int bestCost = 0;
        int bestWeight = 0;
        boolean[] bestSet;
        boolean[] currentSet;
        ArrayList<Item> items;
        int maxWeight;
    }

    private static void findBestSet(State state, int i, int cost, int weight) {
        if (i == state.items.size()) {
            if (weight <= state.maxWeight && (state.bestWeight > state.maxWeight || cost > state.bestCost)) {
                System.arraycopy(state.currentSet, 0, state.bestSet, 0, state.currentSet.length);
                state.bestCost = cost;
                state.bestWeight = weight;
            }
            return;
        }

        // пробуем пропустить текущий предмет
        state.currentSet[i] = false;
        findBestSet(state, i + 1, cost, weight);

        // теперь пробуем взять текущий предмет
        state.currentSet[i] = true;
        var item = state.items.get(i);
        findBestSet(state, i + 1, cost + item.price(), weight + item.weight());
    }

    public static void testPrintBestSet() {
        var list = new ArrayList<Item>();
        list.add(new Item("Книга", 600, 1));
        list.add(new Item("Бинокль", 5000, 2));
        list.add(new Item("Аптечка", 1500, 4));
        list.add(new Item("Ноутбук", 40000, 2));
        list.add(new Item("Котелок", 500, 1));
        list = bestSet(list, 5);
        int cost = 0;
        int weight = 0;
        for (var it : list) {
            cost += it.price();
            weight += it.weight();
            System.out.println(it);
        }
        System.out.printf("вес %d / 5, стоимость %d\n", weight, cost);
    }
}