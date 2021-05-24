package mil.nga.crs;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import mil.nga.proj.ProjectionException;

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
	private List<SimpleCoordinateReferenceSystem> coordinateReferenceSystems = new ArrayList<>();

	/**
	 * Constructor
	 */
	public CompoundCoordinateReferenceSystem() {
		super(CRSType.COMPOUND);
	}

	/**
	 * Constructor
	 * 
	 * @param name
	 *            name
	 * @param type
	 *            coordinate reference system type
	 */
	public CompoundCoordinateReferenceSystem(String name, CRSType type) {
		super(name, type);
	}

	/**
	 * Get the coordinate reference systems
	 * 
	 * @return coordinate reference systems
	 */
	public List<SimpleCoordinateReferenceSystem> getCoordinateReferenceSystems() {
		return Collections.unmodifiableList(coordinateReferenceSystems);
	}

	/**
	 * Number of coordinate reference systems
	 * 
	 * @return coordinate reference systems count
	 */
	public int numCoordinateReferenceSystems() {
		return coordinateReferenceSystems.size();
	}

	/**
	 * Get the coordinate reference system at the index
	 * 
	 * @param index
	 *            crs index
	 * @return coordinate reference system
	 */
	public SimpleCoordinateReferenceSystem getCoordinateReferenceSystem(
			int index) {
		return coordinateReferenceSystems.get(index);
	}

	/**
	 * Set the coordinate reference systems
	 * 
	 * @param coordinateReferenceSystems
	 *            coordinate reference systems
	 */
	public void setCoordinateReferenceSystems(
			List<SimpleCoordinateReferenceSystem> coordinateReferenceSystems) {
		this.coordinateReferenceSystems.clear();
		addCoordinateReferenceSystems(coordinateReferenceSystems);
	}

	/**
	 * Add the coordinate reference system
	 * 
	 * @param crs
	 *            coordinate reference system
	 */
	public void addCoordinateReferenceSystem(
			SimpleCoordinateReferenceSystem crs) {
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
			List<SimpleCoordinateReferenceSystem> crss) {
		for (SimpleCoordinateReferenceSystem crs : crss) {
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
