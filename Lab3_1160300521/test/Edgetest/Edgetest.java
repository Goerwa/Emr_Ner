package Edgetest;

import static org.junit.Assert.*;

import org.junit.Test;

import edge.WordNeighborhood;
import vertex.Word;

public class Edgetest {
	
	Word v1 = new Word("v1");
	Word v2 = new Word("v2");
	Word v3 = new Word("v3");
	WordNeighborhood w1 = new WordNeighborhood(v1,v2,1.0);
	WordNeighborhood w2 = new WordNeighborhood(v1,v2,2.0);
	
	@Test
	public void equals() {
		assertTrue("expected True", w1.equals(w2));

	}

}
