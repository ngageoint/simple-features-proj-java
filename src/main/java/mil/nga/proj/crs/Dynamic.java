package mil.nga.proj.crs;

import java.util.ArrayList;
import java.util.List;

/**
 * Dynamic Coordinate Reference System
 * 
 * @author osbornb
 */
public class Dynamic {

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

}
