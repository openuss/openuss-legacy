package org.openuss.web.papersubmission;

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
import org.openuss.documents.FolderInfo;
import org.openuss.framework.jsfcontrols.breadcrumbs.BreadCrumb;
import org.openuss.web.Constants;
import org.openuss.web.PageLinks;
import org.openuss.web.documents.ZipFileUnpacker;
import org.openuss.web.upload.UploadFileManager;
import org.openuss.web.upload.UploadedDocument;

@Bean(name = "views$secured$papersubmission$submissionaddzip", scope = Scope.REQUEST)
@View
public class PaperSubmissionAddZipPage extends AbstractPaperSubmissionPage{
		
	private static final Logger logger = Logger.getLogger(PaperSubmissionAddZipPage.class);
	
	@Property (value="#{"+Constants.PAPERSUBMISSION_SELECTED_FILEENTRY+"}")
	private FileInfo file;
	
	@Property (value="#{"+Constants.UPLOAD_FILE_MANAGER+"}")
	private UploadFileManager uploadFileManager;
	
	@Prerender
	public void prerender() throws Exception {
		super.prerender();
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
		
		crumb.setName(i18n("document_addzip_header"));
		crumb.setHint(i18n("document_addzip_header"));
		breadcrumbs.addCrumb(crumb);
	}	
	
	public String unzip() throws DocumentApplicationException{
		logger.debug("new document saved");
		UploadedDocument document = (UploadedDocument) getSessionBean(Constants.UPLOADED_FILE);
		paperSubmissionInfo = loadPaperSubmission();

		File zipFile = document.getFile(); 
		ZipFileUnpacker unpacker;
		try {
			unpacker = new ZipFileUnpacker(zipFile);
			List<FileInfo> infos = unpacker.extractZipFile();
			injectReleaseDate(infos);
			try {
				FolderInfo folder = getDocumentService().getFolder(paperSubmissionInfo);
				documentService.createFileEntries(infos, folder);
				
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
			logger.error(e);
			addError(i18n("message_error_zip_file_unpacking"));
			return Constants.FAILURE;
		}
		return Constants.SUCCESS;
	}

	private void injectReleaseDate(List<FileInfo> infos) {
		if (file != null && file.getCreated() != null) {
			logger.debug("injecting release date "+file.getCreated());
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