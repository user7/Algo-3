package geekbrains.lesson5.test;

import geekbrains.lesson5.Power;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

public class PowerTest {
    @DisplayName("Возведение в степень")
    @ParameterizedTest
    @MethodSource("powerData")
    public void power(double a, int n, double expected) {
        Assertions.assertTrue(Math.abs(expected - Power.calc(a, n)) < 0.00001);
    }

    public static Stream<Arguments> powerData() {
        return Stream.of(
                Arguments.arguments(123., 0, 1.),
                Arguments.arguments(11., 2, 121.),
                Arguments.arguments(10., -1, 0.1),
                Arguments.arguments(10., -3, 0.001),
                Arguments.arguments(7., 3, 343)
        );
    }
}
