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

/**
 * Backing Bean for wikiversion.xhtml.
 * @author Projektseminar WS 07/08, Team Collaboration
 *
 */
@Bean(name = "views$secured$wiki$wikiversion", scope = Scope.REQUEST)
@View
public class WikiVersionPage extends AbstractWikiPage{
	
	private static final Logger logger = Logger.getLogger(WikiVersionPage.class);
	
	private WikiDataProvider data = new WikiDataProvider();
	
	private List<WikiSiteInfo> entries;
	
	@Override
	@Prerender
	public void prerender() throws Exception {
		super.prerender();
	}
	
	/**
	 * Returns Wiki Overview Page.
	 * @return Wiki Overview Page.
	 */
	public String overview() {
		return Constants.WIKI_OVERVIEW;
	}
	
	/**
	 * Retrieves a specific Version and returns Wiki Edit Page.
	 * @return Wiki Edit Page.
	 */
	public String editWikiVersion() {
		logger.debug("editing WikiSiteVersion");
		WikiSiteInfo entry = data.getRowData();
		siteVersionInfo = wikiService.getWikiSiteContent(entry.getId());
		
		setSessionBean(Constants.WIKI_CURRENT_SITE_VERSION, siteVersionInfo);
		return Constants.WIKI_EDIT_PAGE;
	}
	
	/**
	 * Deletes a specific Version and returns Wiki Version Page.
	 * @return Wiki Version Page.
	 */
	public String deleteWikiVersion() {
		logger.debug("delete wikiSiteVersion");
		WikiSiteInfo entry = data.getRowData();
		wikiService.deleteWikiSiteVersion(entry.getId());
		
		addMessage(i18n("wiki_message_version_delete_succeeded"));
		return Constants.WIKI_VERSION_PAGE;
	}
	
	public WikiDataProvider getVersionData() {
		return data;
	}
	
	/**
	 * DataProvider for WikiVersion table.
	 * @author Projektseminar WS 07/08, Team Collaboration
	 *
	 */
	private class WikiDataProvider extends AbstractPagedTable<WikiSiteInfo> {
		private static final long serialVersionUID = -1886479086904372812L;
		
		private DataPage<WikiSiteInfo> page;
		
		public WikiDataProvider() {
			setSortColumn("creationDate");
			setAscending(false);
		}
		
		@Override
		public DataPage<WikiSiteInfo> getDataPage(int startRow,	int pageSize) {
			if (page == null) {
				List<WikiSiteInfo> entries = loadWikiSiteVersions();
				page = new DataPage<WikiSiteInfo>(entries.size(), 0, entries);
				sort(entries);
			}
			return page;
		}
	}
	
	@SuppressWarnings("unchecked")
	private List<WikiSiteInfo> loadWikiSiteVersions() {
		if (entries == null) {
			entries = wikiService.findWikiSiteVersionsByWikiSite(this.siteVersionInfo.getWikiSiteId());
		}
		return entries;
	}
	
}