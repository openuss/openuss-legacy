package org.openuss.web.lecture;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.event.ValueChangeEvent;

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
import org.openuss.web.Constants;


/**
 * CourseType Administration Page
 * @author Ingo Düppe
 * @author Kai Stettner
 * @author Sebastian Roekens
 * 
 */
@Bean(name = "views$secured$lecture$institutecoursetypes", scope = Scope.REQUEST)
@View
public class InstituteCourseTypesPage extends AbstractLecturePage {

	public static final Logger logger = Logger.getLogger(InstituteCourseTypesPage.class);

	private LocalDataModelCourseTypes dataCourseTypes = new LocalDataModelCourseTypes();

	/** currently editing course type */
	private Boolean editing = false;

	/** period selection and instantiation of a course type */
	private Boolean instantiate = true;
	
	private UIComponent component;
	
	/** course type info */
	@Property(value="#{"+Constants.COURSE_TYPE_INFO+"}")
	private CourseTypeInfo courseTypeInfo;

	@Prerender
	@SuppressWarnings( { "unchecked" })
	public void prerender() throws Exception {
		super.prerender();
		if (isRedirected()){
			return;
		}
		if (!instituteInfo.isEnabled()) {
			addMessage(i18n("institute_not_activated"));
		}
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
		setBean(Constants.COURSE_TYPE_INFO, courseTypeInfo);
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
		setBean(Constants.COURSE_TYPE_INFO, courseTypeInfo);
		if (courseTypeInfo == null) {
			addWarning(i18n("error_course_type_not_found"));
			return Constants.FAILURE;

		} else {
			logger.debug("selected courseType " + courseTypeInfo.getName());
			editing = true;
			return Constants.SUCCESS;
		}
	}
	
	public void processInstantiateBooleanChanged(ValueChangeEvent event) {
		logger.debug("InstantiateBooleanChanged() processed");
		//final Long periodId = (Long) event.getNewValue();
		//periodInfo = universityService.findPeriod(periodId);
		//setSessionBean(Constants.PERIOD_INFO, periodInfo);
	}

	/**
	 * Saves new courseType or updates changes to courseType Removed current
	 * courseType selection from session scope
	 * 
	 * @return outcome
	 */
	public String saveCourseType() throws DesktopException, LectureException {
		logger.debug("Starting method saveCourseType()");
		if(instantiate && ((periodInfo.getId().longValue() == Constants.PERIODS_ACTIVE) || (periodInfo.getId().longValue() == Constants.PERIODS_PASSIVE))) {
			//((UIInput) component).setValid(false);
			//addError(component.getClientId(FacesContext.getCurrentInstance()), i18n("error_choose_a_valid_period"),null);
			//addError("periodSelection", i18n("error_choose_a_valid_period"),null);
			addError(i18n("error_choose_a_valid_period"),null);
			return Constants.FAILURE;
		} else {
			if (courseTypeInfo.getId() == null) {
				
				courseTypeInfo.setInstituteId(instituteInfo.getId());
				courseTypeService.create(courseTypeInfo);
				
				if(instantiate)
					addCourse();
				
				addMessage(i18n("institute_message_add_coursetype_succeed"));
				courseTypeInfo = null;
				editing = false;
				return Constants.SUCCESS;
			} else {
				courseTypeService.update(courseTypeInfo);
				addMessage(i18n("institute_message_persist_coursetype_succeed"));
				removeSessionBean(Constants.COURSE_TYPE_INFO);
				courseTypeInfo = null;
				editing = false;
				return Constants.SUCCESS;
			}	
		}
	}
	
	public String saveCourseTypeAndGoToSettings() throws DesktopException, LectureException {
		instantiate = true;
		this.saveCourseType();
		return Constants.COURSE_OPTIONS_PAGE;
	}
	
	public String addCourse() throws DesktopException {
		logger.debug("Starting method addCourse");
		courseInfo = new CourseInfo();
		courseInfo.setCourseTypeDescription(courseTypeInfo.getDescription());
		courseInfo.setCourseTypeId(courseTypeInfo.getId());
		courseInfo.setPeriodId(periodInfo.getId());
		courseInfo.setPeriodName(periodInfo.getName());
		courseInfo.setInstituteId(courseTypeInfo.getInstituteId());
		courseService.create(courseInfo);
		addMessage(i18n("institute_message_persist_coursetype_succeed"));
		setBean(Constants.COURSE_INFO, courseInfo);
		return Constants.COURSE_OPTIONS_PAGE;
	}
	
	/**
	 * Cancels editing or adding of current courseType
	 * 
	 * @return outcome
	 */
	public String cancelCourseType() {
		logger.debug("cancelCourseType()");
		setBean(Constants.COURSE_TYPE_INFO, null);
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
		setBean(Constants.COURSE_TYPE_INFO, currentCourseType);

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
				List<CourseTypeInfo> courseTypes = new ArrayList<CourseTypeInfo>(courseTypeService.findCourseTypesByInstitute(instituteInfo.getId()));
				sort(courseTypes);
				// FIXME CourseCount should be calculated within the business layer
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

	public Boolean getInstantiate() {
		return instantiate;
	}

	public void setInstantiate(Boolean instantiate) {
		this.instantiate = instantiate;
	}

	public UIComponent getComponent() {
		return component;
	}

	public void setComponent(UIComponent component) {
		this.component = component;
	}


}
