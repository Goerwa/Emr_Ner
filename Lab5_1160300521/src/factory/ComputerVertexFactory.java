package factory;

import vertex.Computer;
import vertex.Vertex;
import vertex.Word;

public class ComputerVertexFactory extends VertexFactory {


  public ComputerVertexFactory() {

  }

  @Override
  public Vertex createVertex(String label, String[] args) {
    Computer w = new Computer(label);
    w.fillVertexInfo(args);
    return w;
  }

}
