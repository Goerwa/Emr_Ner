package P3.graph;

public class StopImpl implements Stop {

	private String name;
	private double latitude;
	private double longitude;

	/**
	 * the constructor for the stopImpl class
	 * 
	 * @param oneName
	 *            the input name of the class
	 * @param oneLatitude
	 *            the input latitude of the class
	 * @param oneLongitude
	 *            the input longitude of the class
	 */
	public StopImpl(String oneName, double oneLatitude, double oneLongitude) {
		this.name = oneName;
		this.latitude = oneLatitude;
		this.longitude = oneLongitude;
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public double getLatitude() {
		return this.latitude;
	}

	@Override
	public double getLongitude() {
		return this.longitude;
	}

}
