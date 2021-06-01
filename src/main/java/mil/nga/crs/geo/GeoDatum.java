package mil.nga.crs.geo;

import mil.nga.crs.common.Identifiable;

/**
 * Geodetic and Geographic Datum interface
 * 
 * @author osbornb
 */
public interface GeoDatum extends Identifiable {

	/**
	 * Get the datum name
	 * 
	 * @return datum name
	 */
	public String getName();

	/**
	 * Set the datum name
	 * 
	 * @param name
	 *            datum name
	 */
	public void setName(String name);

	/**
	 * Get the ellipsoid
	 * 
	 * @return ellipsoid
	 */
	public Ellipsoid getEllipsoid();

	/**
	 * Set the ellipsoid
	 * 
	 * @param ellipsoid
	 *            ellipsoid
	 */
	public void setEllipsoid(Ellipsoid ellipsoid);

	/**
	 * Get the prime meridian
	 * 
	 * @return prime meridian
	 */
	public PrimeMeridian getPrimeMeridian();

	/**
	 * Has a prime meridian
	 * 
	 * @return true if has prime meridian
	 */
	public boolean hasPrimeMeridian();

	/**
	 * Set the prime meridian
	 * 
	 * @param primeMeridian
	 *            prime meridian
	 */
	public void setPrimeMeridian(PrimeMeridian primeMeridian);

}
