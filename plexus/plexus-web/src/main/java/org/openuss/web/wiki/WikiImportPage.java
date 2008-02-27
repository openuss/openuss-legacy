package org.openuss.web.wiki;

import java.util.LinkedList;
import java.util.List;

import javax.faces.model.SelectItem;

import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.Prerender;
import org.apache.shale.tiger.view.View;
import org.openuss.framework.jsfcontrols.breadcrumbs.BreadCrumb;
import org.openuss.lecture.CourseInfo;
import org.openuss.web.Constants;
import org.openuss.web.PageLinks;

/**
 * Backing Bean for wikiimport.xhtml.
 * @author Projektseminar WS 07/08, Team Collaboration
 *
 */
@Bean(name = "views$secured$wiki$wikiimport", scope = Scope.REQUEST)
@View
public class WikiImportPage extends AbstractWikiPage{
	
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
	
	/**
	 * Sets selected Import Course and Import Sites as Import Type and returns the Wiki Import Confirmation Page.
	 * @return Wiki Import Confirmation Page.
	 */
	public String importWikiSites() {
		setSessionBean(Constants.WIKI_IMPORT_COURSE, selectedCourseId);
		setSessionBean(Constants.WIKI_IMPORT_TYPE, Constants.WIKI_IMPORT_TYPE_IMPORT_WIKI_SITES);
		
		return Constants.WIKI_IMPORT_CONFIRMATION_PAGE;
	}
	
	/**
	 * Sets selected Import Course and Import Versions as Import Type and returns the Wiki Import Confirmation Page.
	 * @return Wiki Import Confirmation Page.
	 */
	public String importWikiVersions() {
		setSessionBean(Constants.WIKI_IMPORT_COURSE, selectedCourseId);
		setSessionBean(Constants.WIKI_IMPORT_TYPE, Constants.WIKI_IMPORT_TYPE_IMPORT_WIKI_VERSIONS);
		
		return Constants.WIKI_IMPORT_CONFIRMATION_PAGE;
	}

	/**
	 * Finds all exportable Wiki Courses.
	 * @return List of all exportable Wiki Courses as SelectItem object.
	 */
	@SuppressWarnings("unchecked")
	public List<SelectItem> getExportableWikiCourses() {
		List<CourseInfo> exportableWikiCourses = wikiService.findAllExportableWikiCoursesByInstituteAndUser(instituteInfo, user, courseInfo);
		List<SelectItem> exportableSelectItems = new LinkedList<SelectItem>();
		
		if (!exportableWikiCourses.isEmpty()) {
			SelectItem pleaseChooseItem = new SelectItem();
			pleaseChooseItem.setLabel(i18n("please_choose"));
			pleaseChooseItem.setDisabled(true);
			exportableSelectItems.add(pleaseChooseItem);
		}
		
		for (CourseInfo exportableWikiCourse : exportableWikiCourses) {
			SelectItem exportableSelectItem = new SelectItem(exportableWikiCourse.getId(), exportableWikiCourse.getName());
			exportableSelectItems.add(exportableSelectItem);
		}
		
		return exportableSelectItems;
	}
	
	/**
	 * Returns selected Import Course ID.
	 * @return Selected Import Course ID.
	 */
	public Long getSelectedCourseId() {
		return selectedCourseId;
	}

	/**
	 * Sets selected Import Course ID.
	 * @param selectedCourseId Selected Import Course ID.
	 */
	public void setSelectedCourseId(Long selectedCourseId) {
		this.selectedCourseId = selectedCourseId;
	}
	
}