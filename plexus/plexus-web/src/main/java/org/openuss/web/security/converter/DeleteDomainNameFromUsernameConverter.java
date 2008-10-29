package org.openuss.web.security.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import org.apache.shale.tiger.register.FacesConverter;
import org.openuss.security.SecurityDomainUtility;

/**
 * Deletes domainName part from a username within a central users profile
 * 
 * @author Peter Schuh
 * 
 */
@FacesConverter(value = "deleteDomainNameFromUsernameConverter")
public class DeleteDomainNameFromUsernameConverter implements Converter {

	public Object getAsObject(FacesContext context, UIComponent component, String username) {
		return SecurityDomainUtility.extractUsername(username);
	}

	public String getAsString(FacesContext context, UIComponent component, Object obj) {
		if (obj instanceof String) {
			return SecurityDomainUtility.extractUsername((String) obj);
		} else {
			return null;
		}
	}
}