package mil.nga.proj.crs.wkt;

import java.io.Closeable;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import mil.nga.proj.ProjectionException;
import mil.nga.proj.crs.CRS;
import mil.nga.proj.crs.CommonCRS;
import mil.nga.proj.crs.CompoundCoordinateReferenceSystem;
import mil.nga.proj.crs.CoordinateReferenceSystem;
import mil.nga.proj.crs.common.Axis;
import mil.nga.proj.crs.common.CoordinateSystem;
import mil.nga.proj.crs.common.DatumEnsemble;
import mil.nga.proj.crs.common.DatumEnsembleMember;
import mil.nga.proj.crs.common.Dynamic;
import mil.nga.proj.crs.common.Extent;
import mil.nga.proj.crs.common.GeographicBoundingBox;
import mil.nga.proj.crs.common.Identifier;
import mil.nga.proj.crs.common.ReferenceFrame;
import mil.nga.proj.crs.common.TemporalExtent;
import mil.nga.proj.crs.common.Unit;
import mil.nga.proj.crs.common.Usage;
import mil.nga.proj.crs.common.VerticalExtent;
import mil.nga.proj.crs.derived.DerivedCoordinateReferenceSystem;
import mil.nga.proj.crs.derived.DerivingConversion;
import mil.nga.proj.crs.engineering.EngineeringCoordinateReferenceSystem;
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
import mil.nga.proj.crs.operation.PointMotionOperation;
import mil.nga.proj.crs.parametric.ParametricCoordinateReferenceSystem;
import mil.nga.proj.crs.projected.MapProjection;
import mil.nga.proj.crs.projected.ProjectedCoordinateReferenceSystem;
import mil.nga.proj.crs.temporal.TemporalCoordinateReferenceSystem;
import mil.nga.proj.crs.temporal.TemporalDatum;
import mil.nga.proj.crs.vertical.VerticalCoordinateReferenceSystem;

/**
 * Well-Known Text writer
 * 
 * @author osbornb
 */
public class CRSWriter implements Closeable {

	/**
	 * Logger
	 */
	private static final Logger logger = Logger
			.getLogger(CRSWriter.class.getName());

	/**
	 * Write a coordinate reference system to well-known text
	 * 
	 * @param crs
	 *            coordinate reference system
	 * @return well-known text
	 * @throws IOException
	 *             upon failure to write
	 */
	public static String write(CRS crs) throws IOException {
		String value = null;
		CRSWriter writer = new CRSWriter();
		try {
			writer.writeCRS(crs);
			value = writer.toString();
		} finally {
			writer.close();
		}
		return value;
	}

	/**
	 * Write a coordinate reference system to well-known pretty text, 4 space
	 * indents
	 * 
	 * @param crs
	 *            coordinate reference system
	 * @return well-known pretty text
	 * @throws IOException
	 *             upon failure to write
	 */
	public static String writePretty(CRS crs) throws IOException {
		return writePretty(write(crs));
	}

	/**
	 * Write a coordinate reference system to well-known pretty text, tab
	 * indents
	 * 
	 * @param crs
	 *            coordinate reference system
	 * @return well-known pretty text
	 * @throws IOException
	 *             upon failure to write
	 */
	public static String writePrettyTabIndent(CRS crs) throws IOException {
		return writePrettyTabIndent(write(crs));
	}

	/**
	 * Write a coordinate reference system to well-known pretty text, no indents
	 * 
	 * @param crs
	 *            coordinate reference system
	 * @return well-known pretty text
	 * @throws IOException
	 *             upon failure to write
	 */
	public static String writePrettyNoIndent(CRS crs) throws IOException {
		return writePrettyNoIndent(write(crs));
	}

	/**
	 * Write a coordinate reference system to well-known pretty text
	 * 
	 * @param crs
	 *            coordinate reference system
	 * @param indent
	 *            indent string
	 * @return well-known pretty text
	 * @throws IOException
	 *             upon failure to write
	 */
	public static String writePretty(CRS crs, String indent)
			throws IOException {
		return writePretty(write(crs), indent);
	}

	/**
	 * Write a coordinate reference system to well-known pretty text
	 * 
	 * @param crs
	 *            coordinate reference system
	 * @param newline
	 *            newline string
	 * @param indent
	 *            indent string
	 * @return well-known pretty text
	 * @throws IOException
	 *             upon failure to write
	 */
	public static String writePretty(CRS crs, String newline, String indent)
			throws IOException {
		return writePretty(write(crs), newline, indent);
	}

	/**
	 * Write well-known text to well-known pretty text, 4 space indents
	 * 
	 * @param wkt
	 *            well-known text
	 * @return well-known pretty text
	 * @throws IOException
	 *             upon failure to write
	 */
	public static String writePretty(String wkt) throws IOException {
		return WKTUtils.pretty(wkt);
	}

	/**
	 * Write well-known text to well-known pretty text, tab indents
	 * 
	 * @param wkt
	 *            well-known text
	 * @return well-known pretty text
	 * @throws IOException
	 *             upon failure to write
	 */
	public static String writePrettyTabIndent(String wkt) throws IOException {
		return WKTUtils.prettyTabIndent(wkt);
	}

	/**
	 * Write well-known text to well-known pretty text, no indents
	 * 
	 * @param wkt
	 *            well-known text
	 * @return well-known pretty text
	 * @throws IOException
	 *             upon failure to write
	 */
	public static String writePrettyNoIndent(String wkt) throws IOException {
		return WKTUtils.prettyNoIndent(wkt);
	}

	/**
	 * Write well-known text to well-known pretty text
	 * 
	 * @param wkt
	 *            well-known text
	 * @param indent
	 *            indent string
	 * @return well-known pretty text
	 * @throws IOException
	 *             upon failure to write
	 */
	public static String writePretty(String wkt, String indent)
			throws IOException {
		return WKTUtils.pretty(wkt, indent);
	}

	/**
	 * Write well-known text to well-known pretty text
	 * 
	 * @param wkt
	 *            well-known text
	 * @param newline
	 *            newline string
	 * @param indent
	 *            indent string
	 * @return well-known pretty text
	 * @throws IOException
	 *             upon failure to write
	 */
	public static String writePretty(String wkt, String newline, String indent)
			throws IOException {
		return WKTUtils.pretty(wkt, newline, indent);
	}

	/**
	 * Writer
	 */
	private Writer writer;

	/**
	 * Constructor
	 */
	public CRSWriter() {
		this(new StringWriter());
	}

	/**
	 * Constructor
	 * 
	 * @param writer
	 *            writer
	 */
	public CRSWriter(Writer writer) {
		this.writer = writer;
	}

	/**
	 * Get the writer
	 * 
	 * @return writer
	 */
	public Writer getWriter() {
		return writer;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return writer.toString();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void close() {
		try {
			writer.close();
		} catch (IOException e) {
			logger.log(Level.WARNING, "Failed to close writer", e);
		}
	}

	/**
	 * Write a CRS to well-known text
	 * 
	 * @param crs
	 *            coordinate reference system
	 * @throws IOException
	 *             upon failure to write
	 */
	public void writeCRS(CRS crs) throws IOException {

		switch (crs.getType()) {
		case GEODETIC:
		case GEOGRAPHIC:
			writeGeo((GeoCoordinateReferenceSystem) crs);
			break;
		case PROJECTED:
			writeProjected((ProjectedCoordinateReferenceSystem) crs);
			break;
		case VERTICAL:
			writeVertical((VerticalCoordinateReferenceSystem) crs);
			break;
		case ENGINEERING:
			writeEngineering((EngineeringCoordinateReferenceSystem) crs);
			break;
		case PARAMETRIC:
			writeParametric((ParametricCoordinateReferenceSystem) crs);
			break;
		case TEMPORAL:
			writeTemporal((TemporalCoordinateReferenceSystem) crs);
			break;
		case DERIVED:
			writeDerived((DerivedCoordinateReferenceSystem) crs);
			break;
		case COMPOUND:
			writeCompound((CompoundCoordinateReferenceSystem) crs);
			break;
		case COORDINATE_METADATA:
			writeCoordinateMetadata((CoordinateMetadata) crs);
			break;
		case COORDINATE_OPERATION:
			writeCoordinateOperation((CoordinateOperation) crs);
			break;
		case POINT_MOTION_OPERATION:
			writePointMotionOperation((PointMotionOperation) crs);
			break;
		default:
			throw new ProjectionException(
					"Unsupported CRS type: " + crs.getType());
		}

	}

	/**
	 * Write a keyword
	 * 
	 * @param keyword
	 *            keyword
	 * @throws IOException
	 *             upon failure to write
	 */
	public void write(CRSKeyword keyword) throws IOException {
		writer.write(keyword.name());
	}

	/**
	 * Write a left delimiter
	 * 
	 * @throws IOException
	 *             upon failure to write
	 */
	public void writeLeftDelimiter() throws IOException {
		writer.write(WKTConstants.LEFT_DELIMITER);
	}

	/**
	 * Write a right delimiter
	 * 
	 * @throws IOException
	 *             upon failure to write
	 */
	public void writeRightDelimiter() throws IOException {
		writer.write(WKTConstants.RIGHT_DELIMITER);
	}

	/**
	 * Write a separator
	 * 
	 * @throws IOException
	 *             upon failure to write
	 */
	public void writeSeparator() throws IOException {
		writer.write(WKTConstants.SEPARATOR);
	}

	/**
	 * Write the text as quoted
	 * 
	 * @param text
	 *            text
	 * @throws IOException
	 *             upon failure to write
	 */
	public void writeQuotedText(String text) throws IOException {
		writer.write("\"");
		writer.write(text.replaceAll("\"", "\"\""));
		writer.write("\"");
	}

	/**
	 * Write a number or quoted text if not a number
	 * 
	 * @param number
	 *            number
	 * @throws IOException
	 *             upon failure to write
	 */
	public void write(Number number) throws IOException {
		writer.write(number.toString());
	}

	/**
	 * Write a number or quoted text if not a number
	 * 
	 * @param text
	 *            text
	 * @throws IOException
	 *             upon failure to write
	 */
	public void writeNumberOrQuotedText(String text) throws IOException {
		try {
			Double.parseDouble(text);
			writer.write(text);
		} catch (NumberFormatException e) {
			writeQuotedText(text);
		}
	}

	/**
	 * Write a keyword delimited text
	 * 
	 * @param keyword
	 *            keyword
	 * @param text
	 *            text
	 * @throws IOException
	 *             upon failure to write
	 */
	public void writeKeywordDelimitedQuotedText(CRSKeyword keyword, String text)
			throws IOException {

		write(keyword);

		writeLeftDelimiter();

		writeQuotedText(text);

		writeRightDelimiter();
	}

	/**
	 * Write a geodetic or geographic CRS to well-known text
	 * 
	 * @param crs
	 *            geodetic or geographic coordinate reference system
	 * @throws IOException
	 *             upon failure to write
	 */
	public void writeGeo(GeoCoordinateReferenceSystem crs) throws IOException {

		CRSKeyword keyword = null;
		switch (crs.getType()) {
		case GEODETIC:
			keyword = CRSKeyword.GEODCRS;
			break;
		case GEOGRAPHIC:
			keyword = CRSKeyword.GEOGCRS;
			break;
		default:
			throw new ProjectionException(
					"Invalid Geodetic or Geographic Coordinate Reference System Type: "
							+ crs.getType());
		}
		write(keyword);

		writeLeftDelimiter();

		writeQuotedText(crs.getName());

		if (crs.hasDynamic()) {
			writeSeparator();
			write(crs.getDynamic());
		}

		writeSeparator();
		if (crs.hasDynamic() || crs.hasReferenceFrame()) {
			write(crs.getReferenceFrame());
		} else {
			write(crs.getDatumEnsemble());
		}

		writeSeparator();
		write(crs.getCoordinateSystem());

		writeScopeExtentIdentifierRemark(crs);

		writeRightDelimiter();
	}

	/**
	 * Write a projected CRS to well-known text
	 * 
	 * @param crs
	 *            projected coordinate reference system
	 * @throws IOException
	 *             upon failure to write
	 */
	public void writeProjected(ProjectedCoordinateReferenceSystem crs)
			throws IOException {

		write(CRSKeyword.PROJCRS);

		writeLeftDelimiter();

		writeQuotedText(crs.getName());

		CRSKeyword baseKeyword = null;
		switch (crs.getBaseType()) {
		case GEODETIC:
			baseKeyword = CRSKeyword.BASEGEODCRS;
			break;
		case GEOGRAPHIC:
			baseKeyword = CRSKeyword.BASEGEOGCRS;
			break;
		default:
			throw new ProjectionException(
					"Invalid Geodetic or Geographic Base Coordinate Reference System Type: "
							+ crs.getBaseType());
		}

		writeSeparator();
		write(baseKeyword);

		writeLeftDelimiter();

		writeQuotedText(crs.getBaseName());

		if (crs.hasDynamic()) {
			writeSeparator();
			write(crs.getDynamic());
		}

		writeSeparator();
		if (crs.hasDynamic() || crs.hasReferenceFrame()) {
			write(crs.getReferenceFrame());
		} else {
			write(crs.getDatumEnsemble());
		}

		if (crs.hasUnit()) {
			writeSeparator();
			write(crs.getUnit());
		}

		if (crs.hasBaseIdentifiers()) {
			writeSeparator();
			writeIdentifiers(crs.getBaseIdentifiers());
		}

		writeRightDelimiter();

		writeSeparator();
		write(crs.getMapProjection());

		writeSeparator();
		write(crs.getCoordinateSystem());

		writeScopeExtentIdentifierRemark(crs);

		writeRightDelimiter();
	}

	/**
	 * Write a vertical CRS to well-known text
	 * 
	 * @param crs
	 *            vertical coordinate reference system
	 * @throws IOException
	 *             upon failure to write
	 */
	public void writeVertical(VerticalCoordinateReferenceSystem crs)
			throws IOException {

		write(CRSKeyword.VERTCRS);

		writeLeftDelimiter();

		writeQuotedText(crs.getName());

		if (crs.hasDynamic()) {
			writeSeparator();
			write(crs.getDynamic());
		}

		writeSeparator();
		if (crs.hasDynamic() || crs.hasReferenceFrame()) {
			write(crs.getReferenceFrame());
		} else {
			write(crs.getDatumEnsemble());
		}

		writeSeparator();
		write(crs.getCoordinateSystem());

		if (crs.hasGeoidModelName()) {
			writeSeparator();
			write(CRSKeyword.GEOIDMODEL);
			writeLeftDelimiter();
			writeQuotedText(crs.getGeoidModelName());
			if (crs.hasGeoidModelIdentifier()) {
				writeSeparator();
				write(crs.getGeoidModelIdentifier());
			}
			writeRightDelimiter();
		}

		writeScopeExtentIdentifierRemark(crs);

		writeRightDelimiter();
	}

	/**
	 * Write an engineering CRS to well-known text
	 * 
	 * @param crs
	 *            engineering coordinate reference system
	 * @throws IOException
	 *             upon failure to write
	 */
	public void writeEngineering(EngineeringCoordinateReferenceSystem crs)
			throws IOException {

		write(CRSKeyword.ENGCRS);

		writeLeftDelimiter();

		writeQuotedText(crs.getName());

		writeSeparator();
		write(crs.getDatum());

		writeSeparator();
		write(crs.getCoordinateSystem());

		writeScopeExtentIdentifierRemark(crs);

		writeRightDelimiter();
	}

	/**
	 * Write a parametric CRS to well-known text
	 * 
	 * @param crs
	 *            parametric coordinate reference system
	 * @throws IOException
	 *             upon failure to write
	 */
	public void writeParametric(ParametricCoordinateReferenceSystem crs)
			throws IOException {

		write(CRSKeyword.PARAMETRICCRS);

		writeLeftDelimiter();

		writeQuotedText(crs.getName());

		writeSeparator();
		write(crs.getDatum());

		writeSeparator();
		write(crs.getCoordinateSystem());

		writeScopeExtentIdentifierRemark(crs);

		writeRightDelimiter();
	}

	/**
	 * Write a temporal CRS to well-known text
	 * 
	 * @param crs
	 *            temporal coordinate reference system
	 * @throws IOException
	 *             upon failure to write
	 */
	public void writeTemporal(TemporalCoordinateReferenceSystem crs)
			throws IOException {

		write(CRSKeyword.TIMECRS);

		writeLeftDelimiter();

		writeQuotedText(crs.getName());

		writeSeparator();
		write(crs.getDatum());

		writeSeparator();
		write(crs.getCoordinateSystem());

		writeScopeExtentIdentifierRemark(crs);

		writeRightDelimiter();
	}

	/**
	 * Write a derived CRS to well-known text
	 * 
	 * @param crs
	 *            temporal coordinate reference system
	 * @throws IOException
	 *             upon failure to write
	 */
	public void writeDerived(DerivedCoordinateReferenceSystem crs)
			throws IOException {

		switch (crs.getBaseType()) {
		case GEODETIC:
		case GEOGRAPHIC:
			writeDerivedGeoCRS(crs);
			break;
		case PROJECTED:
			writeDerivedProjectedCRS(crs);
			break;
		case VERTICAL:
			writeDerivedVerticalCRS(crs);
			break;
		case ENGINEERING:
			writeDerivedEngineeringCRS(crs);
			break;
		case PARAMETRIC:
			writeDerivedParametricCRS(crs);
			break;
		case TEMPORAL:
			writeDerivedTemporalCRS(crs);
			break;
		default:
			throw new ProjectionException(
					"Unsupported derived base CRS type: " + crs.getBaseType());
		}

	}

	/**
	 * Write a derived geo CRS to well-known text
	 * 
	 * @param crs
	 *            derived geo coordinate reference system
	 * @throws IOException
	 *             upon failure to write
	 */
	public void writeDerivedGeoCRS(DerivedCoordinateReferenceSystem crs)
			throws IOException {

		CRSKeyword keyword = null;
		CRSKeyword baseKeyword = null;
		switch (crs.getBaseType()) {
		case GEODETIC:
			keyword = CRSKeyword.GEODCRS;
			baseKeyword = CRSKeyword.BASEGEODCRS;
			break;
		case GEOGRAPHIC:
			keyword = CRSKeyword.GEOGCRS;
			baseKeyword = CRSKeyword.BASEGEOGCRS;
			break;
		default:
			throw new ProjectionException(
					"Invalid Derived Geodetic or Geographic Coordinate Reference System Type: "
							+ crs.getType());
		}

		GeoCoordinateReferenceSystem baseCrs = (GeoCoordinateReferenceSystem) crs
				.getBase();

		write(keyword);

		writeLeftDelimiter();

		writeQuotedText(crs.getName());

		writeSeparator();
		write(baseKeyword);

		writeLeftDelimiter();

		writeQuotedText(baseCrs.getName());

		if (baseCrs.hasDynamic()) {
			writeSeparator();
			write(baseCrs.getDynamic());
		}

		writeSeparator();
		if (baseCrs.hasDynamic() || baseCrs.hasReferenceFrame()) {
			write(baseCrs.getReferenceFrame());
		} else {
			write(baseCrs.getDatumEnsemble());
		}

		if (baseCrs.hasIdentifiers()) {
			writeSeparator();
			writeIdentifiers(baseCrs.getIdentifiers());
		}

		writeRightDelimiter();

		writeSeparator();
		write(crs.getConversion());

		writeSeparator();
		write(crs.getCoordinateSystem());

		writeScopeExtentIdentifierRemark(crs);

		writeRightDelimiter();
	}

	/**
	 * Write a derived projected CRS to well-known text
	 * 
	 * @param crs
	 *            derived projected coordinate reference system
	 * @throws IOException
	 *             upon failure to write
	 */
	public void writeDerivedProjectedCRS(DerivedCoordinateReferenceSystem crs)
			throws IOException {

		ProjectedCoordinateReferenceSystem projectedCrs = (ProjectedCoordinateReferenceSystem) crs
				.getBase();

		write(CRSKeyword.DERIVEDPROJCRS);

		writeLeftDelimiter();

		writeQuotedText(crs.getName());

		writeSeparator();
		write(CRSKeyword.BASEPROJCRS);

		writeLeftDelimiter();

		writeQuotedText(projectedCrs.getName());

		CRSKeyword keyword = null;
		switch (projectedCrs.getBaseType()) {
		case GEODETIC:
			keyword = CRSKeyword.BASEGEODCRS;
			break;
		case GEOGRAPHIC:
			keyword = CRSKeyword.BASEGEOGCRS;
			break;
		default:
			throw new ProjectionException(
					"Invalid Derived Projected Geodetic or Geographic Coordinate Reference System Type: "
							+ crs.getType());
		}

		writeSeparator();
		write(keyword);

		writeLeftDelimiter();

		writeQuotedText(projectedCrs.getBaseName());

		if (projectedCrs.hasDynamic()) {
			writeSeparator();
			write(projectedCrs.getDynamic());
		}

		writeSeparator();
		if (projectedCrs.hasDynamic() || projectedCrs.hasReferenceFrame()) {
			write(projectedCrs.getReferenceFrame());
		} else {
			write(projectedCrs.getDatumEnsemble());
		}

		if (projectedCrs.hasBaseIdentifiers()) {
			writeSeparator();
			writeIdentifiers(projectedCrs.getBaseIdentifiers());
		}

		writeRightDelimiter();

		writeSeparator();
		write(projectedCrs.getMapProjection());

		if (projectedCrs.hasIdentifiers()) {
			writeSeparator();
			writeIdentifiers(projectedCrs.getIdentifiers());
		}

		writeRightDelimiter();

		writeSeparator();
		write(crs.getConversion());

		writeSeparator();
		write(crs.getCoordinateSystem());

		writeScopeExtentIdentifierRemark(crs);

		writeRightDelimiter();
	}

	/**
	 * Write a derived vertical CRS to well-known text
	 * 
	 * @param crs
	 *            derived vertical coordinate reference system
	 * @throws IOException
	 *             upon failure to write
	 */
	public void writeDerivedVerticalCRS(DerivedCoordinateReferenceSystem crs)
			throws IOException {

		VerticalCoordinateReferenceSystem baseCrs = (VerticalCoordinateReferenceSystem) crs
				.getBase();

		write(CRSKeyword.VERTCRS);

		writeLeftDelimiter();

		writeQuotedText(crs.getName());

		writeSeparator();
		write(CRSKeyword.BASEVERTCRS);

		writeLeftDelimiter();

		writeQuotedText(baseCrs.getName());

		if (baseCrs.hasDynamic()) {
			writeSeparator();
			write(baseCrs.getDynamic());
		}

		writeSeparator();
		if (baseCrs.hasDynamic() || baseCrs.hasReferenceFrame()) {
			write(baseCrs.getReferenceFrame());
		} else {
			write(baseCrs.getDatumEnsemble());
		}

		if (baseCrs.hasIdentifiers()) {
			writeSeparator();
			writeIdentifiers(baseCrs.getIdentifiers());
		}

		writeRightDelimiter();

		writeSeparator();
		write(crs.getConversion());

		writeSeparator();
		write(crs.getCoordinateSystem());

		writeScopeExtentIdentifierRemark(crs);

		writeRightDelimiter();
	}

	/**
	 * Write a derived engineering CRS to well-known text
	 * 
	 * @param crs
	 *            derived engineering coordinate reference system
	 * @throws IOException
	 *             upon failure to write
	 */
	public void writeDerivedEngineeringCRS(DerivedCoordinateReferenceSystem crs)
			throws IOException {

		EngineeringCoordinateReferenceSystem baseCrs = (EngineeringCoordinateReferenceSystem) crs
				.getBase();

		write(CRSKeyword.ENGCRS);

		writeLeftDelimiter();

		writeQuotedText(crs.getName());

		writeSeparator();
		write(CRSKeyword.BASEENGCRS);

		writeLeftDelimiter();

		writeQuotedText(baseCrs.getName());

		writeSeparator();
		write(baseCrs.getDatum());

		if (baseCrs.hasIdentifiers()) {
			writeSeparator();
			writeIdentifiers(baseCrs.getIdentifiers());
		}

		writeRightDelimiter();

		writeSeparator();
		write(crs.getConversion());

		writeSeparator();
		write(crs.getCoordinateSystem());

		writeScopeExtentIdentifierRemark(crs);

		writeRightDelimiter();
	}

	/**
	 * Write a derived parametric CRS to well-known text
	 * 
	 * @param crs
	 *            derived parametric coordinate reference system
	 * @throws IOException
	 *             upon failure to write
	 */
	public void writeDerivedParametricCRS(DerivedCoordinateReferenceSystem crs)
			throws IOException {

		ParametricCoordinateReferenceSystem baseCrs = (ParametricCoordinateReferenceSystem) crs
				.getBase();

		write(CRSKeyword.PARAMETRICCRS);

		writeLeftDelimiter();

		writeQuotedText(crs.getName());

		writeSeparator();
		write(CRSKeyword.BASEPARAMCRS);

		writeLeftDelimiter();

		writeQuotedText(baseCrs.getName());

		writeSeparator();
		write(baseCrs.getDatum());

		if (baseCrs.hasIdentifiers()) {
			writeSeparator();
			writeIdentifiers(baseCrs.getIdentifiers());
		}

		writeRightDelimiter();

		writeSeparator();
		write(crs.getConversion());

		writeSeparator();
		write(crs.getCoordinateSystem());

		writeScopeExtentIdentifierRemark(crs);

		writeRightDelimiter();
	}

	/**
	 * Write a derived temporal CRS to well-known text
	 * 
	 * @param crs
	 *            derived temporal coordinate reference system
	 * @throws IOException
	 *             upon failure to write
	 */
	public void writeDerivedTemporalCRS(DerivedCoordinateReferenceSystem crs)
			throws IOException {

		TemporalCoordinateReferenceSystem baseCrs = (TemporalCoordinateReferenceSystem) crs
				.getBase();

		write(CRSKeyword.TIMECRS);

		writeLeftDelimiter();

		writeQuotedText(crs.getName());

		writeSeparator();
		write(CRSKeyword.BASETIMECRS);

		writeLeftDelimiter();

		writeQuotedText(baseCrs.getName());

		writeSeparator();
		write(baseCrs.getDatum());

		if (baseCrs.hasIdentifiers()) {
			writeSeparator();
			writeIdentifiers(baseCrs.getIdentifiers());
		}

		writeRightDelimiter();

		writeSeparator();
		write(crs.getConversion());

		writeSeparator();
		write(crs.getCoordinateSystem());

		writeScopeExtentIdentifierRemark(crs);

		writeRightDelimiter();
	}

	/**
	 * Write a compound CRS to well-known text
	 * 
	 * @param crs
	 *            compound coordinate reference system
	 * @throws IOException
	 *             upon failure to write
	 */
	public void writeCompound(CompoundCoordinateReferenceSystem crs)
			throws IOException {

		write(CRSKeyword.COMPOUNDCRS);

		writeLeftDelimiter();

		writeQuotedText(crs.getName());

		for (CRS coordinateReferenceSystem : crs
				.getCoordinateReferenceSystems()) {
			writeSeparator();
			writeCRS(coordinateReferenceSystem);
		}

		writeScopeExtentIdentifierRemark(crs);

		writeRightDelimiter();
	}

	/**
	 * Write coordinate metadata to well-known text
	 * 
	 * @param metadata
	 *            coordinate metadata
	 * @throws IOException
	 *             upon failure to write
	 */
	public void writeCoordinateMetadata(CoordinateMetadata metadata)
			throws IOException {

		write(CRSKeyword.COORDINATEMETADATA);

		writeLeftDelimiter();

		writeCRS(metadata.getCoordinateReferenceSystem());

		if (metadata.hasEpoch()) {

			writeSeparator();
			write(CRSKeyword.EPOCH);
			writeLeftDelimiter();
			write(metadata.getEpoch());
			writeRightDelimiter();

		}

		writeRightDelimiter();
	}

	/**
	 * Write coordinate operation to well-known text
	 * 
	 * @param operation
	 *            coordinate operation
	 * @throws IOException
	 *             upon failure to write
	 */
	public void writeCoordinateOperation(CoordinateOperation operation)
			throws IOException {

		write(CRSKeyword.COORDINATEOPERATION);

		writeLeftDelimiter();

		writeQuotedText(operation.getName());

		if (operation.hasVersion()) {
			writeSeparator();
			writeVersion(operation.getVersion());
		}

		writeSeparator();
		writeSource(operation.getSource());

		writeSeparator();
		writeTarget(operation.getTarget());

		writeSeparator();
		write(operation.getMethod());

		if (operation.hasParameters()) {
			writeSeparator();
			writeParametersAndFiles(operation.getParameters());
		}

		if (operation.hasInterpolation()) {
			writeSeparator();
			writeInterpolation(operation.getInterpolation());
		}

		if (operation.hasAccuracy()) {
			writeSeparator();
			writeAccuracy(operation.getAccuracy());
		}

		writeScopeExtentIdentifierRemark(operation);

		writeRightDelimiter();
	}

	/**
	 * Write point motion operation to well-known text
	 * 
	 * @param operation
	 *            coordinate operation
	 * @throws IOException
	 *             upon failure to write
	 */
	public void writePointMotionOperation(PointMotionOperation operation)
			throws IOException {

		write(CRSKeyword.POINTMOTIONOPERATION);

		writeLeftDelimiter();

		writeQuotedText(operation.getName());

		if (operation.hasVersion()) {
			writeSeparator();
			writeVersion(operation.getVersion());
		}

		writeSeparator();
		writeSource(operation.getSource());

		writeSeparator();
		write(operation.getMethod());

		if (operation.hasParameters()) {
			writeSeparator();
			writeParametersAndFiles(operation.getParameters());
		}

		if (operation.hasAccuracy()) {
			writeSeparator();
			writeAccuracy(operation.getAccuracy());
		}

		writeScopeExtentIdentifierRemark(operation);

		writeRightDelimiter();
	}

	/**
	 * Write the CRS usages (scope and extent), identifiers, and remark
	 * 
	 * @param crs
	 *            coordinate reference system
	 * @throws IOException
	 *             upon failure to write
	 */
	public void writeScopeExtentIdentifierRemark(CommonCRS crs)
			throws IOException {

		if (crs.hasUsages()) {
			writeSeparator();
			writeUsages(crs.getUsages());
		}

		if (crs.hasIdentifiers()) {
			writeSeparator();
			writeIdentifiers(crs.getIdentifiers());
		}

		if (crs.hasRemark()) {
			writeSeparator();
			writeRemark(crs.getRemark());
		}

	}

	/**
	 * Write a reference frame to well-known text
	 * 
	 * @param referenceFrame
	 *            reference frame
	 * @throws IOException
	 *             upon failure to write
	 */
	public void write(ReferenceFrame referenceFrame) throws IOException {

		GeoReferenceFrame geodeticReferenceFrame = null;
		if (referenceFrame instanceof GeoReferenceFrame) {
			geodeticReferenceFrame = (GeoReferenceFrame) referenceFrame;
		}

		switch (referenceFrame.getType()) {
		case GEODETIC:
		case GEOGRAPHIC:
			write(CRSKeyword.DATUM);
			break;
		case VERTICAL:
			write(CRSKeyword.VDATUM);
			break;
		case ENGINEERING:
			write(CRSKeyword.EDATUM);
			break;
		case PARAMETRIC:
			write(CRSKeyword.PDATUM);
			break;
		default:
			throw new ProjectionException(
					"Unexpected Reference Frame Coordinate Reference System Type: "
							+ referenceFrame.getType());
		}

		writeLeftDelimiter();

		writeQuotedText(referenceFrame.getName());

		if (geodeticReferenceFrame != null) {
			writeSeparator();
			write(geodeticReferenceFrame.getEllipsoid());
		}

		if (referenceFrame.hasAnchor()) {
			writeSeparator();
			writeKeywordDelimitedQuotedText(CRSKeyword.ANCHOR,
					referenceFrame.getAnchor());
		}

		if (referenceFrame.hasIdentifiers()) {
			writeSeparator();
			writeIdentifiers(referenceFrame.getIdentifiers());
		}

		writeRightDelimiter();

		if (geodeticReferenceFrame != null
				&& geodeticReferenceFrame.hasPrimeMeridian()) {
			writeSeparator();
			write(geodeticReferenceFrame.getPrimeMeridian());
		}

	}

	/**
	 * Write a datum ensemble to well-known text
	 * 
	 * @param datumEnsemble
	 *            datum ensemble
	 * @throws IOException
	 *             upon failure to write
	 */
	public void write(DatumEnsemble datumEnsemble) throws IOException {

		GeoDatumEnsemble geodeticDatumEnsemble = null;
		if (datumEnsemble instanceof GeoDatumEnsemble) {
			geodeticDatumEnsemble = (GeoDatumEnsemble) datumEnsemble;
		}

		write(CRSKeyword.ENSEMBLE);

		writeLeftDelimiter();

		writeQuotedText(datumEnsemble.getName());

		for (DatumEnsembleMember member : datumEnsemble.getMembers()) {
			writeSeparator();
			write(member);
		}

		if (geodeticDatumEnsemble != null) {
			writeSeparator();
			write(geodeticDatumEnsemble.getEllipsoid());
		}

		writeSeparator();
		write(CRSKeyword.ENSEMBLEACCURACY);
		writeLeftDelimiter();
		write(datumEnsemble.getAccuracy());
		writeRightDelimiter();

		if (datumEnsemble.hasIdentifiers()) {
			writeSeparator();
			writeIdentifiers(datumEnsemble.getIdentifiers());
		}

		writeRightDelimiter();

		if (geodeticDatumEnsemble != null
				&& geodeticDatumEnsemble.hasPrimeMeridian()) {
			// TODO http://ogc.standardstracker.org/show_request.cgi?id=672
			writeSeparator();
			write(geodeticDatumEnsemble.getPrimeMeridian());
		}

	}

	/**
	 * Write a datum ensemble member to well-known text
	 * 
	 * @param datumEnsembleMember
	 *            datum ensemble member
	 * @throws IOException
	 *             upon failure to write
	 */
	public void write(DatumEnsembleMember datumEnsembleMember)
			throws IOException {

		write(CRSKeyword.MEMBER);

		writeLeftDelimiter();

		writeQuotedText(datumEnsembleMember.getName());

		if (datumEnsembleMember.hasIdentifiers()) {
			writeSeparator();
			writeIdentifiers(datumEnsembleMember.getIdentifiers());
		}

		writeRightDelimiter();
	}

	/**
	 * Write a dynamic to well-known text
	 * 
	 * @param dynamic
	 *            dynamic
	 * @throws IOException
	 *             upon failure to write
	 */
	public void write(Dynamic dynamic) throws IOException {

		write(CRSKeyword.DYNAMIC);

		writeLeftDelimiter();

		write(CRSKeyword.FRAMEEPOCH);

		writeLeftDelimiter();

		write(dynamic.getReferenceEpoch());

		writeRightDelimiter();

		if (dynamic.hasDeformationModelName()) {

			writeSeparator();
			write(CRSKeyword.MODEL);

			writeLeftDelimiter();

			writeQuotedText(dynamic.getDeformationModelName());

			if (dynamic.hasDeformationModelIdentifiers()) {
				writeSeparator();
				writeIdentifiers(dynamic.getDeformationModelIdentifiers());
			}

			writeRightDelimiter();
		}

		writeRightDelimiter();
	}

	/**
	 * Write a prime meridian to well-known text
	 * 
	 * @param primeMeridian
	 *            prime meridian
	 * @throws IOException
	 *             upon failure to write
	 */
	public void write(PrimeMeridian primeMeridian) throws IOException {

		write(CRSKeyword.PRIMEM);

		writeLeftDelimiter();

		writeQuotedText(primeMeridian.getName());

		writeSeparator();

		write(primeMeridian.getIrmLongitude());

		if (primeMeridian.hasIrmLongitudeUnit()) {
			writeSeparator();
			write(primeMeridian.getIrmLongitudeUnit());
		}

		if (primeMeridian.hasIdentifiers()) {
			writeSeparator();
			writeIdentifiers(primeMeridian.getIdentifiers());
		}

		writeRightDelimiter();
	}

	/**
	 * Write an ellipsoid to well-known text
	 * 
	 * @param ellipsoid
	 *            ellipsoid
	 * @throws IOException
	 *             upon failure to write
	 */
	public void write(Ellipsoid ellipsoid) throws IOException {

		write(CRSKeyword.ELLIPSOID);

		writeLeftDelimiter();

		writeQuotedText(ellipsoid.getName());

		writeSeparator();

		write(ellipsoid.getSemiMajorAxis());

		writeSeparator();

		write(ellipsoid.getInverseFlattening());

		if (ellipsoid.hasUnit()) {
			writeSeparator();
			write(ellipsoid.getUnit());
		}

		if (ellipsoid.hasIdentifiers()) {
			writeSeparator();
			writeIdentifiers(ellipsoid.getIdentifiers());
		}

		writeRightDelimiter();
	}

	/**
	 * Write a unit to well-known text
	 * 
	 * @param unit
	 *            unit
	 * @throws IOException
	 *             upon failure to write
	 */
	public void write(Unit unit) throws IOException {

		writer.write(unit.getType().name());

		writeLeftDelimiter();

		writeQuotedText(unit.getName());

		if (unit.hasConversionFactor()) {
			writeSeparator();
			write(unit.getConversionFactor());
		}

		if (unit.hasIdentifiers()) {
			writeSeparator();
			writeIdentifiers(unit.getIdentifiers());
		}

		writeRightDelimiter();
	}

	/**
	 * Write identifiers to well-known text
	 * 
	 * @param identifiers
	 *            identifiers
	 * @throws IOException
	 *             upon failure to write
	 */
	public void writeIdentifiers(List<Identifier> identifiers)
			throws IOException {

		for (int i = 0; i < identifiers.size(); i++) {

			if (i > 0) {
				writeSeparator();
			}

			write(identifiers.get(i));
		}

	}

	/**
	 * Write an identifier to well-known text
	 * 
	 * @param identifier
	 *            identifier
	 * @throws IOException
	 *             upon failure to write
	 */
	public void write(Identifier identifier) throws IOException {

		write(CRSKeyword.ID);

		writeLeftDelimiter();

		writeQuotedText(identifier.getName());

		writeSeparator();

		writeNumberOrQuotedText(identifier.getUniqueIdentifier());

		if (identifier.hasVersion()) {
			writeSeparator();
			writeNumberOrQuotedText(identifier.getVersion());
		}

		if (identifier.hasCitation()) {
			writeSeparator();
			writeKeywordDelimitedQuotedText(CRSKeyword.CITATION,
					identifier.getCitation());
		}

		if (identifier.hasUri()) {
			writeSeparator();
			writeKeywordDelimitedQuotedText(CRSKeyword.URI,
					identifier.getUri());
		}

		writeRightDelimiter();
	}

	/**
	 * Write a coordinate system to well-known text
	 * 
	 * @param coordinateSystem
	 *            coordinate system
	 * @throws IOException
	 *             upon failure to write
	 */
	public void write(CoordinateSystem coordinateSystem) throws IOException {

		write(CRSKeyword.CS);

		writeLeftDelimiter();

		writer.write(coordinateSystem.getType().getName());

		writeSeparator();

		write(coordinateSystem.getDimension());

		if (coordinateSystem.hasIdentifiers()) {
			writeSeparator();
			writeIdentifiers(coordinateSystem.getIdentifiers());
		}

		writeRightDelimiter();

		for (Axis axis : coordinateSystem.getAxes()) {
			writeSeparator();
			write(axis);
		}

		if (coordinateSystem.hasUnit()) {
			writeSeparator();
			write(coordinateSystem.getUnit());
		}

	}

	/**
	 * Write an axis to well-known text
	 * 
	 * @param axis
	 *            axis
	 * @throws IOException
	 *             upon failure to write
	 */
	public void write(Axis axis) throws IOException {

		write(CRSKeyword.AXIS);

		writeLeftDelimiter();

		StringBuilder nameAbbrev = new StringBuilder();
		if (axis.hasName()) {
			nameAbbrev.append(axis.getName());
		}
		if (axis.hasAbbreviation()) {
			if (nameAbbrev.length() > 0) {
				nameAbbrev.append(" ");
			}
			nameAbbrev.append(WKTConstants.AXIS_ABBREV_LEFT_DELIMITER);
			nameAbbrev.append(axis.getAbbreviation());
			nameAbbrev.append(WKTConstants.AXIS_ABBREV_RIGHT_DELIMITER);
		}
		writeQuotedText(nameAbbrev.toString());

		writeSeparator();

		writer.write(axis.getDirection().getName());

		switch (axis.getDirection()) {

		case NORTH:
		case SOUTH:

			if (axis.hasMeridian()) {

				writeSeparator();
				write(CRSKeyword.MERIDIAN);

				writeLeftDelimiter();

				write(axis.getMeridian());

				writeSeparator();

				write(axis.getMeridianUnit());

				writeRightDelimiter();
			}

			break;

		case CLOCKWISE:
		case COUNTER_CLOCKWISE:

			writeSeparator();
			write(CRSKeyword.BEARING);

			writeLeftDelimiter();

			write(axis.getBearing());

			writeRightDelimiter();

			break;

		default:
		}

		if (axis.hasOrder()) {

			writeSeparator();
			write(CRSKeyword.ORDER);

			writeLeftDelimiter();

			write(axis.getOrder());

			writeRightDelimiter();
		}

		if (axis.hasUnit()) {
			writeSeparator();
			write(axis.getUnit());
		}

		if (axis.hasIdentifiers()) {
			writeSeparator();
			writeIdentifiers(axis.getIdentifiers());
		}

		writeRightDelimiter();
	}

	/**
	 * Write a remark to well-known text
	 * 
	 * @param remark
	 *            remark
	 * @throws IOException
	 *             upon failure to write
	 */
	public void writeRemark(String remark) throws IOException {
		writeKeywordDelimitedQuotedText(CRSKeyword.REMARK, remark);
	}

	/**
	 * Write usages to well-known text
	 * 
	 * @param usages
	 *            usages
	 * @throws IOException
	 *             upon failure to write
	 */
	public void writeUsages(List<Usage> usages) throws IOException {

		for (int i = 0; i < usages.size(); i++) {

			if (i > 0) {
				writeSeparator();
			}

			write(usages.get(i));
		}

	}

	/**
	 * Write a usage to well-known text
	 * 
	 * @param usage
	 *            usage
	 * @throws IOException
	 *             upon failure to write
	 */
	public void write(Usage usage) throws IOException {

		write(CRSKeyword.USAGE);

		writeLeftDelimiter();

		writeScope(usage.getScope());

		write(usage.getExtent());

		writeRightDelimiter();
	}

	/**
	 * Write a scope to well-known text
	 * 
	 * @param scope
	 *            scope
	 * @throws IOException
	 *             upon failure to write
	 */
	public void writeScope(String scope) throws IOException {
		writeKeywordDelimitedQuotedText(CRSKeyword.SCOPE, scope);
	}

	/**
	 * Write an extent to well-known text
	 * 
	 * @param extent
	 *            extent
	 * @throws IOException
	 *             upon failure to write
	 */
	public void write(Extent extent) throws IOException {

		if (extent.hasAreaDescription()) {
			writeSeparator();
			writeAreaDescription(extent.getAreaDescription());
		}

		if (extent.hasGeographicBoundingBox()) {
			writeSeparator();
			write(extent.getGeographicBoundingBox());
		}

		if (extent.hasVerticalExtent()) {
			writeSeparator();
			write(extent.getVerticalExtent());
		}

		if (extent.hasTemporalExtent()) {
			writeSeparator();
			write(extent.getTemporalExtent());
		}

	}

	/**
	 * Write an area description to well-known text
	 * 
	 * @param areaDescription
	 *            area description
	 * @throws IOException
	 *             upon failure to write
	 */
	public void writeAreaDescription(String areaDescription)
			throws IOException {
		writeKeywordDelimitedQuotedText(CRSKeyword.AREA, areaDescription);
	}

	/**
	 * Write a geographic bounding box to well-known text
	 * 
	 * @param geographicBoundingBox
	 *            geographic bounding box
	 * @throws IOException
	 *             upon failure to write
	 */
	public void write(GeographicBoundingBox geographicBoundingBox)
			throws IOException {

		write(CRSKeyword.BBOX);

		writeLeftDelimiter();

		write(geographicBoundingBox.getLowerLeftLatitude());

		writeSeparator();

		write(geographicBoundingBox.getLowerLeftLongitude());

		writeSeparator();

		write(geographicBoundingBox.getUpperRightLatitude());

		writeSeparator();

		write(geographicBoundingBox.getUpperRightLongitude());

		writeRightDelimiter();
	}

	/**
	 * Write a vertical extent to well-known text
	 * 
	 * @param verticalExtent
	 *            vertical extent
	 * @throws IOException
	 *             upon failure to write
	 */
	public void write(VerticalExtent verticalExtent) throws IOException {

		write(CRSKeyword.VERTICALEXTENT);

		writeLeftDelimiter();

		write(verticalExtent.getMinimumHeight());

		writeSeparator();

		write(verticalExtent.getMaximumHeight());

		if (verticalExtent.hasUnit()) {
			writeSeparator();
			write(verticalExtent.getUnit());
		}

		writeRightDelimiter();
	}

	/**
	 * Write a temporal extent to well-known text
	 * 
	 * @param temporalExtent
	 *            temporal extent
	 * @throws IOException
	 *             upon failure to write
	 */
	public void write(TemporalExtent temporalExtent) throws IOException {

		write(CRSKeyword.TIMEEXTENT);

		writeLeftDelimiter();

		if (temporalExtent.hasStartDateTime()) {
			writer.write(temporalExtent.getStartDateTime().toString());
		} else {
			writeQuotedText(temporalExtent.getStart());
		}

		writeSeparator();

		if (temporalExtent.hasEndDateTime()) {
			writer.write(temporalExtent.getEndDateTime().toString());
		} else {
			writeQuotedText(temporalExtent.getEnd());
		}

		writeRightDelimiter();
	}

	/**
	 * Write a map projection to well-known text
	 * 
	 * @param mapProjection
	 *            map projection
	 * @throws IOException
	 *             upon failure to write
	 */
	public void write(MapProjection mapProjection) throws IOException {

		write(CRSKeyword.CONVERSION);

		writeLeftDelimiter();

		writeQuotedText(mapProjection.getName());

		writeSeparator();

		write(mapProjection.getMethod());

		if (mapProjection.hasParameters()) {
			writeSeparator();
			writeParameters(mapProjection.getParameters());
		}

		if (mapProjection.hasIdentifiers()) {
			writeSeparator();
			writeIdentifiers(mapProjection.getIdentifiers());
		}

		writeRightDelimiter();
	}

	/**
	 * Write an operation method to well-known text
	 * 
	 * @param method
	 *            operation method
	 * @throws IOException
	 *             upon failure to write
	 */
	public void write(OperationMethod method) throws IOException {

		write(CRSKeyword.METHOD);

		writeLeftDelimiter();

		writeQuotedText(method.getName());

		if (method.hasIdentifiers()) {
			writeSeparator();
			writeIdentifiers(method.getIdentifiers());
		}

		writeRightDelimiter();
	}

	/**
	 * Write operation parameters to well-known text
	 * 
	 * @param parameters
	 *            operation parameters
	 * @throws IOException
	 *             upon failure to write
	 */
	public void writeParameters(List<OperationParameter> parameters)
			throws IOException {

		for (int i = 0; i < parameters.size(); i++) {

			if (i > 0) {
				writeSeparator();
			}

			write(parameters.get(i));
		}

	}

	/**
	 * Write an operation parameter to well-known text
	 * 
	 * @param parameter
	 *            operation parameter
	 * @throws IOException
	 *             upon failure to write
	 */
	public void write(OperationParameter parameter) throws IOException {

		write(CRSKeyword.PARAMETER);

		writeLeftDelimiter();

		writeQuotedText(parameter.getName());

		writeSeparator();

		write(parameter.getValue());

		if (parameter.hasUnit()) {
			writeSeparator();
			write(parameter.getUnit());
		}

		if (parameter.hasIdentifiers()) {
			writeSeparator();
			writeIdentifiers(parameter.getIdentifiers());
		}

		writeRightDelimiter();
	}

	/**
	 * Write a temporal datum to well-known text
	 * 
	 * @param temporalDatum
	 *            temporal datum
	 * @throws IOException
	 *             upon failure to write
	 */
	public void write(TemporalDatum temporalDatum) throws IOException {

		write(CRSKeyword.TDATUM);

		writeLeftDelimiter();

		writeQuotedText(temporalDatum.getName());

		if (temporalDatum.hasCalendar()) {
			writeSeparator();
			writeKeywordDelimitedQuotedText(CRSKeyword.CALENDAR,
					temporalDatum.getCalendar());
		}

		if (temporalDatum.hasOrigin() || temporalDatum.hasOriginDateTime()) {
			writeSeparator();
			write(CRSKeyword.TIMEORIGIN);
			writeLeftDelimiter();
			if (temporalDatum.hasOriginDateTime()) {
				writer.write(temporalDatum.getOriginDateTime().toString());
			} else {
				writeQuotedText(temporalDatum.getOrigin());
			}
			writeRightDelimiter();
		}

		if (temporalDatum.hasIdentifiers()) {
			writeSeparator();
			writeIdentifiers(temporalDatum.getIdentifiers());
		}

		writeRightDelimiter();
	}

	/**
	 * Write a deriving conversion to well-known text
	 * 
	 * @param derivingConversion
	 *            deriving conversion
	 * @throws IOException
	 *             upon failure to write
	 */
	public void write(DerivingConversion derivingConversion)
			throws IOException {

		write(CRSKeyword.DERIVINGCONVERSION);

		writeLeftDelimiter();

		writeQuotedText(derivingConversion.getName());

		writeSeparator();

		write(derivingConversion.getMethod());

		if (derivingConversion.hasParameters()) {
			writeSeparator();
			writeParametersAndFiles(derivingConversion.getParameters());
		}

		if (derivingConversion.hasIdentifiers()) {
			writeSeparator();
			writeIdentifiers(derivingConversion.getIdentifiers());
		}

		writeRightDelimiter();
	}

	/**
	 * Write operation parameters and operation parameter files to well-known
	 * text
	 * 
	 * @param parameters
	 *            operation parameters
	 * @throws IOException
	 *             upon failure to write
	 */
	public void writeParametersAndFiles(List<Parameter> parameters)
			throws IOException {

		for (int i = 0; i < parameters.size(); i++) {

			if (i > 0) {
				writeSeparator();
			}

			write(parameters.get(i));
		}

	}

	/**
	 * Write a parameter to well-known text
	 * 
	 * @param parameter
	 *            parameter
	 * @throws IOException
	 *             upon failure to write
	 */
	public void write(Parameter parameter) throws IOException {

		if (parameter instanceof OperationParameter) {
			write((OperationParameter) parameter);
		} else if (parameter instanceof OperationParameterFile) {
			write((OperationParameterFile) parameter);
		} else {
			throw new ProjectionException("Unsupported parameter type: "
					+ parameter.getClass().getSimpleName());
		}

	}

	/**
	 * Write an operation parameter to well-known text
	 * 
	 * @param file
	 *            operation parameter file
	 * @throws IOException
	 *             upon failure to write
	 */
	public void write(OperationParameterFile file) throws IOException {

		write(CRSKeyword.PARAMETERFILE);

		writeLeftDelimiter();

		writeQuotedText(file.getName());

		writeSeparator();

		writeQuotedText(file.getFileName());

		if (file.hasIdentifiers()) {
			writeSeparator();
			writeIdentifiers(file.getIdentifiers());
		}

		writeRightDelimiter();
	}

	/**
	 * Write an operation version
	 * 
	 * @param version
	 *            operation version
	 * @throws IOException
	 *             upon failure to write
	 */
	public void writeVersion(String version) throws IOException {

		write(CRSKeyword.VERSION);

		writeLeftDelimiter();

		writeQuotedText(version);

		writeRightDelimiter();
	}

	/**
	 * Write a source coordinate reference system
	 * 
	 * @param crs
	 *            coordinate reference system
	 * @throws IOException
	 *             upon failure to write
	 */
	public void writeSource(CoordinateReferenceSystem crs) throws IOException {
		writeCoordinateReferenceSystem(CRSKeyword.SOURCECRS, crs);
	}

	/**
	 * Write a target coordinate reference system
	 * 
	 * @param crs
	 *            coordinate reference system
	 * @throws IOException
	 *             upon failure to write
	 */
	public void writeTarget(CoordinateReferenceSystem crs) throws IOException {
		writeCoordinateReferenceSystem(CRSKeyword.TARGETCRS, crs);
	}

	/**
	 * Write a interpolation coordinate reference system
	 * 
	 * @param crs
	 *            coordinate reference system
	 * @throws IOException
	 *             upon failure to write
	 */
	public void writeInterpolation(CoordinateReferenceSystem crs)
			throws IOException {
		writeCoordinateReferenceSystem(CRSKeyword.INTERPOLATIONCRS, crs);
	}

	/**
	 * Write a coordinate reference system with the keyword
	 * 
	 * @param keyword
	 *            CRS keyword
	 * @param crs
	 *            coordinate reference system
	 * @throws IOException
	 *             upon failure to write
	 */
	public void writeCoordinateReferenceSystem(CRSKeyword keyword,
			CoordinateReferenceSystem crs) throws IOException {

		write(keyword);
		writeLeftDelimiter();

		writeCRS(crs);

		writeRightDelimiter();
	}

	/**
	 * Write an operation accuracy
	 * 
	 * @param accuracy
	 *            operation accuracy
	 * @throws IOException
	 *             upon failure to write
	 */
	public void writeAccuracy(double accuracy) throws IOException {

		write(CRSKeyword.OPERATIONACCURACY);

		writeLeftDelimiter();

		write(accuracy);

		writeRightDelimiter();
	}

}
