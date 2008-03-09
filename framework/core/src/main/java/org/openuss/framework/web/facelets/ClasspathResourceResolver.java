package org.openuss.framework.web.facelets;

import java.net.URL;

import org.apache.log4j.Logger;

import com.sun.facelets.impl.DefaultResourceResolver;

public class ClasspathResourceResolver extends DefaultResourceResolver {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(ClasspathResourceResolver.class);

	public URL resolveUrl(final String path) {
		if (logger.isTraceEnabled()) {
			logger.trace("resolveUrl(path=" + path + ") - start"); 
		}

		URL url = resolveClasspath(Thread.currentThread().getContextClassLoader(), path);
		if (url == null){
			url = super.resolveUrl(path);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("found url "+url); 
		}
		return url;
	}

	public String toString() {
		return "ClasspathResourceResolver";
	}
	
	public URL resolveClasspath(final ClassLoader classLoader, String path) {
		if (logger.isTraceEnabled()) {
			logger.trace("resolveClasspath(path=" + path + ")"); 
		}
		if (path.charAt(0) == '/') {
			path = path.substring(1);
		}
		return classLoader.getResource(path);
	}
	
}
