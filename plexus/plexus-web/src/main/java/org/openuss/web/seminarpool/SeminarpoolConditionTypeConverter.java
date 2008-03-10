package org.openuss.web.seminarpool;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;

import org.apache.shale.tiger.register.FacesConverter;
import org.openuss.framework.web.jsf.controller.BaseBean;
import org.openuss.groups.GroupAccessType;
import org.openuss.seminarpool.ConditionType;
import org.openuss.web.Constants;

@FacesConverter(value = "seminarpoolConditionTypeConverter")
public class SeminarpoolConditionTypeConverter extends BaseBean implements Converter  {

	public Object getAsObject(FacesContext context, UIComponent component, String value) throws ConverterException {
		if (value instanceof String) {
			int literal = Integer.parseInt(value);
			return ConditionType.fromInteger(literal);
		}
/*		if(value.equals(#{msg['seminarpool_condition_type_prefix_1']}))
			literal = 1;
		else if(value.equals("#{msg['seminarpool_condition_type_prefix_2']}"))
			literal = 2;
		else if(value.equals("#{msg['seminarpool_condition_type_prefix_3']}"))
			literal = 3;
		else return null;	
*/		else return null;
	}

	public String getAsString(FacesContext context, UIComponent component, Object value) throws ConverterException {
		if (value instanceof ConditionType){
//			return i18n(Constants.SEMINARPOOL_CONDITION_PREFIX + ((ConditionType)value).getValue());
			ConditionType seminarpoolConditionType = (ConditionType) value;
			return seminarpoolConditionType.getValue().toString(); 
		} else if (value instanceof Integer) {
			return String.valueOf(value);
		} else if (value instanceof String) {
			return (String) value;
		}
		return null;   
	}
}