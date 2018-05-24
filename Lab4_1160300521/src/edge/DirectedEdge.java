package edge;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import vertex.Vertex;

public abstract class DirectedEdge extends Edge{
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

	public DirectedEdge(String label, Double weight) {
		super(label, weight);
	}
	public DirectedEdge(Vertex src,Vertex arg, Double weight) {
		super(src,arg, weight);
	}
	public DirectedEdge(String label, Vertex src,Vertex arg, double weight) {
		super(label, src,arg, weight);
		checkRep();
	}
	
	private void checkRep() {
		assert this.getlabel() != null;
		assert this.getweight() >= 0;
		assert this.getvers().size() == 2;
	}
	@Override
	Set<Vertex> sourceVertices() {
		Set<Vertex> r = new HashSet<>();
		Vertex v = super.getsource();
		r.add(v);
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
			}else {
				return false;
			}
		}else{
			return false;
		}
	}
	
	public String tellcalss() {
		return "DirectedEdge";
	}
	
}
