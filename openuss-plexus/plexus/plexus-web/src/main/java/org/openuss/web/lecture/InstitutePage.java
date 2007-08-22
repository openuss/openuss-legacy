package org.openuss.web.lecture;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.faces.event.ValueChangeEvent;

import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Property;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.Prerender;
import org.apache.shale.tiger.view.View;
import org.openuss.desktop.DesktopException;
import org.openuss.framework.web.jsf.model.AbstractPagedTable;
import org.openuss.framework.web.jsf.model.DataPage;
import org.openuss.lecture.Course;
import org.openuss.lecture.CourseInfo;
import org.openuss.lecture.LectureException;
import org.openuss.lecture.Period;
import org.openuss.news.NewsItemInfo;
import org.openuss.news.NewsService;
import org.openuss.web.Constants;

/**
 * @author Ingo Dueppe
 * @author Sebastian Roekens
 * @author Malte Stockmann
 * @author Kai Stettner
 */
@View
@Bean(name = "views$public$institute$institute", scope = Scope.REQUEST)
public class InstitutePage extends AbstractLecturePage {

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
		
		// temporär auskommentiert
		// KStettner 11.08.07
		/*List periods = null;
		Long universityId = 0l;
		Long departmentId = 0l;
		
		if (instituteInfo != null) {
			departmentId = instituteInfo.getDepartmentId();
			departmentInfo = departmentService.findDepartment(departmentId);
			universityId = departmentInfo.getUniversityId();
			universityService.findActivePeriodByUniversity(universityId);
			periods = universityService.findPeriodsByUniversity(universityId);
		} 
		
		
		if (periodInfo == null && instituteInfo != null || instituteInfo != null && !periods.contains(period)) {
			periodInfo = universityService.findActivePeriodByUniversity(universityId);
			if (periodInfo == null && periods.size() > 0) {
				periodInfo = (PeriodInfo)periods.get(0);
			}
		} else {
			//periodInfo = lectureService.getPeriod(period.getId());
			
		}
		setSessionBean(Constants.PERIOD_INFO, periodInfo);*/
		//breadcrumbs shall not be displayed here
		crumbs.clear();
	}

	/**
	 * Value Change Listener to switch password input text on and off.
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
	
	private class CourseDataModel extends AbstractPagedTable<CourseInfo> {

		private static final long serialVersionUID = 3682383483634321520L;

		private DataPage<CourseInfo> page;

		@Override
		public DataPage<CourseInfo> getDataPage(int startRow, int pageSize) {
			if (page == null) {
				List<CourseInfo> courses = new ArrayList<CourseInfo>();
				/*if (period != null) {
					period = lectureService.getPeriod(period.getId());
					courses.addAll(period.getCourses());
				} else {
					logger.error("institute page - no period selected!");
				}*/
				sort(courses);
				page = new DataPage<CourseInfo>(courses.size(), 0, courses);
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
	public Collection<NewsItemInfo> getCurrentNewsItems() {
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
		CourseInfo courseInfo = courseData.getRowData();
		//desktopService.linkCourse(desktop, course);
		desktopService2.linkCourse(desktopInfo.getId(), courseInfo.getId());
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
