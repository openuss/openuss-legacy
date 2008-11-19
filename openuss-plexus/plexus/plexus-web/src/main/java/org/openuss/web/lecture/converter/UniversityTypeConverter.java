package org.openuss.web.lecture.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import org.openuss.lecture.UniversityType;

/**
 * Converts a UniversityType object to a string representation and vice versa.
 * @author Kai Stettner
 * @author Ron Haus
 */
public class UniversityTypeConverter implements Converter {

	/**
	 * Converts the string locale ID into a UniversityType object.
	 * 
	 * @param facesContext jsf facesContext
	 * @param component converter assigned
	 * @param value to be converterd
	 * @return strong typed value
	 */
	public Object getAsObject(final FacesContext context, final UIComponent component, final String value) throws ConverterException {
		return UniversityType.fromInteger(Integer.valueOf(value));
	}

	/**
	 * Converts a UniversityType object into the string UniversityType id.
	 * @param facesContext jsf faces context
	 * @param component converter assigned
	 * @param value to be converterd
	 * @return string represention of the value
	 */
	public String getAsString(FacesContext context, UIComponent component, Object value) throws ConverterException {
		if (value != null) {
			return ((UniversityType) value).toString();
		}
		return null;
	}

}