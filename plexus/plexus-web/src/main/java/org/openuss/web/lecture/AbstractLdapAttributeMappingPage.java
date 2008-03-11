package org.openuss.web.lecture;

import org.apache.log4j.Logger;
import org.apache.shale.tiger.managed.Property;
import org.apache.shale.tiger.view.Preprocess;
import org.apache.shale.tiger.view.Prerender;
import org.openuss.security.ldap.AttributeMappingInfo;
import org.openuss.security.ldap.LdapConfigurationService;
import org.openuss.web.BasePage;
import org.openuss.web.Constants;

/**
 * Abstract AttribtuteMapping Page
 * 
 */
public abstract class AbstractLdapAttributeMappingPage extends BasePage {

	private static final Logger logger = Logger.getLogger(AbstractLdapAttributeMappingPage.class);
	
	@Property(value = "#{attributeMappingInfo}")
	protected AttributeMappingInfo attributeMappingInfo;

	@Property(value = "#{ldapConfigurationService}")
	protected LdapConfigurationService ldapConfigurationService;


	/**
	 * Refreshing attributeMapping VO
	 * 
	 * @throws Exception
	 */
	@Preprocess
	public void preprocess() throws Exception {
		super.preprocess();
		logger.debug("preprocess - refreshing attributeMapping session object");
		if (attributeMappingInfo != null) {
			if (attributeMappingInfo.getId() != null) {
				attributeMappingInfo = ldapConfigurationService.getAttributeMappingById(attributeMappingInfo.getId());
			} else {
				attributeMappingInfo = (AttributeMappingInfo) getSessionBean(Constants.ATTRIBUTEMAPPING_INFO);				
			}
		}
		setSessionBean(Constants.ATTRIBUTEMAPPING_INFO, attributeMappingInfo);
	}

	@Prerender
	public void prerender() {
		logger.debug("prerender - refreshing attributeMapping session object");
		refreshAttributeMapping();
		if (attributeMappingInfo == null || attributeMappingInfo.getId() == null) {
			addError(i18n("message_ldap_attributemapping_no_attributemapping_selected"));
			redirect(Constants.DESKTOP);
		}
	}

	private void refreshAttributeMapping() {
		if (attributeMappingInfo != null) {
			if (attributeMappingInfo.getId() != null) {
				attributeMappingInfo = ldapConfigurationService.getAttributeMappingById(attributeMappingInfo.getId());
				setSessionBean(Constants.ATTRIBUTEMAPPING_INFO, attributeMappingInfo);
			}
		}
	}

	public AttributeMappingInfo getAttributeMappingInfo() {
		return attributeMappingInfo;
	}

	public void setAttributeMappingInfo(AttributeMappingInfo attributeMappingInfo) {
		this.attributeMappingInfo = attributeMappingInfo;
	}

	
	public LdapConfigurationService getLdapConfigurationService() {
		return ldapConfigurationService;
	}

	public void setLdapConfigurationService(
			LdapConfigurationService ldapConfigurationService) {
		this.ldapConfigurationService = ldapConfigurationService;
	}
	
}