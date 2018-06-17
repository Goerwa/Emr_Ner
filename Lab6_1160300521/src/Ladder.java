
import java.util.HashMap;
import java.util.Map;

public class Ladder {
  private int ID;
  private int length;
  private String direction;
  private Map<Monkey,Integer> ms = new HashMap<>();
  
  public Ladder(int i, int l) {
    this.ID = i;
    this.length = l;
    /** type N R L*/
    this.direction = "N";
  }
  public int  getladderstep() {
    synchronized(Ladder.class) {
      for(int i = 1;i<20;i++) {
        if(this.ms.containsValue(i)) {
          return i;
        }
      }
    }
    return this.length;
  }
  public Map getmap() {
    synchronized(Ladder.class) {
      return this.ms;
    }
  }
  public int getstep(Monkey m) {
    if(this.ms.get(m) != null)
    return this.ms.get(m);
    else {
      return -1;
    }
  }
  public int getid() {
    return this.ID;
  }
  public String getdirection() {
    synchronized(Ladder.class) {
      return this.direction;
    }
  }
  public boolean gotoladder(Monkey m) {
    boolean result = false;
    synchronized(Ladder.class) {
      if(m.getdirection().equals(this.direction) || this.direction.equals("N")) {
        this.direction = m.getdirection();
        boolean gotofirst = true;
        for(int lad:ms.values()) {
          //System.out.println(lad);
          if(lad == 1) {
            gotofirst = false;
          }
        }
        if(gotofirst) {
          m.changestate("inladder");
          m.setladder(this);
          ms.put(m, 1);
          result = true;
          //System.out.println("monkey " + m.getid() + " go to ladder" + 
          //    this.getid() +"'s " + ms.get(m) + " location");
        }
      }
    }
    return result;
  }
  public void gotonext(Monkey m) {
    synchronized(Ladder.class) {
      int oldlocation = ms.get(m);
      int newlocation = oldlocation;
      for(int i = 1;i <= m.getv();i++) {
        newlocation = oldlocation + i;
        if(ms.containsValue(newlocation)) {
          break;
        } else {
          ms.remove(m);
          if(newlocation > this.length) {
            m.changestate("over");
            if(this.ms.size() == 0) {
              this.direction = "N";
            }
          } else {
            ms.put(m,newlocation);
          }
        }
        //System.out.println(m.getid() + "  " + ms.get(m));
      }/*
      if(ms.get(m) != null) {
        System.out.println("monkey " + m.getid() + " go to ladder" + 
            this.getid() +"'s " + ms.get(m) + " location");
      } else {
        System.out.println("monkey " + m.getid() + " go over ladder" + 
            this.getid());
      }*/
    }
  }
  
  
}
