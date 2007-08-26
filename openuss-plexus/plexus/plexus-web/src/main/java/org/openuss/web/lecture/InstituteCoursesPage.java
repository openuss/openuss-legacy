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
import org.openuss.web.course.AbstractCoursePage;

/**e
 * @author Kai Stettner
 */
@Bean(name = "views$public$coursetype$courses", scope = Scope.REQUEST)
@View
public class InstituteCoursesPage extends AbstractCoursePage {

	private static final Logger logger = Logger.getLogger(CourseTypesPage.class);

	private LocalDataModel data = new LocalDataModel();
	
	private Boolean renderCourseNew = false;
	
	@Prerender
	public void prerender() throws LectureException, Exception {
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
	 * Creates a new Course object and sets it into session scope
	 * 
	 * @return outcome
	 */
	public String addCourse() {
		
		courseInfo = new CourseInfo();
		this.renderCourseNew = true;
		
		setSessionBean(Constants.COURSE_INFO, courseInfo);
		return Constants.SUCCESS;
	}
	
	public String saveCourse() throws DesktopException  {
		
		logger.debug("Starting method saveCourse");
					
		//courseInfo.setCourseTypeDescription(courseTypeInfo.getDescription());
		//courseInfo.setCourseTypeId(courseTypeInfo.getId());
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
		
		
	
		this.renderCourseNew = false;
		
		return Constants.SUCCESS;
		
	}
	
	public String shortcutCourse() {
		courseInfo = data.getRowData();;
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



	

	
	
	

	private class LocalDataModel extends AbstractPagedTable<CourseInfo> {
		
		private static final long serialVersionUID = -6289541218529435428L;
		
		private DataPage<CourseInfo> page;

		@Override
		public DataPage<CourseInfo> getDataPage(int startRow, int pageSize) {
			if (page == null) {
				//List<CourseInfo> courses = new ArrayList<CourseInfo>(courseService.findCoursesByCourseType(courseTypeInfo.getId()));
				List<CourseInfo> coursesTest = new ArrayList<CourseInfo>();
				List<CourseInfo> courses = new ArrayList<CourseInfo>(coursesTest);
				sort(courses);
				page = new DataPage<CourseInfo>(courses.size(),0,courses);
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

	public Boolean getRenderCourseNew() {
		return renderCourseNew;
	}

	public void setRenderCourseNew(Boolean renderCourseNew) {
		this.renderCourseNew = renderCourseNew;
	}

}
