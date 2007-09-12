package org.openuss.web.lecture;

import static org.openuss.web.lecture.AbstractLecturePage.logger;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.faces.el.ValueBinding;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Property;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.Prerender;
import org.apache.shale.tiger.view.View;
import org.openuss.desktop.DesktopException;
import org.openuss.framework.web.jsf.model.AbstractPagedTable;
import org.openuss.framework.web.jsf.model.DataPage;
import org.openuss.lecture.ApplicationInfo;
import org.openuss.lecture.CourseInfo;
import org.openuss.lecture.LectureException;
import org.openuss.lecture.PeriodInfo;
import org.openuss.news.NewsItemInfo;
import org.openuss.news.NewsService;
import org.openuss.security.acl.LectureAclEntry;
import org.openuss.web.Constants;

/**
 * Backing bean for the institute page.
 * 
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
	
	private List<PeriodInfo> periodInfos = null;
	private Long universityId = 0l;
	private Long departmentId = 0l;
	private List<SelectItem> institutePeriodItems;
	private List<PeriodInfo> institutePeriods;
	
	private ValueBinding binding = getFacesContext().getApplication().createValueBinding("#{visit.locale}");
	private String locale = (String)binding.getValue(getFacesContext());
	private ResourceBundle bundle = ResourceBundle.getBundle("resources", new Locale(locale));

	@Prerender
	@SuppressWarnings( { "unchecked" })
	public void prerender() throws LectureException {
		super.prerender();
		
		if (instituteInfo != null) {
			departmentId = instituteInfo.getDepartmentId();
			departmentInfo = departmentService.findDepartment(departmentId);
			universityId = departmentInfo.getUniversityId();
			universityInfo = universityService.findUniversity(universityId);
			periodInfos = universityService.findPeriodsByInstituteWithCoursesOrActive(instituteInfo);
			
			Boolean hasPermission = securityService.hasPermission(instituteInfo,
					new Integer[] { LectureAclEntry.INSTITUTE_ADMINISTRATION });
			if(hasPermission) {
				List<ApplicationInfo> applications = instituteService.findApplicationsByInstitute(instituteInfo.getId());
				Iterator<ApplicationInfo> iterApplication = applications.iterator();
				while(iterApplication.hasNext()) {
					ApplicationInfo currentApplication = iterApplication.next();
					if (currentApplication.isConfirmed() == false) {
						addMessage(i18n("application_pending_info", currentApplication.getDepartmentInfo().getName()));
					}
				}
			}
		} 
		
		if (periodInfo == null && instituteInfo != null || instituteInfo != null && !periodInfos.contains(periodInfo)) {
			if (periodInfo.getId() != null) {
				if((periodInfo.getId().longValue() == Constants.COURSES_ALL_PERIODS) || (periodInfo.getId().longValue() == Constants.COURSES_ALL_ACTIVE_PERIODS)) {
				// do nothing
				}
			}
			if ((periodInfo.getId() == null) && (periodInfos.size() > 0)) {
				periodInfo = periodInfos.get(0);
			}
			
			if (periodInfos.size() < 1) {
				// normally this should never happen due to the fact that each university has a standard period!
				periodInfo = new PeriodInfo();	
			}
	
		} 
		
		
		
		
		setSessionBean(Constants.PERIOD_INFO, periodInfo);
		addBreadCrumbs();
	}
	
	
	public void addBreadCrumbs()
	{	
		breadcrumbs.loadInstituteCrumbs(instituteInfo);
	}
	
	private CourseInfo currentCourse() {
		
		CourseInfo course = courseData.getRowData();
		
		return course;
	}
	
	public Boolean getBookmarked()
	{
		try {
			return desktopService2.isInstituteBookmarked(instituteInfo.getId(), user.getId());
		} catch (Exception e) {
			
		}
		
		return false;
	}

	public Boolean getBookmarked2()
	{
		try {
			CourseInfo currentCourse = currentCourse();
			return desktopService2.isCourseBookmarked(currentCourse.getId(), user.getId());
		} catch (Exception e) {
			
		}
		
		return false;
	}

	/**
	 * Store the selected course into session scope and go to course main page.
	 * @return Outcome
	 */
	public String selectCourse() {
		logger.debug("Starting method selectCourse");
		CourseInfo course = currentCourse();
		logger.debug("Returning to method selectCourse");
		logger.debug(course.getId());	
		setSessionBean(Constants.COURSE_INFO, course);
		
		return Constants.COURSE_PAGE;
	}
	
	/**
	 * Bookmarks the selected course on the MyUni Page.
	 * @return Outcome
	 */
	public String shortcutCourse() {
		//courseInfo = courseData.getRowData();
		try {
			CourseInfo currentCourse = currentCourse();
			desktopService2.linkCourse(desktopInfo.getId(), currentCourse.getId());
			addMessage(i18n("desktop_command_add_course_succeed"));
			return Constants.SUCCESS;
		} catch (DesktopException e) {
			logger.error(e);
			addError(i18n(e.getMessage()));
			return Constants.FAILURE;
		}
	}

	public String removeCourseShortcut()
	{
		try {
			//courseInfo = courseData.getRowData();
			CourseInfo currentCourse = currentCourse();
			desktopService2.unlinkCourse(desktopInfo.getId(), currentCourse.getId());
		} catch (Exception e) {
			addError(i18n("institute_error_remove_shortcut"), e.getMessage());
			return Constants.FAILURE;
		}
		
		addMessage(i18n("institute_success_remove_shortcut"));
		return Constants.SUCCESS;
	}

		
	/**
	 * Adds a shortcut to the institute
	 * @return
	 */
	public String addShortcut()
	{
		try {
			desktopService2.linkInstitute(desktopInfo.getId(), instituteInfo.getId());
		} catch (Exception e) {
			addError(i18n("institute_error_shortcut"), e.getMessage());
			return Constants.FAILURE;
		}
		
		this.addMessage(i18n("institute_success_shortcut"));
		return Constants.SUCCESS;
	}
	
	/**
	 * Removes the shortcut to the institute
	 * @return
	 */
	public String removeShortcut()
	{
		try {
			desktopService2.unlinkInstitute(desktopInfo.getId(), instituteInfo.getId());
		} catch (Exception e) {
			addError(i18n("institute_error_remove_shortcut"), e.getMessage());
			return Constants.FAILURE;
		}
		
		addMessage(i18n("institute_success_remove_shortcut"));
		return Constants.SUCCESS;
	}
	
	public List<SelectItem> getBelongingInstitutePeriods() {
		
		institutePeriodItems = new ArrayList<SelectItem>();
		
		if (instituteInfo != null) {
			Long departmentId = instituteInfo.getDepartmentId();
			departmentInfo = departmentService.findDepartment(departmentId);
			Long universityId = departmentInfo.getUniversityId();
			universityInfo = universityService.findUniversity(universityId);
			//get all Period which are active and/or assigned to a course of the institute
			institutePeriods = universityService.findPeriodsByInstituteWithCoursesOrActive(instituteInfo);

			Iterator<PeriodInfo> iter =  institutePeriods.iterator();
			PeriodInfo periodInfo;
			
			//create item in combobox displaying all active periods
			SelectItem itemAllActivePeriods = new SelectItem(Constants.COURSES_ALL_ACTIVE_PERIODS, bundle.getString("all_active_periods"));
			institutePeriodItems.add(itemAllActivePeriods);
			//create item in combobox displaying all periods
			SelectItem itemAllPeriods = new SelectItem(Constants.COURSES_ALL_PERIODS, bundle.getString("all_periods"));
			institutePeriodItems.add(itemAllPeriods);
			
			while (iter.hasNext()) {
				periodInfo = iter.next();
				SelectItem item = new SelectItem(periodInfo.getId(),periodInfo.getName());
				institutePeriodItems.add(item);
			}
			
		} 
		
		return institutePeriodItems;
	}
	
	/**
	 * Value Change Listener to change name of data table.
	 * 
	 * @param event
	 */
	public void processPeriodSelectChanged(ValueChangeEvent event) {
		final Long periodId = (Long) event.getNewValue();
		if (periodId.longValue() == Constants.COURSES_ALL_PERIODS) {
			periodInfo.setName(bundle.getString("all_periods"));
		} else if (periodId.longValue() == Constants.COURSES_ALL_ACTIVE_PERIODS) {
			periodInfo.setName(bundle.getString("all_active_periods"));
		} else {
			periodInfo = universityService.findPeriod(periodId);
			setSessionBean(Constants.PERIOD_INFO, periodInfo);
		}
	}

	/**
	 * Resends the activation mail for the institute again.
	 * 
	 * @param event
	 */
	public String resendActivationMail(){
		instituteService.resendActivationCode(instituteInfo, user.getId());
		addMessage(i18n("institute_activationcode_send"));
		return Constants.SUCCESS;
	}
	
	private class CourseDataModel extends AbstractPagedTable<CourseInfo> {

		private static final long serialVersionUID = 3682383483634321520L;

		private DataPage<CourseInfo> page;
		
		private List<CourseInfo> courses = new ArrayList<CourseInfo>();
		private List<CourseInfo> coursesByPeriodAndInstitute = null;

		@Override
		public DataPage<CourseInfo> getDataPage(int startRow, int pageSize) {
			if (page == null) {
				if (periodInfo != null) {
					if ((periodInfo.getId() != null) && (periodInfo.getId().longValue() == Constants.COURSES_ALL_PERIODS)) {
						coursesByPeriodAndInstitute = courseService.findCoursesByInstituteAndEnabled(instituteInfo.getId(), true);
					} else if ((periodInfo.getId() != null) && (periodInfo.getId().longValue() == Constants.COURSES_ALL_ACTIVE_PERIODS)) {
						coursesByPeriodAndInstitute = courseService.findCoursesByActivePeriodsAndEnabled(instituteInfo.getId(), true);
					} else {
						coursesByPeriodAndInstitute = courseService.findCoursesByPeriodAndInstituteAndEnabled(periodInfo.getId(), instituteInfo.getId(), true);
					}		
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
			organisationService.addAspirant(instituteInfo.getId(), user.getId());
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
		return newsService.getCurrentNewsItems(instituteInfo, 10);
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

	public PeriodInfo getPeriodInfo() {
		return periodInfo;
	}

	public void setPeriodInfo(PeriodInfo periodInfo) {
		this.periodInfo = periodInfo;
	}
}
