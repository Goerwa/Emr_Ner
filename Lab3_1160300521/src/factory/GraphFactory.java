package factory;

import edge.Edge;
import graph.Graph;
import vertex.Vertex;

abstract class GraphFactory {
	abstract public Graph<Vertex, Edge> createGraph(String filePath);
}
