package org.openuss.framework.web.facelets;

import java.net.URL;

import org.apache.log4j.Logger;

import com.sun.facelets.impl.DefaultResourceResolver;
import com.sun.facelets.impl.ResourceResolver;

public class ClasspathResourceResolver extends DefaultResourceResolver implements ResourceResolver {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(ClasspathResourceResolver.class);

	public URL resolveUrl(String path) {
		if (logger.isTraceEnabled()) {
			logger.trace("resolveUrl(path=" + path + ") - start"); //$NON-NLS-1$ //$NON-NLS-2$
		}

		URL url = resolveClasspath(Thread.currentThread().getContextClassLoader(), path);
		if (url == null)
			url = super.resolveUrl(path);

		if (logger.isDebugEnabled()) {
			logger.debug("found url "+url); //$NON-NLS-1$
		}
		return url;
	}

	public String toString() {
		return "ClasspathResourceResolver";
	}
	
	public URL resolveClasspath(ClassLoader classLoader, String path) {
		if (logger.isTraceEnabled()) {
			logger.trace("resolveClasspath(path=" + path + ")"); //$NON-NLS-1$ //$NON-NLS-2$
		}
		
		if (path.startsWith("/"))
			path = path.substring(1);
		URL url = classLoader.getResource(path);

		return url;
	}
	
}
