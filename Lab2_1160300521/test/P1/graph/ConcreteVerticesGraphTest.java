/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package P1.graph;

import static org.junit.Assert.*;

import java.util.Map;

import org.junit.Test;

/**
 * Tests for ConcreteVerticesGraph.
 * 
 * This class runs the GraphInstanceTest tests against ConcreteVerticesGraph, as
 * well as tests for that particular implementation.
 * 
 * Tests against the Graph spec should be in GraphInstanceTest.
 */
public class ConcreteVerticesGraphTest extends GraphInstanceTest {
    
	
	private static final String vertex1 = "v1";
    private static final String vertex2 = "v2";
    private static final String vertex3 = "v3";
    private static final String vertex4 = "v4";
    private static final String vertex5 = "v5";
    
    private static final int weight1 = 1;
    private static final int weight2 = 2;
    private static final int weight3 = 3;
    private static final int weight4 = 4;
    /*
     * Provide a ConcreteVerticesGraph for tests in GraphInstanceTest.
     */
    @Override public Graph<String> emptyInstance() {
        return new ConcreteVerticesGraph();
    }
    
    /*
     * Testing ConcreteVerticesGraph...
     */
    
    //test an empty graph
    @Test
    public void testToStringemptyGragh() {
        Graph<String> graph = emptyInstance();
        String expected = "";
        
        assertEquals("expected empty string", expected, graph.toString());
    }
    
    //test a graph of one vertex
    @Test
    public void testToStringOneVertex() {
        Graph<String> graph = emptyInstance();
        graph.add(vertex1);
        
        String expected = "v1: inEdges{}\toutEdges{}\n";
        
        assertEquals("expected string", expected, graph.toString());
    }
    
    //test a graph of one edge
    @Test
    public void testToStringOneEdge() {
        Graph<String> graph = emptyInstance();
        graph.add(vertex1);
        graph.add(vertex2);
        graph.set(vertex1, vertex2, weight1);
        
        String expected = "v1: inEdges{}\toutEdges{v2=1}\nv2: inEdges{v1=1}\toutEdges{}\n";
        
        
        assertEquals("expected string", expected, graph.toString());
    }
    
    //test a graph of two edge
    @Test
    public void testToStringtwoEdges() {
        Graph<String> graph = emptyInstance();
        graph.add(vertex1);
        graph.add(vertex2);
        graph.add(vertex3);
        graph.set(vertex1, vertex2, weight1);
        graph.set(vertex1, vertex3, weight2);
        
        String expected = "v1: inEdges{}\toutEdges{v2=1, v3=2}\nv2: inEdges{v1=1}\toutEdges{}\n" 
                          + "v3: inEdges{v1=2}\toutEdges{}\n";
        
        assertEquals("expected string", expected, graph.toString());
    }
    // Testing strategy for ConcreteVerticesGraph.toString()
    //   TODO
    
    // TODO tests for ConcreteVerticesGraph.toString()
    
    /*
     * Testing Vertex...
     */
    
    // Testing strategy for Vertex
    //   TODO
    
    // TODO tests for operations of Vertex
    //test getting lable
    @Test
    public void testGetlabel() {
        Vertex<String> v1 = new Vertex<String>(vertex1);
        
        assertEquals("expected to get name 'v1'", v1.getlabel(), vertex1);
    }
    

    //test a vertex with no inedge and outedge
    @Test
    public void testGetnulleVertex() {
        Vertex<String> v1 = new Vertex<String>(vertex1);
        
        Map<String, Integer> map1 = v1.getincoming();
        Map<String, Integer> map2 = v1.getoutcoming();
        
        assertTrue("expected empty map", map1.isEmpty());
        assertTrue("expected empty map", map2.isEmpty());
    }

  //test getting a vertex 
    @Test
    public void testGetOneVertex() {
        Vertex<String> v1 = new Vertex<String>(vertex1);
        Vertex<String> v2 = new Vertex<String>(vertex2);
        Vertex<String> v3 = new Vertex<String>(vertex3);
        v1.addincoming(v2, weight1);
        v1.addoutcoming(v3, weight2);
        
        Map<String, Integer> map1 = v1.getincoming();
        Map<String, Integer> map2 = v1.getoutcoming();
        
        assertTrue("expected map size 1", map1.size() == 1);
        assertTrue("expected map to contain key", map1.containsKey(vertex2));
        assertTrue("expected map size 1", map2.size() == 1);
        assertTrue("expected map to contain key", map2.containsKey(vertex3));
    }
    
  //test getting multiple vertex
    @Test
    public void testGetNVertex() {
        Vertex<String> v1 = new Vertex<String>(vertex1);
        v1.addincoming(new Vertex<String>(vertex2), weight1);
        v1.addoutcoming(new Vertex<String>(vertex3), weight2);
        v1.addincoming(new Vertex<String>(vertex4), weight3);
        v1.addoutcoming(new Vertex<String>(vertex5), weight4);
        
        Map<String, Integer> map1 = v1.getincoming();
        Map<String, Integer> map2 = v1.getoutcoming();
        
        assertTrue("expected map size 2", map1.size() == 2);
        assertTrue("expected map to contain key", map1.containsKey(vertex2));
        assertTrue("expected map to contain key", map1.containsKey(vertex4));
        assertTrue("expected map size 2", map2.size() == 2);
        assertTrue("expected map to contain key", map2.containsKey(vertex3));
        assertTrue("expected map to contain key", map2.containsKey(vertex5));
    }
    
    //test adding notpresent vertex
    @Test
    public void testAddNotPresent() {
        Vertex<String> v1 = new Vertex<String>(vertex1);
        Vertex<String> v2 = new Vertex<String>(vertex2);
        Vertex<String> v3 = new Vertex<String>(vertex3);
        
        Integer oldWeight1 = v1.addincoming(v2, weight1);
        Integer oldWeight2 = v1.addoutcoming(v3, weight2);
        Map<String, Integer> map1 = v1.getincoming();
        Map<String, Integer> map2 = v1.getoutcoming();
        Map<String, Integer> map3 = v2.getoutcoming();
        Map<String, Integer> map4 = v3.getincoming();
        
        assertTrue("expected old weight equals 0", oldWeight1 == 0);
        assertTrue("expected old weight equals 0", oldWeight2 == 0);
        assertTrue("expected map size 1", map1.size() == 1);
        assertTrue("expected map size 1", map2.size() == 1);
        assertTrue("expected map size 1", map3.size() == 1);
        assertTrue("expected map size 1", map4.size() == 1);
        assertTrue("expected map to contain key", map1.containsKey(vertex2));
        assertTrue("expected map to contain key", map2.containsKey(vertex3));
        assertTrue("expected map to contain key", map3.containsKey(vertex1));
        assertTrue("expected map to contain key", map4.containsKey(vertex1));
        assertTrue("expected new weight equals 1", map1.get(vertex2) == weight1);
        assertTrue("expected new weight equals 2", map2.get(vertex3) == weight2);
        assertTrue("expected new weight equals 1", map3.get(vertex1) == weight1);
        assertTrue("expected new weight equals 2", map4.get(vertex1) == weight2);
    }
    
  //test adding present vertex
    @Test
    public void testAddPresent() {
        Vertex<String> v1 = new Vertex<String>(vertex1);
        Vertex<String> v2 = new Vertex<String>(vertex2);
        Vertex<String> v3 = new Vertex<String>(vertex3);
        v1.addincoming(v2, weight1);
        v1.addoutcoming(v3, weight2);
        
        Integer oldWeight1 = v1.addincoming(v2, weight3);
        Integer oldWeight2 = v1.addoutcoming(v3, weight4);
        Map<String, Integer> map1 = v1.getincoming();
        Map<String, Integer> map2 = v1.getoutcoming();
        Map<String, Integer> map3 = v2.getoutcoming();
        Map<String, Integer> map4 = v3.getincoming();
        
        assertTrue("expected weight equals 1", oldWeight1 == weight1);
        assertTrue("expected weight equals 2", oldWeight2 == weight2);
        assertTrue("expected map size 1", map1.size() == 1);
        assertTrue("expected map size 1", map2.size() == 1);
        assertTrue("expected map size 1", map3.size() == 1);
        assertTrue("expected map size 1", map4.size() == 1);
        assertTrue("expected map to contain key", map1.containsKey(vertex2));
        assertTrue("expected map to contain key", map2.containsKey(vertex3));
        assertTrue("expected map to contain key", map3.containsKey(vertex1));
        assertTrue("expected map to contain key", map4.containsKey(vertex1));
        assertTrue("expected new weight equals 3", map1.get(vertex2) == weight3);
        assertTrue("expected new weight equals 4", map2.get(vertex3) == weight4);
        assertTrue("expected new weight equals 3", map3.get(vertex1) == weight3);
        assertTrue("expected new weight equals 4", map4.get(vertex1) == weight4);
    }
    
    //test removing notpresent vertex
    @Test
    public void testRemoveNotPresent() {
       Vertex<String> v1 = new Vertex<String>(vertex1);
       
       Boolean isIn1 = v1.removeincoming(new Vertex<String>(vertex2));
       Boolean isIn2 = v1.removeoutcoming(new Vertex<String>(vertex3));
       
       assertFalse("expected empty edge", isIn1);
       assertFalse("expected empty edge", isIn2);
    }
    
    //test removing present vertex
    @Test
    public void testRemovePresent() {
       Vertex<String> v1 = new Vertex<String>(vertex1);
       Vertex<String> v2 = new Vertex<String>(vertex2);
       Vertex<String> v3 = new Vertex<String>(vertex3);
       v1.addincoming(v2, weight1);
       v1.addoutcoming(v3, weight2);
       
       Boolean isIn1 = v1.removeincoming(v2);
       Boolean isIn2 = v1.removeoutcoming(v3);
       Map<String, Integer> map1 = v1.getincoming();
       Map<String, Integer> map2 = v1.getoutcoming();
       Map<String, Integer> map3 = v2.getoutcoming();
       Map<String, Integer> map4 = v3.getincoming();
       
       assertTrue("expected present edge", isIn1);
       assertTrue("expected present edge", isIn2);
       assertTrue("expected map size 0", map1.size() == 0);
       assertTrue("expected map size 0", map2.size() == 0);
       assertTrue("expected map size 0", map3.size() == 0);
       assertTrue("expected map size 0", map4.size() == 0);
    }
    
    //Test toString
    @Test
    public void testVertexToString(){
        Vertex<String> v1 = new Vertex<String>(vertex1);
        
        v1.addoutcoming(new Vertex<String>(vertex2), weight1);
        v1.addincoming(new Vertex<String>(vertex3), weight2);
        
        String expected = "v1: inEdges{v3=2}\toutEdges{v2=1}\n";
        
        assertEquals("expected string", expected, v1.toString());
    }
}
