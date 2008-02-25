package org.openuss.web.wiki;

import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;

import org.apache.log4j.Logger;
import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Property;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.Prerender;
import org.apache.shale.tiger.view.View;
import org.openuss.framework.web.jsf.model.AbstractPagedTable;
import org.openuss.framework.web.jsf.model.DataPage;
import org.openuss.lecture.CourseDao;
import org.openuss.lecture.CourseInfo;
import org.openuss.lecture.CourseMemberInfo;
import org.openuss.lecture.CourseService;
import org.openuss.security.SecurityService;
import org.openuss.security.User;
import org.openuss.web.Constants;

@Bean(name = "views$secured$wiki$wikiimport", scope = Scope.REQUEST)
@View
public class WikiImportPage extends AbstractWikiPage{
	
	private static final Logger logger = Logger.getLogger(WikiImportPage.class);
	
	@Property(value = "#{securityService}")
	protected SecurityService securityService;
	
	@Property(value = "#{courseService}")
	protected CourseService courseService;
	
	@Property(value = "#{courseDao}")
	protected CourseDao courseDao;
	
	protected List<SelectItem> courses = new ArrayList<SelectItem>();
	
	private LocalDataModelCourses dataCourses = new LocalDataModelCourses();
	
	protected CourseInfo selectedCourse;
	
	protected Long courseId;
	
	@SuppressWarnings("unchecked")
	@Override
	@Prerender
	public void prerender() throws Exception {
		// TODO: this is very dirty! 
		courses.clear();
		
		User currentUser = securityService.getCurrentUser();
		
		List<CourseInfo> availableCourses = courseService.findAllCoursesByInstitute(getInstituteInfo().getId());
		for (CourseInfo courseInfo : availableCourses) {			
			List<CourseMemberInfo> assistants = courseService.getAssistants(courseInfo);
			boolean isAssistant = false;
			for (CourseMemberInfo assistant : assistants) {
				if (assistant.getUserId().equals(currentUser.getId())) {
					isAssistant = true;
				}
			}
			
			if (isAssistant) {
				courses.add(new SelectItem(courseInfo.getId().toString(), courseInfo.getName()));
			}
		}

		// super.prerender();
	}
	
	public String importWikiVersions() {
				
		
		return Constants.WIKI_OVERVIEW;
	}
	
	public String importWikiSites() {
				
		
		return Constants.WIKI_OVERVIEW;
	}

	public SecurityService getSecurityService() {
		return securityService;
	}

	public void setSecurityService(SecurityService securityService) {
		this.securityService = securityService;
	}

	public CourseService getCourseService() {
		return courseService;
	}

	public void setCourseService(CourseService courseService) {
		this.courseService = courseService;
	}

	public CourseDao getCourseDao() {
		return courseDao;
	}

	public void setCourseDao(CourseDao courseDao) {
		this.courseDao = courseDao;
	}

	public CourseInfo getSelectedCourse() {
		return selectedCourse;
	}

	public void setSelectedCourse(CourseInfo selectedCourse) {
		this.selectedCourse = selectedCourse;
	}
	
	private class LocalDataModelCourses extends AbstractPagedTable<CourseInfo> {
		private static final long serialVersionUID = -6289875618529435428L;

		private DataPage<CourseInfo> page;

		@Override
		@SuppressWarnings({"unchecked"})
		public DataPage<CourseInfo> getDataPage(int startRow, int pageSize) {
			
			if (page == null) {
				User currentUser = securityService.getCurrentUser();
				List<CourseInfo> courses = new ArrayList<CourseInfo>();
				List<CourseInfo> availableCourses = courseService.findAllCoursesByInstitute(getInstituteInfo().getId());
				
				for (CourseInfo courseInfo : availableCourses) {			
					List<CourseMemberInfo> assistants = courseService.getAssistants(courseInfo);
					boolean isAssistant = false;
					for (CourseMemberInfo assistant : assistants) {
						if (assistant.getUserId().equals(currentUser.getId())) {
							isAssistant = true;
						}
					}
					
					if (isAssistant) {
						courses.add(courseInfo);
					}
				}
				
				page = new DataPage<CourseInfo>(courses.size(), 0, courses);
			}
			
			return page;
		}
	}

	public LocalDataModelCourses getDataCourses() {
		return dataCourses;
	}

	public void setDataCourses(LocalDataModelCourses dataCourses) {
		this.dataCourses = dataCourses;
	}

	public List<SelectItem> getCourses() {
		return courses;
	}

	public void setCourses(List<SelectItem> courses) {
		this.courses = courses;
	}

	public Long getCourseId() {
		return courseId;
	}

	public void setCourseId(Long courseId) {
		this.courseId = courseId;
	}
}