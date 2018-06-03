package factory;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.log4j.Logger;
import Exception.ExceptionofDirection;
import Exception.ExceptionofDirectionTest;
import Exception.ExceptionofInput;
import Exception.ExceptionofUndirection;
import Exception.ExceptionofUnproperEdge;
import Gui.TimeGraph;
import edge.CommentTie;
import edge.Edge;
import edge.ForwardTie;
import edge.FriendTie;
import graph.Graph;
import graph.NetworkTopology;
import graph.SocialNetwork;
import vertex.Person;
import vertex.Vertex;

public class SocialNetworkFactory extends GraphFactory {
  static Logger logger = Logger.getLogger(SocialNetworkFactory.class);
  private static List<String> vtype = new ArrayList<>();
  private static List<String> etype = new ArrayList<>();
  private static Map<String, Vertex> strver = new HashMap<>();

  public SocialNetworkFactory() {

  }

  private static String replacev(String headline) {
    headline = headline.replace("Vertex =<", "");
    headline = headline.replace("Vertex = <", "");
    headline = headline.replace(">", "");
    headline = headline.replace("<", "");
    headline = headline.replace("\"", "");
    headline = headline.replace(" ", "");
    // System.out.println(headline);
    return headline;
  }

  private static String replacee(String headline) {
    headline = headline.replace("Edge =<", "");
    headline = headline.replace("Edge = <", "");
    headline = headline.replace(">", "");
    headline = headline.replace("\"", "");
    headline = headline.replace(" ", "");
    return headline;
  }

  private static void dealvertex(String str, Graph graph) throws ExceptionofInput, IOException {
    Pattern p = Pattern
        .compile("Vertex =\\s*<(\\s*\"[a-zA-Z_0-9]+\",*\\s*){2}<(\\s*\"[a-zA-Z_0-9]+\",*\\s*)+>>");
    Matcher m = p.matcher(str);
    if (m.matches() == true) {
      str = replacev(str);
      String info[] = str.split(",");
      if (info.length != 4) {
        logger.error("The data read in miss attributes!");
        throw new ExceptionofInput("The data read in miss attributes!");
      }
      if (!vtype.contains(info[1])) {
        logger.error("The type of vertex " + info[1] + " is not in the graph!");
        throw new ExceptionofInput("The type of vertex " + info[1] + " is not in the graph!");
      }
      if (!info[1].equals("Person")) {
        logger.error("The type of vertex " + info[1] + " is not in the graph!");
        throw new ExceptionofInput("The type of vertex " + info[1] + " is not in the graph!");
      } ;
      Person w = new Person(info[0]);
      w.fillVertexInfo(info);
      // logger.info("add vertex " + info[0] + " to the graph!");
      strver.put(w.getlabel(), w);
      graph.addVertex(w);
    } else {
      logger.error("The data read " + str + " in does not match the regular expression!");
      throw new ExceptionofInput(
          "The data read " + str + " in does not match the regular expression!");
    }
  }

  private static void dealedge(String headline, Graph graph) throws ExceptionofInput,
      ExceptionofUnproperEdge, ExceptionofDirection, ExceptionofUndirection, IOException {
    Pattern e = Pattern.compile(
        "Edge =\\s*<(\\s*\"[a-zA-Z_0-9]*([0-9]{1,}[.][0-9]*)*\",*\\s*){5},*\\s\"(Yes|No)\"\\s*>");
    Matcher em = e.matcher(headline);
    if (em.matches() == true) {
      String replace = null;
      headline = replacee(headline);
      // System.out.println(headline);
      String str[] = headline.split(",");
      double weight = Double.valueOf(str[2]);
      if (!(weight > 0 && weight <= 1)) {
        logger.error("The weight is " + weight + " not belong to (0,1]!");
        throw new ExceptionofUnproperEdge("The weight is " + weight + " not belong to (0,1]!");
      }
      if (str[5].equals("No")) {
        logger.error("The edge add in can not be an undirectededge!");
        throw new ExceptionofDirection("The edge add in can not be an undirectededge!");
      }
      Person src = null;
      Person tar = null;
      src = (Person) strver.get(str[3]);
      tar = (Person) strver.get(str[4]);
      if (!etype.contains(str[1])) {
        logger.error("The type of edge " + str[1] + " is not in the graph!");
        throw new ExceptionofInput("The type of edge " + str[1] + " is not in the graph!");
      }
      if (str[1].equals("FriendTie")) {
        FriendTie edge = new FriendTie(str[0], src, tar, weight);
        // logger.info("add edge " + str[0] + " to the graph!");
        graph.addEdge(edge);
      } else if (str[1].equals("ForwardTie")) {
        ForwardTie edge = new ForwardTie(str[0], src, tar, weight);
        // logger.info("add edge " + str[0] + " to the graph!");
        graph.addEdge(edge);
      } else if (str[1].equals("CommentTie")) {
        // logger.info("add edge " + str[0] + " to the graph!");
        CommentTie edge = new CommentTie(str[0], src, tar, weight);
        graph.addEdge(edge);
      } else {
        logger.error("The type of edge " + str[1] + " is not in the graph!");
        throw new ExceptionofInput("The type of edge " + str[1] + " is not in the graph!");
      }
    } else {
      logger.error("The data read " + headline + " in does not match the regular expression!");
      throw new ExceptionofInput(
          "The data read " + headline + " in does not match the regular expression!");
    }
  }

  private static void dealstr(String headline, SocialNetwork graph) throws ExceptionofInput,
      IOException, ExceptionofUnproperEdge, ExceptionofDirection, ExceptionofUndirection {
    String type;
    if (!headline.equals(""))
      type = headline.substring(0, 1);
    else
      return;
    Pattern graphtype = Pattern.compile("GraphType = \\s*\"[a-zA-Z_0-9]+\",*\\s*");
    Matcher m1 = graphtype.matcher(headline);
    Pattern vertextype = Pattern.compile("VertexType = (\\s*\"[a-zA-Z_0-9]+\",*\\s*)+");
    Matcher m2 = vertextype.matcher(headline);
    Pattern edgetype = Pattern.compile("EdgeType = (\\s*\"[a-zA-Z_0-9]+\",*\\s*)+");
    Matcher m3 = edgetype.matcher(headline);
    Pattern graphname = Pattern.compile("GraphName = \\s*\"[a-zA-Z_0-9]+\",*\\s*");
    Matcher m4 = graphname.matcher(headline);
    Pattern h = Pattern.compile(
        "HyperEdge = <(\\s*\"[a-zA-Z_0-9]+\",*\\s*){2}\\{(\\s*\"[a-zA-Z_0-9]+\",*\\s*)+\\}>");
    Matcher hm = h.matcher(headline);
    if (m1.matches()) {
      headline = headline.replace("\"", "");
      headline = headline.replace("GraphType = ", "");
      headline = headline.replace(" ", "");
      if (!headline.toLowerCase().equals("socialnetwork")) {
        logger.error("the Graph type " + headline + " is wrong");
        throw new ExceptionofInput("the Graph type " + headline + " is wrong");
      } else {
        logger.info("the Graph type is " + headline + "!");
      }
    } else if (m4.matches()) {
      headline = headline.replace("\"", "");
      headline = headline.replace("GraphName = ", "");
      headline = headline.replace(" ", "");
      System.out.println("Graphname is " + headline);
      logger.info("Graphname is " + headline);
    } else if (m2.matches()) {
      headline = headline.replace("\"", "");
      headline = headline.replace("VertexType = ", "");
      headline = headline.replace(" ", "");
      String[] types = headline.split(",");
      for (int i = 0; i < types.length; i++) {
        vtype.add(types[i]);
        logger.info("the vertex type " + types[i] + " is added to the graph.");
        System.out.println("the vertex type " + types[i] + " is added to the graph.");
      }
    } else if (m3.matches()) {
      headline = headline.replace("\"", "");
      headline = headline.replace("EdgeType = ", "");
      headline = headline.replace(" ", "");
      String[] types = headline.split(",");
      for (int i = 0; i < types.length; i++) {
        etype.add(types[i]);
        System.out.println("the edge type " + types[i] + " is added to the graph.");
        logger.info("the edge type " + types[i] + " is added to the graph.");
      }
    } else if (type.equals("V")) {
      dealvertex(headline, graph);
    } else if (type.equals("E")) {
      dealedge(headline, graph);
    } else if (hm.matches()) {
      logger.error("this graph cannot have hyperedge!");
      throw new ExceptionofUnproperEdge("this graph cannot have hyperedge!");
    } else {
      logger.error("The data read " + headline + " in does not match the regular expression!");
      throw new ExceptionofInput(
          "The data read " + headline + " in does not match the regular expression!");
    }
  }

  /**
   * readfile by Stream
   * 
   * @param fileName
   * @param graph
   * @throws ExceptionofInput
   * @throws ExceptionofUnproperEdge
   * @throws ExceptionofUndirection
   * @throws ExceptionofDirection
   * @throws FileNotFoundException 
   */
  public static long build1(String fileName, SocialNetwork graph) throws ExceptionofInput,
      ExceptionofUnproperEdge, ExceptionofUndirection, ExceptionofDirection, FileNotFoundException {
    long begin = System.currentTimeMillis();
    long time = 0;
    FileOutputStream out = new FileOutputStream(new File("src/source/write1.txt"));
    try {
      String t = "";
      File file = new File(fileName);
      InputStream in = null;
      in = new FileInputStream(file);
      int tempbyte;
      while ((tempbyte = in.read()) != -1) {
        char a = (char) tempbyte;
        if (a != '\n') {
          t += a;
        } else {
          String headline = t;
          t = "";
          dealstr(headline, graph);
          //out.write(headline.getBytes());
        }
      }
      out.close();
      long end = System.currentTimeMillis();
      time = end - begin;
      System.out.println("读取耗时:" + time + " 毫秒");
    } catch (IOException e) {
      System.out.println("Cannot read from the file!");
    } finally {
      System.out.println("the number of vertex in graph:" + "  " + graph.vertices().size());
      System.out.println("the number of edge in graph:" + "  " + graph.edges().size());
    }
    return time;
  }

  /**
   * readfile by Reader
   * 
   * @param fileName
   * @param graph
   * @throws ExceptionofInput
   * @throws ExceptionofUnproperEdge
   * @throws ExceptionofUndirection
   * @throws ExceptionofDirection
   * @throws IOException 
   */
  public static long build2(String fileName, SocialNetwork graph) throws ExceptionofInput,
      ExceptionofUnproperEdge, ExceptionofUndirection, ExceptionofDirection, IOException {
    long begin = System.currentTimeMillis();
    long time = 0;
    FileWriter fw = fw = new FileWriter("src/source/write2.txt");
    try {
      String t = "";
      int charread = 0;
      Reader reader = new InputStreamReader(new FileInputStream(fileName));
      while ((charread = reader.read()) != -1) {
        char a = (char) charread;
        if (a != '\n') {
          t += a;
        } else {
          String headline = t;
          t = "";
          dealstr(headline, graph);
          //fw.write(headline);
        }
      }
      fw.close();
      long end = System.currentTimeMillis();
      time = end - begin;
      System.out.println("读取耗时:" + time + " 毫秒");
    } catch (IOException e) {
      System.out.println("Cannot read from the file!");
    } finally {
      System.out.println("the number of vertex in graph:" + "  " + graph.vertices().size());
      System.out.println("the number of edge in graph:" + "  " + graph.edges().size());
    }
    return time;
  }

  /**
   * readfile by Buffer
   * 
   * @param fileName
   * @param graph
   * @throws ExceptionofInput
   * @throws ExceptionofUnproperEdge
   * @throws ExceptionofUndirection
   * @throws ExceptionofDirection
   * @throws FileNotFoundException 
   */
  public static long build3(String fileName, SocialNetwork graph) throws ExceptionofInput,
      ExceptionofUnproperEdge, ExceptionofUndirection, ExceptionofDirection, FileNotFoundException {
    long begin = System.currentTimeMillis();
    long time = 0;
    FileOutputStream outSTr = new FileOutputStream(new File("src/source/write3.txt"));
    BufferedOutputStream Buff = new BufferedOutputStream(outSTr);
    try {
      BufferedReader br = new BufferedReader(new FileReader(fileName));
      String headline;
      while ((headline = br.readLine()) != null) {
        // System.out.println(headline.substring(0, 1));
        dealstr(headline, graph);
        //Buff.write(headline.getBytes());
      }
      Buff.close();
      long end = System.currentTimeMillis();
      time = end - begin;
      System.out.println("读取耗时:" + time + " 毫秒");
    } catch (IOException e) {
      System.out.println("Cannot read from the file!");
    } finally {
      System.out.println("the number of vertex in graph:" + "  " + graph.vertices().size());
      System.out.println("the number of edge in graph:" + "  " + graph.edges().size());
    }
    return time;
  }

  /**
   * readfile by Scanner
   * 
   * @param fileName
   * @param graph
   * @throws ExceptionofInput
   * @throws ExceptionofUnproperEdge
   * @throws ExceptionofUndirection
   * @throws ExceptionofDirection
   */
  public static long build4(String fileName, SocialNetwork graph) throws ExceptionofInput,
      ExceptionofUnproperEdge, ExceptionofUndirection, ExceptionofDirection {
    long time = 0;
    long begin = System.currentTimeMillis();
    Scanner s = null;
    try {
      s = new Scanner(new BufferedReader(new FileReader(fileName)));
      s.useDelimiter("\n");
      while (s.hasNext()) {
        String headline = s.next();
        dealstr(headline, graph);
      }
      long end = System.currentTimeMillis();
      time = end - begin;
      System.out.println("读取耗时:" + time + " 毫秒");
    } catch (IOException e) {
      System.out.println("Cannot read from the file!");
    } finally {
      System.out.println("the number of vertex in graph:" + "  " + graph.vertices().size());
      System.out.println("the number of edge in graph:" + "  " + graph.edges().size());
      s.close();
    }
    return time;
  }

  /**
   * readfile by java.nio.file.Files
   * 
   * @param fileName
   * @param graph
   * @throws ExceptionofInput
   * @throws ExceptionofUnproperEdge
   * @throws ExceptionofUndirection
   * @throws ExceptionofDirection
   * @throws IOException 
   */
  public static long build5(String fileName, SocialNetwork graph) throws ExceptionofInput,
      ExceptionofUnproperEdge, ExceptionofUndirection, ExceptionofDirection, IOException {
    long time = 0;
    long begin = System.currentTimeMillis();
    BufferedWriter writer = Files.newBufferedWriter(Paths.get("src/source/write5.txt"), StandardCharsets.UTF_8);
    try {
      BufferedReader br = Files.newBufferedReader(Paths.get(fileName), StandardCharsets.UTF_8);
      String headline;
      while ((headline = br.readLine()) != null) {
        dealstr(headline, graph);
        //writer.write(headline);
      }
      writer.close();
      long end = System.currentTimeMillis();
      time = end - begin;
      System.out.println("读取耗时:" + time + " 毫秒");
    } catch (IOException e) {
      System.out.println("Cannot read from the file!");
    } finally {
      System.out.println("the number of vertex in graph:" + "  " + graph.vertices().size());
      System.out.println("the number of edge in graph:" + "  " + graph.edges().size());
    }
    return time;
  }

  @Override
  public Graph<Vertex, Edge> createGraph(String filePath, int n) throws ExceptionofInput,
      ExceptionofUnproperEdge, ExceptionofDirection, ExceptionofUndirection, IOException {
    SocialNetwork r = new SocialNetwork();
    Scanner sc = new Scanner(System.in);
    String path = filePath;
    boolean flag = true;
    while (flag) {
      try {
        if(n == 1) {
        build1(path, r);
        }
        else if(n == 2) {
          build2(path, r);
          }
        else if(n == 3) {
          build3(path, r);
          }
        else if(n == 4) {
          build4(path, r);
          }
        else if(n == 5) {
          build5(path, r);
          }
        flag = false;
      } catch (ExceptionofInput e) {
        flag = true;
        System.out.println(e);
        System.out.println("input another file path");
        path = sc.nextLine();
      } catch (ExceptionofUnproperEdge e) {
        flag = true;
        System.out.println(e);
        System.out.println("input another file path");
        path = sc.nextLine();
      }
    }
    return r;
  }
  /*
  public static void main(String[] args) throws Exception {
    SocialNetwork graph = new SocialNetwork();
    // Scanner sc = new Scanner(System.in);
    System.out.println("1.read file by Stream");
    System.out.println("2.read file by Reader");
    System.out.println("3.read file by Buffer");
    System.out.println("4.read file by Scanner");
    System.out.println("5.read file by java.nio.file.Files");
    long[] times = new long[5];
    try {
      //times[0] = build1("/Users/goerwa/learngit/Lab5_1160300521/src/source/file3.txt", graph);
      times[1] = build2("/Users/goerwa/learngit/Lab5_1160300521/src/source/file3.txt", graph);
      times[2] = build3("/Users/goerwa/learngit/Lab5_1160300521/src/source/file3.txt", graph);
      times[3] = build4("/Users/goerwa/learngit/Lab5_1160300521/src/source/file3.txt", graph);
      times[4] = build5("/Users/goerwa/learngit/Lab5_1160300521/src/source/file3.txt", graph);
    } catch (ExceptionofInput x) {
      System.out.println(x);
    } finally {
      for (int i = 1; i < 5; i++) {
        System.out.println(times[i] + "ms");
      }
      TimeGraph tg = new TimeGraph(times);
    }
  }
  */
}
