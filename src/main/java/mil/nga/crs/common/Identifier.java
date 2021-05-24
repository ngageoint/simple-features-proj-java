package mil.nga.crs.common;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import mil.nga.crs.wkt.CRSWriter;

/**
 * Identifier (Authority)
 * 
 * @author osbornb
 *
 */
public class Identifier {

	/**
	 * Logger
	 */
	private static final Logger logger = Logger
			.getLogger(Identifier.class.getName());

	/**
	 * Authority Name
	 */
	private String name = null;

	/**
	 * Authority Unique Identifier
	 */
	private String uniqueIdentifier = null;

	/**
	 * Version
	 */
	private String version = null;

	/**
	 * Authority Citation
	 */
	private String citation = null;

	/**
	 * URI
	 */
	private String uri = null;

	/**
	 * Constructor
	 */
	public Identifier() {

	}

	/**
	 * Constructor
	 * 
	 * @param name
	 *            authority name
	 * @param uniqueIdentifier
	 *            authority unique identifier
	 */
	public Identifier(String name, String uniqueIdentifier) {
		setName(name);
		setUniqueIdentifier(uniqueIdentifier);
	}

	/**
	 * Get the authority name
	 * 
	 * @return authority name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Set the authority name
	 * 
	 * @param name
	 *            authority name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Get the authority unique identifier
	 * 
	 * @return authority unique identifier
	 */
	public String getUniqueIdentifier() {
		return uniqueIdentifier;
	}

	/**
	 * Set the authority unique identifier
	 * 
	 * @param uniqueIdentifier
	 *            authority unique identifier
	 */
	public void setUniqueIdentifier(String uniqueIdentifier) {
		this.uniqueIdentifier = uniqueIdentifier;
	}

	/**
	 * Get the version
	 * 
	 * @return version
	 */
	public String getVersion() {
		return version;
	}

	/**
	 * Has a version
	 * 
	 * @return true if has version
	 */
	public boolean hasVersion() {
		return getVersion() != null;
	}

	/**
	 * Set the version
	 * 
	 * @param version
	 *            version
	 */
	public void setVersion(String version) {
		this.version = version;
	}

	/**
	 * Get the authority citation
	 * 
	 * @return authority citation
	 */
	public String getCitation() {
		return citation;
	}

	/**
	 * Has an authority citation
	 * 
	 * @return true if has authority citation
	 */
	public boolean hasCitation() {
		return getCitation() != null;
	}

	/**
	 * Set the authority citation
	 * 
	 * @param citation
	 *            authority citation
	 */
	public void setCitation(String citation) {
		this.citation = citation;
	}

	/**
	 * Get the id uri
	 * 
	 * @return id uri
	 */
	public String getUri() {
		return uri;
	}

	/**
	 * Has a, id uri
	 * 
	 * @return true if has id uri
	 */
	public boolean hasUri() {
		return getUri() != null;
	}

	/**
	 * Set the id uri
	 * 
	 * @param uri
	 *            id uri
	 */
	public void setUri(String uri) {
		this.uri = uri;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((citation == null) ? 0 : citation.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((uniqueIdentifier == null) ? 0
				: uniqueIdentifier.hashCode());
		result = prime * result + ((uri == null) ? 0 : uri.hashCode());
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
		Identifier other = (Identifier) obj;
		if (citation == null) {
			if (other.citation != null)
				return false;
		} else if (!citation.equals(other.citation))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (uniqueIdentifier == null) {
			if (other.uniqueIdentifier != null)
				return false;
		} else if (!uniqueIdentifier.equals(other.uniqueIdentifier))
			return false;
		if (uri == null) {
			if (other.uri != null)
				return false;
		} else if (!uri.equals(other.uri))
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
			logger.log(Level.WARNING, "Failed to write identifier as a string",
					e);
			value = super.toString();
		} finally {
			writer.close();
		}
		return value;
	}

}
