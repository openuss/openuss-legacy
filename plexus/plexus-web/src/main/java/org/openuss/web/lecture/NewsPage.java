package org.openuss.web.lecture;

import java.util.List;

import org.apache.log4j.Logger;
import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Property;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.Prerender;
import org.apache.shale.tiger.view.View;
import org.openuss.framework.jsfcontrols.breadcrumbs.BreadCrumb;
import org.openuss.framework.web.jsf.model.AbstractPagedTable;
import org.openuss.framework.web.jsf.model.DataPage;
import org.openuss.lecture.LectureException;
import org.openuss.news.NewsItemInfo;
import org.openuss.news.NewsService;
import org.openuss.web.Constants;

/**
 * News Page Controller
 * @author Ingo Dueppe
 * @author Kai Stettner
 * @author Sebastian Roekens
 * 
 */
@Bean(name = "views$secured$lecture$news", scope = Scope.REQUEST)
@View
public class NewsPage extends AbstractLecturePage {

	private static final Logger logger = Logger.getLogger(NewsPage.class);

	@Property(value = "#{newsService}")
	private NewsService newsService;

	private NewsDataProvider data = new NewsDataProvider();
	
	@Prerender
	public void prerender() throws LectureException {
		super.prerender();
		if (isRedirected()){
			return;
		}
		if (!instituteInfo.isEnabled()) {
			addMessage(i18n("institute_not_activated"));
		}
		addPageCrumb();
	}	
	
	private void addPageCrumb() {
		BreadCrumb crumb = new BreadCrumb();
		crumb.setLink("");
		crumb.setName(i18n("institute_command_news"));
		crumb.setHint(i18n("institute_command_news"));
		
		breadcrumbs.loadInstituteCrumbs(instituteInfo);
		breadcrumbs.addCrumb(crumb);
	}
	
	private class NewsDataProvider extends AbstractPagedTable<NewsItemInfo> {

		private static final long serialVersionUID = -8735576547984958020L;
		
		private DataPage<NewsItemInfo> page; 
		
		@SuppressWarnings("unchecked")
		@Override 
		public DataPage<NewsItemInfo> getDataPage(int startRow, int pageSize) {
			if (page == null) {
				List<NewsItemInfo> newsInfo = newsService.getNewsItems(instituteInfo);
				page = new DataPage<NewsItemInfo>(newsInfo.size(),0,newsInfo);
				sort(newsInfo);
			}
			return page;
		}
	}
		
	/**
	 * Starting to add a new newsitem. 
	 * @return outcome
	 */
	public String addNewsItem() {
		NewsItemInfo newsItemInfo = new NewsItemInfo();
		// define initial values
		
		setBean(Constants.NEWS_SELECTED_NEWSITEM, newsItemInfo);
		return Constants.INSTITUTE_NEWS_EDIT_PAGE;
	}
	
	/**
	 * Editing a news item
	 * @return outcome
	 */
	public String editNewsItem() {
		logger.debug("edit news item");
		NewsItemInfo newsItemInfo = data.getRowData();
		setBean(Constants.NEWS_SELECTED_NEWSITEM, newsItemInfo);
		return Constants.INSTITUTE_NEWS_EDIT_PAGE;
	}
	
	/**
	 * Remove news item
	 * @return outcome
	 */
	public String removeNewsItem() {
		logger.debug("edit removing newsitem");
		NewsItemInfo newsItemInfo = data.getRowData();
		newsService.deleteNewsItem(newsItemInfo);
		addMessage(i18n("message_newsitem_removed_succeed"));
		return Constants.INSTITUTE_NEWS_PAGE;
	}

	@SuppressWarnings("unchecked")
	public List<NewsItemInfo> getNewsItems() {
		logger.debug("getting newsitems for institute " + instituteInfo);
		return newsService.getNewsItems(instituteInfo);
	}

	/* --------------- properties -------------- */

	public NewsService getNewsService() {
		return newsService;
	}

	public void setNewsService(NewsService newsService) {
		this.newsService = newsService;
	}

	public NewsDataProvider getData() {
		return data;
	}

	public void setData(NewsDataProvider data) {
		this.data = data;
	}
}
