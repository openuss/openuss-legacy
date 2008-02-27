package org.openuss.web.collaboration;

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

/**
 * Controller for the workspacefolderedit.xhtml view.
 * 
 * @author  Projektseminar WS 07/08, Team Collaboration
 *
 */
@Bean(name = "views$secured$collaboration$workspacefolderedit", scope = Scope.REQUEST)
@View
public class WorkspaceFolderEditPage extends AbstractCollaborationPage{
	private static final Logger logger = Logger.getLogger(WorkspaceFolderEditPage.class);
	
	@Property(value = "#{"+Constants.COLLABORATION_SELECTED_FOLDER+"}")
	private FolderInfo selectedFolder;

	@Prerender
	public void prerender() throws Exception {
		super.prerender();
		addPageCrumb();
	}
	
	private void addPageCrumb() {
		BreadCrumb crumb = new BreadCrumb();
		crumb.setName(i18n("document_folder_edit_header"));
		crumb.setHint(i18n("document_folder_edit_header"));
		breadcrumbs.addCrumb(crumb);
	}

	/**
	 * Saves the edited folder
	 * 
	 * @return COLLABORATION_WORKSPACE_PAGE
	 * @throws DocumentApplicationException
	 */
	public String save() throws DocumentApplicationException{
		logger.debug("saving folder");
		if (selectedFolder != null && selectedFolder.getId() == null) {
			documentService.createFolder(selectedFolder, retrieveActualFolder());
			addMessage(i18n("message_documents_new_folder_created"));
		} else if (selectedFolder != null && selectedFolder.getId() != null) {
			documentService.saveFolder(selectedFolder);
			addMessage(i18n("message_documents_save_folder"));
		}
		removeSessionBean(Constants.COLLABORATION_SELECTED_FOLDER);
		return Constants.COLLABORATION_WORKSPACE_PAGE;
	}


	public FolderInfo getSelectedFolder() {
		return selectedFolder;
	}


	public void setSelectedFolder(FolderInfo newFolder) {
		this.selectedFolder = newFolder;
	}
	
} 