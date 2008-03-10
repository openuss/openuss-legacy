package org.openuss.web.lecture;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.faces.component.UIData;
import javax.faces.model.SelectItem;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Property;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.Prerender;
import org.apache.shale.tiger.view.View;
import org.openuss.braincontest.BrainContestApplicationException;
import org.openuss.documents.DocumentApplicationException;
import org.openuss.documents.FileInfo;
import org.openuss.framework.jsfcontrols.breadcrumbs.BreadCrumb;
import org.openuss.lecture.LectureException;
import org.openuss.news.NewsCategory;
import org.openuss.news.NewsItemInfo;
import org.openuss.news.NewsService;
import org.openuss.news.PublisherType;
import org.openuss.security.UserInfo;
import org.openuss.web.Constants;
import org.openuss.web.PageLinks;
import org.openuss.web.upload.UploadFileManager;

/**
 * News Page Controller
 * @author Ingo Dueppe
 * @author Kai Stettner
 */
@Bean(name = "views$secured$lecture$newsedit", scope = Scope.REQUEST)
@View
public class NewsEditPage extends AbstractLecturePage {

	private static final Logger logger = Logger.getLogger(NewsEditPage.class);

	@Property(value = "#{"+Constants.NEWS_SELECTED_NEWSITEM+"}")
	private NewsItemInfo newsItem;

	@Property(value = "#{newsService}")
	private NewsService newsService;

	@Property(value = "#{"+Constants.UPLOAD_FILE_MANAGER+"}")
	private UploadFileManager uploadFileManager;
	
	private UIData attachmentList;
	
	@Override
	@Prerender
	public void prerender() throws LectureException{
		super.prerender();
		if (!isPostBack()) {
			if (newsItem != null && newsItem.getId() != null) {
				newsItem = newsService.getNewsItem(newsItem);
				setSessionBean(Constants.NEWS_SELECTED_NEWSITEM, newsItem);
			} 
		}
		
		addNewsCrumbs();
	}

	private void addNewsCrumbs() {
		BreadCrumb courseNewsCrumb = new BreadCrumb();
		courseNewsCrumb.setName(i18n("institute_command_news"));
		courseNewsCrumb.setHint(i18n("institute_command_news"));
		courseNewsCrumb.setLink(PageLinks.INSTITUTE_NEWS);
		courseNewsCrumb.addParameter("institute",instituteInfo.getId());
		
		BreadCrumb newsEditCrumb = new BreadCrumb();
		newsEditCrumb.setName(i18n("news_selected_newsitem_header"));
		newsEditCrumb.setHint(i18n("news_selected_newsitem_header"));
		newsEditCrumb.setLink("");
		
		breadcrumbs.loadInstituteCrumbs(instituteInfo);
		breadcrumbs.addCrumb(courseNewsCrumb);
		breadcrumbs.addCrumb(newsEditCrumb);
	}
	
	/**
	 * Save the current newsbean into the database.
	 * 
	 * @return outcome
	 * @throws DocumentApplicationException 
	 * @throws IOException 
	 */
	public String save() throws DocumentApplicationException, IOException {
		logger.debug("saving news");
		
		if (newsItem.getPublishDate().after(newsItem.getExpireDate())) {
			addError(i18n("news_error_expire_before_publish_date"));
			return Constants.INSTITUTE_NEWS_EDIT_PAGE;
		}
		newsItem.setAuthor(getAuthorName());
		newsItem.setPublisherType(PublisherType.INSTITUTE);
		newsService.saveNewsItem(newsItem);
		addMessage(i18n("news_saved_success"));		
		return Constants.INSTITUTE_NEWS_PAGE;
	}

	private String getAuthorName() {
		// FIXME - should be called directly from view
		UserInfo user = (UserInfo) getSessionBean(Constants.USER);
		return user.getDisplayName();
	}
	
	
	public String removeAttachment() {
		logger.debug("news attachment removed");
		FileInfo attachment = (FileInfo) attachmentList.getRowData();
		if (newsItem.getAttachments() != null) {
			newsItem.getAttachments().remove(attachment);
		}
		return Constants.SUCCESS;
	}

	public String addAttachment() throws IOException, BrainContestApplicationException {
		logger.debug("news attachment add");
		if (newsItem.getAttachments() == null) {
			newsItem.setAttachments(new ArrayList<FileInfo>());
		}
		FileInfo fileInfo = uploadFileManager.lastUploadAsFileInfo();
		if (fileInfo != null && !newsItem.getAttachments().contains(fileInfo)) {
			if (validFileName(fileInfo.getFileName())) {
				newsItem.getAttachments().add(fileInfo);
			} else {
				addError(i18n("news_filename_already_exists"));
				return Constants.FAILURE;
			}
			
		}
		
		return Constants.SUCCESS;
	}
	
	public List<SelectItem> getNewsCategories() {
		List<SelectItem> items = new ArrayList<SelectItem>();
		items.add(new SelectItem(NewsCategory.GLOBAL,i18n("news_category_global")));
//		items.add(new SelectItem(NewsCategory.DESKTOP,i18n("news_category_desktop")));
		items.add(new SelectItem(NewsCategory.INSTITUTE,i18n("news_category_institute")));
//		items.add(new SelectItem(NewsCategory.COURSE,i18n("news_category_course")));
		return items;
	}
	
	private boolean validFileName(String fileName) {
		for (FileInfo attachment : newsItem.getAttachments()) {
			if (StringUtils.equalsIgnoreCase(fileName, attachment.getFileName())) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Cancel editing the current newsItem
	 * @return
	 */
	public String cancel() {
		logger.debug("cancel");
		removeSessionBean(Constants.NEWS_SELECTED_NEWSITEM);
		removeSessionBean(Constants.UPLOADED_FILE);
		return Constants.INSTITUTE_NEWS_PAGE;
	}

	/* ----------------- properties ------------------*/

	public NewsItemInfo getNewsItem() {
		return newsItem;
	}

	public void setNewsItem(NewsItemInfo newsItem) {
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

	public UIData getAttachmentList() {
		return attachmentList;
	}

	public void setAttachmentList(UIData attachmentList) {
		this.attachmentList = attachmentList;
	}

}
