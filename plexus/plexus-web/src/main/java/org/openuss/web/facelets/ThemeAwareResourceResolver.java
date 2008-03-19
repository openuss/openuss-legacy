package org.openuss.web.facelets;

import org.apache.log4j.Logger;

import java.net.MalformedURLException;
import java.net.URL;

import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;

import org.openuss.framework.web.facelets.ClasspathResourceResolver;
import org.openuss.web.themes.Theme;
import org.openuss.web.themes.ThemeManager;

public class ThemeAwareResourceResolver extends ClasspathResourceResolver {

	private static final Logger logger = Logger.getLogger(ThemeAwareResourceResolver.class);

	@Override
	public URL resolveUrl(final String path) {
		URL url = super.resolveUrl(path);
		if (url == null) {
			Theme theme = getCurrentTheme();
			if (theme != null) {
				ServletContext servletContext = theme.getServletContext();
				try {
					url = servletContext.getResource(path);
					logger.debug("resolved path to "+url);
				} catch (MalformedURLException e) {
					// ignore and try classpath
				}
				if (url == null) {
					ClassLoader classLoader = theme.getClassLoader();
					url = super.resolveClasspath(classLoader, path);
					logger.debug("resolved path to "+url);
				}
			}
		}
		return url;
	}
	
	private Theme getCurrentTheme() {
		Theme theme = (Theme) getFacesContext().getExternalContext().getSessionMap().get(ThemeManager.THEME_SCOPEKEY);
		if (theme == null) {
			theme = (Theme) getFacesContext().getExternalContext().getApplicationMap().get(ThemeManager.THEME_SCOPEKEY);
		}
		return theme;
	}
	
	private FacesContext facesContext;
	
	public FacesContext getFacesContext() {
		if (facesContext != null) {
			return facesContext; // for unit testing
		}
		return FacesContext.getCurrentInstance();
	}

	public void setFacesContext(FacesContext facesContext) {
		this.facesContext = facesContext;
	}

}
