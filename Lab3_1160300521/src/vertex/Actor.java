package vertex;

public class Actor extends Person {

	public Actor(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}
	public String tellclass() {
		return "Actor";
	}
	
	@Override public boolean equals(Object obj) {
		Vertex v = (Vertex) obj;
		if(v.getlabel().toLowerCase().equals(super.getlabel().toLowerCase()) &&
				v.tellclass().equals("Actor")) 
			return true;
		else {
		//System.out.println("not equals");
			return false;
		}
	}
}
