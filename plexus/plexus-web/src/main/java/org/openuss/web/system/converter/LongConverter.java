package org.openuss.web.system.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import org.apache.shale.tiger.register.FacesConverter;


/**
 * Converter for Long values
 * 
 * @author Peter Schuh
 *
 */
@FacesConverter(value = "longConverter")
public class LongConverter implements Converter {

	    public LongConverter() {
	        super();
	    }
	 
	    public Object getAsObject(FacesContext context, UIComponent component, String str) {
	        return str!= null ? Long.valueOf(str) : null;
	    }
	 
	    public String getAsString(FacesContext context, UIComponent component, Object obj) {
	    	String str = String.valueOf(obj); 
	        return str;
	    }

}
