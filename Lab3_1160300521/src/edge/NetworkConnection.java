package edge;

import java.util.Set;

import vertex.Vertex;

public class NetworkConnection extends UndirectedEdge {

	public NetworkConnection(Vertex source, Vertex target, Double weight) {
		super(source, target, weight);
		checkRep();
	}
	
	public NetworkConnection(String label,Vertex source, Vertex target, Double weight) {
		super(label,source, target, weight);
		checkRep();
	}
	
	private void checkRep() {
		assert !super.getsource().equals(super.gettarget());
		assert !(super.getsource().tellclass().equals("Computer") &&
				super.gettarget().tellclass().equals("Computer"));
		assert !(super.getsource().tellclass().equals("Server") &&
				super.gettarget().tellclass().equals("Server"));
		assert (super.getweight() > 0);
	}
	
	@Override
	public String tellclass() {
		return "NetworkConnection";
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
