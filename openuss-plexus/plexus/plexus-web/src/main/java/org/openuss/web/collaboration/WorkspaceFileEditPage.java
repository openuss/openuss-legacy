package org.openuss.web.collaboration;

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

/**
 * Controller for the workspacefileedit.xhtml view.
 * 
 * @author  Projektseminar WS 07/08, Team Collaboration
 *
 */
@Bean(name = "views$secured$collaboration$workspacefileedit", scope = Scope.REQUEST)
@View
public class WorkspaceFileEditPage extends AbstractCollaborationPage {
	
	private static final Logger LOGGER = Logger.getLogger(WorkspaceFileEditPage.class);
	
	@Property(value = "#{"+Constants.COLLABORATION_SELECTED_FILEENTRY+"}")
	private FileInfo selectedFile;
	
	@Property(value = "#{"+Constants.UPLOAD_FILE_MANAGER+"}")
	private UploadFileManager uploadFileManager;
	
	private UIInput fileUpload;

	@Prerender
	public void prerender() throws Exception { // NOPMD by Administrator on 13.03.08 12:54
		super.prerender();
		if (!isPostBack()) {
			if (selectedFile.getId() != null) {
				selectedFile = documentService.getFileEntry(selectedFile.getId(), false);
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
		final BreadCrumb crumb = new BreadCrumb();
		crumb.setName(i18n("documents_file"));
		crumb.setHint(i18n("documents_file"));
		breadcrumbs.addCrumb(crumb);
	}

	/**
	 * Saves the edited FileEntry.
	 * 
	 * @return COLLABORATION_WORKSPACE_PAGE
	 * @throws DocumentApplicationException
	 * @throws IOException
	 */
	public String save() throws DocumentApplicationException, IOException{
		LOGGER.debug("saving file");
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
		removeSessionBean(Constants.COLLABORATION_SELECTED_FILEENTRY);
		return Constants.COLLABORATION_WORKSPACE_PAGE;
	}

	/**
	 * Saves a new file
	 * 
	 * @return true if successful, else false
	 * @throws IOException
	 * @throws DocumentApplicationException
	 */
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
	
	/**
	 * Converts an UploadedDocument to FileInfo selectedFile. 
	 * @param document UploadedDocument to convert.
	 * @throws IOException
	 */
	private void documentToSelectedFile(UploadedDocument document) throws IOException {
		LOGGER.debug("source is "+document.getSource());
		if (StringUtils.isBlank(selectedFile.getFileName())) {
			selectedFile.setFileName(document.getFileName());
		} else {
			String fileName = selectedFile.getFileName();
			
			StringBuilder fname = new StringBuilder(fileName);
			
			String extension = extension(document.getFileName());
			if (!StringUtils.equals(extension(fileName), extension)) {
				fname.append('.').append(extension);
			}
			selectedFile.setFileName(fname.toString());
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
			return fileName.substring(fileName.lastIndexOf('.') +1).trim();
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