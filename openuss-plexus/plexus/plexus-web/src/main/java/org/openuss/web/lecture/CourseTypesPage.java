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
import org.openuss.lecture.LectureException;
import org.openuss.lecture.CourseType;
import org.openuss.web.Constants;

/**
 * @author Ingo Dueppe
 */
@Bean(name = "views$secured$lecture$coursetypes", scope = Scope.REQUEST)
@View
public class CourseTypesPage extends AbstractLecturePage {

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
		courseType = data.getRowData();
		if (courseType == null) {
			return Constants.FAILURE;
		}
		courseType = lectureService.getCourseType(courseType.getId());
		setSessionBean(Constants.COURSE_TYPE, courseType);
		if (courseType == null) {
			addWarning(i18n("error_coursetype_not_found"));
			return Constants.FAILURE;
			
		} else {
			logger.debug("selected courseType "+courseType.getName());
			return Constants.SUCCESS;
		}
	}

	/**
	 * Create new courseType object and set it into session scope
	 * 
	 * @return outcome
	 */
	public String addCourseType() {
		courseType = CourseType.Factory.newInstance();
		courseType.setInstitute(institute);
		setSessionBean(Constants.COURSE_TYPE, courseType);
		return Constants.SUCCESS;
	}

	public String shortcutCourseType() {
		courseType = data.getRowData();;
		try {
			desktopService.linkCourseType(desktop, courseType);
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
		courseType = data.getRowData();;
		setSessionBean(Constants.COURSE_TYPE, courseType);
		return Constants.INSTITUTE_COURSE_TYPE_REMOVE_PAGE;
	}

	/**
	 * Save new courseType or update changes to courseType Removed current courseType
	 * selection from session scope
	 * @return outcome 
	 */
	public String saveCourseType() {
		logger.debug("saveCourseType()");
		if (courseType.getId() == null) {
			lectureService.add(institute.getId(), courseType);
			addMessage(i18n("institute_message_add_coursetype_succeed"));
		} else {
			lectureService.persist(courseType);
			addMessage(i18n("institute_message_persist_coursetype_succeed"));
		}
		removeSessionBean(Constants.COURSE_TYPE);
		courseType = null;
		return Constants.SUCCESS;
	}

	/**
	 * Cancel editing or adding of current courseType
	 * @return outcome 
	 */
	public String cancelCourseType() {
		logger.debug("cancelCourseType()");
		removeSessionBean(Constants.COURSE_TYPE);
		return Constants.SUCCESS;
	}

	private class LocalDataModel extends AbstractPagedTable<CourseType> {
		
		private static final long serialVersionUID = -6289875618529435428L;
		
		private DataPage<CourseType> page;

		@Override
		public DataPage<CourseType> getDataPage(int startRow, int pageSize) {
			if (page == null) {
				List<CourseType> courseTypes = new ArrayList(institute.getCourseTypes());
				sort(courseTypes);
				page = new DataPage<CourseType>(courseTypes.size(),0,courseTypes);
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
