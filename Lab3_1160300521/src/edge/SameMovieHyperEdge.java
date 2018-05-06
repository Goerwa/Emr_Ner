package edge;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import vertex.Vertex;

public class SameMovieHyperEdge extends Edge{
	private List<Vertex> actors = new ArrayList<>();
	
	
	public List<Vertex> getactors() {
		return new ArrayList<Vertex>(this.actors);
	}
	
	public SameMovieHyperEdge(String label,List<Vertex> actors,double weight) {
		super(label,weight);
		this.actors = actors;
	}

	@Override
	Set<Vertex> sourceVertices() {
		Set<Vertex> r = new HashSet<Vertex>();
		r.addAll((actors));
		return r;
	}

	@Override
	public boolean addVertices(List<Vertex> vertices) {
		super.addvers(vertices.get(0), vertices.get(1));
		actors.add(vertices.get(0));
		actors.add(vertices.get(1));
		return true;
	}

	@Override
	Set<Vertex> targetVertices() {
		Set<Vertex> r = new HashSet<Vertex>();
		r.addAll((actors));
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
		return "SameMovieHyperEdge";
	}
}
