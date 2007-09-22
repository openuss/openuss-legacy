package org.openuss.web.news;

import org.apache.log4j.Logger;
import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Property;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.Prerender;
import org.apache.shale.tiger.view.View;
import org.openuss.framework.jsfcontrols.breadcrumbs.BreadCrumb;
import org.openuss.lecture.Course;
import org.openuss.lecture.Institute;
import org.openuss.lecture.LectureService;
import org.openuss.news.NewsItemInfo;
import org.openuss.news.NewsService;
import org.openuss.news.PublisherType;
import org.openuss.web.BasePage;
import org.openuss.web.Constants;
import org.openuss.web.PageLinks;


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
	
	@Property(value="#{lectureService}")
	private LectureService lectureService;
	
	@Prerender
	public void prerender() {
		crumbs.clear();
		if (newsItem != null && newsItem.getId() != null) {
			logger.debug("refreshing newsitem "+newsItem.getId());
			newsItem = newsService.getNewsItem(newsItem);
			setSessionBean(Constants.NEWS_SELECTED_NEWSITEM, newsItem);
			if (newsItem.getPublisherType()==PublisherType.INSTITUTE){
				Institute institute = getLectureService().getInstitute(newsItem.getPublisherIdentifier());
				addInstituteCrumb(institute);
				setSessionBean(Constants.INSTITUTE, institute);
				addNewsCrumb();
			}else if (newsItem.getPublisherType()==PublisherType.COURSE){
				Course course = getLectureService().getCourse(newsItem.getPublisherIdentifier());
				Institute institute = course.getInstitute();
				addInstituteCrumb(institute);
				addCourseCrumb(course);
				setSessionBean(Constants.INSTITUTE, institute);
				addNewsCrumb();
			}
		} 
		if (newsItem == null || newsItem.getId() == null){
			addError(i18n("news_message_newsitem_not_found"));
			redirect(Constants.HOME);
		}
	}

	public void addInstituteCrumb(Institute institute) {
		BreadCrumb instituteCrumb = new BreadCrumb();
		instituteCrumb.setName(institute.getShortcut());
		instituteCrumb.setLink(PageLinks.INSTITUTE_PAGE);
		instituteCrumb.addParameter("institute", institute.getId());		
		instituteCrumb.setHint(institute.getName());
		crumbs.add(instituteCrumb);
	}
	
	public void addCourseCrumb(Course course){
		BreadCrumb courseCrumb = new BreadCrumb();
		courseCrumb.setName(course.getShortcut());
		courseCrumb.setLink(PageLinks.COURSE_PAGE);
		courseCrumb.addParameter("course", course.getId());
		courseCrumb.setHint(course.getName());
		crumbs.add(courseCrumb);
	}
	
	public void addNewsCrumb(){
		BreadCrumb newsCrumb = new BreadCrumb();
		newsCrumb.setName(i18n("institute_command_news"));
		newsCrumb.setHint(i18n("institute_command_news"));
		crumbs.add(newsCrumb);
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

	public LectureService getLectureService() {
		return lectureService;
	}

	public void setLectureService(LectureService lectureService) {
		this.lectureService = lectureService;
	}
}
