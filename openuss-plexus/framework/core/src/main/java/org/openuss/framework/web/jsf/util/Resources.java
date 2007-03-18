package org.openuss.framework.web.jsf.util;

import java.io.InputStream;

import javax.faces.context.FacesContext;

/**
 * 
 * @author Ingo Dueppe
 */
public class Resources {

	public static InputStream getResourceAsStream(String resource) {
		String stripped = resource.startsWith("/") ? resource.substring(1) : resource;
		InputStream stream = null;

		try {
			stream = FacesContext.getCurrentInstance().getExternalContext().getResourceAsStream(resource);
		} catch (Exception e) {
			// nothing found - do nothing
		}

		if (stream == null) {
			stream = getResourceAsStream(resource, stripped);
		}
		return stream;
	}

	private static InputStream getResourceAsStream(String resource, String stripped) {
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		InputStream stream = null;
		if (classLoader != null) {
			stream = classLoader.getResourceAsStream(stripped);
		}
		if (stream == null) {
			stream = Resources.class.getClassLoader().getResourceAsStream(stripped);
		}
		return stream;
	}

}
