package org.openuss.web.wiki;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.Prerender;
import org.apache.shale.tiger.view.View;
import org.openuss.documents.FileInfo;
import org.openuss.documents.FolderEntryInfo;
import org.openuss.framework.web.jsf.model.AbstractPagedTable;
import org.openuss.framework.web.jsf.model.DataPage;
import org.openuss.web.Constants;
import org.openuss.wiki.WikiSiteInfo;
import org.openuss.wiki.WikiSiteVersionInfo;


@Bean(name = "views$secured$wiki$wikiversion", scope = Scope.REQUEST)
@View
public class WikiVersionPage extends AbstractWikiPage{
	private static final Logger logger = Logger.getLogger(WikiVersionPage.class);
	
	WikiDataProvider data = new WikiDataProvider();
	
	private List<WikiSiteVersionInfo> entries;
	
	@Override
	@Prerender
	public void prerender() throws Exception {
		super.prerender();
		
		if (this.siteVersionInfo == null || this.siteVersionInfo.getId() == null) {
			this.siteInfo = this.wikiService.findWikiSiteByCourseAndName(this.courseInfo.getId(), Constants.WIKI_STARTSITE_NAME);
			if (this.siteInfo == null) {
				System.out.println("WikiSite '" + Constants.WIKI_STARTSITE_NAME + "' for course not found. Creating WikiSite for courseId: " + this.courseInfo.getId());
				logger.debug("WikiSite '" + Constants.WIKI_STARTSITE_NAME + "' for course not found. Creating WikiSite for courseId: " + this.courseInfo.getId());
				this.siteInfo = new WikiSiteInfo();
				this.siteInfo.setCourseId(courseInfo.getId());
				this.siteInfo.setName(Constants.WIKI_STARTSITE_NAME);
				this.wikiService.createWikiSite(this.siteInfo);
			}
			
			this.siteVersionInfo = this.wikiService.getNewestWikiSiteVersion(this.siteInfo.getId());
			if (this.siteVersionInfo == null) {
				System.out.println("No version found for wiki with id:" + this.siteInfo.getId() + " ... creating one.");
				logger.debug("No version found for wiki with id:" + this.siteInfo.getId() + " ... creating one.");
				this.siteVersionInfo = new WikiSiteVersionInfo();
				this.siteVersionInfo.setWikiSiteId(this.siteInfo.getId());
				this.siteVersionInfo.setCreationDate(new Date());
				this.siteVersionInfo.setUserId(user.getId());
			}
			
			setSessionBean(Constants.WIKI_CURRENT_SITE, this.siteInfo);
			setSessionBean(Constants.WIKI_CURRENT_SITE_VERSION, this.siteVersionInfo);
		}
		
		this.siteVersionInfo.setNote("");
	
	}
	
	private List<WikiSiteVersionInfo> loadWikiSiteVersions() {
		if (entries == null) {
			entries = wikiService.findWikiSiteVersionsByWikiSite(this.siteInfo.getId());
		}
		return entries;
	}
	
	private class WikiDataProvider extends AbstractPagedTable<WikiSiteVersionInfo> {

		private static final long serialVersionUID = -1886479086904372812L;
		
		private DataPage<WikiSiteVersionInfo> page;
		
		@Override
		public DataPage<WikiSiteVersionInfo> getDataPage(int startRow,
				int pageSize) {
			if (page == null) {
				List<WikiSiteVersionInfo> entries = loadWikiSiteVersions();
				//sort(entries);
				page = new DataPage<WikiSiteVersionInfo>(entries.size(), 0, entries);
			}
			return page;
		}
		
	}
	
	
	public WikiDataProvider getVersionData() {
		return data;
	}
	
	public String overview() {
		return Constants.WIKI_OVERVIEW;
	}
	
	public String showWikiVersion(){
		return null;
	}
	
	public String editWikiVersion() {
		return null;
	}
	
}