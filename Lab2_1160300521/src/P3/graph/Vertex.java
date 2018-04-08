package P3.graph;

import P3.graph.Stop;

public class Vertex {

	private Stop stop;
	private int time;
	private int distance;
	private boolean isVisited;

	/**
	 * Constructor of the vertex class
	 * 
	 * @param oneStop
	 *            the stop of the vertex
	 * @param oneTime
	 *            the even time of the vertex
	 */
	public Vertex(Stop oneStop, int oneTime) {
		this.stop = oneStop;
		this.time = oneTime;
	}

	/**
	 * get the stop of the vertex
	 * 
	 * @return stop
	 */
	public Stop getStop() {
		return this.stop;
	}

	/**
	 * get the time of the vertex
	 * 
	 * @return time
	 */
	public int getTime() {
		return this.time;
	}

	/**
	 * set the distance to the input value
	 * 
	 * @param dist
	 *            the input distance value
	 */
	public void setDistance(int dist) {
		this.distance = dist;
	}

	/**
	 * set the isVisited status to the input value
	 * 
	 * @param status
	 *            the input isVisited value
	 */
	public void setIsVisitedStatus(boolean status) {
		this.isVisited = status;
	}

	/**
	 * get the distance of the vertex class
	 * 
	 * @return the distance of the vertex
	 */
	public int getDistance() {
		return this.distance;
	}

	/**
	 * get the isVisited status of the vertex class
	 * 
	 * @return the isVisited status of the vertex
	 */
	public boolean getStatus() {
		return this.isVisited;
	}

}
