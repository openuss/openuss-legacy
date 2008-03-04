package org.openuss.web.migration;

import org.apache.log4j.Logger;
import org.openuss.security.User;
import org.openuss.security.UserContact;
import org.openuss.security.UserPreferences;
import org.openuss.web.BasePage;
import org.openuss.web.Constants;

public class UserAgreementPage extends BasePage {

	private static final Logger logger = Logger.getLogger(UserAgreementPage.class);
	/**
	 * Create a new user structure for the central user registration process.
	 */	
	private void init() {
		logger.trace("init registration data");
		
		User user = User.Factory.newInstance();
		user.setPreferences(UserPreferences.Factory.newInstance());
		user.setContact(UserContact.Factory.newInstance());
		String locale = getFacesContext().getViewRoot().getLocale().toString();
		user.setLocale(locale);
		setSessionBean(Constants.USER_SESSION_KEY, user);
	}
}
