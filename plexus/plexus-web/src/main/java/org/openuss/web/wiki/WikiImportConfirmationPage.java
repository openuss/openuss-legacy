package org.openuss.web.wiki;

import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;

import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.Prerender;
import org.apache.shale.tiger.view.View;
import org.openuss.framework.jsfcontrols.breadcrumbs.BreadCrumb;
import org.openuss.web.Constants;
import org.openuss.web.PageLinks;

/**
 * Backing Bean for wikiimportconfirmation.xhtml.
 * @author Projektseminar WS 07/08, Team Collaboration
 *
 */
@Bean(name = "views$secured$wiki$wikiimportconfirmation", scope = Scope.REQUEST)
@View
public class WikiImportConfirmationPage extends AbstractWikiPage {

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
		
		final BreadCrumb wikiBreadCrumb = new BreadCrumb();
		wikiBreadCrumb.setLink(PageLinks.WIKI_MAIN);
		wikiBreadCrumb.setName(i18n("wiki_main_header"));
		wikiBreadCrumb.setHint(i18n("wiki_main_header"));
		breadcrumbs.addCrumb(wikiBreadCrumb);
		
		final BreadCrumb importWikiBreadCrumb = new BreadCrumb();
		importWikiBreadCrumb.setName(i18n("wiki_import_wiki"));
		importWikiBreadCrumb.setHint(i18n("wiki_import_wiki"));
		breadcrumbs.addCrumb(importWikiBreadCrumb);
	}
	
	/**
	 * Imports the Wiki.
	 * @return Wiki Overview Page.
	 */
	public String importWiki() {
		final Long selectedCourseId = (Long) getSessionBean(Constants.WIKI_IMPORT_COURSE);
		final String importType = (String) getSessionBean(Constants.WIKI_IMPORT_TYPE);
		
		if (Constants.WIKI_IMPORT_TYPE_IMPORT_WIKI_SITES.equals(importType)) {
			wikiService.importWikiSites(courseInfo.getId(), selectedCourseId);
		} else if (Constants.WIKI_IMPORT_TYPE_IMPORT_WIKI_VERSIONS.equals(importType)) {
			wikiService.importWikiVersions(courseInfo.getId(), selectedCourseId);
		} else {
			throw new RuntimeException("Unexpected Wiki Import Type.");
		}
		
		return Constants.WIKI_OVERVIEW;
	}
	
	/**
	 * Validator to check whether the user has accepted the user agreement or not.
	 * 
	 * @param context FacesContext.
	 * @param toValidate UIComponent that has to be validated.
	 * @param value Inserted Value.
	 */
	public void validateRemoveConfirmation(FacesContext context, UIComponent toValidate, Object value) {
		final boolean accept = (Boolean) value;
		if (!accept) {
			((UIInput) toValidate).setValid(false);
			addError(toValidate.getClientId(context), i18n("error_need_to_confirm_removement"), null);
		}
	}
}
