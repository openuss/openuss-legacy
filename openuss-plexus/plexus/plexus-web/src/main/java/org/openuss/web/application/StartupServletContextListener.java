package org.openuss.web.application;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;
import org.openuss.framework.deploy.HotDeployUtil;
import org.openuss.web.themes.ThemeHotDeployListener;
import org.openuss.web.themes.ThemeManager;
import org.openuss.web.themes.ThemeManagerBean;

/**
 * OpenUSS Startup Listener
 * @author Ingo Dueppe
 *
 */
public class StartupServletContextListener implements ServletContextListener {

	private static final Logger logger = Logger.getLogger(StartupServletContextListener.class);
	
	private ThemeHotDeployListener themeHotDeployListener;

	public void contextDestroyed(ServletContextEvent event) {
		if (themeHotDeployListener != null) {
			logger.debug("Unregister ThemeHotDeployListener");
			HotDeployUtil.unregisterListener(themeHotDeployListener);
		}
	}

	public void contextInitialized(ServletContextEvent event) {
		logger.info("================ Starting OpenUSS Plexus ======================");
		ServletContext servletContext = event.getServletContext();
		configureThemeManager(servletContext);
		
	}

	private void configureThemeManager(ServletContext servletContext) {
		logger.info("configuring themes");
		
		ThemeManager themeManager = (ThemeManager) servletContext.getAttribute(ThemeManager.THEMEMANAGER_APPLICATIONKEY);
		if (themeManager == null) {
			String defaultThemeId = servletContext.getInitParameter(ThemeManager.DEFAULT_THEME_ID_PARAMETER);
			logger.debug("Create ThemeManager");
			themeManager = new ThemeManagerBean();
			themeManager.setServletContext(servletContext);
			themeManager.setDefaultThemeId(defaultThemeId);
			servletContext.setAttribute(ThemeManager.THEMEMANAGER_APPLICATIONKEY, themeManager);
			
			// Conntect ThemeManager with HotDeployer
			logger.debug("Register ThemeManager at HotDeployer");
			
			themeHotDeployListener = new ThemeHotDeployListener();
			themeHotDeployListener.setThemeManager(themeManager);
			HotDeployUtil.registerListener(themeHotDeployListener);
			HotDeployUtil.flushEvents();
		}
	}

}
