package org.openuss.web.seminarpool;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;

import org.apache.shale.tiger.register.FacesConverter;
import org.openuss.groups.GroupAccessType;
import org.openuss.seminarpool.ConditionType;

@FacesConverter(value = "seminarpoolConditionTypeConverter")
public class SeminarpoolConditionTypeConverter implements Converter  {

	public Object getAsObject(FacesContext context, UIComponent component, String value) throws ConverterException {
		int literal = Integer.parseInt(value);
		return ConditionType.fromInteger(literal);
	}

	public String getAsString(FacesContext context, UIComponent component, Object value) throws ConverterException {
		
		if (value instanceof ConditionType){
			ConditionType seminarpoolConditionType = (ConditionType) value;
			return seminarpoolConditionType.getValue().toString();
		} else if (value instanceof GroupAccessType) {
			GroupAccessType groupAccessType = (GroupAccessType) value;
			return groupAccessType.getValue().toString();
		} else if (value instanceof Integer) {
			return String.valueOf(value);
		} else if (value instanceof String) {
			return (String) value;
		}
		return null;
	}
}