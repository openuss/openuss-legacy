package org.openuss.web.lecture;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.faces.event.ValueChangeEvent;

import org.apache.log4j.Logger;
import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Property;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.Prerender;
import org.apache.shale.tiger.view.View;
import org.openuss.desktop.DesktopException;
import org.openuss.framework.web.jsf.model.AbstractPagedTable;
import org.openuss.framework.web.jsf.model.DataPage;
import org.openuss.lecture.Course;
import org.openuss.lecture.LectureException;
import org.openuss.lecture.Period;
import org.openuss.news.NewsService;
import org.openuss.web.Constants;

/**
 * @author Ingo Dueppe
 * @author Sebastian Roekens
 */
@View
@Bean(name = "views$secured$lecture$institute", scope = Scope.REQUEST)
public class InstitutePage extends AbstractLecturePage {

	private static final Logger logger = Logger.getLogger(InstitutePage.class);

	private static final long serialVersionUID = -1982354759705300093L;

	@Property(value = "#{newsService}")
	private NewsService newsService;

	@Property(value = "#{sessionScope.period}")
	private Period period;

	private CourseDataModel courseData = new CourseDataModel();

	/**
	 * Refreshing institute entity
	 * 
	 * @throws Exception
	 */
	@Prerender
	public void prerender() throws LectureException {
		super.prerender();
		if (period == null && institute != null || institute != null && !institute.getPeriods().contains(period)) {
			period = institute.getActivePeriod();
			if (period == null && institute.getPeriods().size() > 0) {
				period = institute.getPeriods().get(0);
			}
		} else {
			period = lectureService.getPeriod(period.getId());
		}
		setSessionBean(Constants.PERIOD, period);
		//breadcrumbs shall not be displayed here
		crumbs.clear();
	}

	/**
	 * Value Change Listener to swithc password input text on and off.
	 * 
	 * @param event
	 */
	public void processPeriodSelectChanged(ValueChangeEvent event) {
		final Long periodId = (Long) event.getNewValue();
		period = lectureService.getPeriod(periodId);
		setSessionBean(Constants.PERIOD, period);
	}

	public String resendActivationMail(){
		getLectureService().sendActivationCode(institute);
		addMessage(i18n("institute_activationcode_send"));
		return Constants.SUCCESS;
	}
	
	private class CourseDataModel extends AbstractPagedTable<Course> {

		private static final long serialVersionUID = 3682383483634321520L;

		private DataPage<Course> page;

		@Override
		public DataPage<Course> getDataPage(int startRow, int pageSize) {
			if (page == null) {
				List<Course> courses = new ArrayList<Course>();
				if (period != null) {
					period = lectureService.getPeriod(period.getId());
					courses.addAll(period.getCourses());
				} else {
					logger.error("institute page - no period selected!");
				}
				sort(courses);
				page = new DataPage<Course>(courses.size(), 0, courses);
			}
			return page;
		}
	}
	
	/**
	 * Apply for Membership.
	 * @return outcome
	 * @throws LectureException 
	 */
	public String applyForMembership() throws LectureException {
		if (user != null && institute != null) {
			lectureService.addInstituteAspirant(user.getId(), institute.getId());
			addMessage(i18n("institute_message_application_of_membership_send"));
		}
		return Constants.SUCCESS;
	}

	/**
	 * Current news items of institute
	 * 
	 * @return
	 * @throws LectureException
	 */
	public Collection getCurrentNewsItems() {
		return newsService.getCurrentNewsItems(institute, 10);
	}

	public NewsService getNewsService() {
		return newsService;
	}

	public void setNewsService(NewsService newsService) {
		this.newsService = newsService;
	}

	public String showApplication() {
		return Constants.DESKTOP;
	}

	public CourseDataModel getCourseData() {
		return courseData;
	}

	public void setCourseData(CourseDataModel courseData) {
		this.courseData = courseData;
	}

	public String shortcutCourse() throws DesktopException {
		Course course = courseData.getRowData();
		desktopService.linkCourse(desktop, course);
		addMessage(i18n("message_course_shortcut_created"));
		return Constants.INSTITUTE_PAGE;
	}

	public Period getPeriod() {
		return period;
	}

	public void setPeriod(Period period) {
		this.period = period;
	}
}
