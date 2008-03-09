package org.openuss.web.lecture;

import org.apache.log4j.Logger;
import org.apache.shale.tiger.managed.Property;
import org.apache.shale.tiger.view.Preprocess;
import org.apache.shale.tiger.view.Prerender;
import org.openuss.lecture.LectureException;
import org.openuss.security.ldap.LdapConfigurationService;
import org.openuss.security.ldap.RoleAttributeKeyInfo;
import org.openuss.security.ldap.RoleAttributeKeySetInfo;
import org.openuss.web.BasePage;
import org.openuss.web.Constants;

/**
 * Abstract Department Page
 * 
 */
public abstract class AbstractLdapRoleAttributeKeyPage extends BasePage {

	private static final Logger logger = Logger.getLogger(AbstractLdapRoleAttributeKeyPage.class);
	
	@Property(value = "#{roleAttributeKeyInfo}")
	protected RoleAttributeKeyInfo roleAttributeKeyInfo;

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
		if (roleAttributeKeyInfo != null) {
			if (roleAttributeKeyInfo.getId() != null) {
				//authenticationDomainInfo = LdapConfigurationService.findDepartment(authenticationDomainInfo.getId());
			} else {
				roleAttributeKeyInfo = (RoleAttributeKeyInfo) getSessionBean(Constants.ROLEATTRIBUTEKEY_INFO);
			}
		}

		setSessionBean(Constants.ROLEATTRIBUTEKEY_INFO, roleAttributeKeyInfo);
	}

	@Prerender
	public void prerender() throws LectureException {
		logger.debug("prerender - refreshing department session object");
		refreshRoleAttributeKeySet();
		if (roleAttributeKeyInfo == null || roleAttributeKeyInfo.getId() == null) {
			addError(i18n("message_error_no_department_selected"));
			redirect(Constants.DESKTOP);
		}
	}

	private void refreshRoleAttributeKeySet() {
		if (roleAttributeKeyInfo != null) {
			if (roleAttributeKeyInfo.getId() != null) {
				//authenticationDomainInfo = departmentService.findDepartment(departmentInfo.getId());
				setSessionBean(Constants.ROLEATTRIBUTEKEY_INFO, roleAttributeKeyInfo);
			}
		}
	}

	public Boolean getBookmarked() {
		try {
			return desktopService2.isDepartmentBookmarked(roleAttributeKeyInfo.getId(), user.getId());
		} catch (Exception e) {

		}

		return false;
	}

	public RoleAttributeKeyInfo getRoleAttributeKeyInfo() {
		return roleAttributeKeyInfo;
	}

	public void setRoleAttributeKeyInfo(RoleAttributeKeyInfo roleAttributeKeyInfo) {
		this.roleAttributeKeyInfo = roleAttributeKeyInfo;
	}

	
	public LdapConfigurationService getLdapConfigurationService() {
		return ldapConfigurationService;
	}

	public void setLdapConfigurationService(
			LdapConfigurationService ldapConfigurationService) {
		this.ldapConfigurationService = ldapConfigurationService;
	}
	
}
