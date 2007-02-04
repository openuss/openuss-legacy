package org.openuss.web.themes;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import javax.faces.model.SelectItem;
import javax.servlet.ServletContext;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Transformer;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.openuss.framework.web.jsf.controller.BaseBean;

/**
 * This class is to intend to provide theme management
 * 
 * @author Ingo Düppe
 */
public class ThemeManagerBean extends BaseBean implements ThemeManager {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(ThemeManagerBean.class);

	private static final long serialVersionUID = -5093914806760249739L;

	private HashMap<String, Theme> themes;
	private HashMap<String, Theme> themesById;

	private Theme currentTheme;

	private Theme defaultTheme;
	private String defaultThemeId;
	
	private ServletContext servletContext;
	

	public ThemeManagerBean() {
		try {
			logger.debug("----- ThemeManager init -----");
			// init hash maps
			themes = new HashMap<String, Theme>();
			themesById = new HashMap<String, Theme>();
		} catch (Exception e) {
			logger.error("constructor ", e);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public Collection<Theme> getThemes() {
		return themes.values();
	}

	/**
	 * {@inheritDoc}
	 */
	public Collection<SelectItem> getSelectableThemes() {
		Collection items = new ArrayList();
		items.addAll(themes.values());
		CollectionUtils.transform(items, new Transformer() {
			public Object transform(Object input) {
				Theme theme = (Theme) input;
				return new SelectItem(theme.getId(), theme.getName());
			}
		});
		return items;
	}


	/**
	 * {@inheritDoc}
	 */
	public void registerTheme(Theme theme) {
		if (theme == null || theme.getServletContext() == null || theme.getClassLoader() == null
				|| StringUtils.isEmpty(theme.getId()))
			throw new IllegalArgumentException("Parameter theme isn't configured right!");

		String contextName = theme.getServletContext().getServletContextName();

		themes.put(contextName, theme);
		themesById.put(theme.getId(), theme);
		
		logger.trace("defaultThemeID="+defaultThemeId);
		if (defaultTheme == null && StringUtils.equals(defaultThemeId, theme.getId())) {
			setDefaultTheme(theme);
		}

		logger.debug("Theme " + theme.getName() + " registered!");
	}

	/**
	 * {@inheritDoc}
	 */
	public void unregisterTheme(Theme theme) {
		if (theme == null || theme.getServletContext() == null)
			throw new IllegalArgumentException("Parameter theme is null or doesn't contain a servletContext");
		String contextName = theme.getServletContext().getServletContextName();
		theme = themes.get(contextName);
		themes.remove(theme.getServletContext().getServletContextName());
		themesById.remove(theme.getId());
	}

	/**
	 * {@inheritDoc}
	 */
	public Theme getCurrentTheme() {
		return currentTheme;
	}
	
	/**
	 * {@inheritDoc}
	 */
	public String getSelectedTheme() {
		if (currentTheme != null) {
			return currentTheme.getId();
		} else if (defaultTheme != null) {
			return defaultTheme.getId();
		} else {
			return "";
		}
	}
	
	/**
	 * {@inheritDoc} 
	 */
	public void setSelectedTheme(String id) {
		currentTheme = themesById.get(id);
		
		logger.debug("Put " + currentTheme.getName() + " into session!");
		setSessionBean(THEME_SCOPEKEY, currentTheme);
	}

	/**
	 * {@inheritDoc}
	 */
	public void setCurrentTheme(Theme theme) {
		setSessionBean(THEME_SCOPEKEY, theme);
		currentTheme = theme;
	}

	/**
	 * {@inheritDoc}
	 */
	public Theme getDefaultTheme() {
		return defaultTheme;
	}

	/**
	 * {@inheritDoc}
	 */
	public String getDefaultThemeId() {
		return defaultThemeId;
	}

	/**
	 * {@inheritDoc}
	 */
	public void setDefaultThemeId(String defaultThemeId) {
		this.defaultThemeId = defaultThemeId;
	}

	/**
	 * {@inheritDoc}
	 */
	public void setDefaultTheme(Theme defaultTheme) {
		if (getServletContext() == null)
			throw new IllegalStateException("DefaultTheme cannot be registered. No ServletContext is available!");
		this.defaultTheme = defaultTheme;
		
		// register theme into application scope
		getServletContext().setAttribute(THEME_SCOPEKEY, defaultTheme);
		logger.debug("register defaultTheme " +defaultTheme.getId() + " in appplication scope!");
	}

	/**
	 * {@inheritDoc}
	 */
	public ServletContext getServletContext() {
		return servletContext;
	}
	
	/**
	 * {@inheritDoc}
	 */
	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
		
	}
}
