/**
 * Title:        OpenUSS - Open University Support System
 * Description:  Utility Class to find factories
 * Copyright:    Copyright (c) Ingo Düppe
 * Company:      University of Muenster
 */
package org.openuss.framework.utilities;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.logging.Logger;

/**
 * @author Ingo Düppe
 * @version 1.0 - 25.11.2004
 */
public class FactoryFinder {

	private static final Logger logger = Logger.getLogger(FactoryFinder.class.getName());

	private static SecuritySupport securitySupport = new SecuritySupport();

	/**
	 * Obtain a instance of the <code>Factory</code> defined by the factoryId.
	 * This static method creates a new factory instance
	 * 
	 * This method uses the following ordered lookup procedure to determine the
	 * factory implementation class to load:
	 * <ul>
	 * <li>Use the <strong>factoryId </strong> system property.</li>
	 * <li>Use the Services API (as detailed in the JAR specification), if
	 * available, to determine the classname. The Services API will look for a
	 * classname in the file
	 * <code>META-INF/services/<strong>factoryId</strong></code> in jars
	 * available to the runtime.</li>
	 * </ul>
	 * 
	 * @param factoryId
	 * @return instance of a factory implementation
	 * @throws ConfigurationError
	 *             if no implementation could be found
	 */
	public static Object find(String factoryId) throws ConfigurationError {
		// Figure out which ClassLoader to use for loading the provider class.
		// If there is a Context ClassLoad then use it.
		ClassLoader classLoader = securitySupport.getContextClassLoader();

		if (classLoader == null) {
			// if no Context ClassLoader then une current class loader.
			classLoader = FactoryFinder.class.getClassLoader();
		}

		// lookup system properties for factoryId
		try {
			String factoryClassName = securitySupport.getSystemProperty(factoryId);

			if (factoryClassName != null) {
				return newInstance(factoryClassName, classLoader);
			}
		} catch (SecurityException sr) {
			// first option fails due to any reason try next option.
		}

		// lookup jar service provider mechanism
		String serviceId = "META-INF/services/" + factoryId;
		InputStream is = null;
		is = securitySupport.getResourceAsStream(classLoader, serviceId);
		if (is != null) {
			BufferedReader rd;
			try {
				rd = new BufferedReader(new InputStreamReader(is, "UTF-8"));
			} catch (UnsupportedEncodingException e) {
				rd = new BufferedReader(new InputStreamReader(is));
			}

			String factoryClassName = null;

			try {
				factoryClassName = rd.readLine();
			} catch (Exception e) {
				// No provider found;
				logger.fine(e.getLocalizedMessage());
			} finally {
				try {
					rd.close();
				} catch (IOException e) {
					logger.fine(e.getLocalizedMessage());
				}
			}

			if ((factoryClassName != null) && !"".equals(factoryClassName)) {
				return newInstance(factoryClassName, classLoader);
			}
		}
		throw new ConfigurationError("Couldn't find implementation for " + factoryId);
	}

	/**
	 * @param factoryClassName
	 * @param classLoader
	 * @return Instance of the factory implementation.
	 */
	private static Object newInstance(String factoryClassName, ClassLoader classLoader) throws ConfigurationError {
		try {
			Class<?> provider;

			if (classLoader == null) {
				provider = Class.forName(factoryClassName);
			} else {
				provider = classLoader.loadClass(factoryClassName);
			}

			return provider.newInstance();
		} catch (ClassNotFoundException ex) {
			throw new ConfigurationError("Provider " + factoryClassName + " not found.", ex);
		} catch (Exception ex) {
			throw new ConfigurationError("Provider " + factoryClassName + " could not be instantiated.", ex);
		}
	}

	static public class ConfigurationError extends Error {
		private static final long serialVersionUID = 1L;
		private Exception exception;

		/**
		 * Construct a new instance with the specified detail string and
		 * exception.
		 */
		ConfigurationError(String msg, Exception x) {
			super(msg);
			this.exception = x;
		}

		ConfigurationError(String msg) {
			super(msg);
		}

		Exception getException() {
			return exception;
		}
	}
}