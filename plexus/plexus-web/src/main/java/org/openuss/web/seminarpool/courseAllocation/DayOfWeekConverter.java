package org.openuss.web.seminarpool.courseAllocation;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;

import org.apache.shale.tiger.register.FacesConverter;
import org.openuss.framework.web.jsf.controller.BaseBean;
import org.openuss.seminarpool.DayOfWeek;
import org.openuss.web.Constants;

@FacesConverter(value = "dayOfWeekConverter")
public class DayOfWeekConverter extends BaseBean implements Converter{


	public Object getAsObject(FacesContext context, UIComponent component, String value) throws ConverterException {
		return null;
	}

	public String getAsString(FacesContext context, UIComponent component, Object value) throws ConverterException {	
			return i18n(Constants.SEMINARPOOL_DAY_OF_WEEK_PREFIX + ((DayOfWeek)value).getValue());
	}
	
}
