package vertex;

import java.text.DecimalFormat;
/**
 * movie used in moviegraph.
 * @author goerwa
 *
 */
public class Movie extends Vertex {
  // Abstraction function:
  // AF(Vertex) = {vertex|label ！= null && 2018 >=year >= 1900
  // && 0.00<= score <=10.00 && nation !=
  // null}.
  //
  // Representation invariant:Release year is four positive
  // integers in the range [1900,
  // 2018],Shooting country cannot be null
  // IMDb score ranges 0-10 rangeValue, up to 2 decimal places.
  //
  // Safety from rep exposure:
  // All fileds are private
  // the get methods can prevent sharing data with clients.
  /** year of movie shooting.*/
  private int year;
  /** nation of movie shooting.*/
  private String nation;
  /** score of movie.*/
  private double score;
  /** the format of score.*/
  private DecimalFormat df = new DecimalFormat("0.00 ");
  /**
   * to consruct.
   * @param name
   * name of movie
   * @param y
   * year of movie shooting
   * @param n
   * nation of movie shooting
   * @param s
   * the format of score
   */
  public Movie(final String name, final int y, final String n, final double s) {
    super(name);
    this.score = Double.parseDouble(df.format(s));
    this.nation = n;
    this.year = y;
    //checkRep();
  }
  /**
   * to consruct.
   * @param name
   * name of movie
   */
  public Movie(final String name) {
    super(name);
    this.score = 8.80;
    this.nation = "china";
    this.year = 2000;
    //checkRep();
  }
  /**
   * to get the shooting year.
   * @return
   * return the shooting year
   */
  public final int getyear() {
    return this.year;
  }
  /**
   * to get the shooting nation.
   * @return
   * return the shooting nation
   */
  public final String getnation() {
    return super.getlabel();
  }
  /**
   * to get score.
   * @return
   * return the score
   */
  public final double getscore() {
    return this.score;
  }
  /**
   * checkrep to assert shooting year is [1900,2018]
   * and shooting nation is not null.
   */
  private void checkRep() {
    assert (this.year >= 1900 && this.year <= 2018);
    assert this.nation != null;
  }

  @Override
  public final void fillVertexInfo(final String[] args) {
    int years = Integer.valueOf(args[2]).intValue();
    //assert (years >= 1900 && years <= 2018);
    //assert args[3] != null;
    this.year = Integer.valueOf(args[2]).intValue();
    this.nation = args[3];
    double s = Double.parseDouble(args[4]);
    this.score = Double.parseDouble(df.format(s));
  }

  @Override
  public final boolean equals(final Object obj) {
    if (obj == null) {
      return false;
    }
    Vertex v = (Vertex) obj;
    if (v.getlabel().toLowerCase().equals(this.getlabel().toLowerCase())
        && v.tellclass().equals("Movie")) {
      return true;
    }
    return false;
  }

  @Override
  public final String toString() {
    return super.getlabel() + " nation: " + this.nation + "\t" + "year: "
        + this.year + "\t" + "score: " + this.score + "\n";
  }

  @Override
  public final String tellclass() {
    return "Movie";
  }
}
