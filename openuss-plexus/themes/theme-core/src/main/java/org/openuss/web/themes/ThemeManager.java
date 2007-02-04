package org.openuss.web.themes;

import java.util.Collection;

import javax.faces.model.SelectItem;
import javax.servlet.ServletContext;

/**
 * ThemeManager controls configured and deployement themes.
 * 
 * Normally this calls should be created at webapplication startup and stored in application scope. 
 * Therefore the servlet context and a default theme id should be configured.
 * 
 * When the default theme is registered through the hot deploy mechanismen this theme will be put into application scope.
 * To override the default theme by user settings set the current theme. 
 * The current theme is stored in the session scope. Within the pages access the needed theme through #{theme} without 
 * any scoping. The depending on if a current theme exist or not it will hide the application scoped theme.   
 * 
 * @author Ingo Dueppe
 */
public interface ThemeManager {

	/**
	 * Session key of current selected theme or the application scope key of the
	 * default theme.
	 */
	public static final String THEME_SCOPEKEY = "theme";
	
	/**
	 * Application scope key of the ThemeManagers.
	 */
	public static final String THEMEMANAGER_APPLICATIONKEY = "themeManager";
	
	/**
	 * Key of the context parameter to configure the default theme id. 
	 */
	public static final String DEFAULT_THEME_ID_PARAMETER = "org.openuss.web.themes.DEFAULT_THEME_ID";
	
	/**
	 * Set the default theme id 
	 * @param id
	 */
	public abstract void setDefaultThemeId(String id);
	
	/**
	 * The id of the default theme
	 * @return String
	 */
	public abstract String getDefaultThemeId();
	
	/**
	 * Sets the default theme.
	 * @param id
	 */
	public abstract void setDefaultTheme(Theme theme);
	
	/**
	 * Sets the servlet context. This reference is needed to register theme into the application and session scope if needed.
	 * @param servletContext
	 */
	public abstract void setServletContext(ServletContext servletContext);
	
	/**
	 * Gets the associated servlet context.
	 * @return ServletContext
	 */
	public abstract ServletContext getServletContext();
	
	/**
	 * The default theme.
	 * @return Theme object
	 */
	public abstract Theme getDefaultTheme();

	/**
	 * Register theme in the registered theme set.
	 * 
	 * @param theme
	 */
	public abstract void registerTheme(Theme theme);

	/**
	 * Unregister theme object from registered theme set.
	 * 
	 * @param theme
	 */
	public abstract void unregisterTheme(Theme theme);

	/**
	 * List of all registered themes
	 * 
	 * @return Collection of theme objects
	 */
	public abstract Collection<Theme> getThemes();

	/**
	 * Sets the current selected theme and stores it into the session scope.
	 */
	public abstract void setCurrentTheme(Theme theme);

	/**
	 * Access the current selected theme.
	 * 
	 * @return Theme object
	 */
	public abstract Theme getCurrentTheme();

	/**
	 * Collection of SelectItem containing the theme object and the name of
	 * theme theme as description.
	 * 
	 * @return Collection<SelectItem>
	 */
	public abstract Collection<SelectItem> getSelectableThemes();

}