package org.openuss.web.seminarpool;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;

import org.apache.shale.tiger.register.FacesConverter;
import org.openuss.seminarpool.SeminarpoolAccessType;

@FacesConverter(value = "seminarpoolAccessTypeConverter")
public class SeminarpoolAccessTypeConverter implements Converter  {

	public Object getAsObject(FacesContext context, UIComponent component, String value) throws ConverterException {
		int literal = Integer.parseInt(value);
		return SeminarpoolAccessType.fromInteger(literal);
	}

	public String getAsString(FacesContext context, UIComponent component, Object value) throws ConverterException {
		
		if (value instanceof SeminarpoolAccessType){
			SeminarpoolAccessType seminarpoolAccessType = (SeminarpoolAccessType) value;
			return seminarpoolAccessType.getValue().toString();
		} else if (value instanceof Integer) {
			return String.valueOf(value);
		} else if (value instanceof String) {
			return (String) value;
		}
		return null;
	}
}