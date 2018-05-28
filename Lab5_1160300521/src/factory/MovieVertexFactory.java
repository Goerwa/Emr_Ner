package factory;

import vertex.Computer;
import vertex.Movie;
import vertex.Vertex;

public class MovieVertexFactory extends VertexFactory {

  public MovieVertexFactory() {

  }

  @Override
  public Vertex createVertex(String label, String[] args) {
    Movie w = new Movie(label);
    w.fillVertexInfo(args);
    return w;
  }
}
