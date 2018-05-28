package helper;

import edge.Edge;
import graph.Graph;
import vertex.Vertex;
import java.util.Set;
import edge.DirectedEdge;
import edge.UndirectedEdge;

public class GraphMetrics {
  // 计算图g中节点v的degree centrality
  static double degreeCentrality(Graph<Vertex, Edge> g, Vertex v) {
    double n = g.vertices().size() - 1.0;
    double m = (double) g.targets(v).size();
    double r = m / n;
    return r;
  }

  static double degreeCentrality(Graph<Vertex, Edge> g) {
    // 计算图g的总体degree centrality
    double sum = 0;
    double n = g.vertices().size() - 1.0;
    for (Vertex v : g.vertices()) {
      sum += (double) g.targets(v).size();
    }
    double r = sum / n;
    return r;
  }

  static double betweennessCentrality(Graph<Vertex, Edge> g, Vertex v) {
    // 计算图g中节点v的betweenness centrality
    boolean flag = false;
    for (Edge e : g.edges()) {
      if (e.tellclass().equals("UndirectedEdge")) {
        flag = true;
        break;
      }
    }
    if (flag) {
      double sum, n;
      sum = g.vertices().size() * (g.vertices().size() - 1) / 2.0;
      n = (double) (g.sources(v).size());
      double r = n / sum;
      return r;
    } else {
      double sum, n;
      sum = g.vertices().size() * (g.vertices().size() - 1) + 0.0;
      n = (double) (g.sources(v).size() + g.targets(v).size());
      double r = n / sum;
      return r;
    }
  }

  static double closenessCentrality(Graph<Vertex, Edge> g, Vertex v) {
    // 计算图g中节点v的closeness centrality
    Set<Vertex> vers = g.vertices();
    double sum = 0.0;
    for (Vertex vertex : vers) {
      if (!vertex.equals(v)) {
        sum += g.getdistance(v, vertex);
      }
    }

    int n = vers.size() - 1;

    return n / sum;
  }

}
