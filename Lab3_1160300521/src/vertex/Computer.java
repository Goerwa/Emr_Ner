package vertex;

public class Computer extends Vertex {
	
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
		//System.out.println(".");
		String[] str = this.ip.split("\\.");
		//System.out.println(str[0]);
		assert (str.length == 4);
		for(int i=0;i<4;i++) {
			int a = Integer.parseInt(str[i]);
			assert (a >= 0 && a<= 255);
		}
	}
	
	@Override
	public
	void fillVertexInfo(String[] args) {
		this.ip = args[2];
		
	}
    
	@Override public boolean equals(Object obj) {
		Vertex v = (Vertex) obj;
		if(v.getlabel().toLowerCase().equals(super.getlabel().toLowerCase()) &&
				v.tellclass().equals("Computer")) 
			return true;
		else {
		//System.out.println("not equals");
			return false;
		}
	}
		
		
	@Override
	public
	String tellclass() {
		// TODO Auto-generated method stub
		return "Computer";
	}


}
