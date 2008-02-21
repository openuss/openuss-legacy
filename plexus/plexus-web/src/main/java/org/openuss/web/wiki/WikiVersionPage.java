package org.openuss.web.wiki;

import java.util.List;

import org.apache.log4j.Logger;
import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.Prerender;
import org.apache.shale.tiger.view.View;
import org.openuss.framework.web.jsf.model.AbstractPagedTable;
import org.openuss.framework.web.jsf.model.DataPage;
import org.openuss.web.Constants;
import org.openuss.wiki.WikiSiteInfo;


@Bean(name = "views$secured$wiki$wikiversion", scope = Scope.REQUEST)
@View
public class WikiVersionPage extends AbstractWikiPage{
	private static final Logger logger = Logger.getLogger(WikiVersionPage.class);
	
	WikiDataProvider data = new WikiDataProvider();
	
	private List<WikiSiteInfo> entries;
	
	@Override
	@Prerender
	public void prerender() throws Exception {
		super.prerender();
	}
	
	@SuppressWarnings("unchecked")
	private List<WikiSiteInfo> loadWikiSiteVersions() {
		if (entries == null) {
			entries = wikiService.findWikiSiteVersionsByWikiSite(this.siteVersionInfo.getWikiSiteId());
		}
		return entries;
	}
	
	private class WikiDataProvider extends AbstractPagedTable<WikiSiteInfo> {

		private static final long serialVersionUID = -1886479086904372812L;
		
		private DataPage<WikiSiteInfo> page;
		
		@Override
		public DataPage<WikiSiteInfo> getDataPage(int startRow,
				int pageSize) {
			if (page == null) {
				List<WikiSiteInfo> entries = loadWikiSiteVersions();
				//sort(entries);
				page = new DataPage<WikiSiteInfo>(entries.size(), 0, entries);
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