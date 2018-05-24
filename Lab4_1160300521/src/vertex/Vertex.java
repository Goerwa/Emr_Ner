package vertex;

import java.util.HashMap;
import java.util.Map;

import edge.Edge;


public abstract class Vertex {
	
	
	// Abstraction function:
    // AF(Vertex) = {vertex|label ！= null }.
	//
    // Representation invariant:
    // label cannot be null.
	//
    // Safety from rep exposure:
	// All fileds are private
	// the get methods can prevent sharing data with clients.
	// the remove methods only return true or false.
	// the add mothods return immutable type or 0
	
	
	private String label;
    private int number = 0;
	// TODO constructor
	public Vertex(String label) {
      assert label != null; 
	  this.label = label;
        checkRep();
    }
	
    // TODO checkRep
	private void checkRep() {
      assert label != null;
    }
    // TODO methods
	public String getlabel() {
      return label;
    }
	public void setlabel(String newlabel) {
      assert newlabel != null;  
	  label = newlabel;
    }
	public int getnumber() {
	   return number;
    }
	public void setnumber(int newnumber) {
		number = newnumber;
    }
	
	abstract public void fillVertexInfo (String[] args);
	
	abstract public String tellclass();
	
    @Override 
    public String toString() {
        return "type :" + this.tellclass() + "label :" + this.getlabel() +  "\n";
    }
    @Override 
    public boolean equals(Object obj) {
		Vertex v = (Vertex) obj;
		if(v.getlabel().toLowerCase().equals(this.label.toLowerCase())) 
			return true;
		else {
			return false;
		}
    }
	@Override 
	public int hashCode() {  
		int result = 17;  
	    result = 31 * result + this.getlabel().hashCode();  
	    return result;  
	}  	
}
