package P2;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import P2.FriendshipGraph;
import P2.Person;

public class FriendshipGraphTest {
	
	Person a = new Person("a");
	Person b = new Person("b");
	Person c = new Person("c");
	Person d = new Person("d");
	// Testing strategy 
    //
    // addVertex():
    //      vertex = in graph, not in graph
    //      graph size = 0, 1, n
	
	// person = null
	@Test(expected = IllegalArgumentException.class)
	public void testaddVertexnull() {
		FriendshipGraph graph = new FriendshipGraph();
		Person lee = null;
		graph.addVertex(lee);
		
	}
	
	// vertex = in graph
	@Test(expected = IllegalArgumentException.class)
	public void testaddVertexsameperson() {
		FriendshipGraph graph = new FriendshipGraph();
		Person lee = new Person("lee");
		graph.addVertex(lee);
		graph.addVertex(lee);
	}
	
	//	vertex = not in graph
	//	graph size = 0
	@Test
	public void testaddVertexonepersonnograph() {
		FriendshipGraph graph = new FriendshipGraph();
		Person lee = new Person("lee");
		
		assertTrue("expected True, added vertex to the graph", graph.addVertex(lee));
        assertTrue("expected graph size 1", graph.vertices().size() == 1);
		assertTrue("expected graph to contain the vertex", graph.vertices().contains("lee"));
	}
	
	//	vertex = not in graph
	//	graph size = 1
	@Test
	public void testaddVertexonepersononegraph() {
		FriendshipGraph graph = new FriendshipGraph();
		Person abc = new Person("abc");
		graph.addVertex(abc);
		Person lee = new Person("lee");
		
		assertTrue("expected True, added vertex to the graph", graph.addVertex(lee));
        assertTrue("expected graph size 1", graph.vertices().size() == 2);
    		assertTrue("expected graph to contain the vertex", graph.vertices().contains("lee"));
    	}
	
	//	vertex = not in graph
	//	graph size = n
	@Test
	public void testaddVertexNgraph() {
		FriendshipGraph graph = new FriendshipGraph();
		graph.addVertex(a);
		graph.addVertex(b);
		graph.addVertex(c);
		Person lee = new Person("lee");
		
		assertTrue("expected True, added vertex to the graph", graph.addVertex(lee));
        assertTrue("expected graph size 1", graph.vertices().size() == 4);
    		assertTrue("expected graph to contain the vertex", graph.vertices().contains("lee"));
    	}
	// Testing strategy 
    //
	// addEege():
	//			 target or source in graph,not in graph
	//           target or soure same, not same
	//			 target or soure null, not null
	//			 edge first, new
	//

	//target or source = not in graph 
	@Test(expected = IllegalArgumentException.class)	
	public void testaddVertexin() {
		FriendshipGraph graph = new FriendshipGraph();
		Person rachel = new Person("Rachel");
		graph.addVertex(rachel);
		graph.addVertex(rachel);
	}
		
	//target or source = null
	@Test(expected = IllegalArgumentException.class)
	public void testaddEdgenull() {
		FriendshipGraph graph = new FriendshipGraph();
		Person rachel = new Person("Rachel");
		Person lee = null;
		graph.addEdge(rachel,lee);
	}
		
	//target or source = same
	@Test (expected = IllegalArgumentException.class)	
	public void testaddEdgesame() {
		FriendshipGraph graph = new FriendshipGraph();
		Person rachel = new Person("Rachel");
		graph.addEdge(rachel,rachel);
	}
	
	//edge = first
	@Test
	public void testaddEdgefirst() {
		FriendshipGraph graph = new FriendshipGraph();
		graph.addVertex(a);
		graph.addVertex(b);
        
        Map<String, Integer> targets = new HashMap<>();
        targets.put(b.getName(), 1);
        Map<String, Integer> sources = new HashMap<>();
        sources.put(a.getName(),1);
        
        assertTrue("expected True, added Edge to the graph",  graph.addEdge(a, b));
        assertEquals("expected Graph containing edge", targets, graph.targets(a));
        assertEquals("expected Graph containing edge", sources, graph.sources(b));
	}
	
	//edge = new
	@Test
	public void testaddEdgenew() {
		FriendshipGraph graph = new FriendshipGraph();
		graph.addVertex(d);
		graph.addVertex(c);
		graph.addVertex(a);
		graph.addVertex(b);
		graph.addEdge(a, c);
		graph.addEdge(d, c);
		
        Map<String, Integer> targets = new HashMap<>();
        targets.put(b.getName(), 1);
        Map<String, Integer> sources = new HashMap<>();
        sources.put(a.getName(),1);
        
        assertTrue("expected True, added Edge to the graph",  graph.addEdge(a, b));
        assertTrue("expected Graph containing edge", graph.targets(a).keySet().contains(b.getName()));
        assertTrue("expected Graph containing edge", graph.sources(b).keySet().contains(a.getName()));
	}
	// Testing strategy 
    //
	// getDistance():
	//			target and source = no edge, edge , sameperson
	//      		vertex = 0, 1, n
	//			 	
	//			 
	//
	//
	//	target and source = no edge
	//  vertex = 0
	
	@Test(expected = IllegalArgumentException.class)
	public void testgetDistencenull() {
		FriendshipGraph graph = new FriendshipGraph();
		
		graph.getDistance(new Person("person1"), new Person("person2"));
		
	}
	
	//	target and source = no edge
	//  vertex = 1
	
	@Test
	public void testgetDistencesame() {
		FriendshipGraph graph = new FriendshipGraph();
		graph.addVertex(a);
		assertEquals(0,graph.getDistance(a, a));
	}
	
	//	target and source =  edge
	//  vertex = n
	@Test
	public void test1() {
		FriendshipGraph graph = new FriendshipGraph();
		
        Person rachel = new Person("Rachel");
        Person ross = new Person("Ross");
        Person ben = new Person("Ben");
        Person kramer = new Person("Kramer");
        graph.addVertex(rachel);
		graph.addVertex(ross);
		graph.addVertex(ben);
		graph.addVertex(kramer);
		
		graph.addEdge(rachel, ross);
		graph.addEdge(ross,rachel);
		graph.addEdge(ross, ben);
		graph.addEdge(ben,ross);
		
		assertEquals(1,graph.getDistance(rachel, ross));
		assertEquals(2,graph.getDistance(rachel, ben));
		assertEquals(0,graph.getDistance(rachel, rachel));
		assertEquals(-1,graph.getDistance(rachel, kramer));
	}
	
	//	target and source =  edge
	//  vertex = n
	@Test
	public void test2() {
		FriendshipGraph graph = new FriendshipGraph();
		
        Person rachel = new Person("Rachel");
        Person ross = new Person("Ross");
        Person ben = new Person("Ben");
        Person kramer = new Person("Kramer");
        Person lee = new Person("lee");
        Person abc = new Person("abc");
        Person gogo = new Person("gogo");
        Person hit = new Person("hit");
        graph.addVertex(rachel);
		graph.addVertex(ross);
		graph.addVertex(ben);
		graph.addVertex(kramer);
		graph.addVertex(lee);
		graph.addVertex(abc);
		graph.addVertex(gogo);
		graph.addVertex(hit);
		
		graph.addEdge(rachel, ross);
		graph.addEdge(ross,rachel);
		
		graph.addEdge(ross, lee);
		graph.addEdge(lee,ross);
		
		graph.addEdge(lee, abc);
		graph.addEdge(abc,lee);
		
		graph.addEdge(ben, lee);
		graph.addEdge(lee,ben);
		
		graph.addEdge(gogo, lee);
		graph.addEdge(lee,gogo);
		
		graph.addEdge(gogo, hit);
		graph.addEdge(hit,gogo);
		
		graph.addEdge(kramer, hit);
		graph.addEdge(hit,kramer);
		
		graph.addEdge(kramer, ben);
		graph.addEdge(ben,kramer);
		
		assertEquals(2,graph.getDistance(rachel, lee));
		assertEquals(2,graph.getDistance(kramer, lee));
		//System.out.println(graph.getDistance(hit, abc));
		assertEquals(3,graph.getDistance(hit, abc));
		//System.out.println(graph.getDistance(rachel, kramer));
		assertEquals(4,graph.getDistance(rachel, kramer));
		
		assertEquals(1,graph.getDistance(rachel, ross));
	}
		
}

