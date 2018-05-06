package factory;

import vertex.Vertex;

abstract class VertexFactory {
	public abstract Vertex createVertex(String label, String[] args);
}
