package org.openuss.web.braincontest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.component.UIData;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Property;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.Prerender;
import org.apache.shale.tiger.view.View;
import org.openuss.braincontest.BrainContestApplicationException;
import org.openuss.braincontest.BrainContestInfo;
import org.openuss.documents.FileInfo;
import org.openuss.framework.jsfcontrols.breadcrumbs.BreadCrumb;
import org.openuss.web.Constants;
import org.openuss.web.upload.UploadFileManager;

/**
 * @author Sebastian Roekens
 */
@Bean(name = "views$secured$braincontest$braincontestnew", scope = Scope.REQUEST)
@View
public class BrainContestNewPage extends AbstractBrainContestPage {
	private static final Logger logger = Logger.getLogger(BrainContestNewPage.class);

	@Property(value = "#{"+Constants.UPLOAD_FILE_MANAGER+"}")
	private UploadFileManager uploadFileManager;
	
	@Property(value = "#{"+Constants.BRAINCONTEST_ATTACHMENTS+"}")
	private List<FileInfo> attachments;
	
	private UIData attachmentList;

	@SuppressWarnings("unchecked")
	@Prerender
	public void prerender() throws Exception { // NOPMD idueppe
		super.prerender();
		if (isRedirected()){
			return;
		}
		if (brainContest == null || brainContest.getId() == null) {
			brainContest = new BrainContestInfo();
			brainContest.setReleaseDate(new Date(System.currentTimeMillis()));
			setBean(Constants.BRAINCONTENT_CONTEST, brainContest);
		}
		if (!isPostBack() && brainContest!=null){
			if (brainContest.getAttachments()==null){
				attachments = new ArrayList<FileInfo>();
				brainContest.setAttachments(attachments);
			} else{
				attachments = brainContest.getAttachments();
			}
			setSessionBean(Constants.BRAINCONTEST_ATTACHMENTS, attachments);
		}
		addPageCrumb();
	}

	private void addPageCrumb() {
		BreadCrumb newBrainContest = new BreadCrumb();
		newBrainContest.setLink("");
		newBrainContest.setName(i18n("braincontest_new"));
		newBrainContest.setHint(i18n("braincontest_new"));
		breadcrumbs.addCrumb(newBrainContest);
	}

	public String save() throws BrainContestApplicationException {
		brainContest.setAttachments(attachments);
		if (this.getBrainContest().getId() == null) {
			brainContest.setDomainIdentifier(courseInfo.getId());
			getBrainContestService().createContest(brainContest);
			addMessage(i18n("braincontest_message_created"));
		} else if (brainContest.getId() != null) {
			brainContest.setDomainIdentifier(courseInfo.getId());
			getBrainContestService().saveContest(brainContest);
			addMessage(i18n("braincontest_message_saved"));
		}
		setSessionBean(Constants.BRAINCONTEST_ATTACHMENTS, null);
		return Constants.BRAINCONTEST_MAIN;
	}

	public String removeAttachment() {
		logger.debug("braincontest attachment removed");
		if (attachments != null) {
			FileInfo attachment = (FileInfo) attachmentList.getRowData();
			attachments.remove(attachment);
			setSessionBean(Constants.BRAINCONTEST_ATTACHMENTS, attachments);
		}
		return Constants.SUCCESS;
	}

	public String addAttachment() throws IOException, BrainContestApplicationException {
		logger.debug("braincontest attachment add");
		if (attachments == null) {
			attachments = new ArrayList<FileInfo>();
			getBrainContest().setAttachments(attachments);
			setSessionBean(Constants.BRAINCONTEST_ATTACHMENTS, attachments);
		}
		FileInfo fileInfo = uploadFileManager.lastUploadAsFileInfo();
		if (fileInfo != null && !attachments.contains(fileInfo)) {
			if (validFileName(fileInfo.getFileName())) {
				attachments.add(fileInfo);
				setSessionBean(Constants.BRAINCONTEST_ATTACHMENTS, attachments);
			} else {
				addError(i18n("braincontest_filename_already_exists"));
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

	public UploadFileManager getUploadFileManager() {
		return uploadFileManager;
	}

	public void setUploadFileManager(UploadFileManager uploadFileManager) {
		this.uploadFileManager = uploadFileManager;
	}

	public UIData getAttachmentList() {
		return attachmentList;
	}

	public void setAttachmentList(UIData attachmentList) {
		this.attachmentList = attachmentList;
	}

	public List<FileInfo> getAttachments() {
		return attachments;
	}

	public void setAttachments(List<FileInfo> attachments) {
		this.attachments = attachments;
	}
	
}