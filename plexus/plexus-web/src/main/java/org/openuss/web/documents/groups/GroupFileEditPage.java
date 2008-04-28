package org.openuss.web.documents.groups;

import java.io.IOException;
import java.util.Date;

import javax.faces.component.UIInput;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Property;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.Prerender;
import org.apache.shale.tiger.view.View;
import org.openuss.documents.DocumentApplicationException;
import org.openuss.documents.FileInfo;
import org.openuss.framework.jsfcontrols.breadcrumbs.BreadCrumb;
import org.openuss.web.Constants;
import org.openuss.web.upload.UploadFileManager;
import org.openuss.web.upload.UploadedDocument;

@Bean(name = "views$secured$documents$groups$fileedit", scope = Scope.REQUEST)
@View
public class GroupFileEditPage extends AbstractGroupDocumentPage{
	private static final Logger logger = Logger.getLogger(GroupFileEditPage.class);
	
	@Property(value = "#{"+Constants.DOCUMENTS_SELECTED_FILEENTRY+"}")
	private FileInfo selectedFile;
	
	@Property(value = "#{"+Constants.UPLOAD_FILE_MANAGER+"}")
	private UploadFileManager uploadFileManager;
	
	private UIInput fileUpload;

	@Prerender
	public void prerender() throws Exception { // NOPMD by devopenuss on 11.03.08 14:31
		super.prerender();
		if (!isPostBack()) {
			if (selectedFile.getId() != null) {
				selectedFile = documentService.getFileEntry(selectedFile.getId(), false);
				setSessionBean(Constants.DOCUMENTS_SELECTED_FILEENTRY, selectedFile);
			}
			if (selectedFile.getCreated() == null) {
				selectedFile.setCreated(new Date());
			}
		}
		addPageCrumb();
	}
	
	private void addPageCrumb() {
		BreadCrumb crumb = new BreadCrumb();
		crumb.setName(i18n("documents_file"));
		crumb.setHint(i18n("documents_file"));
		breadcrumbs.addCrumb(crumb);
	}

	public String save() throws DocumentApplicationException, IOException{
		logger.debug("saving file");
		if (isNewFile()) {
			if (!saveNewFile()) {
				addError(fileUpload.getClientId(getFacesContext()),i18n("error_file_input_required"),i18n("error_file_input_required"));
				return Constants.FAILURE;
			}
		} else if (isExistingFile()) {
			UploadedDocument document = (UploadedDocument) getSessionBean(Constants.UPLOADED_FILE);
			if (document != null) {
				documentToSelectedFile(document);
			}
			documentService.saveFileEntry(selectedFile);
			if (document != null) {
				uploadFileManager.removeDocument(document);
			}
			addMessage(i18n("message_documents_save_file"));
		}
		removeSessionBean(Constants.DOCUMENTS_SELECTED_FILEENTRY);
		return Constants.GROUP_DOCUMENTS_MAIN_PAGE;
	}

	private boolean saveNewFile() throws IOException, DocumentApplicationException {
		UploadedDocument document = (UploadedDocument) getSessionBean(Constants.UPLOADED_FILE);
		if (document != null) {
			documentToSelectedFile(document);
			documentService.createFileEntry(selectedFile, retrieveActualFolder());
			uploadFileManager.removeDocument(document);
			return true;
		} else {
			return false;
		}
	}

	private void documentToSelectedFile(UploadedDocument document) throws IOException {
		logger.debug("source is "+document.getSource());
		if (StringUtils.isBlank(selectedFile.getFileName())) {
			selectedFile.setFileName(document.getFileName());
		} else {
			String fileName = selectedFile.getFileName();
			if (!StringUtils.equals(extension(fileName), extension(document.getFileName()))) {
				fileName = fileName + '.' +extension(document.getFileName()); // NOPMD by devopenuss on 11.03.08 14:32
			}
			selectedFile.setFileName(fileName);
		}
		selectedFile.setExtension(extension(document.getFileName()));
		selectedFile.setContentType(document.getContentType());
		selectedFile.setFileSize(document.getFileSize());
		selectedFile.setInputStream(document.getInputStream());
	}

	private boolean isExistingFile() {
		return selectedFile != null && selectedFile.getId() != null;
	}

	private boolean isNewFile() {
		return selectedFile != null && selectedFile.getId() == null;
	}

	
	private String extension(String fileName) {
		if (fileName != null) {
			return fileName.substring(fileName.lastIndexOf('.')+1).trim();
		} else {
			return "";
		}
	}


	public FileInfo getSelectedFile() {
		return selectedFile;
	}


	public void setSelectedFile(FileInfo newFolder) {
		this.selectedFile = newFolder;
	}

	public UploadFileManager getUploadFileManager() {
		return uploadFileManager;
	}

	public void setUploadFileManager(UploadFileManager uploadFileManager) {
		this.uploadFileManager = uploadFileManager;
	}

	public UIInput getFileUpload() {
		return fileUpload;
	}

	public void setFileUpload(UIInput fileUpload) {
		this.fileUpload = fileUpload;
	}

} 