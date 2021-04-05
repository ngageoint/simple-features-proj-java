package mil.nga.proj.crs.wkt;

import static org.junit.Assert.assertNotNull;

import java.io.IOException;

import org.junit.Test;

import mil.nga.proj.crs.CoordinateReferenceSystem;
import mil.nga.proj.crs.wkt.CRSReader;

/**
 * CRS Reader tests
 * 
 * @author osbornb
 */
public class CRSReaderTest {

	/**
	 * Test transform
	 * 
	 * @throws IOException
	 *             upon error
	 */
	@Test
	public void testRead4979() throws IOException {

		String text = "GEODCRS[\"WGS 84\",DATUM[\"World Geodetic System 1984\",ELLIPSOID[\"WGS 84\",6378137,298.257223563,LENGTHUNIT[\"metre\",1.0]]],CS[ellipsoidal,3],AXIS[\"Geodetic latitude (Lat)\",north,ANGLEUNIT[\"degree\",0.0174532925199433]],AXIS[\"Geodetic longitude (Long)\",east,ANGLEUNIT[\"degree\",0.0174532925199433]],AXIS[\"Ellipsoidal height (h)\",up,LENGTHUNIT[\"metre\",1.0]],ID[\"EPSG\",4979]]\"";

		CoordinateReferenceSystem crs = testRead(text);
		assertNotNull(crs);
	}

	/**
	 * Test read
	 * 
	 * @param text
	 *            crs wkt
	 * @return coordinate reference system
	 * @throws IOException
	 *             upon error
	 */
	private CoordinateReferenceSystem testRead(String text) throws IOException {

		CoordinateReferenceSystem crs = CRSReader.readCRS(text);

		return crs;
	}

}
