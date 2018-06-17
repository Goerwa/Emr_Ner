import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class fair {
  private Map<Integer,Integer> mt = new HashMap<>();
  private int number;
  public fair(int n) {
    this.number = n;
  }
  public void overadd(int m, int t) {
    synchronized(fair.class) {
    mt.put(m, t);
    //System.out.println(mt.size());
    }
  }
  private int gettime() {
    int max = 0;
    for(int i = 0;i<mt.size();i++) {
      if(max < mt.get(i)) {
        max = mt.get(i);
      }
    }
    return max;
  }
  public void isover() {
    if(mt.size() < number) {
      return;
    }
    int num = 0;
    for(int i = 0;i<mt.size();i++) {
      for(int j = i;j<mt.size();j++) {
        if(mt.get(j) - mt.get(i) >= 0) {
          num += 1;
        }else {
          num -= 1;
        }
      }
    }
    System.out.println((number) + " monkeys all have been over the river and cost "
    + gettime() + "s");
    System.out.println("吞吐率为:" +  (number + 0.0) / gettime());
    System.out.println("公平性为:"+ (num + 0.0) / (mt.size() * mt.size()));
  }
}
