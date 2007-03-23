package org.openuss.web.documents;

import org.apache.log4j.Logger;
import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Property;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.View;
import org.openuss.documents.DocumentApplicationException;
import org.openuss.documents.FolderInfo;
import org.openuss.web.Constants;

@Bean(name = "views$secured$documents$folder", scope = Scope.REQUEST)
@View
public class FolderEditPage extends AbstractDocumentPage{
	private static final Logger logger = Logger.getLogger(FolderEditPage.class);
	
	@Property(value = "#{selectedFolder}")
	private FolderInfo selectedFolder;


	public String save() throws DocumentApplicationException{
		logger.debug("new folder saved");
		if (selectedFolder != null && selectedFolder.getId() == null) {
			documentService.createFolder(selectedFolder, currentFolder);
			addMessage(i18n("message_documents_new_folder_created"));
		} else if (selectedFolder != null && selectedFolder.getId() != null) {
			documentService.saveFolder(selectedFolder);
			addMessage(i18n("message_docuements_save_folder"));
		}
		return Constants.SUCCESS;
	}


	public FolderInfo getSelectedFolder() {
		return selectedFolder;
	}


	public void setSelectedFolder(FolderInfo newFolder) {
		this.selectedFolder = newFolder;
	}
	
} 