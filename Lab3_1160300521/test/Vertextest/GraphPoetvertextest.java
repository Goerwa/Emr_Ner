package Vertextest;

import static org.junit.Assert.*;

import org.junit.Test;

import vertex.Person;
import vertex.Word;

public class GraphPoetvertextest {
	
	Word w1 = new Word("w1");
	Word w3 = new Word("w1");
	Word w2 = new Word("w2");
	Person p1 = new Person("w1");
	Person p2 = new Person("w111");
	// Testing strategy 
    //
    // equasl():
    //	label of vertex = same, not same 
	//	type of vertex = same, not same 
	
	//	label of vertex = same
	@Test
	public void testequals1() {
		assertTrue("expected true",w1.equals(w3));
	}
	//	label of vertex = not same 
	@Test
	public void testequals2() {
		assertFalse("expected false",w1.equals(w2));
	}

	//	type of vertex = not same 
	//	label of vertex = not same
	@Test
	public void testequals3() {
		assertFalse("expected false",w1.equals(p1));
	}
	//	type of vertex = same, not same 
	//	label of vertex = same
	@Test
	public void testequals4() {
		assertFalse("expected false",w1.equals(p2));
	}
}
