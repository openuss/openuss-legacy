package org.openuss.web.lecture;

import org.apache.log4j.Logger;
import org.apache.shale.tiger.managed.Property;
import org.apache.shale.tiger.view.Preprocess;
import org.apache.shale.tiger.view.Prerender;
import org.openuss.lecture.DepartmentInfo;
import org.openuss.lecture.DepartmentService;
import org.openuss.lecture.LectureException;
import org.openuss.lecture.UniversityService;
import org.openuss.security.ldap.AuthenticationDomainInfo;
import org.openuss.security.ldap.LdapConfigurationService;
import org.openuss.web.BasePage;
import org.openuss.web.Constants;

/**
 * Abstract LdapDomain Page
 * 
 * @author Christian Grelle
 * @author Peter Schuh

 */
public abstract class AbstractLdapDomainPage extends BasePage {

	private static final Logger logger = Logger.getLogger(AbstractLdapDomainPage.class);

	@Property(value = "#{authenticationDomainInfo}")
	protected AuthenticationDomainInfo authenticationDomainInfo;

	@Property(value = "#{ldapConfigurationService}")
	protected LdapConfigurationService ldapConfigurationService;

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
				//authenticationDomainInfo = LdapConfigurationService.getDomain(authenticationDomainInfo.getId());
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
			// TODO: CHRISTIAN: WRONG MESSAGE!!!
			addError(i18n("message_error_no_department_selected"));
			redirect(Constants.DESKTOP);
		}
	}

	private void refreshAuthenticationDomain() {
		if (authenticationDomainInfo != null) {
			if (authenticationDomainInfo.getId() != null) {
				//authenticationDomainInfo = departmentService.getDomain(departmentInfo.getId());
				setSessionBean(Constants.AUTHENTICATIONDOMAIN_INFO, authenticationDomainInfo);
			}
		}
	}

	public Boolean getBookmarked() {
		try {
			return desktopService2.isDepartmentBookmarked(authenticationDomainInfo.getId(), user.getId());
		} catch (Exception e) {

		}

		return false;
	}

	public AuthenticationDomainInfo getAuthenticationDomainInfo() {
		return authenticationDomainInfo;
	}

	public void setAuthenticationDomainInfo(AuthenticationDomainInfo authenticationDomainInfo) {
		this.authenticationDomainInfo = authenticationDomainInfo;
	}

	
	public LdapConfigurationService getLdapConfigurationService() {
		return ldapConfigurationService;
	}

	public void setLdapConfigurationService(
			LdapConfigurationService ldapConfigurationService) {
		this.ldapConfigurationService = ldapConfigurationService;
	}
}
