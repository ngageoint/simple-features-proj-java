package mil.nga.proj.crs;

/**
 * Well-known text representation of coordinate reference systems type
 * 
 * @author osbornb
 */
public enum CRSType {

	/**
	 * Bound
	 */
	BOUND(CategoryType.CRS),

	/**
	 * Compound
	 */
	COMPOUND(CategoryType.CRS),

	/**
	 * Concatenated Operation
	 */
	CONCATENATED_OPERATION(CategoryType.OPERATION),

	/**
	 * Coordinate Metadata
	 */
	COORDINATE_METADATA(CategoryType.METADATA),

	/**
	 * Coordinate Operation
	 */
	COORDINATE_OPERATION(CategoryType.OPERATION),

	/**
	 * Engineering
	 */
	ENGINEERING(CategoryType.CRS),

	/**
	 * Derived
	 */
	DERIVED(CategoryType.CRS),

	/**
	 * Geodetic
	 */
	GEODETIC(CategoryType.CRS),

	/**
	 * Geographic
	 */
	GEOGRAPHIC(CategoryType.CRS),

	/**
	 * Parametric
	 */
	PARAMETRIC(CategoryType.CRS),

	/**
	 * Point Motion Operation
	 */
	POINT_MOTION_OPERATION(CategoryType.OPERATION),

	/**
	 * Projected
	 */
	PROJECTED(CategoryType.CRS),

	/**
	 * Temporal
	 */
	TEMPORAL(CategoryType.CRS),

	/**
	 * Vertical
	 */
	VERTICAL(CategoryType.CRS);

	/**
	 * Category Type
	 */
	private final CategoryType categoryType;

	/**
	 * Constructor
	 * 
	 * @param categoryType
	 *            category type
	 */
	private CRSType(CategoryType categoryType) {
		this.categoryType = categoryType;
	}

	/**
	 * Get the category type
	 * 
	 * @return category type
	 */
	public CategoryType getCategoryType() {
		return categoryType;
	}

}
