package factory;

import vertex.Computer;
import vertex.Director;
import vertex.Vertex;

public class DirectorVertexFactory extends VertexFactory {

  public DirectorVertexFactory() {

  }

  @Override
  public Vertex createVertex(String label, String[] args) {
    Director w = new Director(label);
    w.fillVertexInfo(args);
    return w;
  }
}
