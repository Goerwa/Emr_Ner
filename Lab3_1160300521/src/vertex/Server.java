package vertex;

public class Server extends Vertex {
	
	private String ip;
	public Server(String label,String ip) {
		super(label);
		this.ip = ip;
	}
	
	public Server(String label) {
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
		String[] str = this.ip.split("\\.");
		assert (str.length == 4);
		for(int i=0;i<4;i++) {
			int a = Integer.parseInt(str[i]);
			assert (a >= 0 && a<= 255);
		}
	}
	
	@Override public boolean equals(Object obj) {
		Vertex v = (Vertex) obj;
		if(v.getlabel().toLowerCase().equals(super.getlabel().toLowerCase()) &&
				v.tellclass().equals("Server")) 
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
		return "Server";
	}
}
