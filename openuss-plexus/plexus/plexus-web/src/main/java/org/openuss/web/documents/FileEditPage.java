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
		setSessionBean(Constants.BREADCRUMBS, crumbs);
	}

	public String save() throws DocumentApplicationException, IOException{
		logger.debug("saving file");
		if (selectedFile != null && selectedFile.getId() == null) {
			UploadedDocument document = (UploadedDocument) getSessionBean(Constants.UPLOADED_FILE);
			logger.debug("source is "+document.getSource());
			if (document != null) {
				if (StringUtils.isBlank(selectedFile.getFileName())) {
					selectedFile.setFileName(document.getFileName());
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
			documentService.saveFileEntry(selectedFile);
			addMessage(i18n("message_docuements_save_folder"));
		}
		removeSessionBean(Constants.DOCUMENTS_SELECTED_FILEENTRY);
		return Constants.DOCUMENTS_MAIN_PAGE;
	}

	
	private String extension(String fileName) {
		if (fileName != null) {
			int index = fileName.lastIndexOf('.');
			return fileName.substring(index+1);
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