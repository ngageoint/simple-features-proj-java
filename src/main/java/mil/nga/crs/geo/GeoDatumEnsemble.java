package mil.nga.crs.geo;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import mil.nga.crs.common.DatumEnsemble;
import mil.nga.crs.common.DatumEnsembleMember;
import mil.nga.crs.wkt.CRSWriter;

/**
 * Geodetic and Geographic Datum Ensemble
 * 
 * @author osbornb
 */
public class GeoDatumEnsemble extends DatumEnsemble implements GeoDatum {

	/**
	 * Logger
	 */
	private static final Logger logger = Logger
			.getLogger(GeoDatumEnsemble.class.getName());

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
	public GeoDatumEnsemble() {

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
	public GeoDatumEnsemble(String name, DatumEnsembleMember member,
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
	public GeoDatumEnsemble(String name, List<DatumEnsembleMember> members,
			Ellipsoid ellipsoid, double accuracy, PrimeMeridian primeMeridian) {
		super(name, members, accuracy);
		setEllipsoid(ellipsoid);
		setPrimeMeridian(primeMeridian);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Ellipsoid getEllipsoid() {
		return ellipsoid;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setEllipsoid(Ellipsoid ellipsoid) {
		this.ellipsoid = ellipsoid;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public PrimeMeridian getPrimeMeridian() {
		return primeMeridian;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean hasPrimeMeridian() {
		return getPrimeMeridian() != null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
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
		GeoDatumEnsemble other = (GeoDatumEnsemble) obj;
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
					"Failed to write geo datum ensemble as a string", e);
			value = super.toString();
		} finally {
			writer.close();
		}
		return value;
	}

}
