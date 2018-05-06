package MovieGraphTest;

import static org.junit.Assert.*;

import org.junit.Test;

import edge.MovieActorRelation;
import edge.NetworkConnection;
import graph.MovieGraph;
import graph.NetworkTopology;
import vertex.Actor;
import vertex.Computer;
import vertex.Director;
import vertex.Movie;
import vertex.Router;
import vertex.Word;

public class MovieGraphtest {
	
	Actor a1 = new Actor("a1");
	Actor a2 = new Actor("a2");
	Actor a3 = new Actor("a1");
	Director d1 = new Director("d1");
	Movie m1 = new Movie("v1");
	
	// Testing strategy 
    //
    // addVertex():
    //	vertex = in graph, not in graph
    // 	graph size = 0, 1, n
	//	type of vertex = include, not include
	//	vertex = null, not null
	// type of vertex = not include
	@Test(expected = IllegalArgumentException.class)
	public void testaddVertexnot() {
		MovieGraph graph = new MovieGraph();
		Word v1 = new Word("word");

		graph.addVertex(v1);	
	}
	
	// vertex = null
	@Test(expected = IllegalArgumentException.class)
	public void testaddVertexnull() {
		MovieGraph graph = new MovieGraph();
		Actor lee = null;
		graph.addVertex(lee);
			
	}
		
	// vertex = in graph
	@Test(expected = IllegalArgumentException.class)
	public void testaddVertexsameperson() {
		MovieGraph graph = new MovieGraph();
		Actor lee = new Actor("lee");
		graph.addVertex(lee);
		graph.addVertex(lee);
	}
		
	//	vertex = not in graph
	//	graph size = 0
	@Test
	public void testaddVertexonepersonnograph() {
		MovieGraph graph = new MovieGraph();
		Actor lee = new Actor("lee");
			
		assertTrue("expected True, added vertex to the graph", graph.addVertex(lee));
	    assertTrue("expected graph size 1", graph.vertices().size() == 1);
		assertTrue("expected graph to contain the vertex", graph.vertices().contains(lee));
	}
		
	//	vertex = not in graph
	//	graph size = 1		
	@Test
	public void testaddVertexonepersononegraph() {
		MovieGraph graph = new MovieGraph();
		Actor abc = new Actor("abc");
		graph.addVertex(abc);
		Director lee = new Director("lee");
			
		assertTrue("expected True, added vertex to the graph", graph.addVertex(lee));
	    assertTrue("expected graph size 2", graph.vertices().size() == 2);
	    assertTrue("expected graph to contain the vertex", graph.vertices().contains(lee));
	}
		
		//	vertex = not in graph
		//	graph size = n
		@Test
		public void testaddVertexNgraph() {
			MovieGraph graph = new MovieGraph();
			graph.addVertex(a1);
			graph.addVertex(a2);
			graph.addVertex(m1);
			Director lee = new Director("lee");
			
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
			MovieGraph graph = new MovieGraph();
			graph.addVertex(a1);
			//System.out.println(a.tellclass());
			//System.out.println(b.tellclass());
			MovieActorRelation n = new MovieActorRelation(a1,m1,10.0);
			graph.addEdge(n);
		}
		
		//		 target or soure null, not null
		@Test(expected = AssertionError.class)	
		public void testaddEdgenull() {
			MovieGraph graph = new MovieGraph();
			graph.addVertex(a1);
			Movie d = null;
			MovieActorRelation n = new MovieActorRelation(a1,d,10.0);
			graph.addEdge(n);
		}
		
		//		 target or soure null, not null
		//			 edge first, new
		@Test
		public void testaddEdgesame() {
			MovieGraph graph = new MovieGraph();
			graph.addVertex(a1);
			graph.addVertex(m1);
			MovieActorRelation n = new MovieActorRelation(a1,m1,10.0);
			graph.addEdge(n);
			assertTrue("expected true",graph.addEdge(n));
		}

}
