package org.openuss.web.system.ldap;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Property;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.Preprocess;
import org.apache.shale.tiger.view.Prerender;
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
public class LdapServerRegistrationController extends AbstractLdapPage {

	@Property(value = "#{ldapServerInfo}")
	protected LdapServerInfo ldapServerInfo;

	/**
	 * Refreshing attributeMapping VO
	 * 
	 * @throws Exception
	 */
	@Preprocess
	public void preprocess() throws Exception {
		super.preprocess();
		logger.debug("preprocess - refreshing ldapServerInfo session object");
		if (ldapServerInfo != null) {
			if (ldapServerInfo.getId() != null) {
				ldapServerInfo = ldapConfigurationService.getLdapServerById(ldapServerInfo.getId());
			} else {
				ldapServerInfo = (LdapServerInfo) getSessionBean(Constants.LDAPSERVER_INFO);				
			}
		}
		setSessionBean(Constants.LDAPSERVER_INFO, ldapServerInfo);
	}

	@Prerender
	public void prerender() {
		logger.debug("prerender - refreshing ldapServerInfo session object");
		refreshLdapServerInfo();
		if (ldapServerInfo == null || ldapServerInfo.getId() == null) {
			addError(i18n("message_ldap_ldapserver_no_ldapserver_selected"));
			redirect(Constants.LDAP_SERVER_PAGE);
		}
	}

	private void refreshLdapServerInfo() {
		if (ldapServerInfo != null) {
			if (ldapServerInfo.getId() != null) {
				ldapServerInfo = ldapConfigurationService.getLdapServerById(ldapServerInfo.getId());
				setSessionBean(Constants.LDAPSERVER_INFO, ldapServerInfo);
			}
		}
	}

	public LdapServerInfo getLdapServerInfoInfo() {
		return ldapServerInfo;
	}

	public void setLdapServerInfo(LdapServerInfo ldapServerInfo) {
		this.ldapServerInfo = ldapServerInfo;
	}
	
	public String start() {
		logger.debug("start registration process");
		ldapServerInfo = new LdapServerInfo();
		setSessionBean(Constants.LDAPSERVER_INFO, ldapServerInfo);
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

	public void processValueChangeUserDnPattern(ValueChangeEvent valueChangeEvent) {
		List<Long> selectedIds = (ArrayList<Long>) valueChangeEvent.getNewValue();
		ldapServerInfo.setUserDnPatternIds(selectedIds);
	}

	public void processValueChangeAuthenticationDomain(ValueChangeEvent valueChangeEvent) {
		Long selectedId = (Long) valueChangeEvent.getNewValue();
		ldapServerInfo.setAuthenticationDomainId(selectedId);
	}

	public List<SelectItem> getAllAuthenticationDomains() {
		List<SelectItem> domainItems = new ArrayList<SelectItem>();
		List<AuthenticationDomainInfo> domains = ldapConfigurationService.getAllDomains();
		for (AuthenticationDomainInfo domain : domains) {
			domainItems.add(new SelectItem(domain.getId(), domain.getName()));
		}
		return domainItems;
	}

	public List<SelectItem> getAllUserDnPatterns() {
		List<SelectItem> userDnPatternItems = new ArrayList<SelectItem>();
		List<UserDnPatternInfo> userDnPatterns = ldapConfigurationService.getAllUserDnPatterns();
		for (UserDnPatternInfo userDnPattern : userDnPatterns) {
			userDnPatternItems.add(new SelectItem(userDnPattern.getId(), userDnPattern.getName()));
		}
		return userDnPatternItems;
	}
	
	public Long[] getUserDnPatternIds() {
		return (Long[]) ldapServerInfo.getUserDnPatternIds().toArray(new Long[ldapServerInfo.getUserDnPatternIds().size()]);
	}
	
	public void setUserDnPatternIds(Long[] ids) {
		ldapServerInfo.setUserDnPatternIds(Arrays.asList(ids));
	}

	public String register() {
		ldapConfigurationService.createLdapServer(ldapServerInfo);
		return Constants.LDAP_SERVER_PAGE;
	}

	public String save() {
		ldapConfigurationService.saveLdapServer(ldapServerInfo);
		return Constants.LDAP_SERVER_PAGE;
	}

}
