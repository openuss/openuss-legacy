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
import org.openuss.lecture.DepartmentInfo;
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
	}	
	
	
	
	
	
	
	
	
	/**
	 * Cancels editing or adding of current courseType
	 * @return outcome 
	 */
	/*public String cancelCourseTypeAssignToPeriod() {
		logger.debug("cancelCourseType()");
		this.renderCourseTypeAssignToPeriod = false;
		removeSessionBean(Constants.COURSE_TYPE_INFO);
		return Constants.SUCCESS;
	}*/
	
	/**
	 * Store the selected department into session scope and go to department remove confirmation page.
	 * @return Outcome
	 */
	/*public String selectCourseTypeAndRedirectToCourses() {
		logger.debug("Starting method selectCourseTypeAndRedirectToCourses");
		CourseTypeInfo currentCourseType = currentCourseType();
		logger.debug("Returning to method selectCourseTypeAndRedirectToCourses");
		logger.debug(currentCourseType.getId());	
		setSessionBean(Constants.COURSE_TYPE_INFO, currentCourseType);
		
		return Constants.INSTITUTE_COURSES_PAGE;
	}*/
	
	/*private CourseTypeInfo currentCourseType() {
		
		CourseTypeInfo courseType = data.getRowData();
		
		return courseType;
	}*/
	
	/**
	 * Creates a new CourseTypeInfo object and sets it into session scope
	 * 
	 * @return outcome
	 */
	/*public String assignCourseTypeToPeriod() {
		
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
	}*/
	
	
	
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
		
		return Constants.SUCCESS;
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
