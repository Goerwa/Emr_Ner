package factory;

import java.util.List;

import edge.Edge;
import edge.FriendTie;
import edge.WordNeighborhood;
import vertex.Vertex;

public class FriendTieFactory extends EdgeFactory{

	public FriendTieFactory() {
		
	}

	@Override
	public Edge createEdge(String label, List<Vertex> vertices, double weight) {
		FriendTie e = new FriendTie(label,vertices.get(0),vertices.get(1),weight);
		return e;
	}
	
	
}
