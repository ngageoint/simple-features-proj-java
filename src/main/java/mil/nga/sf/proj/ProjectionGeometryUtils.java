package mil.nga.sf.proj;

import java.util.List;

import org.locationtech.proj4j.units.Units;

import mil.nga.proj.Projection;
import mil.nga.proj.ProjectionConstants;
import mil.nga.proj.ProjectionFactory;
import mil.nga.sf.GeometryEnvelope;
import mil.nga.sf.Point;
import mil.nga.sf.util.GeometryUtils;

/**
 * Projection Geometry Utilities
 * 
 * @author osbornb
 * @since 4.3.2
 */
public class ProjectionGeometryUtils {

	/**
	 * WGS84 Projection
	 */
	private static final Projection WGS_84_PROJECTION = ProjectionFactory
			.getProjection(ProjectionConstants.EPSG_WORLD_GEODETIC_SYSTEM);

	/**
	 * Create a geodesic path of a points in the projection with a max distance
	 * between any two path points
	 * 
	 * @param points
	 *            points in the projection
	 * @param maxDistance
	 *            max distance allowed between path points
	 * @param projection
	 *            projection of the points
	 * @return geodesic path of points
	 */
	public static List<Point> geodesicPath(List<Point> points,
			double maxDistance, Projection projection) {

		List<Point> geodesicPath = points;

		if (projection != null) {

			// Reproject to WGS84 if not in degrees
			if (!projection.isUnit(Units.DEGREES)) {
				GeometryTransform toWGS84 = GeometryTransform.create(projection,
						WGS_84_PROJECTION);
				geodesicPath = toWGS84.transform(geodesicPath);
			}

			// Create the geodesic path
			geodesicPath = GeometryUtils.geodesicPath(geodesicPath,
					maxDistance);

			// Reproject back to the original projection
			if (!projection.isUnit(Units.DEGREES)) {
				GeometryTransform fromWGS84 = GeometryTransform
						.create(WGS_84_PROJECTION, projection);
				geodesicPath = fromWGS84.transform(geodesicPath);
			}

		}

		return geodesicPath;
	}

	/**
	 * Expand the vertical bounds of a geometry envelope in the projection by
	 * including geodesic bounds
	 * 
	 * @param envelope
	 *            geometry envelope
	 * @param projection
	 *            projection of the envelope
	 * @return geodesic expanded geometry envelope
	 */
	public static GeometryEnvelope geodesicEnvelope(GeometryEnvelope envelope,
			Projection projection) {

		GeometryEnvelope geodesic = envelope;

		if (projection != null) {

			// Reproject to WGS84 if not in degrees
			if (!projection.isUnit(Units.DEGREES)) {
				GeometryTransform toWGS84 = GeometryTransform.create(projection,
						WGS_84_PROJECTION);
				geodesic = toWGS84.transform(geodesic);
			}

			// Expand the envelope for geodesic lines
			geodesic = GeometryUtils.geodesicEnvelope(geodesic);

			// Reproject back to the original projection
			if (!projection.isUnit(Units.DEGREES)) {
				GeometryTransform fromWGS84 = GeometryTransform
						.create(WGS_84_PROJECTION, projection);
				geodesic = fromWGS84.transform(geodesic);
			}

		}

		return geodesic;
	}

}