package vertex;

public class Word extends Vertex{

	public Word(String label) {
		super(label);
		// TODO Auto-generated constructor stub
	}

	@Override
	public
	void fillVertexInfo(String[] args) {
		
	}
	
	@Override 
    public boolean equals(Object obj) {
		Vertex v = (Vertex) obj;
		if(v.getlabel().toLowerCase().equals(super.getlabel().toLowerCase()) &&
				v.tellclass().equals("Word")) 
			return true;
		else {
		//System.out.println("not equals");
			return false;
		}
    }
	
	@Override
	public String tellclass() {
		// TODO Auto-generated method stub
		return "Word";
	}
	
}
