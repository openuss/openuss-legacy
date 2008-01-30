package org.openuss.web.wiki;

import java.util.List;

import org.apache.log4j.Logger;
import org.apache.shale.tiger.managed.Property;
import org.apache.shale.tiger.view.Prerender;
import org.openuss.framework.web.jsf.model.AbstractPagedTable;
import org.openuss.framework.web.jsf.model.DataPage;
import org.openuss.web.course.AbstractCoursePage;
import org.openuss.wiki.WikiService;
import org.openuss.wiki.WikiSiteInfo;

public class WikiOverviewPage extends AbstractCoursePage {
	
	private static final Logger LOGGER = Logger.getLogger(WikiEditLinksPage.class);
	
	private WikiOverviewDataProvider data = new WikiOverviewDataProvider();
	
	@Property(value = "#{wikiService}")
	protected WikiService wikiService;
	
	@Override
	@Prerender
	public void prerender() throws Exception {
		super.prerender();
	}

	public WikiService getWikiService() {
		return wikiService;
	}

	public void setWikiService(WikiService wikiService) {
		this.wikiService = wikiService;
	}
	
	public WikiOverviewDataProvider getData() {
		return data;
	}

	public void setData(WikiOverviewDataProvider data) {
		this.data = data;
	} 
	
	private class WikiOverviewDataProvider extends AbstractPagedTable<WikiSiteInfo> {
		private DataPage<WikiSiteInfo> page; 
		
		@SuppressWarnings("unchecked")
		@Override
		public DataPage<WikiSiteInfo> getDataPage(int startRow, int pageSize) {		
			List<WikiSiteInfo> wikiSiteInfoList = wikiService.findWikiSitesByCourse(courseInfo.getId());		
			page = new DataPage<WikiSiteInfo>(wikiSiteInfoList.size(), 0, wikiSiteInfoList);
			sort(wikiSiteInfoList);
			return page;
		}
	}
	

}
