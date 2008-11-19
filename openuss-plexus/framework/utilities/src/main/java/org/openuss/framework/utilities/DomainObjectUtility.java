package org.openuss.framework.utilities;

import org.apache.log4j.Logger;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class DomainObjectUtility {

	private static final Logger logger = Logger.getLogger(DomainObjectUtility.class);

	public static Long identifierFromObject(Object object) {
		if (object instanceof Long) {
			return (Long) object;
		} else {
			try {
				return obtainIdentityByGetIdMethod(object);
			} catch (Exception e) {
				logger.error("couldn't obtain id from domain object.",e);
				return null;
			}
		}
	}
	
	public static Long obtainIdentityByGetIdMethod(Object object) throws IllegalAccessException, InvocationTargetException {
		final Class<?> clazz = object.getClass();
		try {
			final Method method = clazz.getMethod("getId", new Class[] {});
			final Object result = method.invoke(object, new Object[] {});
			return (Long) result;
		} catch (ClassCastException ex) {
			throw new IllegalArgumentException("Object of class '" + clazz
					+ "' does not provide the required Integer getId() method: " + object);
		} catch (NoSuchMethodException nsme) {
			throw new IllegalArgumentException("Object of class '" + clazz
					+ "' does not provide the required Integer getId() method: " + object);
		}
	}

}
