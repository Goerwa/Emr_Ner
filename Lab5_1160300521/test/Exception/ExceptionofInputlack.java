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

public class ExceptionofInputlack {
  // Testing strategy
  //
  // 输入信息为包含节点的所有属性
  // 图类型 = 三种图
  // 所有属性是否包含 = 包含,不包含


  // 图类型 = SocialNetwork
  // 所有属性是否包含 = 不包含
  @Test(expected = ExceptionofInput.class)
  public void testinputlack1() throws ExceptionofInput, ExceptionofUnproperEdge,
      ExceptionofDirection, ExceptionofUndirection {
    SocialNetwork g2 = new SocialNetwork();
    SocialNetworkFactory f2 = new SocialNetworkFactory();
    f2.build("test/source/testinputlack1.txt", g2);
  }

  // 图类型 = SocialNetwork
  // 所有属性是否包含 = 不包含
  @Test(expected = ExceptionofInput.class)
  public void testinputlack2() throws ExceptionofInput, ExceptionofUnproperEdge,
      ExceptionofDirection, ExceptionofUndirection {
    SocialNetwork g2 = new SocialNetwork();
    SocialNetworkFactory f2 = new SocialNetworkFactory();
    f2.build("test/source/testinputlack2.txt", g2);
  }


  // 图类型 = NetworkTopology
  // 所有属性是否包含 = 不包含
  @Test(expected = ExceptionofInput.class)
  public void testinputlack3() throws ExceptionofInput, ExceptionofUnproperEdge,
      ExceptionofDirection, ExceptionofUndirection {
    NetworkTopology g3 = new NetworkTopology();
    NetworkTopologyFactory f3 = new NetworkTopologyFactory();
    f3.build("test/source/testinputlack3.txt", g3);
  }

  // 图类型 = NetworkTopology
  // 所有属性是否包含 = 不包含
  @Test(expected = ExceptionofInput.class)
  public void testinputlack4() throws ExceptionofInput, ExceptionofUnproperEdge,
      ExceptionofDirection, ExceptionofUndirection {
    NetworkTopology g3 = new NetworkTopology();
    NetworkTopologyFactory f3 = new NetworkTopologyFactory();
    f3.build("test/source/testinputlack4.txt", g3);
  }


  // 图类型 = MovieGraph
  // 所有属性是否包含 = 不包含
  @Test(expected = ExceptionofInput.class)
  public void testinputlack5() throws ExceptionofInput, ExceptionofUnproperEdge,
      ExceptionofDirection, ExceptionofUndirection {
    MovieGraph g4 = new MovieGraph();
    MovieGraphFactory f4 = new MovieGraphFactory();
    f4.build("test/source/testinputlack5.txt", g4);
  }

  // 图类型 = MovieGraph
  // 所有属性是否包含 = 不包含
  @Test(expected = ExceptionofInput.class)
  public void testinputlack6() throws ExceptionofInput, ExceptionofUnproperEdge,
      ExceptionofDirection, ExceptionofUndirection {
    MovieGraph g4 = new MovieGraph();
    MovieGraphFactory f4 = new MovieGraphFactory();
    f4.build("test/source/testinputlack6.txt", g4);
  }

  // 图类型 = MovieGraph
  // 所有属性是否包含 = 不包含
  @Test(expected = ExceptionofInput.class)
  public void testinputlack7() throws ExceptionofInput, ExceptionofUnproperEdge,
      ExceptionofDirection, ExceptionofUndirection {
    MovieGraph g4 = new MovieGraph();
    MovieGraphFactory f4 = new MovieGraphFactory();
    f4.build("test/source/testinputlack7.txt", g4);
  }

  // 图类型 = MovieGraph
  // 所有属性是否包含 = 不包含
  @Test(expected = ExceptionofInput.class)
  public void testinputlack8() throws ExceptionofInput, ExceptionofUnproperEdge,
      ExceptionofDirection, ExceptionofUndirection {
    MovieGraph g4 = new MovieGraph();
    MovieGraphFactory f4 = new MovieGraphFactory();
    f4.build("test/source/testinputlack8.txt", g4);
  }

}
