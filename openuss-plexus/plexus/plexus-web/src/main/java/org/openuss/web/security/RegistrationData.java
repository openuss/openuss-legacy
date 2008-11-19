package org.openuss.web.security;

import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;
import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.Init;
import org.apache.shale.tiger.view.View;
import org.openuss.framework.web.jsf.controller.BaseBean;
import org.openuss.security.UserInfo;
import org.openuss.web.Constants;

/**
 * Value Object containing additional data during registration process
 * 
 * @author Ingo Dueppe
 * 
 */
@Bean(name = Constants.REGISTRATION_DATA, scope = Scope.SESSION)
@View
public class RegistrationData extends BaseBean {

	private static final Logger logger = Logger.getLogger(RegistrationData.class);

	private boolean userAgreementAccepted;
	private String username;

	/**
	 * Create a new user structure for the registration process.
	 */
	@Init
	public void init() {
		logger.trace("init registration data");
		
		UserInfo user = new UserInfo();
		
		String locale = getFacesContext().getViewRoot().getLocale().toString();
		user.setLocale(locale);
		setSessionBean(Constants.USER_SESSION_KEY, user);
	}
	
	/**
	 * Validator to check wether the user has accepted the user agreement or not.
	 * @param context
	 * @param toValidate
	 * @param value
	 */
	public void validateAcception(FacesContext context, UIComponent toValidate, Object value) {
		boolean accept = (Boolean) value;
		if (!accept) {
			if (toValidate instanceof UIInput) {
				((UIInput)toValidate).setValid(false);
			}
			addError(toValidate.getClientId(context), i18n("error_useragreement_must_be_accepted"), null);
		}
	}
	
	public boolean isUserAgreementAccepted() {
		return userAgreementAccepted;
	}

	public void setUserAgreementAccepted(boolean userAgreementAccepted) {
		this.userAgreementAccepted = userAgreementAccepted;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

}
