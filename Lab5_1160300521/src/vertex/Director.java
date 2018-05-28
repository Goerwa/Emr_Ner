package vertex;
/**
 * director used in moviegraph.
 * @author goerwa
 *
 */
public class Director extends Person {
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
   * name of director
   * @param year
   * year of director
   * @param gender
   * gender of director
   */
  public Director(final String name, final int year, final String gender) {
    super(name, year, gender);
  }
  /**
   * to construct.
   * @param name
   * name of director
   */
  public Director(final String name) {
    super(name);
  }

  @Override
  public final String tellclass() {
    return "Director";
  }

  @Override
  public final boolean equals(final Object obj) {
    if (obj == null) {
      return false;
    }
    Vertex v = (Vertex) obj;
    if (v.getlabel().toLowerCase().equals(this.getlabel().toLowerCase())
        && v.tellclass().equals("Director")) {
      return true;
    }
    return false;
  }
}
