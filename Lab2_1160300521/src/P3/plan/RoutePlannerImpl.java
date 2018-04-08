package P3.plan;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

import P1.graph.Graph;
import P3.graph.BusSegment;
import P3.graph.Itinerary;
import P3.graph.Stop;
import P3.graph.TripSegment;
import P3.graph.Vertex;
import P3.graph.WaitSegment;
import P3.plan.RoutePlanner;

public class RoutePlannerImpl implements RoutePlanner {

	private Graph<Vertex> graph;
	private HashMap<String, Stop> stopList = new HashMap<String, Stop>();
	private int maxWaitLimit;

	/**
	 * the constructor for the routePlannerImpl class
	 * 
	 * @param g
	 *            the input graph class
	 * @param sL
	 *            the input hashMap to store the stopList
	 * @param maxTime
	 *            the max wait time
	 */
	public RoutePlannerImpl(Graph<Vertex> g, HashMap<String, Stop> sL, int maxTime) {
		this.graph = g;
		this.stopList = sL;
		this.maxWaitLimit = maxTime;
	}

	@Override
	public ArrayList<Stop> findStopsBySubstring(String searchString) {
		ArrayList<Stop> stops = new ArrayList<Stop>();
		for (String stopName : stopList.keySet()) {
			if (stopName.contains(searchString)) {
				stops.add(stopList.get(stopName));
			}
		}
		return stops;
	}
	
	private ArrayList<Vertex> getVertexListWithSameStop(Stop inputStop) {
		ArrayList<Vertex> all = new ArrayList<Vertex>();
		Set<Vertex> ver = new HashSet<>();
		ver = graph.vertices();
		for(Vertex v:ver) {
			if(v != null) {
				if(v.getStop().getName().equals(inputStop.getName())) {
					all.add(v);
				}
			}
		}
		return all;
	}
	
	
	@Override
	public Itinerary computeRoute(Stop src, Stop dest, int departTime) {
		ArrayList<TripSegment> bestPathList = new ArrayList<TripSegment>();

		ArrayList<Vertex> possibleSrcVertex = new ArrayList<Vertex>();
		for (Vertex eachVertex : getVertexListWithSameStop(src)) {
			if (eachVertex.getTime() - departTime >= 0
					&& eachVertex.getTime() - departTime <= this.maxWaitLimit) {
				possibleSrcVertex.add(eachVertex);
			}
		}
		//System.out.println(possibleSrcVertex.size());
		if (possibleSrcVertex.size() == 0){
			return null;
		}
		int maxTime = Integer.MAX_VALUE;
		Vertex srcVertex = null;
		for (Vertex eachVertex : possibleSrcVertex) {
			if (eachVertex.getTime() < maxTime) {
				srcVertex = eachVertex;
				maxTime = eachVertex.getTime();
			}
		}
		if (srcVertex.getTime() > departTime) {
			bestPathList.add(new WaitSegment(new Vertex(src, departTime),
					srcVertex));
		}
		//System.out.println(bestPathList.size());
		bestPathList.addAll(dijkstarSearch(srcVertex, dest));
		//System.out.println(bestPathList.size());
		return new Itinerary(bestPathList);
	}
	
	
	private ArrayList<TripSegment> getNeighborTripSegmentList(Vertex inputVertex) {
		Map<Vertex, Integer> all = new HashMap<>();
		ArrayList<TripSegment> r = new ArrayList<>();
		ArrayList<Vertex> allstop = new ArrayList<Vertex>();
		allstop = getVertexListWithSameStop(inputVertex.getStop());
		//System.out.println(allstop.size());
		for(Vertex v:allstop) {
			if(v != null) {
				//System.out.println(v.getTime());
				//System.out.println(inputVertex.getTime());
				if(v.getTime() > inputVertex.getTime()) {
					r.add(new WaitSegment(inputVertex,v));
					//System.out.println("1");
				}
				all = graph.targets(v);
				for(Vertex v1:all.keySet()) {
					if(v1 != null && inputVertex.getTime() < v1.getTime())
						r.add(new BusSegment(inputVertex,v1,"bus"));
				}
			}
		}
		return r;
	}
	/**
	 * the method to execute the dijstra's algorithm
	 * 
	 * @param src
	 *            the input start stop
	 * @param dest
	 *            the input destination stop
	 * @return the arrayList of the tripSegment
	 */
	private ArrayList<TripSegment> dijkstarSearch(Vertex src, Stop dest) {
		ArrayList<TripSegment> pathResult = new ArrayList<TripSegment>();
		PriorityQueue<Vertex> sortedVertices = new PriorityQueue<Vertex>(
				this.graph.vertices().size(), new SortQueueViaPriority());
		HashMap<Vertex, Vertex> vertexPrevVertexPair = new HashMap<Vertex, Vertex>();

		HashMap<Vertex, TripSegment> vertexTripSegmentPair = new HashMap<Vertex, TripSegment>();
		for (Vertex v : this.graph.vertices()) {
			if(v != null) {
				vertexPrevVertexPair.put(v, null);
				v.setIsVisitedStatus(false);
				sortedVertices.add(v);
				if (src.equals(v)) {
					v.setDistance(0);
				} else {
					v.setDistance(Integer.MAX_VALUE);
				}
			}
		}
		//sortedVertices.addAll(this.graph.vertices());

		Vertex destVertex = null;

		while (!sortedVertices.isEmpty()) { 
			Vertex u = sortedVertices.poll();
			if (u.getStop().getName().equals(dest.getName())) {
				destVertex = u;
				break;
			} 
			int distU = u.getDistance();
			u.setIsVisitedStatus(true);
			
			//System.out.println(getNeighborTripSegmentList(u).size());
			for (TripSegment eachNeighborTripSegment : getNeighborTripSegmentList(u)) {
				if (!eachNeighborTripSegment.getEndVertex().getStatus()) {
					int alt = distU + eachNeighborTripSegment.getWaitTime();
					if (alt < eachNeighborTripSegment.getEndVertex().getDistance()) {
						eachNeighborTripSegment.getEndVertex().setDistance(alt);
						vertexTripSegmentPair.put(eachNeighborTripSegment.getEndVertex(),
								eachNeighborTripSegment);
						vertexPrevVertexPair.put(eachNeighborTripSegment.getEndVertex(), u);
					}
				}
			}
			sortedVertices.clear();
			for (Vertex v : this.graph.vertices()) {
				if(v != null)
				if (!v.getStatus()) {
					sortedVertices.add(v);
				}
			}
			//System.out.println(sortedVertices.size());
		}

		// get the best path
		while (vertexPrevVertexPair.get(destVertex) != null) {
			pathResult.add(vertexTripSegmentPair.get(destVertex));
			destVertex = vertexPrevVertexPair.get(destVertex);
		}
		Collections.reverse(pathResult);
		return pathResult;
	}

	/**
	 * the comparator to sort the priorityqueue
	 *
	 */
	public class SortQueueViaPriority implements Comparator<Vertex> {

		@Override
		public int compare(Vertex o1, Vertex o2) {
			if (o1.getDistance() < o2.getDistance()) {
				return -1;
			} else {
				return 1;
			}
		}
	}
}
