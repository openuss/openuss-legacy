package org.openuss.web.braincontest;

import java.io.IOException;
import java.util.ArrayList;

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
import org.openuss.braincontest.BrainContestService;
import org.openuss.documents.FileInfo;
import org.openuss.web.Constants;
import org.openuss.web.enrollment.AbstractEnrollmentPage;
import org.openuss.web.upload.UploadFileManager;

@Bean(name = "views$secured$braincontest$braincontestnew", scope = Scope.REQUEST)
@View
public class BrainContestNewPage extends AbstractEnrollmentPage {
	private static final Logger logger = Logger.getLogger(BrainContestNewPage.class);

	@Property(value = "#{braincontest_contest}")
	private BrainContestInfo brainContest;

	@Property(value = "#{brainContestService}")
	private BrainContestService brainContestService;

	@Property(value = "#{"+Constants.UPLOAD_FILE_MANAGER+"}")
	private UploadFileManager uploadFileManager;
	
	private UIData attachmentList;

	@SuppressWarnings("unchecked")
	@Prerender
	public void prerender() throws Exception {
		super.prerender();
		if (!isPostBack()) {
			if (brainContest != null && brainContest.getId() != null) {
				brainContest = brainContestService.getContest(brainContest);
				setSessionBean(Constants.BRAINCONTENT_CONTEST, brainContest);
			} 
		}
	}

	public String save() throws BrainContestApplicationException {
		if (this.brainContest.getId() == null) {
			brainContest.setDomainIdentifier(enrollment.getId());
			brainContestService.createContest(brainContest);
			addMessage(i18n("braincontest_message_created"));
		} else if (this.brainContest.getId() != null) {
			brainContestService.saveContest(brainContest);
			addMessage(i18n("braincontest_message_saved"));
		}
		return Constants.BRAINCONTEST_MAIN;
	}

	public String removeAttachment() {
		logger.debug("braincontest attachment removed");
		FileInfo attachment = (FileInfo) attachmentList.getRowData();
		if (brainContest.getAttachments() != null) {
			brainContest.getAttachments().remove(attachment);
		}
		return Constants.SUCCESS;
	}

	public String addAttachment() throws IOException, BrainContestApplicationException {
		logger.debug("braincontest attachment add");
		if (brainContest.getAttachments() == null) {
			brainContest.setAttachments(new ArrayList<FileInfo>());
		}
		FileInfo fileInfo = uploadFileManager.lastUploadAsFileInfo();
		if (fileInfo != null && !brainContest.getAttachments().contains(fileInfo)) {
			if (validFileName(fileInfo.getFileName())) {
				brainContest.getAttachments().add(fileInfo);
			} else {
				addError(i18n("braincontest_filename_already_exists"));
				return Constants.FAILURE;
			}
			
		}
		
		return Constants.SUCCESS;
	}
	
	private boolean validFileName(String fileName) {
		for (FileInfo attachment : brainContest.getAttachments()) {
			if (StringUtils.equalsIgnoreCase(fileName, attachment.getFileName())) {
				return false;
			}
		}
		return true;
	}

	public BrainContestInfo getBrainContest() {
		return brainContest;
	}

	public void setBrainContest(BrainContestInfo brainContest) {
		this.brainContest = brainContest;
	}

	public BrainContestService getBrainContestService() {
		return brainContestService;
	}

	public void setBrainContestService(BrainContestService brainContestService) {
		this.brainContestService = brainContestService;
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
	
}