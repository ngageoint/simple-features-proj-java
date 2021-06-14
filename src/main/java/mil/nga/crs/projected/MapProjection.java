package mil.nga.crs.projected;

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
import mil.nga.crs.wkt.CRSWriter;

/**
 * Map Projection
 * 
 * @author osbornb
 */
public class MapProjection implements CommonOperation {

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
	 * Method
	 */
	private OperationMethod method = null;

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
	 * @param method
	 *            method
	 */
	public MapProjection(String name, OperationMethod method) {
		setName(name);
		setMethod(method);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public OperationType getOperationType() {
		return OperationType.MAP_PROJECTION;
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
		throw new CRSException("Map Projection does not support version");
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
