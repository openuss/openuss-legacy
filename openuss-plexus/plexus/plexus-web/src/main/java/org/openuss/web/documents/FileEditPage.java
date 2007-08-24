package org.openuss.web.documents;

import java.io.IOException;
import java.util.Date;

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

@Bean(name = "views$secured$documents$fileedit", scope = Scope.REQUEST)
@View
public class FileEditPage extends AbstractDocumentPage{
	private static final Logger logger = Logger.getLogger(FileEditPage.class);
	
	@Property(value = "#{"+Constants.DOCUMENTS_SELECTED_FILEENTRY+"}")
	private FileInfo selectedFile;
	
	@Property(value = "#{"+Constants.UPLOAD_FILE_MANAGER+"}")
	private UploadFileManager uploadFileManager;

	@Prerender
	public void prerender() throws Exception {
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
		crumb.setLink("");
		crumb.setName(i18n("documents_file"));
		crumb.setHint(i18n("documents_file"));
		crumbs.add(crumb);
		setRequestBean(Constants.BREADCRUMBS, crumbs);
	}

	public String save() throws DocumentApplicationException, IOException{
		logger.debug("saving file");
		if (selectedFile != null && selectedFile.getId() == null) {
			UploadedDocument document = (UploadedDocument) getSessionBean(Constants.UPLOADED_FILE);
			if (document != null) {
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

				documentService.createFileEntry(selectedFile, retrieveActualFolder());
				uploadFileManager.removeDocument(document);
			} else {
				documentService.createFileEntry(selectedFile, retrieveActualFolder());
			}
			addMessage(i18n("message_documents_new_ folder_created"));
		} else if (selectedFile != null && selectedFile.getId() != null) {
			
			UploadedDocument document = (UploadedDocument) getSessionBean(Constants.UPLOADED_FILE);
			if (document != null) {
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
			documentService.saveFileEntry(selectedFile);
			
			if (document != null) {
				uploadFileManager.removeDocument(document);
			}
			
			addMessage(i18n("message_documents_save_folder"));
		}
		removeSessionBean(Constants.DOCUMENTS_SELECTED_FILEENTRY);
		return Constants.DOCUMENTS_MAIN_PAGE;
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
	
} 