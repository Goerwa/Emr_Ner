package P2;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Main {
	public static void main(String[] args) {
		FriendshipGraph graph = new FriendshipGraph();
		
        Person rachel = new Person("Rachel");
        Person ross = new Person("Ross");
        Person ben = new Person("Ben");
        Person kramer = new Person("Kramer");
        Person lee = new Person("lee");
        graph.addVertex(rachel);
		graph.addVertex(ross);
		graph.addVertex(ben);
		graph.addVertex(kramer);
		graph.addVertex(lee);
		graph.addEdge(rachel, ross);
		graph.addEdge(ross,rachel);
		graph.addEdge(ross, ben);
		graph.addEdge(ben,ross);
		
		graph.addEdge(ben,lee);
		graph.addEdge(lee,ben);
		
		//graph.test();
		System.out.println(graph.getDistance(rachel, rachel));
		System.out.println(graph.getDistance(rachel, kramer));
		System.out.println(graph.getDistance(rachel, ross));
		System.out.println(graph.getDistance(rachel, ben));
		System.out.println(graph.getDistance(rachel, lee));
	}
}
