package org.openuss.web.course;

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
 * 
 * @author Ingo Dueppe
 */
@Bean(name = "views$secured$course$coursenewsedit", scope = Scope.REQUEST)
@View
public class CourseNewsEditPage extends AbstractCoursePage {

	private static final Logger logger = Logger.getLogger(CourseNewsEditPage.class);

	private static final long serialVersionUID = 792199034646593736L;

	@Property(value = "#{" + Constants.NEWS_SELECTED_NEWSITEM + "}")
	private NewsItemInfo newsItem;

	@Property(value = "#{newsService}")
	private NewsService newsService;

	@Property(value = "#{" + Constants.UPLOAD_FILE_MANAGER + "}")
	private UploadFileManager uploadFileManager;

	private UIData attachmentList;

	@Override
	@Prerender
	public void prerender() throws Exception {
		super.prerender();
		if (!isPostBack() && newsItem != null && newsItem.getId() != null) {
			newsItem = newsService.getNewsItem(newsItem);
			setSessionBean(Constants.NEWS_SELECTED_NEWSITEM, newsItem);
		}

		BreadCrumb newCrumb;

		newCrumb = new BreadCrumb();
		newCrumb.setName(i18n("course_command_options_news"));
		newCrumb.setHint(i18n("course_command_options_news"));
		newCrumb.setLink(PageLinks.COURSE_NEWS);
		breadcrumbs.addCrumb(newCrumb);

		newCrumb = new BreadCrumb();
		newCrumb.setName(i18n("mews_selected_newsitem_header"));
		newCrumb.setHint(i18n("mews_selected_newsitem_header"));
		breadcrumbs.addCrumb(newCrumb);
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

		newsItem.setCategory(NewsCategory.COURSE);
		newsItem.setExpireDate(null);
		newsItem.setPublisherIdentifier(courseInfo.getId());
		newsItem.setAuthor(getAuthorName());
		newsItem.setPublisherType(PublisherType.COURSE);
		newsService.saveNewsItem(newsItem);

		return Constants.COURSE_NEWS_PAGE;
	}

	private String getAuthorName() {
		UserInfo user = (UserInfo) getSessionBean(Constants.USER);
		return user.getDisplayName();
	}

	public String removeAttachment() {
		logger.debug("news attachment removed");
		if (newsItem.getAttachments() != null) {
			FileInfo attachment = (FileInfo) attachmentList.getRowData();
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
		// items.add(new
		// SelectItem(NewsCategory.GLOBAL,i18n("news_category_global")));
		// items.add(new
		// SelectItem(NewsCategory.DESKTOP,i18n("news_category_desktop")));
		// items.add(new
		// SelectItem(NewsCategory.INSTITUTE,i18n("news_category_institute")));
		items.add(new SelectItem(NewsCategory.COURSE, i18n("news_category_course")));
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
	 * 
	 * @return
	 */
	public String cancel() {
		logger.debug("cancel");
		removeSessionBean(Constants.NEWS_SELECTED_NEWSITEM);
		removeSessionBean(Constants.UPLOADED_FILE);
		return Constants.COURSE_NEWS_PAGE;
	}

	/* ----------------- properties ------------------ */

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
