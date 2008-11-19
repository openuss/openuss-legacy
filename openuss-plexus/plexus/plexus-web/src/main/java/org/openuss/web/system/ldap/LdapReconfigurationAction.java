package org.openuss.web.system.ldap;

import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Property;
import org.apache.shale.tiger.managed.Scope;
import org.openuss.framework.web.jsf.controller.BaseBean;
import org.openuss.security.ldap.LdapConfigurationNotifyService;
import org.openuss.web.Constants;

/**
 * Perform Ldap reconfiguration action.
 * 
 * @author Ingo Dueppe
 *
 */
@Bean(name="ldapReconfigurationAction", scope = Scope.REQUEST)
public class LdapReconfigurationAction extends BaseBean {

	@Property(value="#{ldapConfigurationNotifyService}")
	private LdapConfigurationNotifyService service;
	
	public String perform() {
		service.reconfigure();
		addMessage("Reconfiguration initialized...");
		return Constants.SUCCESS;
	}
	
	public LdapConfigurationNotifyService getService() {
		return service;
	}

	public void setService(LdapConfigurationNotifyService service) {
		this.service = service;
	}

}
