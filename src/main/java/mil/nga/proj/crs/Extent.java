package mil.nga.proj.crs;

/**
 * Extent
 * 
 * @author osbornb
 */
public class Extent {

	/**
	 * Area Description
	 */
	private String areaDescription = null;

	/**
	 * Geographic Bounding Box
	 */
	private GeographicBoundingBox geographicBoundingBox = null;

	/**
	 * Vertical Extent
	 */
	private VerticalExtent verticalExtent = null;

	/**
	 * Temporal Extent
	 */
	private TemporalExtent temporalExtent = null;

	/**
	 * Constructor
	 */
	public Extent() {

	}

	/**
	 * Get the area description
	 * 
	 * @return area description
	 */
	public String getAreaDescription() {
		return areaDescription;
	}

	/**
	 * Set the area description
	 * 
	 * @param areaDescription
	 *            area description
	 */
	public void setAreaDescription(String areaDescription) {
		this.areaDescription = areaDescription;
	}

	/**
	 * Get the geographic bounding box
	 * 
	 * @return geographic bounding box
	 */
	public GeographicBoundingBox getGeographicBoundingBox() {
		return geographicBoundingBox;
	}

	/**
	 * Set the geographic bounding box
	 * 
	 * @param geographicBoundingBox
	 *            geographic bounding box
	 */
	public void setGeographicBoundingBox(
			GeographicBoundingBox geographicBoundingBox) {
		this.geographicBoundingBox = geographicBoundingBox;
	}

	/**
	 * Get the vertical extent
	 * 
	 * @return vertical extent
	 */
	public VerticalExtent getVerticalExtent() {
		return verticalExtent;
	}

	/**
	 * Set the vertical extent
	 * 
	 * @param verticalExtent
	 *            vertical extent
	 */
	public void setVerticalExtent(VerticalExtent verticalExtent) {
		this.verticalExtent = verticalExtent;
	}

	/**
	 * Get the temporal extent
	 * 
	 * @return temporal extent
	 */
	public TemporalExtent getTemporalExtent() {
		return temporalExtent;
	}

	/**
	 * Set the temporal extent
	 * 
	 * @param temporalExtent
	 *            temporal extent
	 */
	public void setTemporalExtent(TemporalExtent temporalExtent) {
		this.temporalExtent = temporalExtent;
	}

}
