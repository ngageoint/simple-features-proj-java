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
	 * @param coordinateSystem
	 *            coordinate system
	 */
	public CoordinateReferenceSystem(String name,
			CoordinateSystem coordinateSystem) {
		setName(name);
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

}
