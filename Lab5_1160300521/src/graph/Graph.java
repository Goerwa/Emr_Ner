package graph;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import Exception.ExceptionofInput;
import Exception.ExceptionofUnproperEdge;


/**
 * interface graph.
 * @author goerwa
 *
 * @param <L>
 * vertex
 * @param <E>
 * edge
 */
public interface Graph<L, E> {


  /**
   * Create an empty graph.
   * @param <L>
   * type of vertex in the graph, must be immutabl
   * @param <E>
   * type of Edge in the graph, must be immutabl
   * @return a new empty weighted directed graph
   */
  public static <L, E> Graph<L, E> empty() {
    return (Graph<L, E>) new ConcreteGraph();
  }

  /**
   * Add a vertex to this graph.
   * @param v the new vertex
   * @return true if this graph did not already
   * include a vertex with the given label;
   * otherwise false (and this graph is not modified)
   * @throws ExceptionofInput ExceptionofInput
   * @throws IOException IOException
   */
  boolean addVertex(L v) throws ExceptionofInput, IOException;

  /**
   * Remove a vertex from this graph.
   * @param v
   * the vertex to remove
   * @return
   * true if this graph included a vertex with the given;
   * otherwise false (and this graph is not modified)
   */
  boolean removeVertex(L v);

  /**
   * Get all the vertices in this graph.
   * @return the set of vertices in this graph
   */
  Set<L> vertices();

  /**
   * Get the source vertices with directed edges to a target
   * vertex and the weights of those edges.
   * @param target
   * a label
   * @return
   * a map where the key set is the set of labels of
   *  vertices such that this graph includes an edge
   *  from that vertex to target, and the value for each key is
   *  a list of the weight of the edge from the key to target
   */
  Map<L, List<Double>> sources(L target);

  /**
   * Get the target vertices with directed edges from
   * a source vertex and the weights of those edges.
   * @param source a label
   * @return a map where the key set is the set of labels of vertices
   * such that this graph includes an edge from source to that vertex,
   * and the value for each key is a list of the weight weight of the
   * edge from source to the key
   */
  Map<L, List<Double>> targets(L source);

  /**
   * Get the source vertices with directed edges to a target vertex
   * and the weights of those edges.
   * @param target a label
   * @return a map where the key set is the set of labels of vertices
   * such that this graph includes an edge from that vertex to target,
   * and the value for each key is the (nonzero) weight of the edge
   * from the key to target
   */
  Map<L, Double> sourcesone(L target);

  /**
   * Get the target vertices with directed edges from a source vertex
   * and the weights of those edges.
   * @param source a label
   * @return a map where the key set is the set of labels of vertices
   * such that this graph includes an edge from source to that vertex,
   * and the value for each key is the (nonzero) weight of the edge
   * from source to the key
   */
   Map<L, Double> targetsone(L source);

  /**
   * Add an edge to this graph.
   * @param edge the new edge
   * @return true if this graph did not already include
   * a vertex with the given label; otherwise false
   * (and this graph is not modified)
   * @throws ExceptionofInput ExceptionofInput
   * @throws ExceptionofUnproperEdge ExceptionofUnproperEdge
   * @throws IOException IOException
   */
  boolean addEdge(E edge) throws ExceptionofInput,
  ExceptionofUnproperEdge, IOException;

  /**
   * any edges to be removed,but related vertex not be removed.
   * @param edge the edge to remove
   * @return true if this graph included an edge; otherwise
   * false (and this graph is not modified)
   * @throws ExceptionofInput ExceptionofInput
   * @throws ExceptionofUnproperEdge ExceptionofUnproperEdge
   * @throws IOException  IOException
   */
  boolean removeEdge(E edge) throws ExceptionofInput,
  ExceptionofUnproperEdge, IOException;

  /**
   * Get all the edges in this graph.
   * @return the set of edges in this graph
   */
  Set<E> edges();

  /**
   * Aconfirm weather an edge is in graph.
   * @param edge the edge
   * @return true if this graph include the edge; otherwise false
   */
   boolean containedge(E edge);

  /**
   * confirm the distance of two vertices.
   * @param v1 the source vertex;
   * @param v2 the target vertex
   * @return if two points are connected, return the
   * corresponding distance; else if source and
   * target are the same, return 1; otherwise return 0
   */
  int getdistance(L v1, L v2);

  /**
   * find vertex by label.
   * @param str label of vertex
   * @return vertex if find, otherwise teturn null
   */
  L findvertexbylabel(String str);
}
