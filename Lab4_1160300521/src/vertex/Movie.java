package vertex;

import java.text.DecimalFormat;

public class Movie extends Vertex {
	// Abstraction function:
    // AF(Vertex) = {vertex|label ！= null && 2018 >=year >= 1900 && 0.00<= score <=10.00 && nation != null}.
	//
    // Representation invariant:Release year is four positive integers in the range [1900, 2018],Shooting country cannot be null
	//IMDb score ranges 0-10 rangeValue, up to 2 decimal places.
	//
    // Safety from rep exposure:
	// All fileds are private
	// the get methods can prevent sharing data with clients.
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
		assert (this.year >= 1900 && this.year <= 2018);
		assert this.nation != null;
	}
		
	@Override
	public
	void fillVertexInfo(String[] args) {
	  int years = Integer.valueOf(args[2]).intValue();
	  assert (years >= 1900 && years <= 2018);
      assert args[3] != null;	
	  this.year = Integer.valueOf(args[2]).intValue();
	  this.nation = args[3];
	  double s = Double.parseDouble(args[4]);
	  this.score = Double.parseDouble(df.format(s));
	}
	
	@Override public boolean equals(Object obj) {
		Vertex v = (Vertex) obj;
		if(v.getlabel().toLowerCase().equals(this.getlabel().toLowerCase()) &&
				v.tellclass().equals("Movie")) 
			return true;
		else {
			return false;
		}
	}
	@Override 
    public String toString() {
        return super.getlabel()+ "	nation:" + this.nation 
               + "\t"+"	year:" + this.year + "\t"+"	score:" + this.score+ "\n";
    }
	@Override
	public String tellclass() {
		return "Movie";
	}
}
