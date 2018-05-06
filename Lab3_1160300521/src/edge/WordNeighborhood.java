package edge;

import java.util.Set;

import vertex.Vertex;

public class WordNeighborhood extends DirectedEdge {
	
	public WordNeighborhood(String label, Vertex source, Vertex target, Double weight) {
		super(label, source,target,weight);
	}
	
	public WordNeighborhood(Vertex source, Vertex target, Double weight) {
		super(source,target,weight);
	}
	@Override
	public String tellcalss() {
		return "WordNeighborhood";
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
