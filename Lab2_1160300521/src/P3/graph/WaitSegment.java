package P3.graph;

public class WaitSegment implements TripSegment {

	private Vertex start;
	private Vertex end;
	private int waitTime;
	private String wait;

	/**
	 * the constructor for the waitSegment class
	 * 
	 * @param s
	 *            the input start vertex
	 * @param e
	 *            the input end vertex
	 */
	public WaitSegment(Vertex s, Vertex e) {
		this.start = s;
		this.end = e;
		this.waitTime = e.getTime() - s.getTime();
		this.wait = "wait";
	}

	@Override
	public String getInstructions() {
		return this.wait;
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
