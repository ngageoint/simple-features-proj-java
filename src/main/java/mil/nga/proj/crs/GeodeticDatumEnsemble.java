package mil.nga.proj.crs;

import java.util.List;

/**
 * Geodetic Datum Ensemble
 * 
 * @author osbornb
 */
public class GeodeticDatumEnsemble extends DatumEnsemble {

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
	 * Set the prime meridian
	 * 
	 * @param primeMeridian
	 *            prime meridian
	 */
	public void setPrimeMeridian(PrimeMeridian primeMeridian) {
		this.primeMeridian = primeMeridian;
	}

}
