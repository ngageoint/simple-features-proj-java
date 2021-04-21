package mil.nga.proj.crs.common;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import mil.nga.proj.crs.wkt.CRSWriter;

/**
 * Reference Frame (datum)
 * 
 * @author osbornb
 */
public abstract class ReferenceFrame {

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
	 * Datum anchor description
	 */
	private String anchor = null;

	/**
	 * Identifiers
	 */
	private List<Identifier> identifiers = null;

	/**
	 * Constructor
	 */
	public ReferenceFrame() {

	}

	/**
	 * Constructor
	 * 
	 * @param name
	 *            name
	 */
	public ReferenceFrame(String name) {
		setName(name);
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
		result = prime * result + ((anchor == null) ? 0 : anchor.hashCode());
		result = prime * result
				+ ((identifiers == null) ? 0 : identifiers.hashCode());
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
