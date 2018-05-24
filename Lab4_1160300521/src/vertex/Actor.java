package vertex;

public class Actor extends Person {
	// Abstraction function:
    // AF(Vertex) = {vertex|label ！= null && year > 0 && gender = "M"|"F"}.
	//
    // Representation invariant:
    // label cannot be null,year are over 0 and gender is M or F
	//
    // Safety from rep exposure:
	// All fileds are private
	// the get methods can prevent sharing data with clients.
	public Actor(String name) {
		super(name); 
	}
	public Actor(String name, int y, String g) {
		super(name,y,g);
	}
	@Override
	public String tellclass() {
		return "Actor";
	} 
	
	@Override public boolean equals(Object obj) {
		Vertex v = (Vertex) obj;
		if(v.getlabel().toLowerCase().equals(this.getlabel().toLowerCase()) &&
				v.tellclass().equals("Actor") ) 
			return true;
		else {
			return false;
		}
	}
}
