package mil.nga.proj.crs;

/**
 * Identifier (Authority)
 * 
 * @author osbornb
 *
 */
public class Identifier {

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
	 * Set the id uri
	 * 
	 * @param uri
	 *            id uri
	 */
	public void setUri(String uri) {
		this.uri = uri;
	}

}
