package org.openuss.web.wiki;


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Locale;

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
		if (courseInfo == null || courseInfo.getId() == null) {
			addError(i18n("message_error_course_page"));
			redirect(Constants.OUTCOME_BACKWARD);
			return;
		}
		
		if (siteVersionId != null) {
			siteVersionInfo = wikiService.getWikiSiteContent(siteVersionId);
		} else {
			String pageName = Constants.WIKI_STARTSITE_NAME;
			if (this.siteName != null) {
				pageName = URLUTF8Encoder.decode(this.siteName);
			} else if (this.siteVersionInfo != null && this.siteVersionInfo.getName() != null) {
				pageName = this.siteVersionInfo.getName();
			}

			final WikiSiteContentInfo backup = this.siteVersionInfo;
			this.siteVersionInfo = this.wikiService.findWikiSiteContentByDomainObjectAndName(this.courseInfo.getId(), pageName);
			
			if (this.siteVersionInfo == null && 
					Constants.WIKI_STARTSITE_NAME.equals(pageName)) {
				createInfoIndexPage();
			} else if (this.siteVersionInfo == null) {
				setSessionBean(Constants.WIKI_NEW_SITE_BACKUP, backup);
				setSessionBean(Constants.WIKI_NEW_SITE_NAME, pageName);
				this.siteName = null;
			}
		}
		
		setSessionBean(Constants.WIKI_CURRENT_SITE_VERSION, this.siteVersionInfo);

		super.prerender();
	}
	
	private void createInfoIndexPage() {
		String text = "<h1>Wiki</h1>";
		
		Locale locale = new Locale(getUser().getLocale());
		String country = locale.getLanguage();
		
		InputStream in = getClass().getClassLoader().getResourceAsStream("wiki_index_" + country + ".xhtml");
		if (in == null) {
			in = getClass().getClassLoader().getResourceAsStream("wiki_index.xhtml");
		}
		if (in != null) {
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			byte buf[] = new byte[4096];
			int i;
			try {
				while ((i = in.read(buf)) != -1) {
					out.write(buf, 0, i);
				}
			} catch (IOException e) {
				LOGGER.error("Error reading wiki_index.xhtml", e);
			} finally {
				try {
					in.close();
					out.close();
				} catch (IOException e) {
					LOGGER.error("Error reading wiki_index.xhtml", e);
				}
				
			}
			text = out.toString();
		}
		
		siteVersionInfo = new WikiSiteContentInfo();
		siteVersionInfo.setId(null);
		siteVersionInfo.setName(Constants.WIKI_STARTSITE_NAME);
		siteVersionInfo.setText(text);
		siteVersionInfo.setNote(i18n("wiki_index_description_note"));

		siteVersionInfo.setCreationDate(new Date());
		siteVersionInfo.setAuthorId(user.getId());
		siteVersionInfo.setDomainId(this.courseInfo.getId());
		siteVersionInfo.setDeleted(false);
		siteVersionInfo.setReadOnly(false);
		siteVersionInfo.setStable(false);
		
		getWikiService().saveWikiSite(this.siteVersionInfo);
		setSessionBean(Constants.WIKI_CURRENT_SITE_VERSION, this.siteVersionInfo);
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