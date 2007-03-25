package org.openuss.web.documents;

import org.apache.log4j.Logger;
import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Property;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.Prerender;
import org.apache.shale.tiger.view.View;
import org.openuss.documents.DocumentApplicationException;
import org.openuss.documents.FileInfo;
import org.openuss.web.Constants;

@Bean(name = "views$secured$documents$file", scope = Scope.REQUEST)
@View
public class FileEditPage extends AbstractDocumentPage{
	private static final Logger logger = Logger.getLogger(FileEditPage.class);
	
	@Property(value = "#{"+Constants.DOCUMENTS_SELECTED_FILEENTRY+"}")
	private FileInfo selectedFile;

	@Prerender
	public void prerender() throws Exception {
		super.prerender();
		if (!isPostBack()) {
			if (selectedFile == null || selectedFile.getId() == null) {
				redirect(Constants.ENROLLMENT_PAGE);
			} else {
				selectedFile = documentService.getFileEntry(selectedFile.getId(), false);
				setSessionBean(Constants.DOCUMENTS_SELECTED_FILEENTRY, selectedFile);
			}
		}
	}

	public String save() throws DocumentApplicationException{
		logger.debug("saving file");
		if (selectedFile != null && selectedFile.getId() == null) {
			documentService.createFileEntry(selectedFile, retrieveActualFolder());
			addMessage(i18n("message_documents_new_folder_created"));
		} else if (selectedFile != null && selectedFile.getId() != null) {
			documentService.saveFileEntry(selectedFile);
			addMessage(i18n("message_docuements_save_folder"));
		}
		removeSessionBean(Constants.DOCUMENTS_SELECTED_FILEENTRY);
		return Constants.DOCUMENTS_MAIN_PAGE;
	}


	public FileInfo getSelectedFile() {
		return selectedFile;
	}


	public void setSelectedFile(FileInfo newFolder) {
		this.selectedFile = newFolder;
	}
	
} 