package org.openuss.web.lecture;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

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
import org.openuss.framework.web.jsf.util.AcegiUtils;
import org.openuss.lecture.ApplicationInfo;
import org.openuss.lecture.CourseInfo;
import org.openuss.lecture.CourseMemberInfo;
import org.openuss.lecture.CourseMemberType;
import org.openuss.lecture.LectureException;
import org.openuss.lecture.OrganisationServiceException;
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

	private CourseDataModel courseData = new CourseDataModel();

	private List<PeriodInfo> periodInfos = null;
	private Long universityId;
	private Long departmentId;
	private List<SelectItem> institutePeriodItems;
	private List<PeriodInfo> institutePeriods;

	@Prerender
	@SuppressWarnings( { "unchecked" })
	public void prerender() throws Exception {
		super.prerender();
		if (isRedirected()) {
			return;
		}
		if (instituteInfo != null) {
			departmentId = instituteInfo.getDepartmentId();
			departmentInfo = departmentService.findDepartment(departmentId);
			universityId = departmentInfo.getUniversityId();
			universityInfo = universityService.findUniversity(universityId);
			periodInfos = universityService.findPeriodsByInstituteWithCoursesOrActive(instituteInfo);

			checkForApplicationMessage();
		}

		if (periodInfo != null && instituteInfo != null && !periodInfos.contains(periodInfo)) {
			if (periodInfo.getId() != null) {
				// if id of period is ALL_ACTIVE_PERIODS or ALL_PERIODS do nothing. Remain selected!
				if ((periodInfo.getId().longValue() == Constants.COURSES_ALL_PERIODS)
						|| (periodInfo.getId().longValue() == Constants.COURSES_ALL_ACTIVE_PERIODS)) {
					// do nothing
					// Suppose the case you switch the institute and in the old
					// institute page there was a period selected which is not
					// available in the new institute(this should be always!). 
					// --> The combobox value switches to ALL_ACTIVE_PERIODS
				} else {
					periodInfo.setId(Constants.COURSES_ALL_ACTIVE_PERIODS);
					periodInfo.setName(i18n("all_active_periods"));
				}
			}
			// Suppose the case you initially starting the application no
			// periods are selected --> no periodInfo VO was initiated.
			// Set default selection to ALL_ACTIVE_PERIODS
			if ((periodInfo.getId() == null) && (periodInfos.size() > 0)) {
				periodInfo.setId(Constants.COURSES_ALL_ACTIVE_PERIODS);
				periodInfo.setName(i18n("all_active_periods"));
			}

			if (periodInfos.size() < 1) {
				// normally this should never happen due to the fact that each
				// university has a standard period!
				periodInfo = new PeriodInfo();
			}
		}

		setBean(Constants.PERIOD_INFO, periodInfo);
		addBreadCrumbs();
	}

	@SuppressWarnings("unchecked")
	private void checkForApplicationMessage() {
		// FIXME This should be done within the view. 
		Boolean hasPermission = securityService.hasPermission(instituteInfo, new Integer[] { LectureAclEntry.INSTITUTE_ADMINISTRATION });
		
		if (hasPermission) {
			List<ApplicationInfo> applications = instituteService.findApplicationsByInstitute(instituteInfo.getId());
			Iterator<ApplicationInfo> iterApplication = applications.iterator();
			while (iterApplication.hasNext()) {
				ApplicationInfo currentApplication = iterApplication.next();
				if (currentApplication.isConfirmed() == false) {
					addMessage(i18n("application_pending_info", currentApplication.getDepartmentInfo().getName()));
					addMessage(i18n("application_pending_responsible_info", currentApplication.getDepartmentInfo().getOwnerName()));
				}
			}
		}
	}

	public void addBreadCrumbs() {
		breadcrumbs.loadInstituteCrumbs(instituteInfo);
	}

	private CourseInfo currentCourse() {
		return courseData.getRowData();
	}

	public boolean getInstituteBookmarked() {
		if (desktopInfo == null || desktopInfo.getId() == null) {
			refreshDesktop();
		}
		return desktopInfo.getInstituteInfos().contains(instituteInfo);
	}

	// FIXME Refactor unclear naming
	public boolean isCourseBookmarked() {
		if (desktopInfo == null || desktopInfo.getId() == null) {
			refreshDesktop();
		}
		if (courseData == null || courseData.getRowIndex() == -1) {
			// prevent errors in preprocess phase
			return false;
		}
		return desktopInfo.getCourseInfos().contains(currentCourse());
	}

	/**
	 * Store the selected course into session scope and go to course main page.
	 * 
	 * @return Outcome
	 */
	public String selectCourse() {
		logger.debug("Starting method selectCourse");
		CourseInfo course = currentCourse();
		logger.debug("Returning to method selectCourse");
		logger.debug(course.getId());
		setBean(Constants.COURSE_INFO, course);

		return Constants.COURSE_PAGE;
	}

	/**
	 * Bookmarks the selected course on the MyUni Page.
	 * 
	 * @return Outcome
	 */
	public String shortcutCourse() {
		// courseInfo = courseData.getRowData();
		try {
			CourseInfo currentCourse = currentCourse();
			if (desktopInfo == null || desktopInfo.getId() == null) {
				refreshDesktop();
			}
			desktopService2.linkCourse(desktopInfo.getId(), currentCourse.getId());
			refreshDesktop();
			addMessage(i18n("desktop_command_add_course_succeed"));
			return Constants.SUCCESS;
		} catch (DesktopException e) {
			logger.error(e);
			addError(i18n(e.getMessage()));
			return Constants.FAILURE;
		}
	}

	private boolean isAssistant(CourseInfo courseInfo) {
		return AcegiUtils.hasPermission(courseInfo, new Integer[] {LectureAclEntry.ASSIST});
	}
	
	private boolean isParticipant(CourseInfo course){
		CourseMemberInfo courseMember = getCourseService().getMemberInfo(course, user);
		if (courseMember != null){
			return courseMember.getMemberType().equals(CourseMemberType.PARTICIPANT);
		}
		return false;
	}

	public String removeCourseShortcut() {
		try {
			// courseInfo = courseData.getRowData();
			CourseInfo currentCourse = currentCourse();
			if (isAssistant(currentCourse) && isParticipant(currentCourse)){
				return removeMembership();
			}
			if (desktopInfo == null || desktopInfo.getId() == null) {
				refreshDesktop();
			}
			desktopService2.unlinkCourse(desktopInfo.getId(), currentCourse.getId());
			refreshDesktop();
		} catch (Exception e) {
			addError(i18n("course_error_remove_shortcut"), e.getMessage());
			return Constants.FAILURE;
		}

		addMessage(i18n("desktop_mesage_removed_course_succeed"));
		return Constants.SUCCESS;
	}

	/**
	 * Adds a shortcut to the institute
	 * 
	 * @return
	 */
	public String addShortcut() {
		try {
			if (desktopInfo == null || desktopInfo.getId() == null) {
				refreshDesktop();
			}
			desktopService2.linkInstitute(desktopInfo.getId(), instituteInfo.getId());
			refreshDesktop();
		} catch (Exception e) {
			addError(i18n("institute_error_shortcut"), e.getMessage());
			return Constants.FAILURE;
		}

		this.addMessage(i18n("institute_success_shortcut"));
		return Constants.SUCCESS;
	}

	public String removeMembership() {
		courseInfo = this.courseData.getRowData();
		setBean(Constants.COURSE_INFO, courseInfo);
		return Constants.COURSE_REMOVE_MEMBER;
	}

	/**
	 * Removes the shortcut to the institute
	 * 
	 * @return
	 */
	public String removeShortcut() {
		try {
			if (desktopInfo == null || desktopInfo.getId() == null) {
				refreshDesktop();
			}
			desktopService2.unlinkInstitute(desktopInfo.getId(), instituteInfo.getId());
			refreshDesktop();
		} catch (Exception e) {
			addError(i18n("institute_error_remove_shortcut"), e.getMessage());
			return Constants.FAILURE;
		}

		addMessage(i18n("institute_success_remove_shortcut"));
		return Constants.SUCCESS;
	}

	@SuppressWarnings( { "unchecked" })
	public List<SelectItem> getBelongingInstitutePeriods() {

		institutePeriodItems = new ArrayList<SelectItem>();

		if (instituteInfo != null) {
			Long departmentId = instituteInfo.getDepartmentId();
			departmentInfo = departmentService.findDepartment(departmentId);
			Long universityId = departmentInfo.getUniversityId();
			universityInfo = universityService.findUniversity(universityId);
			// get all Period which are active and/or assigned to a course of
			// the institute
			institutePeriods = universityService.findPeriodsByInstituteWithCoursesOrActive(instituteInfo);

			Iterator<PeriodInfo> iter = institutePeriods.iterator();
			PeriodInfo periodInfo;

			// create item in combobox displaying all active periods
			SelectItem itemAllActivePeriods = new SelectItem(Constants.COURSES_ALL_ACTIVE_PERIODS,
					i18n("all_active_periods"));
			institutePeriodItems.add(itemAllActivePeriods);
			// create item in combobox displaying all periods
			SelectItem itemAllPeriods = new SelectItem(Constants.COURSES_ALL_PERIODS, i18n("all_periods"));
			institutePeriodItems.add(itemAllPeriods);

			while (iter.hasNext()) {
				periodInfo = iter.next();
				SelectItem item = new SelectItem(periodInfo.getId(), periodInfo.getName());
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
			periodInfo.setName(i18n("all_periods"));
		} else if (periodId.longValue() == Constants.COURSES_ALL_ACTIVE_PERIODS) {
			periodInfo.setName(i18n("all_active_periods"));
		} else {
			periodInfo = universityService.findPeriod(periodId);
			setBean(Constants.PERIOD_INFO, periodInfo);
		}
	}

	/**
	 * Resends the activation mail for the institute again.
	 * 
	 * @param event
	 */
	public String resendActivationMail() {
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
		@SuppressWarnings( { "unchecked" })
		public DataPage<CourseInfo> getDataPage(int startRow, int pageSize) {
			if (page == null) {
				if (periodInfo != null) {
					if ((periodInfo.getId() != null)
							&& (periodInfo.getId().longValue() == Constants.COURSES_ALL_PERIODS)) {
						coursesByPeriodAndInstitute = courseService.findCoursesByInstituteAndEnabled(instituteInfo
								.getId(), true);
					} else if ((periodInfo.getId() != null)
							&& (periodInfo.getId().longValue() == Constants.COURSES_ALL_ACTIVE_PERIODS)) {
						coursesByPeriodAndInstitute = courseService.findCoursesByActivePeriodsAndEnabled(instituteInfo
								.getId(), true);
					} else {
						coursesByPeriodAndInstitute = courseService.findCoursesByPeriodAndInstituteAndEnabled(
								periodInfo.getId(), instituteInfo.getId(), true);
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
	 * 
	 * @return outcome
	 * @throws LectureException
	 */
	public String applyForMembership() throws LectureException {
		if (user != null && instituteInfo != null) {
			try {
				organisationService.addAspirant(instituteInfo.getId(), user.getId());
				addMessage(i18n("institute_message_application_of_membership_send"));
			} catch (OrganisationServiceException e) {
				logger.debug(e.getMessage());
				addError(i18n("institute_error_apply_member_at_institute_already_applied_personal"));
			} catch (Exception e) {
				logger.debug(e.getMessage());
				addError("institute_error_apply_member_at_institute_personal");
			}
		}
		return Constants.SUCCESS;
	}

	/**
	 * Current news items of institute
	 * 
	 * @return
	 * @throws LectureException
	 */
	@SuppressWarnings( { "unchecked" })
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
