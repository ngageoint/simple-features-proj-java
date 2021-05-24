package mil.nga.crs.common;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import mil.nga.crs.wkt.CRSWriter;

/**
 * Dynamic Coordinate Reference System
 * 
 * @author osbornb
 */
public class Dynamic implements Identifiable {

	/**
	 * Logger
	 */
	private static final Logger logger = Logger
			.getLogger(Dynamic.class.getName());

	/**
	 * Reference Epoch
	 */
	private double referenceEpoch;

	/**
	 * Deformation Model Name
	 */
	private String deformationModelName = null;

	/**
	 * Deformation Model Identifiers
	 */
	private List<Identifier> identifiers;

	/**
	 * Constructor
	 */
	public Dynamic() {

	}

	/**
	 * Constructor
	 * 
	 * @param referenceEpoch
	 *            reference epoch
	 */
	public Dynamic(double referenceEpoch) {
		setReferenceEpoch(referenceEpoch);
	}

	/**
	 * Get the reference epoch
	 * 
	 * @return reference epoch
	 */
	public double getReferenceEpoch() {
		return referenceEpoch;
	}

	/**
	 * Set the reference epoch
	 * 
	 * @param referenceEpoch
	 *            reference epoch
	 */
	public void setReferenceEpoch(double referenceEpoch) {
		this.referenceEpoch = referenceEpoch;
	}

	/**
	 * Get the deformation model name
	 * 
	 * @return deformation model name
	 */
	public String getDeformationModelName() {
		return deformationModelName;
	}

	/**
	 * Has a deformation model name
	 * 
	 * @return true if has deformation model name
	 */
	public boolean hasDeformationModelName() {
		return getDeformationModelName() != null;
	}

	/**
	 * Set the deformation model name
	 * 
	 * @param deformationModelName
	 *            deformation model name
	 */
	public void setDeformationModelName(String deformationModelName) {
		this.deformationModelName = deformationModelName;
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
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((deformationModelName == null) ? 0
				: deformationModelName.hashCode());
		result = prime * result
				+ ((identifiers == null) ? 0 : identifiers.hashCode());
		long temp;
		temp = Double.doubleToLongBits(referenceEpoch);
		result = prime * result + (int) (temp ^ (temp >>> 32));
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
		Dynamic other = (Dynamic) obj;
		if (deformationModelName == null) {
			if (other.deformationModelName != null)
				return false;
		} else if (!deformationModelName.equals(other.deformationModelName))
			return false;
		if (identifiers == null) {
			if (other.identifiers != null)
				return false;
		} else if (!identifiers.equals(other.identifiers))
			return false;
		if (Double.doubleToLongBits(referenceEpoch) != Double
				.doubleToLongBits(other.referenceEpoch))
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
			logger.log(Level.WARNING, "Failed to write dynamic as a string", e);
			value = super.toString();
		} finally {
			writer.close();
		}
		return value;
	}

}
