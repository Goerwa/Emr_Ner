import java.util.List;

public class choosebehavior2 implements Choosebehavior{

  @Override
  public Ladder operate(List<Ladder> ladders, Monkey mymonkey) {
    /** Plan 1 chooses the ladder that has not taken up most steps. */
    int numstep = -1;
    Ladder l = null;
    for (Ladder lad : ladders) {
      System.out.println(lad.getmap().size());
      if (lad.getdirection().equals(mymonkey.getdirection())
          || lad.getdirection().equals("N")) {
        if (lad.getladderstep() > numstep) {
          //System.out.println("ladder: " + lad.getid() + "'s step is " + lad.getladderstep());
          numstep = lad.getladderstep();
          l = lad;
        }
      }
    }
    return l;
  }
  public String type() {
    return "choosebehavior2";
  }

}
