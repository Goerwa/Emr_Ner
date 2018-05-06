package edge;

import java.util.Set;

import vertex.Director;
import vertex.Movie;
import vertex.Vertex;

public class MovieDirectorRelation extends UndirectedEdge {
	public MovieDirectorRelation(Vertex source, Vertex target, Double weight) {
		super(source, target, weight);
		checkRep();
	}
	
	public MovieDirectorRelation(String label,Vertex source, Vertex target, Double weight) {
		super(label,source, target, weight);
		checkRep();
	}
	
	private void checkRep() {
		assert !this.getsource().equals(this.gettarget());
		if(this.getsource().tellclass().equals("Movie"))
			assert this.gettarget().tellclass().equals("Director");
		else if(this.getsource().tellclass().equals("Director"))
			assert this.gettarget().tellclass().equals("Movie");
		else
			assert this.getsource().tellclass().equals("Movie");
		
	}
	
	@Override
	public boolean equals(Object e) {
		Edge edge = (Edge) e;
		if(edge.tellclass().equals(this.tellclass())) {
			if((edge.getsource().equals(this.getsource()) && (edge.gettarget().equals(this.gettarget())))) {
				return true;
			}else {
				return false;
			}
		}else {
			return false;
		}
	}

}
