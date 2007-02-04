package org.openuss.web.news;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.faces.model.SelectItem;

import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Property;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.View;
import org.openuss.framework.web.jsf.controller.BaseBean;
import org.openuss.news.NewsCategory;
import org.openuss.news.NewsService;

/**
 * NewsCategory
 * @author Ingo Dueppe
 */
@Bean(name="newsCategorySelectItems", scope=Scope.REQUEST)
@View
public class NewsCategorySelectItems extends BaseBean {
	
	private static final long serialVersionUID = 8684092963234860516L;

	private List<SelectItem> items;

	@Property(value="#{newsService}")
	private NewsService newsService;
	
	public List<SelectItem> getItems() {
		if (items == null) {
			items = new ArrayList<SelectItem>();
			Collection<NewsCategory> entities = newsService.getCategories();
			for (NewsCategory category : entities) {
				SelectItem item = new SelectItem(category, i18n(category.getName()));
				items.add(item);
			}
		}
		return items;		
	}

	public void setItems(List<SelectItem> categories) {
		this.items = categories;
	}

	public NewsService getNewsService() {
		return newsService;
	}

	public void setNewsService(NewsService newsService) {
		this.newsService = newsService;
	}
}
