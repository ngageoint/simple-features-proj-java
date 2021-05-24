package mil.nga.crs.common;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import mil.nga.crs.wkt.CRSWriter;

/**
 * Extent
 * 
 * @author osbornb
 */
public class Extent {

	/**
	 * Logger
	 */
	private static final Logger logger = Logger
			.getLogger(Extent.class.getName());

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

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((areaDescription == null) ? 0 : areaDescription.hashCode());
		result = prime * result + ((geographicBoundingBox == null) ? 0
				: geographicBoundingBox.hashCode());
		result = prime * result
				+ ((temporalExtent == null) ? 0 : temporalExtent.hashCode());
		result = prime * result
				+ ((verticalExtent == null) ? 0 : verticalExtent.hashCode());
		return result;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Extent other = (Extent) obj;
		if (areaDescription == null) {
			if (other.areaDescription != null)
				return false;
		} else if (!areaDescription.equals(other.areaDescription))
			return false;
		if (geographicBoundingBox == null) {
			if (other.geographicBoundingBox != null)
				return false;
		} else if (!geographicBoundingBox.equals(other.geographicBoundingBox))
			return false;
		if (temporalExtent == null) {
			if (other.temporalExtent != null)
				return false;
		} else if (!temporalExtent.equals(other.temporalExtent))
			return false;
		if (verticalExtent == null) {
			if (other.verticalExtent != null)
				return false;
		} else if (!verticalExtent.equals(other.verticalExtent))
			return false;
		return true;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		String value = null;
		CRSWriter writer = new CRSWriter();
		try {
			writer.write(this);
			value = writer.toString();
		} catch (IOException e) {
			logger.log(Level.WARNING, "Failed to write extent as a string", e);
			value = super.toString();
		} finally {
			writer.close();
		}
		return value;
	}

}
