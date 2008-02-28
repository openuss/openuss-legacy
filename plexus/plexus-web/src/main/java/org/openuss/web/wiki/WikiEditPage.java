package org.openuss.web.wiki;

import java.util.Date;

import org.apache.log4j.Logger;
import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.Prerender;
import org.apache.shale.tiger.view.View;
import org.openuss.web.Constants;
import org.openuss.wiki.WikiSiteInfo;
import org.openuss.wiki.WikiSiteContentInfo;


@Bean(name = "views$secured$wiki$wikiedit", scope = Scope.REQUEST)
@View
public class WikiEditPage extends AbstractWikiPage{
	private static final Logger logger = Logger.getLogger(WikiEditPage.class);
	
	@Override
	@Prerender
	public void prerender() throws Exception {
		super.prerender();
		
		siteVersionInfo.setNote(null);
	}
	
	public String save() {
		
		// check for concurrent modification
		if (this.siteVersionInfo.getWikiSiteId() != null && this.siteVersionInfo.getWikiSiteId() != 0) {
			Long newestId = this.wikiService.getNewestWikiSite(this.siteVersionInfo.getWikiSiteId()).getId();
			if (newestId > this.siteVersionInfo.getId()) {
				return Constants.WIKI_OVERWRITE_PAGE;
			}
		}
		
		this.siteVersionInfo.setId(null);
		if (this.siteVersionInfo.getName() == null) {
			this.siteVersionInfo.setName((String)getSessionBean(Constants.WIKI_NEW_SITE_NAME));
		}

		this.siteVersionInfo.setCreationDate(new Date());
		this.siteVersionInfo.setAuthorId(user.getId());
		this.siteVersionInfo.setDomainId(this.courseInfo.getId());
		this.siteVersionInfo.setDeleted(false);
		this.siteVersionInfo.setReadOnly(false);
		this.siteVersionInfo.setStable(false);
		
		getWikiService().saveWikiSite(this.siteVersionInfo);

		addMessage(i18n("wiki_site_save_succeeded"));
		
		removeSessionBean(Constants.WIKI_NEW_SITE_BACKUP);
		removeSessionBean(Constants.WIKI_NEW_SITE_NAME);
		
		return Constants.WIKI_MAIN_PAGE;

	}
	
	public String create() {
		return Constants.WIKI_EDIT_PAGE;
	}
	public String cancelCreate() {
		this.siteVersionInfo = (WikiSiteContentInfo) getSessionBean(Constants.WIKI_NEW_SITE_BACKUP);
		removeSessionBean(Constants.WIKI_NEW_SITE_BACKUP);
		removeSessionBean(Constants.WIKI_NEW_SITE_NAME);
		
		return Constants.WIKI_MAIN_PAGE;
	}
	
	public String overview() {
		return Constants.WIKI_OVERVIEW;
	}
	
}