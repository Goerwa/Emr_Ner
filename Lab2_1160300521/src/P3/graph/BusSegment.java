package P3.graph;

public class BusSegment implements TripSegment {
	
	private Vertex start;
	private Vertex end;
	private int waitTime;
	private String busName;

	/**
	 * the constructor of the busSegment class
	 * 
	 * @param s
	 *            the input start vertex
	 * @param e
	 *            the input end vertex
	 * @param name
	 *            the input busName
	 */
	public BusSegment(Vertex s, Vertex e, String name) {
		this.start = s;
		this.end = e;
		this.waitTime = e.getTime() - s.getTime();
		this.busName = "bus";
	}

	@Override
	public String getInstructions() {
		return this.busName;
	}

	@Override
	public int getWaitTime() {
		return this.waitTime;
	}

	@Override
	public Vertex getEndVertex() {
		return this.end;
	}

	@Override
	public Stop getStartStop() {
		return this.start.getStop();
	}

	@Override
	public Stop getEndStop() {
		return this.end.getStop();
	}

	@Override
	public int getStartTime() {
		return this.start.getTime();
	}

	@Override
	public int getEndTime() {
		return this.end.getTime();
	}

}
