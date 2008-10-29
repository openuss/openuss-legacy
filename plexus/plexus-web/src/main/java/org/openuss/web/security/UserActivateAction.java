package org.openuss.web.security;

import org.apache.log4j.Logger;
import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Property;
import org.apache.shale.tiger.managed.Scope;
import org.openuss.framework.web.jsf.controller.BaseBean;
import org.openuss.registration.RegistrationCodeExpiredException;
import org.openuss.registration.RegistrationCodeNotFoundException;
import org.openuss.registration.RegistrationService;
import org.openuss.web.Constants;

/**
 * Action to activate a user by a activation code 
 * @author Ingo Dueppe
 */
@Bean(name="userActivate", scope=Scope.REQUEST)
public class UserActivateAction extends BaseBean {

	private static final Logger logger = Logger.getLogger(UserActivateAction.class);

	@Property(value="#{registrationService}")
	private RegistrationService registrationService;
	
	@Property(value="#{param.code}")
	private String activationCode;
	
	/**
	 * Activate User by activationCode.
	 * @return outcome
	 */
	public String activate() {
		if (activationCode != null) {
			try {
				registrationService.activateUserByCode(activationCode);
				addMessage(i18n("user_activated_message"));
				return Constants.SUCCESS;
			} catch (RegistrationCodeExpiredException e){
				addError(i18n("activation_error_code_expired"));
				return Constants.FAILURE;
			}
			catch (RegistrationCodeNotFoundException e) {
				addError(i18n("activation_error_code_not_found"));
				return Constants.FAILURE;
			}
		}
		return Constants.FAILURE;
	}

	/**
	 * get activation code
	 * @return
	 */
	public String getActivationCode() {
		return activationCode;
	}

	/**
	 * set activation code
	 * @param activationCode
	 */
	public void setActivationCode(String activationCode) {
		logger.debug("setActivationCode(activationCode=" + activationCode + ")"); 
		this.activationCode = activationCode;
	}

	/**
	 * Access associated registration service bean
	 * @return
	 */
	public RegistrationService getRegistrationService() {
		return registrationService;
	}

	/**
	 * Inject associated registration service bean
	 * @param registrationService
	 */
	public void setRegistrationService(RegistrationService registrationService) {
		logger.debug("setRegistrationService(registrationService=" + registrationService + ")");
		this.registrationService = registrationService;

	}
	
	

}
