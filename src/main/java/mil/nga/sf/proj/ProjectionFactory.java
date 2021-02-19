package mil.nga.sf.proj;

import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.locationtech.proj4j.CRSFactory;
import org.locationtech.proj4j.CoordinateReferenceSystem;

import mil.nga.sf.util.SFException;

/**
 * Projection factory for coordinate projections and transformations
 * 
 * @author osbornb
 */
public class ProjectionFactory {

	/**
	 * Logger
	 */
	private static final Logger logger = Logger
			.getLogger(ProjectionFactory.class.getName());

	/**
	 * CRS Factory
	 */
	private static final CRSFactory csFactory = new CRSFactory();

	/**
	 * Projections
	 */
	private static Projections projections = new Projections();

	/**
	 * Get the projection for the EPSG code
	 * 
	 * @param epsg
	 *            EPSG coordinate code
	 * @return projection
	 */
	public static Projection getProjection(long epsg) {
		return getProjection(ProjectionConstants.AUTHORITY_EPSG,
				String.valueOf(epsg));
	}

	/**
	 * Get the projection for the projection name, expected as 'authority:code'
	 * or 'epsg_code'
	 * 
	 * @param name
	 *            projection name
	 * @return projection
	 * @since 3.0.4
	 */
	public static Projection getProjection(String name) {

		String authority = null;
		String code = null;

		String[] projectionParts = name.split(":");

		switch (projectionParts.length) {
		case 1:
			authority = ProjectionConstants.AUTHORITY_EPSG;
			code = projectionParts[0];
			break;
		case 2:
			authority = projectionParts[0];
			code = projectionParts[1];
			break;
		default:
			throw new SFException("Invalid projection name '" + name
					+ "', expected 'authority:code' or 'epsg_code'");
		}

		return getProjection(authority, code);
	}

	/**
	 * Get the projection for authority and code
	 * 
	 * @param authority
	 *            coordinate authority
	 * @param code
	 *            authority coordinate code
	 * @return projection
	 * @since 1.3.0
	 */
	public static Projection getProjection(String authority, long code) {
		return getProjection(authority, String.valueOf(code));
	}

	/**
	 * Get the projection for authority and code
	 * 
	 * @param authority
	 *            coordinate authority
	 * @param code
	 *            authority coordinate code
	 * @return projection
	 * @since 1.3.0
	 */
	public static Projection getProjection(String authority, String code) {
		return getProjection(authority, code, null, null);
	}

	/**
	 * Get the projection for authority, code, and parameter string
	 * 
	 * @param authority
	 *            coordinate authority
	 * @param code
	 *            authority coordinate code
	 * @param paramStr
	 *            proj4 string
	 * @return projection
	 * @since 1.3.0
	 */
	public static Projection getProjection(String authority, long code,
			String paramStr) {
		return getProjection(authority, String.valueOf(code), paramStr);
	}

	/**
	 * Get the projection for authority, code, and parameter string
	 * 
	 * @param authority
	 *            coordinate authority
	 * @param code
	 *            authority coordinate code
	 * @param paramStr
	 *            proj4 string
	 * @return projection
	 * @since 1.3.0
	 */
	public static Projection getProjection(String authority, String code,
			String paramStr) {
		String[] params = null;
		if (paramStr != null && !paramStr.isEmpty()) {
			params = paramStr.split("\\s+");
		}
		Projection projection = getProjection(authority, code, params);
		return projection;
	}

	/**
	 * Get the projection for authority, code, and parameters
	 * 
	 * @param authority
	 *            coordinate authority
	 * @param code
	 *            authority coordinate code
	 * @param params
	 *            proj4 params array
	 * @return projection
	 * @since 1.3.0
	 */
	public static Projection getProjection(String authority, long code,
			String[] params) {
		return getProjection(authority, String.valueOf(code), params);
	}

	/**
	 * Get the projection for authority, code, and parameters
	 * 
	 * @param authority
	 *            coordinate authority
	 * @param code
	 *            authority coordinate code
	 * @param params
	 *            proj4 params array
	 * @return projection
	 * @since 1.3.0
	 */
	public static Projection getProjection(String authority, String code,
			String[] params) {
		return getProjection(authority, code, params, null);
	}

	/**
	 * Get the projection for the authority, code, definition, and custom
	 * parameter array
	 * 
	 * @param authority
	 *            coordinate authority
	 * @param code
	 *            authority coordinate code
	 * @param params
	 *            proj4 params array
	 * @param definition
	 *            definition
	 * @return projection
	 * @since 1.3.0
	 */
	public static Projection getProjection(String authority, long code,
			String[] params, String definition) {
		return getProjection(authority, String.valueOf(code), params,
				definition);
	}

	/**
	 * Get the projection for the authority, code, definition, and custom
	 * parameter array
	 * 
	 * @param authority
	 *            coordinate authority
	 * @param code
	 *            authority coordinate code
	 * @param params
	 *            proj4 params array
	 * @param definition
	 *            definition
	 * @return projection
	 * @since 1.3.0
	 */
	public static Projection getProjection(String authority, String code,
			String[] params, String definition) {

		authority = authority.toUpperCase();

		// Check if the projection already exists
		Projection projection = projections.getProjection(authority, code);

		if (projection == null) {

			// Try to get or create the projection from a definition
			projection = fromDefinition(authority, code, definition);

			if (projection == null) {

				// Try to create the projection from the provided params
				projection = fromParams(authority, code, params);

				if (projection == null) {

					// Try to create the projection from properties
					projection = fromProperties(authority, code);

					if (projection == null) {

						// Try to create the projection from the authority name
						projection = fromName(authority, code);

						if (projection == null) {
							throw new SFException(
									"Failed to create projection for authority: "
											+ authority + ", code: " + code
											+ ", definition: " + definition
											+ ", params: "
											+ Arrays.toString(params));
						}
					}
				}
			}
		}

		return projection;
	}

	/**
	 * Get the projections
	 * 
	 * @return projections
	 * @since 3.0.1
	 */
	public static Projections getProjections() {
		return projections;
	}

	/**
	 * Get the projections for the authority
	 * 
	 * @param authority
	 *            coordinate authority
	 * @return authority projections
	 * @since 1.3.0
	 */
	public static AuthorityProjections getProjections(String authority) {
		return projections.getProjections(authority);
	}

	/**
	 * Clear all authority projections
	 * 
	 * @since 1.3.0
	 */
	public static void clear() {
		projections.clear();
	}

	/**
	 * Clear the authority projections
	 * 
	 * @param authority
	 *            coordinate authority
	 * @since 1.3.0
	 */
	public static void clear(String authority) {
		projections.clear(authority);
	}

	/**
	 * Clear the authority projection code
	 * 
	 * @param authority
	 *            coordinate authority
	 * @param code
	 *            coordinate code
	 * @since 1.3.0
	 */
	public static void clear(String authority, long code) {
		projections.remove(authority, code);
	}

	/**
	 * Clear the authority projection code
	 * 
	 * @param authority
	 *            coordinate authority
	 * @param code
	 *            coordinate code
	 * @since 1.3.0
	 */
	public static void clear(String authority, String code) {
		projections.remove(authority, code);
	}

	/**
	 * Create a projection from the WKT definition
	 * 
	 * @param authority
	 *            coordinate authority
	 * @param code
	 *            coordinate code
	 * @param definition
	 *            WKT coordinate definition
	 * @return projection
	 */
	private static Projection fromDefinition(String authority, String code,
			String definition) {

		Projection projection = null;

		if (definition != null && !definition.isEmpty()) {

			String parametersString = "";
			// TODO parse WKT definition into proj4 parameters

			// Try to create the projection from the parameters
			if (parametersString != null && !parametersString.isEmpty()) {
				try {
					CoordinateReferenceSystem crs = csFactory
							.createFromParameters(
									coordinateName(authority, code),
									parametersString);
					projection = new Projection(authority, code, crs);
					projections.addProjection(projection);
				} catch (Exception e) {
					logger.log(Level.WARNING,
							"Failed to create projection for authority: "
									+ authority + ", code: " + code
									+ ", definition: " + definition
									+ ", parameters: " + parametersString,
							e);
				}
			}

		}

		return projection;
	}

	/**
	 * Create a projection from the proj4 parameters
	 * 
	 * @param authority
	 *            coordinate authority
	 * @param code
	 *            coordinate code
	 * @param params
	 *            proj4 parameters
	 * @return projection
	 */
	private static Projection fromParams(String authority, String code,
			String[] params) {

		Projection projection = null;

		if (params != null && params.length > 0) {
			try {
				CoordinateReferenceSystem crs = csFactory.createFromParameters(
						coordinateName(authority, code), params);
				projection = new Projection(authority, code, crs);
				projections.addProjection(projection);
			} catch (Exception e) {
				logger.log(Level.WARNING,
						"Failed to create projection for authority: "
								+ authority + ", code: " + code
								+ ", parameters: " + Arrays.toString(params),
						e);
			}
		}

		return projection;
	}

	/**
	 * Create a projection from configured coordinate properties
	 * 
	 * @param authority
	 *            coordinate authority
	 * @param code
	 *            coordinate code
	 * @return projection
	 */
	private static Projection fromProperties(String authority, String code) {

		Projection projection = null;

		String parameters = ProjectionRetriever.getProjection(authority, code);

		if (parameters != null && !parameters.isEmpty()) {
			try {
				CoordinateReferenceSystem crs = csFactory.createFromParameters(
						coordinateName(authority, code), parameters);
				projection = new Projection(authority, code, crs);
				projections.addProjection(projection);
			} catch (Exception e) {
				logger.log(Level.WARNING,
						"Failed to create projection for authority: "
								+ authority + ", code: " + code
								+ ", parameters: " + parameters,
						e);
			}
		}

		return projection;
	}

	/**
	 * Create a projection from the coordinate authority and code name
	 * 
	 * @param authority
	 *            coordinate authority
	 * @param code
	 *            coordinate code
	 * @return projection
	 */
	private static Projection fromName(String authority, String code) {

		Projection projection = null;

		String name = coordinateName(authority, code);
		try {
			CoordinateReferenceSystem crs = csFactory.createFromName(name);
			projection = new Projection(authority, code, crs);
			projections.addProjection(projection);
		} catch (Exception e) {
			logger.log(Level.WARNING,
					"Failed to create projection from name: " + name, e);
		}

		return projection;
	}

	/**
	 * Build a coordinate name from the authority and code
	 * 
	 * @param authority
	 *            coordinate authority
	 * @param code
	 *            coordinate code
	 * @return name
	 */
	private static String coordinateName(String authority, String code) {
		return authority.toUpperCase() + ":" + code;
	}

}
