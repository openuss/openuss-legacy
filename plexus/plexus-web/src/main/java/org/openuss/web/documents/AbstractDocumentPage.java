package org.openuss.web.documents;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.shale.tiger.managed.Property;
import org.apache.shale.tiger.view.Prerender;
import org.openuss.documents.DocumentService;
import org.openuss.documents.FolderInfo;
import org.openuss.framework.jsfcontrols.breadcrumbs.BreadCrumb;
import org.openuss.web.Constants;
import org.openuss.web.PageLinks;
import org.openuss.web.course.AbstractCoursePage;

/**
 * @author Ingo Dueppe
 * @author Sebastian Roekens
 * 
 */
public class AbstractDocumentPage extends AbstractCoursePage {
	private static final Logger logger = Logger.getLogger(AbstractDocumentPage.class);

	@Property(value = "#{documentService}")
	protected DocumentService documentService;
	
	@Property(value = "#{documents_current_folder}")
	protected FolderInfo currentFolder;

	@Prerender
	public void prerender() throws Exception {
		super.prerender();
		if (isRedirected()){
			return;
		}
		if (currentFolder == null && courseInfo == null) {
			redirect(Constants.OUTCOME_BACKWARD);
			return;
		} else {
			currentFolder = retrieveActualFolder();
		}
		setBean(Constants.DOCUMENTS_CURRENT_FOLDER, currentFolder);
		addDocumentsCrumbs();
	}

	public void addDocumentsCrumbs(){
		breadcrumbs.loadCourseCrumbs(courseInfo);
		
		for(FolderInfo folder : getCurrentPath()) {
			BreadCrumb crumb = new BreadCrumb();
			if (folder.isRoot()) {
				crumb.setName(i18n("documents_main_header"));
				crumb.setHint(i18n("documents_root_folder"));
			} else {
				crumb.setName(folder.getName());
				crumb.setHint(folder.getDescription());
			}
			crumb.setLink(PageLinks.DOCUMENTS_MAIN);
			crumb.addParameter("course",courseInfo.getId());
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

	public FolderInfo getCurrentFolder() {
		return currentFolder;
	}

	public void setCurrentFolder(FolderInfo currentFolder) {
		this.currentFolder = currentFolder;
	}

	protected FolderInfo retrieveActualFolder() {
		return documentService.getFolder(courseInfo, currentFolder);
	}	
	
	@SuppressWarnings("unchecked")
	public List<FolderInfo> getCurrentPath() {
		logger.debug("getting current path");
		if (currentFolder != null && currentFolder.getId() != null) {
			return documentService.getFolderPath(retrieveActualFolder());
		} else {
			return new ArrayList<FolderInfo>();
		}
	}
}
