package edge;

import vertex.Vertex;
/**
 * MovieActorRelation used in MovieGraph.
 * @author goerwa
 *
 */
public class MovieActorRelation extends UndirectedEdge {
  // Abstraction function:
  // AF(Edge) = {edge|label != null && source ！= null
  // && target ！= null && weight >= 0 && type of
  // soure, target = movie and actor}.
  //
  // Representation invariant:
  // label cannot be null
  // Neither source nor target can be null
  // weight is more than or equals 0
  // two vertices include actor and movie
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
  public MovieActorRelation(final String label, final Double weight) {
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
  public MovieActorRelation(final String label, final Vertex src,
      final Vertex arg, final Double weight) {
    super(label, src, arg, weight);
    //checkRep();
  }
  /**
   * checkrep to assert label cannot be null
   * Neither source nor target can be null
   * weight is more than or equals 0
   * two vertices include actor and movie.
   */
  private void checkRep() {
    assert !this.getsource().equals(this.gettarget());
    assert this.getweight() > 0;
    if (this.getsource().tellclass().equals("Movie")) {
      assert this.gettarget().tellclass().equals("Actor");
    } else if (this.getsource().tellclass().equals("Actor")) {
      assert this.gettarget().tellclass().equals("Movie");
    } else {
      assert this.getsource().tellclass().equals("Actor");
    }
  }
  /**
   * to tell the class of edge.
   * @return
   * return the class of edge
   */
  @Override
  public String tellclass() {
    return "MovieActorRelation";
  }
}
