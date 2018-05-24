package edge;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import vertex.Vertex;

public class UndirectedEdge extends Edge{
	// Abstraction function:
    // AF(Edge) = {edge|label != null && source ！= null && target ！= null}.
	//
    // Representation invariant:
	// label cannot be null
    // Neither source nor target can be null 
	//
    // Safety from rep exposure:
    // All fileds are private
	// the get methods can prevent sharing data with clients.
	public UndirectedEdge(String label, Double weight) {
		super(label, weight);
	}
	public UndirectedEdge(String label,Vertex src, Vertex arg, Double weight) {
		super(label,src, arg, weight);
		checkRep();
	}
	
	private void checkRep() {
		assert this.getlabel() != null;
		assert !(this.getsource().equals(this.gettarget()));
		assert this.getvers().size() == 2;
	}
	@Override
	Set<Vertex> sourceVertices() {
		Set<Vertex> r = new HashSet<>();
		Vertex v = super.getsource();
		r.add(v);
		checkRep();
		return r;
	}
	@Override
	Set<Vertex> targetVertices() {
		Set<Vertex> r = new HashSet<>();
		Vertex v = super.gettarget();
		r.add(v);
		checkRep();
		return r;
	}
	@Override
	public boolean addVertices(List<Vertex> vertices) {
		super.addvers(vertices.get(0), vertices.get(1));
		return true; 
	}
	
	@Override
	public boolean equals(Object e) {
		Edge edge = (Edge) e;
		if(edge.tellclass().equals(this.tellclass())) {
			if((edge.getsource().equals(this.getsource()) && (edge.gettarget().equals(this.gettarget())))) {
				return true;
			}
			else if((edge.getsource().equals(this.gettarget()) && (edge.gettarget().equals(this.getsource())))) {
				return true;
			}else {
				return false;
			}
		}else {
			return false;
		}
	}
	
	public String tellcalss() {
		return "UndirectedEdge";
	}

}
