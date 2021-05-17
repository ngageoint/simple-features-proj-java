package mil.nga.proj.crs.vertical;

import mil.nga.proj.crs.SimpleCoordinateReferenceSystem;
import mil.nga.proj.crs.CRSType;
import mil.nga.proj.crs.common.CoordinateSystem;
import mil.nga.proj.crs.common.Dynamic;
import mil.nga.proj.crs.common.Identifier;

/**
 * Vertical Coordinate Reference System
 * 
 * @author osbornb
 */
public class VerticalCoordinateReferenceSystem
		extends SimpleCoordinateReferenceSystem {

	/**
	 * Vertical Reference Frame
	 */
	private VerticalReferenceFrame referenceFrame = null;

	/**
	 * Vertical Datum Ensemble
	 */
	private VerticalDatumEnsemble datumEnsemble = null;

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
		super(CRSType.VERTICAL);
	}

	/**
	 * Constructor
	 * 
	 * @param name
	 *            name
	 * @param referenceFrame
	 *            vertical reference frame
	 * @param coordinateSystem
	 *            coordinate system
	 */
	public VerticalCoordinateReferenceSystem(String name,
			VerticalReferenceFrame referenceFrame,
			CoordinateSystem coordinateSystem) {
		super(name, CRSType.VERTICAL, coordinateSystem);
		setReferenceFrame(referenceFrame);
	}

	/**
	 * Constructor
	 * 
	 * @param name
	 *            name
	 * @param datumEnsemble
	 *            vertical datum ensemble
	 * @param coordinateSystem
	 *            coordinate system
	 */
	public VerticalCoordinateReferenceSystem(String name,
			VerticalDatumEnsemble datumEnsemble,
			CoordinateSystem coordinateSystem) {
		super(name, CRSType.VERTICAL, coordinateSystem);
		setDatumEnsemble(datumEnsemble);
	}

	/**
	 * Constructor
	 * 
	 * @param name
	 *            name
	 * @param dynamic
	 *            dynamic
	 * @param referenceFrame
	 *            vertical reference frame
	 * @param coordinateSystem
	 *            coordinate system
	 */
	public VerticalCoordinateReferenceSystem(String name, Dynamic dynamic,
			VerticalReferenceFrame referenceFrame,
			CoordinateSystem coordinateSystem) {
		super(name, CRSType.VERTICAL, coordinateSystem);
		setDynamic(dynamic);
		setReferenceFrame(referenceFrame);
	}

	/**
	 * Get the vertical reference frame
	 * 
	 * @return vertical reference frame
	 */
	public VerticalReferenceFrame getReferenceFrame() {
		return referenceFrame;
	}

	/**
	 * Determine if has a vertical reference frame
	 * 
	 * @return true if has vertical reference frame
	 */
	public boolean hasReferenceFrame() {
		return getReferenceFrame() != null;
	}

	/**
	 * Set the vertical reference frame
	 * 
	 * @param referenceFrame
	 *            vertical reference frame
	 */
	public void setReferenceFrame(VerticalReferenceFrame referenceFrame) {
		this.referenceFrame = referenceFrame;
	}

	/**
	 * Get the vertical datum ensemble
	 * 
	 * @return vertical datum ensemble
	 */
	public VerticalDatumEnsemble getDatumEnsemble() {
		return datumEnsemble;
	}

	/**
	 * Determine if has a vertical datum ensemble
	 * 
	 * @return true if has vertical datum ensemble
	 */
	public boolean hasDatumEnsemble() {
		return getDatumEnsemble() != null;
	}

	/**
	 * Set the vertical datum ensemble
	 * 
	 * @param datumEnsemble
	 *            vertical datum ensemble
	 */
	public void setDatumEnsemble(VerticalDatumEnsemble datumEnsemble) {
		this.datumEnsemble = datumEnsemble;
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
		result = prime * result
				+ ((datumEnsemble == null) ? 0 : datumEnsemble.hashCode());
		result = prime * result
				+ ((referenceFrame == null) ? 0 : referenceFrame.hashCode());
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
		if (datumEnsemble == null) {
			if (other.datumEnsemble != null)
				return false;
		} else if (!datumEnsemble.equals(other.datumEnsemble))
			return false;
		if (referenceFrame == null) {
			if (other.referenceFrame != null)
				return false;
		} else if (!referenceFrame.equals(other.referenceFrame))
			return false;
		return true;
	}

}
