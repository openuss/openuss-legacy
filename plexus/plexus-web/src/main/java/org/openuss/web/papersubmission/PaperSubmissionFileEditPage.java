package org.openuss.web.papersubmission;


import java.io.IOException;
import java.util.Date;
import java.util.List;

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
import org.openuss.documents.FolderInfo;
import org.openuss.framework.jsfcontrols.breadcrumbs.BreadCrumb;
import org.openuss.paperSubmission.ExamInfo;
import org.openuss.paperSubmission.PaperSubmissionInfo;
import org.openuss.security.Roles;
import org.openuss.security.SecurityService;
import org.openuss.security.acl.LectureAclEntry;
import org.openuss.web.Constants;
import org.openuss.web.upload.UploadFileManager;
import org.openuss.web.upload.UploadedDocument;

@Bean(name = "views$secured$papersubmission$paperfileedit", scope = Scope.REQUEST)
@View
public class PaperSubmissionFileEditPage extends AbstractPaperSubmissionPage {
	private static final Logger logger = Logger.getLogger(PaperSubmissionFileEditPage.class);
	
	@Property(value = "#{"+Constants.PAPERSUBMISSION_SELECTED_FILEENTRY+"}")
	private FileInfo selectedFile;
	
	@Property(value = "#{"+Constants.UPLOAD_FILE_MANAGER+"}")
	private UploadFileManager uploadFileManager;
	
	private UIInput fileUpload;

	@Prerender
	public void prerender() throws Exception {
		super.prerender();
		
		if (!isPostBack()) {
			if (selectedFile.getId() != null) {
				selectedFile = documentService.getFileEntry(selectedFile.getId(), false);
				setSessionBean(Constants.PAPERSUBMISSION_SELECTED_FILEENTRY, selectedFile);
			}
			if (selectedFile.getCreated() == null) {
				selectedFile.setCreated(new Date());
			}
		}
		addPageCrumb();
	}
	
	private void addPageCrumb() {
		// FIXME: create correct bread-crumbs
		BreadCrumb crumb = new BreadCrumb();
		crumb.setName(i18n("documents_file"));
		crumb.setHint(i18n("documents_file"));
		breadcrumbs.addCrumb(crumb);
	}

	public String save() throws DocumentApplicationException, IOException{
		logger.debug("saving file");
		paperSubmissionInfo = loadPaperSubmission();
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
			permitRolesImageReadPermission(selectedFile);
			
			if (document != null) {
				uploadFileManager.removeDocument(document);
			}
			addMessage(i18n("message_documents_save_file"));
		}
		removeSessionBean(Constants.PAPERSUBMISSION_SELECTED_FILEENTRY);
		return Constants.PAPERSUBMISSION_OVERVIEW_PAGE;
	}
	
	
	
	private boolean saveNewFile() throws IOException, DocumentApplicationException {
		UploadedDocument document = (UploadedDocument) getSessionBean(Constants.UPLOADED_FILE);
		if (document != null) {
			documentToSelectedFile(document);
			FolderInfo folder = getDocumentService().getFolder(paperSubmissionInfo);
			documentService.createFileEntry(selectedFile, folder);
			
			permitRolesImageReadPermission(selectedFile);
			
			uploadFileManager.removeDocument(document);
//			paperSubmissionService.updatePaperSubmission(paperSubmissionInfo);
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
				fileName = fileName + '.' +extension(document.getFileName());
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