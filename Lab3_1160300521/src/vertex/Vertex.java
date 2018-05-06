package vertex;

import java.util.HashMap;
import java.util.Map;

import edge.Edge;


public abstract class Vertex {
	
	
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
	
	
	private final String label;
	private final Map<String, Double> inEdges = new HashMap<>();
	private final Map<String, Double> outEdges = new HashMap<>();
    
	// TODO constructor
	public Vertex(String label) {
        this.label = label;
        checkRep();
    }
	
    // TODO checkRep
	private void checkRep() {
        assert label != null;
        for (Double weight: inEdges.values())
            assert weight > 0;
        for (Double weight : outEdges.values())
            assert weight > 0;
    }
    // TODO methods
	public String getlabel() {
        return label;
    }
	
	public Map<String, Double> getincoming() {
        return new HashMap<String, Double>(inEdges);
    }
	
	public Map<String, Double> getoutcoming() {
        return new HashMap<String, Double>(outEdges);
	}	
	abstract public void fillVertexInfo (String[] args);
	
	abstract public String tellclass();
	
    @Override 
    public String toString() {
        return label + ": inEdges" + getincoming().toString() 
               + "\t"+"outEdges" + getoutcoming().toString() + "\n";
    }
    @Override 
    public boolean equals(Object obj) {
		Vertex v = (Vertex) obj;
		if(v.getlabel().toLowerCase().equals(this.label.toLowerCase())) 
			return true;
		else {
		//System.out.println("not equals");
			return false;
		}
    }
	@Override 
	public int hashCode() {  
		int result = 17;  
	    result = 31 * result + label.hashCode();  
	    return result;  
	}  	
}
