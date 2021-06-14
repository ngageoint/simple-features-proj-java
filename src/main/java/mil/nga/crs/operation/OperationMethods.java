package mil.nga.crs.operation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import mil.nga.crs.CRSException;

/**
 * Operation Methods
 * 
 * @author osbornb
 */
public enum OperationMethods {

	/**
	 * Albers Equal Area
	 */
	ALBERS_EQUAL_AREA(9822, "Albers Equal Area", OperationType.MAP_PROJECTION,
			"Albers", 8821, 8822, 8823, 8824, 8826, 8827),

	/**
	 * American Polyconic
	 */
	AMERICAN_POLYCONIC(9818, "American Polyconic", OperationType.MAP_PROJECTION,
			"Polyconic", 8801, 8802, 8806, 8807),

	/**
	 * Cassini-Soldner
	 */
	CASSINI_SOLDNER(9806, "Cassini-Soldner", OperationType.MAP_PROJECTION,
			"Cassini", 8801, 8802, 8806, 8807),

	/**
	 * Coordinate Frame rotation (geocentric domain)
	 */
	COORDINATE_FRAME_ROTATION(1032,
			"Coordinate Frame rotation (geocentric domain)",
			OperationType.COORDINATE,
			new String[] { "Coordinate Frame rotation", "Bursa-Wolf",
					"Helmert" },
			8605, 8606, 8607, 8608, 8609, 8610, 8611),

	/**
	 * Equidistant Cylindrical (Spherical)
	 */
	EQUIDISTANT_CYLINDRICAL(9823, "Equidistant Cylindrical (Spherical)",
			OperationType.MAP_PROJECTION, "Equidistant Cylindrical", 8801, 8802,
			8806, 8807),

	/**
	 * Geocentric translations (geocentric domain)
	 */
	GEOCENTRIC_TRANSLATIONS(1031, "Geocentric translations (geocentric domain)",
			OperationType.COORDINATE, "Geocentric translations", 8605, 8606,
			8607),

	/**
	 * Hotine Oblique Mercator (variant A)
	 */
	HOTLINE_OBLIQUE_MERCATOR_A(9812, "Hotine Oblique Mercator (variant A)",
			OperationType.MAP_PROJECTION, "Rectified skew orthomorphic", 8811,
			8812, 8813, 8814, 8815, 8806, 8807),

	/**
	 * Hotine Oblique Mercator (variant B)
	 */
	HOTLINE_OBLIQUE_MERCATOR_B(9815, "Hotine Oblique Mercator (variant B)",
			OperationType.MAP_PROJECTION, "Rectified skew orthomorphic", 8811,
			8812, 8813, 8814, 8815, 8816, 8817),

	/**
	 * Krovak
	 */
	KROVAK(9819, "Krovak", OperationType.MAP_PROJECTION, 8811, 8833, 1036, 8818,
			8819, 8806, 8807),

	/**
	 * Lambert Azimuthal Equal Area
	 */
	LAMBERT_AZIMUTHAL_EQUAL_AREA(9820, "Lambert Azimuthal Equal Area",
			OperationType.MAP_PROJECTION,
			new String[] { "Lambert Equal Area", "LAEA" }, 8801, 8802, 8806,
			8807),

	/**
	 * Lambert Conic Conformal (1SP)
	 */
	LAMBERT_CONIC_CONFORMAL_1SP(9801, "Lambert Conic Conformal (1SP)",
			OperationType.MAP_PROJECTION,
			new String[] { "Lambert Conic Conformal", "LCC",
					"Lambert Conformal Conic 1SP" },
			8801, 8802, 8805, 8806, 8807),

	/**
	 * Lambert Conic Conformal (2SP)
	 */
	LAMBERT_CONIC_CONFORMAL_2SP(9802, "Lambert Conic Conformal (2SP)",
			OperationType.MAP_PROJECTION,
			new String[] { "Lambert Conic Conformal", "LCC",
					"Lambert Conformal Conic 2SP" },
			8821, 8822, 8823, 8824, 8826, 8827),

	/**
	 * Lambert Cylindrical Equal Area (Spherical)
	 */
	LAMBERT_CYLINDRICAL_EQUAL_AREA(9834,
			"Lambert Cylindrical Equal Area (Spherical)",
			OperationType.MAP_PROJECTION, "Lambert Cylindrical Equal Area",
			8823, 8802, 8806, 8807),

	/**
	 * Longitude rotation
	 */
	LONGITUDE_ROTATION(9601, "Longitude rotation", OperationType.COORDINATE,
			8602),

	/**
	 * Mercator (variant A)
	 */
	MERCATOR_A(9804, "Mercator (variant A)", OperationType.MAP_PROJECTION,
			"Mercator", 8801, 8802, 8805, 8806, 8807),

	/**
	 * Mercator (variant B)
	 */
	MERCATOR_B(9805, "Mercator (variant B)", OperationType.MAP_PROJECTION,
			"Mercator", 8823, 8802, 8806, 8807),

	/**
	 * Molodensky-Badekas (geocentric domain)
	 */
	MOLODENSKY_BADEKAS(1034, "Molodensky-Badekas (geocentric domain)",
			OperationType.COORDINATE, "Molodensky-Badekas", 8605, 8606, 8607,
			8608, 8609, 8610, 8611, 8617, 8618, 8667),

	/**
	 * NADCON
	 */
	NADCON(9613, "NADCON", OperationType.COORDINATE, 8657, 8658),

	/**
	 * NADCON5 (3D)
	 */
	NADCON5(1075, "NADCON5 (3D)", OperationType.COORDINATE, "NADCON5", 8657,
			8658, 1058),

	/**
	 * New Zealand Map Grid
	 */
	NEW_ZEALAND_MAP_GRID(9811, "New Zealand Map Grid",
			OperationType.MAP_PROJECTION, 8801, 8802, 8806, 8807),

	/**
	 * NTv2
	 */
	NTV2(9615, "NTv2", OperationType.COORDINATE, 8656),

	/**
	 * Oblique stereographic
	 */
	OBLIQUE_STEREOGRAPHIC(9809, "Oblique stereographic",
			OperationType.MAP_PROJECTION, "Double stereographic", 8801, 8802,
			8805, 8806, 8807),

	/**
	 * Polar Stereographic (variant A)
	 */
	POLAR_STEREOGRAPHIC_A(9810, "Polar Stereographic (variant A)",
			OperationType.MAP_PROJECTION, "Polar Stereographic", 8801, 8802,
			8805, 8806, 8807),

	/**
	 * Polar Stereographic (variant B)
	 */
	POLAR_STEREOGRAPHIC_B(9829, "Polar Stereographic (variant B)",
			OperationType.MAP_PROJECTION, 8832, 8833, 8806, 8807),

	/**
	 * Polar Stereographic (variant C)
	 */
	POLAR_STEREOGRAPHIC_C(9830, "Polar Stereographic (variant C)",
			OperationType.MAP_PROJECTION, 8832, 8833, 8826, 8827),

	/**
	 * Popular Visualisation Pseudo Mercator
	 */
	POPULAR_VISUALISATION_PSEUDO_MERCATOR(1024,
			"Popular Visualisation Pseudo Mercator",
			OperationType.MAP_PROJECTION, "Mercator_1SP", 8801, 8802, 8806,
			8807),

	/**
	 * Position Vector transformation (geocentric domain)
	 */
	POSITION_VECTOR_TRANSFORMATION(1033,
			"Position Vector transformation (geocentric domain)",
			OperationType.COORDINATE,
			new String[] { "Position Vector transformation",
					"Position Vector 7-param. transformation", "Bursa-Wolf",
					"Helmert" },
			8605, 8606, 8607, 8608, 8609, 8610, 8611),

	/**
	 * Transverse Mercator
	 */
	TRANSVERSE_MERCATOR(9807, "Transverse Mercator",
			OperationType.MAP_PROJECTION,
			new String[] { "Gauss-Boaga", "Gauss-Krüger", "TM" }, 8801, 8802,
			8805, 8806, 8807),

	/**
	 * Transverse Mercator (South Orientated)
	 */
	TRANSVERSE_MERCATOR_SOUTH_ORIENTATED(9808,
			"Transverse Mercator (South Orientated)",
			OperationType.MAP_PROJECTION, "Gauss-Conform", 8801, 8802, 8805,
			8806, 8807),

	/**
	 * Vertical Offset
	 */
	VERTICAL_OFFSET(9616, "Vertical Offset", OperationType.COORDINATE, 8603);

	/**
	 * Alias to parameter methods
	 */
	private static final Map<String, Set<OperationMethods>> aliasMethods = new HashMap<>();

	/**
	 * Code to method mapping
	 */
	private static final Map<Integer, OperationMethods> codeMethods = new HashMap<>();

	static {
		for (OperationMethods method : values()) {

			for (String alias : method.aliases) {
				String aliasLowerCase = alias.toLowerCase();
				Set<OperationMethods> methodSet = aliasMethods
						.get(aliasLowerCase);
				if (methodSet == null) {
					methodSet = new HashSet<>();
					aliasMethods.put(aliasLowerCase, methodSet);
				}
				methodSet.add(method);
			}

			int code = method.getCode();
			if (codeMethods.containsKey(code)) {
				throw new CRSException(
						"Duplicate configured Operation Method code: " + code);
			}
			codeMethods.put(code, method);

		}
	}

	/**
	 * Parameter name
	 */
	private final String name;

	/**
	 * Operation type
	 */
	private final OperationType operationType;

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
	 * @param code
	 *            method code
	 * @param name
	 *            method name
	 * @param operationType
	 *            operation type
	 * @param parameterCodes
	 *            method parameter codes
	 */
	private OperationMethods(int code, String name, OperationType operationType,
			int... parameterCodes) {
		this(code, name, operationType, new String[] {}, parameterCodes);
	}

	/**
	 * Constructor
	 * 
	 * @param code
	 *            method code
	 * @param name
	 *            method name
	 * @param operationType
	 *            operation type
	 * @param alias
	 *            method alias
	 * @param parameterCodes
	 *            method parameter codes
	 */
	private OperationMethods(int code, String name, OperationType operationType,
			String alias, int... parameterCodes) {
		this(code, name, operationType, new String[] { alias }, parameterCodes);
	}

	/**
	 * Constructor
	 * 
	 * @param code
	 *            method code
	 * @param name
	 *            method name
	 * @param operationType
	 *            operation type
	 * @param aliases
	 *            method aliases
	 * @param parameterCodes
	 *            method parameter codes
	 */
	private OperationMethods(int code, String name, OperationType operationType,
			String[] aliases, int... parameterCodes) {
		this.code = code;
		this.name = name;
		this.operationType = operationType;
		addAlias(name);
		for (String alias : aliases) {
			addAlias(alias);
		}
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
		String underscore = alias.replaceAll(" ", "_");
		aliases.add(underscore);
		aliases.add(underscore.replace("(", "").replace(")", ""));
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
	 * Get the operation type
	 * 
	 * @return operation type
	 */
	public OperationType getOperationType() {
		return operationType;
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
	public OperationParameters getParameter(int index) {
		return OperationParameters.getParameter(getParameterCode(index));
	}

	/**
	 * Get the parameters
	 * 
	 * @return parameters
	 */
	public List<OperationParameters> getParameters() {
		List<OperationParameters> parameters = new ArrayList<>();
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
	public static OperationMethods getMethod(String name) {
		OperationMethods method = null;
		Set<OperationMethods> methods = getMethods(name);
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
	public static Set<OperationMethods> getMethods(String name) {
		Set<OperationMethods> methods = null;
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
	public static OperationMethods getMethod(int code) {
		return codeMethods.get(code);
	}

}
