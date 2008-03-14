package org.openuss.web.wiki;

import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;
import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.Prerender;
import org.apache.shale.tiger.view.View;
import org.openuss.framework.jsfcontrols.breadcrumbs.BreadCrumb;
import org.openuss.web.Constants;
import org.openuss.web.PageLinks;
import org.openuss.wiki.WikiSiteInfo;

/**
 * Backing Bean for wikiimportconfirmation.xhtml.
 * @author Projektseminar WS 07/08, Team Collaboration
 *
 */
@Bean(name = "views$secured$wiki$wikiimportconfirmation", scope = Scope.REQUEST)
@View
public class WikiImportConfirmationPage extends AbstractWikiPage {
	
	private static final Logger LOGGER = Logger.getLogger(WikiImportConfirmationPage.class);

	@Override
	@Prerender
	public void prerender() throws Exception { // NOPMD by Administrator on 13.03.08 12:58
		super.prerender();
		
		addBreadCrumbs();
		
		if (!isConfirmationRequired()) {
			importWiki();
			redirect(Constants.WIKI_OVERVIEW);
		}
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
	 * Checks if Confirmation is required or not.
	 * @return <code>true</code> if there are already existing WikiSites and WikiVersions in the current course, otherwise <code>false</code>.
	 */
	@SuppressWarnings("unchecked")
	private boolean isConfirmationRequired() {
		final List<WikiSiteInfo> wikiSites = wikiService.findWikiSitesByDomainObject(courseInfo.getId());
		if (wikiSites.size() != 1) {
			return true;
		}
		
		final List<WikiSiteInfo> wikiSiteVersions = wikiService.findWikiSiteVersionsByWikiSite(wikiSites.get(0).getWikiSiteId());
		return (wikiSiteVersions.size() != 1);
	}
	
	/**
	 * Imports the Wiki.
	 * @return Wiki Overview Page.
	 */
	public String importWiki() {
		final Long selectedCourseId = (Long) getSessionBean(Constants.WIKI_IMPORT_COURSE); // NOPMD by Administrator on 13.03.08 12:58
		final String importType = (String) getSessionBean(Constants.WIKI_IMPORT_TYPE);
		
		if (Constants.WIKI_IMPORT_TYPE_IMPORT_WIKI_SITES.equals(importType)) {
			LOGGER.debug("Importing WikiSiteVersions from DomainID " + selectedCourseId + ".");
			wikiService.importWikiSites(courseInfo.getId(), selectedCourseId);
		} else if (Constants.WIKI_IMPORT_TYPE_IMPORT_WIKI_VERSIONS.equals(importType)) {
			LOGGER.debug("Importing WikiSites from DomainID " + selectedCourseId + ".");
			wikiService.importWikiVersions(courseInfo.getId(), selectedCourseId);
		} else {
			throw new WikiUnexpectedImportTypeException();
		}
		
		addMessage(i18n("wiki_message_import_successful"));
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