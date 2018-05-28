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

public class ExceptionofHyperedgetest {
  // Testing strategy
  //
  // 某超边中包含的节点数小于 2
  // 超边中的节点数 = 大于等于2，小于2


  // 图类型 = MovieGraph
  // 超边中的节点数 = 小于2
  @Test(expected = ExceptionofUnproperEdge.class)
  public void testhyper1() throws ExceptionofInput, ExceptionofUnproperEdge, ExceptionofDirection,
      ExceptionofUndirection {
    MovieGraph g4 = new MovieGraph();
    MovieGraphFactory f4 = new MovieGraphFactory();
    f4.build("test/source/testhyper1.txt", g4);
  }
  // Testing strategy
  //
  // 在不应存在超边的图中加入了超边
  // 图类型 = 三种图
  // 是否可出现超边 = 是，否

  // 图类型 = GraphPoet
  // 是否可出现超边 = 否
  @Test(expected = ExceptionofUnproperEdge.class)
  public void testhyperedge1() throws ExceptionofInput, ExceptionofUnproperEdge,
      ExceptionofDirection, ExceptionofUndirection {
    GraphPoet g1 = new GraphPoet();
    GraphPoetFactory f1 = new GraphPoetFactory();
    f1.build("test/source/testhyperedge1.txt", g1);
  }

  // 图类型 = SocialNetwork
  // 是否可出现超边 = 否
  @Test(expected = ExceptionofUnproperEdge.class)
  public void testhyperedge2() throws ExceptionofInput, ExceptionofUnproperEdge,
      ExceptionofDirection, ExceptionofUndirection {
    SocialNetwork g2 = new SocialNetwork();
    SocialNetworkFactory f2 = new SocialNetworkFactory();
    f2.build("test/source/testhyperedge2.txt", g2);
  }

  // 图类型 = NetworkTopology
  // 是否可出现超边 = 否
  @Test(expected = ExceptionofUnproperEdge.class)
  public void testhyperedge3() throws ExceptionofInput, ExceptionofUnproperEdge,
      ExceptionofDirection, ExceptionofUndirection {
    NetworkTopology g3 = new NetworkTopology();
    NetworkTopologyFactory f3 = new NetworkTopologyFactory();
    f3.build("test/source/testhyperedge3.txt", g3);
  }


}
