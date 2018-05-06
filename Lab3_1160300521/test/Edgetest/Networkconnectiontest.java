package Edgetest;

import static org.junit.Assert.*;

import org.junit.Test;

import edge.NetworkConnection;
import vertex.Computer;
import vertex.Server;

public class Networkconnectiontest {
	
	Computer c1 = new Computer("c1");
	Computer c2 = new Computer("c2");
	Computer c3 = new Computer("c1");
	Server s1 = new Server("s1");
	Server s2 = new Server("s2");
	
	
	@Test(expected = AssertionError.class)
	public void testedgesame1() {
		NetworkConnection n1 = new NetworkConnection(c1,c2,10.0);
	}
	
	@Test(expected = AssertionError.class)
	public void testedgenotweight() {
		NetworkConnection n1 = new NetworkConnection(c1,c2,-10.0);
	}
	
	@Test(expected = AssertionError.class)
	public void testedgesamevertex() {
		NetworkConnection n1 = new NetworkConnection(c1,c3,10.0);
	}
	
	@Test(expected = AssertionError.class)
	public void testedgesame2() {
		NetworkConnection n1 = new NetworkConnection(s1,s2,10.0);
	}

}
