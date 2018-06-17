import java.util.List;

public class choosebehavior1 implements Choosebehavior {
  
  @Override
  public Ladder operate(List<Ladder> ladders, Monkey mymonkey) {
    /** Plan 0 Select the ladder with the least number of monkeys. */
    int num = 999;
    Ladder l = null;
    for (Ladder lad : ladders) {
      //System.out.println(lad.getmap().size());
      if (lad.getdirection().equals(mymonkey.getdirection()) || lad.getdirection().equals("N")) {
        if (lad.getmap().size() < num) {
          num = lad.getmap().size();
          l = lad;
        }
      }
    }
    return l;
  }
  public String type() {
    return "choosebehavior1";
  }

}
