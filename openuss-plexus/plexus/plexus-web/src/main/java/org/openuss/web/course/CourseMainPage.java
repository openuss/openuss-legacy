package org.openuss.web.course; 

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
import org.openuss.framework.jsfcontrols.breadcrumbs.BreadCrumb;
import org.openuss.lecture.CourseApplicationException;
import org.openuss.lecture.CourseMemberInfo;
import org.openuss.lecture.CourseMemberType;
import org.openuss.web.Constants;

@Bean(name = "views$secured$course$main", scope = Scope.REQUEST)
@View
public class CourseMainPage extends AbstractCoursePage{
	
	private static final Logger logger = Logger.getLogger(CourseMainPage.class);
	
	private String password; 

	private List<CourseMemberInfo> assistants = new ArrayList<CourseMemberInfo>();
	
	@SuppressWarnings("unchecked")
	@Override
	@Prerender
	public void prerender() throws Exception {
		super.prerender();
		assistants = courseService.getAssistants(courseInfo);
	
		BreadCrumb newCrumb = new BreadCrumb();
		newCrumb.setName(i18n("course_command_overview"));
		newCrumb.setHint(i18n("course_command_overview"));
		breadcrumbs.addCrumb(newCrumb);
	}
	
	public void validatePassword(FacesContext context, UIComponent toValidate, Object value) {
		String password = (String) value;
		if (!StringUtils.equalsIgnoreCase(password, courseInfo.getPassword())) {
			((UIInput) toValidate).setValid(false);
			addError(toValidate.getClientId(context), i18n("message_error_password_is_not_correct"), null);
		}
	}
	
	public String applyWithPassword() throws CourseApplicationException{
		logger.debug("course entry with password applied");
		courseService.applyUserByPassword(password, courseInfo, user);
		addMessage(i18n("message_course_password_accepted"));
		return Constants.SUCCESS;
	}
	
	public String apply() throws CourseApplicationException{
		logger.debug("course entry applied");
		courseService.applyUser(courseInfo, user);
		addMessage(i18n("message_course_send_application"));
		return Constants.SUCCESS;
	}
	
	public boolean isAspirant() {
		CourseMemberInfo info = courseService.getMemberInfo(courseInfo, user);
		return (info != null) && (info.getMemberType() == CourseMemberType.ASPIRANT);
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<CourseMemberInfo> getAssistants() {
		return assistants;
	}


}