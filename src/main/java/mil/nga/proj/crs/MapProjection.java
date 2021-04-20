package mil.nga.proj.crs;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import mil.nga.proj.crs.wkt.CRSWriter;

/**
 * Map Projection
 * 
 * @author osbornb
 */
public class MapProjection {

	/**
	 * Logger
	 */
	private static final Logger logger = Logger
			.getLogger(MapProjection.class.getName());

	/**
	 * Name
	 */
	private String name = null;

	/**
	 * Method Name
	 */
	private String methodName = null;

	/**
	 * Method Identifiers
	 */
	private List<Identifier> methodIdentifiers = null;

	/**
	 * Parameters
	 */
	private List<MapProjectionParameter> parameters = null;

	/**
	 * Identifiers
	 */
	private List<Identifier> identifiers = null;

	/**
	 * Constructor
	 */
	public MapProjection() {

	}

	/**
	 * Constructor
	 * 
	 * @param name
	 *            name
	 * @param methodName
	 *            method name
	 */
	public MapProjection(String name, String methodName) {
		setName(name);
		setMethodName(methodName);
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
	 * Get the method name
	 * 
	 * @return method name
	 */
	public String getMethodName() {
		return methodName;
	}

	/**
	 * Set the method name
	 * 
	 * @param methodName
	 *            method name
	 */
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	/**
	 * Get the method identifiers
	 * 
	 * @return method identifiers
	 */
	public List<Identifier> getMethodIdentifiers() {
		return methodIdentifiers;
	}

	/**
	 * Has method identifiers
	 * 
	 * @return true if has method identifiers
	 */
	public boolean hasMethodIdentifiers() {
		return methodIdentifiers != null && !methodIdentifiers.isEmpty();
	}

	/**
	 * Set the method identifiers
	 * 
	 * @param methodIdentifiers
	 *            method identifiers
	 */
	public void setMethodIdentifiers(List<Identifier> methodIdentifiers) {
		this.methodIdentifiers = methodIdentifiers;
	}

	/**
	 * Add the method identifier
	 * 
	 * @param methodIdentifier
	 *            method identifier
	 */
	public void addMethodIdentifier(Identifier methodIdentifier) {
		if (this.methodIdentifiers == null) {
			this.methodIdentifiers = new ArrayList<>();
		}
		this.methodIdentifiers.add(methodIdentifier);
	}

	/**
	 * Add the method identifiers
	 * 
	 * @param methodIdentifiers
	 *            method identifiers
	 */
	public void addMethodIdentifiers(List<Identifier> methodIdentifiers) {
		if (this.methodIdentifiers == null) {
			this.methodIdentifiers = new ArrayList<>();
		}
		this.methodIdentifiers.addAll(methodIdentifiers);
	}

	/**
	 * Get the parameters
	 * 
	 * @return parameters
	 */
	public List<MapProjectionParameter> getParameters() {
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
	public void setParameters(List<MapProjectionParameter> parameters) {
		this.parameters = parameters;
	}

	/**
	 * Add the parameter
	 * 
	 * @param parameter
	 *            parameter
	 */
	public void addParameter(MapProjectionParameter parameter) {
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
	public void addParameters(List<MapProjectionParameter> parameters) {
		if (this.parameters == null) {
			this.parameters = new ArrayList<>();
		}
		this.parameters.addAll(parameters);
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
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((identifiers == null) ? 0 : identifiers.hashCode());
		result = prime * result + ((methodIdentifiers == null) ? 0
				: methodIdentifiers.hashCode());
		result = prime * result
				+ ((methodName == null) ? 0 : methodName.hashCode());
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
		MapProjection other = (MapProjection) obj;
		if (identifiers == null) {
			if (other.identifiers != null)
				return false;
		} else if (!identifiers.equals(other.identifiers))
			return false;
		if (methodIdentifiers == null) {
			if (other.methodIdentifiers != null)
				return false;
		} else if (!methodIdentifiers.equals(other.methodIdentifiers))
			return false;
		if (methodName == null) {
			if (other.methodName != null)
				return false;
		} else if (!methodName.equals(other.methodName))
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
					"Failed to write map projection as a string", e);
			value = super.toString();
		} finally {
			writer.close();
		}
		return value;
	}

}
