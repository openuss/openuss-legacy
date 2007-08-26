package org.openuss.web.lecture;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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
import org.openuss.web.Constants;

/**
 * @author Ingo Dueppe
 * @author Kai Stettner
 */
@Bean(name = "views$public$coursetype$coursetypes", scope = Scope.REQUEST)
@View
public class CourseTypesPage extends AbstractCourseTypePage {

	private static final Logger logger = Logger.getLogger(CourseTypesPage.class);

	private LocalDataModel data = new LocalDataModel();
	
	private List<SelectItem> institutePeriodItems;
	private List<PeriodInfo> institutePeriods;
	
	private Boolean renderCourseTypeEditNew = false;
	private Boolean renderCourseTypeAssignToPeriod = false;
	
	@Prerender
	public void prerender() throws LectureException {
		super.prerender();
		addPageCrumb();
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
		courseTypeInfo = data.getRowData();
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
	
	/**
	 * Cancels editing or adding of current courseType
	 * @return outcome 
	 */
	public String cancelCourseTypeAssignToPeriod() {
		logger.debug("cancelCourseType()");
		this.renderCourseTypeAssignToPeriod = false;
		removeSessionBean(Constants.COURSE_TYPE_INFO);
		return Constants.SUCCESS;
	}
	
	/**
	 * Creates a new CourseTypeInfo object and sets it into session scope
	 * 
	 * @return outcome
	 */
	public String assignCourseTypeToPeriod() {
		
		courseTypeInfo = data.getRowData();
		if (courseTypeInfo == null) {
			return Constants.FAILURE;
		}
		courseTypeInfo = courseTypeService.findCourseType(courseTypeInfo.getId());
		
		//instantiate new CourseInfo Object
		courseInfo = new CourseInfo();
		
		setSessionBean(Constants.COURSE_INFO, courseInfo);
		
		setSessionBean(Constants.COURSE_TYPE_INFO, courseTypeInfo);
		
		if (courseTypeInfo == null) {
			addWarning(i18n("error_course_type_not_found"));
			return Constants.FAILURE;
			
		} else {
			logger.debug("selected courseType "+courseTypeInfo.getName());
			this.renderCourseTypeAssignToPeriod = true;
			return Constants.SUCCESS;
		}	
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
		
		return Constants.INSTITUTE_PAGE;
	}
	
	/**
	 * Saves new courseType or updates changes to courseType Removed current courseType
	 * selection from session scope
	 * @return outcome 
	 */
	public String saveCourseTypeAssignToPeriod() throws DesktopException, LectureException {
		logger.debug("Starting method saveCourseTypeAssignToPeriod()");
		if (courseTypeInfo.getId() == null) {
			
			courseTypeInfo.setInstituteId(instituteInfo.getId());
			Long courseTypeId = courseTypeService.create(courseTypeInfo);
			
			//			 bookmark course tape to myuni page
			desktopService2.linkCourseType(desktopInfo.getId(), courseTypeId);
			
			addMessage(i18n("institute_message_add_coursetype_succeed"));
		} else {
			courseTypeService.update(courseTypeInfo);
			
			courseInfo.setCourseTypeDescription(courseTypeInfo.getDescription());
			courseInfo.setCourseTypeId(courseTypeInfo.getId());
			courseInfo.setDescription(courseTypeInfo.getDescription());
			courseInfo.setInstituteId(courseTypeInfo.getInstituteId());
			//courseInfo.setInstituteName(courseTypeInfo.geti
			courseInfo.setName(courseTypeInfo.getName());
			courseInfo.setShortcut(courseTypeInfo.getShortcut());
			
			courseInfo.setAccessType(AccessType.OPEN);
			
			Long courseId = courseService.create(courseInfo);
			
			// bookmark course to myuni page
			desktopService2.linkCourse(desktopInfo.getId(), courseId);
			
			
			addMessage(i18n("institute_message_persist_coursetype_succeed"));
		}
		
		removeSessionBean(Constants.COURSE_TYPE_INFO);
		courseTypeInfo = null;
		this.renderCourseTypeAssignToPeriod = false;
		//return Constants.SUCCESS;
		return Constants.INSTITUTE_PAGE;
	}
	
		

	
	
	
	
	
	
	public List<SelectItem> getBelongingInstitutePeriods() {
		
		institutePeriodItems = new ArrayList<SelectItem>();
		
		if (instituteInfo != null) {
			Long departmentId = instituteInfo.getDepartmentId();
			departmentInfo = departmentService.findDepartment(departmentId);
			Long universityId = departmentInfo.getUniversityId();
			universityInfo = universityService.findUniversity(universityId);
			//universityService.findActivePeriodByUniversity(universityId);
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
	
	

	

	public String shortcutCourseType() {
		courseTypeInfo = data.getRowData();;
		try {
			// desktopService.linkCourseType(desktop, courseType);
			desktopService2.linkCourseType(desktopInfo.getId(), courseTypeInfo.getId());
			addMessage(i18n("desktop_command_add_coursetype_succeed"));
			return Constants.SUCCESS;
		} catch (DesktopException e) {
			logger.error(e);
			addError(i18n(e.getMessage()));
			return Constants.FAILURE;
		}
	}

	/**
	 * Sets the selected period into session scope and forward to the period
	 * remove view
	 * 
	 * @return outcome
	 */
	public String confirmRemoveCourseType() {
		courseTypeInfo = data.getRowData();;
		setSessionBean(Constants.COURSE_TYPE_INFO, courseTypeInfo);
		return Constants.INSTITUTE_COURSE_TYPE_REMOVE_PAGE;
	}

	

	
	
	

	private class LocalDataModel extends AbstractPagedTable<CourseTypeInfo> {
		
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

	public LocalDataModel getData() {
		return data;
	}

	public void setData(LocalDataModel data) {
		this.data = data;
	}

	public Boolean getRenderCourseTypeEditNew() {
		return renderCourseTypeEditNew;
	}

	public void setRenderCourseTypeEditNew(Boolean renderCourseTypeEditNew) {
		this.renderCourseTypeEditNew = renderCourseTypeEditNew;
	}

	public Boolean getRenderCourseTypeAssignToPeriod() {
		return renderCourseTypeAssignToPeriod;
	}

	public void setRenderCourseTypeAssignToPeriod(
			Boolean renderCourseTypeAssignToPeriod) {
		this.renderCourseTypeAssignToPeriod = renderCourseTypeAssignToPeriod;
	}	
}
