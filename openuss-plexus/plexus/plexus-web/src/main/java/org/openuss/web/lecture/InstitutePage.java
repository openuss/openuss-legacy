package org.openuss.web.lecture;

import static org.openuss.web.lecture.AbstractLecturePage.logger;

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
import org.openuss.framework.jsfcontrols.breadcrumbs.BreadCrumb;
import org.openuss.framework.web.jsf.model.AbstractPagedTable;
import org.openuss.framework.web.jsf.model.DataPage;
import org.openuss.lecture.CourseInfo;
import org.openuss.lecture.LectureException;
import org.openuss.lecture.PeriodInfo;
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

	@Property(value = "#{periodInfo}")
	private PeriodInfo periodInfo;
	
	private CourseDataModel courseData = new CourseDataModel();

	/**
	 * Refreshing institute entity
	 * 
	 * @throws Exception
	 */
	@Prerender
	public void prerender() throws LectureException {
		super.prerender();
		addBreadCrumbs();
		
		List<PeriodInfo> periodInfos = null;
		Long universityId = 0l;
		Long departmentId = 0l;
		
		if (instituteInfo != null) {
			departmentId = instituteInfo.getDepartmentId();
			departmentInfo = departmentService.findDepartment(departmentId);
			universityId = departmentInfo.getUniversityId();
			universityInfo = universityService.findUniversity(universityId);
			periodInfos = universityService.findActivePeriodsByUniversity(universityId);
		} 
		
		if (periodInfo == null && instituteInfo != null || instituteInfo != null && !periodInfos.contains(periodInfo)) {
			periodInfo = periodInfos.get(0);
			if (periodInfo == null && periodInfos.size() > 0) {
				periodInfo = periodInfos.get(0);
			}
		} else {
			periodInfo = universityService.findPeriod(periodInfo.getId());
			
		}
			
		setSessionBean(Constants.PERIOD_INFO, periodInfo);
		//breadcrumbs shall not be displayed here
	}
	
	public void addBreadCrumbs()
	{	
		breadcrumbs.loadInstituteCrumbs(instituteInfo);
	}

	/**
	 * Value Change Listener to switch password input text on and off.
	 * 
	 * @param event
	 */
	public void processPeriodSelectChanged(ValueChangeEvent event) {
		final Long periodId = (Long) event.getNewValue();
		periodInfo = universityService.findPeriod(periodId);
		setSessionBean(Constants.PERIOD_INFO, periodInfo);
	}

	/*public String resendActivationMail(){
		getLectureService().sendActivationCode(institute);
		addMessage(i18n("institute_activationcode_send"));
		return Constants.SUCCESS;
	}*/
	
	private class CourseDataModel extends AbstractPagedTable<CourseInfo> {

		private static final long serialVersionUID = 3682383483634321520L;

		private DataPage<CourseInfo> page;

		@Override
		public DataPage<CourseInfo> getDataPage(int startRow, int pageSize) {
			if (page == null) {
				List<CourseInfo> courses = new ArrayList<CourseInfo>();
				if (periodInfo != null) {
					//period = lectureService.getPeriod(period.getId());
					List<CourseInfo> coursesByPeriodAndInstitute = courseService.findCoursesByPeriodAndInstitute(periodInfo.getId(), instituteInfo.getId());
					if (coursesByPeriodAndInstitute != null) {
						courses.addAll(coursesByPeriodAndInstitute);
					}
				} else {
					logger.error("institute page - no period selected!");
				}
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

	public PeriodInfo getPeriodInfo() {
		return periodInfo;
	}

	public void setPeriodInfo(PeriodInfo periodInfo) {
		this.periodInfo = periodInfo;
	}
}
