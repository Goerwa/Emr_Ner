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
import vertex.Word;

public class ExceptionofInputtest {
    
    // Testing strategy 
    //
    // 	文件中存在不符合语法规则的语句
    //  错误指令类型 = 点，边
    //  图类型 = 四种图
    //  语法规范 = 符合，不符合
    
    
    //  错误指令类型 = 无
    //  图类型 = GraphPoet
    //  语法规范 = 符合
    @Test
    public void testinputright1() throws ExceptionofInput, ExceptionofUnproperEdge, ExceptionofDirection {
          GraphPoet g1 = new GraphPoet();
          GraphPoetFactory f1 = new GraphPoetFactory();
          f1.build("src/source/test1.txt", g1);  
    }
    
    //  错误指令类型 = 点
    //  图类型 = GraphPoet
    //  语法规范 = 不符合 
    @Test(expected = ExceptionofInput.class)
    public void testinput1() throws ExceptionofInput, ExceptionofUnproperEdge, ExceptionofDirection {
          GraphPoet g1 = new GraphPoet();
          GraphPoetFactory f1 = new GraphPoetFactory();
          f1.build("test/source/testinput1.txt", g1);  
    }
    
    
    //  错误指令类型 = 边
    //  图类型 = GraphPoet
    //  语法规范 = 不符合 
    @Test(expected = ExceptionofInput.class)
    public void testinput2() throws ExceptionofInput, ExceptionofUnproperEdge, ExceptionofDirection {
          GraphPoet g1 = new GraphPoet();
          GraphPoetFactory f1 = new GraphPoetFactory();
          f1.build("test/source/testinput2.txt", g1);  
    }
    //  错误指令类型 = 边
    //  图类型 = GraphPoet
    //  语法规范 = 不符合 
    @Test(expected = ExceptionofInput.class)
    public void testinput3() throws ExceptionofInput, ExceptionofUnproperEdge, ExceptionofDirection {
          GraphPoet g1 = new GraphPoet();
          GraphPoetFactory f1 = new GraphPoetFactory();
          f1.build("test/source/testinput3.txt", g1);  
    }	
    //  错误指令类型 = 边
    //  图类型 =GraphPoet
    //  语法规范 = 不符合 
    @Test(expected = ExceptionofInput.class)
    public void testinput4() throws ExceptionofInput, ExceptionofUnproperEdge, ExceptionofDirection {
          GraphPoet g1 = new GraphPoet();
          GraphPoetFactory f1 = new GraphPoetFactory();
          f1.build("test/source/testinput4.txt", g1);  
    }
    
    //  错误指令类型 = 无
    //  图类型 = SocialNetwork
    //  语法规范 = 符合
    @Test
    public void testinputright2() throws ExceptionofInput, ExceptionofUnproperEdge, ExceptionofDirection, ExceptionofUndirection {
          SocialNetwork g2 = new SocialNetwork();
          SocialNetworkFactory f2 = new SocialNetworkFactory();
          f2.build("src/source/test2.txt", g2);  
    }
    
    //  错误指令类型 = 点
    //  图类型 = SocialNetwork
    //  语法规范 = 不符合 
    @Test(expected = ExceptionofInput.class)
    public void testinput5() throws ExceptionofInput, ExceptionofUnproperEdge, ExceptionofDirection, ExceptionofUndirection {
	SocialNetwork g2 = new SocialNetwork();
        SocialNetworkFactory f2 = new SocialNetworkFactory();
          f2.build("test/source/testinput5.txt", g2);  
    }
    //  错误指令类型 = 边
    //  图类型 = SocialNetwork
    //  语法规范 = 不符合 
    @Test(expected = ExceptionofInput.class)
    public void testinput6() throws ExceptionofInput, ExceptionofUnproperEdge, ExceptionofDirection, ExceptionofUndirection {
	SocialNetwork g2 = new SocialNetwork();
        SocialNetworkFactory f2 = new SocialNetworkFactory();
          f2.build("test/source/testinput6.txt", g2);  
    }
    //  错误指令类型 = 边
    //  图类型 = SocialNetwork
    //  语法规范 = 不符合 
    @Test(expected = ExceptionofInput.class)
    public void testinput7() throws ExceptionofInput, ExceptionofUnproperEdge, ExceptionofDirection, ExceptionofUndirection {
	SocialNetwork g2 = new SocialNetwork();
        SocialNetworkFactory f2 = new SocialNetworkFactory();
          f2.build("test/source/testinput7.txt", g2);  
    }
    //  错误指令类型 = 边
    //  图类型 = SocialNetwork
    //  语法规范 = 不符合 
    @Test(expected = ExceptionofInput.class)
    public void testinput8() throws ExceptionofInput, ExceptionofUnproperEdge, ExceptionofDirection, ExceptionofUndirection {
	SocialNetwork g2 = new SocialNetwork();
        SocialNetworkFactory f2 = new SocialNetworkFactory();
          f2.build("test/source/testinput8.txt", g2);  
    }
    //  错误指令类型 = 无
    //  图类型 = NetworkTopology
    //  语法规范 = 符合
    @Test
    public void testinputright3() throws ExceptionofInput, ExceptionofUnproperEdge, ExceptionofDirection, ExceptionofUndirection {
	NetworkTopology g3 = new NetworkTopology();
	NetworkTopologyFactory f3 = new NetworkTopologyFactory();
          f3.build("src/source/test3.txt", g3);  
    }
    
    //  错误指令类型 = 点
    //  图类型 = NetworkTopology
    //  语法规范 = 不符合 
    @Test(expected = ExceptionofInput.class)
    public void testinput9() throws ExceptionofInput, ExceptionofUnproperEdge, ExceptionofDirection, ExceptionofUndirection {
	NetworkTopology g3 = new NetworkTopology();
	NetworkTopologyFactory f3 = new NetworkTopologyFactory();
          f3.build("test/source/testinput9.txt", g3);  
    }
    //  错误指令类型 = 边
    //  图类型 = NetworkTopology
    //  语法规范 = 不符合 
    @Test(expected = ExceptionofInput.class)
    public void testinput10() throws ExceptionofInput, ExceptionofUnproperEdge, ExceptionofDirection, ExceptionofUndirection {
	NetworkTopology g3 = new NetworkTopology();
	NetworkTopologyFactory f3 = new NetworkTopologyFactory();
          f3.build("test/source/testinput10.txt", g3);  
    }
    //  错误指令类型 = 边
    //  图类型 = NetworkTopology
    //  语法规范 = 不符合 
    @Test(expected = ExceptionofInput.class)
    public void testinput11() throws ExceptionofInput, ExceptionofUnproperEdge, ExceptionofDirection, ExceptionofUndirection {
	NetworkTopology g3 = new NetworkTopology();
	NetworkTopologyFactory f3 = new NetworkTopologyFactory();
          f3.build("test/source/testinput11.txt", g3);  
    }
    //  错误指令类型 = 边
    //  图类型 = NetworkTopology
    //  语法规范 = 不符合 
    @Test(expected = ExceptionofInput.class)
    public void testinput12() throws ExceptionofInput, ExceptionofUnproperEdge, ExceptionofDirection, ExceptionofUndirection {
	NetworkTopology g3 = new NetworkTopology();
	NetworkTopologyFactory f3 = new NetworkTopologyFactory();
          f3.build("test/source/testinput12.txt", g3);  
    }
    //  错误指令类型 = 无
    //  图类型 = MovieGraph
    //  语法规范 = 符合
    @Test
    public void testinputright4() throws ExceptionofInput, ExceptionofUnproperEdge, ExceptionofDirection, ExceptionofUndirection {
	MovieGraph g4 = new MovieGraph();
	MovieGraphFactory f4 = new MovieGraphFactory();
        f4.build("test/source/test4.txt", g4);  
    }
    
    //  错误指令类型 = 点
    //  图类型 = MovieGraph
    //  语法规范 = 不符合 
    @Test(expected = ExceptionofInput.class)
    public void testinput13() throws ExceptionofInput, ExceptionofUnproperEdge, ExceptionofDirection, ExceptionofUndirection {
	MovieGraph g4 = new MovieGraph();
	MovieGraphFactory f4 = new MovieGraphFactory();
        f4.build("test/source/testinput13.txt", g4);  
    }
    //  错误指令类型 = 边
    //  图类型 = MovieGraph
    //  语法规范 = 不符合 
    @Test(expected = ExceptionofInput.class)
    public void testinput14() throws ExceptionofInput, ExceptionofUnproperEdge, ExceptionofDirection, ExceptionofUndirection {
	MovieGraph g4 = new MovieGraph();
	MovieGraphFactory f4 = new MovieGraphFactory();
        f4.build("test/source/testinput14.txt", g4);  
    }
    //  错误指令类型 = 边
    //  图类型 = MovieGraph
    //  语法规范 = 不符合 
    @Test(expected = ExceptionofInput.class)
    public void testinput15() throws ExceptionofInput, ExceptionofUnproperEdge, ExceptionofDirection, ExceptionofUndirection {
	MovieGraph g4 = new MovieGraph();
	MovieGraphFactory f4 = new MovieGraphFactory();
        f4.build("test/source/testinput15.txt", g4);  
    }
    //  错误指令类型 = 边
    //  图类型 = MovieGraph
    //  语法规范 = 不符合 
    @Test(expected = ExceptionofInput.class)
    public void testinput16() throws ExceptionofInput, ExceptionofUnproperEdge, ExceptionofDirection, ExceptionofUndirection {
	MovieGraph g4 = new MovieGraph();
	MovieGraphFactory f4 = new MovieGraphFactory();
        f4.build("test/source/testinput16.txt", g4);  
    }
    
    
}
