package Vertextest;

import static org.junit.Assert.*;

import org.junit.Test;

import vertex.Person;
import vertex.Word;

public class GraphPoetvertextest {
	
	Word w1 = new Word("w1");
	Word w3 = new Word("w1");
	Word w2 = new Word("w2");
	Person p1 = new Person("w1");
	Person p2 = new Person("w111");
	// Testing strategy 
    //
    //  equasl():
    //	label of vertex = same, not same 
	//	type of vertex = same, not same 
	
	//	label of vertex = same
	@Test
	public void testequals1() {
		assertTrue("expected true",w1.equals(w3));
	}
	//	label of vertex = not same 
	@Test
	public void testequals2() {
		assertFalse("expected false",w1.equals(w2));
	}

	//	type of vertex = not same 
	//	label of vertex = not same
	@Test
	public void testequals3() {
		assertFalse("expected false",w1.equals(p1));
	}
	//	type of vertex = same, not same 
	//	label of vertex = same
	@Test
	public void testequals4() {
		assertFalse("expected false",w1.equals(p2));
	}
	
	// Testing strategy 
    //
    // checkrep():
	// test label = null,not null;
	
	// label = null
    @Test(expected = AssertionError.class)
    public void testcheckrep1() {
        Word w11 = new Word(null);
        System.out.println(w11.getlabel());
        assertFalse("expected false",w1.equals(p2));
    }
	
    // label = null
    @Test(expected = AssertionError.class)
    public void testcheckrep2() {
        Word w11 = new Word("w11");
        w11.setlabel(null);
        System.out.println(w11.getlabel());
        assertFalse("expected false",w1.equals(p2));
    }
    // label = null
    @Test(expected = AssertionError.class)
    public void testcheckrep3() {
        Word w11 = new Word("w11");
        w11.fillVertexInfo(null);
        System.out.println(w11.getlabel());
        assertFalse("expected false",w1.equals(p2));
    }
}
