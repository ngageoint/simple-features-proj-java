package mil.nga.proj.crs.common;

import java.util.HashMap;
import java.util.Map;

/**
 * Axis Direction Type
 * 
 * @author osbornb
 */
public enum AxisDirectionType {

	/**
	 * aft
	 */
	AFT("aft"),

	/**
	 * awayFrom
	 */
	AWAY_FROM("awayFrom"),

	/**
	 * clockwise
	 */
	CLOCKWISE("clockwise"),

	/**
	 * columnNegative
	 */
	COLUMN_NEGATIVE("columnNegative"),

	/**
	 * columnPositive
	 */
	COLUMN_POSITIVE("columnPositive"),

	/**
	 * counterClockwise
	 */
	COUNTER_CLOCKWISE("counterClockwise"),

	/**
	 * displayDown
	 */
	DISPLAY_DOWN("displayDown"),

	/**
	 * displayLeft
	 */
	DISPLAY_LEFT("displayLeft"),

	/**
	 * displayRight
	 */
	DISPLAY_RIGHT("displayRight"),

	/**
	 * displayUp
	 */
	DISPLAY_UP("displayUp"),

	/**
	 * down
	 */
	DOWN("down"),

	/**
	 * east
	 */
	EAST("east"),

	/**
	 * eastNorthEast
	 */
	EAST_NORTH_EAST("eastNorthEast"),

	/**
	 * eastSouthEast
	 */
	EAST_SOUTH_EAST("eastSouthEast"),

	/**
	 * forward
	 */
	FORWARD("forward"),

	/**
	 * future
	 */
	FUTURE("future"),

	/**
	 * geocentricX
	 */
	GEOCENTRIC_X("geocentricX"),

	/**
	 * geocentricY
	 */
	GEOCENTRIC_Y("geocentricY"),

	/**
	 * geocentricZ
	 */
	GEOCENTRIC_Z("geocentricZ"),

	/**
	 * north
	 */
	NORTH("north"),

	/**
	 * northEast
	 */
	NORTH_EAST("northEast"),

	/**
	 * northNorthEast
	 */
	NORTH_NORTH_EAST("northNorthEast"),

	/**
	 * northNorthWest
	 */
	NORTH_NORTH_WEST("northNorthWest"),

	/**
	 * northWest
	 */
	NORTH_WEST("northWest"),

	/**
	 * past
	 */
	PAST("past"),

	/**
	 * port
	 */
	PORT("port"),

	/**
	 * rowNegative
	 */
	ROW_NEGATIVE("rowNegative"),

	/**
	 * rowPositive
	 */
	ROW_POSITIVE("rowPositive"),

	/**
	 * south
	 */
	SOUTH("south"),

	/**
	 * southEast
	 */
	SOUTH_EAST("southEast"),

	/**
	 * southSouthEast
	 */
	SOUTH_SOUTH_EAST("southSouthEast"),

	/**
	 * southSouthWest
	 */
	SOUTH_SOUTH_WEST("southSouthWest"),

	/**
	 * southWest
	 */
	SOUTH_WEST("southWest"),

	/**
	 * starboard
	 */
	STARBOARD("starboard"),

	/**
	 * towards
	 */
	TOWARDS("towards"),

	/**
	 * unspecified
	 */
	UNSPECIFIED("unspecified"),

	/**
	 * up
	 */
	UP("up"),

	/**
	 * west
	 */
	WEST("west"),

	/**
	 * westNorthWest
	 */
	WEST_NORTH_WEST("westNorthWest"),

	/**
	 * westSouthWest
	 */
	WEST_SOUTH_WEST("westSouthWest");

	/**
	 * Name to type mapping
	 */
	private static final Map<String, AxisDirectionType> nameTypes = new HashMap<>();
	static {
		for (AxisDirectionType type : values()) {
			nameTypes.put(type.getName().toUpperCase(), type);
		}
	}

	/**
	 * Type name
	 */
	private final String name;

	/**
	 * Constructor
	 * 
	 * @param name
	 *            type name
	 */
	private AxisDirectionType(String name) {
		this.name = name;
	}

	/**
	 * Get the type name
	 * 
	 * @return type name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Get the type from the name
	 * 
	 * @param name
	 *            type name
	 * @return type
	 */
	public static AxisDirectionType getType(String name) {
		AxisDirectionType type = null;
		if (name != null) {
			type = nameTypes.get(name.toUpperCase());
		}
		return type;
	}

}
