package edge;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import vertex.Vertex;
/**
 * DirectedEdge.
 * @author goerwa
 *
 */
public abstract class DirectedEdge extends Edge {
  // Abstraction function:
  // AF(Edge) = {edge|label != null && source ！= null
  // && target ！= null && weight >= 0}.
  //
  // Representation invariant:
  // label cannot be null
  // Neither source nor target can be null
  // weight is more than or equals 0
  //
  // Safety from rep exposure:
  // All fileds are private
  // the get methods can prevent sharing data with clients.
  /**
   * to construct.
   * @param label
   * label of edge
   * @param weight
   * weight of edge
   */
  public DirectedEdge(final String label, final Double weight) {
    super(label, weight);
  }
  /**
   * to construct.
   * @param src
   * source of edge
   * @param arg
   * target of edge
   * @param weight
   * weight of edge
   */
  public DirectedEdge(final Vertex src, final Vertex arg, final Double weight) {
    super(src, arg, weight);
  }
  /**
   * to construct.
   * @param label
   * label of edge
   * @param src
   * source of edge
   * @param arg
   * target of edge
   * @param weight
   * weight of edge
   */
  public DirectedEdge(final String label, final Vertex src, final Vertex arg,
      final double weight) {
    super(label, src, arg, weight);
    checkRep();
  }
  /**
   * checkrep to assert label is not null
   * and weight is more than 0
   * and number of vertices if 2.
   */
  private void checkRep() {
    assert this.getlabel() != null;
    assert this.getweight() >= 0;
    assert this.getvers().size() == 2;
  }

  @Override
  final Set<Vertex> sourceVertices() {
    Set<Vertex> r = new HashSet<>();
    Vertex v = super.getsource();
    r.add(v);
    return r;
  }

  @Override
  final Set<Vertex> targetVertices() {
    Set<Vertex> r = new HashSet<>();
    Vertex v = super.gettarget();
    r.add(v);
    checkRep();
    return r;
  }

  @Override
 public final boolean addVertices(final List<Vertex> vertices) {
    super.addvers(vertices.get(0), vertices.get(1));
    return true;
  }

  @Override
  public boolean equals(final Object e) {
    if (e == null) {
      return false;
    }
    Edge edge = (Edge) e;
    if (edge.tellclass().equals(this.tellclass())) {
      if ((edge.getsource().equals(this.getsource())
          && (edge.gettarget().equals(this.gettarget())))) {
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
   public String tellcalss() {
    return "DirectedEdge";
  }

}
