package Edgetest;

import static org.junit.Assert.*;

import org.junit.Test;

import edge.NetworkConnection;
import vertex.Computer;
import vertex.Router;
import vertex.Server;
import vertex.Word;

public class Networkconnectiontest {
	
	Computer c1 = new Computer("c1");
	Computer c2 = new Computer("c2");
	Computer c3 = new Computer("c1");
	Server s1 = new Server("s1");
	Server s2 = new Server("s2");
	Router r1 = new Router("r1");
	Router r2 = new Router("r2");
	NetworkConnection net1 = new NetworkConnection("net1",c1,s1,10.0);
	NetworkConnection net2 = new NetworkConnection("net2",s1,c1,10.0);
	NetworkConnection net3 = new NetworkConnection("net3",r1,s1,10.0);
	NetworkConnection net4 = new NetworkConnection("net4",c2,r1,10.0);
	NetworkConnection net5 = new NetworkConnection("net5",c2,r1,10.0);
	//	Testing strategy 
    //
    // equasl():
    //	source and target = same, not same 
	//
	
	//source and target = same
	@Test
	public void equals() {
		assertTrue("expected True", net1.equals(net2));
		assertTrue("expected True", net4.equals(net5));
	}
	//source and target = not same
	@Test
	public void equals1() {
		assertFalse("expected True", net1.equals(net3));
		assertFalse("expected True", net1.equals(net4));
	}
	// Testing strategy 
    //
    // checkRep():
    //	weight = right,wrong
	//	type of vertex = right,wrong
	//  size of vertice = right,wrong
	//  source and target = same, not same
	
	//type of vertex = wrong
	@Test(expected = AssertionError.class)
	public void testedgesame1() {
		NetworkConnection n1 = new NetworkConnection("n1",c1,c2,10.0);
	}
	//type of vertex = wrong
		@Test(expected = AssertionError.class)
		public void testedgesame2() {
			Word w1 = new Word("w21");
			NetworkConnection n1 = new NetworkConnection("n1",w1,c2,10.0);
		}
	//weight = wrong
	@Test(expected = AssertionError.class)
	public void testedgenotweight() {
		NetworkConnection n1 = new NetworkConnection("n1",c1,s1,-10.0);
	}
	//type of vertex = wrong
	@Test(expected = AssertionError.class)
	public void testedgesamevertex() {
		NetworkConnection n1 = new NetworkConnection("n1",s2,s1,10.0);
	}
	//  source and target = same
	@Test(expected = AssertionError.class)
	public void testedgesamevertex1() {
		NetworkConnection n1 = new NetworkConnection("n1",s1,s1,10.0);
	}
	//
	
}
