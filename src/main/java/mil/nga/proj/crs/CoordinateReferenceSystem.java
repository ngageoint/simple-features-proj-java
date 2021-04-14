package mil.nga.proj.crs;

import java.util.ArrayList;
import java.util.List;

/**
 * Coordinate Reference System
 * 
 * @author osbornb
 */
public abstract class CoordinateReferenceSystem {

	/**
	 * Name
	 */
	private String name = null;

	/**
	 * Type
	 */
	private CoordinateReferenceSystemType type = null;

	/**
	 * Coordinate System
	 */
	private CoordinateSystem coordinateSystem = null;

	/**
	 * Usages
	 */
	private List<Usage> usages = null;

	/**
	 * Identifiers
	 */
	private List<Identifier> identifiers = null;

	/**
	 * Remark
	 */
	private String remark = null;

	/**
	 * Constructor
	 */
	public CoordinateReferenceSystem() {

	}

	/**
	 * Constructor
	 * 
	 * @param name
	 *            name
	 * @param type
	 *            coordinate reference system type
	 * @param coordinateSystem
	 *            coordinate system
	 */
	public CoordinateReferenceSystem(String name,
			CoordinateReferenceSystemType type,
			CoordinateSystem coordinateSystem) {
		setName(name);
		setType(type);
		setCoordinateSystem(coordinateSystem);
	}

	/**
	 * Get the name
	 * 
	 * @return name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Set the name
	 * 
	 * @param name
	 *            name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Get the type
	 * 
	 * @return type
	 */
	public CoordinateReferenceSystemType getType() {
		return type;
	}

	/**
	 * Set the type
	 * 
	 * @param type
	 *            type
	 */
	public void setType(CoordinateReferenceSystemType type) {
		this.type = type;
	}

	/**
	 * Get the coordinate system
	 * 
	 * @return coordinate system
	 */
	public CoordinateSystem getCoordinateSystem() {
		return coordinateSystem;
	}

	/**
	 * Set the coordinate system
	 * 
	 * @param coordinateSystem
	 *            coordinate system
	 */
	public void setCoordinateSystem(CoordinateSystem coordinateSystem) {
		this.coordinateSystem = coordinateSystem;
	}

	/**
	 * Get the usages
	 * 
	 * @return usages
	 */
	public List<Usage> getUsages() {
		return usages;
	}

	/**
	 * Has usages
	 * 
	 * @return true if has usages
	 */
	public boolean hasUsages() {
		return usages != null && !usages.isEmpty();
	}

	/**
	 * Set the usages
	 * 
	 * @param usages
	 *            usages
	 */
	public void setUsages(List<Usage> usages) {
		this.usages = usages;
	}

	/**
	 * Add the usage
	 * 
	 * @param usage
	 *            usage
	 */
	public void addUsage(Usage usage) {
		if (this.usages == null) {
			this.usages = new ArrayList<>();
		}
		this.usages.add(usage);
	}

	/**
	 * Add the usages
	 * 
	 * @param usages
	 *            usages
	 */
	public void addUsages(List<Usage> usages) {
		if (this.usages == null) {
			this.usages = new ArrayList<>();
		}
		this.usages.addAll(usages);
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
	 * Get the remark
	 * 
	 * @return remark
	 */
	public String getRemark() {
		return remark;
	}

	/**
	 * Has remark
	 * 
	 * @return true if has remark
	 */
	public boolean hasRemark() {
		return getRemark() != null;
	}

	/**
	 * Set the remark
	 * 
	 * @param remark
	 *            remark
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((coordinateSystem == null) ? 0
				: coordinateSystem.hashCode());
		result = prime * result
				+ ((identifiers == null) ? 0 : identifiers.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((remark == null) ? 0 : remark.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		result = prime * result + ((usages == null) ? 0 : usages.hashCode());
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
		CoordinateReferenceSystem other = (CoordinateReferenceSystem) obj;
		if (coordinateSystem == null) {
			if (other.coordinateSystem != null)
				return false;
		} else if (!coordinateSystem.equals(other.coordinateSystem))
			return false;
		if (identifiers == null) {
			if (other.identifiers != null)
				return false;
		} else if (!identifiers.equals(other.identifiers))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (remark == null) {
			if (other.remark != null)
				return false;
		} else if (!remark.equals(other.remark))
			return false;
		if (type != other.type)
			return false;
		if (usages == null) {
			if (other.usages != null)
				return false;
		} else if (!usages.equals(other.usages))
			return false;
		return true;
	}

}
