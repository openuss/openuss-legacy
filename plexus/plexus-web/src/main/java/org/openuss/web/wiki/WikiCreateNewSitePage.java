package org.openuss.web.wiki;

import org.apache.log4j.Logger;
import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.View;
import org.openuss.web.Constants;

/**
 * Backing Bean for wikicreatesite.xhtml.
 * @author Projektseminar WS 07/08, Team Collaboration
 *
 */
@Bean(name = "views$secured$wiki$wikicreatenewsite", scope = Scope.REQUEST)
@View
public class WikiCreateNewSitePage extends AbstractWikiPage {
	
	private static final Logger LOGGER = Logger.getLogger(WikiEditPage.class);
	
	private String createNewSiteName;
	
	/**
	 * Opens the Create or View Site Page.
	 * @return Wiki Edit Page or Wiki Main Page.
	 */
	public String createNewSite() {
		this.siteVersionInfo = this.wikiService.findWikiSiteContentByDomainObjectAndName(this.courseInfo.getId(), createNewSiteName);
		
		if (this.siteVersionInfo == null) {
			return handleSiteDoesNotExist();
		} else {
			return handleSiteAlreadyExists();
		}
	}
	
	/**
	 * Returns Wiki Edit Page.
	 * @return Wiki Edit Page.
	 */
	private String handleSiteDoesNotExist() {
		LOGGER.debug("Site '" + createNewSiteName + "' does not exist. Opening Create New Site Page.");			
		
		this.siteName = createNewSiteName;
		
		setSessionBean(Constants.WIKI_CURRENT_SITE_VERSION, this.siteVersionInfo);
		setSessionBean(Constants.WIKI_NEW_SITE_NAME, createNewSiteName);
		
		return Constants.WIKI_EDIT_PAGE;
	}
	
	/**
	 * Returns Wiki Main Page.
	 * @return Wiki Main Page.
	 */
	private String handleSiteAlreadyExists() {
		LOGGER.debug("Site '" + createNewSiteName + "' does already exist. Opening Edit Site Page.");
		
		this.addMessage(i18n("wiki_new_site_already_exists_message", createNewSiteName));
		
		setSessionBean(Constants.WIKI_CURRENT_SITE_VERSION, this.siteVersionInfo);
		setSessionBean(Constants.WIKI_NEW_SITE_NAME, createNewSiteName);
		
		return Constants.WIKI_MAIN_PAGE;
	}

	public String getCreateNewSiteName() {
		return createNewSiteName;
	}

	public void setCreateNewSiteName(String createNewSiteName) {
		this.createNewSiteName = createNewSiteName;
	}
	
}