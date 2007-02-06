package org.openuss.web.news;

import org.apache.log4j.Logger;
import org.openuss.framework.web.jsf.controller.BaseBean;
import org.openuss.news.NewsCategory;
import org.openuss.news.NewsService;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;

/**
 * Converts a NewsCategory to string and vice versa
 * 
 * @author Ingo Dueppe
 * 
 */
public class NewsCategoryConverter extends BaseBean implements Converter {
	
	private static final long serialVersionUID = -1469962525827248875L;

	private static final Logger logger = Logger.getLogger(NewsCategoryConverter.class);
	
	private NewsService newsService;
	
	public Object getAsObject(FacesContext context, UIComponent component, String value) throws ConverterException {
		logger.debug("Converting from " + value + " to object");
		try {
			return getStringAsNewsCategory(value);
		} catch (NumberFormatException e) {
			throw new ConverterException("Can't convert to an NewsCategory; value (" + value + ") is not an integer", e);
		}
	}

	public String getAsString(FacesContext context, UIComponent component, Object value) throws ConverterException {
		if (value == null)
			return "";
		if (logger.isDebugEnabled())
			logger.debug("Converting from " + value + "to string");
		if (value instanceof NewsCategory) {
			return getNewsCategoryAsString((NewsCategory) value);
		} else {
			throw new ConverterException("Incorrect type (" + value.getClass() + "; value" + value
					+ ") value must be an NewsCategory instance");
		}
	}

	private String getNewsCategoryAsString(NewsCategory category) {
		return String.valueOf(category.getId());
	}

	private Object getStringAsNewsCategory(String value) {
		long id = Long.parseLong(value);
		return getNewsService().getCategory(id);
	}

	public NewsService getNewsService() {
		if (newsService == null)
			newsService = (NewsService) getBean("newsService");
		return newsService;
	}

	public void setNewsService(NewsService newsService) {
		this.newsService = newsService;
	}
}
