package mil.nga.proj.crs.projected;

import java.util.List;

import mil.nga.proj.crs.CRSType;
import mil.nga.proj.crs.SimpleCoordinateReferenceSystem;
import mil.nga.proj.crs.common.CoordinateSystem;
import mil.nga.proj.crs.common.Dynamic;
import mil.nga.proj.crs.common.Identifier;
import mil.nga.proj.crs.common.Unit;
import mil.nga.proj.crs.geo.GeoCoordinateReferenceSystem;
import mil.nga.proj.crs.geo.GeoDatumEnsemble;
import mil.nga.proj.crs.geo.GeoReferenceFrame;

/**
 * Projected Coordinate Reference System
 * 
 * @author osbornb
 */
public class ProjectedCoordinateReferenceSystem
		extends SimpleCoordinateReferenceSystem {

	/**
	 * Base
	 */
	private GeoCoordinateReferenceSystem base = new GeoCoordinateReferenceSystem();

	/**
	 * Map Projection
	 */
	private MapProjection mapProjection = null;

	/**
	 * Constructor
	 */
	public ProjectedCoordinateReferenceSystem() {
		super(CRSType.PROJECTED);
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
	 * @param referenceFrame
	 *            reference frame
	 * @param mapProjection
	 *            map projection
	 * @param coordinateSystem
	 *            coordinate system
	 */
	public ProjectedCoordinateReferenceSystem(String name, String baseName,
			CRSType baseType, GeoReferenceFrame referenceFrame,
			MapProjection mapProjection, CoordinateSystem coordinateSystem) {
		super(name, CRSType.PROJECTED, coordinateSystem);
		setBaseName(baseName);
		setBaseType(baseType);
		setReferenceFrame(referenceFrame);
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
	 * @param datumEnsemble
	 *            datum ensemble
	 * @param mapProjection
	 *            map projection
	 * @param coordinateSystem
	 *            coordinate system
	 */
	public ProjectedCoordinateReferenceSystem(String name, String baseName,
			CRSType baseType, GeoDatumEnsemble datumEnsemble,
			MapProjection mapProjection, CoordinateSystem coordinateSystem) {
		super(name, CRSType.PROJECTED, coordinateSystem);
		setBaseName(baseName);
		setBaseType(baseType);
		setDatumEnsemble(datumEnsemble);
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
	 * @param referenceFrame
	 *            reference frame
	 * @param mapProjection
	 *            map projection
	 * @param coordinateSystem
	 *            coordinate system
	 */
	public ProjectedCoordinateReferenceSystem(String name, String baseName,
			CRSType baseType, Dynamic dynamic, GeoReferenceFrame referenceFrame,
			MapProjection mapProjection, CoordinateSystem coordinateSystem) {
		super(name, CRSType.PROJECTED, coordinateSystem);
		setBaseName(baseName);
		setBaseType(baseType);
		setDynamic(dynamic);
		setReferenceFrame(referenceFrame);
		setMapProjection(mapProjection);
	}

	/**
	 * Get the base coordinate reference system
	 * 
	 * @return base coordinate reference system
	 */
	public GeoCoordinateReferenceSystem getBase() {
		return base;
	}

	/**
	 * Set the base coordinate reference system
	 * 
	 * @param base
	 *            base coordinate reference system
	 */
	public void setBase(GeoCoordinateReferenceSystem base) {
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
	public CRSType getBaseType() {
		return getBase().getType();
	}

	/**
	 * Set the base type
	 * 
	 * @param baseType
	 *            base type
	 */
	public void setBaseType(CRSType baseType) {
		getBase().setType(baseType);
	}

	/**
	 * Get the reference frame
	 * 
	 * @return reference frame
	 */
	public GeoReferenceFrame getReferenceFrame() {
		return getBase().getReferenceFrame();
	}

	/**
	 * Determine if has a reference frame
	 * 
	 * @return true if has reference frame
	 */
	public boolean hasReferenceFrame() {
		return getBase().hasReferenceFrame();
	}

	/**
	 * Set the reference frame
	 * 
	 * @param referenceFrame
	 *            reference frame
	 */
	public void setReferenceFrame(GeoReferenceFrame referenceFrame) {
		getBase().setReferenceFrame(referenceFrame);
	}

	/**
	 * Get the datum ensemble
	 * 
	 * @return datum ensemble
	 */
	public GeoDatumEnsemble getDatumEnsemble() {
		return getBase().getDatumEnsemble();
	}

	/**
	 * Determine if has a datum ensemble
	 * 
	 * @return true if has datum ensemble
	 */
	public boolean hasDatumEnsemble() {
		return getBase().hasDatumEnsemble();
	}

	/**
	 * Set the datum ensemble
	 * 
	 * @param datumEnsemble
	 *            datum ensemble
	 */
	public void setDatumEnsemble(GeoDatumEnsemble datumEnsemble) {
		getBase().setDatumEnsemble(datumEnsemble);
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
	 * Number of base identifiers
	 * 
	 * @return base identifiers count
	 */
	public int numBaseIdentifiers() {
		return getBase().numIdentifiers();
	}

	/**
	 * Get the base identifier at the index
	 * 
	 * @param index
	 *            base identifier index
	 * @return base identifier
	 */
	public Identifier getBaseIdentifier(int index) {
		return getBase().getIdentifier(index);
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
