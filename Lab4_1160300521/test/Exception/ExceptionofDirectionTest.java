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

public class ExceptionofDirectionTest {
    // Testing strategy 
    //
    // 	在有向图中引入了无向边
    //  图类型 = 两种图
    //  边的方向是否都正确 = 是，否
    
    
    //  图类型 = SocialNetwork
    //  边的方向是否都正确 = 否 
    @Test(expected = ExceptionofDirection.class)
    public void testdirection1() throws ExceptionofInput, ExceptionofUnproperEdge, ExceptionofDirection, ExceptionofUndirection {
	SocialNetwork g2 = new SocialNetwork();
        SocialNetworkFactory f2 = new SocialNetworkFactory();
          f2.build("test/source/testdirection1.txt", g2);  
    }
    //  图类型 = SocialNetwork
    //  边的方向是否都正确 = 否 
    @Test(expected = ExceptionofDirection.class)
    public void testdirection2() throws ExceptionofInput, ExceptionofUnproperEdge, ExceptionofDirection, ExceptionofUndirection {
	SocialNetwork g2 = new SocialNetwork();
        SocialNetworkFactory f2 = new SocialNetworkFactory();
          f2.build("test/source/testdirection2.txt", g2);  
    }
    
    //  图类型 = GraphPoet
    //  边的方向是否都正确 = 否 
    @Test(expected = ExceptionofDirection.class)
    public void testdirection3() throws ExceptionofInput, ExceptionofUnproperEdge, ExceptionofDirection, ExceptionofUndirection {
	GraphPoet g2 = new GraphPoet();
	GraphPoetFactory f2 = new GraphPoetFactory();
          f2.build("test/source/testdirection3.txt", g2);  
    }
    

    
}
