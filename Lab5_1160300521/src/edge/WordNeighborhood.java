package edge;

import vertex.Vertex;
/**
 * WordNeighborhood used in GraphPoet.
 * @author goerwa
 *
 */
public class WordNeighborhood extends DirectedEdge {
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
  public WordNeighborhood(final String label, final Double weight) {
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
  public WordNeighborhood(final String label, final Vertex src,
      final Vertex arg,  final Double weight) {
    super(label, src, arg, weight);
    //checkRep();
  }
  /**
   * checkrep to assert weight is more than 0
   * and the type of vertices is person.
   */
  private void checkRep() {
    assert this.getweight() >= 0;
    assert !(this.gettarget().equals(this.getsource()));
    assert this.getsource().tellclass().equals("Word");
    assert this.gettarget().tellclass().equals("Word");
  }

  @Override
  public final String tellcalss() {
    return "WordNeighborhood";
  }
  @Override
  public final boolean equals(final Object e) {
    if (e == null) {
      return false;
    }
    Edge edge = (Edge) e;
    if (edge.tellclass().equals(this.tellclass())) {
      if (edge.getsource().equals(this.getsource())
          && edge.gettarget().equals(this.gettarget())) {
        return true;
      }
    }
    return false;
  }

}
