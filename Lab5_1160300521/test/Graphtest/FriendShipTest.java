package Graphtest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import java.io.IOException;
import java.util.Set;
import org.junit.Test;
import Exception.ExceptionofInput;
import Exception.ExceptionofUnproperEdge;
import edge.Edge;
import edge.FriendTie;
import graph.SocialNetwork;
import vertex.Person;
import vertex.Word;

public class FriendShipTest {
  Person a = new Person("a");
  Person b = new Person("b");
  Person c = new Person("c");
  Person d = new Person("d");

  // Testing strategy
  //
  // addVertex():
  // vertex = in graph, not in graph
  // graph size = 0, 1, n
  // type of vertex = person, not person

  // type of vertex = not person
  @Test(expected = ExceptionofInput.class)
  public void testaddVertexnot() throws ExceptionofInput, IOException {
    SocialNetwork graph = new SocialNetwork();
    Word v1 = new Word("word");

    graph.addVertex(v1);
  }

  @Test(expected = AssertionError.class)
  public void testaddedgeweight1() throws ExceptionofInput, ExceptionofUnproperEdge, IOException {
    SocialNetwork graph = new SocialNetwork();
    graph.addVertex(a);
    graph.addVertex(b);
    FriendTie e1 = new FriendTie(a, b, 1.85);

    graph.addEdge(e1);
  }

  @Test
  public void testaddedgeweight2() throws ExceptionofInput, ExceptionofUnproperEdge, IOException {
    SocialNetwork graph = new SocialNetwork();
    graph.addVertex(a);
    graph.addVertex(b);
    FriendTie e1 = new FriendTie(a, b, 0.99);
    // System.out.println(graph.addEdge(e1));
    graph.addEdge(e1);
  }

  @Test(expected = ExceptionofUnproperEdge.class)
  public void testaddedgeweight3() throws ExceptionofInput, ExceptionofUnproperEdge, IOException {
    SocialNetwork graph = new SocialNetwork();
    graph.addVertex(a);
    graph.addVertex(b);
    FriendTie e1 = new FriendTie(a, b, 0.0);

    graph.addEdge(e1);
  }


  // person = null
  @Test(expected = AssertionError.class)
  public void testaddVertexnull() throws ExceptionofInput {
    SocialNetwork graph = new SocialNetwork();
    Person lee = new Person(null);

  }


  // vertex = not in graph
  // graph size = 0
  @Test
  public void testaddVertexonepersonnograph() throws ExceptionofInput, IOException {
    SocialNetwork graph = new SocialNetwork();
    Person lee = new Person("lee");

    assertTrue("expected True, added vertex to the graph", graph.addVertex(lee));
    assertTrue("expected graph size 1", graph.vertices().size() == 1);
    assertTrue("expected graph to contain the vertex", graph.vertices().contains(lee));
  }

  // vertex = not in graph
  // graph size = 1
  @Test
  public void testaddVertexonepersononegraph() throws ExceptionofInput, IOException {
    SocialNetwork graph = new SocialNetwork();
    Person abc = new Person("abc");
    graph.addVertex(abc);
    Person lee = new Person("lee");

    assertTrue("expected True, added vertex to the graph", graph.addVertex(lee));
    assertTrue("expected graph size 2", graph.vertices().size() == 2);
    assertTrue("expected graph to contain the vertex", graph.vertices().contains(lee));
  }

  // vertex = not in graph
  // graph size = n
  @Test
  public void testaddVertexNgraph() throws ExceptionofInput, IOException {
    SocialNetwork graph = new SocialNetwork();
    graph.addVertex(a);
    graph.addVertex(b);
    graph.addVertex(c);
    Person lee = new Person("lee");

    assertTrue("expected True, added vertex to the graph", graph.addVertex(lee));
    assertTrue("expected graph size 1", graph.vertices().size() == 4);
    assertTrue("expected graph to contain the vertex", graph.vertices().contains(lee));
  }
  // Testing strategy
  //
  // addEege():
  // target or source in graph,not in graph
  // target or soure same, not same
  // target or soure null, not null
  // edge first, new
  //



  // target or source = same
  @Test(expected = AssertionError.class)
  public void testaddEdgesame() throws ExceptionofInput, ExceptionofUnproperEdge, IOException {
    SocialNetwork graph = new SocialNetwork();
    Person rachel = new Person("Rachel");
    FriendTie e = new FriendTie(rachel, rachel, 1.0);
    graph.addEdge(e);
  }

  @Test
  public void testaddedge1() throws ExceptionofInput, ExceptionofUnproperEdge, IOException {
    SocialNetwork graph = new SocialNetwork();
    graph.addVertex(a);
    graph.addVertex(b);
    FriendTie e1 = new FriendTie(a, b, 0.85);

    graph.addEdge(e1);
    // System.out.println(graph.getedge(e1).getweight());
    Double t = 1.0;
    assertEquals("expected weight = 1.0", t, graph.getedge(e1).getweight());
  }

  @Test
  public void testaddedge2() throws ExceptionofInput, ExceptionofUnproperEdge, IOException {
    SocialNetwork graph = new SocialNetwork();
    graph.addVertex(a);
    graph.addVertex(b);
    graph.addVertex(c);
    FriendTie e1 = new FriendTie("e1", a, b, 0.25);
    FriendTie e2 = new FriendTie("e2", a, c, 0.50);
    FriendTie e3 = new FriendTie("e3", b, c, 0.60);

    graph.addEdge(e1);
    System.out.println(graph.getedge(e1).getweight());
    graph.addEdge(e2);
    System.out.println(graph.getedge(e1).getweight());
    Double t = 0.20;
    graph.addEdge(e3);
    System.out.println(graph.getedge(e1).getweight());
    System.out.println(graph.getedge(e2).getweight());
    Double sum = 0.0;
    Set<Edge> edges = graph.edges();
    for (Edge e : edges) {
      sum += e.getweight();
    }
    System.out.println("----------------    the sum is   " + sum);
    Double sum1 = 1.0;
    assertEquals("expected weight = 1.0", t, graph.getedge(e1).getweight());
    assertEquals("expected sum = 1.0 ", sum1, sum);
  }

  // test weight = 0.0
  @Test
  public void testaddedge3() throws ExceptionofInput, ExceptionofUnproperEdge, IOException {
    SocialNetwork graph = new SocialNetwork();
    graph.addVertex(a);
    graph.addVertex(b);
    graph.addVertex(c);
    FriendTie e1 = new FriendTie(a, b, 0.25);
    FriendTie e2 = new FriendTie(a, c, 0.50);
    FriendTie e3 = new FriendTie(b, c, 0.50);
    FriendTie e4 = new FriendTie(a, b, 0.75);

    graph.addEdge(e1);
    // System.out.println(graph.getedge(e1).getweight());
    graph.addEdge(e2);
    Double t = 0.25 * 0.25 / 0.75;
    graph.addEdge(e3);
    graph.removeEdge(e1);
    graph.addEdge(e4);
    Double t1 = 0.5 * 0.25 / 0.75;
    Double sum = 0.0;
    Set<Edge> edges = graph.edges();
    for (Edge e : edges) {
      sum += e.getweight();
    }
    Double sum1 = 1.0;
    // System.out.println(sum);
    assertEquals("expected weight = 0.08333333333333333", t, graph.getedge(e2).getweight());
    assertEquals("expected weight = 0.16666666666666666", t1, graph.getedge(e3).getweight());
    assertEquals("expected sum = 1.0 ", sum1, sum);
  }

  @Test
  public void testaddedge4() throws ExceptionofInput, ExceptionofUnproperEdge, IOException {
    SocialNetwork graph = new SocialNetwork();
    graph.addVertex(a);
    graph.addVertex(b);
    graph.addVertex(c);
    FriendTie e1 = new FriendTie(a, b, 0.25);
    FriendTie e2 = new FriendTie(a, c, 0.50);
    FriendTie e3 = new FriendTie(b, c, 0.50);

    graph.addEdge(e1);
    graph.addEdge(e2);
    graph.addEdge(e3);
    // System.out.println(graph.edges().size());
    // graph.removeEdge(e1);
    Double t = 0.25 / 0.75;
    Double t1 = 0.5 / 0.75;
    graph.removeEdge(e1);
    // System.out.println(graph.getedge(e2).getweight());
    // System.out.println(graph.getedge(e3).getweight());
    Double sum = 0.0;
    Set<Edge> edges = graph.edges();
    for (Edge e : edges) {
      sum += e.getweight();
    }
    Double sum1 = 1.0;
    assertEquals("expected weight = 0.08333333333333333", t, graph.getedge(e2).getweight());
    assertEquals("expected weight = 0.16666666666666666", t1, graph.getedge(e3).getweight());
    assertEquals("expected sum = 1.0 ", sum1, sum);
  }
}
