package vertex;

public class Word extends Vertex{
	// Abstraction function:
    // AF(Vertex) = {vertex|label ！= null }.
	//
    // Representation invariant:
    // label cannot be null.
	//
    // Safety from rep exposure:
	// All fileds are private
	// the get methods can prevent sharing data with clients.
	
	public Word(String label) {
		super(label);
		checkRep();
	}
	
	private void checkRep() {
		assert this.getlabel() != null;
	}
	@Override
	public void fillVertexInfo(String[] args) {
	  assert args != null;
	  return;
	}
	@Override 
    public boolean equals(Object obj) {
		Vertex v = (Vertex) obj;
		if(v.getlabel().toLowerCase().equals(this.getlabel().toLowerCase()) &&
				v.tellclass().equals("Word")) 
			return true;
		else {
			return false;
		}
    }
	@Override
	public String tellclass() {
		return "Word";
	}
	
}
