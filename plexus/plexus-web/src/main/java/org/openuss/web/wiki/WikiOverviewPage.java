package org.openuss.web.wiki;

import org.apache.log4j.Logger;
import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.Prerender;
import org.apache.shale.tiger.view.View;
import org.openuss.web.Constants;
import org.openuss.wiki.WikiSiteContentInfo;
import org.openuss.wiki.WikiSiteInfo;

@Bean(name = "views$secured$wiki$wikioverview", scope = Scope.REQUEST)
@View
public class WikiOverviewPage extends AbstractWikiPage {
	
	private static final Logger LOGGER = Logger.getLogger(WikiEditLinksPage.class);
	
	@Override
	@Prerender
	public void prerender() throws Exception {
		super.prerender();
	}

	public String removeSite() {
		setSessionBean(Constants.WIKI_SITE_TO_REMOVE, getData().getRowData());
		
		return Constants.WIKI_REMOVE_SITE_PAGE;
	}
	
	public String markSiteForRemoval() {
		final WikiSiteInfo wikiSiteInfo = getData().getRowData();		
		wikiSiteInfo.setDeleted(true);
		
		wikiService.saveWikiSite(wikiSiteInfo);
		
		return Constants.WIKI_OVERVIEW;
	}
	
	public String recoverSite() {
		final WikiSiteInfo wikiSiteInfo = getData().getRowData();		
		wikiSiteInfo.setDeleted(false);
		
		wikiService.saveWikiSite(wikiSiteInfo);
		
		return Constants.WIKI_OVERVIEW;
	}
	
	public String lockSite() {
		final WikiSiteInfo wikiSiteInfo = getData().getRowData();		
		wikiSiteInfo.setReadOnly(true);
		
		wikiService.saveWikiSite(wikiSiteInfo);
		
		return Constants.WIKI_OVERVIEW;
	}
	
	public String unlockSite() {
		final WikiSiteInfo wikiSiteInfo = getData().getRowData();		
		wikiSiteInfo.setReadOnly(false);
		
		wikiService.saveWikiSite(wikiSiteInfo);
		
		return Constants.WIKI_OVERVIEW;
	}
}
