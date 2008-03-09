package org.openuss.framework.jsfcontrols.converter;

import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.convert.ConverterException;

import org.apache.log4j.Logger;

/**
 * Workaround for shale issue https://issues.apache.org/struts/browse/SHALE-406 
 * @author Ingo Dueppe
 */
public class ShaleLongConverter extends javax.faces.convert.LongConverter {

	private static final Logger logger = Logger.getLogger(ShaleLongConverter.class);

	public Object getAsObject(FacesContext context, UIComponent component, String value) throws ConverterException {
		if (component == null) {
			logger.warn("using workaround for issue SHALE-406 and create dummy viewroot");
			component = new UIViewRoot();
		}
		return super.getAsObject(context, component, value);
	}

	public String getAsString(FacesContext context, UIComponent component, Object value) throws ConverterException {
		if (component == null) {
			logger.warn("using workaround for issue SHALE-406 and create dummy viewroot");
			component = new UIViewRoot();
		}
		return super.getAsString(context, component, value);
	}

}
