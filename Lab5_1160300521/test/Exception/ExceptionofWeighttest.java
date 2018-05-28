package Exception;

import org.junit.Test;
import factory.GraphPoetFactory;
import factory.MovieGraphFactory;
import factory.NetworkTopologyFactory;
import factory.SocialNetworkFactory;
import graph.GraphPoet;
import graph.MovieGraph;
import graph.NetworkTopology;
import graph.SocialNetwork;

public class ExceptionofWeighttest {
  // Testing strategy
  //
  // 带权边的权值不符合应用要求
  // 图类型 = 四种图
  // 带权边的权值是否符合要求 = 是，否

  // 图类型 = GraphPoet
  // 带权边的权值是否符合要求 = 否
  @Test(expected = ExceptionofInput.class)
  public void testweight1() throws ExceptionofInput, ExceptionofUnproperEdge, ExceptionofDirection,
      ExceptionofUndirection {
    GraphPoet g1 = new GraphPoet();
    GraphPoetFactory f1 = new GraphPoetFactory();
    f1.build("test/source/testweight1.txt", g1);
  }

  // 图类型 = SocialNetwork
  // 带权边的权值是否符合要求 = 否
  @Test(expected = ExceptionofUnproperEdge.class)
  public void testweight2() throws ExceptionofInput, ExceptionofUnproperEdge, ExceptionofDirection,
      ExceptionofUndirection {
    SocialNetwork g2 = new SocialNetwork();
    SocialNetworkFactory f2 = new SocialNetworkFactory();
    f2.build("test/source/testweight2.txt", g2);
  }

  // 图类型 = SocialNetwork
  // 带权边的权值是否符合要求 = 否
  @Test(expected = ExceptionofInput.class)
  public void testweight3() throws ExceptionofInput, ExceptionofUnproperEdge, ExceptionofDirection,
      ExceptionofUndirection {
    SocialNetwork g2 = new SocialNetwork();
    SocialNetworkFactory f2 = new SocialNetworkFactory();
    f2.build("test/source/testweight3.txt", g2);
  }

  // 图类型 = NetworkTopology
  // 带权边的权值是否符合要求 = 否
  @Test(expected = ExceptionofUnproperEdge.class)
  public void testweight4() throws ExceptionofInput, ExceptionofUnproperEdge, ExceptionofDirection,
      ExceptionofUndirection {
    NetworkTopology g3 = new NetworkTopology();
    NetworkTopologyFactory f3 = new NetworkTopologyFactory();
    f3.build("test/source/testweight4.txt", g3);
  }

  // 图类型 = MovieGraph
  // 带权边的权值是否符合要求 = 否
  @Test(expected = ExceptionofUnproperEdge.class)
  public void testweight5() throws ExceptionofInput, ExceptionofUnproperEdge, ExceptionofDirection,
      ExceptionofUndirection {
    MovieGraph g4 = new MovieGraph();
    MovieGraphFactory f4 = new MovieGraphFactory();
    f4.build("test/source/testweight5.txt", g4);
  }

}
