package factory;

import vertex.Person;
import vertex.Router;
import vertex.Vertex;

public class PersonVertexFactory extends VertexFactory {

  public PersonVertexFactory() {

  }

  @Override
  public Vertex createVertex(String label, String[] args) {
    Person w = new Person(label);
    w.fillVertexInfo(args);
    return w;
  }
}
