/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package P1.graph;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Tests for ConcreteEdgesGraph.
 * 
 * This class runs the GraphInstanceTest tests against ConcreteEdgesGraph, as
 * well as tests for that particular implementation.
 * 
 * Tests against the Graph spec should be in GraphInstanceTest.
 */
public class ConcreteEdgesGraphTest extends GraphInstanceTest {
    
	private static final String vertex1 = "v1";
    private static final String vertex2 = "v2";
    private static final String vertex3 = "v3";
    
    private static final int weight1 = 1;
    private static final int weight2 = 2;
    
    /*
     * Provide a ConcreteEdgesGraph for tests in GraphInstanceTest.
     */
    @Override public Graph<String> emptyInstance() {
        return new ConcreteEdgesGraph();
    }
    
    /*
     * Testing ConcreteEdgesGraph...
     */
    
    // Testing strategy for ConcreteEdgesGraph.toString()
    //   TODO
    
    // TODO tests for ConcreteEdgesGraph.toString()
    
    @Test
    public void testToStringZeroVerticesZeroEdges() {
        Graph<String> graph = emptyInstance();
        
        String expected = "";
        
        assertEquals("expected empty string", expected, graph.toString());
    }
    
    // covers num vertices = 1
    @Test
    public void testToStringOneVertex() {
        Graph<String> graph = emptyInstance();
        graph.add(vertex1);
        
        String expected = "";
        
        assertEquals("expected string", expected, graph.toString());
    }
    
    // covers num vertices = n
    //        num edges = 1
    @Test
    public void testToStringNVerticesOneEdge() {
        Graph<String> graph = emptyInstance();
        graph.add(vertex1);
        graph.add(vertex2);
        graph.set(vertex1, vertex2, weight1);
        
        String expected = "v1->v2=1\n";
        
        assertEquals("expected string", expected, graph.toString());
    }
    
    // covers num edges = n
    @Test
    public void testToStringNEdges() {
        Graph<String> graph = emptyInstance();
        graph.add(vertex1);
        graph.add(vertex2);
        graph.add(vertex3);
        graph.set(vertex1, vertex2, weight1);
        graph.set(vertex1, vertex3, weight2);
        
        String expected = "v1->v2=1\nv1->v3=2\n";
        
        assertEquals("expected string", expected, graph.toString());
    }
    /*
     * Testing Edge...
     */
    
    // Testing strategy for Edge
    //   TODO
    
    // TODO tests for operations of Edge
    
    @Test 
    public void testGet() {
       Edge<String> edge = new Edge<String>(vertex1, vertex2, weight1);
       
       assertEquals("expected string 'v2'", edge.gettarget(), "v2");
       assertEquals("expected string 'v1'", edge.getsource(), "v1");
       assertEquals("expected string '1'", edge.getweight(), new Integer(1));
    }
    
    // test toString
    @Test
    public void testToString() {
        Edge<String> edge = new Edge<String>(vertex1, vertex2, weight1);
        
        String expected = "v1->v2=1";
        
        assertEquals("expected string", expected, edge.toString());
    }
}
