package org.openuss.web.lecture;

import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Property;
import org.apache.shale.tiger.managed.Scope;
import org.openuss.framework.web.jsf.controller.BaseBean;
import org.openuss.lecture.LectureService;
import org.openuss.registration.RegistrationCodeExpiredException;
import org.openuss.registration.RegistrationCodeNotFoundException;
import org.openuss.registration.RegistrationService;
import org.openuss.web.Constants;

@Bean(name="facultyActivationAction", scope=Scope.REQUEST)
public class FacultyActivationAction extends BaseBean {

	@Property(value="#{lectureService}")
	private LectureService lectureService;
	
	@Property(value="#{registrationService}")
	private RegistrationService registrationService;

	@Property(value="#{param.code}")
	private String activationCode;
	
	public String activateFaculty(){
		if (activationCode != null){
			try {
				getRegistrationService().activateFacultyByCode(activationCode);
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

	public LectureService getLectureService() {
		return lectureService;
	}

	public void setLectureService(LectureService lectureService) {
		this.lectureService = lectureService;
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