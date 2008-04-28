package org.openuss.web.groups.components;

import java.util.Date;
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
import org.openuss.news.NewsCategory;
import org.openuss.news.NewsItem;
import org.openuss.news.NewsItemInfo;
import org.openuss.news.NewsService;
import org.openuss.web.Constants;

/**
 * Group News Page Controller
 * @author Lutz D. Kramer
 * @author Thomas Jansing
 */
@Bean(name = "views$secured$groups$components$groupnews", scope = Scope.REQUEST)
@View
public class GroupNewsPage extends AbstractGroupPage {

	private static final Logger logger = Logger.getLogger(GroupNewsPage.class);

	private static final long serialVersionUID = 792199034646593736L;

	@Property(value = "#{newsService}")
	private NewsService newsService;

	private NewsDataProvider data = new NewsDataProvider();

	/* ----- private classes ----- */
	
	private class NewsDataProvider extends AbstractPagedTable<NewsItemInfo> {

		private static final long serialVersionUID = 8155867089090710357L;
		
		private DataPage<NewsItemInfo> page; 
		
		@Override 
		public DataPage<NewsItemInfo> getDataPage(int startRow, int pageSize) {
			if (page == null) {
				List<NewsItemInfo> news = newsService.getNewsItems(groupInfo);
				page = new DataPage<NewsItemInfo>(news.size(),0,news);
				sort(news);
			}
			return page;
		}
	}
	
	/* ----- business logic ----- */
	
	@Prerender
	@Override
	public void prerender() throws Exception { // NOPMD by devopenuss on 11.03.08 14:27
		super.prerender();
		BreadCrumb crumb = new BreadCrumb();
		crumb.setName(i18n("group_command_news"));
		crumb.setHint(i18n("group_command_news"));
		breadcrumbs.addCrumb(crumb);
	}
	
	public List<NewsItemInfo> getCurrentNews() {
		return newsService.getCurrentNewsItems(groupInfo,null);
	}
	
	/**
	 * Starting to add a new news item. 
	 * @return outcome
	 */
	public String addNewsItem() {
		NewsItemInfo newsItem = new NewsItemInfo();
		// define initial values
		newsItem.setCategory(NewsCategory.GLOBAL);
		newsItem.setPublishDate(new Date());
		newsItem.setExpireDate(new Date(System.currentTimeMillis()+1000L*60L*60L*24L*28L));
		
		newsItem.setPublisherIdentifier(groupInfo.getId());
		newsItem.setPublisherName(groupInfo.getCreatorName());
		
		setSessionBean(Constants.NEWS_SELECTED_NEWSITEM, newsItem);
		return Constants.GROUP_NEWS_EDIT_PAGE;
	}
	
	/**
	 * Editing a news item
	 * @return outcome
	 */
	public String editNewsItem() {
		logger.debug("edit news item");
		NewsItemInfo newsItem = data.getRowData();
		setSessionBean(Constants.NEWS_SELECTED_NEWSITEM, newsItem);
		return Constants.GROUP_NEWS_EDIT_PAGE;
	}
	
	/**
	 * Remove news item
	 * @return outcome
	 */
	public String removeNewsItem() {
		logger.debug("edit removing newsitem");
		NewsItemInfo newsItem = data.getRowData();
		newsService.deleteNewsItem(newsItem);
		addMessage(i18n("message_newsitem_removed_succeed"));
		return Constants.GROUP_NEWS_PAGE;
	}

	public List<NewsItem> getNewsItems() {
		logger.debug("getting newsitems for group " + groupInfo);
		return newsService.getNewsItems(groupInfo);
	}

	/* ----- getter and setter ----- */

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
