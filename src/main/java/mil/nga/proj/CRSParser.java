package mil.nga.proj;

import java.io.IOException;

import org.locationtech.proj4j.CoordinateReferenceSystem;

import mil.nga.crs.CRS;
import mil.nga.crs.wkt.CRSReader;

/**
 * Coordinate Reference System Well-known text parser
 * 
 * @author osbornb
 */
public class CRSParser {

	/**
	 * Parse well-known text into a coordinate reference system
	 * 
	 * @param wkt
	 *            crs well-known text
	 * @return coordiante reference system
	 */
	public static CoordinateReferenceSystem parse(String wkt) {

		CoordinateReferenceSystem crs = null;

		CRS crsObject = null;
		try {
			crsObject = CRSReader.read(wkt);
		} catch (IOException e) {
			throw new ProjectionException("Failed to parse WKT", e);
		}

		// TODO

		return crs;
	}

}
