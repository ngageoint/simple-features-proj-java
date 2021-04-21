package mil.nga.proj.crs.common;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import mil.nga.proj.crs.wkt.CRSWriter;

/**
 * Temporal Extent
 * 
 * @author osbornb
 */
public class TemporalExtent {

	/**
	 * Logger
	 */
	private static final Logger logger = Logger
			.getLogger(TemporalExtent.class.getName());

	/**
	 * Start
	 */
	private String start = null;

	/**
	 * Start date time
	 */
	private DateTime startDateTime = null;

	/**
	 * End
	 */
	private String end = null;

	/**
	 * End date time
	 */
	private DateTime endDateTime = null;

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
	 * Constructor
	 * 
	 * @param start
	 *            start date tiem
	 * @param end
	 *            end date time
	 */
	public TemporalExtent(DateTime start, DateTime end) {
		setStartDateTime(start);
		setEndDateTime(end);
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
		DateTime dateTime = DateTime.tryParse(start);
		if (dateTime != null) {
			this.startDateTime = dateTime;
		}
	}

	/**
	 * Get the start date time
	 * 
	 * @return start date time
	 */
	public DateTime getStartDateTime() {
		return startDateTime;
	}

	/**
	 * Has a start date time
	 * 
	 * @return true if has start date time
	 */
	public boolean hasStartDateTime() {
		return getStartDateTime() != null;
	}

	/**
	 * Set the start date time
	 * 
	 * @param startDateTime
	 *            start date time
	 */
	public void setStartDateTime(DateTime startDateTime) {
		this.startDateTime = startDateTime;
		this.start = startDateTime.toString();
	}

	/**
	 * Set the start date time
	 * 
	 * @param start
	 *            start date time
	 */
	public void setStartDateTime(String start) {
		this.start = start;
		this.startDateTime = DateTime.parse(start);
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
		DateTime dateTime = DateTime.tryParse(end);
		if (dateTime != null) {
			this.endDateTime = dateTime;
		}
	}

	/**
	 * Get the end date time
	 * 
	 * @return end date time
	 */
	public DateTime getEndDateTime() {
		return endDateTime;
	}

	/**
	 * Has an end date time
	 * 
	 * @return true if has end date time
	 */
	public boolean hasEndDateTime() {
		return getEndDateTime() != null;
	}

	/**
	 * Set the end date time
	 * 
	 * @param endDateTime
	 *            end date time
	 */
	public void setEndDateTime(DateTime endDateTime) {
		this.endDateTime = endDateTime;
		this.end = endDateTime.toString();
	}

	/**
	 * Set the end date time
	 * 
	 * @param end
	 *            end date time
	 */
	public void setEndDateTime(String end) {
		this.end = end;
		this.endDateTime = DateTime.parse(end);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((end == null) ? 0 : end.hashCode());
		result = prime * result
				+ ((endDateTime == null) ? 0 : endDateTime.hashCode());
		result = prime * result + ((start == null) ? 0 : start.hashCode());
		result = prime * result
				+ ((startDateTime == null) ? 0 : startDateTime.hashCode());
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
		TemporalExtent other = (TemporalExtent) obj;
		if (end == null) {
			if (other.end != null)
				return false;
		} else if (!end.equals(other.end))
			return false;
		if (endDateTime == null) {
			if (other.endDateTime != null)
				return false;
		} else if (!endDateTime.equals(other.endDateTime))
			return false;
		if (start == null) {
			if (other.start != null)
				return false;
		} else if (!start.equals(other.start))
			return false;
		if (startDateTime == null) {
			if (other.startDateTime != null)
				return false;
		} else if (!startDateTime.equals(other.startDateTime))
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
			logger.log(Level.WARNING,
					"Failed to write temporal extent as a string", e);
			value = super.toString();
		} finally {
			writer.close();
		}
		return value;
	}

}
