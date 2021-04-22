package mil.nga.proj.crs.geodetic;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import mil.nga.proj.crs.CoordinateReferenceSystemType;
import mil.nga.proj.crs.common.ReferenceFrame;
import mil.nga.proj.crs.wkt.CRSWriter;

/**
 * Geodetic Reference Frame (datum)
 * 
 * @author osbornb
 */
public class GeodeticReferenceFrame extends ReferenceFrame {

	/**
	 * Logger
	 */
	private static final Logger logger = Logger
			.getLogger(GeodeticReferenceFrame.class.getName());

	/**
	 * Ellipsoid
	 */
	private Ellipsoid ellipsoid = null;

	/**
	 * Prime Meridian
	 */
	private PrimeMeridian primeMeridian = null;

	/**
	 * Constructor
	 */
	public GeodeticReferenceFrame() {
		super(CoordinateReferenceSystemType.GEODETIC);
	}

	/**
	 * Constructor
	 * 
	 * @param name
	 *            name
	 * @param ellipsoid
	 *            ellipsoid
	 */
	public GeodeticReferenceFrame(String name, Ellipsoid ellipsoid) {
		super(name, CoordinateReferenceSystemType.GEODETIC);
		setEllipsoid(ellipsoid);
	}

	/**
	 * Get the ellipsoid
	 * 
	 * @return ellipsoid
	 */
	public Ellipsoid getEllipsoid() {
		return ellipsoid;
	}

	/**
	 * Set the ellipsoid
	 * 
	 * @param ellipsoid
	 *            ellipsoid
	 */
	public void setEllipsoid(Ellipsoid ellipsoid) {
		this.ellipsoid = ellipsoid;
	}

	/**
	 * Get the prime meridian
	 * 
	 * @return prime meridian
	 */
	public PrimeMeridian getPrimeMeridian() {
		return primeMeridian;
	}

	/**
	 * Has a prime meridian
	 * 
	 * @return true if has prime meridian
	 */
	public boolean hasPrimeMeridian() {
		return getPrimeMeridian() != null;
	}

	/**
	 * Set the prime meridian
	 * 
	 * @param primeMeridian
	 *            prime meridian
	 */
	public void setPrimeMeridian(PrimeMeridian primeMeridian) {
		this.primeMeridian = primeMeridian;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((ellipsoid == null) ? 0 : ellipsoid.hashCode());
		result = prime * result
				+ ((primeMeridian == null) ? 0 : primeMeridian.hashCode());
		return result;
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
		GeodeticReferenceFrame other = (GeodeticReferenceFrame) obj;
		if (ellipsoid == null) {
			if (other.ellipsoid != null)
				return false;
		} else if (!ellipsoid.equals(other.ellipsoid))
			return false;
		if (primeMeridian == null) {
			if (other.primeMeridian != null)
				return false;
		} else if (!primeMeridian.equals(other.primeMeridian))
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
					"Failed to write geodetic reference frame as a string", e);
			value = super.toString();
		} finally {
			writer.close();
		}
		return value;
	}

}
