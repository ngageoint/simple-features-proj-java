package mil.nga.sf.proj;

import java.util.HashMap;
import java.util.Map;

/**
 * Collection of projections by authority
 * 
 * @author osbornb
 * @since 3.0.1
 */
public class Projections {

	/**
	 * Mapping of authorities to authority projections
	 */
	private static Map<String, AuthorityProjections> authorities = new HashMap<>();

	/**
	 * Constructor
	 */
	public Projections() {

	}

	/**
	 * Get the authority projections
	 * 
	 * @param authority
	 *            coordinate authority
	 * @return authority projections
	 */
	public AuthorityProjections getProjections(String authority) {
		return authorities.get(authority.toUpperCase());
	}

	/**
	 * Get the projection for the authority and code
	 * 
	 * @param authority
	 *            coordinate authority
	 * @param code
	 *            coordinate code
	 * @return projection
	 */
	public Projection getProjection(String authority, String code) {
		Projection projection = null;
		AuthorityProjections authorityProjections = getProjections(authority);
		if (authorityProjections != null) {
			projection = authorityProjections.getProjection(code);
		}
		return projection;
	}

	/**
	 * Check if the collection contains the projection
	 * 
	 * @param projection
	 *            projection
	 * @return true if has the projection
	 */
	public boolean hasProjection(Projection projection) {
		return hasProjection(projection.getAuthority(), projection.getCode());
	}

	/**
	 * Check if the collection contains a projection for the authority and code
	 * 
	 * @param authority
	 *            coordinate authority
	 * @param code
	 *            coordinate code
	 * @return true if has a projection
	 */
	public boolean hasProjection(String authority, String code) {
		return getProjection(authority, code) != null;
	}

	/**
	 * Add the projection
	 * 
	 * @param projection
	 *            projection
	 */
	public void addProjection(Projection projection) {
		String authority = projection.getAuthority();
		AuthorityProjections authorityProjections = getProjections(authority);
		if (authorityProjections == null) {
			authorityProjections = new AuthorityProjections(
					authority.toUpperCase());
			authorities.put(authorityProjections.getAuthority(),
					authorityProjections);
		}
		authorityProjections.addProjection(projection);
	}

	/**
	 * Clear all projections
	 */
	public void clear() {
		authorities.clear();
	}

	/**
	 * Clear all projections for the authority
	 * 
	 * @param authority
	 *            coordinate authority
	 */
	public void clear(String authority) {
		authorities.remove(authority.toUpperCase());
	}

	/**
	 * Remove the projection for the authority and code
	 * 
	 * @param authority
	 *            coordinate authority
	 * @param code
	 *            coordinate code
	 */
	public void remove(String authority, long code) {
		remove(authority, String.valueOf(code));
	}

	/**
	 * Remove the projection for the authority and code
	 * 
	 * @param authority
	 *            coordinate authority
	 * @param code
	 *            coordinate code
	 */
	public void remove(String authority, String code) {
		AuthorityProjections authorityProjections = getProjections(authority);
		if (authorityProjections != null) {
			authorityProjections.remove(code);
			if (authorityProjections.isEmpty()) {
				clear(authority);
			}
		}
	}

	/**
	 * Remove the projection
	 * 
	 * @param projection
	 *            projection
	 */
	public void remove(Projection projection) {
		remove(projection.getAuthority(), projection.getCode());
	}

	/**
	 * Get the count of authorities
	 * 
	 * @return count
	 */
	public int authorityCount() {
		return authorities.size();
	}

	/**
	 * Get the count of projections
	 * 
	 * @return count
	 */
	public int projectionCount() {
		int count = 0;
		for (AuthorityProjections authorityProjections : authorities.values()) {
			count += authorityProjections.count();
		}
		return count;
	}

	/**
	 * Is the collection empty?
	 * 
	 * @return true if empty
	 */
	public boolean isEmpty() {
		return authorities.isEmpty();
	}

}
