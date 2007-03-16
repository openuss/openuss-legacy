package org.openuss.web.lecture;

import org.apache.log4j.Logger;
import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Property;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.Prerender;
import org.apache.shale.tiger.view.View;
import org.openuss.news.NewsItem;
import org.openuss.news.NewsService;
import org.openuss.repository.RepositoryFile;
import org.openuss.security.User;
import org.openuss.web.Constants;
import org.openuss.web.upload.UploadFileManager;

/**
 * News Page Controller
 * @author Ingo Dueppe
 */
@Bean(name = "views$secured$lecture$newsedit", scope = Scope.REQUEST)
@View
public class NewsEditPage extends AbstractLecturePage {

	private static final Logger logger = Logger.getLogger(NewsEditPage.class);

	private static final long serialVersionUID = 792199034646593736L;

	@Property(value = "#{sessionScope.newsItem}")
	private NewsItem newsItem;

	@Property(value = "#{uploadFileManager}")
	private UploadFileManager uploadFileManager;
	
	@Property(value = "#{newsService}")
	private NewsService newsService;
	
	@Override
	@Prerender
	public void prerender() {
		if (newsItem == null) {
			logger.debug("news item not set");
			redirect(Constants.FACULTY_NEWS_PAGE);
		}
		// fetch uploaded files also a validation error occurs 
		fetchUploadedFile();
	}
	
	/**
	 * checks whether or not a uploaded file is available
	 * @return true if a file was uploaded
	 */
	private boolean fetchUploadedFile() {
		RepositoryFile attachment = (RepositoryFile) getSessionBean(Constants.UPLOADED_FILE);
		if (attachment != null) {
			newsItem.setAttachment(attachment);
			removeSessionBean(Constants.UPLOADED_FILE);
		}
		return attachment != null;
	}

	/**
	 * Save the current newsbean into the database.
	 * 
	 * @return outcome
	 */
	public String save() {
		logger.debug("save");
		if (!newsItem.isValidExpireDate()) {
			addError(i18n("news_error_expire_before_publish_date"));
			return Constants.FACULTY_NEWS_EDIT_PAGE;
		}
		if (fetchUploadedFile()) {
			// a new file was uploaded so remove it from upload manager to avoid that it will be deleted
			uploadFileManager.unregisterFile(newsItem.getAttachment());
		}
		newsItem.setAuthor(getAuthorName());
		newsService.saveNewsItem(newsItem);
		return Constants.FACULTY_NEWS_PAGE;
	}

	private String getAuthorName() {
		User user = (User) getSessionBean(Constants.USER);
		return user.getFirstName() + " " + user.getLastName();
	}

	/**
	 * Remove current attachment
	 * @return outcome
	 */
	public String removeAttachment() {
		RepositoryFile attachment = newsItem.getAttachment();
		if (attachment != null) {
			newsItem.setAttachment(null);
			uploadFileManager.removeFile(attachment);
			removeSessionBean(Constants.UPLOADED_FILE);
		}
		return Constants.FACULTY_NEWS_EDIT_PAGE;
	}

	/**
	 * Cancel editing the current newsItem
	 * @return
	 */
	public String cancel() {
		logger.debug("cancel");
		removeSessionBean(Constants.NEWSITEM);
		removeSessionBean(Constants.UPLOADED_FILE);
		return Constants.FACULTY_NEWS_PAGE;
	}

	/* ----------------- properties ------------------*/

	public NewsItem getNewsItem() {
		return newsItem;
	}

	public void setNewsItem(NewsItem newsItem) {
		this.newsItem = newsItem;
	}

	public UploadFileManager getUploadFileManager() {
		return uploadFileManager;
	}

	public void setUploadFileManager(UploadFileManager uploadFileManager) {
		this.uploadFileManager = uploadFileManager;
	}

	public NewsService getNewsService() {
		return newsService;
	}

	public void setNewsService(NewsService newsService) {
		this.newsService = newsService;
	}
}
