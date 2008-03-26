package org.openuss.web.wiki;

import java.util.List;

import org.apache.log4j.Logger;
import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Scope;
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
	
	private static final Logger LOGGER = Logger.getLogger(WikiVersionPage.class);
	
	private final WikiDataProvider data = new WikiDataProvider();
	
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
		LOGGER.debug("editing WikiSiteVersion");
		WikiSiteInfo entry = data.getRowData();
		siteVersionInfo = wikiService.getWikiSiteContent(entry.getId());
		// FIXME Do not use session bean for navigation
		setSessionBean(Constants.WIKI_CURRENT_SITE_VERSION, siteVersionInfo);
		return Constants.WIKI_EDIT_PAGE;
	}
	
	/**
	 * Deletes a specific Version and returns Wiki Version Page.
	 * @return Wiki Version Page.
	 */
	public String deleteWikiVersion() {
		LOGGER.debug("delete wikiSiteVersion");
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
			super();
			
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
	
		private List<WikiSiteInfo> entries;
		
		/**
		 * Loads WikiSiteVersions.
		 * @return List of WikiSiteVersions.
		 */
		@SuppressWarnings("unchecked")
		private List<WikiSiteInfo> loadWikiSiteVersions() {
			if (entries == null) {
				entries = wikiService.findWikiSiteVersionsByWikiSite(siteVersionInfo.getWikiSiteId());
			}
			return entries;
		}
	
	}
}