package org.openuss.web.themes;

import javax.servlet.ServletContext;

import org.apache.commons.lang.StringUtils;

/**
 * Implementation of the theme interface
 * 
 * @author idueppe
 */
public class ThemeBean implements Theme {

	private String id;
	private String name;
	private String stylesheet;
	private String layout;
	private String dialog;
	private String imagesPath;
	
	private ServletContext servletContext;
	private ClassLoader classLoader;

	/**
	 * {@inheritDoc}
	 */
	public String getId() {
		return id;
	}

	/**
	 * {@inheritDoc}
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * {@inheritDoc}
	 */
	public String getImagesPath() {
		return imagesPath;
	}

	/**
	 * {@inheritDoc}
	 */
	public void setImagesPath(String imagesPath) {
		this.imagesPath = imagesPath;
	}

	/**
	 * {@inheritDoc}
	 */
	public String getName() {
		return name;
	}

	/**
	 * {@inheritDoc}
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * {@inheritDoc}
	 */
	public String getStylesheet() {
		return stylesheet;
	}

	/**
	 * {@inheritDoc}
	 */
	public void setStylesheet(String stylesheet) {
		this.stylesheet = stylesheet;
	}

	/**
	 * {@inheritDoc}
	 */
	public String getLayout() {
		return layout;
	}

	/**
	 * {@inheritDoc}
	 */
	public void setLayout(String layout) {
		this.layout = layout;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Theme) {
			Theme theme = (Theme) obj;
			return StringUtils.equals(id, theme.getId());
		}
		return super.equals(obj);
	}

	@Override
	public int hashCode() {
		if (id != null)
			return id.hashCode();
		else
			return super.hashCode();
	}

	/**
	 * {@inheritDoc}
	 */
	public ClassLoader getClassLoader() {
		return classLoader;
	}

	/**
	 * {@inheritDoc}
	 */
	public void setClassLoader(ClassLoader classLoader) {
		this.classLoader = classLoader;
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

	/**
	 * {@inheritDoc}
	 */
	public String getDialog() {
		return dialog;
	}

	/**
	 * {@inheritDoc}
	 */
	public void setDialog(String dialog) {
		this.dialog = dialog;
	}
}
