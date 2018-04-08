package P3.graph;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import P3.graph.BusSegment;
import P3.graph.Itinerary;
import P3.graph.Stop;
import P3.graph.StopImpl;
import P3.graph.TripSegment;
import P3.graph.Vertex;
import P3.graph.WaitSegment;

public class ItineraryTest {

	private Vertex s1, s2, s3, s4;
	private String name1, name2;
	private TripSegment seg1, seg2, seg3;
	private Stop stop1, stop2, stop3;
	private int t1, t2, t3, t4;
	private Itinerary iti, iti3;

	/**
	 * Called before each test.
	 */
	@Before
	public void setUp() {
		stop1 = new StopImpl("stop1", 100.0, 200.0);
		stop2 = new StopImpl("stop2", 50.0, 150.0);
		stop3 = new StopImpl("stop3", 10.0, 150.0);
		t1 = 1000;
		t2 = 2000;
		t3 = 3000;
		t4 = 4000;
		s1 = new Vertex(stop1, t1);
		s2 = new Vertex(stop2, t2);
		s3 = new Vertex(stop2, t3);
		s4 = new Vertex(stop3, t4);
		name1 = "61D";
		name2 = "61C";
		seg1 = new BusSegment(s1, s2, name1);
		seg2 = new WaitSegment(s2, s3);
		seg3 = new BusSegment(s3, s4, name2);

		ArrayList<TripSegment> path = new ArrayList<TripSegment>();
		ArrayList<TripSegment> path2 = new ArrayList<TripSegment>();
		path.add(seg1);
		path.add(seg2);
		path.add(seg3);
		path2.add(seg2);
		iti = new Itinerary(path);
		iti3 = new Itinerary(path2);
	}

	@Test
	// I cannot eliminate the warning because I need to achieve 100% coverage
	// test.
	public void testNoPath() {
		Itinerary iti2 = new Itinerary(null);
	}

	@Test
	public void getStartTimeTest() {
		assertEquals(iti.getStartTime(), t1);
		assertEquals(iti3.getStartTime(), 0);
	}

	@Test
	public void getStartLocationTest() {
		assertEquals(iti.getStartLocation(), stop1);
	}

	@Test
	public void getEndLocationTest() {
		assertEquals(iti.getEndLocation(), stop3);
	}

	@Test
	public void getEndTimeTest() {
		assertEquals(iti.getEndTime(), t4);
	}

	@Test
	public void getWaitTimeTest() {
		assertEquals(iti.getWaitTime(), 1000);
	}

	@Test
	public void getInstructionsTest() {
		String str = "Best path: stop1>>>1000, stop2>>>wait,1000>>>1000, stop3>>>stop3";
		System.out.println(iti.getInstructions());
		assertEquals(iti.getInstructions(), str);
	}

}
