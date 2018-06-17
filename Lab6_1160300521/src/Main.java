import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main {
  
  public static void runbyfile(String filename) throws IOException {
    BufferedReader br = new BufferedReader(new FileReader(filename));
    String headline;
    while ((headline = br.readLine()) != null) {
      List<Monkey> monkeys = new ArrayList<>();
      List<Ladder> ladders = new ArrayList<>();
      headline = headline.replace(" ", "").replaceAll("=", "").replaceAll("[a-zA-Z]", "");
      String[] parameter = headline.split(";");
      int n = Integer.parseInt(parameter[0]);
      int h = Integer.parseInt(parameter[1]);
      int t = Integer.parseInt(parameter[2]);
      int N = Integer.parseInt(parameter[3]);
      int k = Integer.parseInt(parameter[4]);
      int MV = Integer.parseInt(parameter[5]);
      //System.out.println(n + " " + h + " " + t + " " + N + " " + k + " " + MV);
      MonkeyGenerator mkl = new MonkeyGenerator(N);
      mkl.build(n,h,N,MV,monkeys,ladders);
      long begin = System.currentTimeMillis();
      mkl.creatmonkeysbytime(k,t,monkeys,ladders,begin);
    }
  }
  
  public static void main(String[] args) throws IOException {
    
    runbyfile("src/source/test.txt");
  }
}
