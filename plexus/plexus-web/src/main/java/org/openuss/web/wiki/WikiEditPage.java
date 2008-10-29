package org.openuss.web.wiki;

import java.util.Date;

import org.apache.log4j.Logger;
import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.Prerender;
import org.apache.shale.tiger.view.View;
import org.openuss.web.Constants;

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
		if (this.siteVersionInfo != null && this.siteVersionInfo.getId() != null) {
			siteVersionInfo = wikiService.getWikiSiteContent(this.siteVersionInfo.getId());
		} else {
			siteVersionInfo.setDomainId(this.courseInfo.getId());
		}
		siteVersionInfo.setNote(null);
	}
	
	/**
	 * Saves a site and returns the Wiki Main Page.
	 * @return Wiki Main Page.
	 */
	public String save() {		
		// TODO This should be handled by business methods. Throw ConcurrentModificationException
		if (hasConcurrentModifcation()) {
			return Constants.WIKI_OVERWRITE_PAGE;
		}
		
		siteVersionInfo.setId(null);
		
		Date creationDate = new Date();
		
		siteVersionInfo.setCreationDate(creationDate);
		siteVersionInfo.setAuthorId(user.getId());
		siteVersionInfo.setDomainId(courseInfo.getId());
		siteVersionInfo.setDeleted(false);
		siteVersionInfo.setReadOnly(false);
		siteVersionInfo.setStable(false);
		
		getWikiService().saveWikiSite(siteVersionInfo);
		
		addMessage(i18n(Constants.WIKI_SITE_SAVE_SUCCEEDED));
		
		return Constants.WIKI_MAIN_PAGE;
	}
	
	/**
	 * Checks for concurrent modifications.
	 * @return <code>true</code> if modification was made while editing, otherwise <code>false</code>.
	 */
	private boolean hasConcurrentModifcation() {
		if (siteVersionInfo.getWikiSiteId() != null && siteVersionInfo.getWikiSiteId() != 0) {
			final Long newestId = wikiService.getNewestWikiSite(siteVersionInfo.getWikiSiteId()).getId();
			if (newestId > siteVersionInfo.getId()) {
				LOGGER.debug("Saving Site " + siteVersionInfo.getName() + ". Concurrent modifications found.");
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
		this.siteVersionInfo = null;
		setBean(Constants.WIKI_CURRENT_SITE_VERSION, null);
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