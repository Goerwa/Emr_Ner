package edge;

import java.util.Set;

import vertex.Actor;
import vertex.Movie;
import vertex.Vertex;

public class MovieActorRelation  extends DirectedEdge {
	public MovieActorRelation(Vertex source, Vertex target, Double weight) {
		super(source, target, weight);
		checkRep();
	}
	
	public MovieActorRelation(String label,Vertex source, Vertex target, Double weight) {
		super(label, source, target, weight);
		checkRep();
	}
	
	private void checkRep() {
		assert !this.getsource().equals(this.gettarget());
		if(this.getsource().tellclass().equals("Movie"))
			assert this.gettarget().tellclass().equals("Actor");
		else if(this.getsource().tellclass().equals("Actor"))
			assert this.gettarget().tellclass().equals("Movie");
		else
			assert this.getsource().tellclass().equals("Actor");
		
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
	@Override
	Set<Vertex> sourceVertices() {
		// TODO Auto-generated method stub
		return null;
	}
}
