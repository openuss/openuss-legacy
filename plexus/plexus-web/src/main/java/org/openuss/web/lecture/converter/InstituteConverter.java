package org.openuss.web.lecture.converter;

import org.apache.log4j.Logger;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;

import org.openuss.lecture.Institute;

/**
 * Converts a Institute by its id to string and vice versa
 * @author Ingo Dueppe
 *
 */
public class InstituteConverter implements Converter {

	private static final Logger logger = Logger.getLogger(InstituteConverter.class);

	public Object getAsObject(FacesContext context, UIComponent component, String value) throws ConverterException {
		try {
			Institute institute = Institute.Factory.newInstance();
			institute.setId(Long.parseLong(value));
			return institute;
		} catch (NumberFormatException ex) {
			logger.error(ex);
			return null;
		}
	}

	public String getAsString(FacesContext context, UIComponent component, Object value) throws ConverterException {
		if (value instanceof Institute) {
			return String.valueOf(((Institute) value).getId());
		}
		return null;
	}

}