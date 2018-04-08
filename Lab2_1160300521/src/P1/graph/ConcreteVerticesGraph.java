/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package P1.graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * An implementation of Graph.
 * 
 * <p>PS2 instructions: you MUST use the provided rep.
 */
public class ConcreteVerticesGraph<L> implements Graph<L> {
    
    private final List<Vertex<L>> vertices = new ArrayList<>();
    
    // Abstraction function:
    // AF(vertices) = {vertices|inedge'weight > 0 && outedge'weight > 0}
    //
    // Representation invariant:
    // inedge'weight and outedge'weight are greater than 0.
    //
    // Safety from rep exposure:
    // All fileds are private
 	// All return value types are mutable, create new variables to prevent sharing with clients.
    // 
    
    // TODO constructor
    public ConcreteVerticesGraph() {
        checkRep();
    }
    // TODO checkRep
    private void checkRep() {
        for (Vertex<L> vertex : vertices) {
            Map<L, Integer> inEdges = vertex.getincoming();
            Map<L, Integer> outEdges = vertex.getoutcoming();
            
            for (Integer weight : inEdges.values()) {
                assert weight > 0;
            }
            for (Integer weight : outEdges.values()) {
                assert weight > 0;
            }
        }
    }
    
    private boolean inGraph(L label){
        Set<L> set = new HashSet<>();
        
        for (Vertex<L> vertex: vertices){
           set.add(vertex.getlabel());
        }
        
        return set.contains(label);
    }
    
    private Vertex<L> findVertex(L label) {
        for (Vertex<L> vertex: vertices) {
            if (vertex.getlabel().equals(label)) {
                return vertex;
            }
        }
        // if vertex was not present, throw error
        throw new AssertionError("Vertex not in graph");
    }
    
    @Override public boolean add(L vertex) {
    		if (inGraph(vertex)) {
            checkRep();
            return false;
        }
        else {
            vertices.add(new Vertex<L>(vertex));
            checkRep();
            return true;
        }
    }
    
    @Override public int set(L source, L target, int weight) {
        //throw new RuntimeException("not implemented");
    	// find vertices in the graph, create new if they don't exist and weight > 0
        Vertex<L> srcVertex;
        if (inGraph(source)) {
            srcVertex = findVertex(source);
        }
        else {
            srcVertex = new Vertex<L>(source);
            if (weight > 0) {
                vertices.add(srcVertex);
                }
        }
        Vertex<L> trgVertex;
        if (inGraph(target)) {
            trgVertex = findVertex(target);
        }    
        else {   
            trgVertex = new Vertex<L>(target);
            if (weight > 0) {
                vertices.add(trgVertex);
                }
        }
        // if edge exists - update or remove it
        Map<L, Integer> srcOutEdges = srcVertex.getoutcoming();
        Map<L, Integer> trgInEdges = trgVertex.getincoming();
        if (srcOutEdges.containsKey(target) && trgInEdges.containsKey(source)) {
            Integer oldWeight = srcOutEdges.get(target);
            if (weight == 0) {
                srcVertex.removeoutcoming(trgVertex);
                return oldWeight;
            }
            srcVertex.addoutcoming(trgVertex, weight);
            return oldWeight;
        }
        else {
            if (weight > 0) {
                srcVertex.addoutcoming(trgVertex, weight);
                return 0;
            }
        }
        return 0;
    }
    
    @Override public boolean remove(L vertex) {
        //throw new RuntimeException("not implemented");
    		if (inGraph(vertex)) {
            Vertex<L> vertexToRemove = findVertex(vertex);
            // remove edges
            for (Vertex<L> vertexTemp : vertices) {
                vertexTemp.removeincoming(vertexToRemove);
                vertexTemp.removeoutcoming(vertexToRemove);
            }
            vertices.remove(vertexToRemove);
            checkRep();
            return true;
        }
        else {
            return false;
        }
    }
    
    @Override public Set<L> vertices() {
        //throw new RuntimeException("not implemented");
    		Set<L> result = new HashSet<>();
        for (Vertex<L> vertex : vertices) {
        		if(vertex != null)
            result.add(vertex.getlabel());
        }
        return result;
    }
    
    @Override public Map<L, Integer> sources(L target) {
        //throw new RuntimeException("not implemented");
    		if (inGraph(target)) {
            Vertex<L> targetVertex = findVertex(target);
            return targetVertex.getincoming();
        }
        return new HashMap<L, Integer>();
    }
    
    @Override public Map<L, Integer> targets(L source) {
        //throw new RuntimeException("not implemented");
    		if (inGraph(source)) {
            Vertex<L> sourceVertex = findVertex(source);
            return sourceVertex.getoutcoming();
        }
        return new HashMap<L, Integer>();
    }
    
    // TODO toString()
    @Override public String toString() {
        String result = "";
        for (Vertex<L> vertex : vertices) {
            result += vertex.toString();
        }
        return result;
    }
}

/**
 * TODO specification
 * Mutable.
 * This class is internal to the rep of ConcreteVerticesGraph.
 * 
 * <p>PS2 instructions: the specification and implementation of this class is
 * up to you.
 */
class Vertex<L> {
    
    // TODO fields
    
    // Abstraction function:
    // AF(Vertex) = {vertex|label ！= null && inedges'weight >0 && outedges'weight >0}.
	//
    // Representation invariant:
    // label cannot be null and both inedges'weight and outedges'weight are greater than 0.
	//
    // Safety from rep exposure:
	// All fileds are private
	// the get methods can prevent sharing data with clients.
	// the remove methods only return true or false.
	// the add mothods return immutable type or 0
	
	
	private final L label;
	private final Map<L, Integer> inEdges = new HashMap<>();
	private final Map<L, Integer> outEdges = new HashMap<>();
    
	// TODO constructor
	public Vertex(L label) {
        this.label = label;
        checkRep();
    }
	
    // TODO checkRep
	private void checkRep() {
        assert label != null;
        for (Integer weight: inEdges.values())
            assert weight > 0;
        for (Integer weight : outEdges.values())
            assert weight > 0;
    }
    // TODO methods
	public L getlabel() {
        return label;
    }
	
	public Map<L, Integer> getincoming() {
        return new HashMap<L, Integer>(inEdges);
    }
	
	public Map<L, Integer> getoutcoming() {
        return new HashMap<L, Integer>(outEdges);
    }
	
	public Integer addincoming(Vertex<L> source, Integer weight){
        if (inEdges.containsKey(source.getlabel())) {
            Integer weightfind = inEdges.put(source.getlabel(), weight);
            source.outEdges.put(this.getlabel(), weight);
            checkRep();
            return weightfind;
        }
        else {
            inEdges.put(source.getlabel(), weight);
            source.outEdges.put(this.getlabel(), weight);
            checkRep();
            return 0;
        }
    }
	
	public Integer addoutcoming(Vertex<L> target, Integer weight){
        if (outEdges.containsKey(target.getlabel())) {
            Integer weightfind = outEdges.put(target.getlabel(), weight);
            target.inEdges.put(this.getlabel(), weight);
            checkRep();
            return weightfind;
        }
        else {
            outEdges.put(target.getlabel(), weight);
            target.inEdges.put(this.getlabel(), weight);
            checkRep();
            return 0;
        }
    }
	
	public boolean removeincoming(Vertex<L> source) {
        if (inEdges.containsKey(source.getlabel())) {
            inEdges.remove(source.getlabel());
            source.outEdges.remove(this.getlabel());
            checkRep();
            return true;
        }
        else {
            checkRep();
            return false;
        }
    }
	
	public boolean removeoutcoming(Vertex<L> target) {
        if (outEdges.containsKey(target.getlabel())) {
            outEdges.remove(target.getlabel());
            target.inEdges.remove(this.getlabel());
            checkRep();
            return true;
        }
        else {
            checkRep();
            return false;
        }
    }
    
    @Override public String toString() {
        return label + ": inEdges" + getincoming().toString() 
               + "\t"+"outEdges" + getoutcoming().toString() + "\n";
    }
    // TODO toString()
    
    
    
    
    
    
}
