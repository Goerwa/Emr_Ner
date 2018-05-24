package Edgetest;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import edge.FriendTie;
import edge.WordNeighborhood;
import vertex.Person;
import vertex.Vertex;
import vertex.Word;

public class GraphPoetEdgetest {
	// Testing strategy 
    //
    // equasl():
    //	source and target = same, not same 
	//	type of vertex = same, not same
	//  
	
	Word v1 = new Word("v1");
	Word v2 = new Word("v2");
	Word v3 = new Word("v3");
	Word v4 = new Word("v4");
	WordNeighborhood w1 = new WordNeighborhood("w1",v1,v2,1.0);
	WordNeighborhood w2 = new WordNeighborhood("w2",v1,v2,2.0);
	Person p1 = new Person("p1");
	Person p2 = new Person("p2");
	FriendTie ft = new FriendTie("ft",p1,p2,1.0);
	
	//	type of vertex = same
	//	source and target = same
	@Test
	public void equals1() {
		assertTrue("expected True", w1.equals(w2));

	}
	
	//	type of vertex = not same
	//	source and target = not same
	@Test
	public void equals2() {
		assertFalse("expected True", w1.equals(ft));

	}
	// Testing strategy 
    //
    // checkRep():
    //	weight = right,wrong
	//	type of vertex = right,wrong
	//  size of vertice = right,wrong
	//  source and target = same, not same
	
	//	weight = wrong
	@Test(expected = AssertionError.class)
	public void testcheckrep1() {
		Word v11 = new Word("v11");
		Word v22 = new Word("v22");
		WordNeighborhood w11 = new WordNeighborhood("w11",v11,v22,-1.0);
	}
	
	//	type of vertex = wrong
	@Test(expected = AssertionError.class)
	public void testcheckrep2() {
		Word v11 = new Word("v11");
		Person v22 = new Person("v22");
		WordNeighborhood w11 = new WordNeighborhood("w11",v11,v22,-1.0);
	}
	//	size of vertice = wrong
	@Test
	public void testcheckrep3() {
		Word v11 = new Word("v11");
		Word v22 = new Word("v22");
		WordNeighborhood w11 = new WordNeighborhood("w11",1.0);
		List<Vertex> l = new ArrayList<>();
		l.add(v11);
		l.add(v22);
		w11.addvertices(l);
		assertTrue("expected true", w11.getvers().size() == 2);
	}
	//	source and target = same
	@Test(expected = AssertionError.class)
	public void testcheckrep4() {
		Word v11 = new Word("v11");
		Word v22 = new Word("v22");
		WordNeighborhood w11 = new WordNeighborhood("w11",v11,v11,1.0);
		//System.out.println(w11.getsource().equals(w11.gettarget()));
	}

}
