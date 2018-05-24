package factory;

import java.util.List;

import edge.Edge;
import edge.FriendTie;
import edge.NetworkConnection;
import vertex.Vertex;

public class NetworkConnectionFactory extends EdgeFactory{
	
	public NetworkConnectionFactory() {
		
	}
	@Override
	public Edge createEdge(String label, List<Vertex> vertices, double weight) {
		NetworkConnection e = new NetworkConnection(label,vertices.get(0),vertices.get(1),weight);
		return e;
	}
}
