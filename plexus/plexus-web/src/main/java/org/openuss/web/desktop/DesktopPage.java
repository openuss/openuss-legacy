package org.openuss.web.desktop;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.Prerender;
import org.apache.shale.tiger.view.View;
import org.openuss.desktop.DesktopException;
import org.openuss.framework.web.jsf.model.AbstractPagedTable;
import org.openuss.framework.web.jsf.model.DataPage;
import org.openuss.lecture.CourseInfo;
import org.openuss.lecture.CourseTypeInfo;
import org.openuss.lecture.InstituteInfo;
import org.openuss.web.BasePage;
import org.openuss.web.Constants;

/**
 * DesktopViewController is the mvc bean to handle the desktop view.
 * 
 * @author Ingo Dueppe
 * @author Kai Stettner
 * @author Sebastian Roekens
 * 
 */
@Bean(name = "views$secured$desktop$desktop", scope = Scope.REQUEST)
@View
public class DesktopPage extends BasePage {

	private static final Logger logger = Logger.getLogger(DesktopPage.class);

	private CourseDataProvider coursesProvider = new CourseDataProvider();
	private CourseTypeDataProvider courseTypesProvider = new CourseTypeDataProvider();
	private InstituteDataProvider institutesProvider = new InstituteDataProvider();
	
	@Prerender
	public void prerender() throws Exception {
		logger.debug("prerender desktop");
		refreshDesktop();
		breadcrumbs.clear();
	}
	
	private void refreshDesktop() {
		if (user != null) {
			try {
				if (desktopInfo == null || desktopInfo.getId() == null) {
					logger.error("No desktop found for user " + user.getUsername() + ". Create new one.");
					desktopInfo = desktopService2.findDesktopByUser(user.getId());
				} else {
					logger.debug("refreshing desktop data");
					desktopInfo = desktopService2.findDesktop(desktopInfo.getId());
				}
				setBean(Constants.DESKTOP_INFO, desktopInfo);
			} catch (DesktopException e) {
				logger.error(e);
				addError(i18n(e.getMessage()));
			}
		}
	}
	
	/**
	 * Show selected institute
	 * 
	 * @return outcome
	 */
	public String showInstitute() {
		logger.debug("starting method showInstitute");
		InstituteInfo instituteInfo = institutesProvider.getRowData();
		setBean(Constants.INSTITUTE_INFO, instituteInfo);
		return Constants.INSTITUTE;
	}

	/**
	 * Remove selected institute from desktop
	 * 
	 * @return outcome = DESKTOP
	 */
	public String removeInstitute() throws DesktopException {
		logger.debug("starting method remove institute");
		InstituteInfo instituteInfo = institutesProvider.getRowData();
		//desktopService.unlinkInstitute(desktop, institute);
		desktopService2.unlinkInstitute(desktopInfo.getId(), instituteInfo.getId());
		addMessage(i18n("desktop_message_removed_institute_succeed", instituteInfo.getName()));
		return Constants.DESKTOP;
	}

	/**
	 * Show selected course
	 * 
	 * @return outcome
	 */
	public String showCourse() {
		logger.debug("starting method showCourse");
		CourseInfo courseInfo = coursesProvider.getRowData();
		setBean(Constants.COURSE_INFO, courseInfo);
		return Constants.COURSE_PAGE;
	}

	/**
	 * Remove course
	 * 
	 * @return outcome
	 */
	public String removeCourse() {
		logger.debug("starting method remove course");
		CourseInfo courseInfo = coursesProvider.getRowData();
		try {
			//desktopService.unlinkCourse(desktop, course);
			desktopService2.unlinkCourse(desktopInfo.getId(), courseInfo.getId());
			addMessage(i18n("desktop_mesage_removed_course_succeed", courseInfo.getShortcut()));
		} catch (DesktopException e) {
			logger.debug(e);
			addError(i18n(e.getMessage()));
		}
		return Constants.DESKTOP;
	}

	/**
	 * Remove courseType
	 * 
	 * @return outcome
	 */
//	public String showCourseType() {
//		logger.debug("starting method showCourseType");
//		CourseTypeInfo courseTypeInfo = courseTypesProvider.getRowData();
//		setSessionBean(Constants.COURSE_TYPE_INFO, courseTypeInfo);
//		return Constants.COURSE_TYPE_COURSE_SELECTION_PAGE;
//	}

//	public String removeCourseType() {
//		logger.debug("starting method remove courseType");
//		CourseTypeInfo courseTypeInfo = courseTypesProvider.getRowData();
//		try {
//			//desktopService.unlinkCourseType(desktop, courseType);
//			desktopService2.unlinkCourseType(desktopInfo.getId(), courseTypeInfo.getId());
//			addMessage(i18n("desktop_message_removed_coursetype_succeed", courseTypeInfo.getName()));
//		} catch (DesktopException e) {
//			logger.debug(e);
//			addError(i18n(e.getMessage()));
//		}
//		return Constants.DESKTOP;
//	}

	/* ------------------ data models ------------------- */
	private class CourseDataProvider extends AbstractPagedTable<CourseInfo> {

		private static final long serialVersionUID = 255073655670856663L;

		private DataPage<CourseInfo> page;

		@SuppressWarnings("unchecked")
		@Override
		public DataPage<CourseInfo> getDataPage(int startRow, int pageSize) {
			if (desktopInfo != null) {
				List<CourseInfo> courses = new ArrayList<CourseInfo>(desktopInfo.getCourseInfos());
				sort(courses);
				page = new DataPage<CourseInfo>(courses.size(), 0, courses);
			}
			return page;
		}
	}

	private class CourseTypeDataProvider extends AbstractPagedTable<CourseTypeInfo> {

		private static final long serialVersionUID = -932405920082089168L;

		private DataPage<CourseTypeInfo> page;

		@SuppressWarnings("unchecked")
		@Override
		public DataPage<CourseTypeInfo> getDataPage(int startRow, int pageSize) {
			List<CourseTypeInfo> courseTypes = new ArrayList<CourseTypeInfo>(desktopInfo.getCourseTypeInfos());
			sort(courseTypes);
			page = new DataPage<CourseTypeInfo>(courseTypes.size(), 0, courseTypes);
			return page;
		}
	}

	private class InstituteDataProvider extends AbstractPagedTable<InstituteInfo> {
		private static final long serialVersionUID = 2146461373793139193L;

		private DataPage<InstituteInfo> page;

		@SuppressWarnings("unchecked")
		@Override
		public DataPage<InstituteInfo> getDataPage(int startRow, int pageSize) {
			List<InstituteInfo> institutes = new ArrayList<InstituteInfo>(desktopInfo.getInstituteInfos());
			sort(institutes);
			page = new DataPage<InstituteInfo>(institutes.size(), 0, institutes);
			return page;
		}
	}

	public CourseDataProvider getCoursesProvider() {
		return coursesProvider;
	}

	public void setCoursesProvider(CourseDataProvider coursesProvider) {
		this.coursesProvider = coursesProvider;
	}

	public InstituteDataProvider getInstitutesProvider() {
		return institutesProvider;
	}

	public void setInstitutesProvider(InstituteDataProvider institutesProvider) {
		this.institutesProvider = institutesProvider;
	}

	public CourseTypeDataProvider getCourseTypesProvider() {
		return courseTypesProvider;
	}

	public void setCourseTypesProvider(CourseTypeDataProvider courseTypesProvider) {
		this.courseTypesProvider = courseTypesProvider;
	}
}
