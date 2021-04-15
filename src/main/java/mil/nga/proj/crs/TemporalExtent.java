package mil.nga.proj.crs;

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

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((end == null) ? 0 : end.hashCode());
		result = prime * result + ((start == null) ? 0 : start.hashCode());
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
		if (start == null) {
			if (other.start != null)
				return false;
		} else if (!start.equals(other.start))
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
		}
		writer.close();
		return value;
	}

}
