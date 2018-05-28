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

public class ExceptionofUnproper {
  // Testing strategy
  //
  // 在单重图中存在了多重边
  // 图类型 = 四种图
  // 是否出现多重边 = 是，否

  // 图类型 = GraphPoet
  // 是否出现多重边 = 是
  @Test(expected = ExceptionofUnproperEdge.class)
  public void testmulti1() throws ExceptionofInput, ExceptionofUnproperEdge, ExceptionofDirection,
      ExceptionofUndirection {
    GraphPoet g1 = new GraphPoet();
    GraphPoetFactory f1 = new GraphPoetFactory();
    f1.build("test/source/testmulti1.txt", g1);
  }

  // 图类型 = SocialNetwork
  // 是否出现多重边 = 是
  @Test(expected = ExceptionofUnproperEdge.class)
  public void testmulti2() throws ExceptionofInput, ExceptionofUnproperEdge, ExceptionofDirection,
      ExceptionofUndirection {
    SocialNetwork g2 = new SocialNetwork();
    SocialNetworkFactory f2 = new SocialNetworkFactory();
    f2.build("test/source/testmulti2.txt", g2);
  }

  // 图类型 = SocialNetwork
  // 是否出现多重边 = 是
  @Test(expected = ExceptionofUnproperEdge.class)
  public void testmulti3() throws ExceptionofInput, ExceptionofUnproperEdge, ExceptionofDirection,
      ExceptionofUndirection {
    SocialNetwork g2 = new SocialNetwork();
    SocialNetworkFactory f2 = new SocialNetworkFactory();
    f2.build("test/source/testmulti3.txt", g2);
  }

  // 图类型 = NetworkTopology
  // 是否出现多重边 = 是
  @Test(expected = ExceptionofUnproperEdge.class)
  public void testmulti4() throws ExceptionofInput, ExceptionofUnproperEdge, ExceptionofDirection,
      ExceptionofUndirection {
    NetworkTopology g3 = new NetworkTopology();
    NetworkTopologyFactory f3 = new NetworkTopologyFactory();
    f3.build("test/source/testmulti4.txt", g3);
  }

  // 图类型 = MovieGraph
  // 是否出现多重边 = 是
  @Test(expected = ExceptionofUnproperEdge.class)
  public void testmulti5() throws ExceptionofInput, ExceptionofUnproperEdge, ExceptionofDirection,
      ExceptionofUndirection {
    MovieGraph g4 = new MovieGraph();
    MovieGraphFactory f4 = new MovieGraphFactory();
    f4.build("test/source/testmulti5.txt", g4);
  }

  // 图类型 = MovieGraph
  // 是否出现多重边 = 是
  @Test(expected = ExceptionofUnproperEdge.class)
  public void testmulti6() throws ExceptionofInput, ExceptionofUnproperEdge, ExceptionofDirection,
      ExceptionofUndirection {
    MovieGraph g4 = new MovieGraph();
    MovieGraphFactory f4 = new MovieGraphFactory();
    f4.build("test/source/testmulti6.txt", g4);
  }

  // Testing strategy
  //
  // 在不应存在边的两个节点之间存在了边
  // 图类型 = 两种图
  // 顶点间是否可形成变 = 是，否

  // 图类型 = NetworkTopology
  // 顶点间是否可形成变 = 否
  @Test(expected = ExceptionofUnproperEdge.class)
  public void testwrongedge1() throws ExceptionofInput, ExceptionofUnproperEdge,
      ExceptionofDirection, ExceptionofUndirection {
    NetworkTopology g3 = new NetworkTopology();
    NetworkTopologyFactory f3 = new NetworkTopologyFactory();
    f3.build("test/source/testwrongedge1.txt", g3);
  }

  // 图类型 = NetworkTopology
  // 顶点间是否可形成变 = 否
  @Test(expected = ExceptionofUnproperEdge.class)
  public void testwrongedge2() throws ExceptionofInput, ExceptionofUnproperEdge,
      ExceptionofDirection, ExceptionofUndirection {
    NetworkTopology g3 = new NetworkTopology();
    NetworkTopologyFactory f3 = new NetworkTopologyFactory();
    f3.build("test/source/testwrongedge2.txt", g3);
  }

  // 图类型 = MovieGraph
  // 顶点间是否可形成变 = 否
  @Test(expected = ExceptionofUnproperEdge.class)
  public void testwrongedge3() throws ExceptionofInput, ExceptionofUnproperEdge,
      ExceptionofDirection, ExceptionofUndirection {
    MovieGraph g4 = new MovieGraph();
    MovieGraphFactory f4 = new MovieGraphFactory();
    f4.build("test/source/testwrongedge3.txt", g4);
  }

  // 图类型 = MovieGraph
  // 顶点间是否可形成变 = 否
  @Test(expected = ExceptionofUnproperEdge.class)
  public void testwrongedge4() throws ExceptionofInput, ExceptionofUnproperEdge,
      ExceptionofDirection, ExceptionofUndirection {
    MovieGraph g4 = new MovieGraph();
    MovieGraphFactory f4 = new MovieGraphFactory();
    f4.build("test/source/testwrongedge4.txt", g4);
  }

  // 图类型 = MovieGraph
  // 顶点间是否可形成变 = 否
  @Test(expected = AssertionError.class)
  public void testwrongedge5() throws ExceptionofInput, ExceptionofUnproperEdge,
      ExceptionofDirection, ExceptionofUndirection {
    MovieGraph g4 = new MovieGraph();
    MovieGraphFactory f4 = new MovieGraphFactory();
    f4.build("test/source/testwrongedge5.txt", g4);
  }

}
