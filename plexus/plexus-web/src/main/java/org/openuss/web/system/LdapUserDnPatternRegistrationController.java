package org.openuss.web.system;

import org.apache.log4j.Logger;
import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.View;
import org.openuss.security.ldap.UserDnPatternInfo;
import org.openuss.web.Constants;

/**
 * Backing bean for the ldap_domain registration. Is responsible starting the
 * wizard, binding the values and registrating the department.
 * 
 * @author Christian Grelle
 * @author Peter Schuh
 * 
 */

@Bean(name = Constants.LDAP_USERDNPATTERN_REGISTRATION_CONTROLLER, scope = Scope.REQUEST)
@View
public class LdapUserDnPatternRegistrationController extends AbstractLdapUserDnPatternPage {

	
	private static final Logger logger = Logger.getLogger(LdapUserDnPatternRegistrationController.class);
	
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
