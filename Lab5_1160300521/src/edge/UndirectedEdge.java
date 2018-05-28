package edge;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import vertex.Vertex;
/**
 * UndirectedEdge.
 * @author goerwa
 *
 */
public class UndirectedEdge extends Edge {
  // Abstraction function:
  // AF(Edge) = {edge|label != null && source ！= null && target ！= null}.
  //
  // Representation invariant:
  // label cannot be null
  // Neither source nor target can be null
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
  public UndirectedEdge(final String label, final Double weight) {
    super(label, weight);
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
  public UndirectedEdge(final String label, final Vertex src, final Vertex arg,
      final Double weight) {
    super(label, src, arg, weight);
    //checkRep();
  }
  /**
   * checkrep to assert label is not null
   * and source is not same as target
   * and number of vertices is 2.
   */
  /*
  private void checkRep() {
    assert this.getlabel() != null;
    assert !(this.getsource().equals(this.gettarget()));
    assert this.getvers().size() == 2;
  }
  */
  @Override Set<Vertex> sourceVertices() {
    Set<Vertex> r = new HashSet<>();
    Vertex v = super.getsource();
    r.add(v);
    //checkRep();
    return r;
  }

  @Override
  Set<Vertex> targetVertices() {
    Set<Vertex> r = new HashSet<>();
    Vertex v = super.gettarget();
    r.add(v);
    //checkRep();
    return r;
  }

  @Override
  public boolean addVertices(final List<Vertex> vertices) {
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
      } else if ((edge.getsource().equals(this.gettarget())
          && (edge.gettarget().equals(this.getsource())))) {
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
  public String tellclass() {
    return "UndirectedEdge";
  }

}
