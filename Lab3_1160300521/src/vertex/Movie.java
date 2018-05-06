package vertex;

import java.text.DecimalFormat;

public class Movie extends Vertex {
	private int year;
	private String nation;
	private double score;
	
	DecimalFormat df = new DecimalFormat( "0.00 "); 
	
	public Movie(String name, int y, String n, double s) {
		super(name);
		this.score = Double.parseDouble(df.format(s));
		this.nation = n;
		this.year = y;
		checkRep();
	}
	public Movie(String name) {
		super(name);
		this.score = 8.80;
		this.nation = "china";
		this.year = 2000;
		checkRep();
	}
	public int getyear() {
		return this.year;
	}
	
	public String getnation() {
		return super.getlabel();
	}
	
	public double getscore() {
		return this.score;
	}

	private void checkRep() {
		System.out.println(this.score);
		assert (this.year >= 1900 && this.year <= 2018);
	}
		
	@Override
	public
	void fillVertexInfo(String[] args) {
		this.year = Integer.valueOf(args[2]).intValue();
		this.nation = args[3];
		this.score = Double.valueOf(args[4]).doubleValue();
	}
	
	@Override public boolean equals(Object obj) {
		Vertex v = (Vertex) obj;
		if(v.getlabel().toLowerCase().equals(super.getlabel().toLowerCase()) &&
				v.tellclass().equals("Movie")) 
			return true;
		else {
		//System.out.println("not equals");
			return false;
		}
	}
	@Override 
    public String toString() {
        return super.getlabel()+ "	nation:" + this.nation 
               + "\t"+"	year:" + this.year + "\t"+"	score:" + this.score+ "\n";
    }
	@Override
	public
	String tellclass() {
		// TODO Auto-generated method stub
		return "Movie";
	}
}
