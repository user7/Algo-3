package geekbrains.lesson4.test;

import geekbrains.lesson4.LinkedList;
import geekbrains.lesson4.LinkedListImpl;
import geekbrains.lesson4.TwoSideLinkedList;
import geekbrains.lesson4.TwoSideLinkedListImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class ListTests {
    static boolean testOk = true;

    @DisplayName("Вставки, удаления, поиск, размеры")
    @ParameterizedTest
    @MethodSource("testEditsData")
    public void testEdits(String whichList, String commands) {
        LinkedList<Integer> list = whichList.equals("SL") ? new LinkedListImpl<Integer>()
                : new TwoSideLinkedListImpl<Integer>();
        if (!commands.equals("")) {
            testEditsAux(list, commands);
        }
    }

    static Stream<Arguments> testEditsData() {
        // тесты интерфейса LinkedList прогоняются для LinkedListImpl и TwoSideLinkedListImpl
        var testBoth = List.of(
                "e 1; s 0; ?= []", // isEmpty, size, toString для пустого листа
                "+f 1; ?= [1]; +f 2; ?= [2, 1]; +f 3; ?= [3, 2, 1]", // insertFirst
                "e 1; +f 5; e 0", // isEmpty
                "e 1; +f 5; e 0; -f 5; e 1; -f", // removeFirst
                "r 5; := [1, 2, 3]; r 5; r 2 1; ?= [1, 3]; r 3 1; ?= [1]; r 1 1; ?= []", // remove
                "c 9", // contains (пустой)
                ":= [6]; c 5; c 7; c 6 1", // contains (1 элемент)
                ":= [6, 8]; c 1; c 9; c 6 1; c 8 1", // contains (2 элемента)
                ":= [6, 2, 8]; c 1; c 9; c 6 1; c 2 1; c 8 1", // contains (3 элемента)
                ":= [2, 1, 1]; c 1 1; r 1 1; ?= [2, 1]; c 1 1; r 1 1; ?= [2]; r 1; c 1; c 2 1", // contains multiple
                ":= [1, 2]; s 2; := [5, 4, 3, 2, 1]; s 5; r 4 1; s 4; r 4; s 4", // size
                "?f; := [7]; ?f 7; := [9, 7]; ?f 9; r 9 1; ?f 7", // getFirst
                ":= [7, 8, 99]; ?.. 7899" // forEach
        );
        var sl1 = testBoth.stream().map((test) -> Arguments.arguments("SL", test));
        var sl2 = testBoth.stream().map((test) -> Arguments.arguments("DL", test));

        // тесты интерфейса TwoSideLinkedList прогоняются только для TwoSideLinkedListImpl
        var testSecond = List.of(
                "+l 1; ?= [1]; +l 2; ?= [1, 2]; +l 3; ?= [1, 2, 3]", // insertLast
                ":= [1, 2, 3]; ?l 3; r 3 1; ?l 2; r 2 1; ?l 1; r 1 1; ?l", // getLast
                ":= [7, 8]; -l 8; ?= [7]; -l 7; ?= []; -l" // removeLast
        );
        var dl = testSecond.stream().map((test) -> Arguments.arguments("DL", test));

        return Stream.concat(Stream.concat(sl1, sl2), dl);
    }

    public void testEditsAux(LinkedList<Integer> list, String editingCommands) {
        String[] commands = editingCommands.split("; ");
        StringBuilder executed = new StringBuilder("");
        var libraryList = new java.util.LinkedList<Integer>();
        for (var cmd : commands) {
            executed.append(cmd);
            String[] args = cmd.split(" ");
            var error = execCommand(list, args, cmd.replaceFirst("^[^ ]* *", ""));
            if (error != null) {
                Assertions.fail(
                        String.format("when executing %s: %s", executed.toString(), error));
            }
            executed.append("; ");
        }
        Assertions.assertTrue(true);
    }

    static String safeCall(String name, Supplier<String> func) {
        try {
            return func.get();
        } catch (Exception e) {
            return String.format("error calling %s: %s", name, e.toString());
        }
    }

    static String safeCallV(String name, Runnable func) {
        return safeCall(name, () -> {
            func.run();
            return null;
        });
    }

    static String safeCallB(String name, Supplier<Boolean> r, Integer exp) {
        return safeCall(name, () -> unexpected(name, r.get(), exp != null && exp != 0));
    }

    static String safeCallI(String name, Supplier<Integer> r, Integer exp) {
        return safeCall(name, () -> unexpected(name, r.get(), exp));
    }

    static String safeCallS(String name, Supplier<String> r, String exp) {
        return safeCall(name, () -> unexpected(name, r.get(), exp));
    }

    static <E> String unexpected(String name, E got, E expected) {
        return java.util.Objects.equals(got, expected) ? null
                : String.format("%s returned %s, expected %s", name, got, expected);
    }

    static Integer safeArg(String[] args, int i) {
        try {
            return Integer.parseInt(args[i]);
        } catch (Exception e) {
            return null;
        }
    }

    String execCommand(LinkedList<Integer> list, String[] args, String allArgs) {
        final var a1 = safeArg(args, 1);
        final var a2 = safeArg(args, 2);
        final var d_list =
                list instanceof TwoSideLinkedList<Integer> ? (TwoSideLinkedList<Integer>) list : null;
        switch (args[0]) {
            case "+f":
                return safeCallV("insertFirst", () -> list.insertFirst(a1));
            case "-f":
                return safeCallI("removeFirst", () -> list.removeFirst(), a1);
            case "r":
                return safeCallB("remove", () -> list.remove(a1), a2);
            case "c":
                return safeCallB("contains", () -> list.contains(a1), a2);
            case "s":
                return safeCallI("size", () -> list.size(), a1);
            case "e":
                return safeCallB("isEmpty", () -> list.isEmpty(), a1);
            case "?=":
                return safeCallS("toString", () -> list.toString(), allArgs);
            case "?f":
                return safeCallI("getFirst", () -> list.getFirst(), a1);
            case "?l":
                return safeCallI("getLast", () -> d_list.getLast(), a1);
            case "+l":
                return safeCallV("insertLast", () -> d_list.insertLast(a1));
            case "-l":
                return safeCallI("insertLast", () -> d_list.removeLast(), a1);
            case "?..": // foreach
                var data = new StringBuilder("");
                return safeCallS("forEach", () -> {
                    list.forEach(val -> data.append(val));
                    return data.toString();
                }, allArgs);
            case ":=":
                // почистить список и присвоить значение
                while (list.removeFirst() != null) ;
                var numArray = allArgs.replaceAll("[^0-9,]", "").split(",");
                for (int i = numArray.length - 1; i >= 0; --i) {
                    final var num = safeArg(numArray, i);
                    var ret = safeCallV("insertFirst", () -> list.insertFirst(num));
                    if (ret != null)
                        return ret;
                }
                return null;
            default:
                return "unknown command " + args[0];
        }
    }
}