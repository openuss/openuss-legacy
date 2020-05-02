package org.openuss.framework.jsfcontrols.tags;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

/**
 * Functions to aid developing JSF applications.
 * 
 * @author Arne Sutor
 * @author Ingo Düppe
 */
public final class JsfFunctions {

	private static final String HINT_SUFFIX = "_hint";
	
	private static final Logger logger = Logger.getLogger(JsfFunctions.class);
	
	public static String getValue(final Object entity, final String property) {
		if ((entity instanceof String) && property != null) {
			return "#{"+(String)entity+"["+property+"]}"; 
		}
		return "";
	}
	
	/**
	 * 
	 * @param value
	 * @return
	 */
	public static Object toVB(final String str1, final String str2) {
		String expression = "#{"+str1+str2+"}";
		FacesContext facesContext = FacesContext.getCurrentInstance();
		ValueBinding vb = facesContext.getApplication().createValueBinding(expression);
		Object value = vb.getValue(facesContext);
		if (logger.isTraceEnabled()) {
			logger.trace("evaluated valuebinding "+expression+" value "+value);
		}
		return value;
	}
	

	/**
	 * Get localized hint string by key.
	 * the key is defined as [&lt;namespace&gt;_]&lt;entity&gt;_&lt;property&gt;_hint
	 * @param entity
	 * @param property
	 * @param namespace
	 * @return
	 */
	public static String getInputHint(final String entity, final String property, final String namespace) {
		return getStringByKey(entity,property,namespace,HINT_SUFFIX);
	}

	/**
	 * Get localized hint string by key.
	 * the key is defined as [&lt;namespace&gt;_]&lt;entity&gt;_&lt;property&gt;
	 * @param entity
	 * @param property
	 * @param namespace
	 * @return
	 */
	public static String getInputLabel(final String entity, final String property, final String namespace) {
		return getStringByKey(entity,property,namespace,"");
	}
	

	/**
	 * Get localized string by Key 
	 * @param entity
	 * @param property
	 * @param namespace
	 * @return localized string for a hint of a input field
	 */
	public static String getStringByKey(final String entity, final String property, final String namespace, final String suffix) {
		try {
			// generate message resource key
			String key = entity + "_" + property + suffix;
			if (StringUtils.isNotBlank(namespace))
				key = namespace + "_" + key;
			key = key.replace(".", "_").toLowerCase(Locale.ENGLISH);

			return i18n(key);
				
		} catch (NullPointerException e) {
			logger.error("Couldn't find resource bundle!",e);
			return "Couldn't find resource bundle!";
			
		} catch (MissingResourceException e) {
			logger.error("Couldn't find resource bundle!",e);
			return "Couldn't find resource bundle!";
		}
	}


	/**
	 * Get localized text from resource bundle by key
	 * @param key
	 * @return localized text
	 */
	public static String i18n(String key) {
		ResourceBundle bundle = getBundle();
		try {
			return bundle.getString(key);
		} catch (MissingResourceException e) {
			logger.warn("Message key \"" + key + "\" not found.");
			return "["+key+"]";
		}
	}
	
	/**
	 * Get resource bundle defined in the faces-config
	 * @return ResourceBundle
	 * @throws NullPointerException
	 * @throws MissingResourceException
	 */
	private static ResourceBundle getBundle() {
		final FacesContext facesContext = FacesContext.getCurrentInstance();
		final Locale locale = facesContext.getViewRoot().getLocale();
		String bundleName = facesContext.getApplication().getMessageBundle();
		
		return ResourceBundle.getBundle(bundleName, locale, getClassLoader());
	}

	/**
	 * Get the field label.
	 * 
	 * @param fieldName
	 *            fieldName
	 * @param formId
	 *            form id
	 * @return Message from the Message Source.
	 */
	public static String getFieldLabel(final String fieldName, final String formId) {

		Locale locale = FacesContext.getCurrentInstance().getViewRoot().getLocale();
		String bundleName = FacesContext.getCurrentInstance().getApplication().getMessageBundle();

		ResourceBundle bundle = ResourceBundle.getBundle(bundleName, locale, getClassLoader());

		/** Look for formId.fieldName, e.g., EmployeeForm.firstName. */

		String label = null;
		try {
			label = bundle.getString(formId + fieldName);
			return label;
		} catch (MissingResourceException e) {
			// do nothing on purpose.
		}

		try {
			/** Look for just fieldName, e.g., firstName. */
			label = bundle.getString(fieldName);
		} catch (MissingResourceException e) {
			/**
			 * Convert fieldName, e.g., firstName automatically becomes First
			 * Name.
			 */
			label = generateLabelValue(fieldName);
		}

		return label;

	}

	private static ClassLoader getClassLoader() {
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		if (classLoader == null) {
			return JsfFunctions.class.getClassLoader();
		}
		return classLoader;
	}

	/**
	 * Generate the field. Transforms firstName into First Name. This allows
	 * reasonable defaults for labels.
	 * 
	 * @param fieldName
	 *            fieldName
	 * 
	 * @return generated label name.
	 */
	public static String generateLabelValue(final String fieldName) {
		StringBuffer buffer = new StringBuffer(fieldName.length() * 2);
		char[] chars = fieldName.toCharArray();

		/* Change firstName to First Name. */
		for (int index = 0; index < chars.length; index++) {
			char cchar = chars[index];

			/* Make the first character uppercase. */
			if (index == 0) {
				cchar = Character.toUpperCase(cchar);
				buffer.append(cchar);

				continue;
			}

			/* Look for an uppercase character, if found add a space. */
			if (Character.isUpperCase(cchar)) {
				buffer.append(' ');
				buffer.append(cchar);

				continue;
			}

			buffer.append(cchar);
		}

		return buffer.toString();
	}
}
