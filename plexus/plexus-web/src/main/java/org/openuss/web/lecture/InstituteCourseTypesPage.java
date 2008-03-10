package org.openuss.web.lecture;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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
import org.openuss.lecture.CourseTypeInfo;
import org.openuss.lecture.LectureException;
import org.openuss.web.Constants;

/**
 * CourseType Administration Page
 * @author Ingo Düppe
 * @author Kai Stettner
 */
@Bean(name = "views$secured$lecture$institutecoursetypes", scope = Scope.REQUEST)
@View
public class InstituteCourseTypesPage extends AbstractLecturePage {

	public static final Logger logger = Logger.getLogger(InstituteCourseTypesPage.class);

	private LocalDataModelCourseTypes dataCourseTypes = new LocalDataModelCourseTypes();

	/** currently editing course type */
	private Boolean editing = false;

	/** course type info */
	@Property(value="#{"+Constants.COURSE_TYPE_INFO+"}")
	private CourseTypeInfo courseTypeInfo;

	@Prerender
	@SuppressWarnings( { "unchecked" })
	public void prerender() throws LectureException {
		super.prerender();
		addPageCrumbs();
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
	 * Creates a new CourseTypeInfo object and sets it into session scope
	 * 
	 * @return outcome
	 */
	public String addCourseType() {
		editing = true;
		courseTypeInfo = new CourseTypeInfo();
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
			logger.debug("selected courseType " + courseTypeInfo.getName());
			editing = true;
			return Constants.SUCCESS;
		}
	}

	/**
	 * Saves new courseType or updates changes to courseType Removed current
	 * courseType selection from session scope
	 * 
	 * @return outcome
	 */
	public String saveCourseType() throws DesktopException, LectureException {
		logger.debug("Starting method saveCourseType()");
		if (courseTypeInfo.getId() == null) {

			courseTypeInfo.setInstituteId(instituteInfo.getId());
			courseTypeService.create(courseTypeInfo);

			addMessage(i18n("institute_message_add_coursetype_succeed") + " " + i18n("institute_message_add_coursetype_advice"));
			courseTypeInfo = null;
			editing = false;
			return Constants.INSTITUTE_COURSES_PAGE;
		} else {
			courseTypeService.update(courseTypeInfo);
			addMessage(i18n("institute_message_persist_coursetype_succeed"));
			removeSessionBean(Constants.COURSE_TYPE_INFO);
			courseTypeInfo = null;
			editing = false;
			return Constants.SUCCESS;
		}

		
	}

	/**
	 * Cancels editing or adding of current courseType
	 * 
	 * @return outcome
	 */
	public String cancelCourseType() {
		logger.debug("cancelCourseType()");
		removeSessionBean(Constants.COURSE_TYPE_INFO);
		editing = false;
		return Constants.SUCCESS;
	}

	private CourseTypeInfo currentCourseType() {
		CourseTypeInfo courseType = dataCourseTypes.getRowData();
		return courseType;
	}

	/**
	 * Store the selected course type into session scope and go to course type
	 * remove confirmation page. remove view
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

	public void setDataCourseTypes(LocalDataModelCourseTypes dataCourseTypes) {
		this.dataCourseTypes = dataCourseTypes;
	}

	public LocalDataModelCourseTypes getDataCourseTypes() {
		return dataCourseTypes;
	}

	private class LocalDataModelCourseTypes extends AbstractPagedTable<CourseTypeInfo> {
		private static final long serialVersionUID = -6289875618529435428L;

		private DataPage<CourseTypeInfo> page;

		@Override
		@SuppressWarnings( { "unchecked" })
		public DataPage<CourseTypeInfo> getDataPage(int startRow, int pageSize) {
			if (page == null) {
				List<CourseTypeInfo> courseTypes = new ArrayList<CourseTypeInfo>(courseTypeService
						.findCourseTypesByInstitute(instituteInfo.getId()));
				sort(courseTypes);
				// FIXME courseCount should be set with the CourseTypeDaoImpl.toCourseTypeInfo()
				for( Iterator<CourseTypeInfo> i = courseTypes.iterator(); i.hasNext(); ) {
					CourseTypeInfo cti = i.next();
					cti.setCourseCount(Long.valueOf(getCourseService().findCoursesByCourseType(cti.getId()).size()));
				}
				page = new DataPage<CourseTypeInfo>(courseTypes.size(), 0, courseTypes);
			}
			return page;
		}

	}

	public Boolean getEditing() {
		return editing;
	}

	public void setEditing(Boolean editing) {
		this.editing = editing;
	}

	public CourseTypeInfo getCourseTypeInfo() {
		return courseTypeInfo;
	}

	public void setCourseTypeInfo(CourseTypeInfo courseTypeInfo) {
		this.courseTypeInfo = courseTypeInfo;
	}


}
