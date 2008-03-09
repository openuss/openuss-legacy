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
	
	private UIData attachmentList;

	@SuppressWarnings("unchecked")
	@Prerender
	public void prerender() throws Exception { // NOPMD idueppe
		super.prerender();
		if (!isPostBack() && getBrainContest() != null && getBrainContest().getId() != null) {
			setBrainContest(getBrainContestService().getContest(getBrainContest()));
			setSessionBean(Constants.BRAINCONTENT_CONTEST, getBrainContest());
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
		if (this.getBrainContest().getId() == null) {
			getBrainContest().setDomainIdentifier(courseInfo.getId());
			getBrainContestService().createContest(getBrainContest());
			addMessage(i18n("braincontest_message_created"));
		} else if (this.getBrainContest().getId() != null) {
			getBrainContestService().saveContest(getBrainContest());
			addMessage(i18n("braincontest_message_saved"));
		}
		return Constants.BRAINCONTEST_MAIN;
	}

	public String removeAttachment() {
		logger.debug("braincontest attachment removed");
		if (getBrainContest().getAttachments() != null) {
			FileInfo attachment = (FileInfo) attachmentList.getRowData();
			getBrainContest().getAttachments().remove(attachment);
		}
		return Constants.SUCCESS;
	}

	public String addAttachment() throws IOException, BrainContestApplicationException {
		logger.debug("braincontest attachment add");
		if (getBrainContest().getAttachments() == null) {
			getBrainContest().setAttachments(new ArrayList<FileInfo>());
		}
		FileInfo fileInfo = uploadFileManager.lastUploadAsFileInfo();
		if (fileInfo != null && !getBrainContest().getAttachments().contains(fileInfo)) {
			if (validFileName(fileInfo.getFileName())) {
				getBrainContest().getAttachments().add(fileInfo);
			} else {
				addError(i18n("braincontest_filename_already_exists"));
				return Constants.FAILURE;
			}
			
		}
		
		return Constants.SUCCESS;
	}
	
	private boolean validFileName(String fileName) {
		for (FileInfo attachment : getBrainContest().getAttachments()) {
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
	
}