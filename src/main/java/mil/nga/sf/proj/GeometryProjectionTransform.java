package mil.nga.sf.proj;

import mil.nga.sf.CircularString;
import mil.nga.sf.CompoundCurve;
import mil.nga.sf.Curve;
import mil.nga.sf.CurvePolygon;
import mil.nga.sf.Geometry;
import mil.nga.sf.GeometryCollection;
import mil.nga.sf.GeometryType;
import mil.nga.sf.LineString;
import mil.nga.sf.MultiLineString;
import mil.nga.sf.MultiPoint;
import mil.nga.sf.MultiPolygon;
import mil.nga.sf.Point;
import mil.nga.sf.Polygon;
import mil.nga.sf.PolyhedralSurface;
import mil.nga.sf.TIN;
import mil.nga.sf.Triangle;
import mil.nga.sf.util.SFException;

import org.locationtech.proj4j.ProjCoordinate;

/**
 * Geometry Projection Transform
 * 
 * @author osbornb
 * @since 1.1.3
 */
public class GeometryProjectionTransform {

	/**
	 * Projection transform
	 */
	private final ProjectionTransform transform;

	/**
	 * Constructor
	 * 
	 * @param transform
	 *            projection transform
	 */
	public GeometryProjectionTransform(ProjectionTransform transform) {
		this.transform = transform;
	}

	/**
	 * Transform the geometry
	 * 
	 * @param geometry
	 *            geometry
	 * @return projected geometry
	 */
	public Geometry transform(Geometry geometry) {

		Geometry to = null;

		GeometryType geometryType = geometry.getGeometryType();
		switch (geometryType) {
		case POINT:
			to = transform((Point) geometry);
			break;
		case LINESTRING:
			to = transform((LineString) geometry);
			break;
		case POLYGON:
			to = transform((Polygon) geometry);
			break;
		case MULTIPOINT:
			to = transform((MultiPoint) geometry);
			break;
		case MULTILINESTRING:
			to = transform((MultiLineString) geometry);
			break;
		case MULTIPOLYGON:
			to = transform((MultiPolygon) geometry);
			break;
		case CIRCULARSTRING:
			to = transform((CircularString) geometry);
			break;
		case COMPOUNDCURVE:
			to = transform((CompoundCurve) geometry);
			break;
		case CURVEPOLYGON:
			to = transform((CurvePolygon<?>) geometry);
			break;
		case POLYHEDRALSURFACE:
			to = transform((PolyhedralSurface) geometry);
			break;
		case TIN:
			to = transform((TIN) geometry);
			break;
		case TRIANGLE:
			to = transform((Triangle) geometry);
			break;
		case GEOMETRYCOLLECTION:
			@SuppressWarnings("unchecked")
			GeometryCollection<Geometry> toCollection = transform((GeometryCollection<Geometry>) geometry);
			to = toCollection;
			break;
		default:
			throw new SFException("Unsupported Geometry Type: " + geometryType);
		}

		return to;
	}

	/**
	 * Transform the projected point
	 * 
	 * @param from
	 *            from point
	 * @return projected from
	 */
	public Point transform(Point from) {

		ProjCoordinate fromCoord;
		if (from.hasZ()) {
			fromCoord = new ProjCoordinate(from.getX(), from.getY(),
					from.getZ() != null ? from.getZ() : Double.NaN);
		} else {
			fromCoord = new ProjCoordinate(from.getX(), from.getY());
		}

		ProjCoordinate toCoord = transform.transform(fromCoord);

		Point to = new Point(from.hasZ(), from.hasM(), toCoord.x, toCoord.y);
		if (from.hasZ()) {
			if (Double.isNaN(toCoord.z)) {
				to.setZ(from.getZ());
			} else {
				to.setZ(toCoord.z);
			}
		}
		if (from.hasM()) {
			to.setM(from.getM());
		}

		return to;
	}

	/**
	 * Transform the projected line string
	 * 
	 * @param lineString
	 *            line string
	 * @return projected line string
	 */
	public LineString transform(LineString lineString) {

		LineString to = null;

		switch (lineString.getGeometryType()) {
		case CIRCULARSTRING:
			to = new CircularString(lineString.hasZ(), lineString.hasM());
			break;
		default:
			to = new LineString(lineString.hasZ(), lineString.hasM());
		}

		for (Point point : lineString.getPoints()) {
			Point toPoint = transform(point);
			to.addPoint(toPoint);
		}

		return to;
	}

	/**
	 * Transform the projected polygon
	 * 
	 * @param polygon
	 *            polygon
	 * @return projected polygon
	 */
	public Polygon transform(Polygon polygon) {

		Polygon to = null;

		switch (polygon.getGeometryType()) {
		case TRIANGLE:
			to = new Triangle(polygon.hasZ(), polygon.hasM());
			break;
		default:
			to = new Polygon(polygon.hasZ(), polygon.hasM());
		}

		for (LineString ring : polygon.getRings()) {
			LineString toRing = transform(ring);
			to.addRing(toRing);
		}

		return to;
	}

	/**
	 * Transform the projected multi point
	 * 
	 * @param multiPoint
	 *            multi point
	 * @return projected multi point
	 */
	public MultiPoint transform(MultiPoint multiPoint) {

		MultiPoint to = new MultiPoint(multiPoint.hasZ(), multiPoint.hasM());

		for (Point point : multiPoint.getPoints()) {
			Point toPoint = transform(point);
			to.addPoint(toPoint);
		}

		return to;
	}

	/**
	 * Transform the projected multi line string
	 * 
	 * @param multiLineString
	 *            multi line string
	 * @return projected multi line string
	 */
	public MultiLineString transform(MultiLineString multiLineString) {

		MultiLineString to = new MultiLineString(multiLineString.hasZ(),
				multiLineString.hasM());

		for (LineString lineString : multiLineString.getLineStrings()) {
			LineString toLineString = transform(lineString);
			to.addLineString(toLineString);
		}

		return to;
	}

	/**
	 * Transform the projected multi polygon
	 * 
	 * @param multiPolygon
	 *            multi polygon
	 * @return projected multi polygon
	 */
	public MultiPolygon transform(MultiPolygon multiPolygon) {

		MultiPolygon to = new MultiPolygon(multiPolygon.hasZ(),
				multiPolygon.hasM());

		for (Polygon polygon : multiPolygon.getPolygons()) {
			Polygon toPolygon = transform(polygon);
			to.addPolygon(toPolygon);
		}

		return to;
	}

	/**
	 * Transform the projected circular string
	 * 
	 * @param circularString
	 *            circular string
	 * @return projected circular string
	 */
	public CircularString transform(CircularString circularString) {
		return (CircularString) transform((LineString) circularString);
	}

	/**
	 * Transform the projected compound curve
	 * 
	 * @param compoundCurve
	 *            compound curve
	 * @return projected compound curve
	 */
	public CompoundCurve transform(CompoundCurve compoundCurve) {

		CompoundCurve to = new CompoundCurve(compoundCurve.hasZ(),
				compoundCurve.hasM());

		for (LineString lineString : compoundCurve.getLineStrings()) {
			LineString toLineString = transform(lineString);
			to.addLineString(toLineString);
		}

		return to;
	}

	/**
	 * Transform the projected curve polygon
	 * 
	 * @param curvePolygon
	 *            curve polygon
	 * @return projected curve polygon
	 * @since 2.0.1
	 */
	public <T extends Curve> CurvePolygon<T> transform(
			CurvePolygon<T> curvePolygon) {

		CurvePolygon<T> to = new CurvePolygon<T>(curvePolygon.hasZ(),
				curvePolygon.hasM());

		for (T ring : curvePolygon.getRings()) {

			Curve toRing = null;

			switch (ring.getGeometryType()) {
			case COMPOUNDCURVE:
				toRing = transform((CompoundCurve) ring);
				break;
			default:
				toRing = transform((LineString) ring);
			}

			@SuppressWarnings("unchecked")
			T typedToRing = (T) toRing;
			to.addRing(typedToRing);
		}

		return to;
	}

	/**
	 * Transform the projected polyhedral surface
	 * 
	 * @param polyhedralSurface
	 *            polyhedral surface
	 * @return projected polyhedral surface
	 */
	public PolyhedralSurface transform(PolyhedralSurface polyhedralSurface) {

		PolyhedralSurface to = null;

		switch (polyhedralSurface.getGeometryType()) {
		case TIN:
			to = new TIN(polyhedralSurface.hasZ(), polyhedralSurface.hasM());
			break;
		default:
			to = new PolyhedralSurface(polyhedralSurface.hasZ(),
					polyhedralSurface.hasM());
		}

		for (Polygon polygon : polyhedralSurface.getPolygons()) {
			Polygon toPolygon = transform(polygon);
			to.addPolygon(toPolygon);
		}

		return to;
	}

	/**
	 * Transform the projected TIN
	 * 
	 * @param tin
	 *            TIN
	 * @return projected tin
	 */
	public TIN transform(TIN tin) {
		return (TIN) transform((PolyhedralSurface) tin);
	}

	/**
	 * Transform the projected triangle
	 * 
	 * @param triangle
	 *            triangle
	 * @return projected triangle
	 */
	public Triangle transform(Triangle triangle) {
		return (Triangle) transform((Polygon) triangle);
	}

	/**
	 * Transform the projected geometry collection
	 * 
	 * @param geometryCollection
	 *            geometry collection
	 * @return projected geometry collection
	 */
	public GeometryCollection<Geometry> transform(
			GeometryCollection<Geometry> geometryCollection) {

		GeometryCollection<Geometry> to = new GeometryCollection<Geometry>(
				geometryCollection.hasZ(), geometryCollection.hasM());

		for (Geometry geometry : geometryCollection.getGeometries()) {
			Geometry toGeometry = transform(geometry);
			to.addGeometry(toGeometry);
		}

		return to;
	}

}
