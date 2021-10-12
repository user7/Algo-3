package geekbrains;

import java.util.Arrays;

public class Main {

    public static void main(String[] args) {
        checkFindMissing(11, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 12, 13, 14, 15, 16);
        checkFindMissing(3,  1, 2, 4, 5, 6);
        checkFindMissing(1);
    }

    static void checkFindMissing(int v, int... a) {
        int missing = findMissing(a);
        if (missing == v)
            System.out.printf("%d\n", missing);
        else
            System.out.printf("for %s expected %d, got %d\n", Arrays.toString(a), v, missing);
    }

    static int findMissing(int[] a) {
        if (a.length == 0 || a[0] != 1)
            return 1;
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