package org.openuss.web.lecture;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

import org.apache.log4j.Logger;
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
import org.openuss.lecture.CourseTypeInfo;
import org.openuss.lecture.LectureException;
import org.openuss.lecture.PeriodInfo;
import org.openuss.web.Constants;

/**
 * CourseType Administration Page
 * @author Ingo Düppe
 * @author Kai Stettner
 * @author Sebastian Roekens
 * 
 */
@Bean(name = "views$secured$lecture$institutecourses", scope = Scope.REQUEST)
@View
public class InstituteCoursesPage extends AbstractLecturePage {

	/** logger **/
	public static final Logger logger = Logger.getLogger(InstituteCoursesPage.class);

	private LocalDataModelCourses dataCourses = new LocalDataModelCourses();

	private List<SelectItem> institutePeriodItems;
	
	private List<PeriodInfo> institutePeriodsActive;
	
	private List<PeriodInfo> institutePeriodsPassive;
	
	private List<SelectItem> instituteCourseTypeItems;
	
	private List<CourseTypeInfo> instituteCourseTypes;

	private List<PeriodInfo> periodInfos;
	
	private Long universityId;
	
	private Long departmentId;
	
	private boolean moving = false;

	@Property(value = "#{"+Constants.COURSE_MOVE_INFO+"}")
	protected CourseInfo courseMoveInfo;	
	
	@Prerender
	@SuppressWarnings( { "unchecked" })
	public void prerender() throws LectureException {
		super.prerender();
		if (isRedirected()){
			return;
		}
		if (instituteInfo != null) {
			departmentId = instituteInfo.getDepartmentId();
			departmentInfo = departmentService.findDepartment(departmentId);
			universityId = departmentInfo.getUniversityId();
			universityInfo = universityService.findUniversity(universityId);
			periodInfos = universityService.findPeriodsByInstituteWithCoursesOrActive(instituteInfo);
		}
		if (courseInfo!=null && courseInfo.getId()!=null){
			courseInfo = courseService.findCourse(courseInfo.getId());
			setBean(Constants.COURSE_INFO, courseInfo);
			moving=true;
		}
		if (moving && (courseMoveInfo == null || courseMoveInfo.getId()==null || !courseMoveInfo.getId().equals(courseInfo.getId()))){
			courseMoveInfo = courseInfo;
			setSessionBean(Constants.COURSE_MOVE_INFO, courseMoveInfo);
		}
		if (periodInfo != null && instituteInfo != null && !periodInfos.contains(periodInfo)) {
			if (periodInfo.getId() != null) {
				if ((periodInfo.getId().longValue() == Constants.COURSES_ALL_PERIODS)
						|| (periodInfo.getId().longValue() == Constants.COURSES_ALL_ACTIVE_PERIODS)) {
					periodInfo = periodInfos.get(0);
				} else {
					periodInfo = universityService.findPeriod(periodInfo.getId());
				}
			}
			if ((periodInfo.getId() == null) && (periodInfos.size() > 0)) {
				periodInfo = periodInfos.get(0);
			}
			if (periodInfos.size() < 1) {
				// normally this should never happen due to the fact that each
				// university has a standard period!
				periodInfo = new PeriodInfo();
				periodInfo.setName("N/A");
			}
		}

		addPageCrumbs();
		setBean(Constants.PERIOD_INFO, periodInfo);
	}

	private void addPageCrumbs() {
		BreadCrumb crumb = new BreadCrumb();
		crumb.setLink("");
		crumb.setName(i18n("institute_courses_header"));

		breadcrumbs.loadInstituteCrumbs(instituteInfo);
		breadcrumbs.addCrumb(crumb);
	}

	/**
	 * Creates a new Course object and sets it into session scope
	 * 
	 * @return outcome
	 */
	public String addCourse() throws DesktopException {
		logger.debug("Starting method addCourse");
		courseInfo = new CourseInfo();
		courseInfo.setCourseTypeDescription(courseTypeInfo.getDescription());
		courseInfo.setCourseTypeId(courseTypeInfo.getId());
		courseInfo.setPeriodId(periodInfo.getId());
		courseInfo.setPeriodName(periodInfo.getName());
		courseInfo.setInstituteId(courseTypeInfo.getInstituteId());

		courseService.create(courseInfo);
		courseInfo=null;
		setBean(Constants.COURSE_INFO, courseInfo);
		addMessage(i18n("institute_message_persist_coursetype_succeed"));
		setSessionBean(Constants.COURSE_INFO, courseInfo);
		return Constants.COURSE_OPTIONS_PAGE;
	}

	public String saveCourse() throws DesktopException {
		logger.debug("Starting method saveCourse");

		courseService.updateCourse(courseMoveInfo);
		addMessage(i18n("institute_message_persist_coursetype_succeed"));
		moving = false;
		redirect(Constants.INSTITUTE_COURSES_PAGE);
		return Constants.SUCCESS;
	}

	/**
	 * Beginning move mode of course to a different period
	 * @return outcome
	 */
	public String moveCourse() {
		logger.debug("Switching to move mode");
		courseInfo= currentCourse();
		courseMoveInfo = currentCourse();
		setBean(Constants.COURSE_INFO, courseInfo);
		setSessionBean(Constants.COURSE_MOVE_INFO, courseMoveInfo);
		moving = true;
		
		return Constants.SUCCESS;
	}

	/**
	 * Canceling move mode of course 
	 * @return outcome
	 */
	public String cancelCourse() {
		logger.debug("Switching to move mode");
		setBean(Constants.COURSE_INFO, null);
		setSessionBean(Constants.COURSE_MOVE_INFO, null);
		moving = false;
		return Constants.SUCCESS;
	}

	private CourseInfo currentCourse() {
		CourseInfo course = dataCourses.getRowData();
		return course;
	}

	/**
	 * Disables the chosen course. This is just evident for the search indexing.
	 * 
	 * @return Outcome
	 */
	public String disableCourse() {
		logger.debug("Starting method disableCourse");
		CourseInfo currentCourse = currentCourse();
		courseService.setCourseStatus(currentCourse.getId(), false);

		addMessage(i18n("message_course_disabled"));
		return Constants.SUCCESS;
	}

	/**
	 * Enables the chosen course. This is just evident for the search indexing.
	 * 
	 * @return Outcome
	 */
	public String enableCourse() {
		logger.debug("Starting method enableCourse");
		CourseInfo currentCourse = currentCourse();
		courseService.setCourseStatus(currentCourse.getId(), true);

		addMessage(i18n("message_course_enabled"));
		return Constants.SUCCESS;
	}

	/**
	 * Store the selected course into session scope and go to course remove
	 * confirmation page.
	 * 
	 * @return Outcome
	 */
	public String selectCourseAndConfirmRemove() {
		logger.debug("Starting method selectCourseAndConfirmRemove");
		CourseInfo currentCourse = currentCourse();
		logger.debug("Returning to method selectCourseAndConfirmRemove");
		logger.debug(currentCourse.getId());
		setBean(Constants.COURSE_INFO, currentCourse);

		return Constants.COURSE_CONFIRM_REMOVE_PAGE;
	}

	/**
	 * Bookmarks the selected course on the MyUni Page.
	 * 
	 * @return Outcome
	 */
	public String shortcutCourse() {
		try {
			CourseInfo currentCourse = currentCourse();
			if (desktopInfo==null){
				refreshDesktop();
			}
			desktopService2.linkCourse(desktopInfo.getId(), currentCourse.getId());
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
			desktopService2.unlinkCourse(desktopInfo.getId(), currentCourse().getId());
		} catch (Exception e) {
			addError(i18n("institute_error_remove_shortcut"), e.getMessage());
			return Constants.FAILURE;
		}

		addMessage(i18n("institute_success_remove_shortcut"));
		return Constants.SUCCESS;
	}

	public Boolean getBookmarked() {
		try {
			CourseInfo currentCourse = currentCourse();
			return desktopService2.isCourseBookmarked(currentCourse.getId(), user.getId());
		} catch (Exception e) {
			logger.error(e);
		}
		return false;
	}

	public LocalDataModelCourses getDataCourses() {
		return dataCourses;
	}

	public void setDataCourses(LocalDataModelCourses dataCourses) {
		this.dataCourses = dataCourses;
	}

	/**
	 * Gets all periods of the institute.
	 * 
	 * @return outcome
	 */
	@SuppressWarnings( { "unchecked" })
	public List<SelectItem> getAllPeriodsOfInstitute() {

		institutePeriodItems = new ArrayList<SelectItem>();

		if (instituteInfo != null) {
			Long departmentId = instituteInfo.getDepartmentId();
			departmentInfo = departmentService.findDepartment(departmentId);
			Long universityId = departmentInfo.getUniversityId();
			universityInfo = universityService.findUniversity(universityId);
			// gets all periods of the institute (resp. the university)
			institutePeriodsActive = universityService.findPeriodsByUniversityAndActivation(universityId, true);
			institutePeriodsPassive = universityService.findPeriodsByUniversityAndActivation(universityId, false);

			Iterator<PeriodInfo> iterActive = institutePeriodsActive.iterator();
			PeriodInfo periodInfoActive;

			if (iterActive.hasNext()) {
				SelectItem item = new SelectItem(Constants.PERIODS_ACTIVE, i18n("periods_active"));
				institutePeriodItems.add(item);
			}

			while (iterActive.hasNext()) {
				periodInfoActive = iterActive.next();
				SelectItem item = new SelectItem(periodInfoActive.getId(), periodInfoActive.getName());
				institutePeriodItems.add(item);
			}

			Iterator<PeriodInfo> iterPassive = institutePeriodsPassive.iterator();
			PeriodInfo periodInfoPassive;

			if (iterPassive.hasNext()) {
				SelectItem item = new SelectItem(Constants.PERIODS_PASSIVE, i18n("periods_passive"));
				institutePeriodItems.add(item);
			}

			while (iterPassive.hasNext()) {
				periodInfoPassive = iterPassive.next();
				SelectItem item = new SelectItem(periodInfoPassive.getId(), periodInfoPassive.getName());
				institutePeriodItems.add(item);
			}
		}

		return institutePeriodItems;
	}

	/**
	 * Gets all course types of the institute.
	 * 
	 * @return outcome
	 */
	@SuppressWarnings( { "unchecked" })
	public List<SelectItem> getCourseTypesOfInstitute() {

		instituteCourseTypeItems = new ArrayList<SelectItem>();

		if (instituteInfo != null) {

			instituteCourseTypes = courseTypeService.findCourseTypesByInstitute(instituteInfo.getId());

			Iterator<CourseTypeInfo> iter = instituteCourseTypes.iterator();
			CourseTypeInfo courseTypeInfo;

			while (iter.hasNext()) {
				courseTypeInfo = iter.next();
				SelectItem item = new SelectItem(courseTypeInfo.getId(), courseTypeInfo.getName());
				instituteCourseTypeItems.add(item);
			}

		}

		return instituteCourseTypeItems;
	}

	/**
	 * Value Change Listener to change name of data table.
	 * 
	 * @param event
	 */
	public void processPeriodSelectChanged(ValueChangeEvent event) {
		final Long periodId = (Long) event.getNewValue();
		periodInfo = universityService.findPeriod(periodId);
		setBean(Constants.PERIOD_INFO, periodInfo);
	}

	private class LocalDataModelCourses extends AbstractPagedTable<CourseInfo> {

		private static final long serialVersionUID = -6289541218529435428L;

		private DataPage<CourseInfo> page;

		@Override
		@SuppressWarnings( { "unchecked" })
		public DataPage<CourseInfo> getDataPage(int startRow, int pageSize) {
			List<CourseInfo> courses = new ArrayList<CourseInfo>();;
			if (page == null) {
				if ((periodInfo.getId() != null) && (periodInfo.getId().longValue() != Constants.COURSES_ALL_PERIODS)
						&& (periodInfo.getId().longValue() != Constants.COURSES_ALL_ACTIVE_PERIODS)) {
					List<CourseInfo> coursesByPeriodAndInstitute = courseService.findCoursesByPeriodAndInstitute(
							periodInfo.getId(), instituteInfo.getId());
					if (coursesByPeriodAndInstitute != null) {
						courses.addAll(coursesByPeriodAndInstitute);
					}
				} else {
					logger.error("institute page - no period selected!");
				}
				page = new DataPage<CourseInfo>(courses.size(), 0, courses);
			}
			sort(courses);
			return page;
		}
	}

	public boolean isMoving() {
		return moving;
	}

	public void setMoving(boolean moving) {
		this.moving = moving;
	}

	public CourseInfo getCourseMoveInfo() {
		return courseMoveInfo;
	}

	public void setCourseMoveInfo(CourseInfo courseMoveInfo) {
		this.courseMoveInfo = courseMoveInfo;
	}

}
