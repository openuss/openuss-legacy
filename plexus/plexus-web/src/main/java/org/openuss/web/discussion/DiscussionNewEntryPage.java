package org.openuss.web.discussion;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

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
import org.openuss.documents.FileInfo;
import org.openuss.web.Constants;
import org.openuss.web.upload.UploadFileManager;

@Bean(name = "views$secured$discussion$discussionnew", scope = Scope.REQUEST)
@View
public class DiscussionNewEntryPage extends AbstractDiscussionPage{

	private static final Logger logger = Logger.getLogger(DiscussionNewEntryPage.class);

	@Property(value = "#{"+Constants.UPLOAD_FILE_MANAGER+"}")
	private UploadFileManager uploadFileManager;
	
	private UIData attachmentList;

	@Prerender
	public void prerender() throws Exception {	
		super.prerender();
		if (getForum().isReadOnly()||topic.isReadOnly()){
			addMessage(i18n("discussion_readonly"));
			redirect(Constants.DISCUSSION_MAIN);			
		}		
	}
	
	public String send(){
		logger.debug("new document saved");
		
		postInfo.setCreated(new Date(System.currentTimeMillis()));
		String ip = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest()).getRemoteAddr();
		logger.debug("Client IP = "+ ip);
		postInfo.setIsEdited(postInfo.getId()!=null);
		postInfo.setLastModification(new Date(System.currentTimeMillis()));
		if (topic.getId()==null){
			postInfo.setIp(ip);
			postInfo.setSubmitter(user.getUsername());
			postInfo.setSubmitterId(user.getId());
			discussionService.createTopic(postInfo, getForum());
			return Constants.DISCUSSION_MAIN;
		}
		if (postInfo.getId()== null){
			postInfo.setIp(ip);
			postInfo.setSubmitter(user.getUsername());
			postInfo.setSubmitterId(user.getId());
			discussionService.addPost(postInfo, topic);			
		} else if (postInfo.getId()!= null){
			postInfo.setEditor(user.getUsername());
			postInfo.setIsEdited(true);
			discussionService.updatePost(postInfo);
		}
		return Constants.DISCUSSION_THREAD;		
	}
	
	public String removeAttachment() {
		logger.debug("discussion attachment removed");
		FileInfo attachment = (FileInfo) attachmentList.getRowData();
		if (postInfo.getAttachments() != null) {
			postInfo.getAttachments().remove(attachment);
		}
		return Constants.SUCCESS;
	}

	public String addAttachment() throws IOException, BrainContestApplicationException {
		logger.debug("discussion attachment added");
		if (postInfo.getAttachments() == null) {
			postInfo.setAttachments(new ArrayList<FileInfo>());
		}
		FileInfo fileInfo = uploadFileManager.lastUploadAsFileInfo();
		if (fileInfo != null && !postInfo.getAttachments().contains(fileInfo)) {
			if (validFileName(fileInfo.getFileName())) {
				postInfo.getAttachments().add(fileInfo);
			} else {
				addError(i18n("discussion_filename_already_exists"));
				return Constants.FAILURE;
			}
			
		}
		
		return Constants.SUCCESS;
	}
	
	private boolean validFileName(String fileName) {
		for (FileInfo attachment : postInfo.getAttachments()) {
			if (StringUtils.equalsIgnoreCase(fileName, attachment.getFileName())) {
				return false;
			}
		}
		return true;
	}	
	
	
	public String addFormula(){
		logger.debug("formula added");
		return Constants.SUCCESS;		
	}
	
	public String cancel(){
		if (topic.getId()==null) return Constants.DISCUSSION_MAIN;
		return Constants.DISCUSSION_THREAD;
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

}