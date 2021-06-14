package mil.nga.crs.operation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import mil.nga.crs.common.Identifiable;
import mil.nga.crs.common.Identifier;
import mil.nga.crs.wkt.CRSWriter;

/**
 * Operation Method
 * 
 * @author osbornb
 */
public class OperationMethod implements Identifiable {

	/**
	 * Logger
	 */
	private static final Logger logger = Logger
			.getLogger(OperationMethod.class.getName());

	/**
	 * Name
	 */
	private String name = null;

	/**
	 * Operation parameters
	 */
	private List<OperationParameter> parameters = null;

	/**
	 * Identifiers
	 */
	private List<Identifier> identifiers = null;

	/**
	 * Commonly encountered operation methods
	 */
	private OperationMethods method;

	/**
	 * Constructor
	 */
	public OperationMethod() {

	}

	/**
	 * Constructor
	 * 
	 * @param name
	 *            name
	 */
	public OperationMethod(String name) {
		this.name = name;
		updateMethod();
	}

	/**
	 * Constructor
	 * 
	 * @param method
	 *            operation method
	 */
	public OperationMethod(OperationMethods method) {
		this.name = method.getName();
		this.method = method;
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
		updateMethod();
	}

	/**
	 * Get the parameters
	 * 
	 * @return parameters
	 */
	public List<OperationParameter> getParameters() {
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
	 * Number of parameters
	 * 
	 * @return parameters count
	 */
	public int numParameters() {
		return parameters != null ? parameters.size() : 0;
	}

	/**
	 * Get the parameter at the index
	 * 
	 * @param index
	 *            parameter index
	 * @return parameter
	 */
	public OperationParameter getParameter(int index) {
		return parameters.get(index);
	}

	/**
	 * Set the parameters
	 * 
	 * @param parameters
	 *            parameters
	 */
	public void setParameters(List<OperationParameter> parameters) {
		this.parameters = parameters;
	}

	/**
	 * Add the parameter
	 * 
	 * @param parameter
	 *            parameter
	 */
	public void addParameter(OperationParameter parameter) {
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
	public void addParameters(List<OperationParameter> parameters) {
		if (this.parameters == null) {
			this.parameters = new ArrayList<>();
		}
		this.parameters.addAll(parameters);
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
	 * Get the commonly known method type
	 * 
	 * @return method type or null
	 */
	public OperationMethods getMethod() {
		return method;
	}

	/**
	 * Is a commonly known method type
	 * 
	 * @return true if has common method type
	 */
	public boolean hasMethod() {
		return getMethod() != null;
	}

	/**
	 * Set the commonly known method type
	 * 
	 * @param method
	 *            method type or null
	 */
	public void setMethod(OperationMethods method) {
		this.method = method;
	}

	/**
	 * Update the commonly known method type using the name
	 */
	public void updateMethod() {
		setMethod(OperationMethods.getMethod(getName()));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((identifiers == null) ? 0 : identifiers.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result
				+ ((parameters == null) ? 0 : parameters.hashCode());
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
		OperationMethod other = (OperationMethod) obj;
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
		if (parameters == null) {
			if (other.parameters != null)
				return false;
		} else if (!parameters.equals(other.parameters))
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
			logger.log(Level.WARNING, "Failed to write method as a string", e);
			value = super.toString();
		} finally {
			writer.close();
		}
		return value;
	}

}
