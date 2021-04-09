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
	 * Has an area description
	 * 
	 * @return true if has area description
	 */
	public boolean hasAreaDescription() {
		return getAreaDescription() != null;
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
	 * Has a geographic bounding box
	 * 
	 * @return true if has geographic bounding box
	 */
	public boolean hasGeographicBoundingBox() {
		return getGeographicBoundingBox() != null;
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
	 * Has a vertical extent
	 * 
	 * @return true if has vertical extent
	 */
	public boolean hasVerticalExtent() {
		return getVerticalExtent() != null;
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
	 * Has a temporal extent
	 * 
	 * @return true if has temporal extent
	 */
	public boolean hasTemporalExtent() {
		return getTemporalExtent() != null;
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
