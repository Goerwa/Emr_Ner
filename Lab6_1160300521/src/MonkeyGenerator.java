import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.apache.log4j.Logger;

public class MonkeyGenerator {
  private int numberofmonkey;
  private int numberofpass;
  private int runtime;

  public MonkeyGenerator(int n) {
    this.numberofmonkey = n;
    this.numberofpass = 0;
  }
  
  private int getruntime() {
    return this.runtime;
  }
  private void addruntime(int n) {
    this.runtime += n;
  }
  public void build(int n, int h, int N, int v, List<Monkey> monkeys, List<Ladder> ladders) {
    this.runtime = 0;
    Random rand = new Random();
    for (int i = 0; i < n; i++) {
      Ladder l = new Ladder(i, h);
      ladders.add(l);
      //System.out.println(l.getmap().size());
    }
    for (int i = 0; i < N; i++) {
      int monkeyv = rand.nextInt((v)) + 1;
      int dir = rand.nextInt() % 2;
      String direction = null;
      if (dir == 0) {
        direction = "L->R";
      } else {
        direction = "R->L";
      }
      Monkey m = new Monkey(i, direction, monkeyv);
      int plan = rand.nextInt(100) % 3;
      if (plan == 0) {
        m.setmybehavior(new choosebehavior1());
      }else if (plan == 1) {
        m.setmybehavior(new choosebehavior2());
      } else {
        m.setmybehavior(new choosebehavior3());
      }
      monkeys.add(m);
      System.out.println(m.toString());
    }
    System.out.println("the number of ladders is: " + ladders.size());
    System.out.println("the number of monkeys is: " + monkeys.size());
  }

  private void monkeyover(long start) {
    synchronized (MonkeyGenerator.class) {
      this.numberofmonkey = this.numberofmonkey - 1;
      this.numberofpass++;
      System.out.println("a monkey has gone over a ladder!");
      System.out.println("the number of left monkey is: " + this.numberofmonkey);
      System.out.println("the number of passed monkey is: " + this.numberofpass);
      if (this.numberofmonkey == 0) {
        long end = System.currentTimeMillis();
        System.out.println("过桥用时为：" + (end - start));
      }
    }
  }

  private void creatthread(int i, List<Monkey> monkeys, List<Ladder> ladders, long start) {
    int number = i;
    System.out.println("creat thread" + i);
    new Thread("monkey ID: " + monkeys.get(i).getid()) {
      public void run() {
        Logger logger = Logger.getLogger(Monkey.class);
        Monkey mymonkey = monkeys.get(number);
        int time = 0;
        int runtime = getruntime();
        boolean flag = true;
        while (flag) {
          if (mymonkey.getstate().equals("wait")) {
            Ladder l = mymonkey.operate(ladders);
            if (l == null) {
              logger.info("runtime: " + (runtime + time) + "\n" +  "monkey " + mymonkey.getid() 
                  + " is waiting in "+ mymonkey.getdirection().substring(0, 1) 
                  + " when created " + time + "s!");
              try {
                this.sleep(1000);
                time++;
                //System.out.println("monkey: " + number + "runs time " + time);
              } catch (InterruptedException e) {
                e.printStackTrace();
              }
              continue;
            }
            if (l.gotoladder(mymonkey)) {
              logger.info("runtime: " + (runtime + time) + "\n" +  "monkey " + mymonkey.getid() 
                  + " go to ladder "+ mymonkey.getmyladder().getid() + "'s" 
                  + l.getstep(mymonkey) + " step"
                  + " when created " + time + "s!");
            } else {
              logger.info("runtime: " + (runtime + time) + "\n" + "monkey " + mymonkey.getid() 
              + " is waiting in "+ mymonkey.getdirection().substring(0, 1) 
              + " when created " + time + "s!");
            }
            try {
              this.sleep(1000);
              time++;
              //System.out.println("monkey: " + number + "runs time " + time);
            } catch (InterruptedException e) {
              e.printStackTrace();
            }
            continue;
          }
          if (mymonkey.getstate().equals("inladder")) {
            Ladder myladder = mymonkey.getmyladder();
            myladder.gotonext(mymonkey);
            if(myladder.getstep(mymonkey) != -1) {
            logger.info("runtime: " + (runtime + time) + "\n" + "monkey " + mymonkey.getid() 
            + " go to ladder "+ mymonkey.getmyladder().getid() + "'s" 
            + myladder.getstep(mymonkey) + " step"
            + " when created " + time + "s!");
            }
            try {
              this.sleep(1000);
              time++;
              //System.out.println("monkey: " + number + "runs time " + time);
            } catch (InterruptedException e) {
              e.printStackTrace();
            }
            continue;
          }
          if (mymonkey.getstate().equals("over")) {
            flag = false;
            logger.info("runtime: " + (runtime + time) + "\n" +  "monkey " + mymonkey.getid() 
            + " go over ladder "+ mymonkey.getmyladder().getid() +" when created " + time + "s!");
            try {
              this.sleep(1000);
              time++;
              //System.out.println("monkey: " + number + "runs time " + time);
            } catch (InterruptedException e) {
              e.printStackTrace();
            }
            continue;
          }
        }
      }
    }.start();
  }

  public void creatmonkeysbytime(int k, int t, List<Monkey> monkeys, List<Ladder> ladders,
      long start) {
    int i = k;
    for (; i < monkeys.size(); i += k) {
      try {
        for (int j = k; j > 0; j--) {
          creatthread(i - j, monkeys, ladders, start);
        }
        Thread.sleep(t * 1000);
        this.addruntime(t);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
    i = i - k;
    try {
      Thread.sleep(t * 1000);
      this.addruntime(t);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    while (i < monkeys.size()) {
      creatthread(i, monkeys, ladders, start);
      i++;
    }
  }

}
