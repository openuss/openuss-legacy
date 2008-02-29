package org.openuss.web.course;

import java.util.ArrayList;
import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Property;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.Prerender;
import org.apache.shale.tiger.view.View;
import org.openuss.calendar.AppointmentInfo;
import org.openuss.calendar.CalendarInfo;
import org.openuss.calendar.CalendarService;
import org.openuss.desktop.DesktopException;
import org.openuss.framework.jsfcontrols.breadcrumbs.BreadCrumb;
import org.openuss.lecture.CourseApplicationException;
import org.openuss.lecture.CourseMemberInfo;
import org.openuss.lecture.CourseMemberType;
import org.openuss.web.Constants;

/**
 *
 * @author Ingo Dueppe
 * 
 */
@Bean(name = "views$secured$course$main", scope = Scope.REQUEST)
@View
public class CourseMainPage extends AbstractCoursePage {

	private static final Logger logger = Logger.getLogger(CourseMainPage.class);

	private String password;
	
	private List<CourseMemberInfo> assistants = new ArrayList<CourseMemberInfo>();
	
	private List<AppointmentInfo> appointments = new ArrayList<AppointmentInfo>();
	
	@SuppressWarnings("unchecked")
	@Override
	@Prerender
	public void prerender() throws Exception {
		super.prerender();
		if (courseInfo != null) {
			assistants = courseService.getAssistants(courseInfo);
			appointments = getCalendarService().getNaturalSerialAppointments(getCalendarInfo());
		}

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

	public String applyWithPassword() throws CourseApplicationException {
		logger.debug("course entry with password applied");
		courseService.applyUserByPassword(password, courseInfo, user);
		addMessage(i18n("message_course_password_accepted"));
		return Constants.SUCCESS;
	}

	public String apply() throws CourseApplicationException {
		logger.debug("course entry applied");
		courseService.applyUser(courseInfo, user);
		addMessage(i18n("message_course_send_application"));
		return Constants.SUCCESS;
	}

	public boolean isAspirant() {
		CourseMemberInfo info = courseService.getMemberInfo(courseInfo, user);
		return (info != null) && (info.getMemberType() == CourseMemberType.ASPIRANT);
	}

	public Boolean getBookmarked() {
		try {
			return desktopService2.isCourseBookmarked(courseInfo.getId(), user.getId());
		} catch (Exception e) {
			logger.error(e);
			return false;
		}
	}
	
	/**
	 * Bookmarks the selected course on the MyUni Page.
	 * @return Outcome
	 */
	public String shortcutCourse() {
		try {
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
			desktopService2.unlinkCourse(desktopInfo.getId(), courseInfo.getId());
			addMessage(i18n("desktop_command_add_course_succeed"));
			return Constants.SUCCESS;
		} catch (DesktopException e) {
			logger.error(e);
			addError(i18n(e.getMessage()));
			return Constants.FAILURE;
		}
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

	public List<AppointmentInfo> getAppointments() {
		return appointments;
	}

	public void setAppointments(List<AppointmentInfo> appointments) {
		this.appointments = appointments;
	}

}