package org.openuss.framework.jsfcontrols.converter;

import java.util.TimeZone;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

/**
 * JavaServer Faces TimeZone Converter.
 * 
 * @author Ingo Dueppe
 */
public class TimeZoneConverter implements Converter {

	/**
	 * Converts the string zone ID into a TimeZone object.
	 * 
	 * @param facesContext jsf facesContext
	 * @param component converter assigned
	 * @param value to be converterd
	 * @return strong typed value
	 */
	public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
		final TimeZone timeZone = TimeZone.getTimeZone(value);
		return timeZone;
	}

	/**
	 * Converts a TimeZone object into the string zone id.
	 * @param facesContext jsf faces context
	 * @param component converter assigned
	 * @param value to be converterd
	 * @return string represention of the value
	 */
	public String getAsString(FacesContext facesContext, UIComponent component, Object value) {
		final TimeZone timeZone = (TimeZone) value;
		if (value != null) {
			return timeZone.getID();
		}

		return null;
	}

}
