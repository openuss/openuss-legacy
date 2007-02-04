package org.openuss.web.lecture;

import java.util.Collection;

import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Property;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.View;
import org.openuss.lecture.LectureException;
import org.openuss.news.NewsService;

/**
 * @author Ingo Dueppe
 */
@View
@Bean(name = "views$secured$lecture$faculty", scope = Scope.REQUEST)
public class FacultyPage extends AbstractLecturePage {
	private static final long serialVersionUID = -1982354759705300093L;

	@Property(value = "#{newsService}")
	private NewsService newsService;
	
	/**
	 * Current news items of faculty
	 * @return
	 * @throws LectureException
	 */
	public Collection getCurrentNewsItems() {
		return newsService.getCurrentNewsItems(faculty, 10);
	}
	
	public NewsService getNewsService() {
		return newsService;
	}

	public void setNewsService(NewsService newsService) {
		this.newsService = newsService;
	}
}
