package org.openuss.web.wiki;

import org.apache.log4j.Logger;
import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Scope;
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
	
	private static final Logger LOGGER = Logger.getLogger(WikiOverviewPage.class);

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
