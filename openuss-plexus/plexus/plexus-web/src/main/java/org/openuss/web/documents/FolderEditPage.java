package org.openuss.web.documents;

import org.apache.log4j.Logger;
import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.view.Prerender;
import org.apache.shale.tiger.managed.Property;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.View;
import org.openuss.documents.DocumentApplicationException;
import org.openuss.documents.FolderInfo;
import org.openuss.framework.jsfcontrols.breadcrumbs.BreadCrumb;
import org.openuss.web.Constants;

@Bean(name = "views$secured$documents$folderedit", scope = Scope.REQUEST)
@View
public class FolderEditPage extends AbstractDocumentPage{
	private static final Logger logger = Logger.getLogger(FolderEditPage.class);
	
	@Property(value = "#{"+Constants.DOCUMENTS_SELECTED_FOLDER+"}")
	private FolderInfo selectedFolder;

	@Prerender
	public void prerender() throws Exception {
		super.prerender();
		addPageCrumb();
	}
	
	private void addPageCrumb() {
		BreadCrumb crumb = new BreadCrumb();
		crumb.setLink("");
		crumb.setName(i18n("document_folder_edit_header"));
		crumb.setHint(i18n("document_folder_edit_header"));
		crumbs.add(crumb);
		setSessionBean(Constants.BREADCRUMBS, crumbs);
	}

	public String save() throws DocumentApplicationException{
		logger.debug("saving folder");
		if (selectedFolder != null && selectedFolder.getId() == null) {
			documentService.createFolder(selectedFolder, retrieveActualFolder());
			addMessage(i18n("message_documents_new_folder_created"));
		} else if (selectedFolder != null && selectedFolder.getId() != null) {
			documentService.saveFolder(selectedFolder);
			addMessage(i18n("message_docuements_save_folder"));
		}
		removeSessionBean(Constants.DOCUMENTS_SELECTED_FOLDER);
		return Constants.DOCUMENTS_MAIN_PAGE;
	}


	public FolderInfo getSelectedFolder() {
		return selectedFolder;
	}


	public void setSelectedFolder(FolderInfo newFolder) {
		this.selectedFolder = newFolder;
	}
	
} 