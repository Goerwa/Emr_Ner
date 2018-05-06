package edge;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import vertex.Vertex;

public abstract class Edge {
	
    // Abstraction function:
    // AF(Edge) = {edge|source ！= null && target ！= null }.
	//
    // Representation invariant:
    // Neither source nor target can be null 
	//
    // Safety from rep exposure:
    // All fileds are private
	// the get methods can prevent sharing data with clients.
	
	private final Vertex source;
	private final Vertex target;
	private final List<Vertex> vers = new ArrayList<>();
	private final Double weight;
	private final String label; 
    // TODO constructor
	public Edge(String label,Vertex source, Vertex target, Double weight) {
		 this.label = label;
         this.source = source;
         this.target = target;
         this.vers.add(0, source);
         this.vers.add(1, target);
         this.weight = weight;
     }
	
	public Edge(Vertex source, Vertex target, Double weight) {
        this.label = "name";
		this.source = source;
        this.target = target;
        this.vers.add(0, source);
        this.vers.add(1, target);
        this.weight = weight;
    }
	
	public Edge(String label,Double weight) {
        this.label = "name";
		this.source = null;
        this.target = null;
        this.weight = weight;
    }
	
	public Edge(String label) {
        this.label = "name";
		this.source = null;
        this.target = null;
        this.weight = 1.0;
    }
	
	boolean containVertex(Vertex v) {
		for(Vertex vertex:vers) {
			if(vertex.equals(v))
				return true;
		}
		return false;
	}
	abstract public boolean addVertices(List<Vertex> vertices);
    abstract Set<Vertex> sourceVertices();
    abstract Set<Vertex> targetVertices();
    
    public void addvers(Vertex v1,Vertex v2) {
    		this.vers.add(0,v1);
    		this.vers.add(1,v2);
    }
    @Override 
	public int hashCode() {  
		int result = 17;  
	    result = 31 * result + label.hashCode() + weight.hashCode();	    
	    return result;  
	}  	
    // TODO methods
    public Vertex getsource() {
    	return source;
    }
    
    public Vertex gettarget() {
    	return target;
    }
    
    public Double getweight() {
    	return weight;
    }
    
    public String getlabel() {
    	return label;
    }
    
    public String tellclass() {
    		return "Edge";
    }
    // TODO toString()
    @Override public String toString() {
    	return source +"->" + target +"="+ weight;
    }
    
    @Override public boolean equals(Object obj) {
    		Edge e = (Edge) obj;
		if((e.gettarget().getlabel().equals(this.gettarget().getlabel())) && 
				(e.getsource().getlabel().equals(this.getsource().getlabel())) ) 
				return true;
		if((e.gettarget().getlabel().equals(this.getsource().getlabel())) && 
				(e.getsource().getlabel().equals(this.gettarget().getlabel())) )
				return true;
		else {
			//System.out.println("not equals");
			return false;
		}
	}
}
