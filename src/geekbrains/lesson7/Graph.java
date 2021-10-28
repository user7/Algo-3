package geekbrains.lesson7;

import java.util.List;

public interface Graph {

    void addVertex(String label);

    String getVertex(int i);

    boolean addEdge(String startLabel, String secondLabel, int weight);

    int getWeight(String firstLabel, String secondLabel);

    List<String> shortestPath(String startLabel, String finishLabel);

    boolean hasVertex(String label);

    int indexOf(String label);

    int getSize();

    void display();

    /**
     * англ. Depth-first search, DFS
     */
    void dfs(String startLabel);

    /**
     * англ. breadth-first search, BFS
     */
    void bfs(String startLabel);
}