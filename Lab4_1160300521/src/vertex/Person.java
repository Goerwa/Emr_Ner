package vertex;

public class Person extends Vertex{
	// Abstraction function:
    // AF(Vertex) = {vertex|label ！= null && year > 0 && gender = "M"|"F"}.
	//
    // Representation invariant:
    // label cannot be null,year are over 0 and gender is M or F
	//
    // Safety from rep exposure:
	// All fileds are private
	// the get methods can prevent sharing data with clients.
	private int year;
	private String gender;
	
	public Person(String name) {
		super(name);
		this.gender = "M";
		this.year = 18;
		checkRep();
	}
	
	public Person(String name, int y, String g) {
		super(name);
		assert (g.equals("M") || g.equals("F"));
		this.gender = g;
		assert (y > 0);
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
		assert (this.gender.equals("M") || this.gender.equals("F"));
		assert (this.year > 0);
	}

	@Override
	public void fillVertexInfo(String[] args) {
	  assert (args[3].equals("M") || args[3].equals("F"));
	  this.gender = args[3];
	  int age = Integer.valueOf(args[2]).intValue();
	  assert (age > 0);
	  this.year = age;
	  checkRep();
	}
	@Override 
    public boolean equals(Object obj) {
		Vertex v = (Vertex) obj;
		if(v.getlabel().toLowerCase().equals(this.getlabel().toLowerCase()) &&
				v.tellclass().equals("Person") ) 
			return true;
		else {
			return false;
		}
    }
	
	@Override 
    public String toString() {
        return "type :" + this.tellclass() + "label :" + this.getlabel() + "Gender:	" + this.gender 
               + "Year: " + this.year + "\n";
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
		return "Person";
	}
}
