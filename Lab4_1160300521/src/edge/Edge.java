package edge;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import vertex.Vertex;

public abstract class Edge {
	
    // Abstraction function:
    // AF(Edge) = {edge|label != null && source ！= null && target ！= null }.
	//
    // Representation invariant:
	// label cannot be null
    // Neither source nor target can be null 
	//
    // Safety from rep exposure:
    // All fileds are private
	// the get methods can prevent sharing data with clients.
	

	private final List<Vertex> vers = new ArrayList<>();
	private final Double weight;
	private String label; 
	private int number = 0;
    // TODO constructor
	public Edge(String label,Double weight) {
        this.label = "name";
        this.weight = weight;
    }
	public Edge(Vertex src,Vertex arg,Double weight) {
        this.label = "name";
        this.vers.add(0, src);
        this.vers.add(1, arg);
        this.weight = weight;
        checkRep();
    }
	public Edge(String label,Vertex src,Vertex arg,Double weight) {
        this.label = label;
        this.vers.add(0, src);
        this.vers.add(1, arg);
        this.weight = weight;
        checkRep();
    }
	
	private void checkRep() {
		assert label != null;
	}
    // TODO methods
	abstract public boolean addVertices(List<Vertex> vertices);
    abstract Set<Vertex> sourceVertices();
    abstract Set<Vertex> targetVertices();
    
	boolean containVertex(Vertex v) {
		for(Vertex vertex:vers) {
			if(vertex.equals(v))
				return true;
		}
		return false;
	}
	public boolean addvertices(List<Vertex> add) {
		vers.addAll(add);
		checkRep();
		return true;
	}
    boolean addvers(Vertex v1, Vertex v2) {
    		vers.add(0, v1);
    		vers.add(0, v2);
    		checkRep();
    		if(vers.size() == 2)
    			return true;
    		return false;
    }
    public List<Vertex> getvers() {
    		return vers;
    }
    public Vertex getsource() {
    	return vers.get(0);
    }
    
    public Vertex gettarget() {
    	return vers.get(1);
    }
    
    public Double getweight() {
    	return weight;
    }
    
    public String getlabel() {
    	return label;
    }
    public void setlabel(String newlabel) {
    		label = newlabel;
    }
    public int getnumber() {
    	return number;
    }
    public void setnumber(int newnumber) {
    		number = newnumber;
    }
    public String tellclass() {
    		return "Edge";
    }
    // TODO toString()
    @Override public String toString() {
    	return vers.get(0) +"->" + vers.get(1) +"="+ weight;
    }
    @Override 
	public int hashCode() {  
		int result = 17;  
	    result = 31 * result + label.hashCode() + weight.hashCode();	    
	    return result;  
	}  	
    @Override public boolean equals(Object obj) {
    		Edge e = (Edge) obj;
		if(this.label.equals(e.getlabel())) {
			return true;
		}else {
			return false;
		}
	}
}
