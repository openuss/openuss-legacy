package org.openuss.web.wiki;

import java.util.Date;

import org.apache.log4j.Logger;
import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.Prerender;
import org.apache.shale.tiger.view.View;
import org.openuss.web.Constants;
import org.openuss.wiki.WikiSiteInfo;
import org.openuss.wiki.WikiSiteVersionInfo;


@Bean(name = "views$secured$wiki$wikiedit", scope = Scope.REQUEST)
@View
public class WikiEditPage extends AbstractWikiPage{
	private static final Logger logger = Logger.getLogger(WikiEditPage.class);
	
	@Override
	@Prerender
	public void prerender() throws Exception {
		super.prerender();
		
		String pageName = (String)getSessionBean(Constants.WIKI_NEW_SITE_NAME);
		if (pageName != null) {
			this.siteInfo.setId(null);
			this.siteInfo.setName(pageName);
			this.siteInfo.setCourseId(courseInfo.getId());
			
			this.siteVersionInfo.setId(null);
			this.siteVersionInfo.setWikiSiteId(null);
			this.siteVersionInfo.setText("");
			this.siteVersionInfo.setCreationDate(new Date());
			this.siteVersionInfo.setUserId(user.getId());
		} else {
			this.siteInfo = this.wikiService.findWikiSiteByCourseAndName(this.courseInfo.getId(), pageName);
			
			if (this.siteInfo != null) {
				if (this.siteVersionId != null) {
					this.siteVersionInfo = this.wikiService.getWikiSiteVersion(this.siteVersionId);
					addMessage(i18n("wiki_editing_old_version"));
				} else {
					this.siteVersionInfo = this.wikiService.getNewestWikiSiteVersion(this.siteInfo.getId());
				}
				
				if (this.siteVersionInfo != null) {
					this.siteVersionInfo.setNote("");
				}
			} else {
				this.siteInfo.setId(null);
				this.siteInfo.setName(this.siteName);
				this.siteInfo.setCourseId(courseInfo.getId());
				
				this.siteVersionInfo.setId(null);
				this.siteVersionInfo.setWikiSiteId(null);
				this.siteVersionInfo.setText("");
				this.siteVersionInfo.setCreationDate(new Date());
				this.siteVersionInfo.setUserId(user.getId());
			}
		}
		
		setSessionBean(Constants.WIKI_CURRENT_SITE, this.siteInfo);
		setSessionBean(Constants.WIKI_CURRENT_SITE_VERSION, this.siteVersionInfo);
	}
	
	public String save() {
		if (this.siteInfo.getId() == null) {
			this.wikiService.createWikiSite(this.siteInfo);
		}
		
		this.siteVersionInfo.setId(null);
		this.siteVersionInfo.setWikiSiteId(this.siteInfo.getId());
		this.siteVersionInfo.setCreationDate(new Date());
		this.siteVersionInfo.setUserId(user.getId());
		getWikiService().createWikiSiteVersion(this.siteVersionInfo);
		
		return Constants.WIKI_MAIN_PAGE;
	}
	
	public String create() {
		return Constants.WIKI_EDIT_PAGE;
	}
	public String cancelCreate() {
		this.siteName = Constants.WIKI_STARTSITE_NAME;
		this.siteVersionId = null;
		
		return Constants.WIKI_MAIN_PAGE;
	}
	
	public String overview() {
		return Constants.WIKI_OVERVIEW;
	}
	
}