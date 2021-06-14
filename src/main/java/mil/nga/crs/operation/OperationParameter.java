package mil.nga.crs.operation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import mil.nga.crs.common.Identifiable;
import mil.nga.crs.common.Identifier;
import mil.nga.crs.common.Unit;
import mil.nga.crs.wkt.CRSWriter;

/**
 * Operation Parameter
 * 
 * @author osbornb
 */
public class OperationParameter implements Identifiable {

	/**
	 * Logger
	 */
	private static final Logger logger = Logger
			.getLogger(OperationParameter.class.getName());

	/**
	 * Name
	 */
	private String name = null;

	/**
	 * Value
	 */
	private double value;

	/**
	 * Unit
	 */
	private Unit unit;

	/**
	 * Name
	 */
	private String fileName = null;

	/**
	 * Identifiers
	 */
	private List<Identifier> identifiers = null;

	/**
	 * Commonly encountered operation parameters
	 */
	private OperationParameters parameter;

	/**
	 * Constructor
	 */
	public OperationParameter() {

	}

	/**
	 * Constructor
	 * 
	 * @param name
	 *            name
	 * @param value
	 *            value
	 */
	public OperationParameter(String name, double value) {
		this.name = name;
		this.value = value;
		updateParameter();
	}

	/**
	 * Constructor
	 * 
	 * @param parameter
	 *            operation parameter
	 * @param value
	 *            value
	 */
	public OperationParameter(OperationParameters parameter, double value) {
		this.name = parameter.getName();
		this.value = value;
		this.parameter = parameter;
	}

	/**
	 * Constructor
	 * 
	 * @param name
	 *            name
	 * @param fileName
	 *            file name
	 */
	public OperationParameter(String name, String fileName) {
		this.name = name;
		this.fileName = fileName;
		updateParameter();
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
		updateParameter();
	}

	/**
	 * Get the value
	 * 
	 * @return value
	 */
	public double getValue() {
		return value;
	}

	/**
	 * Set the value
	 * 
	 * @param value
	 *            value
	 */
	public void setValue(double value) {
		this.value = value;
	}

	/**
	 * Get the unit
	 * 
	 * @return unit
	 */
	public Unit getUnit() {
		return unit;
	}

	/**
	 * Has a unit
	 * 
	 * @return true if has unit
	 */
	public boolean hasUnit() {
		return getUnit() != null;
	}

	/**
	 * Set the unit
	 * 
	 * @param unit
	 *            unit
	 */
	public void setUnit(Unit unit) {
		this.unit = unit;
	}

	/**
	 * Get the file name
	 * 
	 * @return file name
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * Is a parameter file
	 * 
	 * @return true if file
	 */
	public boolean isFile() {
		return getFileName() != null;
	}

	/**
	 * Set the file name
	 * 
	 * @param fileName
	 *            file name
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
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
	 * Get the commonly known parameter type
	 * 
	 * @return parameter type or null
	 */
	public OperationParameters getParameter() {
		return parameter;
	}

	/**
	 * Is a commonly known parameter type
	 * 
	 * @return true if has common parameter type
	 */
	public boolean hasParameter() {
		return getParameter() != null;
	}

	/**
	 * Set the commonly known parameter type
	 * 
	 * @param parameter
	 *            parameter type or null
	 */
	public void setParameter(OperationParameters parameter) {
		this.parameter = parameter;
	}

	/**
	 * Update the commonly known parameter type using the name
	 */
	public void updateParameter() {
		setParameter(OperationParameters.getParameter(getName()));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((fileName == null) ? 0 : fileName.hashCode());
		result = prime * result
				+ ((identifiers == null) ? 0 : identifiers.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result
				+ ((parameter == null) ? 0 : parameter.hashCode());
		result = prime * result + ((unit == null) ? 0 : unit.hashCode());
		long temp;
		temp = Double.doubleToLongBits(value);
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
		OperationParameter other = (OperationParameter) obj;
		if (fileName == null) {
			if (other.fileName != null)
				return false;
		} else if (!fileName.equals(other.fileName))
			return false;
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
		if (parameter != other.parameter)
			return false;
		if (unit == null) {
			if (other.unit != null)
				return false;
		} else if (!unit.equals(other.unit))
			return false;
		if (Double.doubleToLongBits(value) != Double
				.doubleToLongBits(other.value))
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
			logger.log(Level.WARNING, "Failed to write parameter as a string",
					e);
			value = super.toString();
		} finally {
			writer.close();
		}
		return value;
	}

}
