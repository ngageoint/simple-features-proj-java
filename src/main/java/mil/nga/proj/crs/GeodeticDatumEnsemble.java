package mil.nga.proj.crs;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import mil.nga.proj.crs.wkt.CRSWriter;

/**
 * Geodetic Datum Ensemble
 * 
 * @author osbornb
 */
public class GeodeticDatumEnsemble extends DatumEnsemble {

	/**
	 * Logger
	 */
	private static final Logger logger = Logger
			.getLogger(GeodeticDatumEnsemble.class.getName());

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
	public GeodeticDatumEnsemble() {

	}

	/**
	 * Constructor
	 * 
	 * @param name
	 *            name
	 * @param member
	 *            member
	 * @param ellipsoid
	 *            ellipsoid
	 * @param accuracy
	 *            accuracy
	 * @param primeMeridian
	 *            prime meridian
	 */
	public GeodeticDatumEnsemble(String name, DatumEnsembleMember member,
			Ellipsoid ellipsoid, double accuracy, PrimeMeridian primeMeridian) {
		super(name, member, accuracy);
		setEllipsoid(ellipsoid);
		setPrimeMeridian(primeMeridian);
	}

	/**
	 * Constructor
	 * 
	 * @param name
	 *            name
	 * @param members
	 *            members
	 * @param ellipsoid
	 *            ellipsoid
	 * @param accuracy
	 *            accuracy
	 * @param primeMeridian
	 *            prime meridian
	 */
	public GeodeticDatumEnsemble(String name, List<DatumEnsembleMember> members,
			Ellipsoid ellipsoid, double accuracy, PrimeMeridian primeMeridian) {
		super(name, members, accuracy);
		setEllipsoid(ellipsoid);
		setPrimeMeridian(primeMeridian);
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
	 * TODO http://ogc.standardstracker.org/show_request.cgi?id=672
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
		GeodeticDatumEnsemble other = (GeodeticDatumEnsemble) obj;
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
					"Failed to write geodetic datum ensemble as a string", e);
			value = super.toString();
		} finally {
			writer.close();
		}
		return value;
	}

}
