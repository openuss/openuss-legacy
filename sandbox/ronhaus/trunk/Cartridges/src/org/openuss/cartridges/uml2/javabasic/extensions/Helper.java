/**
 *
 */
package org.openuss.cartridges.uml2.javabasic.extensions;

import java.util.Iterator;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.UniqueEList;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.Comment;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Generalization;
import org.eclipse.uml2.uml.Interface;
import org.eclipse.uml2.uml.InterfaceRealization;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.Parameter;
import org.eclipse.uml2.uml.PrimitiveType;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Type;
import org.eclipse.uml2.uml.ValueSpecification;

/**
 * @author Thorsten Kamann <thorsten.kamann@googlemail.com> until release 1.2.0
 * 
 * @author Ron Haus <ronhaus@arcor.de> since release 1.2.1
 * 
 * Helper class for the helper extension of the JavaBasic-Generator.
 */
public class Helper {

	/**
	 * Calculate the full qualified packagename of the given type
	 * 
	 * @param type
	 *            The <code>org.eclipse.uml2.uml.Type</code> to calculate the full qualified name for
	 * @return The full qualified name as <code>java.lang.String</code>
	 */
	public static String getFQNPackageName(Type type) {
		String pn = "";
		Package p = type.getPackage();

		while (p != null) {
			pn = p.getName() + "." + pn;
			try {
				p = (Package) p.getOwner();
				if (p instanceof Model) {
					p = null;
				}
			} catch (Exception ex) {
				p = null;
			}
		}

		if (pn.endsWith(".")) {
			pn = pn.substring(0, pn.length() - 1);
		}
		return pn;
	}

	/**
	 * Calculate the full qualified packagepath of the given type
	 * 
	 * @param type
	 *            The <code>org.eclipse.uml2.uml.Type</code> to calculate the full qualified path for
	 * @return The full qualified path as <code>java.lang.String</code>
	 */
	public static String getFQNPackagePath(Type type) {
		String path = getFQNPackageName(type);

		path = path.replaceAll("\\.", "/");
		return path;
	}

	/**
	 * Collects all attributes the classifier and their generalizations have.
	 * 
	 * @param classifier
	 *            The classifier the attributes should be collected
	 * @return An <code>EList</code> with all collected attributes
	 */
	public static EList getAllAttributesInherited(Classifier classifier) {
		EList attributes = new UniqueEList();
		attributes.addAll(classifier.getAllAttributes());

		for (int i = 0; i < classifier.getGeneralizations().size(); i++) {
			attributes
					.addAll(((Generalization) classifier.getGeneralizations().get(i)).getGeneral().getAllAttributes());
		}

		return attributes;
	}

	/**
	 * Collects all attributes the classifier and their interfaces have.
	 * 
	 * @param classifier
	 *            The classifier the attributes should be collected
	 * @return An <code>EList</code> with all collected attributes
	 */
	public static EList getAllAttributes(Classifier classifier) {
		EList attributes = new UniqueEList();

		attributes.addAll(classifier.getAttributes());
		if (classifier instanceof org.eclipse.uml2.uml.Class) {
			Class clazz = (Class) classifier;
			for (int i = 0; i < clazz.getImplementedInterfaces().size(); i++) {
				attributes.addAll(getAllInterfaceAttributes((Interface) clazz.getImplementedInterfaces().get(i)));
			}
		}
		return attributes;
	}

	/**
	 * Collects all dependencies the classifier and their interfaces have.
	 * 
	 * @param classifier
	 *            The classifier the dependencies should be collected
	 * @return An <code>EList</code> with all collected dependencies
	 */
	public static EList getAllDependencies(Classifier classifier) {
		EList dependencies = new UniqueEList();

		dependencies.addAll(classifier.getClientDependencies());
		if (classifier instanceof org.eclipse.uml2.uml.Class) {
			Class clazz = (Class) classifier;
			for (int i = 0; i < clazz.getImplementedInterfaces().size(); i++) {
				dependencies.addAll(getAllInterfaceAssociations((Interface) clazz.getImplementedInterfaces().get(i)));
			}
		}

		EList depsFiltered = new UniqueEList();
		for (Iterator iter = dependencies.iterator(); iter.hasNext();) {
			Object element = (Object) iter.next();
			if (!(element instanceof InterfaceRealization)) {
				depsFiltered.add(element);
			}
		}
		return depsFiltered;
	}

	/**
	 * Collects all operations the classifier and their interfaces have.
	 * 
	 * @param classifier
	 *            The classifier the operations should be collected
	 * @return An <code>EList</code> with all collected operations
	 */
	public static EList getAllOperations(Classifier classifier) {
		EList operations = new UniqueEList();

		operations.addAll(classifier.getOperations());
		if (classifier instanceof org.eclipse.uml2.uml.Class) {
			Class clazz = (Class) classifier;
			for (int i = 0; i < clazz.getImplementedInterfaces().size(); i++) {
				operations.addAll(getAllInterfaceOperations((Interface) clazz.getImplementedInterfaces().get(i)));
			}
		}
		return operations;
	}

	/**
	 * Collects all associations the classifier and their interfaces have.
	 * 
	 * @param classifier
	 *            The classifier the associations should be collected
	 * @return An <code>EList</code> with all collected associations
	 */
	public static EList getAllAssociations(Classifier classifier) {
		EList associations = new UniqueEList();

		associations.addAll(classifier.getAssociations());
		if (classifier instanceof org.eclipse.uml2.uml.Class) {
			Class clazz = (Class) classifier;
			for (int i = 0; i < clazz.getImplementedInterfaces().size(); i++) {
				associations.addAll(getAllInterfaceAssociations((Interface) clazz.getImplementedInterfaces().get(i)));
			}
		}
		return associations;
	}

	/**
	 * Collects all attributes of the given interface needed to implement (creating fields and getter/setter methods) by
	 * a implementing class.
	 * 
	 * @param iFace
	 *            The interface to collect the operatiosn from
	 * @return An <code>EList</code> with all collected attributes
	 */
	private static EList getAllInterfaceAttributes(Interface iFace) {
		EList attributes = new UniqueEList();

		attributes.addAll(iFace.getAllAttributes());
		if (iFace.getGeneralizations().size() > 0) {
			for (int i = 0; i < iFace.getGeneralizations().size(); i++) {
				attributes.addAll(getAllInterfaceAttributes((Interface) ((Generalization) iFace.getGeneralizations()
						.get(i)).getGeneral()));
			}
		}
		return attributes;
	}

	/**
	 * Collects all operation of the given interface needed to implement by a implementing class.
	 * 
	 * @param iFace
	 *            The interface to collect the operations from
	 * @return An <code>EList</code> with all collected operations
	 */
	private static EList getAllInterfaceOperations(Interface iFace) {
		EList operations = new UniqueEList();

		operations.addAll(iFace.getAllOperations());
		if (iFace.getGeneralizations().size() > 0) {
			for (int i = 0; i < iFace.getGeneralizations().size(); i++) {
				operations.addAll(getAllInterfaceOperations((Interface) ((Generalization) iFace.getGeneralizations()
						.get(i)).getGeneral()));
			}
		}
		return operations;
	}

	/**
	 * Collects all associations of the given interface needed to implement by a implementing class.
	 * 
	 * @param iFace
	 *            The interface to collect the associations from
	 * @return An <code>EList</code> with all collected associations
	 */
	private static EList getAllInterfaceAssociations(Interface iFace) {
		EList associations = new UniqueEList();

		associations.addAll(iFace.getAssociations());
		if (iFace.getGeneralizations().size() > 0) {
			for (int i = 0; i < iFace.getGeneralizations().size(); i++) {
				associations.addAll(getAllInterfaceAssociations((Interface) ((Generalization) iFace
						.getGeneralizations().get(i)).getGeneral()));
			}
		}
		return associations;
	}

	/**
	 * Collects all dependencies of the given interface needed to implement by a implementing class.
	 * 
	 * @param iFace
	 *            The interface to collect the dependencies from
	 * @return An <code>EList</code> with all collected dependencies
	 */
	private static EList getAllInterfaceDependencies(Interface iFace) {
		EList dependencies = new UniqueEList();

		dependencies.addAll(iFace.getClientDependencies());
		if (iFace.getGeneralizations().size() > 0) {
			for (int i = 0; i < iFace.getGeneralizations().size(); i++) {
				dependencies.addAll(getAllInterfaceAssociations((Interface) ((Generalization) iFace
						.getGeneralizations().get(i)).getGeneral()));
			}
		}
		return dependencies;
	}

	public static String formatComment(Element element, String prefix) {
		String comment = "";

		comment = getFormatedComment(element.getOwnedComments(), prefix, null);
		return comment;
	}

	public static String formatParameterComment(Parameter parameter, String prefix, String annotation) {
		String comment = "";

		if (parameter.getOwnedComments().isEmpty()) {
			comment = prefix + "@" + annotation + " " + parameter.getName();
		} else {
			comment = getFormatedComment(parameter.getOwnedComments(), prefix, annotation + " " + parameter.getName());
		}
		return comment;
	}

	public static String getDefaultValue(Property property) {
		ValueSpecification vs = property.getDefaultValue();

		if (vs == null) {
			if (property.getType() instanceof PrimitiveType) {
				PrimitiveType pt = (PrimitiveType) property.getType();
				if (pt.getName().equals("int")) {
					return "0";
				}
			}
			return "null";
		} else {
			if (vs.getType() == null) {
				return vs.stringValue();
			} else if (vs.getType().getName().equals("String")) {
				return vs.stringValue();
			} else if (vs.getType().getName().equals("Integer")) {
				return vs.integerValue() + "";
			} else if (vs.getType().getName().equals("Boolean")) {
				return vs.booleanValue() + "";
			}
			return vs.stringValue();
		}
	}

	private static String getFormatedComment(EList comments, String prefix, String annotation) {
		String s = "";

		if (!comments.isEmpty()) {
			for (int i = 0; i < comments.size(); i++) {
				if (!"".equals(s)) {
					s += "\n";
				}
				Comment c = (Comment) comments.get(i);
				String cs = c.getBody();
				cs = cs.replaceAll("\\n", "\n" + prefix);
				s += prefix;
				if (annotation != null && !"".equals(annotation)) {
					s += "@" + annotation;
				}
				s += " " + cs;
			}
		} else {
			s = "* ";
		}
		return s;
	}
}
