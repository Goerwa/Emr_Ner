package Edgetest;

import static org.junit.Assert.*;

import org.junit.Test;

import Exception.ExceptionofInput;
import Exception.ExceptionofUnproperEdge;
import edge.CommentTie;
import edge.Edge;
import edge.ForwardTie;
import edge.FriendTie;
import edge.WordNeighborhood;
import graph.SocialNetwork;
import vertex.Person;
import vertex.Word;

public class Socialedgetest {

	Person v1 = new Person("v1");
	Person v2 = new Person("v2");
	Person v3 = new Person("v3");
	FriendTie e1 = new FriendTie("e1",v1,v2,0.5);
	FriendTie e2 = new FriendTie("e2",v1,v2,0.5);
	CommentTie e3 = new CommentTie("e3",v1,v2,0.5);
	ForwardTie e4 = new ForwardTie("e4",v1,v2,0.5);
	//	Testing strategy 
    //
    // equasl():
    //	source and target = same, not same 
	//	type of vertex = same, not same
	//
	@Test
	public void equals() {
		
		assertTrue("expected True", e1.equals(e2));

	}
	
	@Test
	public void equalsdiff1() {
		assertFalse("expected False", e1.equals(e3));

	}
	
	@Test
	public void equalsdiff2() {
		assertFalse("expected False", e4.equals(e3));

	}
	@Test
	public void equalsdiff3() {
		ForwardTie e5 = new ForwardTie(v1,v2,0.8);
		assertTrue("expected False", e4.equals(e5));

	}
	
	@Test(expected = ExceptionofUnproperEdge.class)
	public void addsameedges1() throws ExceptionofInput, ExceptionofUnproperEdge {
		SocialNetwork graph = new SocialNetwork();
		graph.addVertex(v1);
		graph.addVertex(v2); 
		//System.out.println(e1.getweight() > 0.0 && e1.getweight() <= 1.0);
		graph.addEdge(e1);
		assertTrue("expected True", graph.addEdge(e1));
		assertEquals("expected size = 1", 1, graph.edges().size());

	}
	
	@Test
	public void adddifferentedges1() throws ExceptionofInput, ExceptionofUnproperEdge {
		SocialNetwork graph = new SocialNetwork();
		graph.addVertex(v1);
		graph.addVertex(v2);
		graph.addEdge(e1);
		assertTrue("expected True", graph.addEdge(e3));
		assertEquals("expected size = 2", 2, graph.edges().size());

	}
	
	@Test(expected = ExceptionofUnproperEdge.class)
	public void adddifferentedges2() throws ExceptionofInput, ExceptionofUnproperEdge {
		SocialNetwork graph = new SocialNetwork();
		graph.addVertex(v1);
		graph.addVertex(v2);
		graph.addEdge(e1);
		graph.addEdge(e4);
		ForwardTie e5 = new ForwardTie("e5",v1,v2,0.8);
		graph.addEdge(e5);
		assertTrue("expected True", graph.addEdge(e3));
		assertEquals("expected size = 3", 3, graph.edges().size());

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
		FriendTie w11 = new FriendTie("w11",v1,v2,1.5);
	}
	//type of vertex = wrong
	@Test(expected = AssertionError.class)
	public void testcheckrep2() {
		Word w = new Word("w11");
		FriendTie w11 = new FriendTie("w11",w,v2,0.8);
	}
	//size of vertex = right
	@Test
	public void testcheckrep3() {
		FriendTie w11 = new FriendTie("w11",v1,v2,0.5);
		assertEquals("expected size = 2", 2, w11.getvers().size());
	}
	//source and target = same
	@Test(expected = AssertionError.class)
	public void testcheckrep4() {
		Word w = new Word("w11");
		FriendTie w11 = new FriendTie("w11",v1,v1,0.8);
	}

	
	
}
