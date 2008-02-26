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
import org.openuss.framework.jsfcontrols.breadcrumbs.BreadCrumb;
import org.openuss.lecture.CourseDao;
import org.openuss.lecture.CourseInfo;
import org.openuss.lecture.CourseMemberInfo;
import org.openuss.security.SecurityService;
import org.openuss.security.User;
import org.openuss.web.Constants;
import org.openuss.web.PageLinks;

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
	
	@Override
	@Prerender
	public void prerender() throws Exception {
		super.prerender();
		
		addBreadCrumbs();
	}
	
	/**
	 * Adds an additional BreadCrumb to the course crumbs.
	 */
	private void addBreadCrumbs() {
		breadcrumbs.loadCourseCrumbs(courseInfo);
		
		BreadCrumb wikiBreadCrumb = new BreadCrumb();
		wikiBreadCrumb.setLink(PageLinks.WIKI_MAIN);
		wikiBreadCrumb.setName(i18n("wiki_main_header"));
		wikiBreadCrumb.setHint(i18n("wiki_main_header"));
		breadcrumbs.addCrumb(wikiBreadCrumb);
		
		BreadCrumb importWikiBreadCrumb = new BreadCrumb();
		importWikiBreadCrumb.setName(i18n("wiki_import_wiki"));
		importWikiBreadCrumb.setHint(i18n("wiki_import_wiki"));
		breadcrumbs.addCrumb(importWikiBreadCrumb);
	}
	
	public String importWikiSites() {
		setSessionBean(Constants.WIKI_IMPORT_COURSE, selectedCourseId);
		setSessionBean(Constants.WIKI_IMPORT_TYPE, Constants.WIKI_IMPORT_TYPE_IMPORT_WIKI_SITES);
		
		return Constants.WIKI_IMPORT_CONFIRMATION_PAGE;
	}
	
	public String importWikiVersions() {
		setSessionBean(Constants.WIKI_IMPORT_COURSE, selectedCourseId);
		setSessionBean(Constants.WIKI_IMPORT_TYPE, Constants.WIKI_IMPORT_TYPE_IMPORT_WIKI_VERSIONS);
		
		return Constants.WIKI_IMPORT_CONFIRMATION_PAGE;
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
			for (CourseInfo availableCourse : availableCourses) {
				if (!availableCourse.equals(courseInfo)) {
					List<CourseMemberInfo> assistants = courseService.getAssistants(availableCourse);
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
						
						exportableWikiCourses.add(new SelectItem(availableCourse.getId(), availableCourse.getName()));
					}
				}
			}
		}

		return exportableWikiCourses;
	}

	public void setExportableWikiCourses(List<SelectItem> exportableWikiCourses) {
		this.exportableWikiCourses = exportableWikiCourses;
	}
	
}