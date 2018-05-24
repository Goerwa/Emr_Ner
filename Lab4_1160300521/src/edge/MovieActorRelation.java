package edge;

import java.util.Set;

import vertex.Actor;
import vertex.Movie;
import vertex.Vertex;

public class MovieActorRelation  extends UndirectedEdge {
	// Abstraction function:
    // AF(Edge) = {edge|label != null && source ！= null && target ！= null && weight >= 0 && type of soure, target = movie and actor}.
	//
    // Representation invariant:
	// label cannot be null
    // Neither source nor target can be null
	// weight is more than or equals 0
	// two vertices include actor and movie
	//
    // Safety from rep exposure:
    // All fileds are private
	// the get methods can prevent sharing data with clients.
	public MovieActorRelation(String label, Double weight) {
		super(label, weight);
		checkRep();  
	}
	public MovieActorRelation(String label,Vertex src, Vertex arg, Double weight) {
		super(label,src, arg, weight);
		checkRep();
	}
	private void checkRep() {
		assert !this.getsource().equals(this.gettarget());
		assert this.getweight() > 0;
		if(this.getsource().tellclass().equals("Movie"))
			assert this.gettarget().tellclass().equals("Actor");
		else if(this.getsource().tellclass().equals("Actor"))
			assert this.gettarget().tellclass().equals("Movie");
		else
			assert this.getsource().tellclass().equals("Actor");
	}
}
