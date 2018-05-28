package graph;

import java.io.IOException;
import org.apache.log4j.Logger;
import Exception.ExceptionofInput;
import Exception.ExceptionofUnproperEdge;
import edge.Edge;
import factory.SocialNetworkFactory;
import vertex.Vertex;

/**
 * NetworkTopology.
 * @author goerwa
 *
 */
public class NetworkTopology extends ConcreteGraph {
  Logger loggersc = Logger.getLogger(SocialNetworkFactory.class);

  @Override
  public final boolean addVertex(final Vertex net) throws ExceptionofInput,
  IOException {
    if (!confirmclass(net)) {
      logger.error("type of vertex is wrong");
      throw new ExceptionofInput("type of vertex is wrong");
    }
    super.addVertex(net);
    return true;
  }
  /**
   * confirm class of vertex is right.
   * @param v vertex
   * @return true if type is right
   */
  private boolean confirmclass(final Vertex v) {
    // System.out.println(v.tellclass());
    if (v.tellclass().equals("Computer")) {
      return true;
    } else if (v.tellclass().equals("Router")) {
      return true;
    } else if (v.tellclass().equals("Server")) {
      return true;
    }
    return false;
  }

  @Override
  public boolean addEdge(final Edge e) throws ExceptionofInput,
  ExceptionofUnproperEdge, IOException {
    Vertex person1 = e.getsource();
    Vertex person2 = e.gettarget();

    if (person1.getlabel().equals(person2.getlabel())) {
      loggersc.error("the source and target vertex cannot be the same!");
      throw new ExceptionofUnproperEdge("the source and target vertex"
          + "cannot be the same!");
    }

    super.addEdge(e);
    return true;
  }
 /**
  * tell class of vertex.
  * @param str label of vertex
  * @return the class of vertex
  */
  public final String vertexbyclass(final String str) {
    for (Vertex v : super.vertices()) {
      if (v.getlabel().equals(str)) {
        return v.tellclass();
      }
    }
    return null;
  }

  @Override
  public final Vertex findvertexbylabel(final String str) {
    for (Vertex v : super.vertices()) {
      if (v.getlabel().equals(str)) {
        return v;
      }
    }
    return null;
  }
}
