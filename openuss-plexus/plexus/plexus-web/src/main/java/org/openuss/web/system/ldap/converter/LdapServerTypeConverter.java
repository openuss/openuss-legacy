package org.openuss.web.system.ldap.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;

import org.apache.shale.tiger.register.FacesConverter;
import org.openuss.security.ldap.LdapServerType;

/**
 * @author Juergen de Braaf
 */
@FacesConverter(value = "ldapServerTypeConverter")
public class LdapServerTypeConverter implements Converter {

	public Object getAsObject(FacesContext context, UIComponent component, String value) throws ConverterException {
		int literal = Integer.parseInt(value);
		return LdapServerType.fromInteger(literal);
	}

	public String getAsString(FacesContext context, UIComponent component, Object value) throws ConverterException {
		
		if (value instanceof LdapServerType){
			LdapServerType ldapServerType = (LdapServerType) value;
			return ldapServerType.getValue().toString();
		}  else if (value instanceof Integer) {
			return String.valueOf(value);
		} else if (value instanceof String) {
			return (String) value;
		}
		return null;
	}
}
