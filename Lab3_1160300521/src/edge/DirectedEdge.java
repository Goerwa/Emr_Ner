package edge;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import vertex.Vertex;

public class DirectedEdge extends Edge{

	public DirectedEdge(Vertex source, Vertex target, Double weight) {
		super(source, target, weight);
		// TODO Auto-generated constructor stub
	}
	
	public DirectedEdge(String label,Vertex source, Vertex target, Double weight) {
		super(label,source, target, weight);
		// TODO Auto-generated constructor stub
	}
	
	public DirectedEdge(String label, Double weight) {
		super(label, weight);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	Set<Vertex> sourceVertices() {
		Set<Vertex> r = new HashSet<>();
		Vertex v = super.getsource();
		r.add(v);
		return r;
	}

	@Override
	public boolean addVertices(List<Vertex> vertices) {
		super.addvers(vertices.get(0), vertices.get(1));
		return true;
	}

	@Override
	Set<Vertex> targetVertices() {
		Set<Vertex> r = new HashSet<>();
		Vertex v = super.gettarget();
		r.add(v);
		return r;
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
	
	public String tellcalss() {
		return "DirectedEdge";
	}
	
}
