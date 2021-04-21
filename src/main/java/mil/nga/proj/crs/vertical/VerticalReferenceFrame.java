package mil.nga.proj.crs.vertical;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import mil.nga.proj.crs.common.ReferenceFrame;
import mil.nga.proj.crs.wkt.CRSWriter;

/**
 * Vertical Reference Frame (datum)
 * 
 * @author osbornb
 */
public class VerticalReferenceFrame extends ReferenceFrame {

	/**
	 * Logger
	 */
	private static final Logger logger = Logger
			.getLogger(VerticalReferenceFrame.class.getName());

	/**
	 * Constructor
	 */
	public VerticalReferenceFrame() {

	}

	/**
	 * Constructor
	 * 
	 * @param name
	 *            name
	 */
	public VerticalReferenceFrame(String name) {
		super(name);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		return super.hashCode();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
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
					"Failed to write vertical reference frame as a string", e);
			value = super.toString();
		} finally {
			writer.close();
		}
		return value;
	}

}
