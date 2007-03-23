package org.openuss.web.documents;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.shale.tiger.managed.Property;
import org.openuss.documents.DocumentService;
import org.openuss.documents.FolderInfo;
import org.openuss.web.enrollment.AbstractEnrollmentPage;

/**
 * 
 * @author Ingo Dueppe
 */
public class AbstractDocumentPage extends AbstractEnrollmentPage {
	private static final Logger logger = Logger.getLogger(AbstractDocumentPage.class);

	@Property(value = "#{documentService}")
	protected DocumentService documentService;
	
	@Property(value = "#{folder}")
	protected FolderInfo currentFolder;

	public DocumentService getDocumentService() {
		return documentService;
	}

	public void setDocumentService(DocumentService documentService) {
		this.documentService = documentService;
	}

	public FolderInfo getCurrentFolder() {
		return currentFolder;
	}

	public void setCurrentFolder(FolderInfo currentFolder) {
		this.currentFolder = currentFolder;
	}

	
	public List<FolderInfo> getPath() {
		logger.debug("getting current path");
		if (currentFolder != null && currentFolder.getId() != null) {
			return documentService.getFolderPath(currentFolder);
		} else {
			return new ArrayList<FolderInfo>();
		}
	}
}
