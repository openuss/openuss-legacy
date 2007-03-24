package org.openuss.web.documents;

import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.View;
import org.openuss.documents.DocumentApplicationException;
import org.openuss.documents.FileInfo;
import org.openuss.web.Constants;

@Bean(name = "views$secured$documents$addzip", scope = Scope.REQUEST)
@View
public class DocumentAddZipPage extends AbstractDocumentPage{
	private Date releaseDate;
		
	private static final Logger logger = Logger.getLogger(DocumentAddZipPage.class);
	
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
			} finally{
				unpacker.closeQuitly();
				zip.delete();
				removeSessionBean(Constants.UPLOADED_ZIP_FILE);
			}
		} catch (IOException e) {
			logger.error(e);
			addMessage(i18n("message_error_zip_file_unpacking"));
			return Constants.FAILURE;
		}
		return Constants.SUCCESS;
	}

	private void injectReleaseDate(List<FileInfo> infos) {
		if (releaseDate != null) {
			for(FileInfo fileInfo : infos) {
				fileInfo.setCreated(releaseDate);
				fileInfo.setModified(releaseDate);
			}
		}
	}
	
	public Date getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(Date releaseDate) {
		this.releaseDate = releaseDate;
	}

}