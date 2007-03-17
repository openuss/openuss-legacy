package org.openuss.framework.utilities;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class DomainObjectUtility {

	public static Long identifierFromObject(Object object) throws IllegalAccessException, InvocationTargetException {
		if (object instanceof Long) {
			return (Long) object;
		} else {
			return obtainIdentityByGetIdMethod(object);
		}
	}
	
	public static Long obtainIdentityByGetIdMethod(Object object) throws IllegalAccessException, InvocationTargetException {
		final Class clazz = object.getClass();
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
