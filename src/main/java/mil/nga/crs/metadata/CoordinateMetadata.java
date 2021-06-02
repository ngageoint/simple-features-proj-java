package mil.nga.crs.metadata;

import java.util.List;

import mil.nga.crs.CRS;
import mil.nga.crs.CRSType;
import mil.nga.crs.CoordinateReferenceSystem;
import mil.nga.crs.common.Identifier;
import mil.nga.crs.common.Usage;

/**
 * Coordinate metadata
 * 
 * @author osbornb
 */
public class CoordinateMetadata extends CRS {

	/**
	 * Coordinate Reference System
	 */
	private CoordinateReferenceSystem coordinateReferenceSystem = null;

	/**
	 * Coordinate Epoch
	 */
	private Double epoch;

	/**
	 * Constructor
	 */
	public CoordinateMetadata() {
		super(CRSType.COORDINATE_METADATA);
	}

	/**
	 * Constructor
	 * 
	 * @param crs
	 *            coordinate reference system
	 */
	public CoordinateMetadata(CoordinateReferenceSystem crs) {
		this();
		setCoordinateReferenceSystem(crs);
	}

	/**
	 * Constructor
	 * 
	 * @param crs
	 *            coordinate reference system
	 * @param epoch
	 *            coordinate epoch
	 */
	public CoordinateMetadata(CoordinateReferenceSystem crs, Double epoch) {
		this(crs);
		setEpoch(epoch);
	}

	/**
	 * Get the coordinate reference system
	 * 
	 * @return coordinate reference system
	 */
	public CoordinateReferenceSystem getCoordinateReferenceSystem() {
		return coordinateReferenceSystem;
	}

	/**
	 * Set the coordinate reference system
	 * 
	 * @param crs
	 *            coordinate reference system
	 */
	public void setCoordinateReferenceSystem(CoordinateReferenceSystem crs) {
		this.coordinateReferenceSystem = crs;
	}

	/**
	 * Get the coordinate epoch
	 * 
	 * @return coordinate epoch
	 */
	public Double getEpoch() {
		return epoch;
	}

	/**
	 * Has a coordinate epoch
	 * 
	 * @return true if has coordinate epoch
	 */
	public boolean hasEpoch() {
		return getEpoch() != null;
	}

	/**
	 * Set the coordinate epoch
	 * 
	 * @param epoch
	 *            coordinate epoch
	 */
	public void setEpoch(Double epoch) {
		this.epoch = epoch;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getName() {
		return coordinateReferenceSystem.getName();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setName(String name) {
		coordinateReferenceSystem.setName(name);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Usage> getUsages() {
		return coordinateReferenceSystem.getUsages();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean hasUsages() {
		return coordinateReferenceSystem.hasUsages();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int numUsages() {
		return coordinateReferenceSystem.numUsages();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Usage getUsage(int index) {
		return coordinateReferenceSystem.getUsage(index);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setUsages(List<Usage> usages) {
		coordinateReferenceSystem.setUsages(usages);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addUsage(Usage usage) {
		coordinateReferenceSystem.addUsage(usage);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addUsages(List<Usage> usages) {
		coordinateReferenceSystem.addUsages(usages);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Identifier> getIdentifiers() {
		return coordinateReferenceSystem.getIdentifiers();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean hasIdentifiers() {
		return coordinateReferenceSystem.hasIdentifiers();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int numIdentifiers() {
		return coordinateReferenceSystem.numIdentifiers();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Identifier getIdentifier(int index) {
		return coordinateReferenceSystem.getIdentifier(index);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setIdentifiers(List<Identifier> identifiers) {
		coordinateReferenceSystem.setIdentifiers(identifiers);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addIdentifier(Identifier identifier) {
		coordinateReferenceSystem.addIdentifier(identifier);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addIdentifiers(List<Identifier> identifiers) {
		coordinateReferenceSystem.addIdentifiers(identifiers);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getRemark() {
		return coordinateReferenceSystem.getRemark();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean hasRemark() {
		return coordinateReferenceSystem.hasRemark();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setRemark(String remark) {
		coordinateReferenceSystem.setRemark(remark);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((coordinateReferenceSystem == null) ? 0
				: coordinateReferenceSystem.hashCode());
		result = prime * result + ((epoch == null) ? 0 : epoch.hashCode());
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
		CoordinateMetadata other = (CoordinateMetadata) obj;
		if (coordinateReferenceSystem == null) {
			if (other.coordinateReferenceSystem != null)
				return false;
		} else if (!coordinateReferenceSystem
				.equals(other.coordinateReferenceSystem))
			return false;
		if (epoch == null) {
			if (other.epoch != null)
				return false;
		} else if (!epoch.equals(other.epoch))
			return false;
		return true;
	}

}
