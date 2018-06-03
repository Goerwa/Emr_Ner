package Exception;

import java.io.FileNotFoundException;
import org.junit.Test;
import factory.GraphPoetFactory;
import factory.MovieGraphFactory;
import factory.NetworkTopologyFactory;
import factory.SocialNetworkFactory;
import graph.GraphPoet;
import graph.MovieGraph;
import graph.NetworkTopology;
import graph.SocialNetwork;

public class ExceptionofUndifindtest {
  // Testing strategy
  //
  // 边或点使用的类型在未提前定义
  // 错误指令类型 = 点，边
  // 图类型 = 四种图
  // 边或点是否定义 = 是，否

  // 错误指令类型 = 点
  // 图类型 = GraphPoet
  // 边或点是否定义 = 否
  @Test(expected = ExceptionofInput.class)
  public void testinputudf1()
      throws ExceptionofInput, ExceptionofUnproperEdge, ExceptionofDirection, ExceptionofUndirection, FileNotFoundException {
    GraphPoet g1 = new GraphPoet();
    GraphPoetFactory f1 = new GraphPoetFactory();
    f1.build1("test/source/testinputudf1.txt", g1);
  }

  // 错误指令类型 = 边
  // 图类型 = GraphPoet
  // 边或点是否定义 = 否
  @Test(expected = ExceptionofInput.class)
  public void testinputudf2()
      throws ExceptionofInput, ExceptionofUnproperEdge, ExceptionofDirection, ExceptionofUndirection, FileNotFoundException {
    GraphPoet g1 = new GraphPoet();
    GraphPoetFactory f1 = new GraphPoetFactory();
    f1.build1("test/source/testinputudf2.txt", g1);
  }

  // 错误指令类型 = 点
  // 图类型 = SocialNetwork
  // 边或点是否定义 = 否
  @Test(expected = ExceptionofInput.class)
  public void testinputudf3() throws ExceptionofInput, ExceptionofUnproperEdge,
      ExceptionofDirection, ExceptionofUndirection, FileNotFoundException {
    SocialNetwork g2 = new SocialNetwork();
    SocialNetworkFactory f2 = new SocialNetworkFactory();
    f2.build1("test/source/testinputudf3.txt", g2);
  }

  // 错误指令类型 = 边
  // 图类型 = SocialNetwork
  // 边或点是否定义 = 否
  @Test(expected = ExceptionofInput.class)
  public void testinputudf4() throws ExceptionofInput, ExceptionofUnproperEdge,
      ExceptionofDirection, ExceptionofUndirection, FileNotFoundException {
    SocialNetwork g2 = new SocialNetwork();
    SocialNetworkFactory f2 = new SocialNetworkFactory();
    f2.build1("test/source/testinputudf4.txt", g2);
  }

  // 错误指令类型 = 点
  // 图类型 = NetworkTopology
  // 边或点是否定义 = 否
  @Test(expected = ExceptionofInput.class)
  public void testinputudf5() throws ExceptionofInput, ExceptionofUnproperEdge,
      ExceptionofDirection, ExceptionofUndirection, FileNotFoundException {
    NetworkTopology g3 = new NetworkTopology();
    NetworkTopologyFactory f3 = new NetworkTopologyFactory();
    f3.build1("test/source/testinputudf5.txt", g3);
  }

  // 错误指令类型 = 边
  // 图类型 = NetworkTopology
  // 边或点是否定义 = 否
  @Test(expected = ExceptionofInput.class)
  public void testinputudf6() throws ExceptionofInput, ExceptionofUnproperEdge,
      ExceptionofDirection, ExceptionofUndirection, FileNotFoundException {
    NetworkTopology g3 = new NetworkTopology();
    NetworkTopologyFactory f3 = new NetworkTopologyFactory();
    f3.build1("test/source/testinputudf6.txt", g3);
  }

  // 错误指令类型 = 点
  // 图类型 = MovieGraph
  // 边或点是否定义 = 否
  @Test(expected = ExceptionofInput.class)
  public void testinputudf7() throws ExceptionofInput, ExceptionofUnproperEdge,
      ExceptionofDirection, ExceptionofUndirection, FileNotFoundException {
    MovieGraph g4 = new MovieGraph();
    MovieGraphFactory f4 = new MovieGraphFactory();
    f4.build1("test/source/testinputudf7.txt", g4);
  }

  // 错误指令类型 = 边
  // 图类型 = MovieGraph
  // 边或点是否定义 = 否
  @Test(expected = ExceptionofInput.class)
  public void testinputudf8() throws ExceptionofInput, ExceptionofUnproperEdge,
      ExceptionofDirection, ExceptionofUndirection, FileNotFoundException {
    MovieGraph g4 = new MovieGraph();
    MovieGraphFactory f4 = new MovieGraphFactory();
    f4.build1("test/source/testinputudf8.txt", g4);
  }

  // 错误指令类型 = 边
  // 图类型 = MovieGraph
  // 边或点是否定义 = 否
  @Test(expected = ExceptionofInput.class)
  public void testinputudf9() throws ExceptionofInput, ExceptionofUnproperEdge,
      ExceptionofDirection, ExceptionofUndirection, FileNotFoundException {
    MovieGraph g4 = new MovieGraph();
    MovieGraphFactory f4 = new MovieGraphFactory();
    f4.build1("test/source/testinputudf9.txt", g4);
  }
}
