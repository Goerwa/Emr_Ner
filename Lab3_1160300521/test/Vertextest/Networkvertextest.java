package Vertextest;

import static org.junit.Assert.*;

import org.junit.Test;

import vertex.Computer;
import vertex.Person;
import vertex.Router;
import vertex.Server;

public class Networkvertextest {
	
	
	Computer c1 = new Computer("c1");
	Computer c2 = new Computer("c1");
	Router r1 = new Router("r1");
	Router r2 = new Router("r2");
	Server s1 = new Server("s1");
	Server s2 = new Server("S1");
	// Testing strategy 
    //
    // checkRep():
    //	ip = right,wrong
	
	
	//	ip = wrong
	@Test(expected = AssertionError.class)
	public void testcheckrep1() {
		Computer p = new Computer("p","192.");
	}
	
	//ip = wrong
	@Test(expected = AssertionError.class)
	public void testcheckrep2() {
		Computer p = new Computer("p","192.192.192.2222");
	}
	
	//	ip = right
	public void testcheckrep3() {
		Computer p = new Computer("p","192.192.178.1");
	}

	// Testing strategy 
    //
    // equasl():
    //	label of vertex = same, not same 
	//	type of vertex = same, not same 
	@Test
	public void testequals1() {
		System.out.println(c1.tellclass());
		System.out.println(c2.tellclass());
		assertTrue("expected true", c1.equals(c2));
	}
	
	@Test
	public void testequals2() {
		System.out.println(r1.tellclass());
		assertFalse("expected false", c1.equals(r1));
	}
	
	@Test
	public void testequals3() {
		System.out.println(r1.tellclass());
		assertFalse("expected false", r2.equals(r1));
	}
	
	@Test
	public void testequals4() {
		System.out.println(s1.tellclass());
		System.out.println(s2.tellclass());
		assertTrue("expected true", s1.equals(s2));
	}

}
