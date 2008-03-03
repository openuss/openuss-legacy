package org.openuss.web.documents.groups;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.shale.tiger.managed.Property;
import org.apache.shale.tiger.view.Prerender;
import org.openuss.documents.DocumentService;
import org.openuss.documents.FolderInfo;
import org.openuss.framework.jsfcontrols.breadcrumbs.BreadCrumb;
import org.openuss.groups.UserGroupInfo;
import org.openuss.web.Constants;
import org.openuss.web.PageLinks;
import org.openuss.web.course.AbstractCoursePage;
import org.openuss.web.documents.AbstractDocumentPage;
import org.openuss.web.groups.components.AbstractGroupPage;

/**
 * @author Lutz D. Kramer
 */
public class AbstractGroupDocumentPage extends AbstractGroupPage {
	
	private static final Logger logger = Logger.getLogger(AbstractDocumentPage.class);
	
	@Property(value = "#{documentService}")
	protected DocumentService documentService;
	
	@Property(value = "#{documents_current_folder}")
	protected FolderInfo currentFolder;

	@Prerender
	public void prerender() throws Exception {
		super.prerender();
		if (currentFolder == null && groupInfo == null) {
			redirect(Constants.OUTCOME_BACKWARD);
		} else {
			currentFolder = retrieveActualFolder();
		}
		setSessionAttribute(Constants.DOCUMENTS_CURRENT_FOLDER, currentFolder);
		addDocumentsCrumbs();
	}

	public void addDocumentsCrumbs(){
		breadcrumbs.loadGroupCrumbs(groupInfo);
		
		for(FolderInfo folder : getCurrentPath()) {
			BreadCrumb crumb = new BreadCrumb();
			if (folder.isRoot()) {
				crumb.setName(i18n("documents_main_header"));
				crumb.setHint(i18n("documents_root_folder"));
			} else {
				crumb.setName(folder.getName());
				crumb.setHint(folder.getDescription());
			}
			crumb.setLink(PageLinks.GROUP_DOCUMENTS_MAIN);
			crumb.addParameter("group",groupInfo.getId());
			crumb.addParameter("folder",folder.getId());
			breadcrumbs.addCrumb(crumb);
		}
	}
	
	public DocumentService getDocumentService() {
		return documentService;
	}

	public void setDocumentService(DocumentService documentService) {
		this.documentService = documentService;
	}
	
	public UserGroupInfo getGroupInfo() {
		return groupInfo;
	}

	public void setGroupInfo(UserGroupInfo groupInfo) {
		this.groupInfo = groupInfo;
	}

	public FolderInfo getCurrentFolder() {
		return currentFolder;
	}

	public void setCurrentFolder(FolderInfo currentFolder) {
		this.currentFolder = currentFolder;
	}

	protected FolderInfo retrieveActualFolder() {
		logger.debug("Scheisse die Wand an");
		return documentService.getFolder(groupInfo, currentFolder);
	}	
	
	public List<FolderInfo> getCurrentPath() {
		logger.debug("getting current path:NOW");
		if (currentFolder != null && currentFolder.getId() != null) {
			return documentService.getFolderPath(retrieveActualFolder());
		} else {
			return new ArrayList<FolderInfo>();
		}
	}
}
