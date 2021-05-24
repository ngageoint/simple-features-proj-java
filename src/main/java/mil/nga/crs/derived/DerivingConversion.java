package mil.nga.crs.derived;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import mil.nga.crs.CRSException;
import mil.nga.crs.common.Identifier;
import mil.nga.crs.operation.CommonOperation;
import mil.nga.crs.operation.OperationMethod;
import mil.nga.crs.operation.OperationType;
import mil.nga.crs.operation.Parameter;
import mil.nga.crs.wkt.CRSWriter;

/**
 * Deriving Conversion
 * 
 * @author osbornb
 */
public class DerivingConversion implements CommonOperation {

	/**
	 * Logger
	 */
	private static final Logger logger = Logger
			.getLogger(DerivingConversion.class.getName());

	/**
	 * Name
	 */
	private String name = null;

	/**
	 * Method
	 */
	private OperationMethod method = null;

	/**
	 * Parameters
	 */
	private List<Parameter> parameters = null;

	/**
	 * Identifiers
	 */
	private List<Identifier> identifiers = null;

	/**
	 * Constructor
	 */
	public DerivingConversion() {

	}

	/**
	 * Constructor
	 * 
	 * @param name
	 *            name
	 * @param method
	 *            method
	 */
	public DerivingConversion(String name, OperationMethod method) {
		setName(name);
		setMethod(method);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public OperationType getOperationType() {
		return OperationType.DERIVING_CONVERSION;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getName() {
		return name;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getVersion() {
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean hasVersion() {
		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setVersion(String version) {
		throw new CRSException("Deriving Conversion does not support version");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public OperationMethod getMethod() {
		return method;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setMethod(OperationMethod method) {
		this.method = method;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Parameter> getParameters() {
		return parameters;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean hasParameters() {
		return parameters != null && !parameters.isEmpty();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int numParameters() {
		return parameters != null ? parameters.size() : 0;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Parameter getParameter(int index) {
		return parameters.get(index);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setParameters(List<Parameter> parameters) {
		this.parameters = parameters;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addParameter(Parameter parameter) {
		if (this.parameters == null) {
			this.parameters = new ArrayList<>();
		}
		this.parameters.add(parameter);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addParameters(List<Parameter> parameters) {
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
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((identifiers == null) ? 0 : identifiers.hashCode());
		result = prime * result + ((method == null) ? 0 : method.hashCode());
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
		DerivingConversion other = (DerivingConversion) obj;
		if (identifiers == null) {
			if (other.identifiers != null)
				return false;
		} else if (!identifiers.equals(other.identifiers))
			return false;
		if (method == null) {
			if (other.method != null)
				return false;
		} else if (!method.equals(other.method))
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
			logger.log(Level.WARNING,
					"Failed to write deriving conversion as a string", e);
			value = super.toString();
		} finally {
			writer.close();
		}
		return value;
	}

}
