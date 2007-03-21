package org.openuss.web.documents;

import org.apache.shale.tiger.managed.Property;
import org.openuss.documents.DocumentService;
import org.openuss.documents.Folder;
import org.openuss.web.enrollment.AbstractEnrollmentPage;

public class AbstractDocumentPage extends AbstractEnrollmentPage {

	@Property(value = "#{documentService}")
	protected DocumentService documentService;
	
	@Property(value = "#{folder}")
	protected Folder currentFolder;

	public DocumentService getDocumentService() {
		return documentService;
	}

	public void setDocumentService(DocumentService documentService) {
		this.documentService = documentService;
	}

	public Folder getCurrentFolder() {
		return currentFolder;
	}

	public void setCurrentFolder(Folder currentFolder) {
		this.currentFolder = currentFolder;
	}

}
