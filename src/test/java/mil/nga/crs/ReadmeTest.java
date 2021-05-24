package mil.nga.crs;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.Test;

import mil.nga.crs.wkt.CRSReader;
import mil.nga.crs.wkt.CRSWriter;

/**
 * README example tests
 * 
 * @author osbornb
 */
public class ReadmeTest {

	/**
	 * Test crs
	 * 
	 * @throws IOException
	 *             upon failure
	 */
	@Test
	public void testCRS() throws IOException {

		String wkt = "GEOGCRS[\"WGS 84\",ENSEMBLE[\"World Geodetic System 1984 ensemble\","
				+ "MEMBER[\"World Geodetic System 1984 (Transit)\",ID[\"EPSG\",1166]],"
				+ "MEMBER[\"World Geodetic System 1984 (G730)\",ID[\"EPSG\",1152]],"
				+ "MEMBER[\"World Geodetic System 1984 (G873)\",ID[\"EPSG\",1153]],"
				+ "MEMBER[\"World Geodetic System 1984 (G1150)\",ID[\"EPSG\",1154]],"
				+ "MEMBER[\"World Geodetic System 1984 (G1674)\",ID[\"EPSG\",1155]],"
				+ "MEMBER[\"World Geodetic System 1984 (G1762)\",ID[\"EPSG\",1156]],"
				+ "ELLIPSOID[\"WGS 84\",6378137.0,298.257223563,ID[\"EPSG\",7030]],"
				+ "ENSEMBLEACCURACY[2.0],ID[\"EPSG\",6326]],"
				+ "CS[ellipsoidal,2,ID[\"EPSG\",6422]],"
				+ "AXIS[\"Geodetic latitude (Lat)\",north],AXIS[\"Geodetic longitude (Lon)\",east],"
				+ "ANGLEUNIT[\"degree\",0.0174532925199433,ID[\"EPSG\",9102]],"
				+ "ID[\"EPSG\",4326]]";
		assertEquals(wkt, testCRS(wkt));

	}

	/**
	 * Test crs
	 * 
	 * @param wkt
	 *            crs well-known text
	 * @return well-known text
	 * @throws IOException
	 *             upon failure
	 */
	private String testCRS(String wkt) throws IOException {

		// String wkt = ...

		CRS crs = CRSReader.read(wkt);

		CRSType type = crs.getType();
		CategoryType category = crs.getCategoryType();

		String text = CRSWriter.write(crs);
		String prettyText = CRSWriter.write(crs);

		return text;
	}

}
