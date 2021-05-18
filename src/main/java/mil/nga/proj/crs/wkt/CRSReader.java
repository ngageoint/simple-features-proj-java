package mil.nga.proj.crs.wkt;

import java.io.Closeable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import mil.nga.proj.ProjectionException;
import mil.nga.proj.crs.CRS;
import mil.nga.proj.crs.CRSType;
import mil.nga.proj.crs.CommonCRS;
import mil.nga.proj.crs.CompoundCoordinateReferenceSystem;
import mil.nga.proj.crs.CoordinateReferenceSystem;
import mil.nga.proj.crs.SimpleCoordinateReferenceSystem;
import mil.nga.proj.crs.common.Axis;
import mil.nga.proj.crs.common.AxisDirectionType;
import mil.nga.proj.crs.common.CoordinateSystem;
import mil.nga.proj.crs.common.CoordinateSystemType;
import mil.nga.proj.crs.common.DatumEnsemble;
import mil.nga.proj.crs.common.DatumEnsembleMember;
import mil.nga.proj.crs.common.Dynamic;
import mil.nga.proj.crs.common.Extent;
import mil.nga.proj.crs.common.GeographicBoundingBox;
import mil.nga.proj.crs.common.Identifier;
import mil.nga.proj.crs.common.ReferenceFrame;
import mil.nga.proj.crs.common.TemporalExtent;
import mil.nga.proj.crs.common.Unit;
import mil.nga.proj.crs.common.UnitType;
import mil.nga.proj.crs.common.Usage;
import mil.nga.proj.crs.common.VerticalExtent;
import mil.nga.proj.crs.derived.DerivedCoordinateReferenceSystem;
import mil.nga.proj.crs.derived.DerivingConversion;
import mil.nga.proj.crs.engineering.EngineeringCoordinateReferenceSystem;
import mil.nga.proj.crs.engineering.EngineeringDatum;
import mil.nga.proj.crs.geo.Ellipsoid;
import mil.nga.proj.crs.geo.GeoCoordinateReferenceSystem;
import mil.nga.proj.crs.geo.GeoDatumEnsemble;
import mil.nga.proj.crs.geo.GeoReferenceFrame;
import mil.nga.proj.crs.geo.PrimeMeridian;
import mil.nga.proj.crs.metadata.CoordinateMetadata;
import mil.nga.proj.crs.operation.CoordinateOperation;
import mil.nga.proj.crs.operation.OperationMethod;
import mil.nga.proj.crs.operation.OperationParameter;
import mil.nga.proj.crs.operation.OperationParameterFile;
import mil.nga.proj.crs.operation.Parameter;
import mil.nga.proj.crs.parametric.ParametricCoordinateReferenceSystem;
import mil.nga.proj.crs.parametric.ParametricDatum;
import mil.nga.proj.crs.projected.MapProjection;
import mil.nga.proj.crs.projected.ProjectedCoordinateReferenceSystem;
import mil.nga.proj.crs.temporal.TemporalCoordinateReferenceSystem;
import mil.nga.proj.crs.temporal.TemporalDatum;
import mil.nga.proj.crs.vertical.VerticalCoordinateReferenceSystem;
import mil.nga.proj.crs.vertical.VerticalDatumEnsemble;
import mil.nga.proj.crs.vertical.VerticalReferenceFrame;

/**
 * Well-Known Text reader
 * 
 * @author osbornb
 */
public class CRSReader implements Closeable {

	/**
	 * Logger
	 */
	private static final Logger logger = Logger
			.getLogger(CRSReader.class.getName());

	/**
	 * Axis Name and Abbreviation Pattern
	 */
	private static final String AXIS_NAME_ABBREV_PATTERN = "((.+ )|^)\\([a-zA-Z]+\\)$";

	/**
	 * Read a Coordinate Reference System from the well-known text
	 * 
	 * @param text
	 *            well-known text
	 * @return Coordinate Reference System
	 * @throws IOException
	 *             upon failure to read
	 */
	public static CRS read(String text) throws IOException {
		return read(text, false);
	}

	/**
	 * Read a Coordinate Reference System from the well-known text
	 * 
	 * @param text
	 *            well-known text
	 * @param strict
	 *            strict enforcement
	 * @return Coordinate Reference System
	 * @throws IOException
	 *             upon failure to read
	 */
	public static CRS read(String text, boolean strict) throws IOException {
		CRS crs = null;
		CRSReader reader = new CRSReader(text, strict);
		try {
			crs = reader.read();
			reader.readEnd();
		} finally {
			reader.close();
		}
		return crs;
	}

	/**
	 * Read a Coordinate Reference System from the well-known text
	 * 
	 * @param text
	 *            well-known text
	 * @param expected
	 *            expected types
	 * @return Coordinate Reference System
	 * @throws IOException
	 *             upon failure to read
	 */
	public static CRS read(String text, CRSType... expected)
			throws IOException {
		return read(text, false, expected);
	}

	/**
	 * Read a Coordinate Reference System from the well-known text
	 * 
	 * @param text
	 *            well-known text
	 * @param strict
	 *            strict enforcement
	 * @param expected
	 *            expected types
	 * @return Coordinate Reference System
	 * @throws IOException
	 *             upon failure to read
	 */
	public static CRS read(String text, boolean strict, CRSType... expected)
			throws IOException {
		CRS crs = read(text, strict);
		Set<CRSType> expectedSet = new HashSet<>(Arrays.asList(expected));
		if (!expectedSet.contains(crs.getType())) {
			throw new ProjectionException(
					"Unexpected Coordinate Reference System Type: "
							+ crs.getType() + ", Expected: " + expectedSet);
		}
		return crs;
	}

	/**
	 * Read a Coordinate Reference System from the well-known text
	 * 
	 * @param text
	 *            well-known text
	 * @return Coordinate Reference System
	 * @throws IOException
	 *             upon failure to read
	 */
	public static CoordinateReferenceSystem readCoordinateReferenceSystem(
			String text) throws IOException {
		return readCoordinateReferenceSystem(text, false);
	}

	/**
	 * Read a Coordinate Reference System from the well-known text
	 * 
	 * @param text
	 *            well-known text
	 * @param strict
	 *            strict enforcement
	 * @return Coordinate Reference System
	 * @throws IOException
	 *             upon failure to read
	 */
	public static CoordinateReferenceSystem readCoordinateReferenceSystem(
			String text, boolean strict) throws IOException {
		return (CoordinateReferenceSystem) read(text, strict, CRSType.GEODETIC,
				CRSType.GEOGRAPHIC, CRSType.PROJECTED, CRSType.VERTICAL,
				CRSType.ENGINEERING, CRSType.PARAMETRIC, CRSType.TEMPORAL,
				CRSType.DERIVED, CRSType.COMPOUND, CRSType.BOUND);
	}

	/**
	 * Read a Simple Coordinate Reference System from the well-known text
	 * 
	 * @param text
	 *            well-known text
	 * @return Simple Coordinate Reference System
	 * @throws IOException
	 *             upon failure to read
	 */
	public static SimpleCoordinateReferenceSystem readSimpleCoordinateReferenceSystem(
			String text) throws IOException {
		return readSimpleCoordinateReferenceSystem(text, false);
	}

	/**
	 * Read a Simple Coordinate Reference System from the well-known text
	 * 
	 * @param text
	 *            well-known text
	 * @param strict
	 *            strict enforcement
	 * @return Simple Coordinate Reference System
	 * @throws IOException
	 *             upon failure to read
	 */
	public static SimpleCoordinateReferenceSystem readSimpleCoordinateReferenceSystem(
			String text, boolean strict) throws IOException {
		return (SimpleCoordinateReferenceSystem) read(text, strict,
				CRSType.GEODETIC, CRSType.GEOGRAPHIC, CRSType.PROJECTED,
				CRSType.VERTICAL, CRSType.ENGINEERING, CRSType.PARAMETRIC,
				CRSType.TEMPORAL, CRSType.DERIVED);
	}

	/**
	 * Read a Geodetic or Geographic Coordinate Reference System from the
	 * well-known text
	 * 
	 * @param text
	 *            well-known text
	 * @return Geodetic or Geographic Coordinate Reference System
	 * @throws IOException
	 *             upon failure to read
	 */
	public static GeoCoordinateReferenceSystem readGeo(String text)
			throws IOException {
		return (GeoCoordinateReferenceSystem) read(text, CRSType.GEODETIC,
				CRSType.GEOGRAPHIC);
	}

	/**
	 * Read a Geodetic Coordinate Reference System from the well-known text
	 * 
	 * @param text
	 *            well-known text
	 * @return Geodetic Coordinate Reference System
	 * @throws IOException
	 *             upon failure to read
	 */
	public static GeoCoordinateReferenceSystem readGeodetic(String text)
			throws IOException {
		return (GeoCoordinateReferenceSystem) read(text, CRSType.GEODETIC);
	}

	/**
	 * Read a Geographic Coordinate Reference System from the well-known text
	 * 
	 * @param text
	 *            well-known text
	 * @return Geographic Coordinate Reference System
	 * @throws IOException
	 *             upon failure to read
	 */
	public static GeoCoordinateReferenceSystem readGeographic(String text)
			throws IOException {
		return (GeoCoordinateReferenceSystem) read(text, CRSType.GEOGRAPHIC);
	}

	/**
	 * Read a Projected Coordinate Reference System from the well-known text
	 * 
	 * @param text
	 *            well-known text
	 * @return Projected Coordinate Reference System
	 * @throws IOException
	 *             upon failure to read
	 */
	public static ProjectedCoordinateReferenceSystem readProjected(String text)
			throws IOException {
		ProjectedCoordinateReferenceSystem crs = null;
		CRSReader reader = new CRSReader(text);
		try {
			crs = reader.readProjected();
			reader.readEnd();
		} finally {
			reader.close();
		}
		return crs;
	}

	/**
	 * Read a Projected Geodetic Coordinate Reference System from the well-known
	 * text
	 * 
	 * @param text
	 *            well-known text
	 * @return Projected Geodetic Coordinate Reference System
	 * @throws IOException
	 *             upon failure to read
	 */
	public static ProjectedCoordinateReferenceSystem readProjectedGeodetic(
			String text) throws IOException {
		ProjectedCoordinateReferenceSystem crs = null;
		CRSReader reader = new CRSReader(text);
		try {
			crs = reader.readProjectedGeodetic();
			reader.readEnd();
		} finally {
			reader.close();
		}
		return crs;
	}

	/**
	 * Read a Projected Geographic Coordinate Reference System from the
	 * well-known text
	 * 
	 * @param text
	 *            well-known text
	 * @return Projected Geographic Coordinate Reference System
	 * @throws IOException
	 *             upon failure to read
	 */
	public static ProjectedCoordinateReferenceSystem readProjectedGeographic(
			String text) throws IOException {
		ProjectedCoordinateReferenceSystem crs = null;
		CRSReader reader = new CRSReader(text);
		try {
			crs = reader.readProjectedGeographic();
			reader.readEnd();
		} finally {
			reader.close();
		}
		return crs;
	}

	/**
	 * Read a Vertical Coordinate Reference System from the well-known text
	 * 
	 * @param text
	 *            well-known text
	 * @return Vertical Coordinate Reference System
	 * @throws IOException
	 *             upon failure to read
	 */
	public static VerticalCoordinateReferenceSystem readVertical(String text)
			throws IOException {
		return (VerticalCoordinateReferenceSystem) read(text, CRSType.VERTICAL);
	}

	/**
	 * Read an Engineering Coordinate Reference System from the well-known text
	 * 
	 * @param text
	 *            well-known text
	 * @return Engineering Coordinate Reference System
	 * @throws IOException
	 *             upon failure to read
	 */
	public static EngineeringCoordinateReferenceSystem readEngineering(
			String text) throws IOException {
		return (EngineeringCoordinateReferenceSystem) read(text,
				CRSType.ENGINEERING);
	}

	/**
	 * Read an Parametric Coordinate Reference System from the well-known text
	 * 
	 * @param text
	 *            well-known text
	 * @return Parametric Coordinate Reference System
	 * @throws IOException
	 *             upon failure to read
	 */
	public static ParametricCoordinateReferenceSystem readParametric(
			String text) throws IOException {
		return (ParametricCoordinateReferenceSystem) read(text,
				CRSType.PARAMETRIC);
	}

	/**
	 * Read an Temporal Coordinate Reference System from the well-known text
	 * 
	 * @param text
	 *            well-known text
	 * @return Temporal Coordinate Reference System
	 * @throws IOException
	 *             upon failure to read
	 */
	public static TemporalCoordinateReferenceSystem readTemporal(String text)
			throws IOException {
		return (TemporalCoordinateReferenceSystem) read(text, CRSType.TEMPORAL);
	}

	/**
	 * Read a Derived Coordinate Reference System from the well-known text
	 * 
	 * @param text
	 *            well-known text
	 * @return Derived Coordinate Reference System
	 * @throws IOException
	 *             upon failure to read
	 */
	public static DerivedCoordinateReferenceSystem readDerived(String text)
			throws IOException {
		return (DerivedCoordinateReferenceSystem) read(text, CRSType.DERIVED);
	}

	/**
	 * Read a Compound Coordinate Reference System from the well-known text
	 * 
	 * @param text
	 *            well-known text
	 * @return Compound Coordinate Reference System
	 * @throws IOException
	 *             upon failure to read
	 */
	public static CompoundCoordinateReferenceSystem readCompound(String text)
			throws IOException {
		CompoundCoordinateReferenceSystem crs = null;
		CRSReader reader = new CRSReader(text);
		try {
			crs = reader.readCompound();
			reader.readEnd();
		} finally {
			reader.close();
		}
		return crs;
	}

	/**
	 * Read Coordinate Metadata from the well-known text
	 * 
	 * @param text
	 *            well-known text
	 * @return Coordinate Metadata
	 * @throws IOException
	 *             upon failure to read
	 */
	public static CoordinateMetadata readCoordinateMetadata(String text)
			throws IOException {
		CoordinateMetadata metadata = null;
		CRSReader reader = new CRSReader(text);
		try {
			metadata = reader.readCoordinateMetadata();
			reader.readEnd();
		} finally {
			reader.close();
		}
		return metadata;
	}

	/**
	 * Read Coordinate Operation from the well-known text
	 * 
	 * @param text
	 *            well-known text
	 * @return Coordinate Operation
	 * @throws IOException
	 *             upon failure to read
	 */
	public static CoordinateOperation readCoordinateOperation(String text)
			throws IOException {
		CoordinateOperation operation = null;
		CRSReader reader = new CRSReader(text);
		try {
			operation = reader.readCoordinateOperation();
			reader.readEnd();
		} finally {
			reader.close();
		}
		return operation;
	}

	/**
	 * Read a Backward Compatible Geodetic or Geographic Coordinate Reference
	 * System from the well-known text
	 * 
	 * @param text
	 *            well-known text
	 * @return Geodetic or Geographic Coordinate Reference System
	 * @throws IOException
	 *             upon failure to read
	 */
	public static GeoCoordinateReferenceSystem readGeoCompat(String text)
			throws IOException {
		GeoCoordinateReferenceSystem crs = null;
		CRSReader reader = new CRSReader(text);
		try {
			crs = reader.readGeoCompat();
			reader.readEnd();
		} finally {
			reader.close();
		}
		return crs;
	}

	/**
	 * Read a Backward Compatible Geodetic Coordinate Reference System from the
	 * well-known text
	 * 
	 * @param text
	 *            well-known text
	 * @return Geodetic Coordinate Reference System
	 * @throws IOException
	 *             upon failure to read
	 */
	public static GeoCoordinateReferenceSystem readGeodeticCompat(String text)
			throws IOException {
		GeoCoordinateReferenceSystem crs = null;
		CRSReader reader = new CRSReader(text);
		try {
			crs = reader.readGeodeticCompat();
			reader.readEnd();
		} finally {
			reader.close();
		}
		return crs;
	}

	/**
	 * Read a Backward Compatible Geographic Coordinate Reference System from
	 * the well-known text
	 * 
	 * @param text
	 *            well-known text
	 * @return Geographic Coordinate Reference System
	 * @throws IOException
	 *             upon failure to read
	 */
	public static GeoCoordinateReferenceSystem readGeographicCompat(String text)
			throws IOException {
		GeoCoordinateReferenceSystem crs = null;
		CRSReader reader = new CRSReader(text);
		try {
			crs = reader.readGeographicCompat();
			reader.readEnd();
		} finally {
			reader.close();
		}
		return crs;
	}

	/**
	 * Read a Backward Compatible Projected Coordinate Reference System from the
	 * well-known text
	 * 
	 * @param text
	 *            well-known text
	 * @return Projected Coordinate Reference System
	 * @throws IOException
	 *             upon failure to read
	 */
	public static ProjectedCoordinateReferenceSystem readProjectedCompat(
			String text) throws IOException {
		ProjectedCoordinateReferenceSystem crs = null;
		CRSReader reader = new CRSReader(text);
		try {
			crs = reader.readProjectedCompat();
			reader.readEnd();
		} finally {
			reader.close();
		}
		return crs;
	}

	/**
	 * Read a Backward Compatible Projected Geodetic Coordinate Reference System
	 * from the well-known text
	 * 
	 * @param text
	 *            well-known text
	 * @return Projected Geodetic Coordinate Reference System
	 * @throws IOException
	 *             upon failure to read
	 */
	public static ProjectedCoordinateReferenceSystem readProjectedGeodeticCompat(
			String text) throws IOException {
		ProjectedCoordinateReferenceSystem crs = null;
		CRSReader reader = new CRSReader(text);
		try {
			crs = reader.readProjectedGeodeticCompat();
			reader.readEnd();
		} finally {
			reader.close();
		}
		return crs;
	}

	/**
	 * Read a Backward Compatible Projected Geographic Coordinate Reference
	 * System from the well-known text
	 * 
	 * @param text
	 *            well-known text
	 * @return Projected Geographic Coordinate Reference System
	 * @throws IOException
	 *             upon failure to read
	 */
	public static ProjectedCoordinateReferenceSystem readProjectedGeographicCompat(
			String text) throws IOException {
		ProjectedCoordinateReferenceSystem crs = null;
		CRSReader reader = new CRSReader(text);
		try {
			crs = reader.readProjectedGeographicCompat();
			reader.readEnd();
		} finally {
			reader.close();
		}
		return crs;
	}

	/**
	 * Text Reader
	 */
	private TextReader reader;

	/**
	 * Strict rule enforcement
	 */
	private boolean strict = false;

	/**
	 * Backward Compatible extras
	 */
	private Map<String, String> extras = new LinkedHashMap<>();

	/**
	 * Constructor
	 * 
	 * @param text
	 *            well-known text
	 */
	public CRSReader(String text) {
		this(new TextReader(text));
	}

	/**
	 * Constructor
	 * 
	 * @param reader
	 *            text reader
	 */
	public CRSReader(TextReader reader) {
		this(reader, false);
	}

	/**
	 * Constructor
	 * 
	 * @param text
	 *            well-known text
	 * @param strict
	 *            strict rule enforcement
	 */
	public CRSReader(String text, boolean strict) {
		this(new TextReader(text), strict);
	}

	/**
	 * Constructor
	 * 
	 * @param reader
	 *            text reader
	 * @param strict
	 *            strict rule enforcement
	 */
	public CRSReader(TextReader reader, boolean strict) {
		this.reader = reader;
		this.strict = strict;
	}

	/**
	 * Get the text reader
	 * 
	 * @return text reader
	 */
	public TextReader getTextReader() {
		return reader;
	}

	/**
	 * Is strict rule enforcement enabled
	 * 
	 * @return true if strict
	 */
	public boolean isStrict() {
		return strict;
	}

	/**
	 * Set the strict rule enforcement setting
	 * 
	 * @param strict
	 *            true for strict enforcement
	 */
	public void setStrict(boolean strict) {
		this.strict = strict;
	}

	/**
	 * Reset the reader
	 * 
	 * @throws IOException
	 *             upon reset error
	 */
	public void reset() throws IOException {
		reader.reset();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void close() {
		reader.close();
	}

	/**
	 * Read a CRS object from the well-known text
	 * 
	 * @return CRS
	 * @throws IOException
	 *             upon failure to read
	 */
	public CRS read() throws IOException {

		CRS crs = null;

		CRSKeyword keyword = peekKeyword();
		switch (keyword) {
		case GEODCRS:
		case GEOGCRS:
			crs = readGeo();
			break;
		case GEOCCS:
		case GEOGCS:
			crs = readGeoCompat();
			break;
		case PROJCRS:
			crs = readProjected();
			break;
		case PROJCS:
			crs = readProjectedCompat();
			break;
		case VERTCRS:
			crs = readVertical();
			break;
		case VERT_CS:
			crs = readVerticalCompat();
			break;
		case ENGCRS:
			crs = readEngineering();
			break;
		case LOCAL_CS:
			crs = readEngineeringCompat();
			break;
		case PARAMETRICCRS:
			crs = readParametric();
			break;
		case TIMECRS:
			crs = readTemporal();
			break;
		case DERIVEDPROJCRS:
			crs = readDerivedProjected();
			break;
		case COMPOUNDCRS:
			crs = readCompound();
			break;
		case COORDINATEMETADATA:
			crs = readCoordinateMetadata();
			break;
		case COORDINATEOPERATION:
			crs = readCoordinateOperation();
			break;
		default:
			throw new ProjectionException(
					"Unsupported WKT CRS keyword: " + keyword);
		}

		return crs;
	}

	/**
	 * Read a Coordinate Reference System from the well-known text
	 * 
	 * @return Coordinate Reference System
	 * @throws IOException
	 *             upon failure to read
	 */
	public CoordinateReferenceSystem readCoordinateReferenceSystem()
			throws IOException {
		CRS crs = read();
		if (!(crs instanceof CoordinateReferenceSystem)) {
			throw new ProjectionException(
					"Unexpected Coordinate Reference System Type: "
							+ crs.getType());
		}
		return (CoordinateReferenceSystem) crs;
	}

	/**
	 * Read a Simple Coordinate Reference System from the well-known text
	 * 
	 * @return Simple Coordinate Reference System
	 * @throws IOException
	 *             upon failure to read
	 */
	public SimpleCoordinateReferenceSystem readSimpleCoordinateReferenceSystem()
			throws IOException {
		CRS crs = read();
		if (!(crs instanceof SimpleCoordinateReferenceSystem)) {
			throw new ProjectionException(
					"Unexpected Simple Coordinate Reference System Type: "
							+ crs.getType());
		}
		return (SimpleCoordinateReferenceSystem) crs;
	}

	/**
	 * Read a WKT CRS keyword
	 * 
	 * @return keyword
	 * @throws IOException
	 *             upon failure to read
	 */
	public CRSKeyword readKeyword() throws IOException {
		return CRSKeyword.getRequiredType(reader.readToken());
	}

	/**
	 * Read WKT CRS keywords
	 * 
	 * @return keywords
	 * @throws IOException
	 *             upon failure to read
	 */
	public Set<CRSKeyword> readKeywords() throws IOException {
		return CRSKeyword.getRequiredTypes(reader.readToken());
	}

	/**
	 * Read a specific WKT CRS keyword, next token when strict, until found when
	 * not
	 * 
	 * @param keywords
	 *            read until one of the keywords
	 * @return keyword read
	 * @throws IOException
	 *             upon failure to read
	 */
	public CRSKeyword readKeyword(CRSKeyword... keywords) throws IOException {
		return readKeyword(true, keywords);
	}

	/**
	 * Read skipping tokens up until before an optional WKT CRS keyword
	 * 
	 * @param keywords
	 *            read until one of the keywords
	 * @return next keyword or null
	 * @throws IOException
	 *             upon failure to read
	 */
	public CRSKeyword readToKeyword(CRSKeyword... keywords) throws IOException {
		CRSKeyword keyword = readKeyword(false, keywords);
		if (keyword != null) {
			reader.pushToken(keyword.name());
		}
		return keyword;
	}

	/**
	 * Read looking for a specific WKT CRS keyword, skipping others if not
	 * strict mode
	 * 
	 * @param required
	 *            true if keyword is required, read only until an external right
	 *            delimiter
	 * @param keywords
	 *            read until one of the keywords
	 * @return keyword read
	 * @throws IOException
	 *             upon failure to read
	 */
	public CRSKeyword readKeyword(boolean required, CRSKeyword... keywords)
			throws IOException {

		CRSKeyword keyword = null;
		Set<CRSKeyword> keywordSet = new HashSet<>(Arrays.asList(keywords));

		int delimiterCount = 0;

		String previousToken = null;
		String token = reader.readToken();

		StringBuilder ignored = null;
		while (token != null) {

			if (!required) {
				if (WKTUtils.isLeftDelimiter(token)) {
					delimiterCount++;
				} else if (WKTUtils.isRightDelimiter(token)) {
					delimiterCount--;
					if (delimiterCount < 0) {
						reader.pushToken(token);
						break;
					}
				}
			}

			Set<CRSKeyword> tokenKeywords = CRSKeyword.getTypes(token);
			if (tokenKeywords != null) {
				for (CRSKeyword kw : tokenKeywords) {
					if (keywordSet.contains(kw)) {
						keyword = kw;
						break;
					}
				}
				if (keyword != null) {
					break;
				}
			}

			if (previousToken != null) {
				if (ignored == null) {
					ignored = new StringBuilder();
				}
				ignored.append(previousToken);
			}

			previousToken = token;
			token = reader.readToken();
		}

		if (required && keyword == null) {
			throw new ProjectionException(
					"Expected keyword not found: " + keywordNames(keywordSet));
		}

		if (previousToken != null && (keyword == null
				|| !previousToken.equals(WKTConstants.SEPARATOR))) {
			if (ignored == null) {
				ignored = new StringBuilder();
			}
			ignored.append(previousToken);
		}

		if (ignored != null) {
			StringBuilder log = new StringBuilder();
			if (strict) {
				log.append("Unexpected");
			} else {
				log.append("Ignored");
			}
			if (keyword != null) {
				log.append(" before ");
				log.append(keyword.getKeywords());
			}
			log.append(": \"");
			log.append(ignored);
			log.append("\"");
			if (strict) {
				throw new ProjectionException(log.toString());
			} else {
				logger.log(Level.WARNING, log.toString());
			}
		}

		return keyword;
	}

	/**
	 * Peek a WKT CRS keyword
	 * 
	 * @return keyword
	 * @throws IOException
	 *             upon failure to read
	 */
	public CRSKeyword peekKeyword() throws IOException {
		return CRSKeyword.getRequiredType(reader.peekToken());
	}

	/**
	 * Peek WKT CRS keywords
	 * 
	 * @return keywords
	 * @throws IOException
	 *             upon failure to read
	 */
	public Set<CRSKeyword> peekKeywords() throws IOException {
		return CRSKeyword.getRequiredTypes(reader.peekToken());
	}

	/**
	 * Peek an optional WKT CRS keyword
	 * 
	 * @return keyword
	 * @throws IOException
	 *             upon failure to read
	 */
	public CRSKeyword peekOptionalKeyword() throws IOException {
		return CRSKeyword.getType(reader.peekToken());
	}

	/**
	 * Peek an optional WKT CRS keywords
	 * 
	 * @return keywords
	 * @throws IOException
	 *             upon failure to read
	 */
	public Set<CRSKeyword> peekOptionalKeywords() throws IOException {
		return CRSKeyword.getTypes(reader.peekToken());
	}

	/**
	 * Peek an optional WKT CRS keyword
	 * 
	 * @param num
	 *            number of tokens out to peek at
	 * @return keyword
	 * @throws IOException
	 *             upon failure to read
	 */
	public CRSKeyword peekOptionalKeyword(int num) throws IOException {
		return CRSKeyword.getType(reader.peekToken(num));
	}

	/**
	 * Peek an optional WKT CRS keywords
	 * 
	 * @param num
	 *            number of tokens out to peek at
	 * @return keywords
	 * @throws IOException
	 *             upon failure to read
	 */
	public Set<CRSKeyword> peekOptionalKeywords(int num) throws IOException {
		return CRSKeyword.getTypes(reader.peekToken(num));
	}

	/**
	 * Read a left delimiter
	 * 
	 * @throws IOException
	 *             upon failure to read
	 */
	public void readLeftDelimiter() throws IOException {
		String token = reader.readExpectedToken();
		if (!WKTUtils.isLeftDelimiter(token)) {
			throw new ProjectionException(
					"Invalid left delimiter token, expected '[' or '('. found: '"
							+ token + "'");
		}
	}

	/**
	 * Peek if the next token is a left delimiter
	 * 
	 * @return true if next token is a left delimiter
	 * @throws IOException
	 *             upon failure to read
	 */
	public boolean peekLeftDelimiter() throws IOException {
		boolean leftDelimiter = false;
		String token = reader.peekToken();
		if (token != null) {
			leftDelimiter = WKTUtils.isLeftDelimiter(token);
		}
		return leftDelimiter;
	}

	/**
	 * Read skipping tokens until an external right delimiter (first right
	 * delimiter without a preceding left)
	 * 
	 * @throws IOException
	 *             upon failure to read
	 */
	public void readRightDelimiter() throws IOException {
		readKeyword(false, new CRSKeyword[] {});
		String token = reader.readExpectedToken();
		if (!WKTUtils.isRightDelimiter(token)) {
			throw new ProjectionException(
					"Invalid right delimiter token, expected ']' or ')'. found: '"
							+ token + "'");
		}
	}

	/**
	 * Peek if the next token is a right delimiter
	 * 
	 * @return true if next token is a right delimiter
	 * @throws IOException
	 *             upon failure to read
	 */
	public boolean peekRightDelimiter() throws IOException {
		boolean rightDelimiter = false;
		String token = reader.peekToken();
		if (token != null) {
			rightDelimiter = WKTUtils.isRightDelimiter(token);
		}
		return rightDelimiter;
	}

	/**
	 * Read a WKT Separator (comma)
	 * 
	 * @throws IOException
	 *             upon failure to read
	 */
	public void readSeparator() throws IOException {
		String token = reader.peekToken();
		if (token.equals(WKTConstants.SEPARATOR)) {
			reader.readExpectedToken();
		} else if (strict) {
			throw new ProjectionException(
					"Invalid separator token, expected ','. found: '" + token
							+ "'");
		} else {
			logger.log(Level.WARNING,
					"Missing expected separator before token: '" + token + "'");
		}
	}

	/**
	 * Peek if the next token is a WKT Separator (comma)
	 * 
	 * @return true if next token is a separator
	 * @throws IOException
	 *             upon failure to read
	 */
	public boolean peekSeparator() throws IOException {
		boolean separator = false;
		String token = reader.peekToken();
		if (token != null) {
			separator = token.equals(WKTConstants.SEPARATOR);
		}
		return separator;
	}

	/**
	 * "Read" an expected end, checking for unexpected trailing tokens
	 * 
	 * @throws IOException
	 *             upon failure to read
	 */
	public void readEnd() throws IOException {

		String token = reader.readToken();
		if (token != null) {
			StringBuilder ignored = new StringBuilder();
			do {
				ignored.append(token);
				token = reader.readToken();
			} while (token != null);

			StringBuilder log = new StringBuilder();
			if (strict) {
				log.append("Unexpected");
			} else {
				log.append("Ignored");
			}
			log.append(" end: \"");
			log.append(ignored);
			log.append("\"");
			if (strict) {
				throw new ProjectionException(log.toString());
			} else {
				logger.log(Level.WARNING, log.toString());
			}
		}
	}

	/**
	 * Read a keyword delimited token
	 * 
	 * @param keyword
	 *            expected keyword
	 * @return token
	 * @throws IOException
	 *             upon failure to read
	 */
	public String readKeywordDelimitedToken(CRSKeyword keyword)
			throws IOException {

		readKeyword(keyword);

		readLeftDelimiter();

		String token = reader.readExpectedToken();

		readRightDelimiter();

		return token;
	}

	/**
	 * Validate the keyword against the expected keywords
	 * 
	 * @param keywords
	 *            keywords
	 * @param expected
	 *            expected keywords
	 * @return matching keyword
	 */
	private CRSKeyword validateKeyword(Set<CRSKeyword> keywords,
			CRSKeyword... expected) {
		CRSKeyword keyword = null;
		Set<CRSKeyword> expectedSet = new HashSet<>(Arrays.asList(expected));
		for (CRSKeyword kw : keywords) {
			if (expectedSet.contains(kw)) {
				keyword = kw;
				break;
			}
		}
		if (keyword == null) {
			throw new ProjectionException(
					"Unexpected keyword. found: " + keywordNames(keywords)
							+ ", expected: " + keywordNames(expectedSet));
		}
		return keyword;
	}

	/**
	 * Set of all keyword names from the set of keywords
	 * 
	 * @param keywords
	 *            keywords
	 * @return set of names
	 */
	private Set<String> keywordNames(Set<CRSKeyword> keywords) {
		Set<String> names = new LinkedHashSet<>();
		for (CRSKeyword keyword : keywords) {
			names.addAll(keyword.getKeywords());
		}
		return names;
	}

	/**
	 * Check if the keyword is next following an immediate next separator
	 * 
	 * @param keywords
	 *            keywords
	 * @return true if next
	 * @throws IOException
	 *             upon failure to read
	 */
	private boolean isKeywordNext(CRSKeyword... keywords) throws IOException {
		boolean next = false;
		boolean separator = peekSeparator();
		if (separator || !strict) {
			int num = separator ? 2 : 1;
			Set<CRSKeyword> nextKeywords = peekOptionalKeywords(num);
			if (nextKeywords != null && !nextKeywords.isEmpty()) {
				for (CRSKeyword keyword : keywords) {
					next = nextKeywords.contains(keyword);
					if (next) {
						break;
					}
				}
			}
		}
		return next;
	}

	/**
	 * Check if the keyword is next following an immediate next separator
	 * 
	 * @param keyword
	 *            keyword
	 * @return true if next
	 * @throws IOException
	 *             upon failure to read
	 */
	private boolean isNonKeywordNext() throws IOException {
		boolean next = false;
		if (peekSeparator()) {
			Set<CRSKeyword> nextKeywords = peekOptionalKeywords(2);
			next = nextKeywords == null || nextKeywords.isEmpty();
		}
		return next;
	}

	/**
	 * Is a unit next following an immediate next separator
	 * 
	 * @return true if next
	 * @throws IOException
	 *             upon failure to read
	 */
	private boolean isUnitNext() throws IOException {
		return isKeywordNext(CRSKeyword.ANGLEUNIT, CRSKeyword.LENGTHUNIT,
				CRSKeyword.PARAMETRICUNIT, CRSKeyword.SCALEUNIT,
				CRSKeyword.TIMEUNIT);
	}

	/**
	 * Is a spatial unit next following an immediate next separator
	 * 
	 * @return true if next
	 * @throws IOException
	 *             upon failure to read
	 */
	private boolean isSpatialUnitNext() throws IOException {
		return isKeywordNext(CRSKeyword.ANGLEUNIT, CRSKeyword.LENGTHUNIT,
				CRSKeyword.PARAMETRICUNIT, CRSKeyword.SCALEUNIT);
	}

	/**
	 * Is a time unit next following an immediate next separator
	 * 
	 * @return true if next
	 * @throws IOException
	 *             upon failure to read
	 */
	private boolean isTimeUnitNext() throws IOException {
		return isKeywordNext(CRSKeyword.TIMEUNIT);
	}

	/**
	 * Read a Geodetic or Geographic CRS
	 * 
	 * @return geodetic, geographic, or derived coordinate reference system
	 * @throws IOException
	 *             upon failure to read
	 */
	public CoordinateReferenceSystem readGeo() throws IOException {

		GeoCoordinateReferenceSystem baseCrs = new GeoCoordinateReferenceSystem();
		SimpleCoordinateReferenceSystem crs = baseCrs;
		DerivedCoordinateReferenceSystem derivedCrs = null;

		CRSKeyword keyword = readKeyword(CRSKeyword.GEODCRS,
				CRSKeyword.GEOGCRS);
		crs.setType(WKTUtils.getCoordinateReferenceSystemType(keyword));

		readLeftDelimiter();

		String name = reader.readExpectedToken();

		if (isKeywordNext(CRSKeyword.BASEGEODCRS, CRSKeyword.BASEGEOGCRS)) {

			switch (keyword) {
			case GEODCRS:
				readKeyword(CRSKeyword.BASEGEODCRS);
				break;
			case GEOGCRS:
				readKeyword(CRSKeyword.BASEGEOGCRS);
				break;
			default:
				throw new ProjectionException(
						"Unsupported Coordinate Reference System Type: "
								+ keyword);
			}

			derivedCrs = new DerivedCoordinateReferenceSystem();
			derivedCrs.setBase(baseCrs);
			crs = derivedCrs;

			readLeftDelimiter();
			baseCrs.setName(reader.readExpectedToken());
		}

		crs.setName(name);

		boolean isDynamic = isKeywordNext(CRSKeyword.DYNAMIC);
		if (isDynamic) {
			readSeparator();
			baseCrs.setDynamic(readDynamic());
		}

		if (isDynamic || isKeywordNext(CRSKeyword.DATUM)) {
			readSeparator();
			GeoReferenceFrame referenceFrame = readGeoReferenceFrame();
			referenceFrame.setType(baseCrs.getType());
			baseCrs.setReferenceFrame(referenceFrame);
		} else if (isKeywordNext(CRSKeyword.ENSEMBLE)) {
			readSeparator();
			baseCrs.setDatumEnsemble(readGeoDatumEnsemble());
		} else {
			// Validation error
			readSeparator();
			readKeyword(CRSKeyword.DATUM, CRSKeyword.ENSEMBLE);
		}

		if (derivedCrs != null) {

			keyword = readToKeyword(CRSKeyword.ID);
			if (keyword == CRSKeyword.ID) {
				baseCrs.setIdentifiers(readIdentifiers());
			}

			readRightDelimiter();

			readSeparator();
			derivedCrs.setConversion(readDerivingConversion());

		}

		readSeparator();
		crs.setCoordinateSystem(readCoordinateSystem());

		readScopeExtentIdentifierRemark(crs);

		readRightDelimiter();

		return crs;
	}

	/**
	 * Read a Projected CRS
	 * 
	 * @return projected coordinate reference system
	 * @throws IOException
	 *             upon failure to read
	 */
	public ProjectedCoordinateReferenceSystem readProjected()
			throws IOException {
		CRSType expectedType = null;
		return readProjected(expectedType);
	}

	/**
	 * Read a Projected Geodetic CRS
	 * 
	 * @return projected geodetic coordinate reference system
	 * @throws IOException
	 *             upon failure to read
	 */
	public ProjectedCoordinateReferenceSystem readProjectedGeodetic()
			throws IOException {
		return readProjected(CRSType.GEODETIC);
	}

	/**
	 * Read a Projected Geographic CRS
	 * 
	 * @return projected geographic coordinate reference system
	 * @throws IOException
	 *             upon failure to read
	 */
	public ProjectedCoordinateReferenceSystem readProjectedGeographic()
			throws IOException {
		return readProjected(CRSType.GEOGRAPHIC);
	}

	/**
	 * Read a Projected CRS
	 * 
	 * @param expectedBaseType
	 *            expected base coordinate reference system type
	 * 
	 * @return projected coordinate reference system
	 * @throws IOException
	 *             upon failure to read
	 */
	public ProjectedCoordinateReferenceSystem readProjected(
			CRSType expectedBaseType) throws IOException {

		ProjectedCoordinateReferenceSystem crs = new ProjectedCoordinateReferenceSystem();

		readKeyword(CRSKeyword.PROJCRS);

		readLeftDelimiter();

		crs.setName(reader.readExpectedToken());

		readSeparator();

		CRSKeyword type = readKeyword(CRSKeyword.BASEGEODCRS,
				CRSKeyword.BASEGEOGCRS);
		CRSType crsType = WKTUtils.getCoordinateReferenceSystemType(type);
		if (expectedBaseType != null && crsType != expectedBaseType) {
			throw new ProjectionException(
					"Unexpected Base Coordinate Reference System Type. expected: "
							+ expectedBaseType + ", found: " + crsType);
		}
		crs.setBaseType(crsType);

		readLeftDelimiter();

		crs.setBaseName(reader.readExpectedToken());

		boolean isDynamic = isKeywordNext(CRSKeyword.DYNAMIC);
		if (isDynamic) {
			readSeparator();
			crs.setDynamic(readDynamic());
		}

		if (isDynamic || isKeywordNext(CRSKeyword.DATUM)) {
			readSeparator();
			GeoReferenceFrame referenceFrame = readGeoReferenceFrame();
			referenceFrame.setType(crsType);
			crs.setReferenceFrame(referenceFrame);
		} else if (isKeywordNext(CRSKeyword.ENSEMBLE)) {
			readSeparator();
			crs.setDatumEnsemble(readGeoDatumEnsemble());
		} else {
			// Validation error
			readSeparator();
			readKeyword(CRSKeyword.DATUM, CRSKeyword.ENSEMBLE);
		}

		CRSKeyword keyword = readToKeyword(CRSKeyword.ANGLEUNIT, CRSKeyword.ID);

		if (keyword == CRSKeyword.ANGLEUNIT) {
			crs.setUnit(readAngleUnit());
			keyword = readToKeyword(CRSKeyword.ID);
		}

		if (keyword == CRSKeyword.ID) {
			crs.setBaseIdentifiers(readIdentifiers());
		}

		readRightDelimiter();

		readSeparator();
		crs.setMapProjection(readMapProjection());

		readSeparator();
		crs.setCoordinateSystem(readCoordinateSystem());

		readScopeExtentIdentifierRemark(crs);

		readRightDelimiter();

		return crs;
	}

	/**
	 * Read a Vertical CRS
	 * 
	 * @return vertical coordinate reference system
	 * @throws IOException
	 *             upon failure to read
	 */
	public CoordinateReferenceSystem readVertical() throws IOException {

		VerticalCoordinateReferenceSystem baseCrs = new VerticalCoordinateReferenceSystem();
		SimpleCoordinateReferenceSystem crs = baseCrs;
		DerivedCoordinateReferenceSystem derivedCrs = null;

		readKeyword(CRSKeyword.VERTCRS);

		readLeftDelimiter();

		String name = reader.readExpectedToken();

		if (isKeywordNext(CRSKeyword.BASEVERTCRS)) {
			readKeyword(CRSKeyword.BASEVERTCRS);

			derivedCrs = new DerivedCoordinateReferenceSystem();
			derivedCrs.setBase(baseCrs);
			crs = derivedCrs;

			readLeftDelimiter();
			baseCrs.setName(reader.readExpectedToken());
		}

		crs.setName(name);

		boolean isDynamic = isKeywordNext(CRSKeyword.DYNAMIC);
		if (isDynamic) {
			readSeparator();
			baseCrs.setDynamic(readDynamic());
		}

		if (isDynamic || isKeywordNext(CRSKeyword.VDATUM)) {
			readSeparator();
			baseCrs.setReferenceFrame(readVerticalReferenceFrame());
		} else if (isKeywordNext(CRSKeyword.ENSEMBLE)) {
			readSeparator();
			baseCrs.setDatumEnsemble(readVerticalDatumEnsemble());
		} else {
			// Validation error
			readSeparator();
			readKeyword(CRSKeyword.VDATUM, CRSKeyword.ENSEMBLE);
		}

		if (derivedCrs != null) {

			CRSKeyword keyword = readToKeyword(CRSKeyword.ID);
			if (keyword == CRSKeyword.ID) {
				baseCrs.setIdentifiers(readIdentifiers());
			}

			readRightDelimiter();

			readSeparator();
			derivedCrs.setConversion(readDerivingConversion());

		}

		readSeparator();
		crs.setCoordinateSystem(readCoordinateSystem());

		if (derivedCrs == null && isKeywordNext(CRSKeyword.GEOIDMODEL)) {
			readSeparator();
			readKeyword(CRSKeyword.GEOIDMODEL);
			readLeftDelimiter();
			baseCrs.setGeoidModelName(reader.readExpectedToken());
			CRSKeyword keyword = readToKeyword(CRSKeyword.ID);
			if (keyword == CRSKeyword.ID) {
				baseCrs.setGeoidModelIdentifier(readIdentifier());
			}
			readRightDelimiter();
		}

		readScopeExtentIdentifierRemark(crs);

		readRightDelimiter();

		return crs;
	}

	/**
	 * Read an Engineering CRS
	 * 
	 * @return engineering coordinate reference system
	 * @throws IOException
	 *             upon failure to read
	 */
	public CoordinateReferenceSystem readEngineering() throws IOException {

		EngineeringCoordinateReferenceSystem baseCrs = new EngineeringCoordinateReferenceSystem();
		SimpleCoordinateReferenceSystem crs = baseCrs;
		DerivedCoordinateReferenceSystem derivedCrs = null;

		readKeyword(CRSKeyword.ENGCRS);

		readLeftDelimiter();

		String name = reader.readExpectedToken();

		if (isKeywordNext(CRSKeyword.BASEENGCRS)) {
			readKeyword(CRSKeyword.BASEENGCRS);

			derivedCrs = new DerivedCoordinateReferenceSystem();
			derivedCrs.setBase(baseCrs);
			crs = derivedCrs;

			readLeftDelimiter();
			baseCrs.setName(reader.readExpectedToken());
		}

		crs.setName(name);

		readSeparator();
		baseCrs.setDatum(readEngineeringDatum());

		if (derivedCrs != null) {

			CRSKeyword keyword = readToKeyword(CRSKeyword.ID);
			if (keyword == CRSKeyword.ID) {
				baseCrs.setIdentifiers(readIdentifiers());
			}

			readRightDelimiter();

			readSeparator();
			derivedCrs.setConversion(readDerivingConversion());

		}

		readSeparator();
		crs.setCoordinateSystem(readCoordinateSystem());

		readScopeExtentIdentifierRemark(crs);

		readRightDelimiter();

		return crs;
	}

	/**
	 * Read a Parametric CRS
	 * 
	 * @return parametric coordinate reference system
	 * @throws IOException
	 *             upon failure to read
	 */
	public CoordinateReferenceSystem readParametric() throws IOException {

		ParametricCoordinateReferenceSystem baseCrs = new ParametricCoordinateReferenceSystem();
		SimpleCoordinateReferenceSystem crs = baseCrs;
		DerivedCoordinateReferenceSystem derivedCrs = null;

		readKeyword(CRSKeyword.PARAMETRICCRS);

		readLeftDelimiter();

		String name = reader.readExpectedToken();

		if (isKeywordNext(CRSKeyword.BASEPARAMCRS)) {
			readKeyword(CRSKeyword.BASEPARAMCRS);

			derivedCrs = new DerivedCoordinateReferenceSystem();
			derivedCrs.setBase(baseCrs);
			crs = derivedCrs;

			readLeftDelimiter();
			baseCrs.setName(reader.readExpectedToken());
		}

		crs.setName(name);

		readSeparator();
		baseCrs.setDatum(readParametricDatum());

		if (derivedCrs != null) {

			CRSKeyword keyword = readToKeyword(CRSKeyword.ID);
			if (keyword == CRSKeyword.ID) {
				baseCrs.setIdentifiers(readIdentifiers());
			}

			readRightDelimiter();

			readSeparator();
			derivedCrs.setConversion(readDerivingConversion());

		}

		readSeparator();
		crs.setCoordinateSystem(readCoordinateSystem());

		readScopeExtentIdentifierRemark(crs);

		readRightDelimiter();

		return crs;
	}

	/**
	 * Read a Temporal CRS
	 * 
	 * @return temporal coordinate reference system
	 * @throws IOException
	 *             upon failure to read
	 */
	public CoordinateReferenceSystem readTemporal() throws IOException {

		TemporalCoordinateReferenceSystem baseCrs = new TemporalCoordinateReferenceSystem();
		SimpleCoordinateReferenceSystem crs = baseCrs;
		DerivedCoordinateReferenceSystem derivedCrs = null;

		readKeyword(CRSKeyword.TIMECRS);

		readLeftDelimiter();

		String name = reader.readExpectedToken();

		if (isKeywordNext(CRSKeyword.BASETIMECRS)) {
			readKeyword(CRSKeyword.BASETIMECRS);

			derivedCrs = new DerivedCoordinateReferenceSystem();
			derivedCrs.setBase(baseCrs);
			crs = derivedCrs;

			readLeftDelimiter();
			baseCrs.setName(reader.readExpectedToken());
		}

		crs.setName(name);

		readSeparator();
		baseCrs.setDatum(readTemporalDatum());

		if (derivedCrs != null) {

			CRSKeyword keyword = readToKeyword(CRSKeyword.ID);
			if (keyword == CRSKeyword.ID) {
				baseCrs.setIdentifiers(readIdentifiers());
			}

			readRightDelimiter();

			readSeparator();
			derivedCrs.setConversion(readDerivingConversion());

		}

		readSeparator();
		crs.setCoordinateSystem(readCoordinateSystem());

		readScopeExtentIdentifierRemark(crs);

		readRightDelimiter();

		return crs;
	}

	/**
	 * Read a Derived Projected CRS
	 * 
	 * @return derived coordinate reference system
	 * @throws IOException
	 *             upon failure to read
	 */
	public DerivedCoordinateReferenceSystem readDerivedProjected()
			throws IOException {

		DerivedCoordinateReferenceSystem crs = new DerivedCoordinateReferenceSystem();
		ProjectedCoordinateReferenceSystem projectedCrs = new ProjectedCoordinateReferenceSystem();
		crs.setBase(projectedCrs);

		readKeyword(CRSKeyword.DERIVEDPROJCRS);

		readLeftDelimiter();

		crs.setName(reader.readExpectedToken());

		readSeparator();
		readKeyword(CRSKeyword.BASEPROJCRS);

		readLeftDelimiter();

		projectedCrs.setName(reader.readExpectedToken());

		readSeparator();
		CRSKeyword keyword = readKeyword(CRSKeyword.BASEGEODCRS,
				CRSKeyword.BASEGEOGCRS);
		projectedCrs.setBaseType(
				WKTUtils.getCoordinateReferenceSystemType(keyword));

		readLeftDelimiter();
		projectedCrs.setBaseName(reader.readExpectedToken());

		boolean isDynamic = isKeywordNext(CRSKeyword.DYNAMIC);
		if (isDynamic) {
			readSeparator();
			projectedCrs.setDynamic(readDynamic());
		}

		if (isDynamic || isKeywordNext(CRSKeyword.DATUM)) {
			readSeparator();
			GeoReferenceFrame referenceFrame = readGeoReferenceFrame();
			referenceFrame.setType(projectedCrs.getBaseType());
			projectedCrs.setReferenceFrame(referenceFrame);
		} else if (isKeywordNext(CRSKeyword.ENSEMBLE)) {
			readSeparator();
			projectedCrs.setDatumEnsemble(readGeoDatumEnsemble());
		} else {
			// Validation error
			readSeparator();
			readKeyword(CRSKeyword.DATUM, CRSKeyword.ENSEMBLE);
		}

		keyword = readToKeyword(CRSKeyword.ID);
		if (keyword == CRSKeyword.ID) {
			projectedCrs.setBaseIdentifiers(readIdentifiers());
		}

		readRightDelimiter();

		readSeparator();
		projectedCrs.setMapProjection(readMapProjection());

		keyword = readToKeyword(CRSKeyword.ID);
		if (keyword == CRSKeyword.ID) {
			projectedCrs.setIdentifiers(readIdentifiers());
		}

		readRightDelimiter();

		readSeparator();
		crs.setConversion(readDerivingConversion());

		readSeparator();
		crs.setCoordinateSystem(readCoordinateSystem());

		readScopeExtentIdentifierRemark(crs);

		readRightDelimiter();

		return crs;
	}

	/**
	 * Read a Compound CRS
	 * 
	 * @return compound coordinate reference system
	 * @throws IOException
	 *             upon failure to read
	 */
	public CompoundCoordinateReferenceSystem readCompound() throws IOException {

		CompoundCoordinateReferenceSystem crs = new CompoundCoordinateReferenceSystem();

		readKeyword(CRSKeyword.COMPOUNDCRS);

		readLeftDelimiter();

		crs.setName(reader.readExpectedToken());

		while (isKeywordNext(CRSKeyword.GEODCRS, CRSKeyword.GEOGCRS,
				CRSKeyword.GEOCCS, CRSKeyword.GEOGCS, CRSKeyword.PROJCRS,
				CRSKeyword.PROJCS, CRSKeyword.VERTCRS, CRSKeyword.VERT_CS,
				CRSKeyword.ENGCRS, CRSKeyword.LOCAL_CS,
				CRSKeyword.PARAMETRICCRS, CRSKeyword.TIMECRS,
				CRSKeyword.DERIVEDPROJCRS)) {

			readSeparator();
			crs.addCoordinateReferenceSystem(
					readSimpleCoordinateReferenceSystem());
		}

		readScopeExtentIdentifierRemark(crs);

		readRightDelimiter();

		if (crs.getCoordinateReferenceSystems().size() < 2) {
			String message = "Compound Coordinate Reference System requires at least two Coordinate Reference Systems";
			if (strict) {
				throw new ProjectionException(message);
			} else {
				logger.log(Level.WARNING, message);
			}
		}

		return crs;
	}

	/**
	 * Read Coordinate Metadata
	 * 
	 * @return coordinate metadata
	 * @throws IOException
	 *             upon failure to read
	 */
	public CoordinateMetadata readCoordinateMetadata() throws IOException {

		CoordinateMetadata metadata = new CoordinateMetadata();

		readKeyword(CRSKeyword.COORDINATEMETADATA);

		readLeftDelimiter();

		metadata.setCoordinateReferenceSystem(readCoordinateReferenceSystem());

		if (isKeywordNext(CRSKeyword.EPOCH)) {

			readSeparator();
			readKeyword(CRSKeyword.EPOCH);
			readLeftDelimiter();
			metadata.setEpoch(reader.readUnsignedNumber());
			readRightDelimiter();

		}

		readRightDelimiter();

		return metadata;
	}

	/**
	 * Read Coordinate Operation
	 * 
	 * @return coordinate operation
	 * @throws IOException
	 *             upon failure to read
	 */
	public CoordinateOperation readCoordinateOperation() throws IOException {

		CoordinateOperation operation = new CoordinateOperation();

		readKeyword(CRSKeyword.COORDINATEOPERATION);

		readLeftDelimiter();

		operation.setName(reader.readExpectedToken());

		if (isKeywordNext(CRSKeyword.VERSION)) {
			readSeparator();
			operation.setVersion(readVersion());
		}

		readSeparator();
		operation.setSource(readSource());

		readSeparator();
		operation.setTarget(readTarget());

		readSeparator();
		operation.setMethod(readMethod());

		if (isKeywordNext(CRSKeyword.PARAMETER, CRSKeyword.PARAMETERFILE)) {
			readSeparator();
			operation.setParameters(readCoordinateOperationParameters());
		}

		if (isKeywordNext(CRSKeyword.INTERPOLATIONCRS)) {
			readSeparator();
			operation.setInterpolation(readInterpolation());
		}

		if (isKeywordNext(CRSKeyword.OPERATIONACCURACY)) {
			readSeparator();
			operation.setAccuracy(readAccuracy());
		}

		readScopeExtentIdentifierRemark(operation);

		readRightDelimiter();

		return operation;
	}

	/**
	 * Read the usages (scope and extent), identifiers, and remark into the CRS
	 * 
	 * @param crs
	 *            coordinate reference system
	 * @throws IOException
	 *             upon failure to read
	 */
	public void readScopeExtentIdentifierRemark(CommonCRS crs)
			throws IOException {

		CRSKeyword keyword = readToKeyword(CRSKeyword.USAGE, CRSKeyword.ID,
				CRSKeyword.REMARK);

		if (keyword == CRSKeyword.USAGE) {
			crs.setUsages(readUsages());
			keyword = readToKeyword(CRSKeyword.ID, CRSKeyword.REMARK);
		}

		if (keyword == CRSKeyword.ID) {
			crs.setIdentifiers(readIdentifiers());
			keyword = readToKeyword(CRSKeyword.REMARK);
		}

		if (keyword == CRSKeyword.REMARK) {
			crs.setRemark(readRemark());
		}

	}

	/**
	 * Read a Geo reference frame
	 * 
	 * @return geo reference frame
	 * @throws IOException
	 *             upon failure to read
	 */
	public GeoReferenceFrame readGeoReferenceFrame() throws IOException {
		ReferenceFrame referenceFrame = readReferenceFrame();
		if (!(referenceFrame instanceof GeoReferenceFrame)) {
			throw new ProjectionException(
					"Reference frame was not an expected Geo Reference Frame");
		}
		return (GeoReferenceFrame) referenceFrame;
	}

	/**
	 * Read a Vertical reference frame
	 * 
	 * @return vertical reference frame
	 * @throws IOException
	 *             upon failure to read
	 */
	public VerticalReferenceFrame readVerticalReferenceFrame()
			throws IOException {
		ReferenceFrame referenceFrame = readReferenceFrame();
		if (!(referenceFrame instanceof VerticalReferenceFrame)) {
			throw new ProjectionException(
					"Reference frame was not an expected Vertical Reference Frame");
		}
		return (VerticalReferenceFrame) referenceFrame;
	}

	/**
	 * Read an Engineering datum
	 * 
	 * @return engineering datum
	 * @throws IOException
	 *             upon failure to read
	 */
	public EngineeringDatum readEngineeringDatum() throws IOException {
		ReferenceFrame referenceFrame = readReferenceFrame();
		if (!(referenceFrame instanceof EngineeringDatum)) {
			throw new ProjectionException(
					"Reference frame was not an expected Engineering Datum");
		}
		return (EngineeringDatum) referenceFrame;
	}

	/**
	 * Read a Parametric datum
	 * 
	 * @return parametric datum
	 * @throws IOException
	 *             upon failure to read
	 */
	public ParametricDatum readParametricDatum() throws IOException {
		ReferenceFrame referenceFrame = readReferenceFrame();
		if (!(referenceFrame instanceof ParametricDatum)) {
			throw new ProjectionException(
					"Reference frame was not an expected Parametric Datum");
		}
		return (ParametricDatum) referenceFrame;
	}

	/**
	 * Read a Reference frame (datum)
	 * 
	 * @return reference frame
	 * @throws IOException
	 *             upon failure to read
	 */
	public ReferenceFrame readReferenceFrame() throws IOException {

		ReferenceFrame referenceFrame = null;
		GeoReferenceFrame geoReferenceFrame = null;

		CRSKeyword type = readKeyword(CRSKeyword.DATUM, CRSKeyword.VDATUM,
				CRSKeyword.EDATUM, CRSKeyword.PDATUM);
		switch (type) {
		case DATUM:
			geoReferenceFrame = new GeoReferenceFrame();
			referenceFrame = geoReferenceFrame;
			break;
		case VDATUM:
			referenceFrame = new VerticalReferenceFrame();
			break;
		case EDATUM:
			referenceFrame = new EngineeringDatum();
			break;
		case PDATUM:
			referenceFrame = new ParametricDatum();
			break;
		default:
			throw new ProjectionException(
					"Unexpected Reference Frame type: " + type);
		}

		readLeftDelimiter();

		referenceFrame.setName(reader.readExpectedToken());

		if (geoReferenceFrame != null) {
			readSeparator();
			geoReferenceFrame.setEllipsoid(readEllipsoid());
		}

		CRSKeyword keyword = readToKeyword(CRSKeyword.TOWGS84,
				CRSKeyword.ANCHOR, CRSKeyword.ID);

		if (keyword == CRSKeyword.TOWGS84) {
			extras.put(CRSKeyword.TOWGS84.name(), readToWGS84Compat());
			keyword = readToKeyword(CRSKeyword.ANCHOR, CRSKeyword.ID);
		}

		if (keyword == CRSKeyword.ANCHOR) {
			referenceFrame
					.setAnchor(readKeywordDelimitedToken(CRSKeyword.ANCHOR));
			keyword = readToKeyword(CRSKeyword.ID, CRSKeyword.TOWGS84);
		}

		if (keyword == CRSKeyword.ID) {
			referenceFrame.setIdentifiers(readIdentifiers());
			keyword = readToKeyword(CRSKeyword.TOWGS84);
		}

		if (keyword == CRSKeyword.TOWGS84) {
			extras.put(CRSKeyword.TOWGS84.name(), readToWGS84Compat());
		}

		readRightDelimiter();

		if (geoReferenceFrame != null && isKeywordNext(CRSKeyword.PRIMEM)) {
			readSeparator();
			geoReferenceFrame.setPrimeMeridian(readPrimeMeridian());
		}

		return referenceFrame;
	}

	/**
	 * Read a Geo datum ensemble
	 * 
	 * @return geo datum ensemble
	 * @throws IOException
	 *             upon failure to read
	 */
	public GeoDatumEnsemble readGeoDatumEnsemble() throws IOException {
		DatumEnsemble datumEnsemble = readDatumEnsemble();
		if (!(datumEnsemble instanceof GeoDatumEnsemble)) {
			throw new ProjectionException(
					"Datum ensemble was not an expected Geo Datum Ensemble");
		}
		return (GeoDatumEnsemble) datumEnsemble;
	}

	/**
	 * Read a Vertical datum ensemble
	 * 
	 * @return vertical datum ensemble
	 * @throws IOException
	 *             upon failure to read
	 */
	public VerticalDatumEnsemble readVerticalDatumEnsemble()
			throws IOException {
		DatumEnsemble datumEnsemble = readDatumEnsemble();
		if (!(datumEnsemble instanceof VerticalDatumEnsemble)) {
			throw new ProjectionException(
					"Datum ensemble was not an expected Vertical Datum Ensemble");
		}
		return (VerticalDatumEnsemble) datumEnsemble;
	}

	/**
	 * Read a Datum ensemble
	 * 
	 * @return datum ensemble
	 * @throws IOException
	 *             upon failure to read
	 */
	public DatumEnsemble readDatumEnsemble() throws IOException {

		readKeyword(CRSKeyword.ENSEMBLE);

		readLeftDelimiter();

		String name = reader.readExpectedToken();

		List<DatumEnsembleMember> members = new ArrayList<>();
		do {

			readSeparator();
			members.add(readDatumEnsembleMember());

		} while (isKeywordNext(CRSKeyword.MEMBER));

		DatumEnsemble datumEnsemble = null;
		GeoDatumEnsemble geoDatumEnsemble = null;

		if (isKeywordNext(CRSKeyword.ELLIPSOID)) {
			geoDatumEnsemble = new GeoDatumEnsemble();
			datumEnsemble = geoDatumEnsemble;
		} else {
			datumEnsemble = new VerticalDatumEnsemble();
		}

		datumEnsemble.setName(name);
		datumEnsemble.setMembers(members);

		if (geoDatumEnsemble != null) {
			readSeparator();
			geoDatumEnsemble.setEllipsoid(readEllipsoid());
		}

		readSeparator();
		readKeyword(CRSKeyword.ENSEMBLEACCURACY);

		readLeftDelimiter();

		datumEnsemble.setAccuracy(reader.readNumber());

		readRightDelimiter();

		CRSKeyword keyword = readToKeyword(CRSKeyword.ID);
		if (keyword == CRSKeyword.ID) {
			datumEnsemble.setIdentifiers(readIdentifiers());
		}

		readRightDelimiter();

		if (geoDatumEnsemble != null && isKeywordNext(CRSKeyword.PRIMEM)) {
			// TODO http://ogc.standardstracker.org/show_request.cgi?id=672
			readSeparator();
			geoDatumEnsemble.setPrimeMeridian(readPrimeMeridian());
		}

		return datumEnsemble;
	}

	/**
	 * Read a Datum ensemble member
	 * 
	 * @return datum ensemble member
	 * @throws IOException
	 *             upon failure to read
	 */
	public DatumEnsembleMember readDatumEnsembleMember() throws IOException {

		DatumEnsembleMember member = new DatumEnsembleMember();

		readKeyword(CRSKeyword.MEMBER);

		readLeftDelimiter();

		member.setName(reader.readExpectedToken());

		CRSKeyword keyword = readToKeyword(CRSKeyword.ID);
		if (keyword == CRSKeyword.ID) {
			member.setIdentifiers(readIdentifiers());
		}

		readRightDelimiter();

		return member;
	}

	/**
	 * Read a Dynamic coordinate reference system
	 * 
	 * @return dynamic
	 * @throws IOException
	 *             upon failure to read
	 */
	public Dynamic readDynamic() throws IOException {

		Dynamic dynamic = new Dynamic();

		readKeyword(CRSKeyword.DYNAMIC);

		readLeftDelimiter();

		readKeyword(CRSKeyword.FRAMEEPOCH);

		readLeftDelimiter();

		dynamic.setReferenceEpoch(reader.readUnsignedNumber());

		readRightDelimiter();

		CRSKeyword keyword = readToKeyword(CRSKeyword.MODEL);
		if (keyword == CRSKeyword.MODEL) {

			readKeyword(CRSKeyword.MODEL);

			readLeftDelimiter();

			dynamic.setDeformationModelName(reader.readExpectedToken());

			keyword = readToKeyword(CRSKeyword.ID);
			if (keyword == CRSKeyword.ID) {
				dynamic.setDeformationModelIdentifiers(readIdentifiers());
			}

			readRightDelimiter();
		}

		readRightDelimiter();

		return dynamic;
	}

	/**
	 * Read a Prime meridian
	 * 
	 * @return prime meridian
	 * @throws IOException
	 *             upon failure to read
	 */
	public PrimeMeridian readPrimeMeridian() throws IOException {

		PrimeMeridian primeMeridian = new PrimeMeridian();

		readKeyword(CRSKeyword.PRIMEM);

		readLeftDelimiter();

		primeMeridian.setName(reader.readExpectedToken());

		readSeparator();
		primeMeridian.setIrmLongitude(reader.readNumber());

		CRSKeyword keyword = readToKeyword(CRSKeyword.ANGLEUNIT, CRSKeyword.ID);

		if (keyword == CRSKeyword.ANGLEUNIT) {
			primeMeridian.setIrmLongitudeUnit(readAngleUnit());
			keyword = readToKeyword(CRSKeyword.ID);
		}

		if (keyword == CRSKeyword.ID) {
			primeMeridian.setIdentifiers(readIdentifiers());
		}

		readRightDelimiter();

		return primeMeridian;
	}

	/**
	 * Read an Ellipsoid
	 * 
	 * @return ellipsoid
	 * @throws IOException
	 *             upon failure to read
	 */
	public Ellipsoid readEllipsoid() throws IOException {

		Ellipsoid ellipsoid = new Ellipsoid();

		readKeyword(CRSKeyword.ELLIPSOID);

		readLeftDelimiter();

		ellipsoid.setName(reader.readExpectedToken());

		readSeparator();
		ellipsoid.setSemiMajorAxis(reader.readUnsignedNumber());

		readSeparator();
		ellipsoid.setInverseFlattening(reader.readUnsignedNumber());

		CRSKeyword keyword = readToKeyword(CRSKeyword.LENGTHUNIT,
				CRSKeyword.ID);

		if (keyword == CRSKeyword.LENGTHUNIT) {
			ellipsoid.setUnit(readLengthUnit());
			keyword = readToKeyword(CRSKeyword.ID);
		}

		if (keyword == CRSKeyword.ID) {
			ellipsoid.setIdentifiers(readIdentifiers());
		}

		readRightDelimiter();

		return ellipsoid;
	}

	/**
	 * Read a Unit
	 * 
	 * @return unit
	 * @throws IOException
	 *             upon failure to read
	 */
	public Unit readUnit() throws IOException {
		return readUnit(UnitType.UNIT);
	}

	/**
	 * Read an Angle Unit
	 * 
	 * @return angle unit
	 * @throws IOException
	 *             upon failure to read
	 */
	public Unit readAngleUnit() throws IOException {
		return readUnit(UnitType.ANGLEUNIT);
	}

	/**
	 * Read a Length Unit
	 * 
	 * @return length unit
	 * @throws IOException
	 *             upon failure to read
	 */
	public Unit readLengthUnit() throws IOException {
		return readUnit(UnitType.LENGTHUNIT);
	}

	/**
	 * Read a Parametric Unit
	 * 
	 * @return parametric unit
	 * @throws IOException
	 *             upon failure to read
	 */
	public Unit readParametricUnit() throws IOException {
		return readUnit(UnitType.PARAMETRICUNIT);
	}

	/**
	 * Read a Scale Unit
	 * 
	 * @return scale unit
	 * @throws IOException
	 *             upon failure to read
	 */
	public Unit readScaleUnit() throws IOException {
		return readUnit(UnitType.SCALEUNIT);
	}

	/**
	 * Read a Time Unit
	 * 
	 * @return time unit
	 * @throws IOException
	 *             upon failure to read
	 */
	public Unit readTimeUnit() throws IOException {
		return readUnit(UnitType.TIMEUNIT);
	}

	/**
	 * Read a Unit
	 * 
	 * @param type
	 *            expected unit type
	 * @return unit
	 * @throws IOException
	 *             upon failure to read
	 */
	public Unit readUnit(UnitType type) throws IOException {

		Unit unit = new Unit();

		Set<CRSKeyword> keywords = readKeywords();
		if (type != UnitType.UNIT) {
			CRSKeyword crsType = CRSKeyword.getType(type.name());
			validateKeyword(keywords, crsType);
		} else if (keywords.size() == 1) {
			type = WKTUtils.getUnitType(keywords.iterator().next());
		} else if (keywords.isEmpty()) {
			throw new ProjectionException("Unexpected unit keyword. found: "
					+ keywordNames(keywords));
		}
		unit.setType(type);

		readLeftDelimiter();

		unit.setName(reader.readExpectedToken());

		if (type != UnitType.TIMEUNIT || isNonKeywordNext()) {
			readSeparator();
			unit.setConversionFactor(reader.readUnsignedNumber());
		}

		CRSKeyword keyword = readToKeyword(CRSKeyword.ID);
		if (keyword == CRSKeyword.ID) {
			unit.setIdentifiers(readIdentifiers());
		}

		readRightDelimiter();

		return unit;
	}

	/**
	 * Read Identifiers
	 * 
	 * @return identifiers
	 * @throws IOException
	 *             upon failure to read
	 */
	public List<Identifier> readIdentifiers() throws IOException {

		List<Identifier> identifiers = new ArrayList<>();

		do {

			if (!identifiers.isEmpty()) {
				readSeparator();
			}

			identifiers.add(readIdentifier());

		} while (isKeywordNext(CRSKeyword.ID));

		return identifiers;
	}

	/**
	 * Read an Identifier
	 * 
	 * @return identifier
	 * @throws IOException
	 *             upon failure to read
	 */
	public Identifier readIdentifier() throws IOException {

		Identifier identifier = new Identifier();

		readKeyword(CRSKeyword.ID);

		readLeftDelimiter();

		identifier.setName(reader.readExpectedToken());

		readSeparator();
		identifier.setUniqueIdentifier(reader.readExpectedToken());

		if (isNonKeywordNext()) {
			readSeparator();
			identifier.setVersion(reader.readExpectedToken());
		}

		CRSKeyword keyword = readToKeyword(CRSKeyword.CITATION, CRSKeyword.URI);

		if (keyword == CRSKeyword.CITATION) {
			identifier.setCitation(
					readKeywordDelimitedToken(CRSKeyword.CITATION));
			keyword = readToKeyword(CRSKeyword.URI);
		}

		if (keyword == CRSKeyword.URI) {
			identifier.setUri(readKeywordDelimitedToken(CRSKeyword.URI));
		}

		readRightDelimiter();

		return identifier;
	}

	/**
	 * Read a Coordinate system
	 * 
	 * @return coordinate system
	 * @throws IOException
	 *             upon failure to read
	 */
	public CoordinateSystem readCoordinateSystem() throws IOException {

		CoordinateSystem coordinateSystem = new CoordinateSystem();

		readKeyword(CRSKeyword.CS);

		readLeftDelimiter();

		String csTypeName = reader.readToken();
		CoordinateSystemType csType = CoordinateSystemType.getType(csTypeName);
		if (csType == null) {
			throw new ProjectionException(
					"Unexpected coordinate system type. found: " + csTypeName);
		}
		coordinateSystem.setType(csType);

		readSeparator();
		coordinateSystem.setDimension(reader.readUnsignedInteger());

		CRSKeyword keyword = readToKeyword(CRSKeyword.ID);
		if (keyword == CRSKeyword.ID) {
			coordinateSystem.setIdentifiers(readIdentifiers());
		}

		readRightDelimiter();

		readSeparator();
		coordinateSystem.setAxes(readAxes(csType));

		if (WKTUtils.isSpatial(csType)) {

			if (isUnitNext()) {

				readSeparator();
				coordinateSystem.setUnit(readUnit());

			}

		}

		return coordinateSystem;
	}

	/**
	 * Read Axes
	 * 
	 * @return axes
	 * @throws IOException
	 *             upon failure to read
	 */
	public List<Axis> readAxes() throws IOException {
		return readAxes(null);
	}

	/**
	 * Read Axes
	 * 
	 * @param type
	 *            coordinate system type
	 * 
	 * @return axes
	 * @throws IOException
	 *             upon failure to read
	 */
	public List<Axis> readAxes(CoordinateSystemType type) throws IOException {

		boolean isTemporalCountMeasure = type != null
				&& WKTUtils.isTemporalCountMeasure(type);

		List<Axis> axes = new ArrayList<>();

		do {

			if (!axes.isEmpty()) {
				readSeparator();
			}

			axes.add(readAxis(type));

			if (isTemporalCountMeasure) {
				break;
			}

		} while (isKeywordNext(CRSKeyword.AXIS));

		return axes;
	}

	/**
	 * Read an Axis
	 * 
	 * @return axis
	 * @throws IOException
	 *             upon failure to read
	 */
	public Axis readAxis() throws IOException {
		return readAxis(null);
	}

	/**
	 * Read an Axis
	 * 
	 * @param type
	 *            coordinate system type
	 * @return axis
	 * @throws IOException
	 *             upon failure to read
	 */
	public Axis readAxis(CoordinateSystemType type) throws IOException {

		Axis axis = new Axis();

		readKeyword(CRSKeyword.AXIS);

		readLeftDelimiter();

		String nameAbbrev = reader.readExpectedToken();
		if (nameAbbrev.matches(AXIS_NAME_ABBREV_PATTERN)) {
			int abbrevIndex = nameAbbrev
					.lastIndexOf(WKTConstants.AXIS_ABBREV_LEFT_DELIMITER);
			axis.setAbbreviation(nameAbbrev.substring(abbrevIndex + 1,
					nameAbbrev.length() - 1));
			if (abbrevIndex > 0) {
				axis.setName(nameAbbrev.substring(0, abbrevIndex - 1));
			}
		} else {
			axis.setName(nameAbbrev);
		}

		readSeparator();

		String axisDirectionTypeName = reader.readToken();
		AxisDirectionType axisDirectionType = AxisDirectionType
				.getType(axisDirectionTypeName);
		if (axisDirectionType == null) {
			if (axisDirectionTypeName
					.equalsIgnoreCase(WKTConstants.AXIS_DIRECTION_OTHER)) {
				axisDirectionType = AxisDirectionType.UNSPECIFIED;
			} else {
				throw new ProjectionException(
						"Unexpected axis direction type. found: "
								+ axisDirectionTypeName);
			}
		}
		axis.setDirection(axisDirectionType);

		if (type != null) {

			switch (axisDirectionType) {

			case NORTH:
			case SOUTH:

				if (isKeywordNext(CRSKeyword.MERIDIAN)) {

					readSeparator();
					readKeyword(CRSKeyword.MERIDIAN);

					readLeftDelimiter();

					axis.setMeridian(reader.readNumber());

					readSeparator();
					axis.setMeridianUnit(readAngleUnit());

					readRightDelimiter();

				}

				break;

			case CLOCKWISE:
			case COUNTER_CLOCKWISE:

				readSeparator();
				readKeyword(CRSKeyword.BEARING);

				readLeftDelimiter();

				axis.setBearing(reader.readNumber());

				readRightDelimiter();

				break;

			default:
			}

			if (isKeywordNext(CRSKeyword.ORDER)) {

				readSeparator();
				readKeyword(CRSKeyword.ORDER);

				readLeftDelimiter();

				axis.setOrder(reader.readUnsignedInteger());

				readRightDelimiter();

			}

			if (WKTUtils.isSpatial(type)) {

				if (isSpatialUnitNext()) {

					readSeparator();
					axis.setUnit(readUnit());

				}

			} else if (WKTUtils.isTemporalCountMeasure(type)) {

				if (isTimeUnitNext()) {

					readSeparator();
					axis.setUnit(readTimeUnit());

				}

			}

			CRSKeyword keyword = readToKeyword(CRSKeyword.ID);
			if (keyword == CRSKeyword.ID) {
				axis.setIdentifiers(readIdentifiers());
			}

		}

		readRightDelimiter();

		return axis;
	}

	/**
	 * Read Remark
	 * 
	 * @return remark
	 * @throws IOException
	 *             upon failure to read
	 */
	public String readRemark() throws IOException {
		return readKeywordDelimitedToken(CRSKeyword.REMARK);
	}

	/**
	 * Read Usages
	 * 
	 * @return usages
	 * @throws IOException
	 *             upon failure to read
	 */
	public List<Usage> readUsages() throws IOException {

		List<Usage> usages = new ArrayList<>();

		do {

			if (!usages.isEmpty()) {
				readSeparator();
			}

			usages.add(readUsage());

		} while (isKeywordNext(CRSKeyword.USAGE));

		return usages;
	}

	/**
	 * Read a Usage
	 * 
	 * @return usage
	 * @throws IOException
	 *             upon failure to read
	 */
	public Usage readUsage() throws IOException {

		Usage usage = new Usage();

		readKeyword(CRSKeyword.USAGE);

		readLeftDelimiter();

		usage.setScope(readScope());

		usage.setExtent(readExtent());

		readRightDelimiter();

		return usage;
	}

	/**
	 * Read a Scope
	 * 
	 * @return scope
	 * @throws IOException
	 *             upon failure to read
	 */
	public String readScope() throws IOException {
		return readKeywordDelimitedToken(CRSKeyword.SCOPE);
	}

	/**
	 * Read an Extent
	 * 
	 * @return extent
	 * @throws IOException
	 *             upon failure to read
	 */
	public Extent readExtent() throws IOException {

		Extent extent = new Extent();

		CRSKeyword keyword = readToKeyword(CRSKeyword.AREA, CRSKeyword.BBOX,
				CRSKeyword.VERTICALEXTENT, CRSKeyword.TIMEEXTENT);

		if (keyword == null) {
			throw new ProjectionException(
					"Missing extent type of [" + CRSKeyword.AREA + ", "
							+ CRSKeyword.BBOX + ", " + CRSKeyword.VERTICALEXTENT
							+ ", " + CRSKeyword.TIMEEXTENT + "]");
		}

		if (keyword == CRSKeyword.AREA) {
			extent.setAreaDescription(readAreaDescription());
			keyword = readToKeyword(CRSKeyword.BBOX, CRSKeyword.VERTICALEXTENT,
					CRSKeyword.TIMEEXTENT);
		}

		if (keyword == CRSKeyword.BBOX) {
			extent.setGeographicBoundingBox(readGeographicBoundingBox());
			keyword = readToKeyword(CRSKeyword.VERTICALEXTENT,
					CRSKeyword.TIMEEXTENT);
		}

		if (keyword == CRSKeyword.VERTICALEXTENT) {
			extent.setVerticalExtent(readVerticalExtent());
			keyword = readToKeyword(CRSKeyword.TIMEEXTENT);
		}

		if (keyword == CRSKeyword.TIMEEXTENT) {
			extent.setTemporalExtent(readTemporalExtent());
		}

		return extent;
	}

	/**
	 * Read an Area description
	 * 
	 * @return area description
	 * @throws IOException
	 *             upon failure to read
	 */
	public String readAreaDescription() throws IOException {
		return readKeywordDelimitedToken(CRSKeyword.AREA);
	}

	/**
	 * Read a Geographic bounding box
	 * 
	 * @return geographic bounding box
	 * @throws IOException
	 *             upon failure to read
	 */
	public GeographicBoundingBox readGeographicBoundingBox()
			throws IOException {

		GeographicBoundingBox boundingBox = new GeographicBoundingBox();

		readKeyword(CRSKeyword.BBOX);

		readLeftDelimiter();

		boundingBox.setLowerLeftLatitude(reader.readNumber());

		readSeparator();
		boundingBox.setLowerLeftLongitude(reader.readNumber());

		readSeparator();
		boundingBox.setUpperRightLatitude(reader.readNumber());

		readSeparator();
		boundingBox.setUpperRightLongitude(reader.readNumber());

		readRightDelimiter();

		return boundingBox;
	}

	/**
	 * Read a Vertical extent
	 * 
	 * @return vertical extent
	 * @throws IOException
	 *             upon failure to read
	 */
	public VerticalExtent readVerticalExtent() throws IOException {

		VerticalExtent verticalExtent = new VerticalExtent();

		readKeyword(CRSKeyword.VERTICALEXTENT);

		readLeftDelimiter();

		verticalExtent.setMinimumHeight(reader.readNumber());

		readSeparator();
		verticalExtent.setMaximumHeight(reader.readNumber());

		CRSKeyword keyword = readToKeyword(CRSKeyword.LENGTHUNIT);
		if (keyword == CRSKeyword.LENGTHUNIT) {
			verticalExtent.setUnit(readLengthUnit());
		}

		readRightDelimiter();

		return verticalExtent;
	}

	/**
	 * Read a Temporal extent
	 * 
	 * @return temporal extent
	 * @throws IOException
	 *             upon failure to read
	 */
	public TemporalExtent readTemporalExtent() throws IOException {

		TemporalExtent temporalExtent = new TemporalExtent();

		readKeyword(CRSKeyword.TIMEEXTENT);

		readLeftDelimiter();

		temporalExtent.setStart(reader.readExpectedToken());

		readSeparator();
		temporalExtent.setEnd(reader.readExpectedToken());

		readRightDelimiter();

		return temporalExtent;
	}

	/**
	 * Read a Map projection
	 * 
	 * @return map projection
	 * @throws IOException
	 *             upon failure to read
	 */
	public MapProjection readMapProjection() throws IOException {

		MapProjection mapProjection = new MapProjection();

		readKeyword(CRSKeyword.CONVERSION);

		readLeftDelimiter();

		mapProjection.setName(reader.readExpectedToken());

		readSeparator();
		mapProjection.setMethod(readMethod());

		CRSKeyword keyword = readToKeyword(CRSKeyword.PARAMETER, CRSKeyword.ID);

		if (keyword == CRSKeyword.PARAMETER) {
			mapProjection.setParameters(readMapProjectionParameters());
			keyword = readToKeyword(CRSKeyword.ID);
		}

		if (keyword == CRSKeyword.ID) {
			mapProjection.setIdentifiers(readIdentifiers());
		}

		readRightDelimiter();

		return mapProjection;
	}

	/**
	 * Read an operation method
	 * 
	 * @return operation method
	 * @throws IOException
	 *             upon failure to read
	 */
	public OperationMethod readMethod() throws IOException {

		OperationMethod method = new OperationMethod();

		readKeyword(CRSKeyword.METHOD);

		readLeftDelimiter();

		method.setName(reader.readExpectedToken());

		CRSKeyword keyword = readToKeyword(CRSKeyword.ID);
		if (keyword == CRSKeyword.ID) {
			method.setIdentifiers(readIdentifiers());
		}

		readRightDelimiter();

		return method;
	}

	/**
	 * Read Map projection parameters
	 * 
	 * @return map projection parameters
	 * @throws IOException
	 *             upon failure to read
	 */
	public List<OperationParameter> readMapProjectionParameters()
			throws IOException {
		return readParameters(CRSType.PROJECTED);
	}

	/**
	 * Read Operation parameters
	 * 
	 * @param type
	 *            coordinate reference system type
	 * 
	 * @return operation parameters
	 * @throws IOException
	 *             upon failure to read
	 */
	public List<OperationParameter> readParameters(CRSType type)
			throws IOException {

		List<OperationParameter> parameters = new ArrayList<>();

		do {

			if (!parameters.isEmpty()) {
				readSeparator();
			}

			parameters.add(readParameter(type));

		} while (isKeywordNext(CRSKeyword.PARAMETER));

		return parameters;
	}

	/**
	 * Read an Operation parameter
	 * 
	 * @param type
	 *            coordinate reference system type
	 * 
	 * @return operation parameter
	 * @throws IOException
	 *             upon failure to read
	 */
	public OperationParameter readParameter(CRSType type) throws IOException {

		OperationParameter parameter = new OperationParameter();

		readKeyword(CRSKeyword.PARAMETER);

		readLeftDelimiter();

		parameter.setName(reader.readExpectedToken());

		readSeparator();
		parameter.setValue(reader.readNumber());

		CRSKeyword[] keywords = null;
		switch (type) {
		case PROJECTED:
			keywords = new CRSKeyword[] { CRSKeyword.LENGTHUNIT,
					CRSKeyword.ANGLEUNIT, CRSKeyword.SCALEUNIT, CRSKeyword.ID };
			break;
		case DERIVED:
		case COORDINATE_OPERATION:
			keywords = new CRSKeyword[] { CRSKeyword.LENGTHUNIT,
					CRSKeyword.ANGLEUNIT, CRSKeyword.SCALEUNIT,
					CRSKeyword.TIMEUNIT, CRSKeyword.PARAMETRICUNIT,
					CRSKeyword.ID };
			break;
		case BOUND:
			keywords = new CRSKeyword[] { CRSKeyword.ID };
			break;
		default:
			throw new ProjectionException("Unsupported CRS Type: " + type);
		}

		CRSKeyword keyword = readToKeyword(keywords);

		if (keyword != null && keyword != CRSKeyword.ID) {
			parameter.setUnit(readUnit());
			keyword = readToKeyword(CRSKeyword.ID);
		}

		if (keyword == CRSKeyword.ID) {
			parameter.setIdentifiers(readIdentifiers());
		}

		readRightDelimiter();

		return parameter;
	}

	/**
	 * Read a Temporal Datum
	 * 
	 * @return temporal datum
	 * @throws IOException
	 *             upon failure to read
	 */
	public TemporalDatum readTemporalDatum() throws IOException {

		TemporalDatum temporalDatum = new TemporalDatum();

		readKeyword(CRSKeyword.TDATUM);

		readLeftDelimiter();

		temporalDatum.setName(reader.readExpectedToken());

		CRSKeyword keyword = readToKeyword(CRSKeyword.CALENDAR,
				CRSKeyword.TIMEORIGIN, CRSKeyword.ID);

		if (keyword == CRSKeyword.CALENDAR) {
			temporalDatum.setCalendar(
					readKeywordDelimitedToken(CRSKeyword.CALENDAR));
			keyword = readToKeyword(CRSKeyword.TIMEORIGIN, CRSKeyword.ID);
		}

		if (keyword == CRSKeyword.TIMEORIGIN) {
			temporalDatum.setOrigin(
					readKeywordDelimitedToken(CRSKeyword.TIMEORIGIN));
			keyword = readToKeyword(CRSKeyword.ID);
		}

		if (keyword == CRSKeyword.ID) {
			temporalDatum.setIdentifiers(readIdentifiers());
		}

		readRightDelimiter();

		return temporalDatum;
	}

	/**
	 * Read a Deriving Conversion
	 * 
	 * @return deriving conversion
	 * @throws IOException
	 *             upon failure to read
	 */
	public DerivingConversion readDerivingConversion() throws IOException {

		DerivingConversion derivingConversion = new DerivingConversion();

		readKeyword(CRSKeyword.DERIVINGCONVERSION);

		readLeftDelimiter();

		derivingConversion.setName(reader.readExpectedToken());

		readSeparator();
		derivingConversion.setMethod(readMethod());

		CRSKeyword keyword = readToKeyword(CRSKeyword.PARAMETER,
				CRSKeyword.PARAMETERFILE, CRSKeyword.ID);

		if (keyword == CRSKeyword.PARAMETER
				|| keyword == CRSKeyword.PARAMETERFILE) {
			derivingConversion.setParameters(readDerivedParameters());
			keyword = readToKeyword(CRSKeyword.ID);
		}

		if (keyword == CRSKeyword.ID) {
			derivingConversion.setIdentifiers(readIdentifiers());
		}

		readRightDelimiter();

		return derivingConversion;
	}

	/**
	 * Read Derived parameters
	 * 
	 * @return derived parameters
	 * @throws IOException
	 *             upon failure to read
	 */
	public List<Parameter> readDerivedParameters() throws IOException {
		return readParametersAndFiles(CRSType.DERIVED);
	}

	/**
	 * Read Operation parameters and parameter files
	 * 
	 * @param type
	 *            coordinate reference system type
	 * 
	 * @return operation parameters
	 * @throws IOException
	 *             upon failure to read
	 */
	public List<Parameter> readParametersAndFiles(CRSType type)
			throws IOException {

		List<Parameter> parameters = new ArrayList<>();

		do {

			if (!parameters.isEmpty()) {
				readSeparator();
			}

			if (peekKeyword() == CRSKeyword.PARAMETERFILE) {
				parameters.add(readParameterFile());
			} else {
				parameters.add(readParameter(type));
			}

		} while (isKeywordNext(CRSKeyword.PARAMETER, CRSKeyword.PARAMETERFILE));

		return parameters;
	}

	/**
	 * Read an Operation parameter file
	 * 
	 * @return operation parameter file
	 * @throws IOException
	 *             upon failure to read
	 */
	public OperationParameterFile readParameterFile() throws IOException {

		OperationParameterFile parameterFile = new OperationParameterFile();

		readKeyword(CRSKeyword.PARAMETERFILE);

		readLeftDelimiter();

		parameterFile.setName(reader.readExpectedToken());

		readSeparator();
		parameterFile.setFileName(reader.readExpectedToken());

		CRSKeyword keyword = readToKeyword(CRSKeyword.ID);

		if (keyword == CRSKeyword.ID) {
			parameterFile.setIdentifiers(readIdentifiers());
		}

		readRightDelimiter();

		return parameterFile;
	}

	/**
	 * Read Coordinate Operation parameters
	 * 
	 * @return parameters
	 * @throws IOException
	 *             upon failure to read
	 */
	public List<Parameter> readCoordinateOperationParameters()
			throws IOException {
		return readParametersAndFiles(CRSType.COORDINATE_OPERATION);
	}

	/**
	 * Read an operation version
	 * 
	 * @return operation version
	 * @throws IOException
	 *             upon failure to read
	 */
	public String readVersion() throws IOException {

		readKeyword(CRSKeyword.VERSION);
		readLeftDelimiter();

		String version = reader.readExpectedToken();

		readRightDelimiter();

		return version;
	}

	/**
	 * Read a source coordinate reference system
	 * 
	 * @return source crs
	 * @throws IOException
	 *             upon failure to read
	 */
	public CoordinateReferenceSystem readSource() throws IOException {
		return readCoordinateReferenceSystem(CRSKeyword.SOURCECRS);
	}

	/**
	 * Read a target coordinate reference system
	 * 
	 * @return target crs
	 * @throws IOException
	 *             upon failure to read
	 */
	public CoordinateReferenceSystem readTarget() throws IOException {
		return readCoordinateReferenceSystem(CRSKeyword.TARGETCRS);
	}

	/**
	 * Read a interpolation coordinate reference system
	 * 
	 * @return interpolation crs
	 * @throws IOException
	 *             upon failure to read
	 */
	public CoordinateReferenceSystem readInterpolation() throws IOException {
		return readCoordinateReferenceSystem(CRSKeyword.INTERPOLATIONCRS);
	}

	/**
	 * Read a coordinate reference system with the keyword
	 * 
	 * @param keyword
	 *            CRS keyword
	 * 
	 * @return crs
	 * @throws IOException
	 *             upon failure to read
	 */
	public CoordinateReferenceSystem readCoordinateReferenceSystem(
			CRSKeyword keyword) throws IOException {

		readKeyword(keyword);
		readLeftDelimiter();

		CoordinateReferenceSystem crs = readCoordinateReferenceSystem();

		readRightDelimiter();

		return crs;
	}

	/**
	 * Read an operation accuracy
	 * 
	 * @return operation accuracy
	 * @throws IOException
	 *             upon failure to read
	 */
	public double readAccuracy() throws IOException {

		readKeyword(CRSKeyword.OPERATIONACCURACY);
		readLeftDelimiter();

		double accuracy = reader.readNumber();

		readRightDelimiter();

		return accuracy;
	}

	/**
	 * Read a Backward Compatible Geodetic or Geographic CRS
	 * 
	 * @return geodetic or geographic coordinate reference system
	 * @throws IOException
	 *             upon failure to read
	 */
	public GeoCoordinateReferenceSystem readGeoCompat() throws IOException {
		CRSType expectedType = null;
		return readGeoCompat(expectedType);
	}

	/**
	 * Read a Backward Compatible Geodetic CRS
	 * 
	 * @return geodetic coordinate reference system
	 * @throws IOException
	 *             upon failure to read
	 */
	public GeoCoordinateReferenceSystem readGeodeticCompat()
			throws IOException {
		return readGeoCompat(CRSType.GEODETIC);
	}

	/**
	 * Read a Backward Compatible Geographic CRS
	 * 
	 * @return geographic coordinate reference system
	 * @throws IOException
	 *             upon failure to read
	 */
	public GeoCoordinateReferenceSystem readGeographicCompat()
			throws IOException {
		return readGeoCompat(CRSType.GEOGRAPHIC);
	}

	/**
	 * Read a Backward Compatible Geodetic or Geographic CRS
	 * 
	 * @param expectedType
	 *            expected coordinate reference system type
	 * 
	 * @return geodetic or geographic coordinate reference system
	 * @throws IOException
	 *             upon failure to read
	 */
	public GeoCoordinateReferenceSystem readGeoCompat(CRSType expectedType)
			throws IOException {

		GeoCoordinateReferenceSystem crs = new GeoCoordinateReferenceSystem();

		CRSKeyword type = readKeyword(CRSKeyword.GEOCCS, CRSKeyword.GEOGCS,
				CRSKeyword.GEODCRS, CRSKeyword.GEOGCRS);
		CRSType crsType = WKTUtils.getCoordinateReferenceSystemType(type);
		if (expectedType != null && crsType != expectedType) {
			throw new ProjectionException(
					"Unexpected Coordinate Reference System Type. expected: "
							+ expectedType + ", found: " + crsType);
		}
		crs.setType(crsType);

		readLeftDelimiter();

		crs.setName(reader.readExpectedToken());

		readSeparator();
		GeoReferenceFrame referenceFrame = readGeoReferenceFrame();
		referenceFrame.setType(crsType);
		crs.setReferenceFrame(referenceFrame);

		crs.setCoordinateSystem(
				readCoordinateSystemCompat(crsType, crs.getReferenceFrame()));

		CRSKeyword keyword = readToKeyword(CRSKeyword.EXTENSION, CRSKeyword.ID);

		if (keyword == CRSKeyword.EXTENSION) {
			extras.putAll(readExtensionsCompat());
			keyword = readToKeyword(CRSKeyword.ID);
		}

		if (keyword == CRSKeyword.ID) {
			crs.setIdentifiers(readIdentifiers());
		}

		readRightDelimiter();

		crs.setRemark(writeExtras());

		return crs;
	}

	/**
	 * Read a Backward Compatible Projected CRS
	 * 
	 * @return projected coordinate reference system
	 * @throws IOException
	 *             upon failure to read
	 */
	public ProjectedCoordinateReferenceSystem readProjectedCompat()
			throws IOException {
		CRSType expectedType = null;
		return readProjectedCompat(expectedType);
	}

	/**
	 * Read a Backward Compatible Projected Geodetic CRS
	 * 
	 * @return projected geodetic coordinate reference system
	 * @throws IOException
	 *             upon failure to read
	 */
	public ProjectedCoordinateReferenceSystem readProjectedGeodeticCompat()
			throws IOException {
		return readProjectedCompat(CRSType.GEODETIC);
	}

	/**
	 * Read a Backward Compatible Projected Geographic CRS
	 * 
	 * @return projected geographic coordinate reference system
	 * @throws IOException
	 *             upon failure to read
	 */
	public ProjectedCoordinateReferenceSystem readProjectedGeographicCompat()
			throws IOException {
		return readProjectedCompat(CRSType.GEOGRAPHIC);
	}

	/**
	 * Read a Backward Compatible Projected CRS
	 * 
	 * @param expectedBaseType
	 *            expected base coordinate reference system type
	 * 
	 * @return projected coordinate reference system
	 * @throws IOException
	 *             upon failure to read
	 */
	public ProjectedCoordinateReferenceSystem readProjectedCompat(
			CRSType expectedBaseType) throws IOException {

		ProjectedCoordinateReferenceSystem crs = new ProjectedCoordinateReferenceSystem();

		readKeyword(CRSKeyword.PROJCS);

		readLeftDelimiter();

		crs.setName(reader.readExpectedToken());

		readSeparator();
		crs.setBase(readGeoCompat(expectedBaseType));

		// Not spec based, but some implementations provide the unit here
		Unit unit = null;
		if (isUnitNext()) {
			readSeparator();
			unit = readUnit();
		}

		readSeparator();
		MapProjection mapProjection = readMapProjectionCompat();
		crs.setMapProjection(mapProjection);

		crs.setCoordinateSystem(readCoordinateSystemCompat(CRSType.PROJECTED,
				crs.getReferenceFrame()));

		if (unit != null && !crs.getCoordinateSystem().hasUnit()) {
			crs.getCoordinateSystem().setUnit(unit);
		}

		CRSKeyword keyword = readToKeyword(CRSKeyword.EXTENSION, CRSKeyword.ID);

		if (keyword == CRSKeyword.EXTENSION) {
			extras.putAll(readExtensionsCompat());
			keyword = readToKeyword(CRSKeyword.ID);
		}

		if (keyword == CRSKeyword.ID) {
			crs.setIdentifiers(readIdentifiers());
		} else if (mapProjection.hasIdentifiers()) {
			crs.setIdentifiers(mapProjection.getIdentifiers());
			mapProjection.setIdentifiers(null);
		}

		readRightDelimiter();

		crs.setRemark(writeExtras());

		return crs;
	}

	/**
	 * Read a Backward Compatible Vertical CRS
	 * 
	 * @return vertical coordinate reference system
	 * @throws IOException
	 *             upon failure to read
	 */
	public VerticalCoordinateReferenceSystem readVerticalCompat()
			throws IOException {

		VerticalCoordinateReferenceSystem crs = new VerticalCoordinateReferenceSystem();

		readKeyword(CRSKeyword.VERT_CS);

		readLeftDelimiter();

		crs.setName(reader.readExpectedToken());

		readSeparator();
		crs.setReferenceFrame(readVerticalDatumCompat());

		crs.setCoordinateSystem(readCoordinateSystemCompat(CRSType.VERTICAL,
				crs.getReferenceFrame()));

		CRSKeyword keyword = readToKeyword(CRSKeyword.EXTENSION, CRSKeyword.ID);

		if (keyword == CRSKeyword.EXTENSION) {
			extras.putAll(readExtensionsCompat());
			keyword = readToKeyword(CRSKeyword.ID);
		}

		if (keyword == CRSKeyword.ID) {
			crs.setIdentifiers(readIdentifiers());
		}

		readRightDelimiter();

		crs.setRemark(writeExtras());

		return crs;
	}

	/**
	 * Read a Backward Compatible Engineering CRS
	 * 
	 * @return engineering coordinate reference system
	 * @throws IOException
	 *             upon failure to read
	 */
	public EngineeringCoordinateReferenceSystem readEngineeringCompat()
			throws IOException {

		EngineeringCoordinateReferenceSystem crs = new EngineeringCoordinateReferenceSystem();

		readKeyword(CRSKeyword.LOCAL_CS);

		readLeftDelimiter();

		crs.setName(reader.readExpectedToken());

		readSeparator();
		crs.setDatum(readEngineeringDatumCompat());

		crs.setCoordinateSystem(readCoordinateSystemCompat(CRSType.ENGINEERING,
				crs.getDatum()));

		CRSKeyword keyword = readToKeyword(CRSKeyword.EXTENSION, CRSKeyword.ID);

		if (keyword == CRSKeyword.EXTENSION) {
			extras.putAll(readExtensionsCompat());
			keyword = readToKeyword(CRSKeyword.ID);
		}

		if (keyword == CRSKeyword.ID) {
			crs.setIdentifiers(readIdentifiers());
		}

		readRightDelimiter();

		crs.setRemark(writeExtras());

		return crs;
	}

	/**
	 * Read a Backward Compatible map projection
	 * 
	 * @return map projection
	 * @throws IOException
	 *             upon failure to read
	 */
	public MapProjection readMapProjectionCompat() throws IOException {

		MapProjection mapProjection = new MapProjection();

		OperationMethod method = readMethod();
		mapProjection.setName(method.getName());
		mapProjection.setMethod(method);

		CRSKeyword keyword = readToKeyword(CRSKeyword.PARAMETER, CRSKeyword.ID);

		if (keyword == CRSKeyword.PARAMETER) {
			mapProjection.setParameters(readMapProjectionParameters());
		}

		if (isKeywordNext(CRSKeyword.ID)) {
			readSeparator();
			mapProjection.setIdentifiers(readIdentifiers());
		}

		return mapProjection;
	}

	/**
	 * Read a Backward Compatible Coordinate System
	 * 
	 * @param type
	 *            coordinate reference system type
	 * @param datum
	 *            reference frame
	 * 
	 * @return coordinate system
	 * @throws IOException
	 *             upon failure to read
	 */
	public CoordinateSystem readCoordinateSystemCompat(CRSType type,
			ReferenceFrame datum) throws IOException {

		CoordinateSystem coordinateSystem = new CoordinateSystem();

		switch (datum.getType()) {
		case GEODETIC:
		case GEOGRAPHIC:
			coordinateSystem.setType(CoordinateSystemType.ELLIPSOIDAL);
			break;
		case VERTICAL:
			coordinateSystem.setType(CoordinateSystemType.VERTICAL);
			break;
		case ENGINEERING:
			coordinateSystem.setType(CoordinateSystemType.CARTESIAN);
			break;
		default:
			throw new ProjectionException(
					"Unexpected Reference Frame Type. expected: "
							+ datum.getType());
		}

		if (isUnitNext()) {
			readSeparator();
			coordinateSystem.setUnit(readUnit());
		}

		if (isKeywordNext(CRSKeyword.AXIS)) {
			readSeparator();
			coordinateSystem.setAxes(readAxes());
		} else {

			switch (type) {
			case GEOGRAPHIC:
				coordinateSystem.addAxis(new Axis(WKTConstants.AXIS_NAME_LON,
						AxisDirectionType.EAST));
				coordinateSystem.addAxis(new Axis(WKTConstants.AXIS_NAME_LAT,
						AxisDirectionType.NORTH));
				break;
			case PROJECTED:
				coordinateSystem.addAxis(new Axis(WKTConstants.AXIS_NAME_X,
						AxisDirectionType.EAST));
				coordinateSystem.addAxis(new Axis(WKTConstants.AXIS_NAME_Y,
						AxisDirectionType.NORTH));
				break;
			case GEODETIC:
				coordinateSystem.addAxis(new Axis(WKTConstants.AXIS_NAME_X,
						AxisDirectionType.UNSPECIFIED));
				coordinateSystem.addAxis(new Axis(WKTConstants.AXIS_NAME_Y,
						AxisDirectionType.EAST));
				coordinateSystem.addAxis(new Axis(WKTConstants.AXIS_NAME_Z,
						AxisDirectionType.NORTH));
				break;
			default:
				throw new ProjectionException(
						"Unexpected Coordinate Reference System Type: " + type);
			}

		}
		coordinateSystem.setDimension(coordinateSystem.getAxes().size());

		// TODO http://ogc.standardstracker.org/show_request.cgi?id=674
		if (isUnitNext()) {
			readSeparator();
			coordinateSystem.setUnit(readUnit());
		}

		return coordinateSystem;
	}

	/**
	 * Read a Backward Compatible vertical datum
	 * 
	 * @return vertical reference frame
	 * @throws IOException
	 *             upon failure to read
	 */
	public VerticalReferenceFrame readVerticalDatumCompat() throws IOException {
		ReferenceFrame referenceFrame = readDatumCompat();
		if (!(referenceFrame instanceof VerticalReferenceFrame)) {
			throw new ProjectionException(
					"Datum was not an expected Vertical Reference Frame");
		}
		return (VerticalReferenceFrame) referenceFrame;
	}

	/**
	 * Read a Backward Compatible engineering datum
	 * 
	 * @return engineering datum
	 * @throws IOException
	 *             upon failure to read
	 */
	public EngineeringDatum readEngineeringDatumCompat() throws IOException {
		ReferenceFrame referenceFrame = readDatumCompat();
		if (!(referenceFrame instanceof EngineeringDatum)) {
			throw new ProjectionException(
					"Datum was not an expected Engineering Datum");
		}
		return (EngineeringDatum) referenceFrame;
	}

	/**
	 * Read a Backward Compatible datum
	 * 
	 * @return reference frame
	 * @throws IOException
	 *             upon failure to read
	 */
	public ReferenceFrame readDatumCompat() throws IOException {

		ReferenceFrame referenceFrame = null;

		CRSKeyword type = readKeyword(CRSKeyword.VDATUM, CRSKeyword.EDATUM);
		switch (type) {
		case VDATUM:
			referenceFrame = new VerticalReferenceFrame();
			break;
		case EDATUM:
			referenceFrame = new EngineeringDatum();
			break;
		default:
			throw new ProjectionException("Unexpected Datum type: " + type);
		}

		readLeftDelimiter();

		referenceFrame.setName(reader.readExpectedToken());

		readSeparator();
		extras.put(WKTConstants.DATUM_TYPE,
				Double.toString(reader.readNumber()));

		CRSKeyword keyword = readToKeyword(CRSKeyword.ID, CRSKeyword.EXTENSION);

		if (keyword == CRSKeyword.ID) {
			referenceFrame.setIdentifiers(readIdentifiers());
			keyword = readToKeyword(CRSKeyword.EXTENSION);
		}

		if (keyword == CRSKeyword.EXTENSION) {
			extras.putAll(readExtensionsCompat());
		}

		readRightDelimiter();

		return referenceFrame;
	}

	/**
	 * Read a Backward Compatible To WGS84 transformation
	 * 
	 * @return abridged transformation
	 * @throws IOException
	 *             upon failure to read
	 */
	public String readToWGS84Compat() throws IOException {

		StringBuilder value = new StringBuilder();

		readKeyword(CRSKeyword.TOWGS84);

		readLeftDelimiter();

		while (!peekRightDelimiter()) {
			value.append(reader.readToken());
		}

		readRightDelimiter();

		return value.toString();
	}

	/**
	 * Read Backward Compatible Extensions
	 * 
	 * @return extensions
	 * @throws IOException
	 *             upon failure to read
	 */
	public Map<String, String> readExtensionsCompat() throws IOException {

		Map<String, String> extensions = new LinkedHashMap<>();

		do {

			if (!extensions.isEmpty()) {
				readSeparator();
			}

			readKeyword(CRSKeyword.EXTENSION);

			readLeftDelimiter();

			String key = reader.readExpectedToken();
			readSeparator();
			String value = reader.readExpectedToken();

			extensions.put(key, value);

			readRightDelimiter();

		} while (isKeywordNext(CRSKeyword.EXTENSION));

		return extensions;
	}

	/**
	 * Write backwards compatible extras map to text
	 * 
	 * @return extras text
	 * @throws IOException
	 *             upon failure to write
	 */
	public String writeExtras() throws IOException {
		return writeExtras(extras);
	}

	/**
	 * Write backwards compatible extras map to text
	 * 
	 * @param extras
	 *            extras map
	 * @return extras text
	 * @throws IOException
	 *             upon failure to write
	 */
	public static String writeExtras(Map<String, String> extras)
			throws IOException {

		String value = null;

		if (!extras.isEmpty()) {

			StringBuilder builder = new StringBuilder();

			for (Entry<String, String> extension : extras.entrySet()) {

				if (builder.length() > 0) {
					builder.append(WKTConstants.SEPARATOR);
				}

				builder.append(WKTConstants.LEFT_DELIMITER);
				builder.append("\"");
				builder.append(extension.getKey());
				builder.append("\"");
				builder.append(WKTConstants.SEPARATOR);
				builder.append("\"");
				builder.append(extension.getValue());
				builder.append("\"");
				builder.append(WKTConstants.RIGHT_DELIMITER);
			}

			value = builder.toString();
		}

		return value;
	}

	/**
	 * Read backwards compatible extras text (extensions, unsupported values)
	 * that were saved as CRS remarks, retrievable by
	 * {@link CommonCRS#getRemark()}
	 * 
	 * @param text
	 *            extras text
	 * @return extras map
	 * @throws IOException
	 *             upon failure to read
	 */
	public static Map<String, String> readExtras(String text)
			throws IOException {

		Map<String, String> extras = new LinkedHashMap<>();

		CRSReader reader = new CRSReader(text);
		try {

			while (reader.peekLeftDelimiter()) {
				reader.readLeftDelimiter();

				String key = reader.getTextReader().readExpectedToken();
				reader.readSeparator();
				String value = reader.getTextReader().readExpectedToken();
				extras.put(key, value);

				reader.readRightDelimiter();

				if (reader.peekSeparator()) {
					reader.readSeparator();
				}
			}
			reader.readEnd();

		} finally {
			reader.close();
		}

		return extras;
	}

}
