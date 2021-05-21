package mil.nga.proj.crs.common;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import mil.nga.proj.crs.wkt.CRSWriter;

/**
 * Coordinate System
 * 
 * @author osbornb
 */
public class CoordinateSystem implements Identifiable {

	/**
	 * Logger
	 */
	private static final Logger logger = Logger
			.getLogger(CoordinateSystem.class.getName());

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
	private List<Axis> axes = new ArrayList<>();

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
	 * {@inheritDoc}
	 */
	@Override
	public List<Identifier> getIdentifiers() {
		return identifiers;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean hasIdentifiers() {
		return identifiers != null && !identifiers.isEmpty();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int numIdentifiers() {
		return identifiers != null ? identifiers.size() : 0;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Identifier getIdentifier(int index) {
		return identifiers.get(index);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setIdentifiers(List<Identifier> identifiers) {
		this.identifiers = identifiers;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addIdentifier(Identifier identifier) {
		if (this.identifiers == null) {
			this.identifiers = new ArrayList<>();
		}
		this.identifiers.add(identifier);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
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
	 * Number of axes
	 * 
	 * @return axes count
	 */
	public int numAxes() {
		return axes.size();
	}

	/**
	 * Get the axis at the index
	 * 
	 * @param index
	 *            axis index
	 * @return axis
	 */
	public Axis getAxis(int index) {
		return axes.get(index);
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
		this.axes.add(axis);
	}

	/**
	 * Add the axes
	 * 
	 * @param axes
	 *            axes
	 */
	public void addAxes(List<Axis> axes) {
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

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((axes == null) ? 0 : axes.hashCode());
		result = prime * result + dimension;
		result = prime * result
				+ ((identifiers == null) ? 0 : identifiers.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		result = prime * result + ((unit == null) ? 0 : unit.hashCode());
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
		CoordinateSystem other = (CoordinateSystem) obj;
		if (axes == null) {
			if (other.axes != null)
				return false;
		} else if (!axes.equals(other.axes))
			return false;
		if (dimension != other.dimension)
			return false;
		if (identifiers == null) {
			if (other.identifiers != null)
				return false;
		} else if (!identifiers.equals(other.identifiers))
			return false;
		if (type != other.type)
			return false;
		if (unit == null) {
			if (other.unit != null)
				return false;
		} else if (!unit.equals(other.unit))
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
					"Failed to write coordinate system as a string", e);
			value = super.toString();
		} finally {
			writer.close();
		}
		return value;
	}

}
