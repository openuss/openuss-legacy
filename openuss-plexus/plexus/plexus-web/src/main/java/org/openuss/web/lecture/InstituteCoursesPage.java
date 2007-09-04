package org.openuss.web.lecture;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.faces.el.ValueBinding;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

import org.apache.log4j.Logger;
import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.Prerender;
import org.apache.shale.tiger.view.View;
import org.openuss.desktop.DesktopException;
import org.openuss.framework.jsfcontrols.breadcrumbs.BreadCrumb;
import org.openuss.framework.web.jsf.model.AbstractPagedTable;
import org.openuss.framework.web.jsf.model.DataPage;
import org.openuss.lecture.AccessType;
import org.openuss.lecture.CourseInfo;
import org.openuss.lecture.CourseTypeInfo;
import org.openuss.lecture.LectureException;
import org.openuss.lecture.PeriodInfo;
import org.openuss.lecture.UniversityInfo;
import org.openuss.web.Constants;
import org.openuss.web.course.AbstractCoursePage;

/**e
 * @author Kai Stettner
 */
@Bean(name = "views$secured$lecture$institutecourses", scope = Scope.REQUEST)
@View
public class InstituteCoursesPage extends AbstractCoursePage {

	private static final Logger logger = Logger.getLogger(InstituteCoursesPage.class);

	private LocalDataModelCourseTypes dataCourseTypes = new LocalDataModelCourseTypes();
	private LocalDataModelCourses dataCourses = new LocalDataModelCourses();
	
	private List<SelectItem> institutePeriodItems;
	private List<PeriodInfo> institutePeriodsActive;
	private List<PeriodInfo> institutePeriodsPassive;
	private List<SelectItem> instituteCourseTypeItems;
	private List<CourseTypeInfo> instituteCourseTypes;
	private Boolean renderCourseNew = false;
	private Boolean renderCourseTypeEditNew = false;
	private List<PeriodInfo> periodInfos = null;
	private Long universityId = 0l;
	private Long departmentId = 0l;
	
	private ValueBinding binding = getFacesContext().getApplication().createValueBinding("#{visit.locale}");
	private String locale = (String)binding.getValue(getFacesContext());
	private ResourceBundle bundle = ResourceBundle.getBundle("resources", new Locale(locale));
	
	@Prerender
	public void prerender() throws LectureException, Exception {
		super.prerender();
		
		if (instituteInfo != null) {
			departmentId = instituteInfo.getDepartmentId();
			departmentInfo = departmentService.findDepartment(departmentId);
			universityId = departmentInfo.getUniversityId();
			universityInfo = universityService.findUniversity(universityId);
			periodInfos = universityService.findPeriodsByInstituteWithCoursesOrActive(instituteInfo);
		} 
		
		if (periodInfo == null && instituteInfo != null || instituteInfo != null && !periodInfos.contains(periodInfo)) {
			if (periodInfo.getId() != null) {
				if((periodInfo.getId().longValue() == Constants.COURSES_ALL_PERIODS) || (periodInfo.getId().longValue() == Constants.COURSES_ALL_ACTIVE_PERIODS)) {
				periodInfo = periodInfos.get(0);
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
		
		addPageCrumbs();
			
		setSessionBean(Constants.PERIOD_INFO, periodInfo);
	}

	private void addPageCrumbs() {
		BreadCrumb crumb = new BreadCrumb();
		crumb.setLink("");
		crumb.setName(i18n("coursetype_coursetypestable_header"));
		crumb.setHint(i18n("coursetype_coursetypestable_header"));
		
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
		
		courseInfo.setAccessType(AccessType.CLOSED);
		
		Long courseId = courseService.create(courseInfo);
		
		addMessage(i18n("institute_message_persist_coursetype_succeed"));
		
		return Constants.SUCCESS;
	}
	
	/**
	 * Creates a new CourseTypeInfo object and sets it into session scope
	 * 
	 * @return outcome
	 */
	public String addCourseType() {
		
		courseTypeInfo = new CourseTypeInfo();
		this.renderCourseTypeEditNew = true;
		
		setSessionBean(Constants.COURSE_TYPE_INFO, courseTypeInfo);
		return Constants.SUCCESS;
	}
	
	
	/**
	 * Set selected courseType into session scope
	 * 
	 * @return outcome
	 * @throws LectureException 
	 */
	public String editCourseType() throws LectureException {
		courseTypeInfo = dataCourseTypes.getRowData();
		if (courseTypeInfo == null) {
			return Constants.FAILURE;
		}
		courseTypeInfo = courseTypeService.findCourseType(courseTypeInfo.getId());
		setSessionBean(Constants.COURSE_TYPE_INFO, courseTypeInfo);
		if (courseTypeInfo == null) {
			addWarning(i18n("error_course_type_not_found"));
			return Constants.FAILURE;
			
		} else {
			logger.debug("selected courseType "+courseTypeInfo.getName());
			this.renderCourseTypeEditNew = true;
			return Constants.SUCCESS;
		}
	}
	
	public String saveCourse() throws DesktopException  {
		
		logger.debug("Starting method saveCourse");
					
		courseInfo.setCourseTypeDescription(courseTypeInfo.getDescription());
		courseInfo.setCourseTypeId(courseTypeInfo.getId());
		courseInfo.setInstituteId(courseTypeInfo.getInstituteId());
		
		courseInfo.setAccessType(AccessType.OPEN);
		
		Long courseId = courseService.create(courseInfo);
				
		addMessage(i18n("institute_message_persist_coursetype_succeed"));
		
		
	
		this.renderCourseNew = false;
		
		return Constants.SUCCESS;
		
	}
	
	/**
	 * Saves new courseType or updates changes to courseType Removed current courseType
	 * selection from session scope
	 * @return outcome 
	 */
	public String saveCourseType() throws DesktopException, LectureException {
		logger.debug("Starting method saveCourseType()");
		if (courseTypeInfo.getId() == null) {
			
			courseTypeInfo.setInstituteId(instituteInfo.getId());
			Long courseTypeId = courseTypeService.create(courseTypeInfo);
						
			addMessage(i18n("institute_message_add_coursetype_succeed"));
		} else {
			courseTypeService.update(courseTypeInfo);
			addMessage(i18n("institute_message_persist_coursetype_succeed"));
		}
		
		removeSessionBean(Constants.COURSE_TYPE_INFO);
		courseTypeInfo = null;
		this.renderCourseTypeEditNew = false;
		
		return Constants.SUCCESS;
	}
	
	
	/**
	 * Cancels editing or adding of current courseType
	 * @return outcome 
	 */
	public String cancelCourseType() {
		logger.debug("cancelCourseType()");
		this.renderCourseTypeEditNew = false;
		removeSessionBean(Constants.COURSE_TYPE_INFO);
		return Constants.SUCCESS;
	}
	
	
	private CourseInfo currentCourse() {
		
		CourseInfo course = dataCourses.getRowData();
		
		return course;
	}
	
	
	private CourseTypeInfo currentCourseType() {
		
		CourseTypeInfo courseType = dataCourseTypes.getRowData();
		
		return courseType;
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
	 * Disables the chosen course. This is just evident for the search indexing.
	 * @return Outcome
	 */
	public String disableCourse() {
		logger.debug("Starting method disableCourse");
		CourseInfo currentCourse = currentCourse();
		// setOrganisationStatus(true) = Enabled
		// setOrganisationStatus(false) = Disabled
		courseService.setCourseStatus(currentCourse.getId(), false);
		
		addMessage(i18n("message_course_disabled"));
		return Constants.SUCCESS;
	}
	
	/**
	 * Enables the chosen course. This is just evident for the search indexing.
	 * @return Outcome
	 */
	public String enableCourse() {
		logger.debug("Starting method enableCourse");
		CourseInfo currentCourse = currentCourse();
		// setOrganisationStatus(true) = Enabled
		// setOrganisationStatus(false) = Disabled
		courseService.setCourseStatus(currentCourse.getId(), true);
		
		addMessage(i18n("message_course_enabled"));
		return Constants.SUCCESS;
	}
	
	/**
	 * Store the selected course into session scope and go to course remove confirmation page.
	 * @return Outcome
	 */
	public String selectCourseAndConfirmRemove() {
		logger.debug("Starting method selectCourseAndConfirmRemove");
		CourseInfo currentCourse = currentCourse();
		logger.debug("Returning to method selectCourseAndConfirmRemove");
		logger.debug(currentCourse.getId());	
		setSessionBean(Constants.COURSE_INFO, currentCourse);
		
		return Constants.COURSE_CONFIRM_REMOVE_PAGE;
	}
	
	/**
	 * Store the selected course type into session scope and go to course type remove confirmation page.
	 * remove view
	 * 
	 * @return outcome
	 */
	public String selectCourseTypeAndConfirmRemove() {		
		logger.debug("Starting method selectCourseTypeAndConfirmRemove");
		CourseTypeInfo currentCourseType = currentCourseType();
		logger.debug("Returning to method selectCourseTypeAndConfirmRemove");
		logger.debug(currentCourseType.getId());	
		setSessionBean(Constants.COURSE_TYPE_INFO, currentCourseType);
		
		return Constants.COURSE_TYPE_CONFIRM_REMOVE_PAGE;
		
	}
	
	/**
	 * Bookmarks the selected course on the MyUni Page.
	 * @return Outcome
	 */
	public String shortcutCourse() {
		courseInfo = dataCourses.getRowData();
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
	
	public LocalDataModelCourses getDataCourses() {
		return dataCourses;
	}

	public void setDataCourses(LocalDataModelCourses dataCourses) {
		this.dataCourses = dataCourses;
	}

	public LocalDataModelCourseTypes getDataCourseTypes() {
		return dataCourseTypes;
	}

	public void setDataCourseTypes(LocalDataModelCourseTypes dataCourseTypes) {
		this.dataCourseTypes = dataCourseTypes;
	}

	/**
	 * Gets all periods of the institute.
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
			//gets all periods of the institute (resp. the university)
			institutePeriodsActive = universityService.findPeriodsByUniversityAndActivation(universityId, true);
			institutePeriodsPassive = universityService.findPeriodsByUniversityAndActivation(universityId, false);
			
			Iterator<PeriodInfo> iterActive =  institutePeriodsActive.iterator();
			PeriodInfo periodInfoActive;
			
			if (iterActive.hasNext()) {
				SelectItem item = new SelectItem(Constants.PERIODS_ACTIVE, bundle.getString("periods_active"));
				institutePeriodItems.add(item);
			}
			
			while (iterActive.hasNext()) {
				periodInfoActive = iterActive.next();
				SelectItem item = new SelectItem(periodInfoActive.getId(),periodInfoActive.getName());
				institutePeriodItems.add(item);
			}
			
			Iterator<PeriodInfo> iterPassive =  institutePeriodsPassive.iterator();
			PeriodInfo periodInfoPassive;
			
			if (iterPassive.hasNext()) {
				SelectItem item = new SelectItem(Constants.PERIODS_PASSIVE, bundle.getString("periods_passive"));
				institutePeriodItems.add(item);
			}
			
			while (iterPassive.hasNext()) {
				periodInfoPassive = iterPassive.next();
				SelectItem item = new SelectItem(periodInfoPassive.getId(),periodInfoPassive.getName());
				institutePeriodItems.add(item);
			}
			
		} 
		
		return institutePeriodItems;
	}
	
	/**
	 * Gets all course types of the institute.
	 * @return outcome 
	 */
	public List<SelectItem> getCourseTypesOfInstitute() {
		
		instituteCourseTypeItems = new ArrayList<SelectItem>();
		
		if (instituteInfo != null) {
	
			instituteCourseTypes = courseTypeService.findCourseTypesByInstitute(instituteInfo.getId());
			
			Iterator<CourseTypeInfo> iter =  instituteCourseTypes.iterator();
			CourseTypeInfo courseTypeInfo;
			
			while (iter.hasNext()) {
				courseTypeInfo = iter.next();
				SelectItem item = new SelectItem(courseTypeInfo.getId(),courseTypeInfo.getName());
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
		setSessionBean(Constants.PERIOD_INFO, periodInfo);
	}



	

	
	private class LocalDataModelCourseTypes extends AbstractPagedTable<CourseTypeInfo> {
		
		private static final long serialVersionUID = -6289875618529435428L;
		
		private DataPage<CourseTypeInfo> page;

		@Override
		public DataPage<CourseTypeInfo> getDataPage(int startRow, int pageSize) {
			if (page == null) {
				List<CourseTypeInfo> courseTypes = new ArrayList<CourseTypeInfo>(courseTypeService.findCourseTypesByInstitute(instituteInfo.getId()));
				sort(courseTypes);
				page = new DataPage<CourseTypeInfo>(courseTypes.size(),0,courseTypes);
			}
			return page;
		}

	}
	

	private class LocalDataModelCourses extends AbstractPagedTable<CourseInfo> {
		
		private static final long serialVersionUID = -6289541218529435428L;
		
		private DataPage<CourseInfo> page;

		@Override
		public DataPage<CourseInfo> getDataPage(int startRow, int pageSize) {
			if (page == null) {
				List<CourseInfo> courses = new ArrayList<CourseInfo>();
				if ((periodInfo.getId() != null) && (periodInfo.getId().longValue() != Constants.COURSES_ALL_PERIODS) && (periodInfo.getId().longValue() != Constants.COURSES_ALL_ACTIVE_PERIODS)) {
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


	public Boolean getRenderCourseNew() {
		return renderCourseNew;
	}

	public void setRenderCourseNew(Boolean renderCourseNew) {
		this.renderCourseNew = renderCourseNew;
	}

	public Boolean getRenderCourseTypeEditNew() {
		return renderCourseTypeEditNew;
	}

	public void setRenderCourseTypeEditNew(Boolean renderCourseTypeEditNew) {
		this.renderCourseTypeEditNew = renderCourseTypeEditNew;
	}

}
