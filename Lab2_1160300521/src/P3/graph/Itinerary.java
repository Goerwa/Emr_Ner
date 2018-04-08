package P3.graph;

import java.util.ArrayList;

/**
 * the itinerary class to stand for the edge in the graph
 * 
 * 
 */
public class Itinerary {

	private ArrayList<TripSegment> tripSegPath = new ArrayList<TripSegment>();
	private String name;

	/**
	 * the constructor of the itinerary class
	 * 
	 * @param path
	 *            the input of the path for the class
	 */
	public Itinerary(ArrayList<TripSegment> path) {
		if (path == null) {
			System.out.println("There is no path from the source to the destination!");
		}
		this.tripSegPath = path;
		this.name = "Best path";
	}

	/**
	 * get the start time of the itinerary
	 * 
	 * @return the start time
	 */
	public int getStartTime() {
		int startTime = 0;
		for (TripSegment seg : tripSegPath) {
			if (!seg.getInstructions().equals("wait")) {
				startTime = seg.getStartTime();
				break;
			}
		}
		return startTime;
	}

	/**
	 * get the start stop of the itinerary
	 * 
	 * @return the start stop
	 */
	public Stop getStartLocation() {
		return tripSegPath.get(0).getStartStop();
	}

	/**
	 * get the end time of the itinerary
	 * 
	 * @return the end time
	 */
	public int getEndTime() {
		return tripSegPath.get(tripSegPath.size() - 1).getEndTime();
	}

	/**
	 * get the end stop of the itinerary
	 * 
	 * @return the end stop
	 */
	public Stop getEndLocation() {
		return tripSegPath.get(tripSegPath.size() - 1).getEndStop();
	}

	/**
	 * get the wait time of the itinerary
	 * 
	 * @return the wait time
	 */
	public int getWaitTime() {
		int totalWaitTime = 0;
		for (TripSegment seg : tripSegPath) {
			if (seg.getInstructions().equals("wait")) {
				totalWaitTime += seg.getWaitTime();
			}
		}
		return totalWaitTime;
	}

	/**
	 * get the instructions for the itinerary
	 * 
	 * @return the instructions string
	 */
	public String getInstructions() {
		String instr = name;
		instr += ": ";
		instr += this.getStartLocation().getName();
		instr += ">>>";
		for (TripSegment seg : tripSegPath) {
			if(seg.getInstructions() == "wait") {
				instr += seg.getInstructions();
				instr += ",";
				instr += seg.getWaitTime();
				instr += ">>>";
			}else {
				instr += seg.getWaitTime();
				instr += ", ";
				instr += seg.getEndStop().getName();
				instr += ">>>";
			}
		}
		instr += this.getEndLocation().getName();
		return instr;
	}

}
