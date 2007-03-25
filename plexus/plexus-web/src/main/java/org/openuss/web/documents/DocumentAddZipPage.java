package org.openuss.web.documents;

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
import org.openuss.web.Constants;

@Bean(name = "views$secured$documents$addzip", scope = Scope.REQUEST)
@View
public class DocumentAddZipPage extends AbstractDocumentPage{
		
	private static final Logger logger = Logger.getLogger(DocumentAddZipPage.class);
	
	@Property (value="#{"+Constants.DOCUMENTS_SELECTED_FILEENTRY+"}")
	private FileInfo file;
	
	@Prerender
	public void prerender() {
		if (file != null && file.getCreated() == null) {
			logger.debug("reseting date");
			file.setCreated(new Date());
		}
	}
	
	public String unzip() throws DocumentApplicationException{
		logger.debug("new document saved");
		File zip = (File) getSessionBean(Constants.UPLOADED_ZIP_FILE);
		ZipFileUnpacker unpacker;
		try {
			unpacker = new ZipFileUnpacker(zip);
			List<FileInfo> infos = unpacker.extractZipFile();
			injectReleaseDate(infos);
			try {
				documentService.createFolderEntries(infos, retrieveActualFolder());
				addMessage(i18n("message_extract_files_successfully", infos.size()));
			} finally{
				unpacker.closeQuitly();
				zip.delete();
				removeSessionBean(Constants.UPLOADED_ZIP_FILE);
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
}