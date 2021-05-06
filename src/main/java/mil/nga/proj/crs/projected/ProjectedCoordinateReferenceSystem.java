package mil.nga.proj.crs.projected;

import java.util.List;
import java.util.logging.Logger;

import mil.nga.proj.crs.CoordinateReferenceSystem;
import mil.nga.proj.crs.CoordinateReferenceSystemType;
import mil.nga.proj.crs.common.CoordinateSystem;
import mil.nga.proj.crs.common.Dynamic;
import mil.nga.proj.crs.common.Identifier;
import mil.nga.proj.crs.common.Unit;
import mil.nga.proj.crs.geodetic.GeodeticCoordinateReferenceSystem;
import mil.nga.proj.crs.geodetic.GeodeticDatumEnsemble;
import mil.nga.proj.crs.geodetic.GeodeticReferenceFrame;

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
	 * Base
	 */
	private GeodeticCoordinateReferenceSystem base = new GeodeticCoordinateReferenceSystem();

	/**
	 * Map Projection
	 */
	private MapProjection mapProjection = null;

	/**
	 * Constructor
	 */
	public ProjectedCoordinateReferenceSystem() {
		super(CoordinateReferenceSystemType.PROJECTED);
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
	 * Get the base coordinate reference system
	 * 
	 * @return base coordinate reference system
	 */
	public GeodeticCoordinateReferenceSystem getBase() {
		return base;
	}

	/**
	 * Set the base coordinate reference system
	 * 
	 * @param base
	 *            base coordinate reference system
	 */
	public void setBase(GeodeticCoordinateReferenceSystem base) {
		this.base = base;
	}

	/**
	 * Get the base name
	 * 
	 * @return base name
	 */
	public String getBaseName() {
		return getBase().getName();
	}

	/**
	 * Set the base name
	 * 
	 * @param baseName
	 *            base name
	 */
	public void setBaseName(String baseName) {
		getBase().setName(baseName);
	}

	/**
	 * Get the base type
	 * 
	 * @return base type
	 */
	public CoordinateReferenceSystemType getBaseType() {
		return getBase().getType();
	}

	/**
	 * Set the base type
	 * 
	 * @param baseType
	 *            base type
	 */
	public void setBaseType(CoordinateReferenceSystemType baseType) {
		getBase().setType(baseType);
	}

	/**
	 * Get the geodetic reference frame
	 * 
	 * @return geodetic reference frame
	 */
	public GeodeticReferenceFrame getGeodeticReferenceFrame() {
		return getBase().getGeodeticReferenceFrame();
	}

	/**
	 * Determine if has a geodetic reference frame
	 * 
	 * @return true if has geodetic reference frame
	 */
	public boolean hasGeodeticReferenceFrame() {
		return getBase().hasGeodeticReferenceFrame();
	}

	/**
	 * Set the geodetic reference frame
	 * 
	 * @param geodeticReferenceFrame
	 *            geodetic reference frame
	 */
	public void setGeodeticReferenceFrame(
			GeodeticReferenceFrame geodeticReferenceFrame) {
		getBase().setGeodeticReferenceFrame(geodeticReferenceFrame);
	}

	/**
	 * Get the geodetic datum ensemble
	 * 
	 * @return geodetic datum ensemble
	 */
	public GeodeticDatumEnsemble getGeodeticDatumEnsemble() {
		return getBase().getGeodeticDatumEnsemble();
	}

	/**
	 * Determine if has a geodetic datum ensemble
	 * 
	 * @return true if has geodetic datum ensemble
	 */
	public boolean hasGeodeticDatumEnsemble() {
		return getBase().hasGeodeticDatumEnsemble();
	}

	/**
	 * Set the geodetic datum ensemble
	 * 
	 * @param geodeticDatumEnsemble
	 *            geodetic datum ensemble
	 */
	public void setGeodeticDatumEnsemble(
			GeodeticDatumEnsemble geodeticDatumEnsemble) {
		getBase().setGeodeticDatumEnsemble(geodeticDatumEnsemble);
	}

	/**
	 * Get the dynamic coordinate reference system
	 * 
	 * @return dynamic coordinate reference system
	 */
	public Dynamic getDynamic() {
		return getBase().getDynamic();
	}

	/**
	 * Determine if has a dynamic
	 * 
	 * @return true if has dynamic
	 */
	public boolean hasDynamic() {
		return getBase().hasDynamic();
	}

	/**
	 * Set the dynamic coordinate reference system
	 * 
	 * @param dynamic
	 *            dynamic coordinate reference system
	 */
	public void setDynamic(Dynamic dynamic) {
		getBase().setDynamic(dynamic);
	}

	/**
	 * Get the base identifiers
	 * 
	 * @return base identifiers
	 */
	public List<Identifier> getBaseIdentifiers() {
		return getBase().getIdentifiers();
	}

	/**
	 * Has base identifiers
	 * 
	 * @return true if has base identifiers
	 */
	public boolean hasBaseIdentifiers() {
		return getBase().hasIdentifiers();
	}

	/**
	 * Set the base identifiers
	 * 
	 * @param baseIdentifiers
	 *            base identifiers
	 */
	public void setBaseIdentifiers(List<Identifier> baseIdentifiers) {
		getBase().setIdentifiers(baseIdentifiers);
	}

	/**
	 * Add the base identifier
	 * 
	 * @param baseIdentifier
	 *            base identifier
	 */
	public void addBaseIdentifier(Identifier baseIdentifier) {
		getBase().addIdentifier(baseIdentifier);
	}

	/**
	 * Add the base identifiers
	 * 
	 * @param baseIdentifiers
	 *            base identifiers
	 */
	public void addBaseIdentifiers(List<Identifier> baseIdentifiers) {
		getBase().addIdentifiers(baseIdentifiers);
	}

	/**
	 * Get the unit (ellipsoidal angle)
	 * 
	 * @return unit (ellipsoidal angle)
	 */
	public Unit getUnit() {
		return getBase().getCoordinateSystem().getUnit();
	}

	/**
	 * Has a unit (ellipsoidal angle)
	 * 
	 * @return true if has unit (ellipsoidal angle)
	 */
	public boolean hasUnit() {
		CoordinateSystem cs = getBase().getCoordinateSystem();
		return cs != null && cs.hasUnit();
	}

	/**
	 * Set the unit (angle)
	 * 
	 * @param unit
	 *            unit (ellipsoidal angle)
	 */
	public void setUnit(Unit unit) {
		CoordinateSystem cs = getBase().getCoordinateSystem();
		if (cs == null) {
			cs = new CoordinateSystem();
			getBase().setCoordinateSystem(cs);
		}
		cs.setUnit(unit);
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
		result = prime * result + ((base == null) ? 0 : base.hashCode());
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
		if (base == null) {
			if (other.base != null)
				return false;
		} else if (!base.equals(other.base))
			return false;
		if (mapProjection == null) {
			if (other.mapProjection != null)
				return false;
		} else if (!mapProjection.equals(other.mapProjection))
			return false;
		return true;
	}

}
