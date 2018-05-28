package edge;

import vertex.Vertex;
/**
 * MovieDirectorRelation used in moviegraph.
 * @author goerwa
 *
 */
public class MovieDirectorRelation extends UndirectedEdge {
  // Abstraction function:
  // AF(Edge) = {edge|label != null && source ！= null
  // && target ！= null && weight >= 0 && type of
  // source, target = movie and director}.
  //
  // Representation invariant:
  // label cannot be null
  // Neither source nor target can be null
  // weight is more than or equals 0
  // type of two vertices include movie and director
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
  public MovieDirectorRelation(final String label, final Double weight) {
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
  public MovieDirectorRelation(final String label, final Vertex src,
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
    if (this.getsource().tellclass().equals("Movie")) {
      assert this.gettarget().tellclass().equals("Director");
    } else if (this.getsource().tellclass().equals("Director")) {
      assert this.gettarget().tellclass().equals("Movie");
    } else {
      assert this.gettarget().tellclass().equals("Movie");
      assert this.getsource().tellclass().equals("Movie");
    }
  }
  /**
   * to tell the class of edge.
   * @return
   * return the class of edge
   */
  @Override
  public String tellclass() {
    return "MovieDirectorRelation";
  }
}
