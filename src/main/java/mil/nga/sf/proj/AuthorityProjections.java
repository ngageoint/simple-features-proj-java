package mil.nga.sf.proj;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Collection of projections for a single coordinate authority
 * 
 * @author osbornb
 * @since 1.3.0
 */
public class AuthorityProjections {

	/**
	 * Coordinate authority
	 */
	private final String authority;

	/**
	 * Projections by code
	 */
	private Map<String, Projection> projections = new HashMap<>();

	/**
	 * Constructor
	 * 
	 * @param authority
	 *            coordinate authority
	 */
	public AuthorityProjections(String authority) {
		this.authority = authority;
	}

	/**
	 * Get the authority
	 * 
	 * @return authority
	 */
	public String getAuthority() {
		return authority;
	}

	/**
	 * Get the projection for the code
	 * 
	 * @param code
	 *            coordinate code
	 * @return projection
	 * @since 3.0.1
	 */
	public Projection getProjection(long code) {
		return getProjection(String.valueOf(code));
	}

	/**
	 * Get the projection for the code
	 * 
	 * @param code
	 *            coordinate code
	 * @return projection
	 */
	public Projection getProjection(String code) {
		return projections.get(code.toUpperCase());
	}

	/**
	 * Check if the collection contains the projection
	 * 
	 * @param projection
	 *            projection
	 * @return true if has the projection
	 * @since 3.0.1
	 */
	public boolean hasProjection(Projection projection) {
		return hasProjection(projection.getCode());
	}

	/**
	 * Check if the collection contains a projection for the code
	 * 
	 * @param code
	 *            coordinate code
	 * @return true if has a projection
	 * @since 3.0.1
	 */
	public boolean hasProjection(long code) {
		return hasProjection(String.valueOf(code));
	}

	/**
	 * Check if the collection contains a projection for the code
	 * 
	 * @param code
	 *            coordinate code
	 * @return true if has a projection
	 * @since 3.0.1
	 */
	public boolean hasProjection(String code) {
		return getProjection(code) != null;
	}

	/**
	 * Add the projection to the authority
	 * 
	 * @param projection
	 *            projection
	 */
	public void addProjection(Projection projection) {
		projections.put(projection.getCode().toUpperCase(), projection);
	}

	/**
	 * Clear all projections for the authority
	 */
	public void clear() {
		projections.clear();
	}

	/**
	 * Remove the projection with the code
	 * 
	 * @param code
	 *            coordinate code
	 * @since 3.0.1
	 */
	public void remove(long code) {
		remove(String.valueOf(code));
	}

	/**
	 * Remove the projection with the code
	 * 
	 * @param code
	 *            coordinate code
	 * @since 3.0.1
	 */
	public void remove(String code) {
		projections.remove(code.toUpperCase());
	}

	/**
	 * Remove the projection
	 * 
	 * @param projection
	 *            projection
	 * @since 3.0.1
	 */
	public void remove(Projection projection) {
		remove(projection.getCode());
	}

	/**
	 * Get the count of authority projections
	 * 
	 * @return count
	 * @since 3.0.1
	 */
	public int count() {
		return projections.size();
	}

	/**
	 * Is the collection empty?
	 * 
	 * @return true if empty
	 * @since 3.0.1
	 */
	public boolean isEmpty() {
		return projections.isEmpty();
	}

	/**
	 * Get the projection codes
	 * 
	 * @return projection codes
	 * @since 3.0.1
	 */
	public Set<String> getCodes() {
		return projections.keySet();
	}

	/**
	 * Get the projections
	 * 
	 * @return projections
	 * @since 3.0.1
	 */
	public Collection<Projection> getProjections() {
		return projections.values();
	}

}
