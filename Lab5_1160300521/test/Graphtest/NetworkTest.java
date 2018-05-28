package Graphtest;

import static org.junit.Assert.assertTrue;
import org.junit.Test;
import Exception.ExceptionofInput;
import Exception.ExceptionofUnproperEdge;
import edge.NetworkConnection;
import graph.NetworkTopology;
import vertex.Computer;
import vertex.Router;
import vertex.Server;
import vertex.Word;

public class NetworkTest {
  Computer a = new Computer("a");
  Router b = new Router("b");
  Server c = new Server("c");



  // Testing strategy
  //
  // addVertex():
  // vertex = in graph, not in graph
  // graph size = 0, 1, n
  // type of vertex = person, not person

  // type of vertex = not person
  @Test(expected = ExceptionofInput.class)
  public void testaddVertexnot() throws ExceptionofInput {
    NetworkTopology graph = new NetworkTopology();
    Word v1 = new Word("word");

    graph.addVertex(v1);
  }

  // vertex = not in graph
  // graph size = 0
  @Test
  public void testaddVertexonepersonnograph() throws ExceptionofInput {
    NetworkTopology graph = new NetworkTopology();
    Computer lee = new Computer("lee");

    assertTrue("expected True, added vertex to the graph", graph.addVertex(lee));
    assertTrue("expected graph size 1", graph.vertices().size() == 1);
    assertTrue("expected graph to contain the vertex", graph.vertices().contains(lee));
  }

  // vertex = not in graph
  // graph size = 1
  @Test
  public void testaddVertexonepersononegraph() throws ExceptionofInput {
    NetworkTopology graph = new NetworkTopology();
    Computer abc = new Computer("abc");
    graph.addVertex(abc);
    Computer lee = new Computer("lee");

    assertTrue("expected True, added vertex to the graph", graph.addVertex(lee));
    assertTrue("expected graph size 2", graph.vertices().size() == 2);
    assertTrue("expected graph to contain the vertex", graph.vertices().contains(lee));
  }

  // vertex = not in graph
  // graph size = n
  @Test
  public void testaddVertexNgraph() throws ExceptionofInput {
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
  // target or source in graph,not in graph
  // target or soure null, not null
  // edge first, new
  //

  // target or source = not in graph
  @Test(expected = ExceptionofUnproperEdge.class)
  public void testaddEdgein() throws ExceptionofInput, ExceptionofUnproperEdge {
    NetworkTopology graph = new NetworkTopology();
    graph.addVertex(a);
    // System.out.println(a.tellclass());
    // System.out.println(b.tellclass());
    NetworkConnection n = new NetworkConnection("n", a, b, 10.0);
    graph.addEdge(n);
  }

  // target or soure null, not null
  @Test(expected = AssertionError.class)
  public void testaddEdgenull() throws ExceptionofInput, ExceptionofUnproperEdge {
    NetworkTopology graph = new NetworkTopology();
    graph.addVertex(a);
    Router d = new Router(null);
    NetworkConnection n = new NetworkConnection("n", a, d, 10.0);
    graph.addEdge(n);
  }

  // target or soure null, not null
  // edge first, new
  @Test(expected = ExceptionofUnproperEdge.class)
  public void testaddEdgesame() throws ExceptionofInput, ExceptionofUnproperEdge {
    NetworkTopology graph = new NetworkTopology();
    graph.addVertex(a);
    graph.addVertex(b);
    NetworkConnection n = new NetworkConnection("n", a, b, 10.0);
    graph.addEdge(n);
    assertTrue("expected true", graph.addEdge(n));
  }
}
