package P3.graph;

/**
 * the tripSegment(edge) of the garph
 * 
 */
public interface TripSegment {

	/**
	 * get the instruction of the tripSegment
	 * 
	 * @return String the busName or wait
	 */
	String getInstructions();

	/**
	 * get the waitTime of the tripSegment
	 * 
	 * @return the waitTime
	 */
	int getWaitTime();

	/**
	 * get the end Vertex of the tripSegment
	 * 
	 * @return the end Vertex
	 */
	Vertex getEndVertex();

	/**
	 * get the start stop of the tripSegment
	 * 
	 * @return the start stop
	 */
	Stop getStartStop();

	/**
	 * get the end stop of the tripSegment
	 * 
	 * @return the end stop
	 */
	Stop getEndStop();

	/**
	 * get the start time of the tripSegment
	 * 
	 * @return the start time
	 */
	int getStartTime();

	/**
	 * get the end time of the tripSegment
	 * 
	 * @return the end time
	 */
	int getEndTime();

}
