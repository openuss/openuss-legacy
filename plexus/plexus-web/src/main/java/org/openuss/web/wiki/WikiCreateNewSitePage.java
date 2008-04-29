package org.openuss.web.wiki;

import org.apache.log4j.Logger;
import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.Prerender;
import org.apache.shale.tiger.view.View;
import org.openuss.framework.jsfcontrols.breadcrumbs.BreadCrumb;
import org.openuss.web.Constants;
import org.openuss.web.PageLinks;
import org.openuss.wiki.WikiSiteContentInfo;

/**
 * Backing Bean for wikicreatesite.xhtml.
 * @author Projektseminar WS 07/08, Team Collaboration
 *
 */
@Bean(name = "views$secured$wiki$wikicreatenewsite", scope = Scope.REQUEST)
@View
public class WikiCreateNewSitePage extends AbstractWikiPage {
	
	private static final Logger LOGGER = Logger.getLogger(WikiEditPage.class);
	
	@Override
	@Prerender
	public void prerender() throws Exception { // NOPMD by Administrator on 13.03.08 12:58
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
		wikiBreadCrumb.setName(i18n(Constants.WIKI_MAIN_HEADER));
		wikiBreadCrumb.setHint(i18n(Constants.WIKI_MAIN_HEADER));
		breadcrumbs.addCrumb(wikiBreadCrumb);
		
		final BreadCrumb wikiSiteBreadCrumb = new BreadCrumb();
		wikiSiteBreadCrumb.setName(readablePageName(i18n(Constants.WIKI_NEW_SITE_HEADER)));
		wikiSiteBreadCrumb.setHint(readablePageName(i18n(Constants.WIKI_NEW_SITE_HEADER)));
		breadcrumbs.addCrumb(wikiSiteBreadCrumb);
	}
	
	
	
	/**
	 * Opens the Create or View Site Page.
	 * @return Wiki Edit Page or Wiki Main Page.
	 */
	public String createNewSite() {
		WikiSiteContentInfo site = wikiService.findWikiSiteContentByDomainObjectAndName(courseInfo.getId(), siteVersionInfo.getName());
		if (site == null) {
			LOGGER.debug("Site '" + siteVersionInfo.getName() + "' does not exist. Opening Create New Site Page.");	
			return Constants.WIKI_EDIT_PAGE;
		} else {
			LOGGER.debug("Site '" + siteVersionInfo.getName() + "' does already exist. Opening Edit Site Page.");
			this.addMessage(i18n("wiki_new_site_already_exists_message", siteVersionInfo.getName()));
			return Constants.WIKI_MAIN_PAGE;
		}
	}
	
}