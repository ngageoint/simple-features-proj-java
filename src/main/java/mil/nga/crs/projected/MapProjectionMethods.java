package mil.nga.crs.projected;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Map Projection Methods
 * 
 * @author osbornb
 */
public enum MapProjectionMethods {

	/**
	 * Albers Equal Area
	 */
	ALBERS_EQUAL_AREA("Albers Equal Area", "Albers", 9822, 8821, 8822, 8823,
			8824, 8826, 8827),

	/**
	 * American Polyconic
	 */
	AMERICAN_POLYCONIC("American Polyconic", "Polyconic", 9818, 8801, 8802,
			8806, 8807),

	/**
	 * Cassini-Soldner
	 */
	CASSINI_SOLDNER("Cassini-Soldner", "Cassini", 9806, 8801, 8802, 8806, 8807),

	/**
	 * Hotine Oblique Mercator (variant A)
	 */
	HOTLINE_OBLIQUE_MERCATOR_A("Hotine Oblique Mercator (variant A)",
			"Rectified skew orthomorphic", 9812, 8811, 8812, 8813, 8814, 8815,
			8806, 8807),

	/**
	 * Hotine Oblique Mercator (variant B)
	 */
	HOTLINE_OBLIQUE_MERCATOR_B("Hotine Oblique Mercator (variant B)",
			"Rectified skew orthomorphic", 9815, 8811, 8812, 8813, 8814, 8815,
			8816, 8817),

	/**
	 * Lambert Azimuthal Equal Area
	 */
	LAMBERT_AZIMUTHAL_EQUAL_AREA("Lambert Azimuthal Equal Area",
			new String[] { "Lambert Equal Area", "LAEA" }, 9820, 8801, 8802,
			8806, 8807),

	/**
	 * Lambert Conic Conformal (1SP)
	 */
	LAMBERT_CONIC_CONFORMAL_1SP("Lambert Conic Conformal (1SP)",
			new String[] { "Lambert Conic Conformal", "LCC" }, 9801, 8801, 8802,
			8805, 8806, 8807),

	/**
	 * Lambert Conic Conformal (2SP)
	 */
	LAMBERT_CONIC_CONFORMAL_2SP("Lambert Conic Conformal (2SP)",
			new String[] { "Lambert Conic Conformal", "LCC" }, 9802, 8821, 8822,
			8823, 8824, 8826, 8827),

	/**
	 * Mercator (variant A)
	 */
	MERCATOR_A("Mercator (variant A)", "Mercator", 9804, 8801, 8802, 8805, 8806,
			8807),

	/**
	 * Mercator (variant B)
	 */
	MERCATOR_B("Mercator (variant B)", "Mercator", 9805, 8823, 8802, 8806,
			8807),

	/**
	 * Oblique stereographic
	 */
	OBLIQUE_STEREOGRAPHIC("Oblique stereographic", "Double stereographic", 9809,
			8801, 8802, 8805, 8806, 8807),

	/**
	 * Polar Stereographic (variant A)
	 */
	POLAR_STEREOGRAPHIC_A("Polar Stereographic (variant A)", 9810, 8801, 8802,
			8805, 8806, 8807),

	/**
	 * Polar Stereographic (variant B)
	 */
	POLAR_STEREOGRAPHIC_B("Polar Stereographic (variant B)", 9829, 8832, 8833,
			8806, 8807),

	/**
	 * Polar Stereographic (variant C)
	 */
	POLAR_STEREOGRAPHIC_C("Polar Stereographic (variant C)", 9830, 8832, 8833,
			8826, 8827),

	/**
	 * Transverse Mercator
	 */
	TRANSVERSE_MERCATOR("Transverse Mercator",
			new String[] { "Gauss-Boaga", "Gauss-Krüger", "TM" }, 9807, 8801,
			8802, 8805, 8806, 8807),

	/**
	 * Transverse Mercator (South Orientated)
	 */
	TRANSVERSE_MERCATOR_SOUTH_ORIENTATED(
			"Transverse Mercator (South Orientated)", "Gauss-Conform", 9808,
			8801, 8802, 8805, 8806, 8807);

	/**
	 * Alias to parameter methods
	 */
	private static final Map<String, Set<MapProjectionMethods>> aliasMethods = new HashMap<>();

	/**
	 * Code to method mapping
	 */
	private static final Map<Integer, MapProjectionMethods> codeMethods = new HashMap<>();

	static {
		for (MapProjectionMethods method : values()) {

			for (String alias : method.aliases) {
				String aliasLowerCase = alias.toLowerCase();
				Set<MapProjectionMethods> methodSet = aliasMethods
						.get(aliasLowerCase);
				if (methodSet == null) {
					methodSet = new HashSet<>();
					aliasMethods.put(aliasLowerCase, methodSet);
				}
				methodSet.add(method);
			}

			codeMethods.put(method.getCode(), method);

		}
	}

	/**
	 * Parameter name
	 */
	private final String name;

	/**
	 * Aliases
	 */
	private final Set<String> aliases = new LinkedHashSet<>();

	/**
	 * Parameter code
	 */
	private final int code;

	/**
	 * Method parameter codes
	 */
	private final List<Integer> parameterCodes = new ArrayList<>();

	/**
	 * Constructor
	 * 
	 * @param name
	 *            method name
	 * @param code
	 *            method code
	 * @param parameterCodes
	 *            method parameter codes
	 */
	private MapProjectionMethods(String name, int code, int... parameterCodes) {
		this(name, new String[] {}, code, parameterCodes);
	}

	/**
	 * Constructor
	 * 
	 * @param name
	 *            method name
	 * @param alias
	 *            method alias
	 * @param code
	 *            method code
	 * @param parameterCodes
	 *            method parameter codes
	 */
	private MapProjectionMethods(String name, String alias, int code,
			int... parameterCodes) {
		this(name, new String[] { alias }, code, parameterCodes);
	}

	/**
	 * Constructor
	 * 
	 * @param name
	 *            method name
	 * @param aliases
	 *            method aliases
	 * @param code
	 *            method code
	 * @param parameterCodes
	 *            method parameter codes
	 */
	private MapProjectionMethods(String name, String[] aliases, int code,
			int... parameterCodes) {
		this.name = name;
		addAlias(name);
		for (String alias : aliases) {
			addAlias(alias);
		}
		this.code = code;
		for (int parameterCode : parameterCodes) {
			this.parameterCodes.add(parameterCode);
		}
	}

	/**
	 * Add an alias
	 * 
	 * @param alias
	 *            alias name
	 */
	private void addAlias(String alias) {
		aliases.add(alias);
		aliases.add(alias.replaceAll(" ", "_"));
	}

	/**
	 * Get the method name
	 * 
	 * @return name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Get the aliases
	 * 
	 * @return aliases
	 */
	public Set<String> getAliases() {
		return Collections.unmodifiableSet(aliases);
	}

	/**
	 * Get the method code
	 * 
	 * @return code
	 */
	public int getCode() {
		return code;
	}

	/**
	 * Get the parameter codes
	 * 
	 * @return parameter codes
	 */
	public List<Integer> getParameterCodes() {
		return Collections.unmodifiableList(parameterCodes);
	}

	/**
	 * Number of parameters
	 * 
	 * @return parameter count
	 */
	public int numParameters() {
		return parameterCodes.size();
	}

	/**
	 * Get the parameter code at the index
	 * 
	 * @param index
	 *            parameter index
	 * @return parameter code
	 */
	public int getParameterCode(int index) {
		return parameterCodes.get(index);
	}

	/**
	 * Get the parameter at the index
	 * 
	 * @param index
	 *            parameter index
	 * @return parameter
	 */
	public MapProjectionParameters getParameter(int index) {
		return MapProjectionParameters.getParameter(getParameterCode(index));
	}

	/**
	 * Get the parameters
	 * 
	 * @return parameters
	 */
	public List<MapProjectionParameters> getParameters() {
		List<MapProjectionParameters> parameters = new ArrayList<>();
		for (int i = 0; i < numParameters(); i++) {
			parameters.add(getParameter(i));
		}
		return parameters;
	}

	/**
	 * Get the method type from the alias
	 * 
	 * @param name
	 *            name or alias
	 * @return method type
	 */
	public static MapProjectionMethods getMethod(String name) {
		MapProjectionMethods method = null;
		Set<MapProjectionMethods> methods = getMethods(name);
		if (methods != null && !methods.isEmpty()) {
			method = methods.iterator().next();
		}
		return method;
	}

	/**
	 * Get the method types from the alias
	 * 
	 * @param name
	 *            name or alias
	 * @return method types
	 */
	public static Set<MapProjectionMethods> getMethods(String name) {
		Set<MapProjectionMethods> methods = null;
		if (name != null) {
			methods = aliasMethods.get(name.toLowerCase());
		}
		return methods;
	}

	/**
	 * Get the method type from the code
	 * 
	 * @param code
	 *            method code
	 * @return method type
	 */
	public static MapProjectionMethods getMethod(int code) {
		return codeMethods.get(code);
	}

}
