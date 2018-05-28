package factory;

import java.util.List;
import edge.Edge;
import vertex.Vertex;

abstract class EdgeFactory {
  public abstract Edge createEdge(String label, List<Vertex> vertices, double weight);
}
