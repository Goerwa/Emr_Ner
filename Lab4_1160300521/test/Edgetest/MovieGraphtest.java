package Edgetest;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import edge.MovieActorRelation;
import edge.MovieDirectorRelation;
import edge.SameMovieHyperEdge;
import vertex.Actor;
import vertex.Director;
import vertex.Movie;
import vertex.Vertex;

public class MovieGraphtest { 
	
	Actor a1 = new Actor("a1");
	Actor a2 = new Actor("a2");
	Actor a3 = new Actor("a1");
	Director d1 = new Director("d1");
	Director d2 = new Director("d2");
	Movie m1 = new Movie("m1");
	Movie m2 = new Movie("m2");
	MovieActorRelation ma1 = new MovieActorRelation("ma1",a1,m1,1.0);
	MovieActorRelation ma2 = new MovieActorRelation("ma2",m1,a1,1.0);
	MovieActorRelation ma3 = new MovieActorRelation("ma3",m1,a2,1.0);
	MovieDirectorRelation md1 = new MovieDirectorRelation("md1",d1,m1,-1.0);
	MovieDirectorRelation md2 = new MovieDirectorRelation("md2",m1,d1,-1.0);
	MovieDirectorRelation md3 = new MovieDirectorRelation("md3",d2,m1,-1.0);
	//	Testing strategy 
    //
    // equasl():
    //	source and target = same, not same 
	//	type of vertex = same, not same
	
	//source and target = same
    //type of vertex = same
	@Test
	public void testequals() {
		assertTrue("expected True", md1.equals(md1));
		assertTrue("expected True", md2.equals(md1));
		assertTrue("expected True", ma1.equals(ma2));
	}
	
	//source and target = not same
    //type of vertex = not same
	@Test
	public void testnotequals() {
		assertFalse("expected False", md1.equals(ma1));
		assertFalse("expected False", md2.equals(ma1));
		assertFalse("expected False", md1.equals(ma3));
		assertFalse("expected False", ma1.equals(ma3));

	}
	
	// Testing strategy 
    //
    // checkRep():
    //	weight = right,wrong
	//	type of vertex = right,wrong
	//  size of vertice = right,wrong
	//  source and target = same, not same
	
	@Test(expected = AssertionError.class)
	public void testhyper1() {
		List<Vertex> vers = new ArrayList<>();
		vers.add(a1);
		vers.add(d2);
		SameMovieHyperEdge hyper = new SameMovieHyperEdge("hyper", 10);
		hyper.addVertices(vers);
		
	}
	
	@Test(expected = AssertionError.class)
	public void testhyper2() {
		List<Vertex> vers = new ArrayList<>();
		vers.add(a1);
		SameMovieHyperEdge hyper = new SameMovieHyperEdge("hyper", 10);
		hyper.addVertices(vers);
		
	}
	@Test
	public void testmaedge1() {
		MovieActorRelation ma1 = new MovieActorRelation("ma1",a1,m1,1.0);
	}
	
	@Test(expected = AssertionError.class)
	public void testmaedge2() {
		MovieActorRelation ma1 = new MovieActorRelation("ma1",a1,d1,1.0);
	}
	
	@Test(expected = AssertionError.class)
	public void testmaedge3() {
		MovieActorRelation ma1 = new MovieActorRelation("ma1",a1,a2,1.0);
	}
	
	@Test(expected = AssertionError.class)
	public void testmaedge4() {
		MovieActorRelation ma1 = new MovieActorRelation("ma1",a1,a1,1.0);
	}
	
	@Test
	public void testmdedge1() {
		MovieDirectorRelation ma1 = new MovieDirectorRelation("ma1",d1,m1,-1.0);
	}
	
	@Test(expected = AssertionError.class)
	public void testmdedge2() {
		MovieDirectorRelation ma1 = new MovieDirectorRelation("ma1",a1,m1,1.0);
		System.out.println(ma1.getsource().tellclass());
		System.out.println(ma1.gettarget().tellclass());
	}
	
	@Test(expected = AssertionError.class)
	public void testmdedge3() {
		MovieDirectorRelation ma1 = new MovieDirectorRelation("ma1",m2,m1,1.0);
	}
	
	@Test(expected = AssertionError.class)
	public void testmdedge4() {
		MovieDirectorRelation ma1 = new MovieDirectorRelation("ma1",d1,d1,1.0);
	}
}
