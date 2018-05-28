package graph;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import org.apache.log4j.Logger;
import Exception.ExceptionofInput;
import Exception.ExceptionofUnproperEdge;
import edge.CommentTie;
import edge.Edge;
import edge.ForwardTie;
import edge.FriendTie;
import vertex.Person;
import vertex.Vertex;;
/**
 * socialnetwork.
 * @author goerwa
 *
 */
public class SocialNetwork extends ConcreteGraph {
  Logger loggersc = Logger.getLogger(SocialNetwork.class);
  /** to construct. */
  public SocialNetwork() {

  }

  @Override
  public final boolean addVertex(final Vertex person)
      throws ExceptionofInput,  IOException {
    if (!person.tellclass().equals("Person")) {
      loggersc.error("type of vertex is wrong");
      throw new ExceptionofInput("type of vertex is wrong");
    }
    super.addVertex(person);
    return true;
  }
 /**
  * Determine if the point is in the graph.
  * @param person person
  * @return true if in graph,otherwise return false;
  */
  private boolean isingraph(final Vertex person) {
    Set<Vertex> r = new HashSet<Vertex>();
    r = super.vertices();
    if (r.contains(person)) {
      return true;
    }
    return false;
  }
  /**
   * to get edge.
   * @param e edge.
   * @return edge if find it
   */
  public final Edge getedge(final Edge e) {
    Set<Edge> edges = super.edges();
    for (Edge edge : edges) {
      if (edge.equals(e)) {
        return edge;
      }
    }
    return null;
  }

  @Override
  public boolean addEdge(final Edge e) throws ExceptionofInput,
  ExceptionofUnproperEdge, IOException {
    Person person1 = (Person) e.getsource();
    Person person2 = (Person) e.gettarget();
    // System.out.println(e.getlabel() + " add ");
    if (person1.getlabel().equals(person2.getlabel())) {
      loggersc.error("the source and target vertex cannot be the same!");
      throw new ExceptionofUnproperEdge("the source and target vertex"
          + "cannot be the same!");
    }
    if (e.getweight() > 1.0 || e.getweight() <= 0) {
      loggersc.error("the weight of edge must be (0,1]!");
      throw new ExceptionofUnproperEdge("the weight of edge must be (0,1]!");
    }
    if (!(e.getsource().tellclass().equals("Person")
        && e.gettarget().tellclass().equals("Person"))) {
      loggersc.error("type of vertex is wrong");
      throw new ExceptionofInput("type of vertex is wrong");
    }

    if (super.edges().size() == 0) {
      if (e.tellclass().equals("FriendTie")) {
        FriendTie ne = new FriendTie(e.getlabel(), e.getsource(),
            e.gettarget(), 1.0);
        super.addEdge(ne);
      }
      if (e.tellclass().equals("CommentTie")) {
        CommentTie ne = new CommentTie(e.getlabel(), e.getsource(),
            e.gettarget(), 1.0);

        super.addEdge(ne);
      }
      if (e.tellclass().equals("ForwardTie")) {
        ForwardTie ne = new ForwardTie(e.getlabel(), e.getsource(),
            e.gettarget(), 1.0);

        super.addEdge(ne);
      }
      return true;
    }

    boolean r = false;
    boolean contain = super.containedge(e);
    double eweight = e.getweight();
    double old = 0;
    Set<Edge> edges = super.edges();
    // System.out.println(contain);
    if (!contain) {
      r = super.addEdge(e);
    } else {
      logger.error("Multiple edges are not allowed in the graph");
      throw new ExceptionofUnproperEdge("Multiple edges are not"
          + "allowed in the graph");
    }
    for (Edge edge : edges) {
      if (edge.equals(e)) {
        old = edge.getweight();
      }
    }
    for (Edge edge : edges) {
      if (edge.equals(e)) {
        super.removeEdge(edge);
        r = super.addEdge(e);
      }
      if (edge.tellclass().equals("FriendTie")) {
        if (contain) {
          // equals new
          String name = edge.getlabel();
          double wei = edge.getweight() * (1.0 - eweight) / (1.0 - old);
          if (old == 1.0) {
            wei = 0.0;
          }
          FriendTie newedge = new FriendTie(name, edge.getsource(),
              edge.gettarget(), wei);
          super.removeEdge(edge);
          super.addEdge(newedge);
        } else {
          // equals other
          String name = edge.getlabel();
          double wei = edge.getweight() * (1.0 - eweight);
          if (super.edges().isEmpty()) {
            wei = 1.0;
          }
          FriendTie newedge = new FriendTie(name, edge.getsource(),
              edge.gettarget(), wei);
          super.removeEdge(edge);
          super.addEdge(newedge);
        }
      } else if (edge.tellclass().equals("CommentTie")) {
        // System.out.println("eeeeee");
        if (contain) {
          double wei = edge.getweight() * (1.0 - eweight) / (1.0 - old);
          String name = edge.getlabel();
          if (old == 1.0) {
            wei = 0.0;
          }
          CommentTie newedge = new CommentTie(name, edge.getsource(),
              edge.gettarget(), wei);
          super.removeEdge(edge);
          super.addEdge(newedge);
        } else {
          double wei = edge.getweight() * (1.0 - eweight);
          String name = edge.getlabel();
          if (super.edges().isEmpty()) {
            wei = 1.0;
          }
          CommentTie newedge = new CommentTie(name, edge.getsource(),
              edge.gettarget(), wei);
          super.removeEdge(edge);
          super.addEdge(newedge);
        }
      } else if (edge.tellclass().equals("ForwardTie")) {
        if (contain) {
          double wei = edge.getweight() * (1.0 - eweight) / (1.0 - old);
          if (old == 1.0) {
            wei = 0.0;
          }
          String name = edge.getlabel();
          ForwardTie newedge = new ForwardTie(name, edge.getsource(),
              edge.gettarget(), wei);
          super.removeEdge(edge);
          super.addEdge(newedge);
        } else {
          double wei = edge.getweight() * (1.0 - eweight);
          String name = edge.getlabel();
          if (super.edges().isEmpty()) {
            wei = 1.0;
          }
          ForwardTie newedge = new ForwardTie(name, edge.getsource(),
              edge.gettarget(), wei);
          super.removeEdge(edge);
          super.addEdge(newedge);
        }
      }
    }
    return r;
  }

  @Override
  public boolean removeEdge(final Edge e) throws ExceptionofInput,
  ExceptionofUnproperEdge, IOException {
    Set<Edge> edges = super.edges();
    boolean r = false;
    boolean in = false;
    for (Edge edge : edges) {
      if (edge.equals(e)) {
        in = true;
      }
    }
    if (!in) {
      // System.out.println("not cotain");
      return false;
    }
    for (Edge edge : edges) {
      if (!edge.equals(e)) {
        double wa = e.getweight();
        double wei = edge.getweight() / (1.0 - wa);
        // System.out.println(wei);
        FriendTie newedge = new FriendTie(edge.getsource(),
            edge.gettarget(), wei);
        super.removeEdge(edge);
        super.addEdge(newedge);
      } else {
        r = super.removeEdge(e);
      }
    }
    // System.out.println(r);
    return r;

  }
}
