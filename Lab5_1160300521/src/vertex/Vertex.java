package vertex;

/**
 * vertex.
 * @author goerwa
 *
 */
public abstract class Vertex {


  // Abstraction function:
  // AF(Vertex) = {vertex|label ！= null }.
  //
  // Representation invariant:
  // label cannot be null.
  //
  // Safety from rep exposure:
  // All fileds are private
  // the get methods can prevent sharing data with clients.
  // the remove methods only return true or false.
  // the add mothods return immutable type or 0

  /** label of vertex. */
  private String label;
  /** number of vertex. */
  private int number = 0;

  /**
   * to construct.
   * @param l
   * label of vertex
   */
  public Vertex(final String l) {
    this.label = l;
    //checkRep();
  }

  /** checkrep to assert label is not null. */
  private void checkRep() {
    assert label != null;
  }
  /**
   * to get label.
   * @return
   * return the label of vertex
   */
  public final String getlabel() {
    return label;
  }
  /**
   * to set a new label.
   * @param newlabel
   *  newlabel
   */
  public final void setlabel(final String newlabel) {
    assert newlabel != null;
    label = newlabel;
  }
  /**
   * to get number of vertex.
   * @return
   * return the number
   */
  public final int getnumber() {
    return number;
  }
  /**
   * to set a new number.
   * @param newnumber
   * newnumber
   */
  public final void setnumber(final int newnumber) {
    number = newnumber;
  }
  /**
   * to fill information.
   * @param args
   * information
   */
  abstract void fillVertexInfo(String[] args);
  /**
   * to tell the class of vertex.
   * @return
   * return the class name of vertex
   */
  public String tellclass() {
    return "Vertex";
  }
  @Override
  public String toString() {
    return "type :" + this.tellclass() + "label :" + this.getlabel() + "\n";
  }

  @Override
  public boolean equals(final Object obj) {
    if (obj == null) {
      return false;
    }
    Vertex v = (Vertex) obj;
    if (v.getlabel().toLowerCase().equals(this.label.toLowerCase())) {
      return true;
    }
    return false;
  }

  @Override
  public int hashCode() {
    int result = 17;
    result = 31 * result + this.getlabel().hashCode();
    return result;
  }
}
