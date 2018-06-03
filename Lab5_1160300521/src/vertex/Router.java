package vertex;
/**
 * router used in nettopology.
 * @author goerwa
 *
 */
public class Router extends Vertex {
  // Abstraction function:
  // AF(Vertex) = {vertex|label ！= null &&
  // ip = [0,255].[0,255].[0,255].[0,255]}.
  //
  // Representation invariant:
  // label cannot be null,IP address divided into
  // four parts with ".", the range of each part is
  // [0,255].
  //
  // Safety from rep exposure:
  // All fileds are private
  // the get methods can prevent sharing data with clients.
  /** ip of router. */
  private String ip;
  /**
   * to construct.
   * @param label
   * name of router
   * @param i
   * ip of router
   */
  public Router(final String label, final String i) {
    super(label);
    this.ip = i;
    //checkRep();
  }
  /**
   * to construct.
   * @param label
   * name of router
   */
  public Router(final String label) {
    super(label);
    this.ip = "192.168.1.1";
    //checkRep();
  }
  /**
   * to get ip.
   * @return
   * return ip
   */
  public final String getip() {
    return this.ip;
  }

  @Override
  public final void fillVertexInfo(final String[] args) {
    //assert args[2] != null;
    this.ip = args[2];
    //checkRep();
  }
  /**
   * checkrep to assert ip is right.
   */
  private void checkRep() {
    String[] str = this.ip.split("\\.");
    assert (str.length == 4);
    for (int i = 0; i < 4; i++) {
      int a = Integer.parseInt(str[i]);
      assert (a >= 0 && a <= 255);
    }
  }

  @Override
  public boolean equals(final Object obj) {
    if (obj == null) {
      return false;
    }
    Vertex v = (Vertex) obj;
    if (v.getlabel().toLowerCase().equals(this.getlabel().toLowerCase())
        && v.tellclass().equals("Router")) {
      return true;
    }
    return false;

  }

  @Override
  public  String tellclass() {
    return "Router";
  }

  @Override
  public int hashCode() {
    int result = 17;
    result = 31 * result + this.getlabel().hashCode() + this.getip().hashCode();
    return result;
  }
}
