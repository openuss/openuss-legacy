package org.openuss.web.lecture;

import java.util.List;

import org.apache.log4j.Logger;
import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Property;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.Prerender;
import org.apache.shale.tiger.view.View;
import org.openuss.documents.DocumentApplicationException;
import org.openuss.documents.DocumentService;
import org.openuss.documents.FileInfo;
import org.openuss.documents.FolderEntry;
import org.openuss.documents.FolderInfo;
import org.openuss.news.NewsItem;
import org.openuss.news.NewsService;
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
	
	@Property(value = "#{documentService}")
	private DocumentService documentService;
	
	@Property(value = "#{newsService}")
	private NewsService newsService;
	
	@Override
	@Prerender
	public void prerender() {
		if (newsItem == null) {
			logger.debug("news item not set");
			redirect(Constants.FACULTY_NEWS_PAGE);
		}
	}

	/**
	 * Save the current newsbean into the database.
	 * 
	 * @return outcome
	 * @throws DocumentApplicationException 
	 */
	public String save() throws DocumentApplicationException {
		logger.debug("save");
		if (!newsItem.isValidExpireDate()) {
			addError(i18n("news_error_expire_before_publish_date"));
			return Constants.FACULTY_NEWS_EDIT_PAGE;
		}
		newsItem.setAuthor(getAuthorName());
		newsService.saveNewsItem(newsItem);

		processAttachment();
		return Constants.FACULTY_NEWS_PAGE;
	}

	private void processAttachment() throws DocumentApplicationException {
		FileInfo attachment = (FileInfo) getSessionBean(Constants.UPLOADED_FILE);
		if (attachment != null && newsItem.getId() != null) {
			FolderInfo folder = documentService.getFolder(newsItem);
			List<FolderEntry> attachments = documentService.getFolderEntries(null, folder);
			documentService.removeFolderEntries(attachments);
			documentService.createFileEntry(attachment, folder);
			newsItem.setAttachmentId(attachment.getId());
			newsService.saveNewsItem(newsItem);
			uploadFileManager.removeFile(attachment);
			removeSessionBean(Constants.UPLOADED_FILE);
		}
	}

	private String getAuthorName() {
		User user = (User) getSessionBean(Constants.USER);
		return user.getFirstName() + " " + user.getLastName();
	}

	/**
	 * Remove current attachment
	 * @return outcome
	 * @throws DocumentApplicationException 
	 */
	public String removeAttachment() throws DocumentApplicationException {
		Long attachmentId = newsItem.getAttachmentId();
		if (attachmentId != null) {
			documentService.removeFolderEntry(attachmentId);
			newsItem.setAttachmentId(null);
			newsService.saveNewsItem(newsItem);
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

	public DocumentService getDocumentService() {
		return documentService;
	}

	public void setDocumentService(DocumentService documentService) {
		this.documentService = documentService;
	}
}
