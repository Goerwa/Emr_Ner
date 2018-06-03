package edge;

import vertex.Vertex;

/**
 * NetworkConnection used in newworkTopolpgy.
 * @author goerwa
 *
 */
public class NetworkConnection extends UndirectedEdge {
  // Abstraction function:
  // AF(Edge) = {edge|label != null && source ！= null
  // && target ！= null && weight >= 0 && type of
  // source, target != computer,server}.
  //
  // Representation invariant:
  // label cannot be null
  // Neither source nor target can be null
  // weight is more than or equals 0
  // type of two vertices cannot be both computer and server
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
  public NetworkConnection(final String label, final Double weight) {
    super(label, weight);
    //checkRep();
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
  public NetworkConnection(final String label, final Vertex src,
      final Vertex arg,  final Double weight) {
    super(label, src, arg, weight);
    //checkRep();
  }

  /**
   * checkrep to assert label cannot be null
   *  Neither source nor target can be null
   *   weight is more than or equals 0
   *   type of two vertices cannot be both computer and server.
   */
  private void checkRep() {
    assert !super.getsource().equals(super.gettarget());
    assert this.getweight() > 0;
    assert (this.getsource().tellclass().equals("Computer")
        || this.getsource().tellclass().equals("Router")
        || this.getsource().tellclass().equals("Server"));
    assert (this.gettarget().tellclass().equals("Computer")
        || this.gettarget().tellclass().equals("Router")
        || this.gettarget().tellclass().equals("Server"));
    assert !(super.getsource().tellclass().equals("Computer")
        && super.gettarget().tellclass().equals("Computer"));
    assert !(super.getsource().tellclass().equals("Server")
        && super.gettarget().tellclass().equals("Server"));
    assert (super.getweight() > 0);
  }

  @Override
  public final boolean equals(final Object e) {
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

  @Override
  public final String tellclass() {
    return "NetworkConnection";
  }
}
