package org.openuss.web.system.ldap;

import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Property;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.Preprocess;
import org.apache.shale.tiger.view.Prerender;
import org.apache.shale.tiger.view.View;
import org.openuss.lecture.LectureException;
import org.openuss.security.ldap.UserDnPatternInfo;
import org.openuss.web.Constants;

/**
 * Backing bean for the ldap_domain registration. Is responsible starting the
 * wizard, binding the values and registrating the department.
 * 
 * @author Christian Grelle
 * @author Peter Schuh
 * @author Ingo Dueppe
 * 
 */

@Bean(name = Constants.LDAP_USERDNPATTERN_REGISTRATION_CONTROLLER, scope = Scope.REQUEST)
@View
public class LdapUserDnPatternRegistrationController extends AbstractLdapPage {

	@Property(value = "#{userDnPatternInfo}")
	private UserDnPatternInfo userDnPatternInfo;

	/**
	 * Refreshing userDnPattern VO
	 * 
	 * @throws Exception
	 */
	@Preprocess
	public void preprocess() throws Exception {
		super.preprocess();
		logger.debug("preprocess - refreshing userDnPattern session object");
		if (userDnPatternInfo != null) {
			if (userDnPatternInfo.getId() != null) {
				userDnPatternInfo = ldapConfigurationService.getUserDnPatternById(userDnPatternInfo.getId());
			} else {
				userDnPatternInfo = (UserDnPatternInfo) getSessionBean(Constants.USERDNPATTERN_INFO);
			}
		}

		setSessionBean(Constants.USERDNPATTERN_INFO, userDnPatternInfo);
	}

	@Prerender
	public void prerender() throws LectureException {
		logger.debug("prerender - refreshing userDnPattern session object");
		refreshUserDnPattern();
		if (userDnPatternInfo == null || userDnPatternInfo.getId() == null) {			
			addError(i18n("message_ldap_userdnpattern_no_userdnpattern_selected"));
			redirect(Constants.DESKTOP);
		}
	}

	private void refreshUserDnPattern() {
		if (userDnPatternInfo != null) {
			if (userDnPatternInfo.getId() != null) {
				userDnPatternInfo = ldapConfigurationService.getUserDnPatternById(userDnPatternInfo.getId());
				setSessionBean(Constants.USERDNPATTERN_INFO, userDnPatternInfo);
			}
		}
	}


	public UserDnPatternInfo getUserDnPatternInfo() {
		return userDnPatternInfo;
	}

	public void setUserDnPatternInfo(UserDnPatternInfo userDnPatternInfo) {
		this.userDnPatternInfo = userDnPatternInfo;
	}	
	
	public String start() {
		logger.debug("start registration process");
		
		userDnPatternInfo = new UserDnPatternInfo();
		setSessionBean(Constants.USERDNPATTERN_INFO, userDnPatternInfo);
		
		return Constants.LDAP_USERDNPATTERN_REGISTRATION_STEP1_PAGE;
	}

	

	public String register() /*throws DesktopException, LectureException*/ {
		ldapConfigurationService.createUserDnPattern(userDnPatternInfo);

		return Constants.LDAP_USERDNPATTERN_PAGE;
	}
	
	public String save() {
		ldapConfigurationService.saveUserDnPattern(userDnPatternInfo);
		return Constants.LDAP_USERDNPATTERN_PAGE;
	}
	
}
