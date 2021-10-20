package geekbrains.lesson5;

public class Power {
    public static double calc(double a, int n) {
        return n < 0 ? calc(1 / a, -n, 1) : calc(a, n, 1);
    }

    private static double calc(double a, int n, double acc) {
        return n == 0 ? acc : calc(a * a, n / 2, n % 2 == 0 ? acc : acc * a);
    }
}
