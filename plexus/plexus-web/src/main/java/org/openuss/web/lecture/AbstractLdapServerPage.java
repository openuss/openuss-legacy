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
 * Abstract ldapServer Page
 * 
 * @author Christian Grelle
 * @author Peter Schuh 
 * 
 */
public abstract class AbstractLdapServerPage extends BasePage {

	private static final Logger logger = Logger.getLogger(AbstractLdapServerPage.class);
	
	@Property(value = "#{ldapServerInfo}")
	protected LdapServerInfo ldapServerInfo;

	@Property(value = "#{ldapConfigurationService}")
	protected LdapConfigurationService ldapConfigurationService;


	/**
	 * Refreshing ldapServer VO
	 * 
	 * @throws Exception
	 */
	@Preprocess
	public void preprocess() throws Exception {
		super.preprocess();
		logger.debug("preprocess - refreshing ldapServer session object");
		if (ldapServerInfo != null) {
			if (ldapServerInfo.getId() != null) {
				//ldapServerInfo = LdapConfigurationService.getLdapServer(ldapServerInfo.getId());
			} else {
				ldapServerInfo = (LdapServerInfo) getSessionBean(Constants.SERVER_INFO);
			}
		}

		setSessionBean(Constants.SERVER_INFO, ldapServerInfo);
	}

	@Prerender
	public void prerender() throws LectureException {
		logger.debug("prerender - refreshing ldapServer session object");
		refreshLdapServer();
		if (ldapServerInfo == null || ldapServerInfo.getId() == null) {
			//TODO: CHRISTIAN: WRONG MESSAGE!!!
			addError(i18n("message_error_no_department_selected"));
			redirect(Constants.DESKTOP);
		}
	}

	private void refreshLdapServer() {
		if (ldapServerInfo != null) {
			if (ldapServerInfo.getId() != null) {
				//ldapServerInfo = LdapConfigurationService.getLdapServer(ldapServerInfo.getId());
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
