package vertex;
/**
 * person used in SocialNetwork.
 * @author goerwa
 *
 */
public class Person extends Vertex {
  // Abstraction function:
  // AF(Vertex) = {vertex|label ！= null && year > 0 && gender = "M"|"F"}.
  //
  // Representation invariant:
  // label cannot be null,year are over 0 and gender is M or F
  //
  // Safety from rep exposure:
  // All fileds are private
  // the get methods can prevent sharing data with clients.
  /** year of people. */
  private int year;
  /** gender of people. */
  private String gender;
  /**
   * to construct.
   * @param name
   * name of person
   */
  public Person(final String name) {
    super(name);
    this.gender = "M";
    this.year = 18;
    //checkRep();
  }
  /**
   * to construct.
   * @param name
   * name of person
   * @param y
   * year of person
   * @param g
   * gender of person
   */
  public Person(final String name, final int y, final String g) {
    super(name);
    //assert (g.equals("M") || g.equals("F"));
    this.gender = g;
    //assert (y > 0);
    this.year = y;
    //checkRep();
  }
  /**
   * to get year of person.
   * @return
   * return year of person
   */
  public final int getyear() {
    return this.year;
  }
  /**
   * to get gender of person.
   * @return
   * return gender of person
   */
  public final String getgender() {
    return this.gender;
  }
  /**
   * checkrep to assert gender is M or F
   * and year is more than 0.
   */
  private void checkRep() {
    assert (this.gender.equals("M") || this.gender.equals("F"));
    assert (this.year > 0);
  }

  @Override
  public void fillVertexInfo(final String[] args) {
    //assert (args[2].equals("M") || args[2].equals("F"));
    this.gender = args[2];
    int age = Integer.valueOf(args[3]).intValue();
    //assert (age > 0);
    this.year = age;
    checkRep();
  }

  @Override
  public boolean equals(final Object obj) {
    if (obj == null) {
      return false;
    }
    Vertex v = (Vertex) obj;
    if (v.getlabel().toLowerCase().equals(this.getlabel().toLowerCase())
        && v.tellclass().equals("Person")) {
      return true;
    }
    return false;
  }

  @Override
  public final String toString() {
    return "type :" + this.tellclass() + "label :" + this.getlabel()
    + "Gender: " + this.gender + "Year: " + this.year + "\n";
  }

  @Override
  public final int hashCode() {
    int result = 17;
    result = 31 * result + super.getlabel().hashCode() + this.gender.hashCode();
    return result;
  }

  @Override
  public String tellclass() {
    return "Person";
  }
}
