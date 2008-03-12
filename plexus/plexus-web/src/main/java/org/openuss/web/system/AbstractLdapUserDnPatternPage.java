package org.openuss.web.system;

import org.apache.log4j.Logger;
import org.apache.shale.tiger.managed.Property;
import org.apache.shale.tiger.view.Preprocess;
import org.apache.shale.tiger.view.Prerender;
import org.openuss.lecture.LectureException;
import org.openuss.security.ldap.LdapConfigurationService;
import org.openuss.security.ldap.UserDnPatternInfo;
import org.openuss.web.BasePage;
import org.openuss.web.Constants;

/**
 * Abstract userDnPattern Page
 * 
 * @author Christian Grelle
 * @author Peter Schuh 
 * 
 */
public abstract class AbstractLdapUserDnPatternPage extends BasePage {

	private static final Logger logger = Logger.getLogger(AbstractLdapUserDnPatternPage.class);
	
	@Property(value = "#{userDnPatternInfo}")
	protected UserDnPatternInfo userDnPatternInfo;

	@Property(value = "#{ldapConfigurationService}")
	protected LdapConfigurationService ldapConfigurationService;


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

	
	public LdapConfigurationService getLdapConfigurationService() {
		return ldapConfigurationService;
	}

	public void setLdapConfigurationService(
			LdapConfigurationService ldapConfigurationService) {
		this.ldapConfigurationService = ldapConfigurationService;
	}
	
}