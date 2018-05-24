package factory;

import java.util.List;

import edge.CommentTie;
import edge.Edge;
import edge.FriendTie;
import vertex.Vertex;

public class CommentTieFactory extends EdgeFactory{
	
	public CommentTieFactory() {
		
	}
	@Override
	public Edge createEdge(String label, List<Vertex> vertices, double weight) {
		CommentTie e = new CommentTie(label,vertices.get(0),vertices.get(1),weight);
		return e;
	}

}
