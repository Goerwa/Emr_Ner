package P3;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

import P3.FriendshipGraph;
import P3.Person;

public class FriendshipGraphTest {
	
	//test a null person
	@Test(expected = IllegalArgumentException.class)
	public void testaddVertexnull() {
		FriendshipGraph graph = new FriendshipGraph();
		Person lee = null;
		graph.addVertex(lee);
	}
	
	//Test adding person with the same name 
	@Test(expected = IllegalArgumentException.class)	
	public void testaddVertexin() {
		FriendshipGraph graph = new FriendshipGraph();
		Person rachel = new Person("Rachel");
		graph.addVertex(rachel);
		graph.addVertex(rachel);
	}
	
	//Test adding an edge with null perosn
	@Test(expected = IllegalArgumentException.class)
	public void testaddEdgenull() {
		FriendshipGraph graph = new FriendshipGraph();
		Person rachel = new Person("Rachel");
		Person lee = null;
		graph.addEdge(rachel,lee);
	}
	
	//Test adding an edge with the same name perosn
	@Test (expected = IllegalArgumentException.class)	
	public void testaddEdgesame() {
		FriendshipGraph graph = new FriendshipGraph();
		Person rachel = new Person("Rachel");
		graph.addEdge(rachel,rachel);
	}
	
	//Test adding a duplicated edge
	@Test (expected = IllegalArgumentException.class)	
	public void testaddEdgein() {
		FriendshipGraph graph = new FriendshipGraph();
		Person rachel = new Person("Rachel");
		Person lee = new Person("lee");
		graph.addEdge(rachel,lee);
		graph.addEdge(rachel,lee);
	}
	
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
		assertEquals(3,graph.getDistance(hit, abc));
		assertEquals(4,graph.getDistance(rachel, kramer));
		assertEquals(1,graph.getDistance(rachel, ross));
	}
	
}
