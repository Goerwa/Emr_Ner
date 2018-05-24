package vertex;

public class Computer extends Vertex {
	// Abstraction function:
    // AF(Vertex) = {vertex|label ！= null && ip = [0,255].[0,255].[0,255].[0,255]}.
	//
    // Representation invariant:
    // label cannot be null,IP address divided into four parts with ".", the range of each part is [0,255].
	//
    // Safety from rep exposure:
	// All fileds are private
	// the get methods can prevent sharing data with clients.
	private String ip;
	
	public Computer(String label,String ip) {
		super(label);
		this.ip = ip; 
		checkRep();
	}
	
	public Computer(String label) {
		super(label);
		this.ip = "192.108.1.1";
		checkRep();
	}
	
	public String getip() {
		return this.ip;
	}
	
	private void checkRep() {
		String[] str = this.ip.split("\\.");
		assert (str.length == 4);
		for(int i=0;i<4;i++) {
			int a = Integer.parseInt(str[i]);
			assert (a >= 0 && a<= 255);
		}
	}
	@Override 
    public String toString() {
        return "type :" + this.tellclass() + "label :" + this.getlabel() +  "\n";
    }
	@Override
	public
	void fillVertexInfo(String[] args) {
	  assert args[2] != null;
	  this.ip = args[2];
	  checkRep();
	}
    
	@Override public boolean equals(Object obj) {
		Vertex v = (Vertex) obj;
		if(v.getlabel().toLowerCase().equals(this.getlabel().toLowerCase()) &&
				v.tellclass().equals("Computer")) 
			return true;
		else {
			return false;
		}
	}

	@Override
	public
	String tellclass() {
		return "Computer";
	}

	@Override 
    public int hashCode() {  
        int result = 17;  
        result = 31 * result + this.getlabel().hashCode() + this.getip().hashCode();  
        return result;  
    }
}
