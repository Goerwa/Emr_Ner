package vertex;

public class Router extends Vertex {
	private String ip;
	public Router(String label,String ip) {
		super(label);
		this.ip = ip;
	}
	
	public Router(String label) {
		super(label);
		this.ip = "192.168.1.1";
	}
	public String getip() {
		return this.ip;
	}
	
	@Override
	public
	void fillVertexInfo(String[] args) {
		this.ip = args[0];
		
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
	
	@Override public boolean equals(Object obj) {
		Vertex v = (Vertex) obj;
		if(v.getlabel().toLowerCase().equals(super.getlabel().toLowerCase()) &&
				v.tellclass().equals("Router")) 
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
		return "Router";
	}
}
