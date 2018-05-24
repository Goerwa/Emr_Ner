package edge;

import java.util.Set;

import vertex.Vertex;

public class NetworkConnection extends UndirectedEdge {
	// Abstraction function:
    // AF(Edge) = {edge|label != null && source ！= null && target ！= null && weight >= 0 && type of source, target != computer,server}.
	//
    // Representation invariant:
	// label cannot be null
    // Neither source nor target can be null
	// weight is more than or equals 0
	// type of two vertices cannot be both computer and server
	//
    // Safety from rep exposure:
    // All fileds are private
	// the get methods can prevent sharing data with clients.
	public NetworkConnection(String label, Double weight) {
		super(label,weight);
		checkRep();
	}
	public NetworkConnection(String label,Vertex src, Vertex arg, Double weight) {
		super(label,src, arg, weight);
		checkRep();
	}
	private void checkRep() {
		assert !super.getsource().equals(super.gettarget()); 
		assert this.getweight() > 0;
		assert (this.getsource().tellclass().equals("Computer") || this.getsource().tellclass().equals("Router") 
				|| this.getsource().tellclass().equals("Server"));
		assert (this.gettarget().tellclass().equals("Computer") || this.gettarget().tellclass().equals("Router") 
				|| this.gettarget().tellclass().equals("Server"));
		assert !(super.getsource().tellclass().equals("Computer") &&
				super.gettarget().tellclass().equals("Computer"));
		assert !(super.getsource().tellclass().equals("Server") &&
				super.gettarget().tellclass().equals("Server"));
		assert (super.getweight() > 0);
	}
	@Override
	public boolean equals(Object e) {
		Edge edge = (Edge) e;
		if(edge.tellclass().equals(this.tellclass())) {
			if((edge.getsource().equals(this.getsource()) && (edge.gettarget().equals(this.gettarget())))) {
				//System.out.println(edge.getsource().getlabel());
				//System.out.println(edge.gettarget().getlabel());
				System.out.println(edge.getlabel() + " equals "+this.getlabel());
				return true;
			}
			else if((edge.getsource().equals(this.gettarget()) && (edge.gettarget().equals(this.getsource())))) {
				//System.out.println(edge.getsource().getlabel());
				//System.out.println(edge.gettarget().getlabel());
				System.out.println(edge.getlabel() + " equals "+this.getlabel());
				return true;
			}else {
				return false;
			}
		}else {
			return false;
		}
	}
	@Override
	public String tellclass() {
		return "NetworkConnection";
	}
}
