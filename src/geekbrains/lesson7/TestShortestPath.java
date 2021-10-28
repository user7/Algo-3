package geekbrains.lesson7;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.*;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class TestShortestPath {

    @DisplayName("кратчайший путь")
    @ParameterizedTest
    @MethodSource("shortestPathData")
    void testShortestPath(String graphString, String start, String finish, Boolean verbose) {
        String[] edges = graphString.split(", ");

        HashSet<String> verticeLabelSet = new HashSet<>();
        for (var edge : edges) {
            String[] abw = edge.split(" ");
            verticeLabelSet.add(abw[0]);
            verticeLabelSet.add(abw[1]);
        }
        var graph = new GraphImpl(verticeLabelSet.size());

        for (var edge : edges) {
            String[] abw = edge.split(" ");
            graph.addVertex(abw[0]);
            graph.addVertex(abw[1]);
            graph.addEdge(abw[0], abw[1], Integer.parseInt(abw[2]));
        }
        var gotPath = graph.shortestPath(start, finish);
        var expectedPath = findBestPathExhaustive(graph, start, finish);
        Assertions.assertEquals(pathToString(graph, expectedPath), pathToString(graph, gotPath));
        if (verbose) {
            System.out.println("\nGraph:");
            graph.display();
            System.out.printf("Path: %s\n", gotPath);
        }
    }

    String pathToString(Graph graph, List<String> path) {
        if (path == null) {
            return "<no path>";
        }
        int pathWeight = 0;
        String prevLabel = null;
        for (var label : path) {
            if (prevLabel != null) {
                pathWeight += graph.getWeight(prevLabel, label);
            }
            prevLabel = label;
        }
        return String.format("w=%d p=%s", pathWeight, path);
    }

    static final String cities = ""
            + "Москва Тула 1, "
            + "Тула Липецк 1, "
            + "Липецк Воронеж 1, "
            + "Москва Рязань 1, "
            + "Рязань Тамбов 1, "
            + "Тамбов Саратов 1, "
            + "Саратов Воронеж 1, "
            + "Москва Калуга 1, "
            + "Калуга Орёл 1, "
            + "Орёл Курск 1, "
            + "Курск Воронеж 1";

    static Stream<Arguments> shortestPathData() {
        return Stream.of(
                Arguments.arguments("A B 2", "A", "A"),
                Arguments.arguments("A B 2", "A", "B"),
                Arguments.arguments("A B 2, C A 5", "A", "C"),
                Arguments.arguments("A B 2, C A 5", "C", "B")
        );
    }

    @DisplayName("рандомизированный граф из презентации")
    @ParameterizedTest
    @MethodSource("seedData")
    void testRandomGraph(int seed) {
        var rnd = new Random(seed);
        String[] edges = cities.split(", ");
        for (int i = 0; i < edges.length; ++i) {
            edges[i] = edges[i].replace("1", "" + (rnd.nextInt(9) + 1)); // случайный вес
        }
        testShortestPath(String.join(", ", edges), "Москва", "Воронеж", true);
    }

    static Stream<Arguments> seedData() {
        return IntStream.range(101, 109).mapToObj(i -> Arguments.arguments(i));
    }

    static class ExhaustiveSearchContext {
        ArrayList<String> bestPath;
        int bestWeight;
        int start;
        int finish;
        Graph graph;
        int[] curPath;
    }

    // решение перебором всех путей
    List<String> findBestPathExhaustive(Graph graph, String start, String finish) {
        int vertices = graph.getSize();
        var ctx = new ExhaustiveSearchContext();
        ctx.bestWeight = -1;
        ctx.start = graph.indexOf(start);
        ctx.finish = graph.indexOf(finish);
        ;
        ctx.graph = graph;
        ctx.curPath = new int[vertices];
        Arrays.fill(ctx.curPath, -1);
        findBestPathExhaustiveRec(ctx, graph.indexOf(start), 0);
        return ctx.bestPath;
    }

    static void findBestPathExhaustiveRec(ExhaustiveSearchContext ctx, int cur, int weight) {
        if (cur == ctx.finish) {
            if (ctx.bestWeight == -1 || ctx.bestWeight > weight) {
                ctx.bestPath = new ArrayList<>();
                for (int i = cur; ; i = ctx.curPath[i]) {
                    ctx.bestPath.add(ctx.graph.getVertex(i));
                    if (i == ctx.start) {
                        break;
                    }
                }
                Collections.reverse(ctx.bestPath);
                ctx.bestWeight = weight;
            }
            return;
        }

        for (int i = 0; i < ctx.curPath.length; ++i) {
            if (i != cur && ctx.curPath[i] == -1) {
                int w = ctx.graph.getWeight(ctx.graph.getVertex(cur), ctx.graph.getVertex(i));
                if (w > 0) {
                    ctx.curPath[i] = cur;
                    findBestPathExhaustiveRec(ctx, i, weight + w);
                    ctx.curPath[i] = -1;
                }
            }
        }
    }
}
