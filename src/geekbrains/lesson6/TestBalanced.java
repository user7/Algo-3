package geekbrains.lesson6;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.Random;
import java.util.stream.Stream;

public class TestBalanced {

    enum Algo {
        QUIT_AT_MAX,      // выход по достижению maxHeight
        QUIT_AFTER_MAX,   // выход перед достижением maxHeight + 1
        QUIT_WHEN_FULL    // выход при насыщении
    }

    public static void main(String[] args) {
        testRandomTreesBalanced(20, 4, 25, Algo.QUIT_AT_MAX);
        testRandomTreesBalanced(20, 4, 25, Algo.QUIT_AFTER_MAX);
        testRandomTreesBalanced(20, 4, 25, Algo.QUIT_WHEN_FULL);

        // те же на алгоритмы по 10000 прогонов, для статистики
        // testRandomTreesBalanced(10000, 4, 25, Algo.QUIT_AT_MAX);    //  1.5%
        // testRandomTreesBalanced(10000, 4, 25, Algo.QUIT_AFTER_MAX); //  7.5%
        // testRandomTreesBalanced(10000, 4, 25, Algo.QUIT_WHEN_FULL); // 53.9%

        // задание из методички, по алгоритмам 1 и 2 практически невозможно получить сбалансированное дерево
        testRandomTreesBalanced(10000, 6, 100, Algo.QUIT_AT_MAX);    // 0%
        testRandomTreesBalanced(10000, 6, 100, Algo.QUIT_AFTER_MAX); // 0%
        testRandomTreesBalanced(10000, 6, 100, Algo.QUIT_WHEN_FULL); // 0.7%
    }

    @DisplayName("testTreeBalance")
    @ParameterizedTest
    @MethodSource("testArgs")
    void testTreeBalance(int minHeight, int maxHeight, int[] data) {
        var tree = new TreeImpl<Integer>();
        Arrays.stream(data).forEach(n -> tree.add(n));
        var height = tree.heightMinMax();
        Assertions.assertEquals(height.min, minHeight, "incorrect min height");
        Assertions.assertEquals(height.max, maxHeight, "incorrect max height");
    }

    static Stream<Arguments> testArgs() {
        return Stream.of(
                Arguments.arguments(0, 0, new int[] {}),
                Arguments.arguments(1, 1, new int[] {1}),
                Arguments.arguments(1, 2, new int[] {1, 2}),
                Arguments.arguments(1, 3, new int[] {1, 2, 3}),
                Arguments.arguments(1, 4, new int[] {1, 2, 3, 4}),
                Arguments.arguments(2, 2, new int[] {2, 1, 3}),
                Arguments.arguments(2, 3, new int[] {10, 15, 5, 7, 13}),
                Arguments.arguments(3, 3, new int[] {4, 2, 1, 3, 6, 7, 5})
        );
    }

    public static void testRandomTreesBalanced(int treeCount, int maxHeight, int valueRange, Algo algo) {
        int balanced = countRandomTreesBalanced(treeCount, maxHeight, valueRange, algo);
        System.out.printf("Алгоритм %d, высота %d, диапазон [-%d, %d], сбалансированных: %d / %d\n",
                algo.ordinal() + 1, maxHeight, valueRange, valueRange, balanced, treeCount);
    }

    static Random rnd = new Random();

    public static int countRandomTreesBalanced(int treeCount, int maxHeight, int valueRange, Algo mode) {
        if (maxHeight <= 0) {
            return 1;
        }

        int balancedTrees = 0;
        for (int i = 0; i < treeCount; ++i) {
            var tree = new TreeImpl<Integer>();
            TreeImpl.Height height;
            int consecutiveSkips = 0;
            while (true) {
                int insertedValue = rnd.nextInt(valueRange * 2 + 1) - valueRange;
                tree.add(insertedValue);
                height = tree.heightMinMax();

                // алгоритм 1
                if (mode == Algo.QUIT_AT_MAX && height.max >= maxHeight) {
                    break;
                }

                if (height.max <= maxHeight) {
                    consecutiveSkips = 0;
                } else {
                    tree.remove(insertedValue);
                    height = tree.heightMinMax(); // пересчитаем высоту после remove на случай выхода из цикла

                    // алгоритм 2
                    if (mode == Algo.QUIT_AFTER_MAX) {
                        break;
                    }

                    // алгоритм 3
                    consecutiveSkips++;
                    if (mode == Algo.QUIT_WHEN_FULL && consecutiveSkips >= 10) {
                        break;
                    }
                }
            }

            if (height.max - height.min <= 1) {
                ++balancedTrees;
            }
        }
        return balancedTrees;
    }
}