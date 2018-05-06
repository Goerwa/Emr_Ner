package edge;

import java.util.Set;

import vertex.Vertex;

public class FriendTie extends DirectedEdge{
	public FriendTie(String label,Vertex source, Vertex target, double weight) {
		super(label,source,target,weight);
	}
	
	public FriendTie(Vertex source, Vertex target, double weight) {
		super(source,target,weight);
	}
	@Override
	public String tellclass() {
		return "FriendTie";
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
