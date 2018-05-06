package factory;

import vertex.Computer;
import vertex.Router;
import vertex.Vertex;

public class RouterVertexFactory extends VertexFactory{
	
	public RouterVertexFactory() {
		
	}
	@Override
	public Vertex createVertex(String label, String[] args) {
		Router w = new Router(label);
		w.fillVertexInfo(args);
		return w;
	}
}
