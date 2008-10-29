package org.openuss.web.news;

import org.apache.log4j.Logger;
import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Property;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.Prerender;
import org.apache.shale.tiger.view.View;
import org.openuss.framework.jsfcontrols.breadcrumbs.BreadCrumb;
import org.openuss.lecture.CourseInfo;
import org.openuss.lecture.CourseService;
import org.openuss.lecture.InstituteInfo;
import org.openuss.lecture.InstituteService;
import org.openuss.news.NewsItemInfo;
import org.openuss.news.NewsService;
import org.openuss.news.PublisherType;
import org.openuss.web.BasePage;
import org.openuss.web.Constants;


/**
 * News Details Page
 * @author Ingo Dueppe
 * @author Sebastian Roekens
 * 
 */
@Bean(name = "views$public$news$newsDetail", scope = Scope.REQUEST)
@View
public class NewsDetailsPage extends BasePage {
	private static final Logger logger = Logger.getLogger(NewsDetailsPage.class);
	
	@Property(value="#{"+Constants.NEWS_SELECTED_NEWSITEM+"}")
	private NewsItemInfo newsItem;
	
	@Property(value="#{newsService}")
	private NewsService newsService;
	
	@Property(value="#{instituteService}")
	private InstituteService instituteService;
	
	@Property(value="#{courseService}")
	private CourseService courseService;
	
	@Prerender
	public void prerender() {
		if (newsItem != null && newsItem.getId() != null) {
			logger.debug("refreshing newsitem "+newsItem.getId());
			newsItem = newsService.getNewsItem(newsItem);
			setBean(Constants.NEWS_SELECTED_NEWSITEM, newsItem);
			if (newsItem.getPublisherType()==PublisherType.INSTITUTE){
				InstituteInfo instituteInfo = instituteService.findInstitute(newsItem.getPublisherIdentifier());
				breadcrumbs.loadInstituteCrumbs(instituteInfo);
				setBean(Constants.INSTITUTE_INFO, instituteInfo);
				addNewsCrumb();
			}else if (newsItem.getPublisherType()==PublisherType.COURSE){
				CourseInfo courseInfo = courseService.findCourse(newsItem.getPublisherIdentifier());
				breadcrumbs.loadCourseCrumbs(courseInfo);
				setBean(Constants.COURSE_INFO, courseInfo);
				addNewsCrumb();
			}
		} 
		if (newsItem == null || newsItem.getId() == null){
			addError(i18n("news_message_newsitem_not_found"));
			redirect(Constants.HOME);
			return;
		}
	}

	public void addNewsCrumb(){
		BreadCrumb newsCrumb = new BreadCrumb();
		newsCrumb.setName(i18n("institute_command_news"));
		newsCrumb.setHint(i18n("institute_command_news"));
		breadcrumbs.addCrumb(newsCrumb);
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

	public InstituteService getInstituteService() {
		return instituteService;
	}

	public void setInstituteService(InstituteService instituteService) {
		this.instituteService = instituteService;
	}

	public CourseService getCourseService() {
		return courseService;
	}

	public void setCourseService(CourseService courseService) {
		this.courseService = courseService;
	}
}
