package Networktest;

import static org.junit.Assert.*;

import org.junit.Test;

import edge.FriendTie;
import edge.NetworkConnection;
import graph.NetworkTopology;
import graph.SocialNetwork;
import vertex.Computer;
import vertex.Person;
import vertex.Router;
import vertex.Server;
import vertex.Word;

public class Networktest {
	
	
	Computer a = new Computer("a");
	Router b = new Router("b");
	Server c = new Server("c");
	
	
	
	// Testing strategy 
    //
    // addVertex():
    //	vertex = in graph, not in graph
    // 	graph size = 0, 1, n
	//	type of vertex = person, not person
	
	// type of vertex = not person
	@Test(expected = IllegalArgumentException.class)
	public void testaddVertexnot() {
		NetworkTopology graph = new NetworkTopology();
		Word v1 = new Word("word");

		graph.addVertex(v1);	
	}
	
	// person = null
	@Test(expected = IllegalArgumentException.class)
	public void testaddVertexnull() {
		NetworkTopology graph = new NetworkTopology();
		Computer lee = null;
		graph.addVertex(lee);
			
	}
		
	// vertex = in graph
	@Test(expected = IllegalArgumentException.class)
	public void testaddVertexsameperson() {
		NetworkTopology graph = new NetworkTopology();
		Computer lee = new Computer("lee");
		graph.addVertex(lee);
		graph.addVertex(lee);
	}
		
	//	vertex = not in graph
	//	graph size = 0
	@Test
	public void testaddVertexonepersonnograph() {
		NetworkTopology graph = new NetworkTopology();
		Computer lee = new Computer("lee");
			
		assertTrue("expected True, added vertex to the graph", graph.addVertex(lee));
	    assertTrue("expected graph size 1", graph.vertices().size() == 1);
		assertTrue("expected graph to contain the vertex", graph.vertices().contains(lee));
	}
		
	//	vertex = not in graph
	//	graph size = 1		
	@Test
	public void testaddVertexonepersononegraph() {
		NetworkTopology graph = new NetworkTopology();
		Computer abc = new Computer("abc");
		graph.addVertex(abc);
		Computer lee = new Computer("lee");
			
		assertTrue("expected True, added vertex to the graph", graph.addVertex(lee));
	    assertTrue("expected graph size 2", graph.vertices().size() == 2);
	    assertTrue("expected graph to contain the vertex", graph.vertices().contains(lee));
	}
		
		//	vertex = not in graph
		//	graph size = n
		@Test
		public void testaddVertexNgraph() {
			NetworkTopology graph = new NetworkTopology();
			graph.addVertex(a);
			graph.addVertex(b);
			graph.addVertex(c);
			Computer lee = new Computer("lee");
			
			assertTrue("expected True, added vertex to the graph", graph.addVertex(lee));
	        assertTrue("expected graph size 1", graph.vertices().size() == 4);
	    		assertTrue("expected graph to contain the vertex", graph.vertices().contains(lee));
	    	}
		// Testing strategy 
	    //
		// addEege():
		//			 target or source in graph,not in graph
		//			 target or soure null, not null
		//			 edge first, new
		//

		//target or source = not in graph 
		@Test(expected = IllegalArgumentException.class)	
		public void testaddEdgein() {
			NetworkTopology graph = new NetworkTopology();
			graph.addVertex(a);
			//System.out.println(a.tellclass());
			//System.out.println(b.tellclass());
			NetworkConnection n = new NetworkConnection(a,b,10.0);
			graph.addEdge(n);
		}
		
		//		 target or soure null, not null
		@Test(expected = AssertionError.class)	
		public void testaddEdgenull() {
			NetworkTopology graph = new NetworkTopology();
			graph.addVertex(a);
			Router d = null;
			NetworkConnection n = new NetworkConnection(a,d,10.0);
			graph.addEdge(n);
		}
		
		//		 target or soure null, not null
		//			 edge first, new
		@Test
		public void testaddEdgesame() {
			NetworkTopology graph = new NetworkTopology();
			graph.addVertex(a);
			graph.addVertex(b);
			NetworkConnection n = new NetworkConnection(a,b,10.0);
			graph.addEdge(n);
			assertTrue("expected true",graph.addEdge(n));
		}
	
}
