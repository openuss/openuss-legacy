package org.openuss.web.wiki;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.Prerender;
import org.apache.shale.tiger.view.View;
import org.openuss.foundation.ApplicationException;
import org.openuss.web.Constants;
import org.openuss.wiki.WikiSiteContentInfo;
import org.openuss.wiki.WikiSiteInfo;

/**
 * Backing Bean for wikimain.xhtml.
 * @author Projektseminar WS 07/08, Team Collaboration
 *
 */
@Bean(name = "views$secured$wiki$wikimain", scope = Scope.REQUEST)
@View
public class WikiMainPage extends AbstractWikiPage {
	
	private static final Logger LOGGER = Logger.getLogger(WikiMainPage.class);
	
	@Override
	@Prerender
	public void prerender() throws Exception { 
		if (!checkCourseInfo()) {
			return;
		}
		
		siteVersionInfo = (WikiSiteContentInfo) getBean(Constants.WIKI_CURRENT_SITE_VERSION);
		
		if (isPostBack() && siteVersionInfo != null && siteVersionInfo.getId() != null) {
			siteVersionInfo = wikiService.getWikiSiteContent(this.siteVersionInfo.getId());
		} else {
			String siteName = null;
			if (siteVersionInfo != null && StringUtils.isNotBlank(siteVersionInfo.getName())) {
				siteName = this.siteVersionInfo.getName().trim();
				
				if (!isValidWikiSiteName(siteName)) {
					addError(i18n("wiki_error_illegal_site_name"));
					return;
				}
	
				siteName = formatPageName(siteName);
			} else {
				siteName = Constants.WIKI_STARTSITE_NAME;
			}

			WikiSiteContentInfo version = this.wikiService.findWikiSiteContentByDomainObjectAndName(courseInfo.getId(), siteName);
			
			if (version == null) {
				this.siteVersionInfo = new WikiSiteContentInfo();
				siteVersionInfo.setName(siteName);
				setBean(Constants.WIKI_CURRENT_SITE_VERSION, siteVersionInfo);
			} else {
				this.siteVersionInfo = version;
			}
		}

		setBean(Constants.WIKI_CURRENT_SITE_VERSION, this.siteVersionInfo);
				
		super.prerender();
	}
	
	/**
	 * Returns the Wiki Overview Page.
	 * @return Wiki Overview Page.
	 */
	public String overview() {
		return Constants.WIKI_OVERVIEW;
	}
	
	/**
	 * Recovers a Site and returns the Wiki Current Site Version Page.
	 * @return Wiki Current Site Version Page.
	 */
	public String recoverSite() {
		final WikiSiteInfo wikiSiteInfo = wikiService.getWikiSite(siteVersionInfo.getWikiSiteId());
		LOGGER.debug("Recovering Site " + wikiSiteInfo.getName() + ".");
		wikiSiteInfo.setDeleted(false);
		
		wikiService.saveWikiSite(wikiSiteInfo);
		
		addMessage(i18n("wiki_message_recover_successful"));
		return Constants.WIKI_CURRENT_SITE_VERSION;
	}
	
	/**
	 * Recovers a Site and returns the Wiki Current Site Version Page.
	 * @return Wiki Current Site Version Page.
	 */
	public String lockSite() {
		if (this.siteVersionInfo == null || this.siteVersionInfo.getWikiSiteId() == null) {
			LOGGER.error("Site version info incomplete!");
			addError(i18n("wiki_error_siteinfo_not_found"));
			return Constants.WIKI_MAIN_PAGE;
		}
		
		final WikiSiteInfo wikiSiteInfo = this.wikiService.getWikiSite(this.siteVersionInfo.getWikiSiteId());
		LOGGER.debug("Locking Site " + wikiSiteInfo.getName() + ".");
		wikiSiteInfo.setReadOnly(true);
		
		wikiService.saveWikiSite(wikiSiteInfo);
		
		addMessage(i18n("wiki_message_lock_successful"));
		return Constants.WIKI_CURRENT_SITE_VERSION;
	}
	
	/**
	 * Unlocks a Site and returns the Wiki Current Site Version Page.
	 * @return Wiki Current Site Version Page.
	 */
	public String unlockSite() {
		final WikiSiteInfo wikiSiteInfo = this.wikiService.getWikiSite(this.siteVersionInfo.getWikiSiteId());
		LOGGER.debug("Unlocking Site " + wikiSiteInfo.getName() + ".");
		wikiSiteInfo.setReadOnly(false);
		
		wikiService.saveWikiSite(wikiSiteInfo);
		
		addMessage(i18n("wiki_message_unlock_successful"));
		return Constants.WIKI_CURRENT_SITE_VERSION;
	}
	
	/**
	 * Returns the Wiki Import Page.
	 * @return Wiki Import Page.
	 */
	public String showImportPage() {
		return Constants.WIKI_IMPORT_PAGE;
	}
	
	/**
	 * Returns the stable Version of a Site.
	 * @return Wiki Main Page.
	 * @throws ApplicationException 
	 */
	public String showStable() throws ApplicationException {
//		reloadWikiSiteInfo();

		WikiSiteContentInfo site = wikiService.getNewestStableWikiSiteContent(siteVersionInfo.getWikiSiteId());
		if (site == null) {
			site = wikiService.getNewestWikiSiteContent(siteVersionInfo.getWikiSiteId());
		}
		
		siteVersionInfo = site;
		setBean(Constants.WIKI_CURRENT_SITE_VERSION, siteVersionInfo);
		
		return Constants.WIKI_MAIN_PAGE;
	}
	
	/**
	 * Marks a Version as stable and returns the Wiki Main Page.
	 * @return Wiki Main Page.
	 * @throws ApplicationException 
	 */
	public String markStable() throws ApplicationException {
		reloadWikiSiteInfo();
		
		LOGGER.debug("Marking Site " + siteVersionInfo.getName() + " stable.");
		siteVersionInfo.setStable(true);
		wikiService.saveWikiSite(siteVersionInfo);
		
		addMessage(i18n("wiki_message_mark_stable_successful"));
		return Constants.WIKI_MAIN_PAGE;
	}
	
	/**
	 * Unmarks a Version as stable and returns the Wiki Main Page.
	 * @return Wiki Main Page.
	 * @throws ApplicationException 
	 */
	public String unmarkStable() throws ApplicationException {
		reloadWikiSiteInfo();
		
		LOGGER.debug("Unmarking Site " + siteVersionInfo.getName() + " stable.");
		siteVersionInfo.setStable(false);
		wikiService.saveWikiSite(siteVersionInfo);
		
		addMessage(i18n("wiki_message_unmark_stable_successful"));
		return Constants.WIKI_MAIN_PAGE;
	}
	
	/**
	 * Shows Wiki Start Page. Resets Session.
	 * @return Wiki Main Page.
	 */
	public String showStartPage() {
		siteVersionInfo = null;
		
		setSessionBean(Constants.WIKI_CURRENT_SITE_VERSION, null);
		
		return Constants.WIKI_MAIN_PAGE;
	}
	
	public String getSiteTitleNoVersion() {
		return readablePageName(siteVersionInfo.getName());
	}
	
	public Boolean getHasStableVersion() {
		return wikiService.getNewestStableWikiSiteContent(siteVersionInfo.getWikiSiteId()) != null;
	}
}