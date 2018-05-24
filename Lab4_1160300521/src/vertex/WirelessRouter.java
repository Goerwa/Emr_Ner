package vertex;

public class WirelessRouter extends Router{
	// Abstraction function:
    // AF(Vertex) = {vertex|label ！= null && ip = [0,255].[0,255].[0,255].[0,255]}.
	//
    // Representation invariant:
    // label cannot be null,IP address divided into four parts with ".", the range of each part is [0,255].
	//
    // Safety from rep exposure:
	// All fileds are private
	// the get methods can prevent sharing data with clients.
	public WirelessRouter(String label,String ip) {
		super(label,ip);
	}
	
	public WirelessRouter(String label) {
		super(label);
	}
	public String getip() {
		return super.getip();
	}
	
	
	@Override public boolean equals(Object obj) {
		Vertex v = (Vertex) obj;
		if(v.getlabel().toLowerCase().equals(super.getlabel().toLowerCase()) &&
				v.tellclass().equals("WirelessRouter")) 
			return true;
		else {
			return false;
		}
	}
	
	@Override
	public
	String tellclass() {
		return "WirelessRouter";
	}

}
