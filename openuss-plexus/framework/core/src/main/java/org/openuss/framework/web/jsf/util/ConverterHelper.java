package org.openuss.framework.web.jsf.util;

import org.apache.log4j.Logger;

import javax.faces.FacesException;
import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;

import org.openuss.framework.web.jsf.controller.BaseBean;

/**
 * Inspired by
 *  @see org.apache.shale.util.ConverterHelper
 *  
 * @author Ingo Dueppe
 *
 */
public class ConverterHelper extends BaseBean {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(ConverterHelper.class);


    /**
     * <p>Use the registered by-type <code>Converter</code> to convert the
     * specified String value to a corresponding Object value.</p>
     *
     * @param context <code>FacesContext</code> for the current request
     * @param type Type to which this value should be converted
     *  (used to select the appropriate by-type converter)
     * @param value Value to be converted
     *
     * @exception ConverterException if a conversion error occurs
     */
	public Object asObject(FacesContext context, Class<?> type, String value) {

        if (String.class == type) {
            return value;
        }
        UIComponent component = getComponent(context);
        return converter(context, type).getAsObject(context, component, value);

    }



    /**
     * <p>Use the registered by-type <code>Converter</code> to convert the
     * specified Object value to a corresponding String value.</p>
     *
     * @param context <code>FacesContext</code> for the current request
     * @param type Type from which this value should be converted
     *  (used to select the appropriate by-type converter)
     * @param value Value to be converted
     *
     * @exception ConverterException if a conversion error occurs
     */
    public String asString(FacesContext context, Class<?> type, Object value) {
        if (value == null) {
            return null;
        } else if ((String.class == type) && (value instanceof String)) {
            return (String) value;
        }
        return converter(context, type).getAsString(context, getComponent(context), value);

    }


    // --------------------------------------------------------- Private Methods

    /**
     * Checks whether viewRoot is already set or not.
     * If it is not set a dummy component will be created to support javax.faces.convert.Converter specification.
     */
    private UIComponent getComponent(FacesContext context) {
    	UIComponent component = context.getViewRoot();
    	if (component == null) {
    		logger.warn("viewroot not set <------------------------");
    		component = new UIViewRoot();
    	}
    	return component;
    }

    /**
     * <p>Return an appropriate <code>Converter</code> instance for the
     * specified type.</p>
     *
     * @param context <code>FacesContext</code> for the current request
     * @param type Type for which to retrieve a <code>Converter</code>
     *
     * @exception ConverterException if no <code>Converter</code> has been
     *  registered for the specified type
     */
    private Converter converter(FacesContext context, Class<?> type) {
        if (type == null) {
            throw new ConverterException(i18n("converter_type_not_defined"));
        }

        Converter converter = null;
        try {
            converter = context.getApplication().createConverter(type);
        } catch (FacesException e) {
            throw new ConverterException(e);
        }
        if (converter == null) {
            throw new ConverterException(i18n("converter_type_not_defined"));
        }
        return converter;

    }

}
