package org.openuss.web.discussion;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.component.UIData;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Property;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.Prerender;
import org.apache.shale.tiger.view.View;
import org.openuss.braincontest.BrainContestApplicationException;
import org.openuss.discussion.PostInfo;
import org.openuss.documents.FileInfo;
import org.openuss.framework.jsfcontrols.breadcrumbs.BreadCrumb;
import org.openuss.web.Constants;
import org.openuss.web.upload.UploadFileManager;

@Bean(name = "views$secured$discussion$discussionnew", scope = Scope.REQUEST)
@View
public class DiscussionNewEntryPage extends AbstractDiscussionPage {

	private static final Logger logger = Logger.getLogger(DiscussionNewEntryPage.class);

	@Property(value = "#{" + Constants.UPLOAD_FILE_MANAGER + "}")
	private UploadFileManager uploadFileManager;

	@Property(value = "#{" + Constants.DISCUSSION_QUOTE_POST + "}")
	private PostInfo quotePost;
	
	@Property(value = "#{" + Constants.POST_ATTACHMENTS + "}")
	private List<FileInfo> attachments;
	
	private UIData attachmentList;

	@Prerender
	public void prerender() throws Exception {
		super.prerender();
		if (isRedirected()) {
			return;
		}
		// reload topic
		if (topic != null && topic.getId() != null) {
			topic = discussionService.getTopic(topic);
			setBean(Constants.DISCUSSION_TOPIC, topic);
		}
		if (!(topic == null || topic.getId() == null)) {
			// check if existing topic belongs to forum
			if (!topic.getForumId().equals(forum.getId())) {
				addError(i18n(Constants.DISCUSSION_THREAD_NOT_FOUND));
				redirect(Constants.DISCUSSION_MAIN);
				return;
			}
			// check if forum or topic is read only
			if ((getForum().isReadOnly() || topic.isReadOnly()) && (!isAssistant())) {
				addError(i18n("discussion_readonly"));
				redirect(Constants.DISCUSSION_MAIN);
				return;
			}
			// case = new post or edit post
			if (postInfo.getId() != null) {
				// case = edit post
				postInfo = discussionService.getPost(postInfo);
				// check if post belongs to topic
				if (postInfo == null || (!postInfo.getTopicId().equals(topic.getId()))) {
					addError(i18n(Constants.DISCUSSION_POST_NOT_FOUND));
					redirect(Constants.DISCUSSION_MAIN);
					return;
				}				
				// check if user is submitter
				if ((!postInfo.getSubmitterId().equals(user.getId())) && (!isAssistant())) {
					// redirect rights
					addError(i18n(Constants.DISCUSSION_POST_NOT_AUTHOR));
					redirect(Constants.DISCUSSION_MAIN);
					return;
				}
				if (!isPostBack() && postInfo != null){
					attachments = postInfo.getAttachments();
					setSessionBean(Constants.POST_ATTACHMENTS, attachments);
				}
			} else if (postInfo.getId() == null) {
				// case = new post
				postInfo = new PostInfo();
				postInfo.setTitle("Re:" + topic.getTitle());
				//check if post = quote of other post
				if (quotePost!=null && quotePost.getId() != null){
					quotePost = discussionService.getPost(quotePost);
					//check if quote post exists
					if (quotePost.getId()!=null){
						postInfo = prepareQuotePost(quotePost);
					}
				}
			}
		} else if ((topic == null || topic.getId() == null)) {
			// case = new topic
			// check if forum is read only
			if (getForum().isReadOnly() && (!isAssistant())) {
				addError(i18n("discussion_readonly"));
				redirect(Constants.DISCUSSION_MAIN);
				return;
			}
			postInfo = new PostInfo();
		}
		if (attachments == null){
			attachments = new ArrayList<FileInfo>();
			setSessionBean(Constants.POST_ATTACHMENTS, attachments);
		}
		setBean(Constants.DISCUSSION_DISCUSSIONENTRY, postInfo);
		addPageCrumb();
	}

	private PostInfo prepareQuotePost(PostInfo quoteFrom) {
		PostInfo post = new PostInfo();
		String text = "<div style=\"border: 1px solid rgb(204, 204, 204); margin: 15px 20px; padding: 4px; background-color: rgb(238, 238, 238);\"><strong>"
				+ quoteFrom.getSubmitter()
				+ " - "
				+ quoteFrom.getCreated()
				+ ":</strong> <br/>"
				+ quoteFrom.getText()
				+ "</div><br/><br/>";
		post.setText(text);
		post.setTitle("Re: " + quoteFrom.getTitle());
		return post;
	}	
	
	public String send() {
		postInfo.setCreated(new Date(System.currentTimeMillis()));
		String ip = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest())
				.getRemoteAddr();
		logger.debug("Saving discussion post edited by Client IP = " + ip);
		postInfo.setIsEdited(postInfo.getId() != null);
		postInfo.setLastModification(new Date(System.currentTimeMillis()));
		postInfo.setAttachments(attachments);
		if (topic.getId() == null) {
			postInfo.setIp(ip);
			postInfo.setSubmitter(user.getUsername());
			postInfo.setSubmitterId(user.getId());
			discussionService.createTopic(postInfo, getForum());
			setSessionBean(Constants.POST_ATTACHMENTS, null);
			return Constants.DISCUSSION_MAIN;
		}
		if (postInfo.getId() == null) {
			postInfo.setIp(ip);
			postInfo.setSubmitter(user.getUsername());
			postInfo.setSubmitterId(user.getId());
			discussionService.addPost(postInfo, topic);
		} else if (postInfo.getId() != null) {
			postInfo.setEditor(user.getUsername());
			postInfo.setIsEdited(true);
			discussionService.updatePost(postInfo);
		}
		setSessionBean(Constants.POST_ATTACHMENTS, null);
		return Constants.DISCUSSION_THREAD;
	}

	private void addPageCrumb() {
		BreadCrumb crumb = new BreadCrumb();
		crumb.setName(i18n("discussion_new_discussion_entry"));
		crumb.setHint(i18n("discussion_new_discussion_entry"));
		breadcrumbs.addCrumb(crumb);
	}

	public String removeAttachment() {
		logger.debug("discussion attachment removed");
		if (attachments != null) {
			FileInfo attachment = (FileInfo) attachmentList.getRowData();
			attachments.remove(attachment);
			setSessionBean(Constants.POST_ATTACHMENTS, attachments);
		}
		return Constants.SUCCESS;
	}

	public String addAttachment() throws IOException, BrainContestApplicationException {
		logger.debug("discussion attachment added");
		if (attachments == null) {
			attachments = new ArrayList<FileInfo>();
			setSessionBean(Constants.POST_ATTACHMENTS, attachments);
		}
		FileInfo fileInfo = uploadFileManager.lastUploadAsFileInfo();
		if (fileInfo != null && !attachments.contains(fileInfo)) {
			if (validFileName(fileInfo.getFileName())) {
				attachments.add(fileInfo);
				setSessionBean(Constants.POST_ATTACHMENTS, attachments);
			} else {
				addError(i18n("discussion_filename_already_exists"));
				return Constants.FAILURE;
			}

		}

		return Constants.SUCCESS;
	}

	private boolean validFileName(String fileName) {
		for (FileInfo attachment : attachments) {
			if (StringUtils.equalsIgnoreCase(fileName, attachment.getFileName())) {
				return false;
			}
		}
		return true;
	}

	public String addFormula() {
		logger.debug("formula added");
		return Constants.SUCCESS;
	}

	public String cancel(){
		if (topic.getId() == null) {
			return Constants.DISCUSSION_MAIN;
		} else {
			setBean(Constants.DISCUSSION_TOPIC, topic);
			return Constants.DISCUSSION_THREAD;
		}
	}

	public UIData getAttachmentList() {
		return attachmentList;
	}

	public void setAttachmentList(UIData attachmentList) {
		this.attachmentList = attachmentList;
	}

	public UploadFileManager getUploadFileManager() {
		return uploadFileManager;
	}

	public void setUploadFileManager(UploadFileManager uploadFileManager) {
		this.uploadFileManager = uploadFileManager;
	}

	public PostInfo getQuotePost() {
		return quotePost;
	}

	public void setQuotePost(PostInfo quotePost) {
		this.quotePost = quotePost;
	}

	public List<FileInfo> getAttachments() {
		return attachments;
	}

	public void setAttachments(List<FileInfo> attachments) {
		this.attachments = attachments;
	}

}