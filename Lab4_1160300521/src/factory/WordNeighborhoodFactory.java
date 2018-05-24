package factory;

import java.util.List;

import edge.Edge;
import edge.WordNeighborhood;
import vertex.Vertex;

public class WordNeighborhoodFactory extends EdgeFactory{
	
	public WordNeighborhoodFactory() {
		
	}
	@Override
	public Edge createEdge(String label, List<Vertex> vertices, double weight) {
		WordNeighborhood e = new WordNeighborhood(label,vertices.get(0),vertices.get(1),weight);
		return e;
	}

}
