package edge;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import vertex.Vertex;
/**
 * SameMovieHyperEdge used in Moviegraph.
 * @author goerwa
 *
 */
public class SameMovieHyperEdge extends Edge {
  // Abstraction function:
  // AF(vertices,Edge) = {vertices,edge|vertices != null
  // && size of vertices > 1 && type of vertices
  // = Actor}.
  //
  // Representation invariant:
  // all vertices are not null and size of vertices are over 1
  // type of vertices is Actor
  // Safety from rep exposure:
  // All fileds are private
  // All return value types are mutable,
  // create new variables to prevent sharing with clients.
  //
  /**
   * to construct.
   * @param label
   * label of edge
   * @param weight
   * weight of edge
   */
  public SameMovieHyperEdge(final String label, final double weight) {
    super(label, weight);
  }
  /**
   * checkrep to assert number of vertices is over 2
   * and type of vertices is actor.
   */
  private void checkRep() {
    assert this.getvers().size() > 1;
    for (Vertex v : super.getvers()) {
      assert (v.tellclass().equals("Actor"));
    }
  }

  @Override
  public final boolean addVertices(final List<Vertex> vertices) {
    super.addvertices(vertices);
    //checkRep();
    return true;
  }

  @Override
  final Set<Vertex> sourceVertices() {
    Set<Vertex> r = new HashSet<Vertex>();
    r.addAll(super.getvers());
    return r;
  }

  @Override
  final Set<Vertex> targetVertices() {
    Set<Vertex> r = new HashSet<Vertex>();
    r.addAll(super.getvers());
    return r;
  }

  @Override
  public final boolean equals(final Object e) {
    if (e == null) {
      return false;
    }
    Edge edge = (Edge) e;
    if (edge.tellclass().equals(this.tellclass())) {
      if (edge.getlabel().equals(this.getlabel())) {
        return true;
      }
    }
    return false;
  }
  /**
   * to tell the class of edge.
   * @return
   * return the class of edge
   */
  @Override
  public final String tellclass() {
    return "SameMovieHyperEdge";
  }
}
