package org.openuss.framework.servlet;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.openuss.framework.deploy.HotDeployEvent;
import org.openuss.framework.deploy.HotDeployUtil;

/**
 * ThemeContextListener must be configured within the web.xml of a theme webapp. 
 * 
 * It will notify openuss-plexus that a new theme is deployed at the server.
 * 
 * @author Ingo Dueppe
 * 
 * Inspired by:
 * @author Ivica Cardic
 * @author Brian Wing Shun Chan
 * 
 */
public class ThemeContextListener implements ServletContextListener {

	public void contextInitialized(ServletContextEvent event) {
		HotDeployUtil.fireDeployEvent(new HotDeployEvent(event.getServletContext(), Thread.currentThread()
				.getContextClassLoader()));
	}

	public void contextDestroyed(ServletContextEvent sce) {
		HotDeployUtil.fireUndeployEvent(new HotDeployEvent(sce.getServletContext(), Thread.currentThread()
				.getContextClassLoader()));
	}

}
