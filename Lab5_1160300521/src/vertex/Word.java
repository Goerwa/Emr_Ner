package vertex;
/**
 * word used in GraphPoet.
 * @author goerwa
 *
 */
public class Word extends Vertex {
  // Abstraction function:
  // AF(Vertex) = {vertex|label ！= null }.
  //
  // Representation invariant:
  // label cannot be null.
  //
  // Safety from rep exposure:
  // All fileds are private
  // the get methods can prevent sharing data with clients.
  /**
   * to construct.
   * @param label
   * the label of word
   */
  public Word(final String label) {
    super(label);
    checkRep();
  }
  /** checkrep to assert label is not null.*/
  private void checkRep() {
    assert this.getlabel() != null;
  }
  /**
   * to fill information.
   * @param args
   * information
   */
  @Override
  public final void fillVertexInfo(final String[] args) {
    assert args != null;
    return;
  }

  @Override
  public boolean equals(final Object obj) {
    if (obj == null) {
      return false;
    }
    Vertex v = (Vertex) obj;
    if (v.getlabel().toLowerCase().equals(this.getlabel().toLowerCase())
        && v.tellclass().equals("Word")) {
      return true;
    }
    return false;
  }

  @Override
  public final String tellclass() {
    return "Word";
  }

}
