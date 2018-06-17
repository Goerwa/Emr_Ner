import java.util.List;

public interface Choosebehavior {
  Ladder operate(List<Ladder> ladders,Monkey mymonkey);
  String type();
}
