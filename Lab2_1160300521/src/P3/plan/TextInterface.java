package P3.plan;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import P3.graph.Itinerary;
import P3.graph.Stop;
import P3.plan.RoutePlanner;
import P3.plan.RoutePlannerBuilderImpl;
import P3.plan.TextInterface;

public class TextInterface {
	private static final String TRANSIT_FILE = "src/P3/resources/mytest_stop_times.txt";
	private static final int MAX_WAIT = 1200; // don't make rider wait more than

	// 20 minutes

	/**
	 * Allows user to select start stop, end stop, and departure times, finding
	 * efficient paths in Pittsburgh's transit system.
	 * 
	 * @param args
	 *            Command-line arguments to the program (ignored).
	 * @throws IOException
	 *             catch the exceptions
	 */
	public static void main(String[] args) throws IOException {
		System.out.println("Building the route planner, please wait...");
		RoutePlanner planner = new RoutePlannerBuilderImpl().build(
				TRANSIT_FILE, MAX_WAIT);

		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

		new TextInterface().run(planner, in);

	}

	/**
	 * the private method to run the method
	 * 
	 * @param planner
	 *            the input planner
	 * @param in
	 *            the input buffer
	 * @throws IOException
	 *             catch the exceptions
	 */
	private void run(RoutePlanner planner, BufferedReader in)
			throws IOException {
		while (true) {
			Stop srcStop = findStop("Enter a substring for the source stop: ",
					planner, in);
			Stop destStop = findStop(
					"Enter a substring for the destination stop: ", planner, in);
			int time = getDepartureTime(
					"Enter your intended departure time in seconds since midnight: ",
					in);

			Itinerary itinerary = planner.computeRoute(srcStop, destStop, time);
			System.out.println(itinerary.getInstructions());
		}
	}

	/**
	 * Interacts with the user (using stdin/stdout) to select a stop
	 * 
	 * @param prompt
	 *            String to print to ask for user input.
	 * @param planner
	 *            RoutePlanner to use to find matching stops.
	 * @param in
	 *            BufferedReader to use to obtain user input.
	 * @return Stop selected by the user, not null
	 * @throws IOException
	 *             exceptions
	 */
	private Stop findStop(String prompt, RoutePlanner planner, BufferedReader in)
			throws IOException {
		while (true) {
			System.out.print(prompt);

			String searchString = in.readLine();
			if (searchString == null) {
				// terminating
				System.exit(0);
			}
			List<Stop> matchingStops = planner
					.findStopsBySubstring(searchString);
			if (matchingStops.isEmpty()) {
				System.out
						.println("Sorry, there are no stops matching your search string.  Try again.");
				continue;
			}
			if (matchingStops.size() == 1)
				return matchingStops.get(0);
			else
				return chooseFromStopList("Multiple stops found:",
						matchingStops, in);
		}
	}

	/**
	 * Given a list of stops, prints the list and reads a string as user input
	 * from stdin, returning the stop selected by the user.
	 * 
	 * @param prompt
	 *            String to print to ask for user input.
	 * @param stopList
	 *            List of stops from which user will select a stop.
	 * @param in
	 *            BufferedReader to use to obtain user input.
	 * @return The stop the user selected from the list.
	 * @throws IOException
	 *             catch the exceptions
	 */
	private Stop chooseFromStopList(String prompt, List<Stop> stopList,
			BufferedReader in) throws IOException {
		int choice = -1;
		String choiceString = null;
		while (true) {
			System.out.println(prompt);
			for (int i = 0; i < stopList.size(); ++i) {
				System.out.println(String.valueOf(i + 1) + ".\t"
						+ stopList.get(i).getName());
			}
			System.out.print("Select a stop: ");
			try {
				choiceString = in.readLine();
				if (choiceString == null) {
					System.exit(0);
				}
				choice = Integer.parseInt(choiceString);
			} catch (NumberFormatException e) {
				// OK to ignore because choice will be out of range
				System.out.println("The choice is out of range");
			}
			if (choice >= 1 && choice <= stopList.size()) {
				return stopList.get(choice - 1);
			}
			System.out.println(choiceString
					+ " is not a valid choice.  Please try again.");
		}
	}

	/**
	 * Reads a departure time (in seconds since midnight) entered by the user.
	 * 
	 * @param prompt
	 *            String to print to ask for user input.
	 * @param in
	 *            BufferedReader to use to obtain user input.
	 * @return The time (in seconds since midnight) entered by the user.
	 * @throws IOException
	 *             catch the exceptions
	 */
	private int getDepartureTime(String prompt, BufferedReader in)
			throws IOException {
		int time = -1;
		String choiceString = null;
		while (true) {
			System.out.print(prompt);
			try {
				choiceString = in.readLine();
				if (choiceString == null) {
					System.exit(0);
				}
				time = Integer.parseInt(choiceString);
			} catch (NumberFormatException e) {
				// OK to ignore because time will be out of range
				System.out.println("Time is out of range");
			}
			if (time >= 0) {
				return time;
			}
			System.out.println(choiceString
					+ " is not a valid choice.  Please try again.");
		}
	}
}
