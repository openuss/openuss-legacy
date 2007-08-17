package org.openuss.web.lecture;

import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Property;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.Prerender;
import org.apache.shale.tiger.view.View;
import org.openuss.framework.jsfcontrols.breadcrumbs.BreadCrumb;
import org.openuss.lecture.LectureException;
import org.openuss.lecture.Period;
import org.openuss.news.NewsService;
import org.openuss.web.Constants;

/**
 *
 * @author Malte Stockmann
 * @author Kai Stettner
 */
@View
@Bean(name = "views$public$university$university", scope = Scope.REQUEST)
public class UniversityPage extends AbstractUniversityPage {
	
	private static final long serialVersionUID = -1982354759705358593L;

	@Property(value = "#{newsService}")
	private NewsService newsService;

	/**
	 * Refreshing university entity
	 * 
	 * @throws Exception
	 */
	@Prerender
	public void prerender() throws LectureException {
		super.prerender();
		addPageCrumb();
	}
	
	private void addPageCrumb() {
		BreadCrumb crumb = new BreadCrumb();
		crumb.setLink("");
		crumb.setName(i18n("university_command_university"));
		crumb.setHint(i18n("university_command_university"));
		crumbs.add(crumb);
		setSessionBean(Constants.BREADCRUMBS, crumbs);
	}	

	public NewsService getNewsService() {
		return newsService;
	}

	public void setNewsService(NewsService newsService) {
		this.newsService = newsService;
	}


}
