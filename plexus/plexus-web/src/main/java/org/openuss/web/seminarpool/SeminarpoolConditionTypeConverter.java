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
		if (value.equals(i18n(Constants.SEMINARPOOL_CONDITION_PREFIX + 1))) {
			return ConditionType.TEXTFIELD;
		} else if (value.equals(i18n(Constants.SEMINARPOOL_CONDITION_PREFIX + 2))) {
			return ConditionType.TEXTAREA;			
		} else if (value.equals(i18n(Constants.SEMINARPOOL_CONDITION_PREFIX + 3))) {
			return ConditionType.CHECKBOX;
		}	
		return null;
	}

	public String getAsString(FacesContext context, UIComponent component, Object value) throws ConverterException {
		if (value instanceof ConditionType){
			return i18n(Constants.SEMINARPOOL_CONDITION_PREFIX + ((ConditionType)value).getValue());
		}
		return null;   
	}
}