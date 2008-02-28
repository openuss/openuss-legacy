package org.openuss.web.wiki;

import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.Prerender;
import org.apache.shale.tiger.view.View;
import org.openuss.web.Constants;
import org.openuss.wiki.WikiSiteInfo;

/**
 * Backing Bean for wikioverview.xhtml.
 * @author Projektseminar WS 07/08, Team Collaboration
 *
 */
@Bean(name = "views$secured$wiki$wikioverview", scope = Scope.REQUEST)
@View
public class WikiOverviewPage extends AbstractWikiPage {
	
	@Override
	@Prerender
	public void prerender() throws Exception {
		super.prerender();
	}

	/**
	 * Removes a Site and returns Wiki Remove Site Page.
	 * @return Wiki Remove Site Page.
	 */
	public String removeSite() {
		setSessionBean(Constants.WIKI_SITE_TO_REMOVE, getData().getRowData());
		
		return Constants.WIKI_REMOVE_SITE_PAGE;
	}
	
	/**
	 * Marks a Site for removal and returns Wiki Remove Overview Page.
	 * @return Wiki Overview Page.
	 */
	public String markSiteForRemoval() {
		final WikiSiteInfo wikiSiteInfo = getData().getRowData();		
		wikiSiteInfo.setDeleted(true);
		
		wikiService.saveWikiSite(wikiSiteInfo);
		
		return Constants.WIKI_OVERVIEW;
	}
	
	/**
	 * Recovers a Site and returns Wiki Overview Page.
	 * @return Wiki Overview Page.
	 */
	public String recoverSite() {
		final WikiSiteInfo wikiSiteInfo = getData().getRowData();		
		wikiSiteInfo.setDeleted(false);
		
		wikiService.saveWikiSite(wikiSiteInfo);
		
		return Constants.WIKI_OVERVIEW;
	}
	
	/**
	 * Locks a Site and returns Wiki Overview Page.
	 * @return Wiki Overview Page.
	 */
	public String lockSite() {
		final WikiSiteInfo wikiSiteInfo = getData().getRowData();		
		wikiSiteInfo.setReadOnly(true);
		
		wikiService.saveWikiSite(wikiSiteInfo);
		
		return Constants.WIKI_OVERVIEW;
	}
	
	/**
	 * Unlocks a Site and returns Wiki Overview Page.
	 * @return Wiki Overview Page.
	 */
	public String unlockSite() {
		final WikiSiteInfo wikiSiteInfo = getData().getRowData();		
		wikiSiteInfo.setReadOnly(false);
		
		wikiService.saveWikiSite(wikiSiteInfo);
		
		return Constants.WIKI_OVERVIEW;
	}
}
