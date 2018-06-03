package factory;

import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Set;
import Exception.ExceptionofDirection;
import Exception.ExceptionofDirectionTest;
import Exception.ExceptionofInput;
import Exception.ExceptionofUndirection;
import Exception.ExceptionofUnproperEdge;
import edge.Edge;
import graph.Graph;
import vertex.Vertex;

abstract class GraphFactory {
  abstract public Graph<Vertex, Edge> createGraph(String filePath) throws ExceptionofInput,
      ExceptionofUnproperEdge, ExceptionofUndirection, ExceptionofDirection, FileNotFoundException;
}
