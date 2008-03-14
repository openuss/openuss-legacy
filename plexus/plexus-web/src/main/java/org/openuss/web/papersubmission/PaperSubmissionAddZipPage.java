package org.openuss.web.papersubmission;

import java.io.File;
import java.io.IOException;
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
		
	private static final Logger LOGGER = Logger.getLogger(PaperSubmissionAddZipPage.class);
	
	@Property (value="#{"+Constants.PAPERSUBMISSION_SELECTED_FILEENTRY+"}")
	private FileInfo file;
	
	@Property (value="#{"+Constants.UPLOAD_FILE_MANAGER+"}")
	private UploadFileManager uploadFileManager;
	
	@Prerender
	public void prerender() throws Exception { // NOPMD by Administrator on 13.03.08 12:56
		super.prerender();
		addPageCrumb();
	}
	
	private void addPageCrumb() {
		BreadCrumb crumb = new BreadCrumb(PageLinks.PAPERSUBMISSION_EXAM, 
				i18n("papersubmission_paperlist_header"), 
				i18n("papersubmission_paperlist_header"));

		breadcrumbs.loadCourseCrumbs(courseInfo);
		breadcrumbs.addCrumb(crumb);
		
		if (this.examInfo != null) {
			crumb = new BreadCrumb(examInfo.getName(), examInfo.getName());
			if(courseInfo != null && courseInfo.getId() != null 
					&& examInfo != null && examInfo.getId() != null){
				
				crumb.setLink(PageLinks.PAPERSUBMISSION_SUBMISSIONVIEW);
				crumb.addParameter("course",courseInfo.getId());
				crumb.addParameter("exam",examInfo.getId());
			}			
			breadcrumbs.addCrumb(crumb);
			
			breadcrumbs.addCrumb(new BreadCrumb(i18n("document_addzip_header"), i18n("document_addzip_header")));
		}
	}	
	
	public String unzip() throws DocumentApplicationException{ // NOPMD by Administrator on 13.03.08 12:56
		LOGGER.debug("new document saved");
		
		UploadedDocument document = (UploadedDocument) getSessionBean(Constants.UPLOADED_FILE);
		paperSubmissionInfo = loadPaperSubmission();

		File zipFile = document.getFile(); 
		ZipFileUnpacker unpacker;
		try {
			unpacker = new ZipFileUnpacker(zipFile);
			final List<FileInfo> infos = unpacker.extractZipFile();
			injectReleaseDate(infos);
			try {
				final FolderInfo folder = getDocumentService().getFolder(paperSubmissionInfo);
				for(FileInfo fileInfo : infos){
					String fileName = fileInfo.getFileName();
					fileInfo.setFileName(fileName.replaceAll("/", "_"));
					
				}
				documentService.createFileEntries(infos, folder);
				
				uploadFileManager.removeDocument(document);
				addMessage(i18n("message_extract_files_successfully", infos.size()));
				
				checkSubmissionStatus(paperSubmissionInfo);
			} finally {
				unpacker.closeQuitly();
				if (!zipFile.delete()) {
					LOGGER.error("Unable to delete zip file " + zipFile.getAbsolutePath());
				}
				removeSessionBean(Constants.UPLOADED_FILE);
			}
		} catch (IOException e) {
			LOGGER.error("Unzipping failed", e);
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
				fileInfo.setModified(file.getModified());
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