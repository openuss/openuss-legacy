package org.openuss.security.acegi.ldap;

import org.acegisecurity.providers.AuthenticationProvider;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.MessageSourceAware;

public interface ConfigurableLdapAuthenticationProvider extends AuthenticationProvider, MessageSourceAware, InitializingBean {
	
	public void reconfigure();

}
