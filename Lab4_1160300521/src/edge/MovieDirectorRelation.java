package edge;

import java.util.Set;

import vertex.Director;
import vertex.Movie;
import vertex.Vertex;

public class MovieDirectorRelation extends UndirectedEdge {
	// Abstraction function:
    // AF(Edge) = {edge|label != null && source ！= null && target ！= null && weight >= 0 && type of source, target = movie and director}.
	//
    // Representation invariant:
	// label cannot be null
    // Neither source nor target can be null
	// weight is more than or equals 0
	// type of two vertices include movie and director
	//
    // Safety from rep exposure:
    // All fileds are private
	// the get methods can prevent sharing data with clients.
	public MovieDirectorRelation(String label, Double weight) {
		super(label, weight);
		checkRep();
	}
	public MovieDirectorRelation(String label,Vertex src, Vertex arg, Double weight) {
		super(label,src, arg, weight);
		checkRep();
	}
	private void checkRep() {
		//System.out.println(this.getsource().tellclass() + this.getsource().getlabel());
		//System.out.println(this.gettarget().tellclass() + this.gettarget().getlabel());
		assert !this.getsource().equals(this.gettarget()); 
		if(this.getsource().tellclass().equals("Movie"))
			assert this.gettarget().tellclass().equals("Director");
		else if(this.getsource().tellclass().equals("Director"))
			assert this.gettarget().tellclass().equals("Movie");
		else {
			assert this.gettarget().tellclass().equals("Movie");
			assert this.getsource().tellclass().equals("Movie");
		}
		
	}
	
}
