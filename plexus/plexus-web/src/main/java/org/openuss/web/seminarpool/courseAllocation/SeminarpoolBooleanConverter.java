package org.openuss.web.seminarpool.courseAllocation;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;

import org.apache.shale.tiger.register.FacesConverter;
import org.openuss.framework.web.jsf.controller.BaseBean;
import org.openuss.web.Constants;

@FacesConverter(value = "seminarpoolBooleanConverter")
public class SeminarpoolBooleanConverter extends BaseBean  implements Converter {

	public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2) throws ConverterException {												
		return null;
	}

	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) throws ConverterException {											
		return i18n(Constants.SEMINARPOOL_PREFIX + arg2);
	}

}
