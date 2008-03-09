package org.openuss.web.seminarpool.courseAllocation;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;

import org.apache.shale.tiger.register.FacesConverter;


@FacesConverter(value = "seminarpoolDateConverter")
public class SeminarpoolDateConverter implements Converter {

	private static final String datePattern = "HH:mm";
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat(datePattern);

	
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2)
			throws ConverterException {
		try {
			return dateFormat.parse(arg2);
		} catch (ParseException e) {
			throw new ConverterException();
		}
	}

	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2)
			throws ConverterException {
		if (arg2 instanceof Date) {
			return dateFormat.format((Date)arg2);	
		}
		return null;
	}



}
