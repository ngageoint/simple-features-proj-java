package mil.nga.proj.crs;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import mil.nga.proj.ProjectionException;
import mil.nga.proj.crs.common.CoordinateSystem;

/**
 * Compound Coordinate Reference System
 * 
 * @author osbornb
 */
public class CompoundCoordinateReferenceSystem
		extends CoordinateReferenceSystem {

	/**
	 * Coordinate Reference Systems
	 */
	private List<CoordinateReferenceSystem> coordinateReferenceSystems = new ArrayList<>();

	/**
	 * Constructor
	 */
	public CompoundCoordinateReferenceSystem() {
		super(CoordinateReferenceSystemType.COMPOUND);
	}

	/**
	 * Constructor
	 * 
	 * @param name
	 *            name
	 * @param type
	 *            coordinate reference system type
	 */
	public CompoundCoordinateReferenceSystem(String name,
			CoordinateReferenceSystemType type) {
		super(name, type);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public CoordinateSystem getCoordinateSystem() {
		throw new UnsupportedOperationException(
				"Coordinate System not supported for Compound CRS");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setCoordinateSystem(CoordinateSystem coordinateSystem) {
		throw new UnsupportedOperationException(
				"Coordinate System not supported for Compound CRS");
	}

	/**
	 * Get the coordinate reference systems
	 * 
	 * @return coordinate reference systems
	 */
	public List<CoordinateReferenceSystem> getCoordinateReferenceSystems() {
		return Collections.unmodifiableList(coordinateReferenceSystems);
	}

	/**
	 * Set the coordinate reference systems
	 * 
	 * @param coordinateReferenceSystems
	 *            coordinate reference systems
	 */
	public void setCoordinateReferenceSystems(
			List<CoordinateReferenceSystem> coordinateReferenceSystems) {
		this.coordinateReferenceSystems.clear();
		addCoordinateReferenceSystems(coordinateReferenceSystems);
	}

	/**
	 * Add the coordinate reference system
	 * 
	 * @param crs
	 *            coordinate reference system
	 */
	public void addCoordinateReferenceSystem(CoordinateReferenceSystem crs) {
		switch (crs.getType()) {
		case GEODETIC:
		case GEOGRAPHIC:
		case PROJECTED:
		case VERTICAL:
		case ENGINEERING:
		case PARAMETRIC:
		case TEMPORAL:
		case DERIVED:
			this.coordinateReferenceSystems.add(crs);
			break;
		default:
			throw new ProjectionException(
					"Unsupported Compound Coordinate Reference System Type: "
							+ crs.getType());
		}
	}

	/**
	 * Add the coordinate reference systems
	 * 
	 * @param crss
	 *            coordinate reference systems
	 */
	public void addCoordinateReferenceSystems(
			List<CoordinateReferenceSystem> crss) {
		for (CoordinateReferenceSystem crs : crss) {
			addCoordinateReferenceSystem(crs);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((coordinateReferenceSystems == null) ? 0
				: coordinateReferenceSystems.hashCode());
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
		CompoundCoordinateReferenceSystem other = (CompoundCoordinateReferenceSystem) obj;
		if (coordinateReferenceSystems == null) {
			if (other.coordinateReferenceSystems != null)
				return false;
		} else if (!coordinateReferenceSystems
				.equals(other.coordinateReferenceSystems))
			return false;
		return true;
	}

}
