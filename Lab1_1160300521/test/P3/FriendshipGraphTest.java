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
	
}
