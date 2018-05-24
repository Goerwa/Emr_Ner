package factory;

import vertex.Actor;
import vertex.Computer;
import vertex.Vertex;

public class ActorVertexFactory extends VertexFactory{
	
	public ActorVertexFactory() {
		
	}
	@Override
	public Vertex createVertex(String label, String[] args) {
		Actor w = new Actor(label);
		w.fillVertexInfo(args);
		return w;
	}
}
