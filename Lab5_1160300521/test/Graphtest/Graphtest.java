package Graphtest;

import static org.junit.Assert.*;
import java.awt.Label;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Test;
import Exception.ExceptionofInput;
import Exception.ExceptionofUnproperEdge;
import edge.Edge;
import edge.WordNeighborhood;
import graph.Graph;
import vertex.Vertex;
import vertex.Word;

public class Graphtest {

  // test addVertex()
  // Testing strategy
  // graphsize = 0,1,n
  // vertex in graph,not in graph

  Word v1 = new Word("v1");
  Word v2 = new Word("v2");
  Word v3 = new Word("v3");
  Word v4 = new Word("v4");
  Word v5 = new Word("v5");
  WordNeighborhood e1 = new WordNeighborhood("edge1", v1, v2, 1.0);
  WordNeighborhood e2 = new WordNeighborhood("edge2", v3, v2, 1.0);
  WordNeighborhood e3 = new WordNeighborhood("edge3", v1, v5, 1.0);


  // graphsize = 0
  // vertex not in graph
  @Test
  public void testaddvertexone() throws ExceptionofInput, IOException {
    Graph<Vertex, Edge> graph = Graph.empty();

    assertTrue("expected True, added vertex to the graph", graph.addVertex(v1));
    assertTrue("expected graph size 1", graph.vertices().size() == 1);
    assertTrue("expected graph to contain the vertex", graph.vertices().contains(v1));
  }


  // graphsize = n
  // vertex not in graph
  @Test
  public void testaddvertexn() throws ExceptionofInput, IOException {
    Graph<Vertex, Edge> graph = Graph.empty();
    graph.addVertex(v1);
    graph.addVertex(v2);
    assertTrue("expected True, added vertex to the graph", graph.addVertex(v3));
    assertTrue("expected graph size 3", graph.vertices().size() == 3);
    assertTrue("expected graph to contain the vertex", graph.vertices().contains(v1));
    assertTrue("expected graph to contain the vertex", graph.vertices().contains(v2));
    assertTrue("expected graph to contain the vertex", graph.vertices().contains(v3));
  }

  // test addEdge()
  // Testing strategy
  // edgenumber = 1,n
  // edge = new, not new
  // weight = zero, positive
  // source, target = in graph, not in graph

  // edgenumber = 1
  // edge = new
  // weight = positive
  // source, target = in graph
  @Test
  public void testaddEdgeone() throws ExceptionofInput, ExceptionofUnproperEdge, IOException {
    Graph<Vertex, Edge> graph = Graph.empty();
    graph.addVertex(v1);
    graph.addVertex(v2);
    graph.addEdge(e1);

    Map<Vertex, Double> targets = new HashMap<>();
    Map<Vertex, Double> sources = new HashMap<>();
    targets.put(v2, 1.0);
    sources.put(v1, 1.0);

    assertEquals("expected number of edge to be 1", 1, graph.edges().size());
    assertEquals("expected Graph containing edge", targets, graph.targetsone(v1));
    assertEquals("expected Graph containing edge", sources, graph.sourcesone(v2));
  }

  // edgenumber = n
  // edge = new
  // weight = positive
  // source, target = in graph
  @Test
  public void testaddEdgen() throws ExceptionofInput, ExceptionofUnproperEdge, IOException {
    Graph<Vertex, Edge> graph = Graph.empty();
    graph.addVertex(v1);
    graph.addVertex(v2);
    graph.addVertex(v3);
    graph.addVertex(v5);
    graph.addEdge(e1);
    graph.addEdge(e2);
    graph.addEdge(e3);
    Map<Vertex, Double> targets = new HashMap<>();
    Map<Vertex, Double> sources = new HashMap<>();
    targets.put(v2, 1.0);
    targets.put(v5, 1.0);
    sources.put(v1, 1.0);
    sources.put(v3, 1.0);

    assertEquals("expected number of edge to be 3", 3, graph.edges().size());
    assertEquals("expected Graph containing edge", targets, graph.targetsone(v1));
    assertEquals("expected Graph containing edge", sources, graph.sourcesone(v2));
  }

  // edgenumber = 1
  // edge = not new
  // weight = positive
  // source, target = in graph
  @Test(expected = ExceptionofUnproperEdge.class)
  public void testaddEdgenotnew() throws ExceptionofInput, ExceptionofUnproperEdge, IOException {
    Graph<Vertex, Edge> graph = Graph.empty();
    graph.addVertex(v1);
    graph.addVertex(v2);
    graph.addEdge(e1);
    Map<Vertex, Double> targets = new HashMap<>();
    Map<Vertex, Double> sources = new HashMap<>();
    WordNeighborhood e11 = new WordNeighborhood("edge1", v1, v2, 2.0);
    graph.addEdge(e11);
    targets.put(v2, 2.0);
    sources.put(v1, 2.0);
    // System.out.println(graph.edges().size());
    assertEquals("expected number of edge to be 1", 1, graph.edges().size());
    assertEquals("expected Graph containing edge", targets, graph.targetsone(v1));
    assertEquals("expected Graph containing edge", sources, graph.sourcesone(v2));
  }

  // edgenumber = 1
  // edge = not new
  // weight = positive
  // source, target = in graph
  @Test(expected = ExceptionofUnproperEdge.class)
  public void testaddEdgeweightzero() throws ExceptionofInput, ExceptionofUnproperEdge, IOException {
    Graph<Vertex, Edge> graph = Graph.empty();
    graph.addVertex(v1);
    graph.addVertex(v2);
    graph.addEdge(e1);
    assertEquals("expected number of edge to be 1", 1, graph.edges().size());
    Map<Vertex, Double> targets = new HashMap<>();
    Map<Vertex, Double> sources = new HashMap<>();
    WordNeighborhood e11 = new WordNeighborhood("edge1", v1, v2, 0.0);
    graph.addEdge(e11);
    // System.out.println(graph.targetsone(v1).size());
    assertEquals("expected number of edge to be 0", 0, graph.edges().size());
    assertEquals("expected Graph containing edge", targets.size(), graph.targetsone(v1).size());
    assertEquals("expected Graph containing edge", sources.size(), graph.sourcesone(v2).size());
  }

  // edgenumber = 1
  // edge = not new
  // weight = positive
  // source, target = not in graph
  @Test(expected = ExceptionofUnproperEdge.class)
  public void testaddEdgenotin() throws ExceptionofInput, ExceptionofUnproperEdge, IOException {
    Graph<Vertex, Edge> graph = Graph.empty();
    graph.addEdge(e1);
    assertFalse("expected addEdge false", graph.addEdge(e1));
    assertEquals("expected number of edge to be 0", 0, graph.edges().size());
  }

  // test removeVertex()
  // Testing strategy
  // vertex = in graph, not in graph
  // graph size = 0,1,n

  // vertex = not in graph
  // graph size = 0
  @Test
  public void testremoveVertexnotin() {
    Graph<Vertex, Edge> graph = Graph.empty();

    assertFalse("expected False, vertex not in the Graph", graph.removeVertex(v1));
    assertTrue("expected empty graph", graph.vertices().size() == 0);
  }

  // vertex = in graph
  // graph size = 1
  @Test
  public void testremoveVertexone() throws ExceptionofInput, IOException {
    Graph<Vertex, Edge> graph = Graph.empty();
    graph.addVertex(v1);
    assertTrue("expected True, vertex in the Graph", graph.removeVertex(v1));
    assertTrue("expected empty graph", graph.vertices().size() == 0);
  }

  // vertex = in graph
  // graph size = n
  @Test
  public void testremoveVertexn() throws ExceptionofInput, IOException {
    Graph<Vertex, Edge> graph = Graph.empty();
    graph.addVertex(v1);
    graph.addVertex(v2);
    graph.addVertex(v3);
    graph.addVertex(v4);
    graph.addVertex(v5);
    assertTrue("expected True, vertex in the Graph", graph.removeVertex(v1));
    assertTrue("expected empty graph", graph.vertices().size() == 4);
    assertTrue("expected True, vertex in the Graph", graph.removeVertex(v2));
    assertTrue("expected empty graph", graph.vertices().size() == 3);
    assertTrue("expected True, vertex in the Graph", graph.removeVertex(v4));
    assertTrue("expected empty graph", graph.vertices().size() == 2);
  }


  // test removeEdge()
  // Testing strategy
  // edge = in graph, not in graph
  // edge size = 0,1,n

  // edge = not in graph
  // edge size = 0
  @Test
  public void testremoveEdgenotin() throws ExceptionofInput, ExceptionofUnproperEdge, IOException {
    Graph<Vertex, Edge> graph = Graph.empty();

    assertFalse("expected False, vertex not in the Graph", graph.removeEdge(e1));
    assertTrue("expected empty graph", graph.edges().size() == 0);
  }

  // edge = in graph
  // edge size = 1
  @Test
  public void testremoveEdgeone() throws ExceptionofInput, ExceptionofUnproperEdge, IOException {
    Graph<Vertex, Edge> graph = Graph.empty();
    graph.addVertex(v1);
    graph.addVertex(v2);
    graph.addEdge(e1);
    assertTrue("expected False, vertex not in the Graph", graph.removeEdge(e1));
    assertTrue("expected empty graph", graph.edges().size() == 0);
  }

  // edge = in graph
  // edge size = n
  @Test
  public void testremoveEdgen() throws ExceptionofInput, ExceptionofUnproperEdge, IOException {
    Graph<Vertex, Edge> graph = Graph.empty();
    graph.addVertex(v1);
    graph.addVertex(v2);
    graph.addVertex(v3);
    graph.addVertex(v5);
    graph.addEdge(e1);
    graph.addEdge(e2);
    graph.addEdge(e3);
    assertTrue("expected False, vertex not in the Graph", graph.removeEdge(e1));
    assertTrue("expected empty graph", graph.edges().size() == 2);
    assertTrue("expected False, vertex not in the Graph", graph.removeEdge(e3));
    assertTrue("expected empty graph", graph.edges().size() == 1);
  }

  // test sources()
  // Testing strategy
  //
  // nember of source = 0,1,n
  // edge num = 0,1,n
  //



  // nember of source = 1
  // edge num = 1
  @Test
  public void testsourceone() throws ExceptionofInput, ExceptionofUnproperEdge, IOException {
    Graph<Vertex, Edge> graph = Graph.empty();
    graph.addVertex(v1);
    graph.addVertex(v2);
    graph.addEdge(e1);
    Map<Vertex, List<Double>> sources = new HashMap<>();
    List<Double> r = new ArrayList<Double>();
    r.add(1.0);
    sources.put(v1, r);
    // System.out.println(sources.size());
    assertEquals("expected the vertex to contain zero sources", sources, graph.sources(v2));
  }

  // nember of source = n
  // edge num = n
  @Test
  public void testsourcen() throws ExceptionofInput, ExceptionofUnproperEdge, IOException {
    Graph<Vertex, Edge> graph = Graph.empty();
    graph.addVertex(v1);
    graph.addVertex(v2);
    graph.addVertex(v3);
    graph.addVertex(v4);
    graph.addEdge(e1);
    WordNeighborhood e11 = new WordNeighborhood("edge3", v3, v2, 1.0);
    WordNeighborhood e22 = new WordNeighborhood("edge3", v4, v2, 1.0);
    graph.addEdge(e11);
    graph.addEdge(e22);
    Map<Vertex, List<Double>> sources = new HashMap<>();
    List<Double> r = new ArrayList<Double>();
    r.add(1.0);
    sources.put(v1, r);
    sources.put(v3, r);
    sources.put(v4, r);
    // System.out.println(sources.size());
    assertEquals("expected the vertex to contain zero sources", sources, graph.sources(v2));
  }

  // test targrets()
  // Testing strategy
  //
  // nember of target = 0,1,n
  // edge num = 0,1,n
  //

  // nember of target = 0
  // edge num = 0
  @Test
  public void testtargetsnotin() throws ExceptionofInput, IOException {
    Graph<Vertex, Edge> graph = Graph.empty();
    graph.addVertex(v1);

    Map<Vertex, List<Double>> sources = new HashMap<>();
    assertEquals("expected the vertex to contain zero sources", sources, graph.targets(v1));
  }

  // nember of target = 1
  // edge num = 1
  @Test
  public void testtargetone() throws ExceptionofInput, ExceptionofUnproperEdge, IOException {
    Graph<Vertex, Edge> graph = Graph.empty();
    graph.addVertex(v1);
    graph.addVertex(v2);
    graph.addEdge(e1);
    Map<Vertex, List<Double>> sources = new HashMap<>();
    List<Double> r = new ArrayList<Double>();
    r.add(1.0);
    sources.put(v2, r);
    // System.out.println(sources.size());
    assertEquals("expected the vertex to contain zero sources", sources, graph.targets(v1));
  }

  // nember of target = n
  // edge num = n
  @Test
  public void testtargetn() throws ExceptionofInput, ExceptionofUnproperEdge, IOException {
    Graph<Vertex, Edge> graph = Graph.empty();
    graph.addVertex(v1);
    graph.addVertex(v2);
    graph.addVertex(v3);
    graph.addVertex(v4);
    graph.addEdge(e1);
    WordNeighborhood e11 = new WordNeighborhood("edge3", v1, v3, 1.0);
    WordNeighborhood e22 = new WordNeighborhood("edge3", v1, v4, 1.0);
    graph.addEdge(e11);
    graph.addEdge(e22);
    Map<Vertex, List<Double>> sources = new HashMap<>();
    List<Double> r = new ArrayList<Double>();
    r.add(1.0);
    sources.put(v2, r);
    sources.put(v3, r);
    sources.put(v4, r);
    // System.out.println(sources.size());
    assertEquals("expected the vertex to contain zero sources", sources, graph.targets(v1));
  }

  // test getdistance()
  // Testing strategy
  // vertex = 1,n
  // vertex = not in graph, in graph

  // vertex = 1
  // vertex = in graph
  @Test
  public void testgetdistancesame() throws ExceptionofInput, IOException {
    Graph<Vertex, Edge> graph = Graph.empty();
    graph.addVertex(v1);

    assertEquals("expected distance 1", 1, graph.getdistance(v1, v1));
  }

  // vertex = 1
  // vertex = not in graph
  @Test
  public void testgetdistancenotin() throws ExceptionofInput, IOException {
    Graph<Vertex, Edge> graph = Graph.empty();
    graph.addVertex(v1);

    assertEquals("expected distance 1", 0, graph.getdistance(v1, v2));
  }

  // vertex = n
  // vertex = in graph
  @Test
  public void testgetdistancen() throws ExceptionofInput, ExceptionofUnproperEdge, IOException {
    Graph<Vertex, Edge> graph = Graph.empty();
    graph.addVertex(v1);
    graph.addVertex(v2);
    graph.addVertex(v3);
    graph.addVertex(v4);
    graph.addVertex(v5);
    WordNeighborhood e11 = new WordNeighborhood("edge", v1, v2, 1.0);
    WordNeighborhood e12 = new WordNeighborhood("edge", v1, v3, 1.0);
    WordNeighborhood e13 = new WordNeighborhood("edge", v1, v4, 1.0);
    WordNeighborhood e14 = new WordNeighborhood("edge", v1, v5, 1.0);
    WordNeighborhood e15 = new WordNeighborhood("edge", v3, v4, 1.0);
    WordNeighborhood e16 = new WordNeighborhood("edge", v4, v5, 1.0);
    graph.addEdge(e11);
    graph.addEdge(e12);
    graph.addEdge(e13);
    graph.addEdge(e14);
    graph.addEdge(e15);
    graph.addEdge(e16);
    // System.out.println(graph.targets(v1).size());
    // System.out.println(graph.getdistance(v1, v4));
    // System.out.println(graph.getdistance(v3, v5));
    assertEquals("expected distance 1", 1, graph.getdistance(v1, v2));
    assertEquals("expected distance 2", 2, graph.getdistance(v3, v5));
  }
}
