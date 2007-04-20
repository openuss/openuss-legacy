/**
 *
 */
package org.openuss.cartridges.uml2.javabasic.extensions;

import java.util.Iterator;

import org.eclipse.emf.common.util.BasicEMap;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.EMap;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.Parameter;
import org.eclipse.uml2.uml.Property;
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
			rt = getTypeName(parameter.getType());
			if (parameter.isMultivalued() && parameter.isOrdered()) {
				rt = "java.util.List<" + rt + ">";
			}
			if (parameter.isMultivalued() && !parameter.isOrdered()) {
				rt = "java.util.Set<" + rt + ">";
			}
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

		if (getPrimitiveTypeDefaults().containsKey(type.getName())) {
			value = (String) getPrimitiveTypeDefaults().get(type.getName());
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

	public static String getJavaType(Property p) {
		String jt = getTypeName(p.getType());

		if (p.isMultivalued() && p.isOrdered()) {
			jt = "java.util.List<" + jt + ">";
		}
		if (p.isMultivalued() && !p.isOrdered()) {
			jt = "java.util.Set<" + jt + ">";
		}

		return jt;
	}

	public static String getJavaType(Parameter p) {
		String jt = getTypeName(p.getType());

		if (p.isMultivalued() && p.isOrdered()) {
			jt = "java.util.List<" + jt + ">";
		}
		if (p.isMultivalued() && !p.isOrdered()) {
			jt = "java.util.Set<" + jt + ">";
		}

		return jt;
	}

	public static String getTypeName(Type t) {
		String tn = "";

		if (t != null) {
			tn = Helper.getFQNPackageName(t)+"."+t.getName();

			if (t.getName().equals("void"))
				tn = "void";
			if (t.getName().equals("byte"))
				tn = "byte";
			if (t.getName().equals("short"))
				tn = "short";
			if (t.getName().equals("int"))
				tn = "int";
			if (t.getName().equals("long"))
				tn = "long";
			if (t.getName().equals("float"))
				tn = "float";
			if (t.getName().equals("double"))
				tn = "double";
			if (t.getName().equals("char"))
				tn = "char";
			if (t.getName().equals("boolean"))
				tn = "boolean";
			if (t.getName().equals("Byte"))
				tn = "java.lang.Byte";
			if (t.getName().equals("Short"))
				tn = "java.lang.Short";
			if (t.getName().equals("Integer"))
				tn = "java.lang.Integer";
			if (t.getName().equals("Long"))
				tn = "java.lang.Long";
			if (t.getName().equals("Float"))
				tn = "java.lang.Float";
			if (t.getName().equals("Double"))
				tn = "java.lang.Double";
			if (t.getName().equals("Character"))
				tn = "java.lang.Character";
			if (t.getName().equals("String"))
				tn = "java.lang.String";
			if (t.getName().equals("Boolean"))
				tn = "java.lang.Boolean";
			if (t.getName().equals("Date"))
				tn = "java.util.Date";
			if (t.getName().equals("Time"))
				tn = "java.sql.Time";
			if (t.getName().equals("Timestamp"))
				tn = "java.sql.Timestamp";
		}

		return tn;
	}

}
