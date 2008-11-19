package org.openuss.layout;

import javax.servlet.ServletContext;

/**
 * A theme holds all needed information that openuss needs according to use this theme.
 * This informations are the position of facelets composite templates as well the path to find images and stylesheets.
 *   
 * @author idueppe
 */
public interface Theme{

	public static final String IMAGE_ROOTPATH = "images";
	public static final String LAYOUT = "layout";
	public static final String DIALOG = "dialog";
	public static final String STYLESHEET = "stylesheet";

	/**
	 * Unique Id of the theme
	 * @return String
	 */
	public abstract String getId();

	/**
	 * Sets the unique Id of the theme
	 * @param id
	 */
	public abstract void setId(String id);

	/**
	 * Images path prefix of the theme. This path will be used to prefix all standard images.
	 * @return String
	 */
	public abstract String getImagesPath();
	
	/**
	 * Sets the path prefix of the theme.
	 * @param imageRootPath
	 */
	public abstract void setImagesPath(String imagesPath);
	
	
	/**
	 * Helper Method to access the context path of the web application
	 */
	public abstract String getContextPath();
		
	/**
	 * Name of the theme to be displayed.
	 * @return String
	 */
	public abstract String getName();

	/**
	 * Sets the name of the theme.
	 * @param name
	 */
	public abstract void setName(String name);

	/**
	 * path and name of the stylesheet that is used.
	 * The format is like /classic/css/design.css. 
	 * @return String
	 */
	public abstract String getStylesheet();

	/**
	 * Sets the path and stylessheet of the theme.
	 * @param stylesheet
	 */
	public abstract void setStylesheet(String stylesheet);
	
	/**
	 * The path where the standard template can be found.
	 * @return String
	 */
	public abstract String getLayout();

	/**
	 * Sets the template path.
	 * @param templatePath
	 */
	public abstract void setLayout(String layout);
	
	
	/**
	 * The path where the dialog template can be found.
	 * The dialog is a popup frame embedded in standard layout frame.
	 */
	public abstract String getDialog();
	
	/**
	 * Sets the path of a dialog template
	 */
	public abstract void setDialog(String dialog);
	
	/**
	 * Sets the ServletContext of the theme.
	 * @param servletContext
	 */
	public abstract void setServletContext(ServletContext servletContext);

	/**
	 * The ServletContext of the theme webapp.
	 * @return ServletContext
	 */
	public abstract ServletContext getServletContext();
	
	/**
	 * Sets the ClassLoader of the theme webapp.
	 * @param classLoader
	 */
	public abstract void setClassLoader(ClassLoader classLoader);
	
	/**
	 * The ClassLoader of the theme webapp.
	 * @return classLoader
	 */
	public abstract ClassLoader getClassLoader();

}