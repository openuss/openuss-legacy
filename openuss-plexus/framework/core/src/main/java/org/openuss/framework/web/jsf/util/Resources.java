package org.openuss.framework.web.jsf.util;

import org.apache.log4j.Logger;

import java.io.InputStream;

import javax.faces.context.FacesContext;

/**
 * 
 * @author Ingo Dueppe
 */
public class Resources {

	private static final Logger logger = Logger.getLogger(Resources.class);

	public static InputStream getResourceAsStream(String resource) {
		String stripped = resource.startsWith("/") ? resource.substring(1) : resource;
		InputStream stream = null;

		try {
			stream = FacesContext.getCurrentInstance().getExternalContext().getResourceAsStream(resource);
		} catch (Exception e) {
			logger.debug("Could not resolve resouce "+resource, e);
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
