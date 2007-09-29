package org.openuss.web.lecture.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;

import org.apache.shale.tiger.register.FacesConverter;
import org.openuss.framework.web.jsf.controller.BaseBean;
import org.openuss.lecture.AccessType;

@FacesConverter(value = "accessTypeLabelConverter")
public class AccessTypeLabelConverter extends BaseBean implements Converter  {

	public Object getAsObject(FacesContext context, UIComponent component, String value) throws ConverterException {
		return null;
	}

	public String getAsString(FacesContext context, UIComponent component, Object value) throws ConverterException {
		if (value instanceof AccessType){
			AccessType accessType = (AccessType) value;
			if (accessType == AccessType.ANONYMOUS) {
				return i18n("course_options_access_anonymous_short");
			} else if (accessType == AccessType.OPEN) {
				return i18n("course_options_access_open_short");
			} else if (accessType == AccessType.CLOSED) {
				return i18n("course_options_access_closed_short");
			} else if (accessType == AccessType.PASSWORD) {
				return i18n("course_options_access_password_short");
			} else if (accessType == AccessType.APPLICATION) {
				return i18n("course_options_access_application_short");
			}
			return accessType.getValue().toString();
		}  else if (value instanceof Integer) {
			return String.valueOf(value);
		} else if (value instanceof String) {
			return (String) value;
		}
		return null;
	}
}
