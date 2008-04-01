package org.openuss.web.wiki;

import java.util.List;

import org.apache.log4j.Logger;
import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.Prerender;
import org.apache.shale.tiger.view.View;
import org.openuss.framework.jsfcontrols.breadcrumbs.BreadCrumb;
import org.openuss.framework.web.jsf.model.AbstractPagedTable;
import org.openuss.framework.web.jsf.model.DataPage;
import org.openuss.web.Constants;
import org.openuss.web.PageLinks;
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
	
	@Override
	@Prerender
	public void prerender() throws Exception { // NOPMD by Administrator on 13.03.08 12:58
		super.prerender();
		
		if (this.siteVersionInfo.getName() != null) {
			String siteName = this.siteVersionInfo.getName().trim();
			
			if (!isValidWikiSiteName(siteName)) {
				addError(i18n("wiki_error_illegal_site_name"));
				return;
			}

			siteName = formatPageName(siteName);

			this.siteVersionInfo = this.wikiService.findWikiSiteContentByDomainObjectAndName(this.courseInfo.getId(), siteName);
		} else {
			addError("site version id not set!");
		}
		
		addBreadCrumbs();
	}
	
	/**
	 * Adds an additional BreadCrumb to the course crumbs.
	 */
	private void addBreadCrumbs() {
		breadcrumbs.loadCourseCrumbs(courseInfo);
		
		final BreadCrumb wikiBreadCrumb = new BreadCrumb();
		wikiBreadCrumb.setLink(PageLinks.WIKI_MAIN);
		wikiBreadCrumb.setName(i18n(Constants.WIKI_MAIN_HEADER));
		wikiBreadCrumb.setHint(i18n(Constants.WIKI_MAIN_HEADER));
		wikiBreadCrumb.addParameter("course",courseInfo.getId());
		wikiBreadCrumb.addParameter("page", Constants.WIKI_STARTSITE_NAME);
		breadcrumbs.addCrumb(wikiBreadCrumb);
		
		final BreadCrumb wikiVersionsBreadCrumb = new BreadCrumb();
		wikiVersionsBreadCrumb.setName(i18n("wiki_version_header"));
		wikiVersionsBreadCrumb.setHint(i18n("wiki_version_header"));
		breadcrumbs.addCrumb(wikiVersionsBreadCrumb);
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
		LOGGER.debug("editing WikiSiteVersion");
		WikiSiteInfo siteInfo = data.getRowData();
		if (siteInfo == null) {
			return Constants.FAILURE;
		}
		siteVersionInfo = wikiService.getWikiSiteContent(siteInfo.getId());
		setRequestBean(Constants.WIKI_CURRENT_SITE_VERSION, siteVersionInfo);
		if (siteVersionInfo == null) {
			addWarning(i18n("error_wikisite_not_found"));
			return Constants.FAILURE;
		} else {
			LOGGER.debug("selected siteVersionInfo " + siteVersionInfo.getName());
			return Constants.WIKI_EDIT_PAGE;
		}
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