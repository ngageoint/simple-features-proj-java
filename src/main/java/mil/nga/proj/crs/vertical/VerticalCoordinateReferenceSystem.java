package mil.nga.proj.crs.vertical;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import mil.nga.proj.crs.CoordinateReferenceSystem;
import mil.nga.proj.crs.CoordinateReferenceSystemType;
import mil.nga.proj.crs.common.CoordinateSystem;
import mil.nga.proj.crs.common.Dynamic;
import mil.nga.proj.crs.wkt.CRSWriter;

/**
 * Vertical Coordinate Reference System
 * 
 * @author osbornb
 */
public class VerticalCoordinateReferenceSystem
		extends CoordinateReferenceSystem {

	/**
	 * Logger
	 */
	private static final Logger logger = Logger
			.getLogger(VerticalCoordinateReferenceSystem.class.getName());

	/**
	 * Vertical Reference Frame
	 */
	private VerticalReferenceFrame verticalReferenceFrame = null;

	/**
	 * Vertical Datum Ensemble
	 */
	private VerticalDatumEnsemble verticalDatumEnsemble = null;

	/**
	 * Dynamic coordinate reference system
	 */
	private Dynamic dynamic = null;

	// TODO geoid model ID

	/**
	 * Constructor
	 */
	public VerticalCoordinateReferenceSystem() {
		setType(CoordinateReferenceSystemType.VERTICAL);
	}

	/**
	 * Constructor
	 * 
	 * @param name
	 *            name
	 * @param verticalReferenceFrame
	 *            vertical reference frame
	 * @param coordinateSystem
	 *            coordinate system
	 */
	public VerticalCoordinateReferenceSystem(String name,
			VerticalReferenceFrame verticalReferenceFrame,
			CoordinateSystem coordinateSystem) {
		super(name, CoordinateReferenceSystemType.VERTICAL, coordinateSystem);
		setVerticalReferenceFrame(verticalReferenceFrame);
	}

	/**
	 * Constructor
	 * 
	 * @param name
	 *            name
	 * @param verticalDatumEnsemble
	 *            vertical datum ensemble
	 * @param coordinateSystem
	 *            coordinate system
	 */
	public VerticalCoordinateReferenceSystem(String name,
			VerticalDatumEnsemble verticalDatumEnsemble,
			CoordinateSystem coordinateSystem) {
		super(name, CoordinateReferenceSystemType.VERTICAL, coordinateSystem);
		setVerticalDatumEnsemble(verticalDatumEnsemble);
	}

	/**
	 * Constructor
	 * 
	 * @param name
	 *            name
	 * @param dynamic
	 *            dynamic
	 * @param verticalReferenceFrame
	 *            vertical reference frame
	 * @param coordinateSystem
	 *            coordinate system
	 */
	public VerticalCoordinateReferenceSystem(String name, Dynamic dynamic,
			VerticalReferenceFrame verticalReferenceFrame,
			CoordinateSystem coordinateSystem) {
		super(name, CoordinateReferenceSystemType.VERTICAL, coordinateSystem);
		setDynamic(dynamic);
		setVerticalReferenceFrame(verticalReferenceFrame);
	}

	/**
	 * Get the vertical reference frame
	 * 
	 * @return vertical reference frame
	 */
	public VerticalReferenceFrame getVerticalReferenceFrame() {
		return verticalReferenceFrame;
	}

	/**
	 * Determine if has a geodetic reference frame
	 * 
	 * @return true if has geodetic reference frame
	 */
	public boolean hasVerticalReferenceFrame() {
		return getVerticalReferenceFrame() != null;
	}

	/**
	 * Set the vertical reference frame
	 * 
	 * @param verticalReferenceFrame
	 *            vertical reference frame
	 */
	public void setVerticalReferenceFrame(
			VerticalReferenceFrame verticalReferenceFrame) {
		this.verticalReferenceFrame = verticalReferenceFrame;
	}

	/**
	 * Get the vertical datum ensemble
	 * 
	 * @return vertical datum ensemble
	 */
	public VerticalDatumEnsemble getVerticalDatumEnsemble() {
		return verticalDatumEnsemble;
	}

	/**
	 * Determine if has a vertical datum ensemble
	 * 
	 * @return true if has vertical datum ensemble
	 */
	public boolean hasVerticalDatumEnsemble() {
		return getVerticalDatumEnsemble() != null;
	}

	/**
	 * Set the vertical datum ensemble
	 * 
	 * @param verticalDatumEnsemble
	 *            vertical datum ensemble
	 */
	public void setVerticalDatumEnsemble(
			VerticalDatumEnsemble verticalDatumEnsemble) {
		this.verticalDatumEnsemble = verticalDatumEnsemble;
	}

	/**
	 * Get the dynamic coordinate reference system
	 * 
	 * @return dynamic coordinate reference system
	 */
	public Dynamic getDynamic() {
		return dynamic;
	}

	/**
	 * Determine if has a dynamic
	 * 
	 * @return true if has dynamic
	 */
	public boolean hasDynamic() {
		return getDynamic() != null;
	}

	/**
	 * Set the dynamic coordinate reference system
	 * 
	 * @param dynamic
	 *            dynamic coordinate reference system
	 */
	public void setDynamic(Dynamic dynamic) {
		this.dynamic = dynamic;
	}

	// TODO

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		String value = null;
		try {
			value = CRSWriter.writeCRS(this);
		} catch (IOException e) {
			logger.log(Level.WARNING,
					"Failed to write vertical coordinate reference system as a string",
					e);
			value = super.toString();
		}
		return value;
	}

}
