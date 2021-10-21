package geekbrains.util;

public class ArrayUtils {
    public static <E, A extends Iterable<E>> String toString(A a) {
        StringBuilder sb = new StringBuilder("[");
        boolean first = true;
        for (E e : a) {
            if (!first) {
                sb.append(", ");
            }
            first = false;
            sb.append(e.toString());
        }
        sb.append("]");
        return sb.toString();
    }
}
