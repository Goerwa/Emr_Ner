package edge;

import java.util.Set;

import vertex.Vertex;

public class WordNeighborhood extends DirectedEdge {
	// Abstraction function:
    // AF(Edge) = {edge|label != null && source ！= null && target ！= null && weight >= 0}.
	//
    // Representation invariant:
	// label cannot be null
    // Neither source nor target can be null
	// weight is more than or equals 0
	//
    // Safety from rep exposure:
    // All fileds are private
	// the get methods can prevent sharing data with clients.
	public WordNeighborhood(String label, Double weight) {
		super(label,weight);
	}
	public WordNeighborhood(String label,Vertex src,Vertex arg, Double weight) {
		super(label,src,arg,weight);
		checkRep();
	}
	
	private void checkRep() {
		assert this.getvers().size() == 2;
		assert this.getweight() >= 0;
		assert !(this.gettarget().equals(this.getsource())); 
		assert this.getsource().tellclass().equals("Word");
		assert this.gettarget().tellclass().equals("Word");
	}
	@Override
	public String tellcalss() {
		return "WordNeighborhood";
	}
		
}
