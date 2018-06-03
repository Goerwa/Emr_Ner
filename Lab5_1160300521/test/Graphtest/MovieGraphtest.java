package Graphtest;

import static org.junit.Assert.assertTrue;
import java.io.IOException;
import org.junit.Test;
import Exception.ExceptionofInput;
import Exception.ExceptionofUnproperEdge;
import edge.MovieActorRelation;
import graph.MovieGraph;
import vertex.Actor;
import vertex.Director;
import vertex.Movie;
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
  // vertex = in graph, not in graph
  // graph size = 0, 1, n
  // type of vertex = include, not include
  // vertex = null, not null
  // type of vertex = not include
  @Test(expected = ExceptionofInput.class)
  public void testaddVertexnot() throws ExceptionofInput, IOException {
    MovieGraph graph = new MovieGraph();
    Word v1 = new Word("word");

    graph.addVertex(v1);
  }

  // vertex = null
  @Test(expected = AssertionError.class)
  public void testaddVertexnull() throws ExceptionofInput, IOException {
    MovieGraph graph = new MovieGraph();
    Actor lee = new Actor(null);
    graph.addVertex(lee);

  }


  // vertex = not in graph
  // graph size = 0
  @Test
  public void testaddVertexonepersonnograph() throws ExceptionofInput, IOException {
    MovieGraph graph = new MovieGraph();
    Actor lee = new Actor("lee");

    assertTrue("expected True, added vertex to the graph", graph.addVertex(lee));
    assertTrue("expected graph size 1", graph.vertices().size() == 1);
    assertTrue("expected graph to contain the vertex", graph.vertices().contains(lee));
  }

  // vertex = not in graph
  // graph size = 1
  @Test
  public void testaddVertexonepersononegraph() throws ExceptionofInput, IOException {
    MovieGraph graph = new MovieGraph();
    Actor abc = new Actor("abc");
    graph.addVertex(abc);
    Director lee = new Director("lee");

    assertTrue("expected True, added vertex to the graph", graph.addVertex(lee));
    assertTrue("expected graph size 2", graph.vertices().size() == 2);
    assertTrue("expected graph to contain the vertex", graph.vertices().contains(lee));
  }

  // vertex = not in graph
  // graph size = n
  @Test
  public void testaddVertexNgraph() throws ExceptionofInput, IOException {
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
  // target or source in graph,not in graph
  // target or soure null, not null
  // edge first, new
  //

  // target or source = not in graph
  @Test(expected = ExceptionofUnproperEdge.class)
  public void testaddEdgein() throws ExceptionofInput, ExceptionofUnproperEdge, IOException {
    MovieGraph graph = new MovieGraph();
    graph.addVertex(a1);
    // System.out.println(a.tellclass());
    // System.out.println(b.tellclass());
    MovieActorRelation n = new MovieActorRelation("n", a1, m1, 10.0);
    graph.addEdge(n);
  }

  // target or soure null, not null
  @Test(expected = AssertionError.class)
  public void testaddEdgenull() throws ExceptionofInput, ExceptionofUnproperEdge, IOException {
    MovieGraph graph = new MovieGraph();
    graph.addVertex(a1);
    Movie d = new Movie(null);
    MovieActorRelation n = new MovieActorRelation("n", a1, d, 10.0);
    graph.addEdge(n);
  }

  // target or soure null, not null
  // edge first, new
  @Test(expected = ExceptionofUnproperEdge.class)
  public void testaddEdgesame() throws ExceptionofInput, ExceptionofUnproperEdge, IOException {
    MovieGraph graph = new MovieGraph();
    graph.addVertex(a1);
    graph.addVertex(m1);
    MovieActorRelation n = new MovieActorRelation("n", a1, m1, 10.0);
    graph.addEdge(n);
    assertTrue("expected true", graph.addEdge(n));
  }
}
