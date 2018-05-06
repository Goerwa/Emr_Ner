package Edgetest;

import static org.junit.Assert.*;

import org.junit.Test;

import edge.MovieActorRelation;
import edge.MovieDirectorRelation;
import vertex.Actor;
import vertex.Director;
import vertex.Movie;

public class MovieGraphtest {
	
	Actor a1 = new Actor("a1");
	Actor a2 = new Actor("a2");
	Actor a3 = new Actor("a1");
	Director d1 = new Director("d1");
	Movie m1 = new Movie("m1");
	Movie m2 = new Movie("m2");
	
	@Test
	public void testmaedge1() {
		MovieActorRelation ma1 = new MovieActorRelation(a1,m1,1.0);
	}
	
	@Test(expected = AssertionError.class)
	public void testmaedge2() {
		MovieActorRelation ma1 = new MovieActorRelation(a1,d1,1.0);
	}
	
	@Test(expected = AssertionError.class)
	public void testmaedge3() {
		MovieActorRelation ma1 = new MovieActorRelation(a1,a2,1.0);
	}
	
	@Test(expected = AssertionError.class)
	public void testmaedge4() {
		MovieActorRelation ma1 = new MovieActorRelation(a1,a1,1.0);
	}
	
	@Test
	public void testmdedge1() {
		MovieDirectorRelation ma1 = new MovieDirectorRelation(d1,m1,1.0);
	}
	
	@Test(expected = AssertionError.class)
	public void testmdedge2() {
		MovieDirectorRelation ma1 = new MovieDirectorRelation(a1,m1,1.0);
	}
	
	@Test(expected = AssertionError.class)
	public void testmdedge3() {
		MovieDirectorRelation ma1 = new MovieDirectorRelation(m2,m1,1.0);
	}
	
	@Test(expected = AssertionError.class)
	public void testmdedge4() {
		MovieDirectorRelation ma1 = new MovieDirectorRelation(d1,d1,1.0);
	}
}
