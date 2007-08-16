package org.openuss.web.lecture;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.shale.tiger.managed.Bean;
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
 * @author Ingo Dueppe
 * @author Kai Stettner
 */
@Bean(name = "views$secured$lecture$coursetypes", scope = Scope.REQUEST)
@View
public class CourseTypesPage extends AbstractCourseTypePage {

	private static final Logger logger = Logger.getLogger(CourseTypesPage.class);

	private LocalDataModel data = new LocalDataModel();
	
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
		crumbs.add(crumb);
		setSessionBean(Constants.BREADCRUMBS, crumbs);
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
			return Constants.SUCCESS;
		}
	}

	/**
	 * Create new courseType object and set it into session scope
	 * 
	 * @return outcome
	 */
	public String addCourseType() {
		//courseType = CourseType.Factory.newInstance();
		courseTypeInfo = new CourseTypeInfo();
		courseTypeInfo.setInstituteId(instituteInfo.getId());
		setSessionBean(Constants.COURSE_TYPE_INFO, courseTypeInfo);
		return Constants.SUCCESS;
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

	/**
	 * Save new courseType or update changes to courseType Removed current courseType
	 * selection from session scope
	 * @return outcome 
	 */
	/*public String saveCourseType() {
		logger.debug("saveCourseType()");
		if (courseTypeInfo.getId() == null) {
			lectureService.add(institute.getId(), courseType);
			
			addMessage(i18n("institute_message_add_coursetype_succeed"));
		} else {
			lectureService.persist(courseType);
			addMessage(i18n("institute_message_persist_coursetype_succeed"));
		}
		removeSessionBean(Constants.COURSE_TYPE);
		courseTypeInfo = null;
		return Constants.SUCCESS;
	}*/

	/**
	 * Cancel editing or adding of current courseType
	 * @return outcome 
	 */
	public String cancelCourseType() {
		logger.debug("cancelCourseType()");
		removeSessionBean(Constants.COURSE_TYPE_INFO);
		return Constants.SUCCESS;
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
}
