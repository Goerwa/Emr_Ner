package vertex;

public class Person extends Vertex{
	
	private int year;
	private String gender;
	
	public Person(String name) {
		super(name);
		this.gender = "M";
		this.year = 18;
	}
	
	public Person(String name, int y, String g) {
		super(name);
		this.gender = g;
		this.year = y;
		checkRep();
	}
	
	public int getyear() {
		return this.year;
	}
	
	public String getgender() {
		return this.gender;
	}
	
	private void checkRep() {
		assert (this.gender.equalsIgnoreCase("M") || this.gender.equalsIgnoreCase("F"));
		assert (this.year > 0);
	}

	@Override
	public void fillVertexInfo(String[] args) {
		this.gender = args[3];
		this.year = Integer.valueOf(args[2]).intValue();
	}
	@Override 
    public boolean equals(Object obj) {
		Vertex v = (Vertex) obj;
		if(v.getlabel().toLowerCase().equals(super.getlabel().toLowerCase()) &&
				v.tellclass().equals("Person")) 
			return true;
		else {
		//System.out.println("not equals");
			return false;
		}
    }
	
	@Override 
    public String toString() {
        return super.getlabel() + "	Gender:	" + this.gender 
               + "\t"+"	Year:	" + this.year + "\n";
    }
	
	@Override 
	public int hashCode() {  
		int result = 17;  
	    result = 31 * result + super.getlabel().hashCode()+
	    		this.gender.hashCode(); 
	    return result;  
	}
	
	@Override
	public String tellclass() {
		// TODO Auto-generated method stub
		return "Person";
	}
}
