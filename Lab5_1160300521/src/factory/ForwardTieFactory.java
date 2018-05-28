package factory;

import java.util.List;
import edge.Edge;
import edge.ForwardTie;
import edge.FriendTie;
import vertex.Vertex;

public class ForwardTieFactory extends EdgeFactory {


  public ForwardTieFactory() {

  }

  @Override
  public Edge createEdge(String label, List<Vertex> vertices, double weight) {
    ForwardTie e = new ForwardTie(label, vertices.get(0), vertices.get(1), weight);
    return e;
  }

}
