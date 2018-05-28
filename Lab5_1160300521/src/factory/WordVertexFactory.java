package factory;

import vertex.Vertex;
import vertex.Word;

public class WordVertexFactory extends VertexFactory {

  public WordVertexFactory() {

  }

  @Override
  public Vertex createVertex(String label, String[] args) {
    // System.out.println(args[1]);
    if (!(args[1].equals("Word") || args[1].equals("word"))) {
      System.out.println("Type wrong");
    }
    Word w = new Word(label);
    // w.fillVertexInfo(args);
    return w;
  }

}
