package org.openuss.web.enrollment; 

import java.util.ArrayList;
import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.Prerender;
import org.apache.shale.tiger.view.View;
import org.openuss.lecture.EnrollmentApplicationException;
import org.openuss.lecture.EnrollmentMemberInfo;
import org.openuss.lecture.EnrollmentMemberType;
import org.openuss.web.Constants;

@Bean(name = "views$secured$enrollment$main", scope = Scope.REQUEST)
@View
public class EnrollmentMainPage extends AbstractEnrollmentPage{
	
	private static final Logger logger = Logger.getLogger(EnrollmentMainPage.class);
	
	private String password; 

	private List<EnrollmentMemberInfo> assistants = new ArrayList<EnrollmentMemberInfo>();
	
	@Override
	@Prerender
	public void prerender() throws Exception {
		super.prerender();
		assistants = enrollmentService.getAssistants(enrollment);
	}
	

	public void validatePassword(FacesContext context, UIComponent toValidate, Object value) {
		String password = (String) value;
		if (!StringUtils.equalsIgnoreCase(password, enrollment.getPassword())) {
			((UIInput) toValidate).setValid(false);
			addError(toValidate.getClientId(context), i18n("message_error_password_is_not_correct"), null);
		}
	}
	
	public String applyWithPassword() throws EnrollmentApplicationException{
		logger.debug("enrollment entry with password applied");
		enrollmentService.applyUserByPassword(password, enrollment, user);
		addMessage(i18n("message_enrollment_password_accepted"));
		return Constants.SUCCESS;
	}
	
	public String apply() throws EnrollmentApplicationException{
		logger.debug("enrollment entry applied");
		enrollmentService.applyUser(enrollment, user);
		addMessage(i18n("message_enrollment_send_application"));
		return Constants.SUCCESS;
	}
	
	public boolean isAspirant() {
		EnrollmentMemberInfo info = enrollmentService.getMemberInfo(enrollment, user);
		return (info != null) && (info.getMemberType() == EnrollmentMemberType.ASPIRANT);
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<EnrollmentMemberInfo> getAssistants() {
		return assistants;
	}


}