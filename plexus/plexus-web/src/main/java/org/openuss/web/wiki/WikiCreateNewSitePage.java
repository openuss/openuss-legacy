package org.openuss.web.wiki;

import org.apache.log4j.Logger;
import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.Prerender;
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
	
	@Override
	@Prerender
	public void prerender() throws Exception {
		super.prerender();
	}
	
	/**
	 * Opens the Create or Edit Site Page.
	 * @return Wiki Edit Page.
	 */
	public String createNewSite() {
		this.siteVersionInfo = this.wikiService.findWikiSiteContentByDomainObjectAndName(this.courseInfo.getId(), createNewSiteName);
		
		if (this.siteVersionInfo == null) {
			LOGGER.debug("Site '" + createNewSiteName + "' does not exist. Opening Create New Site Page.");			
			this.siteName = createNewSiteName;
		} else {
			LOGGER.debug("Site '" + createNewSiteName + "' does already exist. Opening Edit Site Page.");
			this.addMessage("!!HARDCODE!! Site exists.");
		}
		
		setSessionBean(Constants.WIKI_CURRENT_SITE_VERSION, this.siteVersionInfo);
		setSessionBean(Constants.WIKI_NEW_SITE_NAME, createNewSiteName);
		
		return Constants.WIKI_EDIT_PAGE;
	}

	public String getCreateNewSiteName() {
		return createNewSiteName;
	}

	public void setCreateNewSiteName(String createNewSiteName) {
		this.createNewSiteName = createNewSiteName;
	}
	
}