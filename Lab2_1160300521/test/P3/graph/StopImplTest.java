package P3.graph;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import P3.graph.Stop;
import P3.graph.StopImpl;

public class StopImplTest {

	private Stop stop;
	private double lat;
	private double lon;
	private String str;
	private static final double DELTA = 1e-15;

	/**
	 * Called before each test.
	 */
	@Before
	public void setUp() {
		lat = 100.0;
		lon = 200.0;
		str = "5th&Forbes";
		stop = new StopImpl(str, lat, lon);
	}

	@Test
	public void getNameTest() {
		assertEquals(stop.getName(), str);
	}

	@Test
	public void getLatitudeTest() {
		assertEquals(stop.getLatitude(), lat, DELTA);
	}

	@Test
	public void getLongitudeTest() {
		assertEquals(stop.getLongitude(), lon, DELTA);
	}

}
