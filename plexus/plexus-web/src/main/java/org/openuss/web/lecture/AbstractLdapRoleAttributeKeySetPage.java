package org.openuss.web.lecture;

import org.apache.log4j.Logger;
import org.apache.shale.tiger.managed.Property;
import org.apache.shale.tiger.view.Preprocess;
import org.apache.shale.tiger.view.Prerender;
import org.openuss.lecture.LectureException;
import org.openuss.security.ldap.LdapConfigurationService;
import org.openuss.security.ldap.RoleAttributeKeySetInfo;
import org.openuss.web.BasePage;
import org.openuss.web.Constants;

/**
 * Abstract Department Page
 * 
 * @author Kai Stettner
 * @author Tianyu Wang
 * @author Weijun Chen
 */
public abstract class AbstractLdapRoleAttributeKeySetPage extends BasePage {

	private static final Logger logger = Logger.getLogger(AbstractLdapRoleAttributeKeySetPage.class);
	
	@Property(value = "#{roleAttributeKeySetInfo}")
	protected RoleAttributeKeySetInfo roleAttributeKeySetInfo;

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
		if (roleAttributeKeySetInfo != null) {
			if (roleAttributeKeySetInfo.getId() != null) {
				//authenticationDomainInfo = LdapConfigurationService.findDepartment(authenticationDomainInfo.getId());
			} else {
				roleAttributeKeySetInfo = (RoleAttributeKeySetInfo) getSessionBean(Constants.ROLEATTRIBUTEKEYSET_INFO);
			}
		}

		setSessionBean(Constants.ROLEATTRIBUTEKEYSET_INFO, roleAttributeKeySetInfo);
	}

	@Prerender
	public void prerender() throws LectureException {
		logger.debug("prerender - refreshing department session object");
		refreshRoleAttributeKeySet();
		if (roleAttributeKeySetInfo == null || roleAttributeKeySetInfo.getId() == null) {
			addError(i18n("message_error_no_department_selected"));
			redirect(Constants.DESKTOP);
		}
	}

	private void refreshRoleAttributeKeySet() {
		if (roleAttributeKeySetInfo != null) {
			if (roleAttributeKeySetInfo.getId() != null) {
				//authenticationDomainInfo = departmentService.findDepartment(departmentInfo.getId());
				setSessionBean(Constants.ROLEATTRIBUTEKEYSET_INFO, roleAttributeKeySetInfo);
			}
		}
	}

	public Boolean getBookmarked() {
		try {
			return desktopService2.isDepartmentBookmarked(roleAttributeKeySetInfo.getId(), user.getId());
		} catch (Exception e) {

		}

		return false;
	}

	public RoleAttributeKeySetInfo getRoleAttributeKeySetInfo() {
		return roleAttributeKeySetInfo;
	}

	public void setRoleAttributeKeySetInfo(RoleAttributeKeySetInfo roleAttributeKeySetInfo) {
		this.roleAttributeKeySetInfo = roleAttributeKeySetInfo;
	}

	
	public LdapConfigurationService getLdapConfigurationService() {
		return ldapConfigurationService;
	}

	public void setLdapConfigurationService(
			LdapConfigurationService ldapConfigurationService) {
		this.ldapConfigurationService = ldapConfigurationService;
	}
	
}
