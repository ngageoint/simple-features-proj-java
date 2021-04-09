package mil.nga.proj.crs;

import java.util.ArrayList;
import java.util.List;

/**
 * Coordinate System
 * 
 * @author osbornb
 */
public class CoordinateSystem {

	/**
	 * Type
	 */
	private CoordinateSystemType type = null;

	/**
	 * Dimension
	 */
	private int dimension;

	/**
	 * Identifiers
	 */
	private List<Identifier> identifiers = null;

	/**
	 * Axes
	 */
	private List<Axis> axes = null;

	/**
	 * Unit
	 */
	private Unit unit = null;

	/**
	 * Constructor
	 */
	public CoordinateSystem() {

	}

	/**
	 * Constructor
	 * 
	 * @param type
	 *            type
	 * @param dimension
	 *            dimension
	 * @param axis
	 *            axis
	 */
	public CoordinateSystem(CoordinateSystemType type, int dimension,
			Axis axis) {
		setType(type);
		setDimension(dimension);
		addAxis(axis);
	}

	/**
	 * Constructor
	 * 
	 * @param type
	 *            type
	 * @param dimension
	 *            dimension
	 * @param axes
	 *            axes
	 */
	public CoordinateSystem(CoordinateSystemType type, int dimension,
			List<Axis> axes) {
		setType(type);
		setDimension(dimension);
		addAxes(axes);
	}

	/**
	 * Get the type
	 * 
	 * @return coordinate system type
	 */
	public CoordinateSystemType getType() {
		return type;
	}

	/**
	 * Set the type
	 * 
	 * @param type
	 *            coordinate system type
	 */
	public void setType(CoordinateSystemType type) {
		this.type = type;
	}

	/**
	 * Get the dimension
	 * 
	 * @return dimension
	 */
	public int getDimension() {
		return dimension;
	}

	/**
	 * Set the dimension
	 * 
	 * @param dimension
	 *            dimension
	 */
	public void setDimension(int dimension) {
		this.dimension = dimension;
	}

	/**
	 * Get the identifiers
	 * 
	 * @return identifiers
	 */
	public List<Identifier> getIdentifiers() {
		return identifiers;
	}

	/**
	 * Has identifiers
	 * 
	 * @return true if has identifiers
	 */
	public boolean hasIdentifiers() {
		return identifiers != null && !identifiers.isEmpty();
	}

	/**
	 * Set the identifiers
	 * 
	 * @param identifiers
	 *            identifiers
	 */
	public void setIdentifiers(List<Identifier> identifiers) {
		this.identifiers = identifiers;
	}

	/**
	 * Add the identifier
	 * 
	 * @param identifier
	 *            identifier
	 */
	public void addIdentifier(Identifier identifier) {
		if (this.identifiers == null) {
			this.identifiers = new ArrayList<>();
		}
		this.identifiers.add(identifier);
	}

	/**
	 * Add the identifiers
	 * 
	 * @param identifiers
	 *            identifiers
	 */
	public void addIdentifiers(List<Identifier> identifiers) {
		if (this.identifiers == null) {
			this.identifiers = new ArrayList<>();
		}
		this.identifiers.addAll(identifiers);
	}

	/**
	 * Get the axes
	 * 
	 * @return axes
	 */
	public List<Axis> getAxes() {
		return axes;
	}

	/**
	 * Set the axes
	 * 
	 * @param axes
	 *            axes
	 */
	public void setAxes(List<Axis> axes) {
		this.axes = axes;
	}

	/**
	 * Add the axis
	 * 
	 * @param axis
	 *            axis
	 */
	public void addAxis(Axis axis) {
		if (this.axes == null) {
			this.axes = new ArrayList<>();
		}
		this.axes.add(axis);
	}

	/**
	 * Add the axes
	 * 
	 * @param axes
	 *            axes
	 */
	public void addAxes(List<Axis> axes) {
		if (this.axes == null) {
			this.axes = new ArrayList<>();
		}
		this.axes.addAll(axes);
	}

	/**
	 * Get the unit
	 * 
	 * @return unit
	 */
	public Unit getUnit() {
		return unit;
	}

	/**
	 * Has a unit
	 * 
	 * @return true if has unit
	 */
	public boolean hasUnit() {
		return getUnit() != null;
	}

	/**
	 * Set the unit
	 * 
	 * @param unit
	 *            unit
	 */
	public void setUnit(Unit unit) {
		this.unit = unit;
	}

}
