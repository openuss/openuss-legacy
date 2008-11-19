package org.openuss.web.course;

import java.util.ArrayList;
import java.util.List;

import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

import org.apache.log4j.Logger;
import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Property;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.Prerender;
import org.apache.shale.tiger.view.View;
import org.openuss.framework.jsfcontrols.breadcrumbs.BreadCrumb;
import org.openuss.lecture.AccessType;
import org.openuss.lecture.CourseInfo;
import org.openuss.lecture.LectureException;
import org.openuss.web.Constants;

/**
 * Course Edit Page Controller
 * 
 * @author Ingo Dueppe
 * @author Sebastian Roekens
 * 
 */
@Bean(name = "views$secured$course$courseoptions", scope = Scope.REQUEST)
@View
public class CourseOptionsPage extends AbstractCoursePage {

	private static final Logger logger = Logger.getLogger(CourseOptionsPage.class);

	@Property(value = "#{" + Constants.COURSE_OPTIONS_INFO + "}")
	private CourseInfo courseOptionsInfo;

	private static final long serialVersionUID = 8821048605517398410L;

	@Prerender
	@Override
	public void prerender() throws Exception {
		super.prerender();
		if (isRedirected()) {
			return;
		}
		initCourseOptionsBean();
		breadcrumbs.loadCourseCrumbs(courseInfo);
		addPageCrumb();
	}
	
	public void preprocess() throws Exception {
		super.preprocess();
		initCourseOptionsBean();
	}

	private void initCourseOptionsBean() {
		if (courseOptionsInfo == null || !courseOptionsInfo.equals(courseInfo)) {
			courseOptionsInfo = courseService.findCourse(courseInfo.getId());
			setBean(Constants.COURSE_OPTIONS_INFO, courseOptionsInfo);
		}
	}

	private void addPageCrumb() {
		BreadCrumb crumb = new BreadCrumb();
		crumb.setName(i18n("course_command_options_config"));
		crumb.setHint(i18n("course_command_options_config"));
		breadcrumbs.addCrumb(crumb);
	}

	/**
	 * Save changes of the course
	 * 
	 * @return outcome
	 * @throws LectureException
	 */
	public String saveOptions() throws LectureException {
		logger.trace("saving course options");
		courseService.updateCourse(courseOptionsInfo);
		addMessage(i18n("message_course_options_saved"));
		return Constants.COURSE_OPTIONS_PAGE;
	}

	/**
	 * Cancel changes to the Options. Redirect to the previous page
	 * 
	 * @return outcome
	 */
	public String cancelOptions() {
		return Constants.COURSE_PAGE;
	}

	/**
	 * Value Change Listener to switch password input text on and off.
	 * 
	 * @param event
	 */
	public void processAccessTypeChanged(ValueChangeEvent event) {
		Object accessType = event.getNewValue();
		courseOptionsInfo.setAccessType((AccessType) accessType);
		setSessionBean(Constants.COURSE_OPTIONS_INFO, courseOptionsInfo);
		if (!AccessType.PASSWORD.equals(accessType) && !AccessType.APPLICATION.equals(accessType)) {
			courseOptionsInfo.setCollaboration(false);
			courseOptionsInfo.setPapersubmission(false);
		}
	}

	public List<SelectItem> getAccessTypes() {
		List<SelectItem> items = new ArrayList<SelectItem>();
		items.add(new SelectItem(AccessType.ANONYMOUS, i18n("course_options_access_anonymous")));
		items.add(new SelectItem(AccessType.OPEN, i18n("course_options_access_open")));
		items.add(new SelectItem(AccessType.CLOSED, i18n("course_options_access_closed")));
		items.add(new SelectItem(AccessType.PASSWORD, i18n("course_options_access_password")));
		items.add(new SelectItem(AccessType.APPLICATION, i18n("course_options_access_application")));
		return items;
	}

	public CourseInfo getCourseOptionsInfo() {
		return courseOptionsInfo;
	}

	public void setCourseOptionsInfo(CourseInfo courseOptionsInfo) {
		this.courseOptionsInfo = courseOptionsInfo;
	}

}
