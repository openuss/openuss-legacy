package org.openuss.web.utils;

import java.util.ArrayList;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;

import org.openuss.framework.web.xss.HtmlInputFilter;

/**
 * Sanitize text inputs against cross scripting attacks
 * @author Team Collaboration
 */
public class WikiHtmlInputFilterConverter implements Converter {

	public Object getAsObject(FacesContext context, UIComponent component, String value) throws ConverterException {
		return new WikiHtmlInputFilter().filter(value);
	}

	public String getAsString(FacesContext context, UIComponent component, Object value) throws ConverterException {
		return (value == null) ? "" : value.toString();
	}

	private static class WikiHtmlInputFilter extends HtmlInputFilter {
		protected final static String ADDITIONAL_ALLOWED_TAGS[] = {
			"h1", "h2", "h3", "h4", "h5", "h6", "div", "pre", "sup", "sub"};
		public WikiHtmlInputFilter() {
			super();
			
			ArrayList<String> default_atts = new ArrayList<String>();
			default_atts.add("color");
			default_atts.add("style");
			default_atts.add("align");
			
			for (String tag : ADDITIONAL_ALLOWED_TAGS) {
				vAllowed.put(tag, default_atts);
			}			
		}
	}
}
