package P3.plan;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import P3.graph.Itinerary;
import P3.graph.Stop;
import P3.graph.StopImpl;
import P3.plan.RoutePlanner;
import P3.plan.RoutePlannerBuilder;
import P3.plan.RoutePlannerBuilderImpl;

public class RoutePlannerImplTest {
	private RoutePlanner planner, planner2;
	private RoutePlannerBuilder builder, builder2;
	private static final String TRANSIT_FILE = "src/P3/sources/test1.txt";
	private static final String SECOND_TRANSIT_FILE = "src/P3/sources/test2.txt";
	private static final double DELTA = 1e-15;

	@Before
	public void setUp() {
		builder = new RoutePlannerBuilderImpl();
		builder2 = new RoutePlannerBuilderImpl();
		planner = builder.build(TRANSIT_FILE, 1200);
		planner2 = builder2.build(SECOND_TRANSIT_FILE, 1200);
	}

	@Test
	public void findStopsBySubstringTest() {
		assertEquals(planner.findStopsBySubstring("stop").size(), 3);
		assertEquals(planner.findStopsBySubstring("stop1").size(), 1);
		assertEquals(planner.findStopsBySubstring("stop1").get(0).getName(),
				"stop1");
		assertEquals(planner.findStopsBySubstring("stop1").get(0).getLatitude(),
				40, DELTA);
		assertEquals(planner.findStopsBySubstring("stop1").get(0).getLongitude(),
				80, DELTA);
	}

	@Test
	public void computeRouteTest() {
		/*
		Stop src = new StopImpl("stop1", 40, 80);
		Stop dest = new StopImpl("stop2", 100, 20);
		Itinerary iti1;
		Stop dest2 = new StopImpl("stop3", 50, 20);
		iti1 = planner.computeRoute(src, dest2, 25620);
		//System.out.println(iti1.getInstructions());
		String str2 = "Best path: stop1>>>60, stop2>>>120, stop3>>>stop3";
		assertEquals(iti1.getInstructions(), str2);
		
		Itinerary iti2;
		iti2 = planner.computeRoute(src, dest, 25500);
		//System.out.println(iti2.getInstructions());
		String str3 = "Best path: stop1>>>wait,120>>>60, stop2>>>stop2"; 
		assertEquals(iti2.getInstructions(), str3);

		// coverage test. 
		Itinerary iti3;
		iti3 = planner.computeRoute(dest, src, 27800);  
		Itinerary iti4;
		iti4 = planner.computeRoute(src, dest, 23620);
		*/
		Stop secondSrc = new StopImpl("stop1", 10,20); 
		Stop secondSrc2 = new StopImpl("stop3", 30,40); 
		Stop secondDest = new StopImpl("stop7", 70, 80);
		Itinerary iti5 = planner2.computeRoute(secondSrc, secondDest, 26600);
		System.out.println(iti5.getInstructions());
		String str4 = "Best path: stop1>>>100, stop2>>>100, stop3>>>100, stop4>>>150, stop6>>>100, stop7>>>stop7";
		assertEquals(iti5.getInstructions(), str4);
	}

}
