package org.openuss.web.security.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import org.apache.shale.tiger.register.FacesConverter;
import org.openuss.security.SecurityConstants;

/**
 * Deletes domainName part from a username within a central users profile
 * 
 * @author Peter Schuh
 *
 */
@FacesConverter(value = "deleteDomainNameFromUsernameConverter")
public class DeleteDomainNameFromUsernameConverter implements Converter {

    public DeleteDomainNameFromUsernameConverter() {
        super();
    }
 
    public Object getAsObject(FacesContext context, UIComponent component, String str) {
        return str!= null ? str.substring(str.lastIndexOf(SecurityConstants.USERNAME_DOMAIN_DELIMITER)+1) : str;
    }
 
    public String getAsString(FacesContext context, UIComponent component, Object obj) {
    	if (obj instanceof String) {
    		String str = (String)obj;
    		return str.substring(str.lastIndexOf(SecurityConstants.USERNAME_DOMAIN_DELIMITER)+1);
    	}
        return null;
    }
}