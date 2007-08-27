package org.openuss.web.lecture;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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
import org.openuss.lecture.DepartmentInfo;
import org.openuss.lecture.LectureException;
import org.openuss.lecture.PeriodInfo;
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
	private List<PeriodInfo> institutePeriods;
	private List<SelectItem> instituteCourseTypeItems;
	private List<CourseTypeInfo> instituteCourseTypes;
	private Boolean renderCourseNew = false;
	private Boolean renderCourseTypeEditNew = false;
	
	@Prerender
	public void prerender() throws LectureException, Exception {
		super.prerender();
	}

	private void addPageCrumb() {
		BreadCrumb crumb = new BreadCrumb();
		crumb.setLink("");
		crumb.setName(i18n("coursetype_coursetypestable_header"));
		crumb.setHint(i18n("coursetype_coursetypestable_header"));
		
		breadcrumbs.loadCourseCrumbs(courseInfo);
		breadcrumbs.addCrumb(crumb);
		
		// TODO Remove old crumb code
		crumbs.add(crumb);
		setSessionBean(Constants.BREADCRUMBS, crumbs);
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
		
		// bookmark course to myuni page
		desktopService2.linkCourse(desktopInfo.getId(), courseId);
		
		
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
		
		// bookmark course to myuni page
		desktopService2.linkCourse(desktopInfo.getId(), courseId);
		
		
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
			
			// bookmark course type to myuni page
			desktopService2.linkCourseType(desktopInfo.getId(), courseTypeId);
			
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
	 * Sets the selected period into session scope and forward to the period
	 * remove view
	 * 
	 * @return outcome
	 */
	public String confirmRemoveCourseType() {
		courseTypeInfo = dataCourseTypes.getRowData();;
		setSessionBean(Constants.COURSE_TYPE_INFO, courseTypeInfo);
		return Constants.INSTITUTE_COURSE_TYPE_REMOVE_PAGE;
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
	public List<SelectItem> getAllPeriodsOfInstitute() {
		
		institutePeriodItems = new ArrayList<SelectItem>();
		
		if (instituteInfo != null) {
			Long departmentId = instituteInfo.getDepartmentId();
			departmentInfo = departmentService.findDepartment(departmentId);
			Long universityId = departmentInfo.getUniversityId();
			universityInfo = universityService.findUniversity(universityId);
			//gets all periods of the institute (resp. the university)
			institutePeriods = universityService.findPeriodsByUniversity(universityId);

			Iterator<PeriodInfo> iter =  institutePeriods.iterator();
			PeriodInfo periodInfo;
			
			while (iter.hasNext()) {
				periodInfo = iter.next();
				SelectItem item = new SelectItem(periodInfo.getId(),periodInfo.getName());
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
				List<CourseInfo> courses = new ArrayList<CourseInfo>(courseService.findCoursesByCourseType(courseTypeInfo.getId()));
				sort(courses);
				page = new DataPage<CourseInfo>(courses.size(),0,courses);
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
