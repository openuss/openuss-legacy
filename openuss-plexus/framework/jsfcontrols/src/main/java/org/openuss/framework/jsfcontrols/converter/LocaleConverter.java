package org.openuss.framework.jsfcontrols.converter;

import java.util.Locale;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;

/**
 * Converts a Locale object to a string representation and vice versa.
 * @author Ingo Dueppe
 */
public class LocaleConverter implements Converter {

	/**
	 * Converts the string locale ID into a Locale object.
	 * 
	 * @param facesContext jsf facesContext
	 * @param component converter assigned
	 * @param value to be converterd
	 * @return strong typed value
	 */
	public Object getAsObject(FacesContext context, UIComponent component, String value) throws ConverterException {
		final Locale locale = new Locale(value);
		return locale;
	}

	/**
	 * Converts a Locale object into the string Locale id.
	 * @param facesContext jsf faces context
	 * @param component converter assigned
	 * @param value to be converterd
	 * @return string represention of the value
	 */
	public String getAsString(FacesContext context, UIComponent component, Object value) throws ConverterException {
		final Locale locale = (Locale) value;
		if (locale != null) {
			return locale.getCountry();
		}
		return null;
	}

}
