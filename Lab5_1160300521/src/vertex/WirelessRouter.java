package vertex;
/**
 * wirelessrouter used in networktopology.
 * @author goerwa
 *
 */
public class WirelessRouter extends Router {
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
  /**
   * to construct.
   * @param label
   * name of router
   * @param i
   * ip of router
   */
  public WirelessRouter(final String label, final String i) {
    super(label, i);
  }
  /**
   * to construct.
   * @param label
   * name of router
   */
  public WirelessRouter(final String label) {
    super(label);
  }

  @Override
  public final boolean equals(final Object obj) {
    if (obj == null) {
      return false;
    }
    Vertex v = (Vertex) obj;
    if (v.getlabel().toLowerCase().equals(super.getlabel().toLowerCase())
        && v.tellclass().equals("WirelessRouter")) {
      return true;
    }
    return false;
  }

  @Override
  public final String tellclass() {
    return "WirelessRouter";
  }

}
