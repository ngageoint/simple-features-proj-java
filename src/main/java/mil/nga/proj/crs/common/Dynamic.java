package mil.nga.proj.crs.common;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import mil.nga.proj.crs.wkt.CRSWriter;

/**
 * Dynamic Coordinate Reference System
 * 
 * @author osbornb
 */
public class Dynamic {

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
	private List<Identifier> deformationModelIdentifiers;

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
	 * Get the deformation model identifiers
	 * 
	 * @return deformation model identifiers
	 */
	public List<Identifier> getDeformationModelIdentifiers() {
		return deformationModelIdentifiers;
	}

	/**
	 * Has deformation model identifiers
	 * 
	 * @return true if has deformation model identifiers
	 */
	public boolean hasDeformationModelIdentifiers() {
		return deformationModelIdentifiers != null
				&& !deformationModelIdentifiers.isEmpty();
	}

	/**
	 * Set the deformation model identifiers
	 * 
	 * @param identifiers
	 *            deformation model identifiers
	 */
	public void setDeformationModelIdentifiers(List<Identifier> identifiers) {
		this.deformationModelIdentifiers = identifiers;
	}

	/**
	 * Add the deformation model identifier
	 * 
	 * @param identifier
	 *            deformation model identifier
	 */
	public void addIdentifier(Identifier identifier) {
		if (this.deformationModelIdentifiers == null) {
			this.deformationModelIdentifiers = new ArrayList<>();
		}
		this.deformationModelIdentifiers.add(identifier);
	}

	/**
	 * Add the deformation model identifiers
	 * 
	 * @param identifiers
	 *            deformation model identifiers
	 */
	public void addIdentifiers(List<Identifier> identifiers) {
		if (this.deformationModelIdentifiers == null) {
			this.deformationModelIdentifiers = new ArrayList<>();
		}
		this.deformationModelIdentifiers.addAll(identifiers);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((deformationModelIdentifiers == null) ? 0
				: deformationModelIdentifiers.hashCode());
		result = prime * result + ((deformationModelName == null) ? 0
				: deformationModelName.hashCode());
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
		if (deformationModelIdentifiers == null) {
			if (other.deformationModelIdentifiers != null)
				return false;
		} else if (!deformationModelIdentifiers
				.equals(other.deformationModelIdentifiers))
			return false;
		if (deformationModelName == null) {
			if (other.deformationModelName != null)
				return false;
		} else if (!deformationModelName.equals(other.deformationModelName))
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
