package org.openuss.web.news;

import org.apache.log4j.Logger;

import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Property;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.Prerender;
import org.apache.shale.tiger.view.View;
import org.openuss.news.NewsItemInfo;
import org.openuss.news.NewsService;
import org.openuss.web.BasePage;
import org.openuss.web.Constants;


/**
 * News Details Page
 * @author Ingo Dueppe
 */
@Bean(name = "views$public$news$newsDetail", scope = Scope.REQUEST)
@View
public class NewsDetailsPage extends BasePage {
	private static final Logger logger = Logger.getLogger(NewsDetailsPage.class);
	
	@Property(value="#{"+Constants.NEWS_SELECTED_NEWSITEM+"}")
	private NewsItemInfo newsItem;
	
	@Property(value="#{newsService}")
	private NewsService newsService;
	
	@Prerender
	public void prerender() {
		if (newsItem != null && newsItem.getId() != null) {
			logger.debug("refreshing newsitem "+newsItem.getId());
			newsItem = newsService.getNewsItem(newsItem);
			setSessionBean(Constants.NEWS_SELECTED_NEWSITEM, newsItem);
		} 
		if (newsItem == null || newsItem.getId() == null){
			addError(i18n("news_message_newsitem_not_found"));
		}
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
}
