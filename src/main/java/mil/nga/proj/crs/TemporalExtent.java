package mil.nga.proj.crs;

/**
 * Temporal Extent
 * 
 * @author osbornb
 */
public class TemporalExtent {

	/**
	 * Start
	 */
	private String start = null;

	/**
	 * End
	 */
	private String end = null;

	/**
	 * Constructor
	 */
	public TemporalExtent() {

	}

	/**
	 * Constructor
	 * 
	 * @param start
	 *            start
	 * @param end
	 *            end
	 */
	public TemporalExtent(String start, String end) {
		setStart(start);
		setEnd(end);
	}

	/**
	 * Get the start
	 * 
	 * @return start
	 */
	public String getStart() {
		return start;
	}

	/**
	 * Set the start
	 * 
	 * @param start
	 *            start
	 */
	public void setStart(String start) {
		this.start = start;
	}

	/**
	 * Get the end
	 * 
	 * @return end
	 */
	public String getEnd() {
		return end;
	}

	/**
	 * Set the end
	 * 
	 * @param end
	 *            end
	 */
	public void setEnd(String end) {
		this.end = end;
	}

}
