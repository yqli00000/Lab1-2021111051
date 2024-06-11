import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * type类型.
*/

public class Type {
  private Map<String, List<Edge>> adjacencyList;
  /**
   * type初始化邻接表.
   */

  // 构造函数，初始化邻接表
  public Type() {
    adjacencyList = new HashMap<>();
  }
  /**
   * 添加顶点.
   */

  // 添加顶点
  public void addVertex(String vertex) {
    if (!adjacencyList.containsKey(vertex)) {
      adjacencyList.put(vertex, new ArrayList<>());
    }
  }
  /**
   * 添加有向边.
   */

  // 添加有向边
  public void addEdge(String source, String destination, int weight) {
    addVertex(source);
    addVertex(destination);
    adjacencyList.get(source).add(new Edge(destination, weight));
  }
  /**
   * 显示邻接表.
   */

  // 显示邻接表
  public void displayAdjacencyList() {
    System.out.println("Adjacency List:");
    for (Map.Entry<String, List<Edge>> entry : adjacencyList.entrySet()) {
      System.out.print(entry.getKey() + " -> ");
      for (Edge neighbor : entry.getValue()) {
        System.out.print(neighbor.destination + "(" + neighbor.weight + ") ");
      }
      System.out.println();
    }
  }
  /**
   * 获取邻接表.
   */

  public Map<String, List<Edge>> getAdjacencyList() {
    return new HashMap<>(adjacencyList);
  }
  /**
   * 顶点数.
   */

  public int getVertexCount() {
    return adjacencyList.size();
  }
  /**
   * 边数.
   */

  public int getEdgeCount() {
    int edgeCount = 0;
    for (List<Edge> edges : adjacencyList.values()) {
      edgeCount += edges.size();
    }
    return edgeCount;
  }
  /**
   * 获取权重.
   */

  public int getWeight(String source, String destination) {
    List<Edge> edges = adjacencyList.get(source);
    if (edges != null) {
      for (Edge edge : edges) {
        if (edge.destination.equals(destination)) {
          return edge.weight;
        }
      }
    }
    return -1; // 或者您可以根据需要返回其他默认值
  }
  /**
   * 表示边.
   */

  // 内部类表示边
  static class Edge {
    String destination;
    int weight;

    Edge(String destination, int weight) {
      this.destination = destination;
      this.weight = weight;
    }
  }
}
