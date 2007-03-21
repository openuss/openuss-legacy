package org.openuss.web.documents;

import java.sql.Date;

import org.apache.log4j.Logger;
import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Property;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.View;
import org.openuss.documents.DocumentService;
import org.openuss.documents.Folder;
import org.openuss.repository.RepositoryFile;
import org.openuss.repository.RepositoryService;
import org.openuss.web.BasePage;
import org.openuss.web.Constants;
import org.openuss.web.upload.UploadFileManager;

@Bean(name = "views$secured$documents$addzip", scope = Scope.REQUEST)
@View
public class DocumentAddZipPage extends BasePage{
	private Date releaseDate;
		
	private static final Logger logger = Logger.getLogger(DocumentAddZipPage.class);
	
	@Property(value="#{documentService}")
	private DocumentService documentService;
	
	@Property(value="#{sessionScope.selectedDocumentFolder}")
	private Folder folder;
	
	@Property(value="#{sessionScope.enrollment}")
	private Object domainObject;
	
	@Property(value = "#{uploadFileManager}")
	private UploadFileManager uploadFileManager;
	
	@Property(value = "#{repositoryService}")
	private RepositoryService repositoryService;

	public String unzip(){
		logger.debug("new document saved");
		
		if (folder == null) {
			folder = documentService.getFolder(domainObject, null);
		} else {
			folder = documentService.getFolder(null, folder);
		}
		
		RepositoryFile zipFile = (RepositoryFile) getSessionBean(Constants.UPLOADED_FILE);
		if (zipFile != null) {
			zipFile = repositoryService.getFile(zipFile);
			documentService.createFolderEntryFromZip(zipFile.getInputStream(), folder);
			uploadFileManager.removeFile(zipFile);
			addMessage(i18n("message_documents_zip_file_upload_succeed"));
			return Constants.SUCCESS;
		} else {
			addError(i18n("message_docuements_zip_upload_failed"));
			return Constants.FAILURE;
		}
	}
	
	public UploadFileManager getUploadFileManager() {
		return uploadFileManager;
	}


	public void setUploadFileManager(UploadFileManager uploadFileManager) {
		this.uploadFileManager = uploadFileManager;
	}




	public Date getReleaseDate() {
		return releaseDate;
	}


	public void setReleaseDate(Date releaseDate) {
		this.releaseDate = releaseDate;
	}


	public DocumentService getDocumentService() {
		return documentService;
	}


	public void setDocumentService(DocumentService documentService) {
		this.documentService = documentService;
	}


	public Object getDomainObject() {
		return domainObject;
	}


	public void setDomainObject(Object object) {
		this.domainObject = object;
	}


	public Folder getFolder() {
		return folder;
	}


	public void setFolder(Folder folder) {
		this.folder = folder;
	}

	public RepositoryService getRepositoryService() {
		return repositoryService;
	}

	public void setRepositoryService(RepositoryService repositoryService) {
		this.repositoryService = repositoryService;
	}
	
}