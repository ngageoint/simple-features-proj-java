package mil.nga.proj.crs;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import mil.nga.proj.crs.wkt.CRSWriter;

/**
 * Projected Coordinate Reference System
 * 
 * @author osbornb
 */
public class ProjectedCoordinateReferenceSystem
		extends CoordinateReferenceSystem {

	/**
	 * Logger
	 */
	private static final Logger logger = Logger
			.getLogger(ProjectedCoordinateReferenceSystem.class.getName());

	/**
	 * Name
	 */
	private String baseName = null;

	/**
	 * Base Type
	 */
	private CoordinateReferenceSystemType baseType = null;

	/**
	 * Geodetic Reference Frame
	 */
	private GeodeticReferenceFrame geodeticReferenceFrame = null;

	/**
	 * Geodetic Datum Ensemble
	 */
	private GeodeticDatumEnsemble geodeticDatumEnsemble = null;

	/**
	 * Dynamic coordinate reference system
	 */
	private Dynamic dynamic = null;

	/**
	 * Ellipsoidal Angle Unit
	 */
	private Unit ellipsoidalAngleUnit = null;

	/**
	 * Base Identifiers
	 */
	private List<Identifier> baseIdentifiers = null;

	/**
	 * Map Projection
	 */
	private MapProjection mapProjection = null;

	/**
	 * Constructor
	 */
	public ProjectedCoordinateReferenceSystem() {

	}

	/**
	 * Constructor
	 * 
	 * @param name
	 *            name
	 * @param baseName
	 *            base CRS name
	 * @param baseType
	 *            base coordinate reference system type
	 * @param geodeticReferenceFrame
	 *            geodetic reference frame
	 * @param mapProjection
	 *            map projection
	 * @param coordinateSystem
	 *            coordinate system
	 */
	public ProjectedCoordinateReferenceSystem(String name, String baseName,
			CoordinateReferenceSystemType baseType,
			GeodeticReferenceFrame geodeticReferenceFrame,
			MapProjection mapProjection, CoordinateSystem coordinateSystem) {
		super(name, CoordinateReferenceSystemType.PROJECTED, coordinateSystem);
		setBaseName(baseName);
		setBaseType(baseType);
		setGeodeticReferenceFrame(geodeticReferenceFrame);
		setMapProjection(mapProjection);
	}

	/**
	 * Constructor
	 * 
	 * @param name
	 *            name
	 * @param baseName
	 *            base CRS name
	 * @param baseType
	 *            base coordinate reference system type
	 * @param geodeticDatumEnsemble
	 *            geodetic datum ensemble
	 * @param mapProjection
	 *            map projection
	 * @param coordinateSystem
	 *            coordinate system
	 */
	public ProjectedCoordinateReferenceSystem(String name, String baseName,
			CoordinateReferenceSystemType baseType,
			GeodeticDatumEnsemble geodeticDatumEnsemble,
			MapProjection mapProjection, CoordinateSystem coordinateSystem) {
		super(name, CoordinateReferenceSystemType.PROJECTED, coordinateSystem);
		setBaseName(baseName);
		setBaseType(baseType);
		setGeodeticDatumEnsemble(geodeticDatumEnsemble);
		setMapProjection(mapProjection);
	}

	/**
	 * Constructor
	 * 
	 * @param name
	 *            name
	 * @param baseName
	 *            base CRS name
	 * @param baseType
	 *            base coordinate reference system type
	 * @param dynamic
	 *            dynamic
	 * @param geodeticReferenceFrame
	 *            geodetic reference frame
	 * @param mapProjection
	 *            map projection
	 * @param coordinateSystem
	 *            coordinate system
	 */
	public ProjectedCoordinateReferenceSystem(String name, String baseName,
			CoordinateReferenceSystemType baseType, Dynamic dynamic,
			GeodeticReferenceFrame geodeticReferenceFrame,
			MapProjection mapProjection, CoordinateSystem coordinateSystem) {
		super(name, CoordinateReferenceSystemType.PROJECTED, coordinateSystem);
		setBaseName(baseName);
		setBaseType(baseType);
		setDynamic(dynamic);
		setGeodeticReferenceFrame(geodeticReferenceFrame);
		setMapProjection(mapProjection);
	}

	/**
	 * Get the base name
	 * 
	 * @return base name
	 */
	public String getBaseName() {
		return baseName;
	}

	/**
	 * Set the base name
	 * 
	 * @param baseName
	 *            base name
	 */
	public void setBaseName(String baseName) {
		this.baseName = baseName;
	}

	/**
	 * Get the base type
	 * 
	 * @return base type
	 */
	public CoordinateReferenceSystemType getBaseType() {
		return baseType;
	}

	/**
	 * Set the base type
	 * 
	 * @param baseType
	 *            base type
	 */
	public void setBaseType(CoordinateReferenceSystemType baseType) {
		this.baseType = baseType;
	}

	/**
	 * Get the geodetic reference frame
	 * 
	 * @return geodetic reference frame
	 */
	public GeodeticReferenceFrame getGeodeticReferenceFrame() {
		return geodeticReferenceFrame;
	}

	/**
	 * Determine if has a geodetic reference frame
	 * 
	 * @return true if has geodetic reference frame
	 */
	public boolean hasGeodeticReferenceFrame() {
		return getGeodeticReferenceFrame() != null;
	}

	/**
	 * Set the geodetic reference frame
	 * 
	 * @param geodeticReferenceFrame
	 *            geodetic reference frame
	 */
	public void setGeodeticReferenceFrame(
			GeodeticReferenceFrame geodeticReferenceFrame) {
		this.geodeticReferenceFrame = geodeticReferenceFrame;
	}

	/**
	 * Get the geodetic datum ensemble
	 * 
	 * @return geodetic datum ensemble
	 */
	public GeodeticDatumEnsemble getGeodeticDatumEnsemble() {
		return geodeticDatumEnsemble;
	}

	/**
	 * Determine if has a geodetic datum ensemble
	 * 
	 * @return true if has geodetic datum ensemble
	 */
	public boolean hasGeodeticDatumEnsemble() {
		return getGeodeticDatumEnsemble() != null;
	}

	/**
	 * Set the geodetic datum ensemble
	 * 
	 * @param geodeticDatumEnsemble
	 *            geodetic datum ensemble
	 */
	public void setGeodeticDatumEnsemble(
			GeodeticDatumEnsemble geodeticDatumEnsemble) {
		this.geodeticDatumEnsemble = geodeticDatumEnsemble;
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
	 * Get the ellipsoidal angle unit
	 * 
	 * @return ellipsoidal angle unit
	 */
	public Unit getEllipsoidalAngleUnit() {
		return ellipsoidalAngleUnit;
	}

	/**
	 * Has an ellipsoidal angle unit
	 * 
	 * @return true if has ellipsoidal angle unit
	 */
	public boolean hasEllipsoidalAngleUnit() {
		return getEllipsoidalAngleUnit() != null;
	}

	/**
	 * Set the ellipsoidal angle unit
	 * 
	 * @param ellipsoidalAngleUnit
	 *            ellipsoidal angle unit
	 */
	public void setEllipsoidalAngleUnit(Unit ellipsoidalAngleUnit) {
		this.ellipsoidalAngleUnit = ellipsoidalAngleUnit;
	}

	/**
	 * Get the base identifiers
	 * 
	 * @return base identifiers
	 */
	public List<Identifier> getBaseIdentifiers() {
		return baseIdentifiers;
	}

	/**
	 * Has base identifiers
	 * 
	 * @return true if has base identifiers
	 */
	public boolean hasBaseIdentifiers() {
		return baseIdentifiers != null && !baseIdentifiers.isEmpty();
	}

	/**
	 * Set the base identifiers
	 * 
	 * @param baseIdentifiers
	 *            base identifiers
	 */
	public void setBaseIdentifiers(List<Identifier> baseIdentifiers) {
		this.baseIdentifiers = baseIdentifiers;
	}

	/**
	 * Add the base identifier
	 * 
	 * @param baseIdentifier
	 *            base identifier
	 */
	public void addBaseIdentifier(Identifier baseIdentifier) {
		if (this.baseIdentifiers == null) {
			this.baseIdentifiers = new ArrayList<>();
		}
		this.baseIdentifiers.add(baseIdentifier);
	}

	/**
	 * Add the base identifiers
	 * 
	 * @param baseIdentifiers
	 *            base identifiers
	 */
	public void addIdentifiers(List<Identifier> baseIdentifiers) {
		if (this.baseIdentifiers == null) {
			this.baseIdentifiers = new ArrayList<>();
		}
		this.baseIdentifiers.addAll(baseIdentifiers);
	}

	/**
	 * Get the map projection
	 * 
	 * @return map projection
	 */
	public MapProjection getMapProjection() {
		return mapProjection;
	}

	/**
	 * Set the map projection
	 * 
	 * @param mapProjection
	 *            map projection
	 */
	public void setMapProjection(MapProjection mapProjection) {
		this.mapProjection = mapProjection;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((baseIdentifiers == null) ? 0 : baseIdentifiers.hashCode());
		result = prime * result
				+ ((baseName == null) ? 0 : baseName.hashCode());
		result = prime * result
				+ ((baseType == null) ? 0 : baseType.hashCode());
		result = prime * result + ((dynamic == null) ? 0 : dynamic.hashCode());
		result = prime * result + ((ellipsoidalAngleUnit == null) ? 0
				: ellipsoidalAngleUnit.hashCode());
		result = prime * result + ((geodeticDatumEnsemble == null) ? 0
				: geodeticDatumEnsemble.hashCode());
		result = prime * result + ((geodeticReferenceFrame == null) ? 0
				: geodeticReferenceFrame.hashCode());
		result = prime * result
				+ ((mapProjection == null) ? 0 : mapProjection.hashCode());
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
		ProjectedCoordinateReferenceSystem other = (ProjectedCoordinateReferenceSystem) obj;
		if (baseIdentifiers == null) {
			if (other.baseIdentifiers != null)
				return false;
		} else if (!baseIdentifiers.equals(other.baseIdentifiers))
			return false;
		if (baseName == null) {
			if (other.baseName != null)
				return false;
		} else if (!baseName.equals(other.baseName))
			return false;
		if (baseType != other.baseType)
			return false;
		if (dynamic == null) {
			if (other.dynamic != null)
				return false;
		} else if (!dynamic.equals(other.dynamic))
			return false;
		if (ellipsoidalAngleUnit == null) {
			if (other.ellipsoidalAngleUnit != null)
				return false;
		} else if (!ellipsoidalAngleUnit.equals(other.ellipsoidalAngleUnit))
			return false;
		if (geodeticDatumEnsemble == null) {
			if (other.geodeticDatumEnsemble != null)
				return false;
		} else if (!geodeticDatumEnsemble.equals(other.geodeticDatumEnsemble))
			return false;
		if (geodeticReferenceFrame == null) {
			if (other.geodeticReferenceFrame != null)
				return false;
		} else if (!geodeticReferenceFrame.equals(other.geodeticReferenceFrame))
			return false;
		if (mapProjection == null) {
			if (other.mapProjection != null)
				return false;
		} else if (!mapProjection.equals(other.mapProjection))
			return false;
		return true;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		String value = null;
		CRSWriter writer = new CRSWriter();
		try {
			writer.write(this);
			value = writer.toString();
		} catch (IOException e) {
			logger.log(Level.WARNING,
					"Failed to projected coordinate reference system as a string",
					e);
			value = super.toString();
		} finally {
			writer.close();
		}
		return value;
	}

}
