package org.openuss.web.wiki;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.faces.model.SelectItem;

import org.apache.log4j.Logger;
import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Property;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.Prerender;
import org.apache.shale.tiger.view.View;
import org.openuss.lecture.CourseDao;
import org.openuss.lecture.CourseInfo;
import org.openuss.lecture.CourseMemberInfo;
import org.openuss.security.SecurityService;
import org.openuss.security.User;
import org.openuss.web.Constants;

@Bean(name = "views$secured$wiki$wikiimport", scope = Scope.REQUEST)
@View
public class WikiImportPage extends AbstractWikiPage{
	
	private static final Logger logger = Logger.getLogger(WikiImportPage.class);
	
	@Property(value = "#{securityService}")
	protected SecurityService securityService;
	
	@Property(value = "#{courseDao}")
	protected CourseDao courseDao;
	
	protected List<SelectItem> exportableWikiCourses;
	
	protected Long selectedCourseId;
	
	public String importWikiVersions() {
		
		return Constants.WIKI_OVERVIEW;
	}
	
	public String importWikiSites() {		
		wikiService.importWikiSites(courseInfo.getId(), selectedCourseId);
		
		return Constants.WIKI_OVERVIEW;
	}

	public SecurityService getSecurityService() {
		return securityService;
	}

	public void setSecurityService(SecurityService securityService) {
		this.securityService = securityService;
	}

	public CourseDao getCourseDao() {
		return courseDao;
	}

	public void setCourseDao(CourseDao courseDao) {
		this.courseDao = courseDao;
	}

	public Long getSelectedCourseId() {
		return selectedCourseId;
	}

	public void setSelectedCourseId(Long selectedCourseId) {
		this.selectedCourseId = selectedCourseId;
	}

	@SuppressWarnings("unchecked")
	public List<SelectItem> getExportableWikiCourses() {
		// TODO: this is very dirty!
		boolean firstIteration = true;
		
		if (exportableWikiCourses == null) {
			exportableWikiCourses = new ArrayList<SelectItem>();
			
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
					if (firstIteration) {
						SelectItem item = new SelectItem();
						item.setLabel(i18n("please_choose"));
						item.setDisabled(true);
						exportableWikiCourses.add(item);
						
						firstIteration = false;
					}
					
					exportableWikiCourses.add(new SelectItem(courseInfo.getId(), courseInfo.getName()));
				}
			}
		}

		return exportableWikiCourses;
	}

	public void setExportableWikiCourses(List<SelectItem> exportableWikiCourses) {
		this.exportableWikiCourses = exportableWikiCourses;
	}
	
}