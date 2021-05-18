package mil.nga.proj.crs.operation;

import java.util.ArrayList;
import java.util.List;

import mil.nga.proj.crs.CRSType;
import mil.nga.proj.crs.CommonCRS;
import mil.nga.proj.crs.CoordinateReferenceSystem;

/**
 * Coordinate Operation
 * 
 * @author osbornb
 */
public class CoordinateOperation extends CommonCRS {

	/**
	 * Operation Version
	 */
	private String version = null;

	/**
	 * Source Coordinate Reference System
	 */
	private CoordinateReferenceSystem source = null;

	/**
	 * Target Coordinate Reference System
	 */
	private CoordinateReferenceSystem target = null;

	/**
	 * Operation Method
	 */
	private OperationMethod method = null;

	/**
	 * Coordinate operation parameters and parameter files
	 */
	private List<Parameter> parameters = null;

	/**
	 * Interpolation Coordinate Reference System
	 */
	private CoordinateReferenceSystem interpolation = null;

	/**
	 * Coordinate operation accuracy
	 */
	private Double accuracy;

	/**
	 * Constructor
	 */
	public CoordinateOperation() {
		super(CRSType.COORDINATE_OPERATION);
	}

	/**
	 * Constructor
	 * 
	 * @param name
	 *            name
	 * @param source
	 *            source crs
	 * @param target
	 *            target crs
	 * @param method
	 *            operation method
	 */
	public CoordinateOperation(String name, CoordinateReferenceSystem source,
			CoordinateReferenceSystem target, OperationMethod method) {
		super(name, CRSType.COORDINATE_OPERATION);
		setSource(source);
		setTarget(target);
		setMethod(method);
	}

	/**
	 * Get the operation version
	 * 
	 * @return operation version
	 */
	public String getVersion() {
		return version;
	}

	/**
	 * Has an operation version
	 * 
	 * @return true if has operation version
	 */
	public boolean hasVersion() {
		return getVersion() != null;
	}

	/**
	 * Set the operation version
	 * 
	 * @param version
	 *            operation version
	 */
	public void setVersion(String version) {
		this.version = version;
	}

	/**
	 * Get the source coordinate reference system
	 * 
	 * @return source crs
	 */
	public CoordinateReferenceSystem getSource() {
		return source;
	}

	/**
	 * Set the source coordinate reference system
	 * 
	 * @param source
	 *            source crs
	 */
	public void setSource(CoordinateReferenceSystem source) {
		this.source = source;
	}

	/**
	 * Get the target coordinate reference system
	 * 
	 * @return target crs
	 */
	public CoordinateReferenceSystem getTarget() {
		return target;
	}

	/**
	 * Set the target coordinate reference system
	 * 
	 * @param target
	 *            target crs
	 */
	public void setTarget(CoordinateReferenceSystem target) {
		this.target = target;
	}

	/**
	 * Get the operation method
	 * 
	 * @return operation method
	 */
	public OperationMethod getMethod() {
		return method;
	}

	/**
	 * Set the operation method
	 * 
	 * @param method
	 *            operation method
	 */
	public void setMethod(OperationMethod method) {
		this.method = method;
	}

	/**
	 * Get the parameters
	 * 
	 * @return parameters
	 */
	public List<Parameter> getParameters() {
		return parameters;
	}

	/**
	 * Has parameters
	 * 
	 * @return true if has parameters
	 */
	public boolean hasParameters() {
		return parameters != null && !parameters.isEmpty();
	}

	/**
	 * Set the parameters
	 * 
	 * @param parameters
	 *            parameters
	 */
	public void setParameters(List<Parameter> parameters) {
		this.parameters = parameters;
	}

	/**
	 * Add the parameter
	 * 
	 * @param parameter
	 *            parameter
	 */
	public void addParameter(Parameter parameter) {
		if (this.parameters == null) {
			this.parameters = new ArrayList<>();
		}
		this.parameters.add(parameter);
	}

	/**
	 * Add the parameters
	 * 
	 * @param parameters
	 *            parameters
	 */
	public void addParameters(List<Parameter> parameters) {
		if (this.parameters == null) {
			this.parameters = new ArrayList<>();
		}
		this.parameters.addAll(parameters);
	}

	/**
	 * Get the interpolation coordinate reference system
	 * 
	 * @return interpolation crs
	 */
	public CoordinateReferenceSystem getInterpolation() {
		return interpolation;
	}

	/**
	 * Has an interpolation coordinate reference system
	 * 
	 * @return true if has interpolation crs
	 */
	public boolean hasInterpolation() {
		return getInterpolation() != null;
	}

	/**
	 * Set the interpolation coordinate reference system
	 * 
	 * @param interpolation
	 *            interpolation crs
	 */
	public void setInterpolation(CoordinateReferenceSystem interpolation) {
		this.interpolation = interpolation;
	}

	/**
	 * Get the coordinate operation accuracy
	 * 
	 * @return coordinate operation accuracy
	 */
	public Double getAccuracy() {
		return accuracy;
	}

	/**
	 * Has a coordinate operation accuracy
	 * 
	 * @return true if has coordinate operation accuracy
	 */
	public boolean hasAccuracy() {
		return getAccuracy() != null;
	}

	/**
	 * Set the coordinate operation accuracy
	 * 
	 * @param accuracy
	 *            coordinate operation accuracy
	 */
	public void setAccuracy(Double accuracy) {
		this.accuracy = accuracy;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((accuracy == null) ? 0 : accuracy.hashCode());
		result = prime * result
				+ ((interpolation == null) ? 0 : interpolation.hashCode());
		result = prime * result + ((method == null) ? 0 : method.hashCode());
		result = prime * result
				+ ((parameters == null) ? 0 : parameters.hashCode());
		result = prime * result + ((source == null) ? 0 : source.hashCode());
		result = prime * result + ((target == null) ? 0 : target.hashCode());
		result = prime * result + ((version == null) ? 0 : version.hashCode());
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
		CoordinateOperation other = (CoordinateOperation) obj;
		if (accuracy == null) {
			if (other.accuracy != null)
				return false;
		} else if (!accuracy.equals(other.accuracy))
			return false;
		if (interpolation == null) {
			if (other.interpolation != null)
				return false;
		} else if (!interpolation.equals(other.interpolation))
			return false;
		if (method == null) {
			if (other.method != null)
				return false;
		} else if (!method.equals(other.method))
			return false;
		if (parameters == null) {
			if (other.parameters != null)
				return false;
		} else if (!parameters.equals(other.parameters))
			return false;
		if (source == null) {
			if (other.source != null)
				return false;
		} else if (!source.equals(other.source))
			return false;
		if (target == null) {
			if (other.target != null)
				return false;
		} else if (!target.equals(other.target))
			return false;
		if (version == null) {
			if (other.version != null)
				return false;
		} else if (!version.equals(other.version))
			return false;
		return true;
	}

}
