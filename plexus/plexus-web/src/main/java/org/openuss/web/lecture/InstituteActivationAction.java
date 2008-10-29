package org.openuss.web.lecture;

import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Property;
import org.apache.shale.tiger.managed.Scope;
import org.openuss.framework.web.jsf.controller.BaseBean;
import org.openuss.registration.RegistrationCodeExpiredException;
import org.openuss.registration.RegistrationCodeNotFoundException;
import org.openuss.registration.RegistrationParentDepartmentDisabledException;
import org.openuss.registration.RegistrationService;
import org.openuss.web.Constants;

@Bean(name="instituteActivationAction", scope=Scope.REQUEST)
public class InstituteActivationAction extends BaseBean {
	
	@Property(value="#{registrationService}")
	private RegistrationService registrationService;

	@Property(value="#{param.code}")
	private String activationCode;
	
	public String activateInstitute(){
		if (activationCode != null){
			try {
				getRegistrationService().activateInstituteByCode(activationCode);
				addMessage(i18n("institute_activation_successful"));
				return Constants.SUCCESS;
			} catch (RegistrationCodeNotFoundException e) {
				addError(i18n("activation_error_code_not_found"));
				return Constants.FAILURE;
			} catch (RegistrationCodeExpiredException e){
				addError(i18n("activation_error_code_expired"));
				return Constants.FAILURE;	
			} catch (RegistrationParentDepartmentDisabledException e){
				addError(i18n("message_institute_enabled_failed_department_disabled"));
				return Constants.FAILURE;
			}
		}
		addError(i18n("activation_error_code_not_found"));
		return Constants.FAILURE;

	}

	public RegistrationService getRegistrationService() {
		return registrationService;
	}

	public void setRegistrationService(RegistrationService registrationService) {
		this.registrationService = registrationService;
	}

	public String getActivationCode() {
		return activationCode;
	}

	public void setActivationCode(String activationCode) {
		this.activationCode = activationCode;
	}
}