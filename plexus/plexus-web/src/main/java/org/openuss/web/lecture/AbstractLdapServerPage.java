package org.openuss.web.lecture;

import org.apache.log4j.Logger;
import org.apache.shale.tiger.managed.Property;
import org.apache.shale.tiger.view.Preprocess;
import org.apache.shale.tiger.view.Prerender;
import org.openuss.lecture.LectureException;
import org.openuss.security.ldap.LdapConfigurationService;
import org.openuss.security.ldap.LdapServerInfo;
import org.openuss.web.BasePage;
import org.openuss.web.Constants;

/**
 * Abstract Department Page
 * 
 */
public abstract class AbstractLdapServerPage extends BasePage {

	private static final Logger logger = Logger.getLogger(AbstractLdapUserDnPatternPage.class);
	
	@Property(value = "#{ldapServerInfo}")
	protected LdapServerInfo ldapServerInfo;

	@Property(value = "#{ldapConfigurationService}")
	protected LdapConfigurationService ldapConfigurationService;


	/**
	 * Refreshing department VO
	 * 
	 * @throws Exception
	 */
	@Preprocess
	public void preprocess() throws Exception {
		super.preprocess();
		logger.debug("preprocess - refreshing department session object");
		if (ldapServerInfo != null) {
			if (ldapServerInfo.getId() != null) {
				//authenticationDomainInfo = LdapConfigurationService.findDepartment(authenticationDomainInfo.getId());
			} else {
				ldapServerInfo = (LdapServerInfo) getSessionBean(Constants.SERVER_INFO);
			}
		}

		setSessionBean(Constants.SERVER_INFO, ldapServerInfo);
	}

	@Prerender
	public void prerender() throws LectureException {
		logger.debug("prerender - refreshing department session object");
		refreshLdapServer();
		if (ldapServerInfo == null || ldapServerInfo.getId() == null) {
			addError(i18n("message_error_no_department_selected"));
			redirect(Constants.DESKTOP);
		}
	}

	private void refreshLdapServer() {
		if (ldapServerInfo != null) {
			if (ldapServerInfo.getId() != null) {
				//authenticationDomainInfo = departmentService.findDepartment(departmentInfo.getId());
				setSessionBean(Constants.SERVER_INFO, ldapServerInfo);
			}
		}
	}



	public LdapServerInfo getLdapServerInfo() {
		return ldapServerInfo;
	}

	public void setLdapServerInfo(LdapServerInfo ldapServerInfo) {
		this.ldapServerInfo = ldapServerInfo;
	}

	
	public LdapConfigurationService getLdapConfigurationService() {
		return ldapConfigurationService;
	}

	public void setLdapConfigurationService(
			LdapConfigurationService ldapConfigurationService) {
		this.ldapConfigurationService = ldapConfigurationService;
	}
	
}
