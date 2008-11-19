package org.openuss.web.utils;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;

import org.openuss.framework.web.xss.HtmlInputFilter;

/**
 * Sanitize text inputs against cross scripting attacks
 * @author Ingo Dueppe
 */
public class HtmlInputFilterConverter implements Converter {

	public Object getAsObject(FacesContext context, UIComponent component, String value) throws ConverterException {
		return new HtmlInputFilter().filter(value);
	}

	public String getAsString(FacesContext context, UIComponent component, Object value) throws ConverterException {
		return (value == null) ? "" : value.toString();
	}

}
