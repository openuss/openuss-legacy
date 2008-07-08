package org.openuss.web.security;

import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Property;
import org.apache.shale.tiger.managed.Scope;
import org.openuss.framework.web.jsf.controller.BaseBean;
import org.openuss.registration.RegistrationCodeExpiredException;
import org.openuss.registration.RegistrationCodeNotFoundException;
import org.openuss.registration.RegistrationService;
import org.openuss.web.Constants;

/**
 * 
 * @author Sebastian Roekens
 *
 */
@Bean(name="userDeleteAction", scope=Scope.REQUEST)
public class UserDeleteAction extends BaseBean {

	@Property(value="#{registrationService}")
	private RegistrationService registrationService;
	 
	@Property(value="#{param.code}")
	private String changeCode;
	
	/**
	 * Activate User by activationCode.
	 * @return outcome
	 */
	public String deleteUser() {
		if (changeCode != null) {						
			try {
				registrationService.generateDeleteUserCommand(changeCode);
				return Constants.SUCCESS;
			} catch (RegistrationCodeNotFoundException e) {
				addError(i18n("delete_error_code_not_found"));
				return Constants.FAILURE;
			} catch (RegistrationCodeExpiredException e){
				addError(i18n("delete_error_code_expired"));
				return Constants.FAILURE;				
			}
		}
		addError(i18n("delete_error_code_not_found"));
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

}