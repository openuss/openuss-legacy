package org.openuss.web.lecture;

import org.apache.log4j.Logger;
import org.apache.shale.tiger.managed.Property;
import org.apache.shale.tiger.view.Preprocess;
import org.apache.shale.tiger.view.Prerender;
import org.openuss.lecture.LectureException;
import org.openuss.lecture.UniversityService;
import org.openuss.security.ldap.AttributeMappingInfo;
import org.openuss.security.ldap.AuthenticationDomainInfo;
import org.openuss.security.ldap.LdapConfigurationService;
import org.openuss.web.BasePage;
import org.openuss.web.Constants;

/**
 * Abstract Department Page
 * 
 * @author Kai Stettner
 * @author Tianyu Wang
 * @author Weijun Chen
 */
public abstract class AbstractLdapAttributeMappingPage extends BasePage {

	private static final Logger logger = Logger.getLogger(AbstractLdapAttributeMappingPage.class);
	
	@Property(value = "#{attributeMappingInfo}")
	protected AttributeMappingInfo attributeMappingInfo;

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
		if (attributeMappingInfo != null) {
			if (attributeMappingInfo.getId() != null) {
				//authenticationDomainInfo = LdapConfigurationService.findDepartment(authenticationDomainInfo.getId());
			} else {
				attributeMappingInfo = (AttributeMappingInfo) getSessionBean(Constants.ATTRIBUTEMAPPING_INFO);
			}
		}

		setSessionBean(Constants.ATTRIBUTEMAPPING_INFO, attributeMappingInfo);
	}

	@Prerender
	public void prerender() throws LectureException {
		logger.debug("prerender - refreshing department session object");
		refreshAuthenticationDomain();
		if (attributeMappingInfo == null || attributeMappingInfo.getId() == null) {
			addError(i18n("message_error_no_department_selected"));
			redirect(Constants.DESKTOP);
		}
	}

	private void refreshAuthenticationDomain() {
		if (attributeMappingInfo != null) {
			if (attributeMappingInfo.getId() != null) {
				//authenticationDomainInfo = departmentService.findDepartment(departmentInfo.getId());
				setSessionBean(Constants.ATTRIBUTEMAPPING_INFO, attributeMappingInfo);
			}
		}
	}

	public Boolean getBookmarked() {
		try {
			return desktopService2.isDepartmentBookmarked(attributeMappingInfo.getId(), user.getId());
		} catch (Exception e) {

		}

		return false;
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
