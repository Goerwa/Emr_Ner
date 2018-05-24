package edge;

import java.util.Set;

import vertex.Vertex;

public class FriendTie extends DirectedEdge{
	// Abstraction function:
    // AF(Edge) = {edge|label != null && source ！= null && target ！= null && weight > 0 && weight <= 1 && type of source, target = person}.
	//
    // Representation invariant:
	// label cannot be null
    // Neither source nor target can be null
	// weight is more than or equals 0
	// type of two vertices is person
	//
    // Safety from rep exposure:
    // All fileds are private
	// the get methods can prevent sharing data with clients.
	public FriendTie(String label, double weight) {
		super(label,weight);
	}
	public FriendTie(Vertex src,Vertex arg, double weight) {
		super(src,arg, weight);
		checkRep();
	}
	public FriendTie(String label, Vertex src,Vertex arg, double weight) {
		super(label, src,arg, weight);
		checkRep();
	}
	private void checkRep() {
		//System.out.println(this.getweight());
		assert this.getweight() >= 0.0 && this.getweight() <= 1.0;
		assert !super.getsource().equals(super.gettarget());
		assert this.getsource().tellclass().equals("Person");
		assert this.gettarget().tellclass().equals("Person");
	}
	@Override
	public String tellclass() {
		return "FriendTie";
	}
	@Override
	public boolean equals(Object e) {
		Edge edge = (Edge) e;
		if(edge.tellclass().equals(this.tellclass())) {
			if(edge.getsource().equals(this.getsource()) && edge.gettarget().equals(this.gettarget())
					) {
				return true;
			}else {
				return false;
			}
		}else{
			return false;
		}
	}
}
