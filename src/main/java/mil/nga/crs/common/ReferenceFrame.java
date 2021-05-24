package mil.nga.crs.common;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import mil.nga.crs.CRSType;
import mil.nga.crs.wkt.CRSWriter;

/**
 * Reference Frame (datum)
 * 
 * @author osbornb
 */
public abstract class ReferenceFrame implements Identifiable {

	/**
	 * Logger
	 */
	private static final Logger logger = Logger
			.getLogger(ReferenceFrame.class.getName());

	/**
	 * Datum Name
	 */
	private String name = null;

	/**
	 * Type
	 */
	private CRSType type = null;

	/**
	 * Datum anchor description
	 */
	private String anchor = null;

	/**
	 * Identifiers
	 */
	private List<Identifier> identifiers = null;

	/**
	 * Constructor
	 * 
	 * @param type
	 *            type
	 */
	public ReferenceFrame(CRSType type) {
		setType(type);
	}

	/**
	 * Constructor
	 * 
	 * @param name
	 *            name
	 * @param type
	 *            type
	 */
	public ReferenceFrame(String name, CRSType type) {
		setName(name);
		setType(type);
	}

	/**
	 * Get the datum name
	 * 
	 * @return datum name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Set the datum name
	 * 
	 * @param name
	 *            datum name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Get the type
	 * 
	 * @return type
	 */
	public CRSType getType() {
		return type;
	}

	/**
	 * Set the type
	 * 
	 * @param type
	 *            type
	 */
	public void setType(CRSType type) {
		this.type = type;
	}

	/**
	 * Get the datum anchor description
	 * 
	 * @return datum anchor description
	 */
	public String getAnchor() {
		return anchor;
	}

	/**
	 * Has a datum anchor description
	 * 
	 * @return true if has datum anchor description
	 */
	public boolean hasAnchor() {
		return getAnchor() != null;
	}

	/**
	 * Set the datum anchor description
	 * 
	 * @param anchor
	 *            datum anchor description
	 */
	public void setAnchor(String anchor) {
		this.anchor = anchor;
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
		result = prime * result + ((anchor == null) ? 0 : anchor.hashCode());
		result = prime * result
				+ ((identifiers == null) ? 0 : identifiers.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
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
		ReferenceFrame other = (ReferenceFrame) obj;
		if (anchor == null) {
			if (other.anchor != null)
				return false;
		} else if (!anchor.equals(other.anchor))
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
		if (type != other.type)
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
					"Failed to write reference frame as a string", e);
			value = super.toString();
		} finally {
			writer.close();
		}
		return value;
	}

}
