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

public class ExceptionofUnproperinput {
  // Testing strategy
  //
  // 在某种类型的图应用中引入了不应出现的节点或边的类型
  // 错误指令类型 = 点，边
  // 图类型 = 四种图
  // 边或点是否应该出现 = 是，否

  // 错误指令类型 = 点
  // 图类型 = GraphPoet
  // 边或点是否应该出现 = 否
  @Test(expected = ExceptionofInput.class)
  public void testinputupi1()
      throws ExceptionofInput, ExceptionofUnproperEdge, ExceptionofDirection {
    GraphPoet g1 = new GraphPoet();
    GraphPoetFactory f1 = new GraphPoetFactory();
    f1.build("test/source/testinputupi1.txt", g1);
  }

  // 错误指令类型 = 边
  // 图类型 = GraphPoet
  // 边或点是否应该出现 = 否
  @Test(expected = ExceptionofInput.class)
  public void testinputupi2()
      throws ExceptionofInput, ExceptionofUnproperEdge, ExceptionofDirection {
    GraphPoet g1 = new GraphPoet();
    GraphPoetFactory f1 = new GraphPoetFactory();
    f1.build("test/source/testinputupi2.txt", g1);
  }

  // 错误指令类型 = 点
  // 图类型 = SocialNetwork
  // 边或点是否应该出现 = 否
  @Test(expected = ExceptionofInput.class)
  public void testinputupi3() throws ExceptionofInput, ExceptionofUnproperEdge,
      ExceptionofDirection, ExceptionofUndirection {
    SocialNetwork g2 = new SocialNetwork();
    SocialNetworkFactory f2 = new SocialNetworkFactory();
    f2.build("test/source/testinputupi3.txt", g2);
  }

  // 错误指令类型 = 边
  // 图类型 = SocialNetwork
  // 边或点是否应该出现 = 否
  @Test(expected = ExceptionofInput.class)
  public void testinputupi4() throws ExceptionofInput, ExceptionofUnproperEdge,
      ExceptionofDirection, ExceptionofUndirection {
    SocialNetwork g2 = new SocialNetwork();
    SocialNetworkFactory f2 = new SocialNetworkFactory();
    f2.build("test/source/testinputupi4.txt", g2);
  }

  // 错误指令类型 = 点
  // 图类型 = NetworkTopology
  // 边或点是否应该出现 = 否
  @Test(expected = ExceptionofInput.class)
  public void testinputupi5() throws ExceptionofInput, ExceptionofUnproperEdge,
      ExceptionofDirection, ExceptionofUndirection {
    NetworkTopology g3 = new NetworkTopology();
    NetworkTopologyFactory f3 = new NetworkTopologyFactory();
    f3.build("test/source/testinputupi5.txt", g3);
  }

  // 错误指令类型 = 边
  // 图类型 = NetworkTopology
  // 边或点是否应该出现 = 否
  @Test(expected = ExceptionofInput.class)
  public void testinputupi6() throws ExceptionofInput, ExceptionofUnproperEdge,
      ExceptionofDirection, ExceptionofUndirection {
    NetworkTopology g3 = new NetworkTopology();
    NetworkTopologyFactory f3 = new NetworkTopologyFactory();
    f3.build("test/source/testinputupi6.txt", g3);
  }

  // 错误指令类型 = 点
  // 图类型 = MovieGraph
  // 边或点是否应该出现 = 否
  @Test(expected = ExceptionofInput.class)
  public void testinputupi7() throws ExceptionofInput, ExceptionofUnproperEdge,
      ExceptionofDirection, ExceptionofUndirection {
    MovieGraph g4 = new MovieGraph();
    MovieGraphFactory f4 = new MovieGraphFactory();
    f4.build("test/source/testinputupi7.txt", g4);
  }

  // 错误指令类型 = 边
  // 图类型 = MovieGraph
  // 边或点是否应该出现 = 否
  @Test(expected = ExceptionofInput.class)
  public void testinputupi8() throws ExceptionofInput, ExceptionofUnproperEdge,
      ExceptionofDirection, ExceptionofUndirection {
    MovieGraph g4 = new MovieGraph();
    MovieGraphFactory f4 = new MovieGraphFactory();
    f4.build("test/source/testinputupi8.txt", g4);
  }

  // 错误指令类型 = 边
  // 图类型 = MovieGraph
  // 边或点是否应该出现 = 否
  @Test(expected = ExceptionofInput.class)
  public void testinputupi9() throws ExceptionofInput, ExceptionofUnproperEdge,
      ExceptionofDirection, ExceptionofUndirection {
    MovieGraph g4 = new MovieGraph();
    MovieGraphFactory f4 = new MovieGraphFactory();
    f4.build("test/source/testinputupi9.txt", g4);
  }
}
