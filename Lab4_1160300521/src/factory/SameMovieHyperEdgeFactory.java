package factory;

import java.util.List;

import edge.Edge;
import edge.FriendTie;
import edge.SameMovieHyperEdge;
import vertex.Router;
import vertex.Vertex;

public class SameMovieHyperEdgeFactory extends EdgeFactory{
	
	public SameMovieHyperEdgeFactory() {
		
	}
	@Override
	public Edge createEdge(String label, List<Vertex> vertices, double weight) {
		SameMovieHyperEdge e = new SameMovieHyperEdge(label,weight);
		e.addVertices(vertices);
		return e;
	}
}
