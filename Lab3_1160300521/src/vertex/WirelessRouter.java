package vertex;

public class WirelessRouter extends Router{

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
		//System.out.println("not equals");
			return false;
		}
	}
	
	
	@Override
	public
	String tellclass() {
		// TODO Auto-generated method stub
		return "WirelessRouter";
	}

}
