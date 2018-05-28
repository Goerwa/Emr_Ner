package graph;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import org.apache.log4j.Logger;
import Exception.ExceptionofInput;
import Exception.ExceptionofLabel;
import Exception.ExceptionofUnproperEdge;
import vertex.Vertex;
import edge.Edge;
import factory.SocialNetworkFactory;
/**
 * ConcreteGraph.
 * @author goerwa
 *
 */
public class ConcreteGraph implements Graph<Vertex, Edge> {
  /** vertices. */
  private final Set<Vertex> vertices = new HashSet<>();
  /** edges.*/
  private Set<Edge> edges = new HashSet<>();
  /** log.*/
  Logger logger = Logger.getLogger(ConcreteGraph.class);
  // Abstraction function:
  // AF(vertices,Edge) = {vertices,edge|vertices != null && edges'weight >0}.
  //
  // Representation invariant:
  // all vertices are not null and all edges'weight are greater than 0.
  //
  // Safety from rep exposure:
  // All fileds are private
  // All return value types are mutable, create new variables to
  // prevent sharing with clients.
  //
 /**
  * checkrep to assert vertices are not null.
  */
  private void checkRep() {
    assert vertices != null;
  }
  /**
   * to construct.
   */
  public ConcreteGraph() {
    checkRep();
  }

  @Override
  public boolean addVertex(final Vertex v)
      throws ExceptionofInput, IOException {
    boolean r;
    boolean flag = true;
    while (flag) {
      flag = false;
      try {
        for (Vertex v1 : vertices) {
          if (v1.getlabel().equals(v.getlabel())) {
            flag = true;
            logger.warn("the label is repeated");
            throw new ExceptionofLabel("the label is repeated");
          }
        }
      } catch (ExceptionofLabel l) {
        System.out.print("the label of vertex named " + v.getlabel()
        + " is repeated ");
        int old = v.getnumber();
        old++;
        String number = old + "";
        String newstr = v.getlabel() + old;
        System.out.println("so change label to " + newstr + "!");
        v.setlabel(newstr);
      }
    }
    if (v != null) {
      r = vertices.add(v);
    } else {
      return false;
    }
    checkRep();
    return r;
  }

  @Override
  public boolean removeVertex(final Vertex v) {
    if (!vertices.contains(v)) {
      checkRep();
      return false;
    }
    vertices.remove(v);
    for (Iterator<Edge> iterator = edges.iterator(); iterator.hasNext();) {
      Edge edge = iterator.next();
      if (edge.getsource().equals(v) || edge.gettarget().equals(v)) {
        iterator.remove();
      }
    }
    checkRep();
    return true;
  }

  @Override
  public Set<Vertex> vertices() {
    return new HashSet<Vertex>(vertices);
  }

  @Override
  public Map<Vertex, List<Double>> sources(final Vertex target) {
    Map<Vertex, List<Double>> result = new HashMap<>();
    for (Edge edge : edges) {
      if (target.equals(edge.gettarget())) {
        List<Double> l = new ArrayList<>();
        l.add(edge.getweight());
        result.put(edge.getsource(), l);
      }
    }

    checkRep();
    return result;
  }

  @Override
  public Map<Vertex, Double> sourcesone(final Vertex target) {
    Map<Vertex, Double> result = new HashMap<>();
    for (Edge edge : edges) {
      if (target.equals(edge.gettarget())) {
        Double l = new Double(edge.getweight());
        result.put(edge.getsource(), l);
      }
    }

    checkRep();
    return result;
  }

  @Override
  public Map<Vertex, List<Double>> targets(final Vertex source) {
    Map<Vertex, List<Double>> result = new HashMap<>();
    for (Edge edge : edges) {
      if (source.equals(edge.getsource())) {
        List<Double> l = new ArrayList<>();
        l.add(edge.getweight());
        result.put(edge.gettarget(), l);
      }
    }

    checkRep();
    return result;
  }


  @Override
  public Map<Vertex, Double> targetsone(final Vertex target) {
    Map<Vertex, Double> result = new HashMap<>();
    for (Edge edge : edges) {
      if (target.equals(edge.getsource())) {
        Double l = new Double(edge.getweight());
        result.put(edge.gettarget(), l);
      }
    }

    checkRep();
    return result;
  }

  @Override
  public boolean addEdge(final Edge edge) throws
  ExceptionofInput, ExceptionofUnproperEdge, IOException {
    boolean r;
    boolean flag = true;
    if (edge == null) {
      return false;
    }
    if (!vertices.contains(edge.getsource())
        || !vertices.contains(edge.gettarget())) {
      throw new ExceptionofUnproperEdge("one vertex is not in graph");
    }
    for (Edge e : edges) {
      if (e.equals(edge)) {
        logger.error("Multiple edges are not allowed in the graph");
        throw new ExceptionofUnproperEdge(
            "Multiple edges are not allowed in the graph");
      }
    }
    while (flag) {
      flag = false;
      try {
        for (Edge e : edges) {
          if (e.getlabel().equals(edge.getlabel())) {
            logger.warn("the label is repeated");
            flag = true;
            throw new ExceptionofLabel("the label is repeated");
          }
        }
      } catch (ExceptionofLabel l) {
        System.out.print("the label of edge named "
            + edge.getlabel() + " is repeated ");
        int old = edge.getnumber();
        old++;
        String number = old + "";
        String newstr = edge.getlabel() + old;
        System.out.println("so change label to " + newstr + "!");
        edge.setlabel(newstr);
      }
    }
    // r = edges.add(edge);

    if ((edge != null)) {
      r = edges.add(edge);
    } else {
      return false;
    }
    checkRep();
    return r;
  }

  @Override
  public boolean removeEdge(final Edge edge) throws
  ExceptionofInput, ExceptionofUnproperEdge, IOException {
    boolean r = false;
    Edge redge = null;
    for (Edge e : edges) {
      if (edge.equals(e)) {
        r = true;
        redge = e;
      }
    }
    edges.remove(redge);
    if (!r) {
      // System.out.println("not cotain");
      checkRep();
      return false;
    } else {
      checkRep();
      return r;
    }
  }

  @Override
  public boolean containedge(final Edge edge) {
    for (Edge e : edges) {
      if (e.equals(edge)) {
        return true;
      }
    }
    return false;
  }

  @Override
  public int getdistance(final Vertex v1, final Vertex v2) {
    if (v1.equals(v2)) {
      return 1;
    }
    if (!vertices.contains(v1)
        || !vertices.contains(v2)) {
      return 0;
    }
    Queue<Vertex> queue = new LinkedList<Vertex>();
    Map<String, Integer> visit = new HashMap<>();
    Set<Vertex> marked = new HashSet<>();
    queue.add(v1);
    visit.put(v1.getlabel(), 0);
    marked.add(v1);
    while (!queue.isEmpty()) {
      // Set edges1
      Vertex old = queue.poll();
      for (Edge e : edges) {
        // System.out.println(old.getlabel()+ " "+e.getsource().getlabel()+"
        // "+e.gettarget().getlabel() );
        if (e.gettarget().equals(v2) && e.getsource().equals(old)) {
          int oldv = visit.get(old.getlabel());
          // System.out.println(oldv);
          return ++oldv;
        }
      }
      for (Edge e : edges) {
        // System.out.println(old.getlabel()+ " "+e.getsource().getlabel()+"
        // "+e.gettarget().getlabel() );
        if (e.getsource().equals(old) && !marked.contains(e.gettarget())) {

          int oldv = visit.get(old.getlabel());
          visit.remove(old.getlabel());
          oldv++;
          visit.put(e.gettarget().getlabel(), oldv);
          marked.add(e.gettarget());
          queue.add(e.gettarget());

        }
      }
    }
    return 0;

  }


  @Override
  public Set<Edge> edges() {
    return new HashSet<Edge>(edges);
  }

  @Override
  public Vertex findvertexbylabel(final String str) {

    for (Vertex v : vertices) {
      if (v.getlabel().equals(str)) {
        return v;
      }
    }
    return null;
  }

}
