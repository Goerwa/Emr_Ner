package factory;

import vertex.Vertex;
import vertex.Word;

public class WordVertexFactory extends VertexFactory{
	
	public WordVertexFactory() {
		
	}
	@Override
	public Vertex createVertex(String label, String[] args) {
		Word w = new Word(label);
		//w.fillVertexInfo(args);
		return w;
	}

}
