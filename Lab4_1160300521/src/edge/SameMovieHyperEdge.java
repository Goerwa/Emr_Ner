package edge;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import vertex.Vertex;

public class SameMovieHyperEdge extends Edge{
	 // Abstraction function:
    // AF(vertices,Edge) = {vertices,edge|vertices != null && size of vertices > 1 && type of vertices = Actor}.
    //
    // Representation invariant:
    // all vertices are not null and size of vertices are over 1
    // type of vertices is Actor
    // Safety from rep exposure:
    // All fileds are private
 	// All return value types are mutable, create new variables to prevent sharing with clients.
    //
    
	public SameMovieHyperEdge(String label,double weight) {
		super(label,weight);
	}
	
	private void checkRep() {
		assert this.getvers().size() > 1;
		for(Vertex v:super.getvers()) {
			assert (v.tellclass().equals("Actor"));
		}
 	}
	@Override
	public boolean addVertices(List<Vertex> vertices) {
		super.addvertices(vertices);
		checkRep();
		return true;
	}
	@Override
	Set<Vertex> sourceVertices() {
		Set<Vertex> r = new HashSet<Vertex>();
		r.addAll(super.getvers());
		return r;
	}
	@Override
	Set<Vertex> targetVertices() {
		Set<Vertex> r = new HashSet<Vertex>();
		r.addAll(super.getvers());
		return r;
	}
	
	@Override
	public boolean equals(Object e) {
		Edge edge = (Edge) e;
		if(edge.tellclass().equals(this.tellclass())) {
			if(edge.getlabel().equals(this.getlabel()))
				return true;
			else
				return false;
		}else {
			return false;
		}
	}
	
	public String tellcalss() {
		return "SameMovieHyperEdge";
	}
}
