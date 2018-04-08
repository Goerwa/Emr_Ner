package P3.graph;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import P3.graph.Stop;
import P3.graph.StopImpl;
import P3.graph.TripSegment;
import P3.graph.Vertex;
import P3.graph.WaitSegment;

public class WaitSegmentTest {

	private Vertex s;
	private Vertex e;
	private TripSegment seg;
	private Stop s1;
	private Stop e1;
	private int t1;
	private int t2;

	/**
	 * Called before each test.
	 */
	@Before
	public void setUp() {
		s1 = new StopImpl("someStop", 100.0, 200.0);
		t1 = 1000;
		s = new Vertex(s1, t1);
		e1 = new StopImpl("anotherStop", 50.0, 150.0);
		t2 = 2000;
		e = new Vertex(e1, t2);
		seg = new WaitSegment(s, e);
	}

	@Test
	public void getInstructionsTest() {
		assertEquals(seg.getInstructions(), "wait");
	}

	@Test
	public void getWaitTime() {
		assertEquals(seg.getWaitTime(), 1000);
	}

	@Test
	public void getEndVertexTest() {
		assertEquals(seg.getEndVertex(), e);
	}

	@Test
	public void getStartStopTest() {
		assertEquals(seg.getStartStop(), s1);
	}

	@Test
	public void getEndStopTest() {
		assertEquals(seg.getEndStop(), e1);
	}

	@Test
	public void getStartTimeTest() {
		assertEquals(seg.getStartTime(), t1);
	}

	@Test
	public void getEndTimeTest() {
		assertEquals(seg.getEndTime(), t2);
	}

}
