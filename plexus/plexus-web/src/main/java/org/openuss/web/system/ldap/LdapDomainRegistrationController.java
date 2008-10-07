package org.openuss.web.system.ldap;

import java.util.ArrayList;
import java.util.List;

import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Property;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.Preprocess;
import org.apache.shale.tiger.view.Prerender;
import org.apache.shale.tiger.view.View;
import org.openuss.lecture.LectureException;
import org.openuss.security.ldap.AttributeMappingInfo;
import org.openuss.security.ldap.AuthenticationDomainInfo;
import org.openuss.web.Constants;

/**
 * Backing bean for the ldap_domain maintenance. 
 * 
 * @author Peter Schuh
 * @author Christian Grelle
 * @author Ingo Dueppe
 * 
 */

@Bean(name = Constants.LDAP_DOMAIN_REGISTRATION_CONTROLLER, scope = Scope.REQUEST)
@View
public class LdapDomainRegistrationController extends AbstractLdapPage {

	@Property(value = "#{authenticationDomainInfo}")
	private AuthenticationDomainInfo authenticationDomainInfo;

	/**
	 * Refreshing authenticationDomain VO
	 * 
	 * @throws Exception
	 */
	@Preprocess
	public void preprocess() throws Exception {
		super.preprocess();
		logger.debug("preprocess - refreshing authenticationDomain session object");
		if (authenticationDomainInfo != null) {
			if (authenticationDomainInfo.getId() != null) {
				authenticationDomainInfo = ldapConfigurationService.getDomainById(authenticationDomainInfo.getId());
			} else {
				authenticationDomainInfo = (AuthenticationDomainInfo) getSessionBean(Constants.AUTHENTICATIONDOMAIN_INFO);
			}
		}

		setSessionBean(Constants.AUTHENTICATIONDOMAIN_INFO, authenticationDomainInfo);
	}

	@Prerender
	public void prerender() throws LectureException {
		logger.debug("prerender - refreshing authenticationDomain session object");
		refreshAuthenticationDomain();
		if (authenticationDomainInfo == null || authenticationDomainInfo.getId() == null) {	
			addError(i18n("message_ldap_authenticationdomain_no_authenticationdomain_selected"));
			redirect(Constants.DESKTOP);
		}
	}

	private void refreshAuthenticationDomain() {
		if (authenticationDomainInfo != null) {
			if (authenticationDomainInfo.getId() != null) {
				authenticationDomainInfo = ldapConfigurationService.getDomainById(authenticationDomainInfo.getId());
				setSessionBean(Constants.AUTHENTICATIONDOMAIN_INFO, authenticationDomainInfo);
			}
		}
	}

	public AuthenticationDomainInfo getAuthenticationDomainInfo() {
		return authenticationDomainInfo;
	}

	public void setAuthenticationDomainInfo(AuthenticationDomainInfo authenticationDomainInfo) {
		this.authenticationDomainInfo = authenticationDomainInfo;
	}

	
	public String start() {
		logger.debug("start registration process");
		
		authenticationDomainInfo = new AuthenticationDomainInfo();
		setSessionBean(Constants.AUTHENTICATIONDOMAIN_INFO, authenticationDomainInfo);
		
		return Constants.LDAP_DOMAIN_EDIT_PAGE;
	}


	@SuppressWarnings("unchecked")
	public List<SelectItem> getAllAttributeMappings() {
		List<SelectItem> attributeMappingItems = new ArrayList<SelectItem>();
		List<AttributeMappingInfo> attributeMappings = ldapConfigurationService.getAllAttributeMappings();
		for (AttributeMappingInfo attributeMapping : attributeMappings) {
				attributeMappingItems.add(new SelectItem(attributeMapping.getId(), attributeMapping.getMappingName()));
		}
		return attributeMappingItems;
	}
	
	public void processValueChange(ValueChangeEvent valueChangeEvent) {
		Long attributeMappingId = (Long) valueChangeEvent.getNewValue();
		authenticationDomainInfo.setAttributeMappingId(attributeMappingId);
	}				
	
	public String register() {
		ldapConfigurationService.createDomain(authenticationDomainInfo);
		return Constants.LDAP_DOMAIN_PAGE;
	}
	
	public String save() {
		ldapConfigurationService.saveDomain(authenticationDomainInfo);
		return Constants.LDAP_DOMAIN_PAGE;
	}	

}
