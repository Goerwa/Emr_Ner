package factory;

import java.util.List;

import edge.Edge;
import edge.FriendTie;
import edge.MovieActorRelation;
import vertex.Vertex;

public class MovieActorRelationFactory extends EdgeFactory{
	public MovieActorRelationFactory() {
		
	}
	
	@Override
	public Edge createEdge(String label, List<Vertex> vertices, double weight) {
		MovieActorRelation e = new MovieActorRelation(label,vertices.get(0),vertices.get(1),weight);
		return e;
	}
}
