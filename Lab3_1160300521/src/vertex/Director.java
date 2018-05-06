package vertex;

public class Director extends Person {
	
	
	public Director(String name, int year, String gender) {
		super(name,year,gender);
		// TODO Auto-generated constructor stub
	}
	public Director(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}
	public String tellclass() {
		return "Director";
	}
	
	@Override public boolean equals(Object obj) {
		Vertex v = (Vertex) obj;
		if(v.getlabel().toLowerCase().equals(super.getlabel().toLowerCase()) &&
				v.tellclass().equals("Director")) 
			return true;
		else {
		//System.out.println("not equals");
			return false;
		}
	}
}
