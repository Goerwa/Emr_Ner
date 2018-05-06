package edge;

import java.util.Set;

import vertex.Vertex;

public class ForwardTie extends DirectedEdge {

	public ForwardTie(Vertex source, Vertex target, double weight) {
		super(source, target, weight);
		// TODO Auto-generated constructor stub
	}
	
	public ForwardTie(String label,Vertex source, Vertex target, double weight) {
		super(label,source, target, weight);
		// TODO Auto-generated constructor stub
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
	public String tellclass() {
		return "ForwardTie";
	}
	

}
