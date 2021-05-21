package mil.nga.proj.crs.bound;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import mil.nga.proj.crs.common.Identifier;
import mil.nga.proj.crs.common.ScopeExtentIdentifierRemark;
import mil.nga.proj.crs.common.Usage;
import mil.nga.proj.crs.operation.CommonOperation;
import mil.nga.proj.crs.operation.OperationMethod;
import mil.nga.proj.crs.operation.OperationType;
import mil.nga.proj.crs.operation.Parameter;
import mil.nga.proj.crs.wkt.CRSWriter;

/**
 * Abridged Coordinate Transformation
 * 
 * @author osbornb
 */
public class AbridgedCoordinateTransformation
		implements CommonOperation, ScopeExtentIdentifierRemark {

	/**
	 * Logger
	 */
	private static final Logger logger = Logger
			.getLogger(AbridgedCoordinateTransformation.class.getName());

	/**
	 * Name
	 */
	private String name = null;

	/**
	 * Operation Version
	 */
	private String version = null;

	/**
	 * Operation Method
	 */
	private OperationMethod method = null;

	/**
	 * Coordinate operation parameters and parameter files
	 */
	private List<Parameter> parameters = null;

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
	public AbridgedCoordinateTransformation() {

	}

	/**
	 * Constructor
	 * 
	 * @param name
	 *            operation name
	 * @param method
	 *            operation method
	 */
	public AbridgedCoordinateTransformation(String name,
			OperationMethod method) {
		setName(name);
		setMethod(method);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public OperationType getOperationType() {
		return OperationType.ABRIDGED_COORDINATE_TRANSFORMATION;
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
		return version;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean hasVersion() {
		return getVersion() != null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setVersion(String version) {
		this.version = version;
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
	public List<Usage> getUsages() {
		return usages;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean hasUsages() {
		return usages != null && !usages.isEmpty();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int numUsages() {
		return usages != null ? usages.size() : 0;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Usage getUsage(int index) {
		return usages.get(index);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setUsages(List<Usage> usages) {
		this.usages = usages;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addUsage(Usage usage) {
		if (this.usages == null) {
			this.usages = new ArrayList<>();
		}
		this.usages.add(usage);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addUsages(List<Usage> usages) {
		if (this.usages == null) {
			this.usages = new ArrayList<>();
		}
		this.usages.addAll(usages);
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
	public String getRemark() {
		return remark;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean hasRemark() {
		return getRemark() != null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
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
		result = prime * result
				+ ((identifiers == null) ? 0 : identifiers.hashCode());
		result = prime * result + ((method == null) ? 0 : method.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result
				+ ((parameters == null) ? 0 : parameters.hashCode());
		result = prime * result + ((remark == null) ? 0 : remark.hashCode());
		result = prime * result + ((usages == null) ? 0 : usages.hashCode());
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
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AbridgedCoordinateTransformation other = (AbridgedCoordinateTransformation) obj;
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
		if (remark == null) {
			if (other.remark != null)
				return false;
		} else if (!remark.equals(other.remark))
			return false;
		if (usages == null) {
			if (other.usages != null)
				return false;
		} else if (!usages.equals(other.usages))
			return false;
		if (version == null) {
			if (other.version != null)
				return false;
		} else if (!version.equals(other.version))
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
					"Failed to write abridged coordinate transformation as a string",
					e);
			value = super.toString();
		} finally {
			writer.close();
		}
		return value;
	}

}
