package org.openuss.web.lecture;

import java.util.ArrayList;
import java.util.List;

import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

import org.apache.log4j.Logger;
import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.View;
import org.openuss.security.ldap.AuthenticationDomainInfo;
import org.openuss.security.ldap.LdapServerInfo;
import org.openuss.security.ldap.LdapServerType;
import org.openuss.security.ldap.UserDnPatternInfo;
import org.openuss.web.Constants;

/**
 * Backing bean for the ldap_server registration. Is responsible starting the
 * wizard, binding the values and registration the LdapServer.
 *
 * @author Jürgen de Braaf
 * @author Christian Grelle
 * @author Peter Schuh 
 */

@Bean(name = Constants.LDAP_SERVER_REGISTRATION_CONTROLLER, scope = Scope.REQUEST)
@View
public class LdapServerRegistrationController extends AbstractLdapServerPage {

	
	private static final Logger logger = Logger.getLogger(LdapServerRegistrationController.class);
	
	public String start() {
		logger.debug("start registration process");
		
		ldapServerInfo = new LdapServerInfo();		
		setSessionBean(Constants.SERVER_INFO, ldapServerInfo);
		
		return Constants.LDAP_SERVER_REGISTRATION_STEP1_PAGE;
	}

	
	/**
	 * Value Change Listener
	 * 
	 * @param event
	 */
	public void processValueChangeLdapServerType(ValueChangeEvent event) {
		Object ldapServerType = event.getNewValue();
		ldapServerInfo.setLdapServerType((LdapServerType) ldapServerType);
	}
	
	
	public List<SelectItem> getLdapServerTypes() {
		List<SelectItem> items = new ArrayList<SelectItem>();
		items.add(new SelectItem(LdapServerType.ACTIVE_DIRECTORY, i18n("ldap_server_types_ms_active_directory")));
		items.add(new SelectItem(LdapServerType.OTHER, i18n("ldap_server_types_other")));		
		return items;
	}
	
	
	@SuppressWarnings( { "unchecked" })
	public void processValueChangeUserDnPattern(ValueChangeEvent valueChangeEvent) {
		List<Long> selectedIds = (ArrayList<Long>) valueChangeEvent.getNewValue();
		ldapServerInfo.setUserDnPatternIds(selectedIds);
	}	
	
	public void processValueChangeAuthenticationDomain(ValueChangeEvent valueChangeEvent) {
		Long selectedId = (Long) valueChangeEvent.getNewValue();
		ldapServerInfo.setAuthenticationDomainId(selectedId);
	}	
	
	@SuppressWarnings( { "unchecked" })
	public List<SelectItem> getAllAuthenticationDomains() {

		List<SelectItem> domainItems = new ArrayList<SelectItem>();

		List<AuthenticationDomainInfo> domains = ldapConfigurationService.getAllDomains();
		
		for (AuthenticationDomainInfo domain : domains) {
				domainItems.add(new SelectItem(domain.getId(), domain.getName()));
			}
	
		return domainItems;
	}

	@SuppressWarnings( { "unchecked" })
	public List<SelectItem> getAllUserDnPatterns() {

		List<SelectItem> userDnPatternItems = new ArrayList<SelectItem>();
		List<UserDnPatternInfo> userDnPatterns = ldapConfigurationService.getAllUserDnPatterns();
		
		for (UserDnPatternInfo userDnPattern : userDnPatterns) {
			userDnPatternItems.add(new SelectItem(userDnPattern.getId(), userDnPattern.getName()));
			}
	
		return userDnPatternItems;
	}	
	
	public String register() {
//		create LdapServer
		ldapConfigurationService.createLdapServer(ldapServerInfo);
		
		return Constants.LDAP_SERVER_PAGE;
	}	
	
	public String save() {		
//		update LdapServer
		ldapConfigurationService.saveLdapServer(ldapServerInfo);
		
		return Constants.LDAP_SERVER_PAGE;
	}

}
