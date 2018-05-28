package factory;

import vertex.Router;
import vertex.Server;
import vertex.Vertex;

public class ServerVertexFactory extends VertexFactory {

  public ServerVertexFactory() {

  }

  @Override
  public Vertex createVertex(String label, String[] args) {
    Server w = new Server(label);
    w.fillVertexInfo(args);
    return w;
  }
}
