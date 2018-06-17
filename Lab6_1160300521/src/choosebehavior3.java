import java.util.List;

public class choosebehavior3 implements Choosebehavior{
  
  @Override
  public Ladder operate(List<Ladder> ladders, Monkey mymonkey) {
    /** Plan 2 choose ladder without monkey. */
    Ladder l = null;
    for (Ladder lad : ladders) {
      if (lad.getdirection().equals(mymonkey.getdirection())
          || lad.getdirection().equals("N")) {
        //System.out.println(lad.getmap().size());
        if (lad.getmap().size() == 0) {
          l = lad;
        }
      }
    }
    return l;
  }
  public String type() {
    return "choosebehavior3";
  }
}
