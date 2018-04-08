package P3.plan;

import java.util.ArrayList;

import P3.graph.Itinerary;
import P3.graph.Stop;

/**
 * The interface to implement the method to find a list of stop by the
 * searchString, and compute the best path with shortest time
 */
public interface RoutePlanner {

	/**
	 * find a list of stops whose name contains the provided search
	 * 
	 * @param searchString
	 *            the provided substring
	 * @return ArrayList<Stop> a list of all stops whose name contains the
	 *         provided search
	 */
	ArrayList<Stop> findStopsBySubstring(String searchString);

	/**
	 * find the best path from the src stop to the dest stop at the departTime
	 * 
	 * @param src
	 *            the start stop
	 * @param dest
	 *            the destination stop
	 * @param departTime
	 *            the input of the depart time
	 * @return itinerary with the shortest time
	 */
	Itinerary computeRoute(Stop src, Stop dest, int departTime);

}