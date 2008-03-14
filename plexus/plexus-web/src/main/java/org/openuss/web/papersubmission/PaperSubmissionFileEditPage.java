package org.openuss.web.papersubmission;

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
import org.openuss.documents.FolderInfo;
import org.openuss.foundation.DefaultDomainObject;
import org.openuss.framework.jsfcontrols.breadcrumbs.BreadCrumb;
import org.openuss.paperSubmission.SubmissionStatus;
import org.openuss.web.Constants;
import org.openuss.web.PageLinks;
import org.openuss.web.upload.UploadFileManager;
import org.openuss.web.upload.UploadedDocument;

/**
 * Backing Bean for submissionfileedit.xhtml.
 * @author Projektseminar WS 07/08, Team Collaboration
 *
 */
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
	public void prerender() throws Exception { // NOPMD by Administrator on 13.03.08 13:00
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
	
	/**
	 * Adds an additional BreadCrumb to the course crumbs.
	 */
	private void addPageCrumb() {
		BreadCrumb crumb = new BreadCrumb(PageLinks.PAPERSUBMISSION_EXAM,
				i18n("papersubmission_paperlist_header"),
				i18n("papersubmission_paperlist_header"));

		breadcrumbs.loadCourseCrumbs(courseInfo);
		breadcrumbs.addCrumb(crumb);
		
		if (examInfo != null && examInfo.getId() != null) {
			crumb = new BreadCrumb(examInfo.getName(), examInfo.getName());
			
			if(courseInfo != null && courseInfo.getId() != null){
				crumb.setLink(PageLinks.PAPERSUBMISSION_SUBMISSIONVIEW);
				crumb.addParameter("course",courseInfo.getId());
				crumb.addParameter("exam",examInfo.getId());
			}
			
			breadcrumbs.addCrumb(crumb);
		}
		
		crumb = new BreadCrumb(i18n("documents_file"), i18n("documents_file"));
		breadcrumbs.addCrumb(crumb);
	}

	/**
	 * Saves a PaperSubmission.
	 * @return PaperSubmission Overview Page.
	 * @throws DocumentApplicationException
	 * @throws IOException
	 */
	public String save() throws DocumentApplicationException, IOException{ // NOPMD by Administrator on 13.03.08 12:56
		LOGGER.debug("saving file");
		paperSubmissionInfo = loadPaperSubmission();
		if (isNewFile()) {
			if (!saveNewFile()) {
				addError(fileUpload.getClientId(getFacesContext()),i18n("error_file_input_required"),i18n("error_file_input_required")); // NOPMD by Administrator on 13.03.08 13:01
				return Constants.FAILURE;
			}
		} else if (isFileExistingInNewSubmission()) {
			selectedFile.setId(null);
			selectedFile.setModified(paperSubmissionInfo.getDeliverDate());
			if(!saveNewFile()){
				addError(fileUpload.getClientId(getFacesContext()),i18n("error_file_input_required"),i18n("error_file_input_required")); // NOPMD by Administrator on 13.03.08 13:01
				return Constants.FAILURE;
			}
			
		}else if(isExistingFile()){
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
		
		checkSubmissionStatus(paperSubmissionInfo);
		
		removeSessionBean(Constants.PAPERSUBMISSION_SELECTED_FILEENTRY);
		return Constants.PAPERSUBMISSION_OVERVIEW_PAGE;
	}
	
	/**
	 * Checks if a new File is existing in a new PaperSubmission.
	 * @return <code>true</code> if the new File is existing in a new PaperSubmission, otherwise <code>false</code>.
	 */
	private boolean isFileExistingInNewSubmission(){
		DefaultDomainObject domainObject = new DefaultDomainObject(paperSubmissionInfo.getId());
		return (isExistingFile() && paperSubmissionInfo.getSubmissionStatus().equals(SubmissionStatus.NOT_IN_TIME) 
				&& !getDocumentService().getFileEntries(domainObject).contains(selectedFile));
	}
	
	/**
	 * Saves a new File.
	 * @return <code>true</code> if the new File was uploaded, otherwise <code>false</code>.
	 * @throws IOException
	 * @throws DocumentApplicationException
	 */	
	private boolean saveNewFile() throws IOException, DocumentApplicationException {
		UploadedDocument document = (UploadedDocument) getSessionBean(Constants.UPLOADED_FILE);
		if (document != null) {
			documentToSelectedFile(document);
			FolderInfo folder = getDocumentService().getFolder(paperSubmissionInfo);
			documentService.createFileEntry(selectedFile, folder);
			
			uploadFileManager.removeDocument(document);
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Converts UploadedDocument to FileInfo selectedFile.
	 * @param document UploadedDocument File.
	 * @throws IOException
	 */
	private void documentToSelectedFile(UploadedDocument document) throws IOException {
		LOGGER.debug("source is " + document.getSource());
		if (StringUtils.isBlank(selectedFile.getFileName())) {
			selectedFile.setFileName(document.getFileName());
		} else {
			final String fname = selectedFile.getFileName();
			final StringBuilder fileName = new StringBuilder(fname);
			
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

	/**
	 * Return if a File is selected or not.
	 * @return <code>true</code> if a File is selected, otherwise <code>false</false>. 
	 */
	private boolean isExistingFile() {
		return selectedFile != null && selectedFile.getId() != null;
	}

	/**
	 * Return if a File is new or not.
	 * @return <code>true</code> if a File is new, otherwise <code>false</false>. 
	 */
	private boolean isNewFile() {
		return selectedFile != null && selectedFile.getId() == null;
	}

	/**
	 * Returns extension of a filename.
	 * @param fileName Filename to check.
	 * @return Extension of the filename.
	 */
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