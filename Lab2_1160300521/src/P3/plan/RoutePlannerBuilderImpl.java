package P3.plan;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

import P3.graph.Stop;
import P3.graph.StopImpl;
import P3.graph.Vertex;
import P3.plan.RoutePlanner;
import P3.plan.RoutePlannerBuilder;
import P3.plan.RoutePlannerImpl;
import P1.graph.*;

public class RoutePlannerBuilderImpl implements RoutePlannerBuilder { 

	private RoutePlanner planner;
	private Graph<Vertex> adjGraph = Graph.empty();
	private HashMap<String, Stop> stopList = new HashMap<String, Stop>();
	private static final int SOME_CONSTANT = 3;

	@Override
	public RoutePlanner build(String fileName, int maxWaitLimit) {
		// parse the transit data from the file
		parse(fileName, maxWaitLimit);
		planner = new RoutePlannerImpl(adjGraph, stopList, maxWaitLimit);
		return planner;
	}

	/**
	 * the method to parse the given transit data file
	 * 
	 * @param fileName
	 *            the name of the data file
	 * @param maxWaitLimit
	 *            the max wait time
	 */
	private void parse(String fileName, int maxWaitLimit) {
		try {
			BufferedReader br = new BufferedReader(new FileReader(fileName));
			String headLine;
			String busName;
			int stopNumber;

			while ((headLine = br.readLine()) != null) {
				String[] headLineList = headLine.split(",");
				busName = headLineList[0];
				stopNumber = Integer.parseInt(headLineList[1]);

				String firstLine = br.readLine();
				String[] firstLineList = firstLine.split(",");
				Stop firstStop = new StopImpl(firstLineList[0],
						Double.parseDouble(firstLineList[1]),
						Double.parseDouble(firstLineList[2]));
				Vertex srcVertex = new Vertex(firstStop,
						Integer.parseInt(firstLineList[SOME_CONSTANT]));
				adjGraph.add(srcVertex);

				for (int i = 1; i < stopNumber; i++) {
					if (!stopList.containsKey(srcVertex.getStop().getName())) {
						stopList.put(srcVertex.getStop().getName(),
								srcVertex.getStop());
					}
					String destLine = br.readLine();
					String[] destLineList = destLine.split(",");
					Stop destStop = new StopImpl(destLineList[0],
							Double.parseDouble(destLineList[1]),
							Double.parseDouble(destLineList[2]));
					Vertex destVertex = new Vertex(destStop,
							Integer.parseInt(destLineList[SOME_CONSTANT]));
					adjGraph.add(destVertex);
					adjGraph.set(srcVertex, destVertex,maxWaitLimit);
					srcVertex = destVertex;
				}

				adjGraph.set(srcVertex, null,maxWaitLimit);
				if (!stopList.containsKey(srcVertex.getStop().getName())) {
					stopList.put(srcVertex.getStop().getName(),
							srcVertex.getStop());
				}
			}
			br.close();

		} catch (IOException e) {
			System.out.println("Cannot read from the file!");
		}
	}

}
