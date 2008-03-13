package org.openuss.web.wiki;

import java.util.Date;

import org.apache.log4j.Logger;
import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.Prerender;
import org.apache.shale.tiger.view.View;
import org.openuss.web.Constants;
import org.openuss.wiki.WikiSiteContentInfo;

/**
 * Backing Bean for wikiedit.xhtml.
 * @author Projektseminar WS 07/08, Team Collaboration
 *
 */
@Bean(name = "views$secured$wiki$wikiedit", scope = Scope.REQUEST)
@View
public class WikiEditPage extends AbstractWikiPage {
	
	private static final Logger LOGGER = Logger.getLogger(WikiEditPage.class);
	
	@Override
	@Prerender
	public void prerender() throws Exception { // NOPMD by Administrator on 13.03.08 12:58
		super.prerender();
		
		siteVersionInfo.setDomainId(this.courseInfo.getId());
		siteVersionInfo.setNote(null);
	}
	
	/**
	 * Saves a site and returns the Wiki Main Page.
	 * @return Wiki Main Page.
	 */
	public String save() {		
		if (hasConcurrentModifcation()) {
			return Constants.WIKI_OVERWRITE_PAGE;
		}
		
		siteVersionInfo.setId(null);
		if (siteVersionInfo.getName() == null) {
			siteVersionInfo.setName((String)getSessionBean(Constants.WIKI_NEW_SITE_NAME));
		}
		
		Date creationDate = new Date();
		
		siteVersionInfo.setCreationDate(creationDate);
		siteVersionInfo.setAuthorId(user.getId());
		siteVersionInfo.setDomainId(this.courseInfo.getId());
		siteVersionInfo.setDeleted(false);
		siteVersionInfo.setReadOnly(false);
		siteVersionInfo.setStable(false);
		
		getWikiService().saveWikiSite(this.siteVersionInfo);
		setSessionBean(Constants.WIKI_CURRENT_SITE_VERSION, this.siteVersionInfo);

		addMessage(i18n(Constants.WIKI_SITE_SAVE_SUCCEEDED));
		
		removeSessionBean(Constants.WIKI_NEW_SITE_BACKUP);
		removeSessionBean(Constants.WIKI_NEW_SITE_NAME);
		
		return Constants.WIKI_MAIN_PAGE;
	}
	
	/**
	 * Checks for concurrent modifications.
	 * @return <code>true</code> if modification was made while editing, otherwise <code>false</code>.
	 */
	private boolean hasConcurrentModifcation() {
		if (this.siteVersionInfo.getWikiSiteId() != null && this.siteVersionInfo.getWikiSiteId() != 0) {
			final Long newestId = this.wikiService.getNewestWikiSite(this.siteVersionInfo.getWikiSiteId()).getId();
			if (newestId > this.siteVersionInfo.getId()) {
				LOGGER.debug("Saving Site " + this.siteVersionInfo.getName() + ". Concurrent modifications found.");
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * Returns the Wiki Edit Page.
	 * @return Wiki Edit Page.
	 */
	public String create() {
		return Constants.WIKI_EDIT_PAGE;
	}
	
	/**
	 * Returns the Wiki Main Page.
	 * @return Wiki Main Page.
	 */
	public String cancelCreate() {
		this.siteVersionInfo = (WikiSiteContentInfo) getSessionBean(Constants.WIKI_NEW_SITE_BACKUP);
		removeSessionBean(Constants.WIKI_NEW_SITE_BACKUP);
		removeSessionBean(Constants.WIKI_NEW_SITE_NAME);
		
		return Constants.WIKI_MAIN_PAGE;
	}
	
	/**
	 * Returns the Wiki Overview Page.
	 * @return Wiki Overview Page.
	 */
	public String overview() {
		return Constants.WIKI_OVERVIEW;
	}
	
}