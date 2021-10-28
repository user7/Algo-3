package geekbrains.lesson7;

import java.util.*;

public class GraphImpl implements Graph {

    private final ArrayList<Vertex> vertexList;
    private final int[][] weightMatrix; // weight = 0 если ребра нет

    public GraphImpl(int maxVertexCount) {
        this.vertexList = new ArrayList<>(maxVertexCount);
        this.weightMatrix = new int[maxVertexCount][maxVertexCount];
    }

    @Override
    public void addVertex(String label) {
        if (!hasVertex(label)) {
            vertexList.add(new Vertex(label));
        }
    }

    @Override
    public String getVertex(int i) {
        return i >= 0 && i < vertexList.size() ? vertexList.get(i).getLabel() : null;
    }

    @Override
    public boolean addEdge(String startLabel, String secondLabel, int weight) {
        int startIndex = indexOf(startLabel);
        int endIndex = indexOf(secondLabel);

        if (startIndex == -1 || endIndex == -1) {
            return false;
        }

        weightMatrix[startIndex][endIndex] = weight;
        return true;
    }

    @Override
    public boolean hasVertex(String label) {
        return indexOf(label) != -1;
    }

    @Override
    public int getWeight(String firstLabel, String secondLabel) {
        return weightMatrix[indexOf(firstLabel)][indexOf(secondLabel)];
    }

    @Override
    public int indexOf(String label) {
        for (int i = 0; i < vertexList.size(); i++) {
            if (vertexList.get(i).getLabel().equals(label)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public int getSize() {
        return vertexList.size();
    }

    @Override
    public void display() {
        for (int i = 0; i < getSize(); i++) {
            for (int j = 0; j < getSize(); j++) {
                if (weightMatrix[i][j] > 0) {
                    System.out.printf("%s -(%d)-> %s\n", vertexList.get(i), weightMatrix[i][j], vertexList.get(j));
                }
            }
            System.out.println();
        }
    }

    @Override
    public void dfs(String startLabel) {
        int startIndex = indexOf(startLabel);
        if (startIndex == -1) {
            throw new IllegalArgumentException("Неверная вершина: " + startLabel);
        }

        Stack<Vertex> stack = new Stack<>();
        Vertex vertex = vertexList.get(startIndex);

        visitedVertex(stack, vertex);
        while (!stack.isEmpty()) {
            vertex = getNearUnvisitedVertex(stack.peek());
            if (vertex != null) {
                visitedVertex(stack, vertex);
            } else {
                stack.pop();
            }
        }
        System.out.println();
    }

    private Vertex getNearUnvisitedVertex(Vertex vertex) {
        int currentIndex = vertexList.indexOf(vertex);
        for (int i = 0; i < getSize(); i++) {
            if (weightMatrix[currentIndex][i] > 0 && !vertexList.get(i).isVisited()) {
                return vertexList.get(i);
            }
        }
        return null;
    }

    private void visitedVertex(Stack<Vertex> stack, Vertex vertex) {
        System.out.print(vertex.getLabel() + " ");
        stack.add(vertex);
        vertex.setVisited(true);
    }

    private void visitedVertex(Queue<Vertex> queue, Vertex vertex) {
        System.out.print(vertex.getLabel() + " ");
        queue.add(vertex);
        vertex.setVisited(true);
    }

    @Override
    public void bfs(String startLabel) {
        int startIndex = indexOf(startLabel);
        if (startIndex == -1) {
            throw new IllegalArgumentException("Неверная вершина: " + startLabel);
        }

        Queue<Vertex> queue = new LinkedList<>();
        Vertex vertex = vertexList.get(startIndex);

        visitedVertex(queue, vertex);
        while (!queue.isEmpty()) {
            vertex = getNearUnvisitedVertex(queue.peek());
            if (vertex != null) {
                visitedVertex(queue, vertex);
            } else {
                queue.remove();
            }
        }
        System.out.println();
    }

    @Override
    public List<String> shortestPath(String startLabel, String finishLabel) {
        var pathWeight = new int[vertexList.size()];
        var pathBackref = new int[vertexList.size()];
        Arrays.fill(pathBackref, -1);

        ArrayList<String> result = new ArrayList<>();
        var start = indexOf(startLabel);
        var finish = indexOf(finishLabel);
        if (start == -1 || finish == -1) {
            return null;
        }
        if (start == finish) {
            result.add(startLabel);
            return result;
        }

        // индексы вершин графа, отсортированные по длине пути до них
        var wave = new TreeSet<Integer>((a, b) -> pathWeight[a] != pathWeight[b] ? pathWeight[a] - pathWeight[b] : a - b);
        wave.add(start);

        while (true) {
            var i = wave.pollFirst();
            if (i == null) {
                return null; // в волне не осталось вершин, не существует пути от start до finish
            }

            if (i == finish) {
                // кратчайший путь найден
                while (true) {
                    result.add(vertexList.get(i).getLabel());
                    if (i == start) {
                        Collections.reverse(result);
                        return result;
                    }
                    i = pathBackref[i];
                }
            }

            // по каждой смежной вершине волна катится дальше
            for (int j = 0; j < vertexList.size(); ++j) {
                int w = weightMatrix[i][j];
                if (j != i && w > 0) {
                    int newPathWeight = pathWeight[i] + w;
                    if (pathBackref[j] == -1 || pathWeight[j] > newPathWeight) {
                        if (pathBackref[j] != -1) {
                            wave.remove(j);
                        }
                        pathWeight[j] = newPathWeight;
                        pathBackref[j] = i;
                        wave.add(j);
                    }
                }
            }
        }
    }

    void printWave(String ctx, TreeSet<Integer> wave) {
        System.out.println(ctx + " " + wave.toString());
    }
}