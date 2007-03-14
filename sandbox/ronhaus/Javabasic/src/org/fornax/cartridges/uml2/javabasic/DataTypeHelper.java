/**
 *
 */
package org.fornax.cartridges.uml2.javabasic;

import java.util.Iterator;

import org.eclipse.emf.common.util.BasicEMap;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.EMap;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.Parameter;
import org.eclipse.uml2.uml.Stereotype;
import org.eclipse.uml2.uml.Type;

/**
 * @author Thorsten Kamann <thorsten.kamann@googlemail.com> changed by Ron Haus
 *         (removed DefaultValues)
 */
public class DataTypeHelper {
	private static EMap ptd = null;

	private static EMap td = null;

	public static String getDataTypeName(Type type, String spec) {
		return spec + ":" + type.getName();
	}

	/**
	 * Calculates the type to return
	 * 
	 * @param parameter
	 *            The parameter modelled as return type
	 * @return The name of the type to return or <code>null</code> if no type
	 *         is defined as return type
	 */
	public static String getReturnType(Parameter parameter) {
		String rt = "";

		if (parameter == null) {
			rt = "void";
		} else {
			rt = parameter.getType().getName();
			if (rt.equals("Boolean") && (parameter.getType() instanceof org.eclipse.uml2.uml.PrimitiveType))
				rt = "boolean";

		}
		return rt;
	}

	public static Object getTaggedValue(Classifier classifier, String stereoTypeName, String name, String type) {
		Object tv = null;
		EList stereotypes = null;

		stereotypes = classifier.getAppliedStereotypes();
		for (Iterator iter = stereotypes.iterator(); iter.hasNext();) {
			Stereotype stereotype = (Stereotype) iter.next();
			if (stereotype.getName().equalsIgnoreCase(stereoTypeName)) {
				try {
					tv = classifier.getValue(stereotype, name);
				} catch (Exception ex) {
					tv = getTypeDefaults().get(type);
				}
				break;
			}
		}
		return tv;
	}

	/**
	 * @param type
	 *            The type the default value to calculate for
	 * @return The default value of the given type
	 */
	public static String getDefaultValue(Type type) {
		String value = "null";

		if (getPrimitiveTypeDefaults().containsKey(type.getName().toLowerCase())) {
			value = (String) getPrimitiveTypeDefaults().get(type.getName().toLowerCase());
		}
		return value;
	}

	/**
	 * Creates the <code>EMap</code> with the defaults for all PrimitiveTypes.
	 * 
	 * @return The <code>EMap</code> with the defaults
	 */
	private static EMap getPrimitiveTypeDefaults() {
		if (ptd == null) {
			ptd = new BasicEMap();
			ptd.put("int", "0");
			ptd.put("short", "0");
			ptd.put("double", "0");
			ptd.put("long", "0");
			ptd.put("boolean", "false");
			ptd.put("byte", new Byte("0").byteValue() + "");
			ptd.put("char", new String("' '"));
		}
		return ptd;
	}

	private static EMap getTypeDefaults() {
		if (td == null) {
			td = new BasicEMap();
			td.put("int", new Integer(0));
			td.put("short", new Short("0"));
			td.put("double", new Double(0d));
			td.put("long", new Long(0l));
			td.put("boolean", new Boolean(false));
			td.put("byte", new Byte("0"));
			td.put("char", new Character(' '));
		}
		return td;
	}
}
