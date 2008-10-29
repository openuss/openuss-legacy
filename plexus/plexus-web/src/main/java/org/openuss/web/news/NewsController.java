package org.openuss.web.news;

import java.util.List;

import javax.faces.component.UIData;

import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Property;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.View;
import org.openuss.framework.web.jsf.controller.BaseBean;
import org.openuss.news.NewsCategory;
import org.openuss.news.NewsItemInfo;
import org.openuss.news.NewsService;
import org.openuss.web.Constants;

/**
 * NewsController for news displaying 
 * @author Ingo Dueppe
 * @author Arne Sutor
 * @author Sebastian Roekens
 * 
 */
@Bean(name="news_controller", scope=Scope.REQUEST)
@View
public class NewsController extends BaseBean {

	@Property(value="#{newsService}")
	private NewsService newsService;
	
	@Property(value="#{"+Constants.NEWS_SELECTED_NEWSITEM+"}")
	private NewsItemInfo newsItem;
	
	@Property(value="#{"+Constants.NEWS_PUBLISHER+"}")
	private Object publisher;
	
	private UIData newsTable;
	
	private List<NewsItemInfo> news;

	/**
	 * @TODO the viewId of the page to be return should be fetched from an conversational context!
	 * @return /viewId of the welcome page
	 */
	public String back() {
		return Constants.OUTCOME_BACKWARD;
	}

	@SuppressWarnings("unchecked")
	public List<NewsItemInfo> getCurrentGlobalNews() {
		if (news == null) {
			news = newsService.getCurrentNewsItems(NewsCategory.GLOBAL, 20);
		}
		return news;
	}
	
	@SuppressWarnings("unchecked")
	public List<NewsItemInfo> getCurrentSidebarNews() {
		if (news == null) {
			news = newsService.getCurrentNewsItems(NewsCategory.GLOBAL, 3);
		}
		return news;
	}

	public void fetchNewsItem() {
		if (newsItem != null) {
			newsItem = newsService.getNewsItem(newsItem);
		}
	}
	
	public String details() {
		newsItem = (NewsItemInfo) newsTable.getRowData();
		newsItem = newsService.getNewsItem(newsItem);
		setBean(Constants.NEWS_SELECTED_NEWSITEM, newsItem);
		return Constants.NEWS_NEWSDETAIL_PAGE;
	}

	public String save() {
		newsService.saveNewsItem(newsItem);
		return "save";
	}

	public NewsItemInfo getNewsItem() {
		return newsItem;
	}

	public void setNewsItem(NewsItemInfo newsItem) {
		this.newsItem = newsItem;
	}

	public NewsService getNewsService() {
		return newsService;
	}

	public void setNewsService(NewsService newsService) {
		this.newsService = newsService;
	}

	public UIData getNewsTable() {
		return newsTable;
	}

	public void setNewsTable(UIData newsTable) {
		this.newsTable = newsTable;
	}

	public Object getPublisher() {
		return publisher;
	}

	public void setPublisher(Object publisher) {
		this.publisher = publisher;
	}

}
