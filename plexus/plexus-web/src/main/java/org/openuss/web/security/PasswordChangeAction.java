package org.openuss.web.security;

import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Property;
import org.apache.shale.tiger.managed.Scope;
import org.openuss.framework.web.jsf.controller.BaseBean;
import org.openuss.registration.RegistrationCodeExpiredException;
import org.openuss.registration.RegistrationCodeNotFoundException;
import org.openuss.registration.RegistrationService;
import org.openuss.security.SecurityService;
import org.openuss.security.UserInfo;
import org.openuss.web.Constants;

/**
 * 
 * @author Ingo Dueppe
 * @author Sebastian Roekens
 *
 */
@Bean(name="passwordChangeAction", scope=Scope.REQUEST)
public class PasswordChangeAction extends BaseBean {

	@Property(value="#{registrationService}")
	private RegistrationService registrationService;
	 
	@Property(value="#{securityService}")
	private SecurityService securityService;
	
	@Property(value="#{param.code}")
	private String changeCode;
	
	private String newPassword;
	
	@Property(value="#{sessionScope.user}")
	private UserInfo user; 
	
	/**
	 * Activate User by activationCode.
	 * @return outcome
	 */
	public String loginUserByCode() {
		if (changeCode != null) {						
			try {
				user = (UserInfo) getSecurityService().getUser(registrationService.loginUserByActivationCode(changeCode).getId());
				setSessionBean(Constants.USER, user);
				return Constants.SUCCESS;
			} catch (RegistrationCodeNotFoundException e) {
				addError(i18n("activation_error_code_not_found"));
				return Constants.FAILURE;
			} catch (RegistrationCodeExpiredException e){
				addError(i18n("activation_error_code_expired"));
				return Constants.FAILURE;				
			}
		}
		addError(i18n("activation_error_code_not_found"));
		return Constants.FAILURE;
	}

	public String changePassword() {
		if (user != null) {
			user.setPassword(newPassword);
			getSecurityService().changePassword(newPassword);			
			addMessage(i18n("password_change_successful"));
			return Constants.SUCCESS;
		}
		return Constants.FAILURE;
	}
	
	public String getChangeCode() {
		return changeCode;
	}

	public void setChangeCode(String changeCode) {
		this.changeCode = changeCode;
	}

	public RegistrationService getRegistrationService() {
		return registrationService;
	}

	public void setRegistrationService(RegistrationService registrationService) {
		this.registrationService = registrationService;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public SecurityService getSecurityService() {
		return securityService;
	}

	public void setSecurityService(SecurityService securityService) {
		this.securityService = securityService;
	}

	public UserInfo getUser() {
		return user;
	}

	public void setUser(UserInfo user) {
		this.user = user;
	}
}