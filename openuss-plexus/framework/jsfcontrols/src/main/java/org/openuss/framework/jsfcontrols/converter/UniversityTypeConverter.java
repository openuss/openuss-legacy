package org.openuss.framework.jsfcontrols.converter;

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
	public Object getAsObject(FacesContext context, UIComponent component, String value) throws ConverterException {
		final UniversityType universityType = UniversityType.fromInteger(Integer.valueOf(value));
		return universityType;
	}

	/**
	 * Converts a UniversityType object into the string UniversityType id.
	 * @param facesContext jsf faces context
	 * @param component converter assigned
	 * @param value to be converterd
	 * @return string represention of the value
	 */
	public String getAsString(FacesContext context, UIComponent component, Object value) throws ConverterException {
		final UniversityType universityType = (UniversityType) value;
		if (universityType != null) {
			return universityType.toString();
		}
		return null;
	}

}