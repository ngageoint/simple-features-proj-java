package mil.nga.proj.crs.vertical;

import java.util.logging.Logger;

import mil.nga.proj.crs.CoordinateReferenceSystem;
import mil.nga.proj.crs.CoordinateReferenceSystemType;
import mil.nga.proj.crs.common.CoordinateSystem;
import mil.nga.proj.crs.common.Dynamic;
import mil.nga.proj.crs.common.Identifier;

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

	/**
	 * Geoid Model Name
	 */
	private String geoidModelName;

	/**
	 * Geoid Model Identifier
	 */
	private Identifier geoidModelIdentifier;

	/**
	 * Constructor
	 */
	public VerticalCoordinateReferenceSystem() {
		super(CoordinateReferenceSystemType.VERTICAL);
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

	/**
	 * Get the geoid model name
	 * 
	 * @return geoid model name
	 */
	public String getGeoidModelName() {
		return geoidModelName;
	}

	/**
	 * Has a geoid model name
	 * 
	 * @return true if has geoid model name
	 */
	public boolean hasGeoidModelName() {
		return getGeoidModelName() != null;
	}

	/**
	 * Set the geoid model name
	 * 
	 * @param geoidModelName
	 *            geoid model name
	 */
	public void setGeoidModelName(String geoidModelName) {
		this.geoidModelName = geoidModelName;
	}

	/**
	 * Get the geoid model identifier
	 * 
	 * @return geoid model identifier
	 */
	public Identifier getGeoidModelIdentifier() {
		return geoidModelIdentifier;
	}

	/**
	 * Has a geoid model identifier
	 * 
	 * @return true if has geoid model identifier
	 */
	public boolean hasGeoidModelIdentifier() {
		return getGeoidModelIdentifier() != null;
	}

	/**
	 * Set the geoid model identifier
	 * 
	 * @param geoidModelIdentifier
	 *            geoid model identifier
	 */
	public void setGeoidModelIdentifier(Identifier geoidModelIdentifier) {
		this.geoidModelIdentifier = geoidModelIdentifier;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((dynamic == null) ? 0 : dynamic.hashCode());
		result = prime * result + ((geoidModelIdentifier == null) ? 0
				: geoidModelIdentifier.hashCode());
		result = prime * result
				+ ((geoidModelName == null) ? 0 : geoidModelName.hashCode());
		result = prime * result + ((verticalDatumEnsemble == null) ? 0
				: verticalDatumEnsemble.hashCode());
		result = prime * result + ((verticalReferenceFrame == null) ? 0
				: verticalReferenceFrame.hashCode());
		return result;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		VerticalCoordinateReferenceSystem other = (VerticalCoordinateReferenceSystem) obj;
		if (dynamic == null) {
			if (other.dynamic != null)
				return false;
		} else if (!dynamic.equals(other.dynamic))
			return false;
		if (geoidModelIdentifier == null) {
			if (other.geoidModelIdentifier != null)
				return false;
		} else if (!geoidModelIdentifier.equals(other.geoidModelIdentifier))
			return false;
		if (geoidModelName == null) {
			if (other.geoidModelName != null)
				return false;
		} else if (!geoidModelName.equals(other.geoidModelName))
			return false;
		if (verticalDatumEnsemble == null) {
			if (other.verticalDatumEnsemble != null)
				return false;
		} else if (!verticalDatumEnsemble.equals(other.verticalDatumEnsemble))
			return false;
		if (verticalReferenceFrame == null) {
			if (other.verticalReferenceFrame != null)
				return false;
		} else if (!verticalReferenceFrame.equals(other.verticalReferenceFrame))
			return false;
		return true;
	}

}
