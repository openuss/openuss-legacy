package org.openuss.web.wiki;

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
 * Backing Bean for wikioverview.xhtml.
 * @author Projektseminar WS 07/08, Team Collaboration
 *
 */
@Bean(name = "views$secured$wiki$wikioverview", scope = Scope.REQUEST)
@View
public class WikiOverviewPage extends AbstractWikiPage {
	
	private static final Logger LOGGER = Logger.getLogger(WikiOverviewPage.class);

	
	@Override
	@Prerender
	public void prerender() throws Exception { // NOPMD by Administrator on 13.03.08 12:59
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
		wikiSiteBreadCrumb.setName(readablePageName(i18n(Constants.WIKI_OVERVIEW_HEADER)));
		wikiSiteBreadCrumb.setHint(readablePageName(i18n(Constants.WIKI_OVERVIEW_HEADER)));
		breadcrumbs.addCrumb(wikiSiteBreadCrumb);
	}
	
	
	/**
	 * Removes a Site and returns Wiki Remove Site Page.
	 * @return Wiki Remove Site Page.
	 */
	public String removeSite() {
		setBean(Constants.WIKI_SITE_TO_REMOVE, getData().getRowData());
		return Constants.WIKI_REMOVE_SITE_PAGE;
	}
	
	/**
	 * Marks a Site for removal and returns Wiki Remove Overview Page.
	 * @return Wiki Overview Page.
	 */
	public String markSiteForRemoval() {
		final WikiSiteInfo wikiSiteInfo = getData().getRowData();
		LOGGER.debug("Marking Site " + wikiSiteInfo.getName() + " for removal.");
		wikiSiteInfo.setDeleted(true);
		
		wikiService.saveWikiSite(wikiSiteInfo);
		
		addMessage(i18n("wiki_message_mark_for_removal_successful"));
		return Constants.WIKI_OVERVIEW;
	}
	
	/**
	 * Recovers a Site and returns Wiki Overview Page.
	 * @return Wiki Overview Page.
	 */
	public String recoverSite() {
		final WikiSiteInfo wikiSiteInfo = getData().getRowData();
		LOGGER.debug("Recovering Site " + wikiSiteInfo.getName() + ".");
		wikiSiteInfo.setDeleted(false);
		
		wikiService.saveWikiSite(wikiSiteInfo);
		
		addMessage(i18n("wiki_message_recover_successful"));
		return Constants.WIKI_OVERVIEW;
	}
	
	/**
	 * Locks a Site and returns Wiki Overview Page.
	 * @return Wiki Overview Page.
	 */
	public String lockSite() {
		final WikiSiteInfo wikiSiteInfo = getData().getRowData();
		LOGGER.debug("Locking Site " + wikiSiteInfo.getName() + ".");
		wikiSiteInfo.setReadOnly(true);
		
		wikiService.saveWikiSite(wikiSiteInfo);
		
		addMessage(i18n("wiki_message_lock_successful"));
		return Constants.WIKI_OVERVIEW;
	}
	
	/**
	 * Unlocks a Site and returns Wiki Overview Page.
	 * @return Wiki Overview Page.
	 */
	public String unlockSite() {
		final WikiSiteInfo wikiSiteInfo = getData().getRowData();
		LOGGER.debug("Unlocking Site " + wikiSiteInfo.getName() + ".");
		wikiSiteInfo.setReadOnly(false);
		
		wikiService.saveWikiSite(wikiSiteInfo);
		
		addMessage(i18n("wiki_message_unlock_successful"));
		return Constants.WIKI_OVERVIEW;
	}
}
