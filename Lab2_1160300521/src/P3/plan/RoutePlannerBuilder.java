package P3.plan;



public interface RoutePlannerBuilder {

	/**
	 * build the RoutePlanner, including parse the transit data file and
	 * construct data structures
	 * 
	 * @param fileName
	 *            the file storing the transit data
	 * @param maxWaitLimit
	 *            the max time for wait
	 * @return RoutePlanner
	 */
	RoutePlanner build(String fileName, int maxWaitLimit);

}


