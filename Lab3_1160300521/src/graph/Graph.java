package graph;

import java.util.List;
import java.util.Map;
import java.util.Set;



public interface Graph<L,E> {
	
	
	/**
     * Create an empty graph.
     * 
     * @param <L> type of vertex in the graph, must be immutabl
     * 		  <E>type of Edge in the graph, must be immutabl
     * 			
     * @return a new empty weighted directed graph
     */
	public static <L,E> Graph<L,E> empty() {
		//throw new RuntimeException("not implemented");
		return (Graph<L, E>) new ConcreteGraph();
    }
	/**
     * Add a vertex to this graph.
     * 
     * @param  the new vertex
     * @return true if this graph did not already include a vertex with the
     *         given label; otherwise false (and this graph is not modified)
     */
    public boolean addVertex(L v);  
    /**
     * Remove a vertex from this graph;
     * 
     * @param  the vertex to remove
     * @return true if this graph included a vertex with the given;
     *         otherwise false (and this graph is not modified)
     */
    public boolean removeVertex(L v);
    /**
     * Get all the vertices in this graph.
     * 
     * @return the set of vertices in this graph
     */
    public Set<L> vertices();
    /**
     * Get the source vertices with directed edges to a target vertex and the
     * weights of those edges.
     * 
     * @param target a label
     * @return a map where the key set is the set of labels of vertices such
     *         that this graph includes an edge from that vertex to target, and
     *         the value for each key is a list of the weight of the edge from
     *         the key to target
     */
    public Map<L, List<Double>> sources(L target);
    /**
     * Get the target vertices with directed edges from a source vertex and the
     * weights of those edges.
     * 
     * @param source a label
     * @return a map where the key set is the set of labels of vertices such
     *         that this graph includes an edge from source to that vertex, and
     *         the value for each key is a list of the weight weight of the edge from
     *         source to the key
     */
    public Map<L, List<Double>> targets(L source);
    /**
     * Get the source vertices with directed edges to a target vertex and the
     * weights of those edges.
     * 
     * @param target a label
     * @return a map where the key set is the set of labels of vertices such
     *         that this graph includes an edge from that vertex to target, and
     *         the value for each key is the (nonzero) weight of the edge from
     *         the key to target
     */
    public Map<L, Double> sourcesone(L target);
    /**
     * Get the target vertices with directed edges from a source vertex and the
     * weights of those edges.
     * 
     * @param source a label
     * @return a map where the key set is the set of labels of vertices such
     *         that this graph includes an edge from source to that vertex, and
     *         the value for each key is the (nonzero) weight of the edge from
     *         source to the key
     */
    public Map<L, Double> targetsone(L source);
    /**
     * Add an edge to this graph.
     * 
     * @param the new edge
     * @return true if this graph did not already include a vertex with the
     *         given label; otherwise false (and this graph is not modified)
     */
    public boolean addEdge(E edge);
    /**
     * any edges to be removed,but related vertex not be removed.
     * 
     * @param  the edge to remove
     * @return true if this graph included an edge;
     *         otherwise false (and this graph is not modified)
     */
    public boolean removeEdge(E edge);
    /**
     * Get all the edges in this graph.
     * 
     * @return the set of edges in this graph
     */
    public Set<E> edges();
    /**
     * Aconfirm weather an edge is in graph
     * 
     * @param  the edge
     * @return true if this graph include the edge;
     *         otherwise false
     */
    public boolean containedge(E edge);
    /**
     * Aconfirm the distance of two vertices an 
     * 
     * @param  the source vertex;the target vertex
     * @return if two points are connected, return the corresponding distance;
     * 		   else if source and target are the same, return 1;
     *         otherwise return 0
     */
    public int getdistance(L v1,L v2);
}
