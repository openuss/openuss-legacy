package org.openuss.web.lecture.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;

import org.openuss.lecture.DepartmentType;

/**
 * Converts a DepartmentType object to a string representation and vice versa.
 * @author Kai Stettner
 */
public class DepartmentTypeConverter implements Converter {

	/**
	 * Converts the string locale ID into a DepartmentType object.
	 * 
	 * @param facesContext jsf facesContext
	 * @param component converter assigned
	 * @param value to be converterd
	 * @return strong typed value
	 */
	public Object getAsObject(FacesContext context, UIComponent component, String value) throws ConverterException {
		final DepartmentType departmentType = DepartmentType.fromInteger(Integer.valueOf(value));
		return departmentType;
	}

	/**
	 * Converts a DepartmentType object into the string DepartmentType id.
	 * @param facesContext jsf faces context
	 * @param component converter assigned
	 * @param value to be converterd
	 * @return string represention of the value
	 */
	public String getAsString(FacesContext context, UIComponent component, Object value) throws ConverterException {
		final DepartmentType departmentType = (DepartmentType) value;
		if (departmentType != null) {
			return departmentType.toString();
		}
		return null;
	}

}