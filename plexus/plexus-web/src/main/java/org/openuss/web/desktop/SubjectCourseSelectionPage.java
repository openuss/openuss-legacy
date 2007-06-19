package org.openuss.web.desktop;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Property;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.Prerender;
import org.apache.shale.tiger.view.View;
import org.openuss.framework.web.jsf.model.AbstractPagedTable;
import org.openuss.framework.web.jsf.model.DataPage;
import org.openuss.lecture.Course;
import org.openuss.lecture.LectureService;
import org.openuss.lecture.Subject;
import org.openuss.web.BasePage;
import org.openuss.web.Constants;


/**
 * SubjectCourseSelectionPage is the controller bean to handle the desktop view. 
 * @author Ingo Dueppe
 */
@Bean(name="views$secured$desktop$subjectcourseselection", scope=Scope.REQUEST)
@View
public class SubjectCourseSelectionPage extends BasePage {

	private static final Logger logger = Logger.getLogger(SubjectCourseSelectionPage.class);
	
	private CourseDataProvider coursesProvider = new CourseDataProvider();
	
	@Property(value="#{sessionScope.subject}")
	private Subject subject;
	
	@Property(value="#{lectureService}")
	LectureService lectureService;

	@Prerender
	public void prerender() {
		logger.debug("prerender subject course selction");
		if (subject == null) {
			addError(i18n("message_error_no_subject_selected"));
			redirect(Constants.OUTCOME_BACKWARD);
		} else {
			subject = lectureService.getSubject(subject.getId());
		}
		setSessionBean(Constants.SUBJECT, subject);
	}
	
	/* ------------------ data models ------------------- */
	private class CourseDataProvider extends AbstractPagedTable<Course> {
		private static final long serialVersionUID = 6604486126694733013L;
		
		private DataPage<Course> page;

		@Override
		public DataPage<Course> getDataPage(int startRow, int pageSize) {
			if (page == null) {
				List<Course> courses = new ArrayList(subject.getCourses());
				sort(courses);
				page = new DataPage<Course>(courses.size(),0,courses);
			}
			return page;
		}
	}

	public CourseDataProvider getCoursesProvider() {
		return coursesProvider;
	}

	public void setCoursesProvider(CourseDataProvider coursesProvider) {
		this.coursesProvider = coursesProvider;
	}

	public LectureService getLectureService() {
		return lectureService;
	}

	public void setLectureService(LectureService lectureService) {
		this.lectureService = lectureService;
	}

	public Subject getSubject() {
		return subject;
	}

	public void setSubject(Subject subject) {
		this.subject = subject;
	}
}
