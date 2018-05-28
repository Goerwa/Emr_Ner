package edge;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import vertex.Vertex;
/**
 * edge.
 * @author goerwa
 *
 */
public abstract class Edge {

  // Abstraction function:
  // AF(Edge) = {edge|label != null && source ！= null && target ！= null }.
  //
  // Representation invariant:
  // label cannot be null
  // Neither source nor target can be null
  //
  // Safety from rep exposure:
  // All fileds are private
  // the get methods can prevent sharing data with clients.

  /** the list of all vertices.*/
  private List<Vertex> vers = new ArrayList<>();
  /** weight of edge.*/
  private Double weight;
  /** label of edge.*/
  private String label;
  /** number of edge.*/
  private int number = 0;
  /**
   * to construct.
   * @param l
   * label of edge
   * @param w
   * weight of edge
   */
  public Edge(final String l, final Double w) {
    this.label = l;
    this.weight = w;
  }
  /**
   * to construct.
   * @param src
   * source of edge
   * @param arg
   * target of edge
   * @param w
   * weight of edge
   */
  public Edge(final Vertex src, final Vertex arg, final Double w) {
    this.label = "name";
    this.vers.add(0, src);
    this.vers.add(1, arg);
    this.weight = w;
    //checkRep();
  }
  /**
   * to construct.
   * @param l
   * label of edge
   * @param src
   * source of edge
   * @param arg
   * target of edge
   * @param w
   * weight of edge 
   */
  public Edge(final String l, final Vertex src, final Vertex arg,
      final Double w) {
    this.label = l;
    this.vers.add(0, src);
    this.vers.add(1, arg);
    this.weight = w;
    //checkRep();
  }
  /**
   * checkrep used to assert label is not null.
   */
  private void checkRep() {
    assert label != null;
  }
  
  /**
   * to add vertices.
   * @param vertices
   * vertices needed to add
   * @return
   * return true or false
   */
  abstract boolean addVertices(List<Vertex> vertices);
  /**
   * to get all vertices of source.
   * @return
   * return a set of all vertices of source
   */
  abstract Set<Vertex> sourceVertices();
  /**
   * to get all vertices of target.
   * @return
   * return a set of all vertices of target
   */
  abstract Set<Vertex> targetVertices();
  /**
   * determine if it contains the vertex.
   * @param v
   * the vertex
   * @return
   * if containing return true,otherwise return false
   */
  final boolean containVertex(final Vertex v) {
    for (Vertex vertex : vers) {
      if (vertex.equals(v)) {
        return true;
      }
    }
    return false;
  }
  /**
   * to add vertieces to edge.
   * @param add
   * vertieces
   * @return
   * return true if successfully,otherwise return false
   */
  public final boolean addvertices(final List<Vertex> add) {
    vers.addAll(add);
    //checkRep();
    return true;
  }
  /**
   * to add vertieces to edge.
   * @param v1
   * vertex one
   * @param v2
   * vertex two
   * @return
   * return true if successfully,otherwise return false
   */
  boolean addvers(final Vertex v1, final Vertex v2) {
    vers.add(0, v1);
    vers.add(0, v2);
    //checkRep();
    if (vers.size() == 2) {
      return true;
    }
    return false;
  }
  /**
   * to get vertices of edge.
   * @return
   * return vertices of edge
   */
  public final List<Vertex> getvers() {
    return vers;
  }
  /**
   * to get vertex of source.
   * @return
   * return vertex of source
   */
  public final Vertex getsource() {
    return vers.get(0);
  }
  /**
   * to get vertex of target.
   * @return
   * return vertex of target
   */
  public final Vertex gettarget() {
    return vers.get(1);
  }
  /**
   * to get weight of vertex.
   * @return
   * return weight of vertex
   */
  public final Double getweight() {
    return weight;
  }
  /**
   * to get label of vertex.
   * @return
   * return label of vertex
   */
  public final String getlabel() {
    return label;
  }
  /**
   * to set a new label.
   * @param newlabel
   * new label
   */
  public final void setlabel(final String newlabel) {
    label = newlabel;
  }
  /**
   * to get number of edge.
   * @return
   * return number of edge
   */
  public final int getnumber() {
    return number;
  }
  /**
   * to set a new number.
   * @param newnumber
   * new number
   */
  public final void setnumber(final int newnumber) {
    number = newnumber;
  }
 /**
  * to tell the class of edge.
  * @return
  * return the class of edge
  */
  public String tellclass() {
    return "Edge";
  }

  @Override
  public String toString() {
    return vers.get(0) + "->" + vers.get(1) + "=" + weight;
  }

  @Override
  public int hashCode() {
    int result = 17;
    result = 31 * result + label.hashCode() + weight.hashCode();
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if(obj == null) {
      return false;
    }
    Edge e = (Edge) obj;
    if (this.label.equals(e.getlabel())) {
      return true;
    }
     return false;
  }
}
