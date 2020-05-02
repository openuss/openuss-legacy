/**
 * Title:        OpenUSS - Open University Support System
 * Description:  Utility Class to find factories
 * Copyright:    Copyright (c) Ingo Düppe
 * Company:      University of Muenster
 */
package org.openuss.api.utilities;

import java.io.InputStream;

import java.security.AccessController;
import java.security.PrivilegedAction;

/**
 * @author Ingo Düppe
 * @version 1.0 - 25.11.2004
 */
/*package*/ class SecuritySupport {
    /**
     * 
     * @return Context ClassLoader of current Thread
     */
    @SuppressWarnings("unchecked")
	ClassLoader getContextClassLoader() {
        return (ClassLoader) AccessController.doPrivileged(new PrivilegedAction() {
            public Object run() {
                ClassLoader cl = null;

                try {
                    cl = Thread.currentThread().getContextClassLoader();
                } catch (SecurityException ex) {
                    ex.printStackTrace();
                }

                return cl;
            }
        });
    }

    /**
     * @param factoryId
     * @return Value of the system property
     */
    @SuppressWarnings("unchecked")
	public String getSystemProperty(final String propertyName) {
        return (String) AccessController.doPrivileged(new PrivilegedAction() {
            public Object run() {
                return System.getProperty(propertyName);
            }
        });
    }

    /**
     * @param classLoader
     * @param serviceId
     * @return
     */
    @SuppressWarnings("unchecked")
	public InputStream getResourceAsStream(final ClassLoader classLoader, 
                                           final String serviceId) {
        return (InputStream) AccessController.doPrivileged(new PrivilegedAction() {
            public Object run() {
                InputStream is;

                if (classLoader == null) {
                    is = ClassLoader.getSystemResourceAsStream(serviceId);
                } else {
                    is = classLoader.getResourceAsStream(serviceId);
                }

                return is;
            }
        });
    }
}