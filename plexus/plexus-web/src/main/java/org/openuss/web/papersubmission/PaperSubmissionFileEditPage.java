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
import org.openuss.foundation.DefaultDomainObject;
import org.openuss.framework.jsfcontrols.breadcrumbs.BreadCrumb;
import org.openuss.paperSubmission.SubmissionStatus;
import org.openuss.web.Constants;
import org.openuss.web.PageLinks;
import org.openuss.web.upload.UploadFileManager;
import org.openuss.web.upload.UploadedDocument;

@Bean(name = "views$secured$papersubmission$submissionfileedit", scope = Scope.REQUEST)
@View
public class PaperSubmissionFileEditPage extends AbstractPaperSubmissionPage {
	
	private static final Logger LOGGER = Logger.getLogger(PaperSubmissionFileEditPage.class);
	
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
		BreadCrumb crumb = new BreadCrumb();
		crumb.setLink(PageLinks.PAPERSUBMISSION_EXAM);
		crumb.setName(i18n("papersubmission_paperlist_header"));
		crumb.setHint(i18n("papersubmission_paperlist_header"));

		breadcrumbs.loadCourseCrumbs(courseInfo);
		breadcrumbs.addCrumb(crumb);
		
		crumb = new BreadCrumb();
		crumb.setName(examInfo.getName());
		crumb.setHint(examInfo.getName());
		
		if(courseInfo != null && courseInfo.getId() != null 
				&& examInfo != null && examInfo.getId() != null){
			
			crumb.setLink(PageLinks.PAPERSUBMISSION_SUBMISSIONVIEW);
			crumb.addParameter("course",courseInfo.getId());
			crumb.addParameter("exam",examInfo.getId());
		}
		
		breadcrumbs.addCrumb(crumb);
		
		crumb = new BreadCrumb();
		crumb.setName(i18n("documents_file"));
		crumb.setHint(i18n("documents_file"));
		breadcrumbs.addCrumb(crumb);
	}

	public String save() throws DocumentApplicationException, IOException{
		LOGGER.debug("saving file");
		paperSubmissionInfo = loadPaperSubmission();
		if (isNewFile()) {
			if (!saveNewFile()) {
				addError(fileUpload.getClientId(getFacesContext()),i18n("error_file_input_required"),i18n("error_file_input_required"));
				return Constants.FAILURE;
			}
		} else if (isFileExistingInNewSubmission()) {
			selectedFile.setId(null);
			selectedFile.setModified(paperSubmissionInfo.getDeliverDate());
			if(!saveNewFile()){
				addError(fileUpload.getClientId(getFacesContext()),i18n("error_file_input_required"),i18n("error_file_input_required"));
				return Constants.FAILURE;
			}
			
		}else if(isExistingFile()){
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
		
		checkSubmissionStatus(paperSubmissionInfo);
		
		removeSessionBean(Constants.PAPERSUBMISSION_SELECTED_FILEENTRY);
		return Constants.PAPERSUBMISSION_OVERVIEW_PAGE;
	}
	
	private boolean isFileExistingInNewSubmission(){
		DefaultDomainObject domainObject = new DefaultDomainObject(paperSubmissionInfo.getId());
		if(isExistingFile() && paperSubmissionInfo.getSubmissionStatus().equals(SubmissionStatus.NOT_IN_TIME) 
				&& !getDocumentService().getFileEntries(domainObject).contains(selectedFile))
			return true;
		else
			return false;
	}
	
	
	
	private boolean saveNewFile() throws IOException, DocumentApplicationException {
		UploadedDocument document = (UploadedDocument) getSessionBean(Constants.UPLOADED_FILE);
		if (document != null) {
			documentToSelectedFile(document);
			FolderInfo folder = getDocumentService().getFolder(paperSubmissionInfo);
			documentService.createFileEntry(selectedFile, folder);
			
			permitRolesImageReadPermission(selectedFile);
			
			uploadFileManager.removeDocument(document);
			return true;
		} else {
			return false;
		}
	}

	private void documentToSelectedFile(UploadedDocument document) throws IOException {
		LOGGER.debug("source is " + document.getSource());
		if (StringUtils.isBlank(selectedFile.getFileName())) {
			selectedFile.setFileName(document.getFileName());
		} else {
			String fname = selectedFile.getFileName();
			StringBuilder fileName = new StringBuilder(fname);
			
			String extension = extension(document.getFileName());			
			if (!StringUtils.equals(extension(fname), extension)) {
				fileName.append('.').append(extension);
			}
			
			selectedFile.setFileName(fileName.toString());
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