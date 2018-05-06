package Edgetest;

import static org.junit.Assert.*;

import org.junit.Test;

import edge.CommentTie;
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
	FriendTie e1 = new FriendTie(v1,v2,0.5);
	FriendTie e2 = new FriendTie(v1,v2,0.5);
	CommentTie e3 = new CommentTie(v1,v2,0.5);
	ForwardTie e4 = new ForwardTie(v1,v2,0.5);
	@Test
	public void equals() {
		
		System.out.println(e1.tellclass());
		System.out.println(e2.tellclass());
		assertTrue("expected True", e1.equals(e2));

	}
	
	@Test
	public void equalsdiff1() {
		System.out.println(e1.tellclass());
		System.out.println(e3.tellclass());
		assertFalse("expected False", e1.equals(e3));

	}
	
	@Test
	public void equalsdiff2() {
		System.out.println(e3.tellclass());
		System.out.println(e4.tellclass());
		assertFalse("expected False", e4.equals(e3));

	}
	
	@Test
	public void addsameedges1() {
		SocialNetwork graph = new SocialNetwork();
		graph.addVertex(v1);
		graph.addVertex(v2);
		graph.addEdge(e1);
		assertTrue("expected True", graph.addEdge(e1));
		assertEquals("expected size = 1", 1, graph.edges().size());

	}
	
	@Test
	public void adddifferentedges1() {
		SocialNetwork graph = new SocialNetwork();
		graph.addVertex(v1);
		graph.addVertex(v2);
		graph.addEdge(e1);
		assertTrue("expected True", graph.addEdge(e3));
		System.out.println(graph.edges().size());
		assertEquals("expected size = 2", 2, graph.edges().size());

	}
	
	@Test
	public void adddifferentedges2() {
		SocialNetwork graph = new SocialNetwork();
		graph.addVertex(v1);
		graph.addVertex(v2);
		graph.addEdge(e1);
		graph.addEdge(e4);
		ForwardTie e5 = new ForwardTie(v1,v2,0.8);
		graph.addEdge(e5);
		assertTrue("expected True", graph.addEdge(e3));
		System.out.println(graph.edges().size());
		assertEquals("expected size = 3", 3, graph.edges().size());

	}
	
}
