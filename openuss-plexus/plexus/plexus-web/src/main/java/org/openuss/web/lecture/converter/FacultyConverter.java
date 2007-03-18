package org.openuss.web.lecture.converter;

import org.apache.log4j.Logger;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;

import org.openuss.lecture.Faculty;

/**
 * Converts a Faculty by its id to string and vice versa
 * @author Ingo Dueppe
 *
 */
public class FacultyConverter implements Converter {

	private static final Logger logger = Logger.getLogger(FacultyConverter.class);

	public Object getAsObject(FacesContext context, UIComponent component, String value) throws ConverterException {
		try {
			Faculty faculty = Faculty.Factory.newInstance();
			faculty.setId(Long.parseLong(value));
			return faculty;
		} catch (NumberFormatException ex) {
			logger.error(ex);
			return null;
		}
	}

	public String getAsString(FacesContext context, UIComponent component, Object value) throws ConverterException {
		if (value instanceof Faculty) {
			return String.valueOf(((Faculty) value).getId());
		}
		return null;
	}

}
