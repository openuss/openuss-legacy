package org.openuss.web.lecture;

import org.apache.log4j.Logger;
import org.apache.shale.tiger.managed.Property;
import org.apache.shale.tiger.view.Preprocess;
import org.apache.shale.tiger.view.Prerender;
import org.openuss.lecture.LectureException;
import org.openuss.security.ldap.LdapConfigurationService;
import org.openuss.security.ldap.RoleAttributeKeyInfo;
import org.openuss.web.BasePage;
import org.openuss.web.Constants;

/**
 * Abstract RoleAttributeKey Page
 * 
 * @author Christian Grelle
 * @author Peter Schuh 
 */
public abstract class AbstractLdapRoleAttributeKeyPage extends BasePage {

	private static final Logger logger = Logger.getLogger(AbstractLdapRoleAttributeKeyPage.class);
	
	@Property(value = "#{roleAttributeKeyInfo}")
	protected RoleAttributeKeyInfo roleAttributeKeyInfo;

	@Property(value = "#{ldapConfigurationService}")
	protected LdapConfigurationService ldapConfigurationService;


	/**
	 * Refreshing roleAttributeKey VO
	 * 
	 * @throws Exception
	 */
	@Preprocess
	public void preprocess() throws Exception {
		super.preprocess();
		logger.debug("preprocess - refreshing roleAttributeKey session object");
		if (roleAttributeKeyInfo != null) {
			if (roleAttributeKeyInfo.getId() != null) {
				//roleAttributeKeyInfo = LdapConfigurationService.getRoleAttributeKeyById(roleAttributeKeyInfo.getId());
			} else {
				roleAttributeKeyInfo = (RoleAttributeKeyInfo) getSessionBean(Constants.ROLEATTRIBUTEKEY_INFO);
			}
		}

		setSessionBean(Constants.ROLEATTRIBUTEKEY_INFO, roleAttributeKeyInfo);
	}

	@Prerender
	public void prerender() throws LectureException {
		logger.debug("prerender - refreshing roleAttributeKey session object");
		refreshRoleAttributeKeySet();
		if (roleAttributeKeyInfo == null || roleAttributeKeyInfo.getId() == null) {
			//TODO: CHRISTIAN: WRONG MESSAGE!!!
			addError(i18n("message_error_no_department_selected"));
			redirect(Constants.DESKTOP);
		}
	}

	private void refreshRoleAttributeKeySet() {
		if (roleAttributeKeyInfo != null) {
			if (roleAttributeKeyInfo.getId() != null) {
				//roleAttributeKeyInfo = LdapConfigurationService.getRoleAttributeKeyById(roleAttributeKeyInfo.getId());
				setSessionBean(Constants.ROLEATTRIBUTEKEY_INFO, roleAttributeKeyInfo);
			}
		}
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
