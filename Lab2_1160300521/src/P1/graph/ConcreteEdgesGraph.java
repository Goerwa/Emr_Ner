/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package P1.graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * An implementation of Graph.
 * 
 * <p>PS2 instructions: you MUST use the provided rep.
 */
public class ConcreteEdgesGraph<L> implements Graph<L> {
    
    private final Set<L> vertices = new HashSet<>();
    private final List<Edge<L>> edges = new ArrayList<>();
    
    // Abstraction function:
    // AF(vertices,Edge) = {vertices,edge|vertices != null && edges'weight >0}.
    //
    // Representation invariant:
    // all vertices are not null and all edges'weight are greater than 0.
    //
    // Safety from rep exposure:
    // All fileds are private
 	// All return value types are mutable, create new variables to prevent sharing with clients.
    //
    
    // TODO constructor
    public ConcreteEdgesGraph() {
    		checkRep();
    }
    // TODO checkRep
    private void checkRep() {
    		assert vertices != null;
    		for(Edge<L> edge:edges) {
    			assert edge.getweight() >= 0;
    		}
    }
    
    @Override public boolean add(L vertex) {
        //throw new RuntimeException("not implemented");
    		boolean r;
    		if(vertex != null)
    			r = vertices.add(vertex);
    		else
    			return false;
    		checkRep();
    		return r;
    }
    
    @Override public int set(L source, L target, int weight) {
        //throw new RuntimeException("not implemented");
    		for(Edge<L> edge:edges) {
    			if(source.equals(edge.getsource()) && target.equals(edge.gettarget())) {
    				int weightfind = edge.getweight();
    				if(weight == 0) {
    					edges.remove(edge);
    					checkRep();
    					return weightfind;
    				}else{
    					edges.remove(edge);
    					Edge<L> edgenew = new Edge<L>(source,target,weight);
    					edges.add(edgenew);
    					checkRep();
    					return weightfind;
    				}    				
    			}
    		}
    		
    		if(weight > 0) {
    			if(!vertices.contains(source))
    				vertices.add(source);
    			if(!vertices.contains(target))
    				vertices.add(target);
    			Edge<L> edgenew = new Edge<L>(source,target,weight);
			edges.add(edgenew);
			checkRep();
    			return 0;
    		}
    		checkRep();
    		return 0;   		
    }
    
    @Override public boolean remove(L vertex) {
        //throw new RuntimeException("not implemented");
    		if (!vertices.contains(vertex)) {
            checkRep();
            return false;
        }
    		vertices.remove(vertex);
        for (Iterator<Edge<L>> iterator = edges.iterator(); iterator.hasNext();) {
        		Edge<L> edge = iterator.next();
        		if (edge.getsource().equals(vertex) || edge.gettarget().equals(vertex)) {
        			iterator.remove();
        		}
        }
        checkRep();
        return true;	
    }
    
    @Override public Set<L> vertices() {
        //throw new RuntimeException("not implemented");
    		return new HashSet<L>(vertices);
    }
    
    @Override public Map<L, Integer> sources(L target) {
        //throw new RuntimeException("not implemented");
    		Map<L, Integer> result = new HashMap<>();
    		for(Edge<L> edge:edges) {
    			if(target.equals(edge.gettarget()))
    				result.put(edge.getsource(), edge.getweight());
    		}
    		
    		checkRep();
    		return result;
    }
    
    @Override public Map<L, Integer> targets(L source) {
        //throw new RuntimeException("not implemented");
    		Map<L, Integer> result = new HashMap<>();
    		for(Edge<L> edge:edges) {
    			if(source.equals(edge.getsource()))
    				result.put(edge.gettarget(), edge.getweight());
    		}
    		
    		checkRep();
    		return result;
    }
    
    // TODO toString()
    @Override public String toString() {
        String result = "";
        for (Edge edge : edges) {
            result += edge.toString() + "\n"; 
        }
        return result;
    }
}

/**
 * TODO specification
 * Immutable.
 * This class is internal to the rep of ConcreteEdgesGraph.
 * 
 * <p>PS2 instructions: the specification and implementation of this class is
 * up to you.
 * @param <L>
 */
class Edge<L>{
    
    // TODO fields
    
    // Abstraction function:
    // AF(Edge) = {edge|source ！= null && target ！= null && weight >0}.
	//
    // Representation invariant:
    // Neither source nor target can be null and weight must greater than 0.
	//
    // Safety from rep exposure:
    // All fileds are private
	// the get methods can prevent sharing data with clients.
	
	private final L source;
	private final L target;
	private final Integer weight;
	 
    // TODO constructor
	public Edge(L source, L target, Integer weight) {
         this.source = source;
         this.target = target;
         this.weight = weight;
     }
    // TODO checkRep
    private void checkRep() {
    	assert source != null;
    	assert target != null;
    	assert weight > 0;
    }
    // TODO methods
    public L getsource() {
    	return source;
    }
    
    public L gettarget() {
    	return target;
    }
    
    public Integer getweight() {
    	return weight;
    }
    // TODO toString()
    @Override public String toString() {
    	return source +"->" + target +"="+ weight;
    }
   
}
