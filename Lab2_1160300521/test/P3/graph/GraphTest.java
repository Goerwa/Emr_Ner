package P3.graph;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import P1.graph.*;
import P3.graph.BusSegment;
import P3.graph.Stop;
import P3.graph.StopImpl;
import P3.graph.TripSegment;
import P3.graph.Vertex;

public class GraphTest {

	private Graph<Vertex> g;
	private Stop s1 = new StopImpl("5th&wood", 40, -80);
	private Vertex v1 = new Vertex(s1, 25620);
	private Stop s2 = new StopImpl("5th&smith", 50, -10);
	private Vertex v2 = new Vertex(s2, 25680);
	private Stop s3 = new StopImpl("5th&ross", 400, -180);
	private Stop s4 = new StopImpl("5th&forbes", 100, -50);
	private Vertex v3 = new Vertex(s3, 25800);
	private Vertex v4 = new Vertex(s1, 26620);
	private Vertex v5 = new Vertex(s2, 26680);
	private Vertex v6 = new Vertex(s3, 26800);
	private Vertex v7 = new Vertex(s1, 26000);
	private Vertex v8 = new Vertex(s4, 27120);
	private Vertex v9 = new Vertex(s1, 28620);
	private Vertex v10 = new Vertex(s4, 28700);
	
	private ArrayList<Vertex> getVertexListWithSameStop(Stop inputStop) {
		
		ArrayList<Vertex> all = new ArrayList<Vertex>();
		Set<Vertex> ver = new HashSet<>();
		ver = g.vertices();
		//System.out.println(ver.size());
		//System.out.println(ver.toString());
		for(Vertex v:ver) {
			if(v != null)
			if(v.getStop().getName().equals(inputStop.getName())) {
				all.add(v);
			}
		}
		return all;
	}
	
	private ArrayList<TripSegment> getNeighborTripSegmentList(Vertex inputVertex) {
		Map<Vertex, Integer> all = new HashMap<>();
		all = g.targets(inputVertex);
		ArrayList<TripSegment> r = new ArrayList<>();
		//int val = all.get(inputVertex);
		for(Vertex v:all.keySet()) {
			r.add(new BusSegment(inputVertex,v,"61C"));
		}
		
		return r;
	}
	/**
	 * Called before each test.
	 */
	@Before
	public void setUp() {
		g = Graph.empty();
		//System.out.println(g.vertices().toString());
		g.add(v1);
		g.add(v2);
		g.set(v1, v2, 1200);
		g.add(v3);
		g.set(v2, v3, 1200);
		g.set(v3, null, 1200);
		g.add(v4);
		g.add(v5);
		g.set(v4, v5, 1200);
		g.add(v6);
		g.set(v5, v6, 1200);
		g.set(v6, null, 1200);
		g.add(v7);
		g.add(v8);
		g.set(v7, v8, 1200);
		g.set(v8, null, 1200);
		g.add(v9);
		g.add(v10);
		g.set(v9, v10, 1200);
		g.set(v10, null, 1200);
	}
	
	
	@Test
	public void getNeighborTripSegmentListTest() {
		TripSegment t1 = new BusSegment(v2, v3, "61C");
		System.out.println(getNeighborTripSegmentList(v2).get(0).getInstructions());
		System.out.println(t1.getInstructions());
		assertEquals(getNeighborTripSegmentList(v2).get(0).getInstructions(),
				t1.getInstructions());
		assertEquals(getNeighborTripSegmentList(v2).get(0).getWaitTime(),
				t1.getWaitTime());
	}
	
	
	@Test
	public void getVerticesListTest() {
		Set<Vertex> vertices = new HashSet<Vertex>();
		vertices.add(v1);
		vertices.add(v2);
		vertices.add(v3);
		vertices.add(v4);
		vertices.add(v5);
		vertices.add(v6);
		vertices.add(v7);
		vertices.add(v8);
		vertices.add(v9);
		vertices.add(v10);
		vertices.add(null);
		//System.out.println(g.vertices().toString());
		//System.out.println(vertices.toString());
		System.out.println(vertices.size());
		assertEquals(g.vertices(), vertices);
	}
	
	@Test
	public void getVertexListWithSameStopTest() {
		ArrayList<Vertex> vertices = new ArrayList<Vertex>();
		vertices.add(v9);
		vertices.add(v4);
		vertices.add(v7);
		vertices.add(v1);
		ArrayList<Vertex> r = getVertexListWithSameStop(s1);
		System.out.println(r.toString());
		System.out.println(vertices.toString());
		assertEquals(getVertexListWithSameStop(s1), vertices);
	}

}
