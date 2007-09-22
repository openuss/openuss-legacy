package org.openuss.framework.deploy;

import javax.servlet.ServletContext;

/**
 * @author Ingo Dueppe
 * 
 * inpired by:
 * @author Ivica Cardic
 * @author Brian Wing Shun Chan
 */
public class HotDeployEvent {

	private ServletContext servletContext;

	private ClassLoader contextClassLoader;

	public HotDeployEvent(ServletContext servletContext, ClassLoader contextClassLoader) {
		this.servletContext = servletContext;
		this.contextClassLoader = contextClassLoader;
	}

	public ServletContext getServletContext() {
		return servletContext;
	}

	public ClassLoader getContextClassLoader() {
		return contextClassLoader;
	}

}