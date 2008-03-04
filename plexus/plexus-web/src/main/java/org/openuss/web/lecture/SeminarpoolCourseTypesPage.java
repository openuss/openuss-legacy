package org.openuss.web.lecture;

import java.util.ArrayList;
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
import org.openuss.seminarpool.CourseSeminarpoolAllocationInfo;
import org.openuss.seminarpool.SeminarpoolAdministrationService;
import org.openuss.seminarpool.SeminarpoolInfo;
import org.openuss.web.Constants;

/**
 * CourseType Administration Page
 * @author Ingo Düppe
 * @author Kai Stettner
 */
@Bean(name = "views$secured$lecture$seminarpoolcoursetypes", scope = Scope.REQUEST)
@View
public class SeminarpoolCourseTypesPage extends AbstractLecturePage {

	public static final Logger logger = Logger.getLogger(SeminarpoolCourseTypesPage.class);

	private LocalDataModelCourseTypes dataCourseTypes = new LocalDataModelCourseTypes();
	
	@Property(value = "#{seminarpoolAdministrationService}")
	private SeminarpoolAdministrationService seminarpoolAdministrationService;

	/** currently editing course type */
	private Boolean editing = false;

	/** course type info */
	@Property(value="#{"+Constants.COURSESEMINARPOOLALLOCATION_INFO+"}")
	private CourseSeminarpoolAllocationInfo courseSeminarpoolAllocationInfo;
	
	@Property(value="#{"+Constants.SEMINARPOOL_INFO+"}")
	private SeminarpoolInfo seminarpoolInfo;

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
	 * Creates a new CourseSeminarpoolAllocationInfo object and sets it into session scope
	 * 
	 * @return outcome
	 */
	public String addCourseType() {
		editing = true;
		courseSeminarpoolAllocationInfo = new CourseSeminarpoolAllocationInfo();
		setSessionBean(Constants.COURSE_TYPE_INFO, courseSeminarpoolAllocationInfo);
		return Constants.SUCCESS;
	}

	/**
	 * Set selected courseType into session scope
	 * 
	 * @return outcome
	 * @throws LectureException
	 */
	public String activateSeminar() throws LectureException {
		logger.debug("Starting method activateSeminar");
		CourseSeminarpoolAllocationInfo currentCourseType = currentCourseType();
		logger.debug("Returning to method activateSeminar");
		logger.debug(currentCourseType.getId());
		this.seminarpoolAdministrationService.activateSeminar(currentCourseType);
		return "";
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

	private CourseSeminarpoolAllocationInfo currentCourseType() {
		CourseSeminarpoolAllocationInfo courseType = dataCourseTypes.getRowData();
		return courseType;
	}

	/**
	 * Store the selected course type into session scope and go to course type
	 * remove confirmation page. remove view
	 * 
	 * @return outcome
	 */
	public String selectSeminarAndConfirmRemove() {
		logger.debug("Starting method selectSeminarAndConfirmRemove");
		CourseSeminarpoolAllocationInfo currentCourseType = currentCourseType();
		logger.debug("Returning to method selectSeminarAndConfirmRemove");
		logger.debug(currentCourseType.getId());
		this.seminarpoolAdministrationService.removeSeminar(currentCourseType);
		//setSessionBean(Constants.COURSE_TYPE_INFO, currentCourseType);
		
		//return Constants.COURSE_TYPE_CONFIRM_REMOVE_PAGE;
		return "";

	}

	public void setDataCourseTypes(LocalDataModelCourseTypes dataCourseTypes) {
		this.dataCourseTypes = dataCourseTypes;
	}

	public LocalDataModelCourseTypes getDataCourseTypes() {
		return dataCourseTypes;
	}

	private class LocalDataModelCourseTypes extends AbstractPagedTable<CourseSeminarpoolAllocationInfo> {
		private static final long serialVersionUID = -6289875618529435428L;

		private DataPage<CourseSeminarpoolAllocationInfo> page;

		@Override
		@SuppressWarnings( { "unchecked" })
		public DataPage<CourseSeminarpoolAllocationInfo> getDataPage(int startRow, int pageSize) {
			if (page == null) {
				List<CourseSeminarpoolAllocationInfo> courseTypes = new ArrayList<CourseSeminarpoolAllocationInfo>(seminarpoolAdministrationService.findCoursesInSeminarpool(seminarpoolInfo.getId()));
				sort(courseTypes);
				page = new DataPage<CourseSeminarpoolAllocationInfo>(courseTypes.size(), 0, courseTypes);
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

	public CourseSeminarpoolAllocationInfo getCourseSeminarpoolAllocationInfo() {
		return courseSeminarpoolAllocationInfo;
	}

	public void setCourseSeminarpoolAllocationInfo(CourseSeminarpoolAllocationInfo courseTypeInfo) {
		this.courseSeminarpoolAllocationInfo = courseTypeInfo;
	}

	public SeminarpoolAdministrationService getSeminarpoolAdministrationService() {
		return seminarpoolAdministrationService;
	}

	public void setSeminarpoolAdministrationService(
			SeminarpoolAdministrationService seminarpoolAdministrationService) {
		this.seminarpoolAdministrationService = seminarpoolAdministrationService;
	}

	public SeminarpoolInfo getSeminarpoolInfo() {
		return seminarpoolInfo;
	}

	public void setSeminarpoolInfo(SeminarpoolInfo seminarpoolInfo) {
		this.seminarpoolInfo = seminarpoolInfo;
	}


}
