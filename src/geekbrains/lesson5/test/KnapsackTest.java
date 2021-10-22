package geekbrains.lesson5.test;

import geekbrains.lesson5.Knapsack;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.stream.Stream;

public class KnapsackTest {

    @DisplayName("Задача о рюкзаке")
    @ParameterizedTest
    @MethodSource("solutionsData")
    public void solutions(String itemString, int maxWeight, int expected) {
        // itemString format: price1/weight1 price2/weight2
        var items = new ArrayList<Knapsack.Item>();
        if (!itemString.isEmpty()) {
            for (var pair : itemString.split(" ")) {
                var pw = pair.split("/");
                items.add(new Knapsack.Item("", Integer.parseInt(pw[0]), Integer.parseInt(pw[1])));
            }
        }
        int got = Knapsack.bestCost(items, maxWeight);
        Assertions.assertTrue(expected == got,
                String.format("items %s, expected result %d, got %d", itemString, expected, got));
    }

    public static Stream<Arguments> solutionsData() {
        return Stream.of(
                Arguments.arguments("", 5, 0),
                Arguments.arguments("1/9 2/9 3/5 4/9", 5, 3),
                Arguments.arguments("5/1 8/6 8/6 5/2 5/3", 6, 15),
                Arguments.arguments("5/1 8/6 17/6 5/2 5/3", 6, 17)
                );
    }
}
