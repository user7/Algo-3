package geekbrains.lesson3;

import java.util.Arrays;

public class FindMissing {

    public static void test(int from, int to) {
        int total = 0;
        int good = 0;
        for (int i = from; i <= to; ++i) {
            int[] a = new int[i];
            for (int j = 0; j <= i; ++j) {
                for (int k = 0; k < i; ++k) {
                    a[k] = k >= j ? k + 2 : k + 1;
                }
                if (checkFindMissing(false, j + 1, a))
                    good++;
                total++;
            }
        }
        System.out.println("FindMissing: " + good + "/" + total + " tests passed");
    }

    public static boolean checkFindMissing(boolean verbose, int v, int... a) {
        int missing = findMissing(a);
        if (missing == v) {
            if (verbose)
                System.out.printf("%d\n", missing);
            return true;
        } else {
            System.out.printf("for %s expected %d, got %d\n", Arrays.toString(a), v, missing);
            return false;
        }
    }

    public static int findMissing(int[] a) {
        if (a.length == 0 || a[0] != 1)
            return 1;
        if (a[a.length - 1] == a.length)
            return a.length + 1;
        int beg = 0;
        int end = a.length - 1;
        while (true) {
            if (end - beg <= 1)
                return beg + 2;
            int mid = (beg + end + 1) / 2;
            if (a[mid] != mid + 1) {
                end = mid;
            } else {
                beg = mid;
            }
        }
    }
}
