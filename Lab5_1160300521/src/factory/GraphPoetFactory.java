package factory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.log4j.Logger;
import Exception.ExceptionofDirection;
import Exception.ExceptionofDirectionTest;
import Exception.ExceptionofInput;
import Exception.ExceptionofUnproperEdge;
import edge.Edge;
import edge.WordNeighborhood;
import graph.Graph;
import graph.GraphPoet;
import vertex.Vertex;
import vertex.Word;

public class GraphPoetFactory extends GraphFactory {
  static Logger logger = Logger.getLogger(SocialNetworkFactory.class);
  private List<String> vtype = new ArrayList<>();
  private List<String> etype = new ArrayList<>();


  public GraphPoetFactory() {}

  private static String replacev(String headline) {
    headline = headline.replace("Vertex = <", "");
    headline = headline.replace(">", "");
    headline = headline.replace("\"", "");
    // headline = headline.replace("”", "");
    headline = headline.replace(" ", "");
    return headline;
  }

  private static String replacee(String headline) {
    headline = headline.replace("Edge = <", "");
    headline = headline.replace(">", "");
    headline = headline.replace("\"", "");
    // headline = headline.replace("”", "");
    headline = headline.replace(" ", "");
    return headline;
  }

  private void dealvertex(String str, GraphPoet graph) throws ExceptionofInput, IOException {
    Pattern p = Pattern.compile("Vertex = <(\\s*\"[a-zA-Z_0-9]+\",*\\s*){2}>");
    Matcher m = p.matcher(str);
    if (m.matches()) {
      str = replacev(str);
      // System.out.println(str);
      String info[] = null;
      info = str.split(",");
      info[1] = info[1].trim();
      if (!vtype.contains(info[1])) {
        logger.error("The type of vertex " + info[1] + " is not in the graph!");
        throw new ExceptionofInput("The type of vertex " + info[1] + " is not in the graph!");
      }
      if (info[1].equals("Word")) {
        Word w = (Word) new WordVertexFactory().createVertex(info[0], info);
        logger.info("add vertex " + info[0] + " to the graph!");
        graph.addVertex(w);
      } else {
        logger.error("The type of vertex " + info[1] + " is not in the graph!");
        throw new ExceptionofInput("The type of vertex " + info[1] + " is not in the graph!");
      }
    } else {
      logger.error("The data read " + str + " in does not match the regular expression!");
      throw new ExceptionofInput(
          "The data read " + str + " in does not match the regular expression!");
    }
  }

  private void dealedge(String headline, GraphPoet graph)
      throws ExceptionofInput, ExceptionofUnproperEdge, ExceptionofDirection, IOException {
    Pattern e = Pattern.compile(
        "Edge = <(\\s*\"[a-zA-Z_0-9]*([0-9]{1,}[.][0-9]*)*\",*\\s*){5},*\\s\"(Yes|No)\"\\s*>");
    // Pattern e = Pattern.compile("Edge = <(\\s*“[a-zA-Z_0-9]*([0-9]{1,}[.][0-9]*)*”,*\\s*){6}>");
    Matcher em = e.matcher(headline);
    if (em.matches() == true) {
      String replace = null;
      headline = replacee(headline);
      // System.out.println(headline);
      String str[] = headline.split(",");
      // showstr(str);
      double weight = Double.valueOf(str[2]);
      if (str[5].equals("No")) {
        logger.error("The edge add in can not be an undirectededge!");
        throw new ExceptionofDirection("The edge add in can not be an undirectededge!");
      }
      Word src = null;
      Word tar = null;
      Set<Vertex> words = graph.vertices();
      for (Vertex w : words) {
        if (w.getlabel().equals(str[3])) {
          src = (Word) w;
        }
        if (w.getlabel().equals(str[4]))
          tar = (Word) w;
      }
      if (!etype.contains(str[1])) {
        logger.error("The type of edge " + str[1] + " is not in the graph!");
        throw new ExceptionofInput("The type of edge " + str[1] + " is not in the graph!");
      }
      if (str[1].equals("WordNeighborhood")) {
        WordNeighborhood edge = new WordNeighborhood(str[0], src, tar, weight);
        logger.info("add edge " + str[0] + " to the graph!");
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

  public void build(String fileName, GraphPoet graph)
      throws ExceptionofInput, ExceptionofUnproperEdge, ExceptionofDirection {
    try {
      BufferedReader br = new BufferedReader(new FileReader(fileName));
      String headline;
      while ((headline = br.readLine()) != null) {
        // System.out.println(headline.substring(0, 1));
        String type;
        // System.out.println("eee" + headline.equals(""));
        if (!headline.equals(""))
          type = headline.substring(0, 1);
        else
          continue;
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
          if (!headline.toLowerCase().equals("graphpoet")) {
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
            this.vtype.add(types[i]);
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
    } catch (IOException e) {
      System.out.println("Cannot read from the file!");
      logger.error("Cannot read from the file!");
      return;
    } finally {
      System.out.println("the number of vertex in graph:" + "  " + graph.vertices().size());
      System.out.println("the number of edge in graph:" + "  " + graph.edges().size());
    }
  }

  @Override
  public Graph<Vertex, Edge> createGraph(String filePath)
      throws ExceptionofInput, ExceptionofUnproperEdge, ExceptionofDirection {
    GraphPoet r = new GraphPoet();
    Scanner sc = new Scanner(System.in);
    String path = filePath;
    boolean flag = true;
    while (flag) {
      try {
        build(path, r);
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
      } catch (ExceptionofDirection e) {
        flag = true;
        System.out.println(e);
        System.out.println("input another file path");
        path = sc.nextLine();
      }
    }
    return r;
  }
  /*
   * public static void main(String[] args) throws ExceptionofUnproperEdge, ExceptionofInput,
   * ExceptionofDirection { GraphPoet r= new GraphPoet(); Scanner sc = new Scanner(System.in);
   * String path = "src/source/test1.txt"; //tring path = "test/source/testinput7.txt"; boolean flag
   * = true; while(flag) { try { build(path, r); flag = false; } catch(ExceptionofInput e) { flag =
   * true; System.out.println(e); System.out.println("\n" +"input another file path"); path =
   * sc.nextLine(); } } }
   */


}
