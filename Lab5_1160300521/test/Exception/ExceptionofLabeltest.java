package Exception;

import static org.junit.Assert.assertTrue;
import java.io.IOException;
import org.junit.Test;
import edge.WordNeighborhood;
import factory.GraphPoetFactory;
import factory.MovieGraphFactory;
import factory.NetworkTopologyFactory;
import factory.SocialNetworkFactory;
import graph.GraphPoet;
import graph.MovieGraph;
import graph.NetworkTopology;
import graph.SocialNetwork;
import vertex.Actor;
import vertex.Computer;
import vertex.Movie;
import vertex.Person;
import vertex.Router;
import vertex.Vertex;
import vertex.Word;
import edge.Edge;
import edge.FriendTie;
import edge.MovieActorRelation;
import edge.NetworkConnection;;

public class ExceptionofLabeltest {
  // Testing strategy
  //
  // 多个节点的 label 重复、多个边的 label 重复
  // 图类型 = 四种图
  // 指令类型 = 点，边
  // 是否出现重复 = 是，否

  // 图类型 = GraphPoet
  // 指令类型 = 点
  // 是否出现重复 = 是

  Word w1 = new Word("w1");
  Word w2 = new Word("w1");
  Word w3 = new Word("w1");

  @Test
  public void testlabel1() throws ExceptionofInput, ExceptionofUnproperEdge, ExceptionofDirection,
      ExceptionofUndirection, IOException {
    GraphPoet g1 = new GraphPoet();
    g1.addVertex(w1);
    g1.addVertex(w2);
    g1.addVertex(w3);
    for (Vertex v : g1.vertices()) {
      System.out.println(v.getlabel());
    }
    assertTrue("expected true", g1.vertices().size() == 3);

  }

  // 图类型 = GraphPoet
  // 指令类型 = 边
  // 是否出现重复 = 是
  @Test
  public void testlabel2() throws ExceptionofInput, ExceptionofUnproperEdge, ExceptionofDirection,
      ExceptionofUndirection, IOException {
    GraphPoet g1 = new GraphPoet();
    g1.addVertex(w1);
    g1.addVertex(w2);
    g1.addVertex(w3);
    WordNeighborhood wn1 = new WordNeighborhood("wn1", w1, w2, 1.0);
    WordNeighborhood wn2 = new WordNeighborhood("wn1", w1, w3, 1.0);
    WordNeighborhood wn3 = new WordNeighborhood("wn1", w2, w3, 1.0);
    g1.addEdge(wn1);
    g1.addEdge(wn2);
    g1.addEdge(wn3);
    for (Edge e : g1.edges()) {
      System.out.println(e.getlabel());
    }
    assertTrue("expected true", g1.edges().size() == 3);

  }

  Person p1 = new Person("p1");
  Person p2 = new Person("p1");
  Person p3 = new Person("p1");

  // 图类型 = SocialNetwork
  // 指令类型 = 点
  // 是否出现重复 = 是
  @Test
  public void testlabel3() throws ExceptionofInput, ExceptionofUnproperEdge, ExceptionofDirection,
      ExceptionofUndirection, IOException {
    SocialNetwork g2 = new SocialNetwork();
    g2.addVertex(p1);
    g2.addVertex(p2);
    g2.addVertex(p3);
    for (Vertex v : g2.vertices()) {
      System.out.println(v.getlabel());
    }
    assertTrue("expected true", g2.vertices().size() == 3);
  }

  // 图类型 = SocialNetwork
  // 指令类型 = 边
  // 是否出现重复 = 是
  @Test
  public void testlabel4() throws ExceptionofInput, ExceptionofUnproperEdge, ExceptionofDirection,
      ExceptionofUndirection, IOException {
    SocialNetwork g2 = new SocialNetwork();
    g2.addVertex(p1);
    g2.addVertex(p2);
    g2.addVertex(p3);
    FriendTie ft1 = new FriendTie("ft1", p1, p2, 0.50);
    FriendTie ft2 = new FriendTie("ft1", p1, p3, 0.50);
    FriendTie ft3 = new FriendTie("ft1", p3, p2, 0.50);
    g2.addEdge(ft1);
    g2.addEdge(ft2);
    g2.addEdge(ft3);
    for (Edge e : g2.edges()) {
      System.out.println(e.getlabel());
    }
    assertTrue("expected true", g2.edges().size() == 3);
  }

  Computer c1 = new Computer("c1");
  Computer c2 = new Computer("c1");
  Computer c3 = new Computer("c1");
  Router r1 = new Router("r1");

  // 图类型 = NetworkTopology
  // 指令类型 = 点
  // 是否出现重复 = 是
  @Test
  public void testlabel5() throws ExceptionofInput, ExceptionofUnproperEdge, ExceptionofDirection,
      ExceptionofUndirection, IOException {
    NetworkTopology g3 = new NetworkTopology();
    g3.addVertex(c1);
    g3.addVertex(r1);
    g3.addVertex(c2);
    g3.addVertex(c3);
    for (Vertex v : g3.vertices()) {
      System.out.println(v.getlabel());
    }
    assertTrue("expected true", g3.vertices().size() == 4);
  }

  // 图类型 = NetworkTopology
  // 指令类型 = 边
  // 是否出现多重边 = 是
  @Test
  public void testlabel6() throws ExceptionofInput, ExceptionofUnproperEdge, ExceptionofDirection,
      ExceptionofUndirection, IOException {
    NetworkTopology g3 = new NetworkTopology();
    g3.addVertex(c1);
    g3.addVertex(r1);
    g3.addVertex(c2);
    g3.addVertex(c3);
    NetworkConnection nc1 = new NetworkConnection("nc1", c1, r1, 100.0);
    NetworkConnection nc2 = new NetworkConnection("nc1", c2, r1, 100.0);
    NetworkConnection nc3 = new NetworkConnection("nc1", c3, r1, 100.0);
    g3.addEdge(nc1);
    g3.addEdge(nc2);
    g3.addEdge(nc3);
    for (Edge e : g3.edges()) {
      System.out.println(e.getlabel());
    }
    assertTrue("expected true", g3.edges().size() == 3);
  }

  Movie m1 = new Movie("m1");
  Actor a1 = new Actor("a1");
  Actor a2 = new Actor("a1");
  Actor a3 = new Actor("a1");

  // 图类型 = MovieGraph
  // 指令类型 = 点
  // 是否出现重复 = 是
  @Test
  public void testlabel7() throws ExceptionofInput, ExceptionofUnproperEdge, ExceptionofDirection,
      ExceptionofUndirection, IOException {
    MovieGraph g4 = new MovieGraph();
    g4.addVertex(m1);
    g4.addVertex(a1);
    g4.addVertex(a2);
    g4.addVertex(a3);
    for (Vertex v : g4.vertices()) {
      System.out.println(v.getlabel());
    }
    assertTrue("expected true", g4.vertices().size() == 4);
  }

  // 图类型 = MovieGraph
  // 指令类型 = 边
  // 是否出现重复 = 是
  @Test
  public void testlabel8() throws ExceptionofInput, ExceptionofUnproperEdge, ExceptionofDirection,
      ExceptionofUndirection, IOException {
    MovieGraph g4 = new MovieGraph();
    g4.addVertex(m1);
    g4.addVertex(a1);
    g4.addVertex(a2);
    g4.addVertex(a3);
    MovieActorRelation ma1 = new MovieActorRelation("ma1", m1, a1, 1.0);
    MovieActorRelation ma2 = new MovieActorRelation("ma1", m1, a2, 1.0);
    MovieActorRelation ma3 = new MovieActorRelation("ma1", m1, a3, 1.0);
    g4.addEdge(ma1);
    g4.addEdge(ma2);
    g4.addEdge(ma3);
    for (Edge e : g4.edges()) {
      System.out.println(e.getlabel());
    }
    assertTrue("expected true", g4.edges().size() == 3);
  }
}
