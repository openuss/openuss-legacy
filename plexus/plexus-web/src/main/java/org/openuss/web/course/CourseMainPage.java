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
import org.openuss.desktop.DesktopException;
import org.openuss.framework.jsfcontrols.breadcrumbs.BreadCrumb;
import org.openuss.lecture.CourseApplicationException;
import org.openuss.lecture.CourseMemberInfo;
import org.openuss.lecture.CourseMemberType;
import org.openuss.web.Constants;

/**
 * Page class of Course Main Page.
 * 
 * @author Ingo Dueppe
 * @author Sebastian Roekens
 * 
 */
@Bean(name = "views$secured$course$main", scope = Scope.REQUEST)
@View
public class CourseMainPage extends AbstractCoursePage {

	private static final Logger logger = Logger.getLogger(CourseMainPage.class);

	private String password;

	private List<CourseMemberInfo> assistants = new ArrayList<CourseMemberInfo>();

	@SuppressWarnings("unchecked")
	@Override
	@Prerender
	public void prerender() throws Exception {
		super.prerender();
		if (isRedirected()){
			return;
		}
		if (courseInfo != null){
			assistants = courseService.getAssistants(courseInfo);
		}

		BreadCrumb newCrumb = new BreadCrumb();
		newCrumb.setName(i18n("course_command_overview"));
		newCrumb.setHint(i18n("course_command_overview"));
		breadcrumbs.addCrumb(newCrumb);
	}

	public void validatePassword(FacesContext context, UIComponent toValidate, Object value) {
		String password = (String) value;
		if (!StringUtils.equalsIgnoreCase(password, courseInfo.getPassword())) {
			if (toValidate instanceof UIInput) {
				((UIInput) toValidate).setValid(false);
			}
			addError(toValidate.getClientId(context), i18n("message_error_password_is_not_correct"), null);
		}
	}

	public String applyWithPassword() throws CourseApplicationException {
		logger.debug("course entry with password applied");
		courseService.applyUser(courseInfo, user, password);
		addMessage(i18n("message_course_password_accepted"));
		return Constants.SUCCESS;
	}

	public String apply() throws CourseApplicationException {
		logger.debug("course entry applied");
		courseService.applyUser(courseInfo, user);
		try {
			desktopService2.linkCourse(desktopInfo.getId(), courseInfo.getId());
		} catch (DesktopException e) {
			//should not occur
			logger.error(e.getMessage());
		}
		addMessage(i18n("message_course_send_application"));
		return Constants.SUCCESS;
	}
	
	public String approve(){
		addMessage(i18n("message_course_open_approve"));
		courseService.applyUser(courseInfo, user);
		try {
			desktopService2.linkCourse(desktopInfo.getId(), courseInfo.getId());
		} catch (DesktopException e) {
			//should not occur
			logger.error(e.getMessage());
		}
		return Constants.SUCCESS;
	}

	public boolean isAspirant() {
		CourseMemberInfo info = courseService.getMemberInfo(courseInfo, user);
		return (info != null) && (info.getMemberType() == CourseMemberType.ASPIRANT);
	}

	public Boolean getBookmarked() {
		if (desktopInfo == null || desktopInfo.getId() == null){
			refreshDesktop();
		}
		return desktopInfo.getCourseInfos().contains(courseInfo);
	}
	
	/**
	 * Bookmarks the selected course on the MyUni Page.
	 * @return Outcome
	 */
	public String shortcutCourse() {
		//courseInfo = courseData.getRowData();
		try {
			if (desktopInfo == null || desktopInfo.getId() == null){
				refreshDesktop();
			}
			desktopService2.linkCourse(desktopInfo.getId(), courseInfo.getId());
			addMessage(i18n("desktop_command_add_course_succeed"));
			return Constants.SUCCESS;
		} catch (DesktopException e) {
			logger.error(e);
			addError(i18n(e.getMessage()));
			return Constants.FAILURE;
		}
	}

	public String removeCourseShortcut() {
		try {
			if (desktopInfo==null){
				refreshDesktop();
			}
			desktopService2.unlinkCourse(desktopInfo.getId(), courseInfo.getId());
		} catch (Exception e) {
			addError(i18n("institute_error_remove_shortcut"), e.getMessage());
			return Constants.FAILURE;
		}
		
		addMessage(i18n("institute_success_remove_shortcut"));
		return Constants.SUCCESS;
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