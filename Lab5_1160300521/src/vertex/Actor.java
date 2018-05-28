package vertex;
/**
 * actor used in moviegtaph.
 * @author goerwa
 *
 */
public class Actor extends Person {
  // Abstraction function:
  // AF(Vertex) = {vertex|label ！= null && year > 0 && gender = "M"|"F"}.
  //
  // Representation invariant:
  // label cannot be null,year are over 0 and gender is M or F
  //
  // Safety from rep exposure:
  // All fileds are private
  // the get methods can prevent sharing data with clients.
  /**
   * to construct.
   * @param name
   * name of actor
   */
  public Actor(final String name) {
    super(name);
  }
  /**
   * to construct.
   * @param name
   * name of actor
   * @param y
   * year of actor
   * @param g
   * gender of actor
   */
  public Actor(final String name, final int y, final String g) {
    super(name, y, g);
  }

  @Override
  public final String tellclass() {
    return "Actor";
  }

  @Override
  public final boolean equals(final Object obj) {
    if (obj == null) {
      return false;
    }
    Vertex v = (Vertex) obj;
    if (v.getlabel().toLowerCase().equals(this.getlabel().toLowerCase())
        && v.tellclass().equals("Actor")) {
      return true;
    }
    return false;
  }
}