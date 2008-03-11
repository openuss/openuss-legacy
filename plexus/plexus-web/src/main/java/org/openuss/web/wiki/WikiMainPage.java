package org.openuss.web.wiki;


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Locale;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.Prerender;
import org.apache.shale.tiger.view.View;
import org.openuss.framework.utilities.URLUTF8Encoder;
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
		if (!checkSession()) {
			return;
		}
		
		if (this.siteVersionId != null) {
			siteVersionInfo = wikiService.getWikiSiteContent(siteVersionId);
		} else {
			String name = getPageName(this.siteName);

			WikiSiteContentInfo version = this.wikiService.findWikiSiteContentByDomainObjectAndName(this.courseInfo.getId(), name);
			
			if (version == null && Constants.WIKI_STARTSITE_NAME.equals(name)) {
				createInfoIndexPage();
			} else if (version == null) {
				setSessionBean(Constants.WIKI_NEW_SITE_BACKUP, this.siteVersionInfo);
				setSessionBean(Constants.WIKI_NEW_SITE_NAME, name);
				this.siteVersionInfo = null;
				this.siteName = null;
			} else {
				this.siteVersionInfo = version;
			}
		}
		
		setSessionBean(Constants.WIKI_CURRENT_SITE_VERSION, this.siteVersionInfo);
				
		super.prerender();
	}
		
	private String getPageName(String siteName) {
		if (siteName != null) {
			return URLUTF8Encoder.decode(siteName);
		} else if (this.siteVersionInfo != null && this.siteVersionInfo.getName() != null) {
			return this.siteVersionInfo.getName();
		} else {
			return Constants.WIKI_STARTSITE_NAME;
		}
	}

	private void createInfoIndexPage() {
		Locale locale = new Locale(getUser().getLocale());
		String country = locale.getLanguage();
		
		InputStream inStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("wiki_index_" + country + ".xhtml");
		if (inStream == null) {
			inStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("wiki_index.xhtml");
		}
		if (inStream != null) {
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			try {
				IOUtils.copyLarge(inStream, out);
				
				saveIndexVersion(out.toString());
			} catch (IOException ex) {
				LOGGER.error("Error creating info page.", ex);
			} finally {
				try {
					inStream.close();
					out.close();
				} catch (IOException e) {
					LOGGER.error("Error reading wiki_index.xhtml", e);
				}
			}
		}
	}

	private WikiSiteContentInfo saveIndexVersion(String text) {
		this.siteVersionInfo = new WikiSiteContentInfo();
		this.siteVersionInfo.setId(null);
		this.siteVersionInfo.setName(Constants.WIKI_STARTSITE_NAME);
		this.siteVersionInfo.setText(text);
		this.siteVersionInfo.setNote(i18n("wiki_index_description_note"));

		this.siteVersionInfo.setCreationDate(new Date());
		this.siteVersionInfo.setAuthorId(user.getId());
		this.siteVersionInfo.setDomainId(this.courseInfo.getId());
		this.siteVersionInfo.setDeleted(false);
		this.siteVersionInfo.setReadOnly(false);
		this.siteVersionInfo.setStable(false);
		
		getWikiService().saveWikiSite(this.siteVersionInfo);
		setSessionBean(Constants.WIKI_CURRENT_SITE_VERSION, this.siteVersionInfo);
		
		return siteVersionInfo;
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
	 */
	public String showStable() {
		WikiSiteContentInfo wikiSiteContentInfo = wikiService.getNewestStableWikiSiteContent(siteVersionInfo.getWikiSiteId());
		
		if (wikiSiteContentInfo == null) {
			wikiSiteContentInfo = wikiService.getNewestWikiSiteContent(siteVersionInfo.getWikiSiteId());
		}
		
		siteVersionInfo = wikiSiteContentInfo;
		siteVersionId = siteVersionInfo.getId();
		
		return Constants.WIKI_MAIN_PAGE;
	}
	
	/**
	 * Marks a Version as stable and returns the Wiki Main Page.
	 * @return Wiki Main Page.
	 */
	public String markStable() {
		LOGGER.debug("Marking Site " + siteVersionInfo.getName() + " stable.");
		if (this.siteVersionInfo == null || this.siteVersionInfo.getDomainId() == null) {
			LOGGER.error("Site version info incomplete!");
			addError(i18n("wiki_error_siteinfo_not_found"));
			return Constants.WIKI_MAIN_PAGE;
		}
		
		siteVersionInfo.setStable(true);
		wikiService.saveWikiSite(siteVersionInfo);
		
		addMessage(i18n("wiki_message_mark_stable_successful"));
		return Constants.WIKI_MAIN_PAGE;
	}
	
	/**
	 * Unmarks a Version as stable and returns the Wiki Main Page.
	 * @return Wiki Main Page.
	 */
	public String unmarkStable() {
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
		siteVersionId = null;
		siteName = null;
		
		setSessionBean(Constants.WIKI_CURRENT_SITE_VERSION, null);
		
		return Constants.WIKI_MAIN_PAGE;
	}
	
	public String getSiteTitleNoVersion() {
		if (siteVersionInfo.getName() == null) {
			return siteName;
		}
		
		return readablePageName(siteVersionInfo.getName());
	}
	
	public Boolean getHasStableVersion() {
		return wikiService.getNewestStableWikiSiteContent(siteVersionInfo.getWikiSiteId()) != null;
	}
}