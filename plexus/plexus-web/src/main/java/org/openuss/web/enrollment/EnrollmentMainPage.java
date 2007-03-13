package org.openuss.web.enrollment; 

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Property;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.Prerender;
import org.apache.shale.tiger.view.View;
import org.openuss.lecture.EnrollmentMemberInfo;
import org.openuss.lecture.EnrollmentMemberType;
import org.openuss.lecture.LectureException;
import org.openuss.security.User;
import org.openuss.web.Constants;

@Bean(name = "views$secured$enrollment$main", scope = Scope.REQUEST)
@View
public class EnrollmentMainPage extends AbstractEnrollmentPage{
	
	private static final Logger logger = Logger.getLogger(EnrollmentMainPage.class);
	
	private String password; 

	private List<EnrollmentMemberInfo> assistants = new ArrayList<EnrollmentMemberInfo>();
	
	@Property(value="#{user}")
	private User user;
	
	@Override
	@Prerender
	public void prerender() throws LectureException {
		super.prerender();
		assistants = enrollmentService.getAssistants(enrollment);
	}
	

	public String applyWithPassword(){
		logger.debug("enrollment entry with password applied");
		enrollmentService.applyUserByPassword(password, enrollment, user);
		addMessage(i18n("message_enrollment_password_accepted"));
		return Constants.SUCCESS;
	}
	
	public String apply(){
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

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}


}