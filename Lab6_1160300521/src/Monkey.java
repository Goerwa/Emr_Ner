import java.util.List;

public class Monkey {
  private int ID;
  private String direction;
  /**
   * wait,inladder,over
   */
  private String state;
  private int v;
  private int c;
  private Ladder myladder = null;
  private Choosebehavior mybehavior;
  public Monkey(int i, String d, int v) {
    this.ID = i;
    this.direction = d;
    this.state = "wait";
    this.v = v;
    this.c = 0;
  }
  public void setmybehavior(Choosebehavior c) {
    this.mybehavior = c;
  }
  public Ladder operate(List<Ladder> ladders) {
   return  mybehavior.operate(ladders, this);
  }
  public String getdirection() {
    return this.direction;
  }
  public void nextc(int n) {
    if(n == 0) {
    this.c += this.v;
    }
    else if(n == 1) {
      this.c += 1;
    }
  }
  public void geti(Ladder l) {
    if(l.getdirection().equals(this.getdirection())) {
      
    }
  }
  public void setladder(Ladder l) {
    this.myladder = l;
  }
  public Ladder getmyladder() {
    return this.myladder;
  }
  public String getstate() {
    return this.state;
  }
  public int getid() {
    return this.ID;
  }
  public int getv() {
    return this.v;
  }
  public void changestate(String s) {
    this.state = s;
  }
  public String toString() {
    return "id: " + this.ID + " direction: " + this.direction 
        + " v: " + this.v + "  behavior is " + this.mybehavior.type();
  }
}
