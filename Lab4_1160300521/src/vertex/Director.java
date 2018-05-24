package vertex;

public class Director extends Person {
	// Abstraction function:
    // AF(Vertex) = {vertex|label ！= null && year > 0 && gender = "M"|"F"}.
	//
    // Representation invariant:
    // label cannot be null,year are over 0 and gender is M or F
	//
    // Safety from rep exposure:
	// All fileds are private
	// the get methods can prevent sharing data with clients.
	
	public Director(String name, int year, String gender) {
		super(name,year,gender);
	}
	public Director(String name) {
		super(name);
	}
	@Override
	public String tellclass() {
		return "Director";
	}
	
	@Override public boolean equals(Object obj) {
		Vertex v = (Vertex) obj;
		if(v.getlabel().toLowerCase().equals(this.getlabel().toLowerCase()) &&
				v.tellclass().equals("Director") ) 
			return true;
		else {
			return false;
		}
	}
}
