package org.openuss.web.collaboration;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

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
import org.openuss.web.documents.ZipFileUnpacker;
import org.openuss.web.upload.UploadFileManager;
import org.openuss.web.upload.UploadedDocument;

/**
 * Controller for the addzip.xhtml view.
 * 
 * @author  Projektseminar WS 07/08, Team Collaboration
 *
 */
@Bean(name = "views$secured$collaboration$addzip", scope = Scope.REQUEST)
@View
public class WorkspaceAddZipPage extends AbstractCollaborationPage{
		
	private static final Logger LOGGER = Logger.getLogger(WorkspaceAddZipPage.class);
	
	@Property (value="#{"+Constants.COLLABORATION_SELECTED_FILEENTRY+"}")
	private FileInfo file;
	
	@Property (value="#{"+Constants.UPLOAD_FILE_MANAGER+"}")
	private UploadFileManager uploadFileManager;
	
	@Prerender
	public void prerender() {
		super.prerender();
		if (file != null && file.getCreated() == null) {
			LOGGER.debug("reseting date");
			file.setCreated(new Date());
		}
		addPageCrumb();
	}
	
	private void addPageCrumb() {
		BreadCrumb crumb = new BreadCrumb();
		crumb.setName(i18n("document_addzip_header"));
		crumb.setHint(i18n("document_addzip_header"));
		breadcrumbs.addCrumb(crumb);
	}	
	
	/**
	 * Unzips a uploaded file
	 * 
	 * @return success
	 * @throws DocumentApplicationException
	 */
	public String unzip() throws DocumentApplicationException{
		LOGGER.debug("new document saved");
		UploadedDocument document = (UploadedDocument) getSessionBean(Constants.UPLOADED_FILE);

		File zipFile = document.getFile(); 
		ZipFileUnpacker unpacker;
		try {
			unpacker = new ZipFileUnpacker(zipFile);
			List<FileInfo> infos = unpacker.extractZipFile();
			injectReleaseDate(infos);
			try {
				documentService.createFileEntries(infos, retrieveActualFolder());
				
				for (FileInfo fileInfo : infos) {
					permitRolesImageReadPermission(fileInfo);
				}
				
				uploadFileManager.removeDocument(document);
				addMessage(i18n("message_extract_files_successfully", infos.size()));
			} finally{
				unpacker.closeQuitly();
				zipFile.delete();
				removeSessionBean(Constants.UPLOADED_FILE);
			}
		} catch (IOException e) {
			LOGGER.error(e);
			addError(i18n("message_error_zip_file_unpacking"));
			return Constants.FAILURE;
		}
		return Constants.SUCCESS;
	}

	private void injectReleaseDate(List<FileInfo> infos) {
		if (file != null && file.getCreated() != null) {
			LOGGER.debug("injecting release date "+file.getCreated());
			for(FileInfo fileInfo : infos) {
				fileInfo.setCreated(file.getCreated());
				fileInfo.setModified(file.getCreated());
			}
		}
	}

	public FileInfo getFile() {
		return file;
	}

	public void setFile(FileInfo file) {
		this.file = file;
	}

	public UploadFileManager getUploadFileManager() {
		return uploadFileManager;
	}

	public void setUploadFileManager(UploadFileManager uploadFileManager) {
		this.uploadFileManager = uploadFileManager;
	}
}