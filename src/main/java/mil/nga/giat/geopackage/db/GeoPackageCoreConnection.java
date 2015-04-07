package mil.nga.giat.geopackage.db;

import java.io.Closeable;
import java.nio.ByteBuffer;

import mil.nga.giat.geopackage.GeoPackageConstants;

import com.j256.ormlite.support.ConnectionSource;

/**
 * GeoPackage Connection used to define common functionality within different
 * connection types
 * 
 * @author osbornb
 */
public abstract class GeoPackageCoreConnection implements Closeable {

	/**
	 * Get a connection source
	 * 
	 * @return
	 */
	public abstract ConnectionSource getConnectionSource();

	/**
	 * Execute the sql
	 * 
	 * @param sql
	 */
	public abstract void execSQL(String sql);

	/**
	 * Check if the table exists
	 * 
	 * @param tableName
	 * @return
	 */
	public abstract boolean tableExists(String tableName);

	/**
	 * Convenience method for deleting rows in the database.
	 * 
	 * @param table
	 * @param whereClause
	 * @param whereArgs
	 * @return
	 */
	public abstract int delete(String table, String whereClause,
			String[] whereArgs);

	/**
	 * {@inheritDoc}
	 */
	@Override
	public abstract void close();

	/**
	 * Set the GeoPackage application id
	 */
	public void setApplicationId() {
		// Set the application id as a GeoPackage
		int applicationId = ByteBuffer
				.wrap(GeoPackageConstants.APPLICATION_ID.getBytes())
				.asIntBuffer().get();
		execSQL(String.format("PRAGMA application_id = %d;", applicationId));
	}

}
