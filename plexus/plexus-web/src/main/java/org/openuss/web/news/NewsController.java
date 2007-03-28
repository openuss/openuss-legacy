package org.openuss.web.news;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.faces.component.UIData;
import javax.faces.model.SelectItem;

import org.apache.log4j.Logger;
import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Property;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.Init;
import org.apache.shale.tiger.view.View;
import org.openuss.framework.web.jsf.controller.BaseBean;
import org.openuss.lecture.Faculty;
import org.openuss.news.NewsCategory;
import org.openuss.news.NewsItem;
import org.openuss.news.NewsPublisher;
import org.openuss.news.NewsService;
import org.openuss.web.Constants;

/**
 * NewsController for news displaying 
 * @author Ingo Dueppe
 * @author Arne Sutor
 */
@Bean(name="newsController", scope=Scope.REQUEST)
@View
public class NewsController extends BaseBean {

	private static final long serialVersionUID = -4246786726261901182L;

	private static final Logger logger = Logger.getLogger(NewsController.class);
	
	@Property(value="#{newsService}")
	transient private NewsService newsService;
	
	@Property(value="#{newsItem}")
	private NewsItem newsItem;
	
	@Property(value="#{faculty}")
	private Faculty faculty;
	
	private UIData newsTable;
	
	private ArrayList<SelectItem> categories;
	
	private String outcome = "home";
	
	private List<SelectItem> newsCategoriesSelectItems;

	@Init
	public void init() {
		logger.debug("newsController init <-----------------------------------------------------------");
		
		//TODO call business method here
		
		// NewsItem createEmptyNewsItem(faculty);
		newsItem = NewsItem.Factory.newInstance();
		NewsPublisher publisher = NewsPublisher.Factory.newInstance();
		if (faculty != null)
			publisher.setDisplayName(faculty.getName());
		else
			publisher.setDisplayName("unknown");
		newsItem.setPublishDate(new Timestamp(System.currentTimeMillis()));
		newsItem.setPublisher(publisher);
		
		setSessionBean(Constants.NEWSITEM, newsItem);
	}
	
	public String cancel() {
		return Constants.FACULTY_NEWS_PAGE;
	}
	
	
	/**
	 * @TODO the viewId of the page to be return should be fetched from an conversational context!
	 * @return /viewId of the welcome page
	 */
	public String back() {
		return outcome;
	}

	@SuppressWarnings("unchecked")
	public Collection getNewsItems() {
		Collection<NewsItem> returnCollection = newsService.getNewsItems(new Timestamp(System.currentTimeMillis()), 5);
		return returnCollection;
	}
	
	@SuppressWarnings("unchecked")
	public List<SelectItem> getCategories() {
		if (categories == null) {
			categories = new ArrayList<SelectItem>();
		}
		Collection<NewsCategory> entities = newsService.getCategories();
		Iterator<NewsCategory> iter = entities.iterator();
		while (iter.hasNext()) {
			NewsCategory element = (NewsCategory) iter.next();
			SelectItem item = new SelectItem(element.getId().toString(), i18n(element.getName()));
			categories.add(item);
		}
		return categories;
	}	
	
	public void fetchNewsItem() {
		if (newsItem != null) {
			newsItem = newsService.getNewsItem(newsItem.getId());
		}
	}
	
	public String details() {
		newsItem = (NewsItem) newsTable.getRowData();
		newsItem = newsService.getNewsItem(newsItem.getId());
		setSessionBean("newsItem", newsItem);
		return "detailsNewsItem";
	}

	public String save() {
		newsService.saveNewsItem(newsItem);
		return "save";
	}

	
	public void deleteAttachment() {
		newsItem.setAttachmentId(null);
	}
	
	public NewsItem getNewsItem() {
		return newsItem;
	}

	public void setNewsItem(NewsItem newsItem) {
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
		
		setRequestBean("newsItem", null);
		
	}

	/**
	 * @return the outcome
	 */
	public String getOutcome() {
		return outcome;
	}

	/**
	 * @param outcome the outcome to set
	 */
	public void setOutcome(String outcome) {
		this.outcome = outcome;
	}


	public Faculty getFaculty() {
		return faculty;
	}


	public void setFaculty(Faculty faculty) {
		this.faculty = faculty;
	}


	public void setCategories(ArrayList<SelectItem> categories) {
		this.categories = categories;
	}

	public List<SelectItem> getNewsCategoriesSelectItems() {
		return newsCategoriesSelectItems;
	}

	public void setNewsCategoriesSelectItems(List<SelectItem> newsCategoriesSelectItems) {
		this.newsCategoriesSelectItems = newsCategoriesSelectItems;
	}

}
