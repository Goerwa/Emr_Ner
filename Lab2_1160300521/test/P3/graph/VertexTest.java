package P3.graph;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import P3.graph.Stop;
import P3.graph.StopImpl;
import P3.graph.Vertex;

public class VertexTest {
	private Vertex vert;
	private Stop s;

	/**
	 * Called before each test.
	 */
	@Before
	public void setUp() {
		s = new StopImpl("someStop", 100.0, 200.0);
		vert = new Vertex(s, 2000);
		vert.setDistance(0);
		vert.setIsVisitedStatus(true);
	}

	@Test
	public void testGetStop() {
		assertEquals(vert.getStop(), s);
	}

	@Test
	public void testGetTime() {
		assertEquals(vert.getTime(), 2000);
	}

	@Test
	public void testGetDistance() {
		assertEquals(vert.getDistance(), 0);
	}

	@Test
	public void testGetStatus() {
		assertEquals(vert.getStatus(), true);
	}
}

