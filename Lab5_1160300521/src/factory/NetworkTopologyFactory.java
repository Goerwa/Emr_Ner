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
import Exception.ExceptionofInput;
import Exception.ExceptionofUndirection;
import Exception.ExceptionofUnproperEdge;
import edge.Edge;
import edge.MovieActorRelation;
import edge.MovieDirectorRelation;
import edge.NetworkConnection;
import edge.SameMovieHyperEdge;
import graph.Graph;
import graph.MovieGraph;
import graph.NetworkTopology;
import graph.SocialNetwork;
import vertex.Actor;
import vertex.Computer;
import vertex.Director;
import vertex.Movie;
import vertex.Router;
import vertex.Server;
import vertex.Vertex;

public class NetworkTopologyFactory extends GraphFactory {
  static Logger logger = Logger.getLogger(SocialNetworkFactory.class);
  private List<String> vtype = new ArrayList<>();
  private List<String> etype = new ArrayList<>();

  public NetworkTopologyFactory() {

  }

  private static String replacev(String headline) {
    headline = headline.replace("Vertex = <", "");
    headline = headline.replace(">", "");
    headline = headline.replace("<", "");
    headline = headline.replace("\"", "");
    headline = headline.replace(" ", "");
    // System.out.println(headline);
    return headline;
  }

  private static String replacee(String headline) {
    headline = headline.replace("Edge = <", "");
    headline = headline.replace(">", "");
    headline = headline.replace("\"", "");
    headline = headline.replace(" ", "");
    return headline;
  }

  private void dealvertex(String str, NetworkTopology graph) throws ExceptionofInput, IOException {
    Pattern p = Pattern.compile(
        "Vertex = <(\\s*\"[a-zA-Z_0-9]+\",*\\s*){2}<(\\s*\"[a-zA-Z_0-9]*([0-9]{1,}[.][0-9]*)*\",*\\s*)+>>");
    Matcher m = p.matcher(str);
    if (m.matches() == true) {
      str = replacev(str);
      String info[] = str.split(",");
      // showstr(info);
      if (info.length != 3) {
        logger.error("The data read in miss attributes!");
        throw new ExceptionofInput("The data read in miss attributes!");
      }
      if (!vtype.contains(info[1])) {
        logger.error("The type of vertex " + info[1] + " is not in the graph!");
        throw new ExceptionofInput("The type of vertex " + info[1] + " is not in the graph!");
      }
      if (info[1].equals("Computer")) {
        Computer w = new Computer(info[0]);
        w.fillVertexInfo(info);
        logger.info("add vertex " + info[0] + " to the graph!");
        graph.addVertex(w);
      } else if (info[1].equals("Router")) {
        Router w = new Router(info[0]);
        w.fillVertexInfo(info);
        logger.info("add vertex " + info[0] + " to the graph!");
        graph.addVertex(w);
      } else if (info[1].equals("Server")) {
        Server w = new Server(info[0]);
        w.fillVertexInfo(info);
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

  private void dealedge(String headline, NetworkTopology graph)
      throws ExceptionofInput, ExceptionofUnproperEdge, ExceptionofUndirection, IOException {
    Pattern e = Pattern.compile(
        "Edge = <(\\s*\"-*[a-zA-Z_0-9]*([0-9]{1,}[.][0-9]*)*\",*\\s*){5},*\\s\"(Yes|No)\"\\s*>");
    // System.out.println(headline);
    Matcher em = e.matcher(headline);
    if (em.matches() == true) {
      String replace = null;
      headline = replacee(headline);
      // System.out.println(headline);
      String str[] = headline.split(",");
      // showstr(str);
      double weight = Double.valueOf(str[2]);
      if (!(weight > 0)) {
        logger.error("The weight is " + weight + " not more than 0!");
        throw new ExceptionofUnproperEdge("The weight is " + weight + " not more than 0!");
      }
      Vertex src = null;
      Vertex tar = null;
      Set<Vertex> words = graph.vertices();
      for (Vertex w : words) {
        if (w.getlabel().equals(str[3])) {
          if (w.tellclass().equals("Computer"))
            src = (Computer) w;
          if (w.tellclass().equals("Router"))
            src = (Router) w;
          if (w.tellclass().equals("Server"))
            src = (Server) w;
        }
      }
      for (Vertex w : words) {
        if (w.getlabel().equals(str[4])) {
          // System.out.println(str[4] + " is the same as " + w.getlabel());
          if (w.tellclass().equals("Computer"))
            tar = (Computer) w;
          if (w.tellclass().equals("Router"))
            tar = (Router) w;
          if (w.tellclass().equals("Server"))
            tar = (Server) w;
        }
      }
      if (!etype.contains(str[1])) {
        logger.error("The type of edge " + str[1] + " is not in the graph!");
        throw new ExceptionofInput("The type of edge " + str[1] + " is not in the graph!");
      }
      if (str[1].equals("NetworkConnection")) {
        try {
          if (str[5].equals("Yes")) {
            logger.error("The edge add in can not be an directededge!");
            throw new ExceptionofUndirection("The edge add in can not be an directededge!");
          }
        } catch (ExceptionofUndirection ex) {
          System.out.println(ex);
        }
        if (src.tellclass().equals("Computer")) {
          if (tar.tellclass().equals("Computer")) {
            logger.error("Provided vertices cannot form edges!");
            throw new ExceptionofUnproperEdge("Provided vertices cannot form edges!");
          }
        }
        if (src.tellclass().equals("Server")) {
          if (tar.tellclass().equals("Server")) {
            logger.error("Provided vertices cannot form edges!");
            throw new ExceptionofUnproperEdge("Provided vertices cannot form edges!");
          }
        }
        NetworkConnection edge = new NetworkConnection(str[0], src, tar, weight);
        logger.info("add edge " + str[0] + " to the graph!");
        // System.out.println(edge.getsource().getlabel());
        // System.out.println(edge.gettarget().getlabel());
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

  public void build(String fileName, NetworkTopology graph)
      throws ExceptionofInput, ExceptionofUnproperEdge, ExceptionofUndirection {
    try {
      BufferedReader br = new BufferedReader(new FileReader(fileName));
      String headline;
      while ((headline = br.readLine()) != null) {
        // System.out.println(headline.substring(0, 1));
        String type;
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
          if (!headline.toLowerCase().equals("networktopology")) {
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

          headline = headline.replace("VertexType = ", "");
          headline = headline.replace("\"", "");
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
    } catch (IOException e) {
      System.out.println("Cannot read from the file!");
    } finally {
      System.out.println("the number of vertex in graph:" + "  " + graph.vertices().size());
      System.out.println("the number of edge in graph:" + "  " + graph.edges().size());
    }

  }

  @Override
  public Graph<Vertex, Edge> createGraph(String filePath)
      throws ExceptionofInput, ExceptionofUnproperEdge, ExceptionofUndirection {
    NetworkTopology r = new NetworkTopology();
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
      }
    }
    return r;
  }
  /*
   * public static void main(String[] args) throws ExceptionofInput, ExceptionofUnproperEdge,
   * ExceptionofUndirection { NetworkTopology graph = new NetworkTopology(); try{
   * build("src/source/test3.txt",graph); }catch(ExceptionofInput x) { System.out.println(x);
   * //build("src/source/test2right.txt",graph); } }
   */

}
