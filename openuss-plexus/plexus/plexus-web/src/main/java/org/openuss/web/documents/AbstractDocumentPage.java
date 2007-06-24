package org.openuss.web.documents;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.shale.tiger.managed.Property;
import org.apache.shale.tiger.view.Prerender;
import org.openuss.documents.DocumentService;
import org.openuss.documents.FolderInfo;
import org.openuss.foundation.DomainObject;
import org.openuss.framework.jsfcontrols.breadcrumbs.BreadCrumb;
import org.openuss.system.SystemProperties;
import org.openuss.web.Constants;
import org.openuss.web.PageLinks;
import org.openuss.web.course.AbstractCoursePage;

/**
 * @author Ingo Dueppe
 */
public class AbstractDocumentPage extends AbstractCoursePage {
	private static final Logger logger = Logger.getLogger(AbstractDocumentPage.class);

	@Property(value = "#{documentService}")
	protected DocumentService documentService;
	
	@Property(value = "#{documents_current_folder}")
	protected FolderInfo currentFolder;

	@Property(value = "#{sessionScope.course}")
	protected DomainObject domainObject;
	
	@Prerender
	public void prerender() throws Exception {
		super.prerender();
	
		if (currentFolder == null && domainObject == null) {
			redirect(Constants.OUTCOME_BACKWARD);
		} else {
			retrieveActualFolder();
		}
		setSessionAttribute(Constants.DOCUMENTS_CURRENT_FOLDER, currentFolder);
		addDocumentCrumb();
	}
	
	private void addDocumentCrumb(){
		BreadCrumb documentCrumb = new BreadCrumb();
		documentCrumb.setHint(i18n("course_command_documents"));
		documentCrumb.setName(i18n("course_command_documents"));
		documentCrumb.setLink(getSystemService().getProperty(SystemProperties.OPENUSS_SERVER_URL).getValue()+PageLinks.DOCUMENTS_MAIN+"?course="+course.getId());
		crumbs.add(documentCrumb);		
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
		if (currentFolder == null || currentFolder.getId() == null) {
			currentFolder = documentService.getFolder(domainObject);
		} else {
			currentFolder = documentService.getFolder(currentFolder);
		}
		return currentFolder;
	}	
	
	public List<FolderInfo> getPath() {
		logger.debug("getting current path");
		if (currentFolder != null && currentFolder.getId() != null) {
			return documentService.getFolderPath(currentFolder);
		} else {
			return new ArrayList<FolderInfo>();
		}
	}

	public DomainObject getDomainObject() {
		return domainObject;
	}

	public void setDomainObject(DomainObject domainObject) {
		this.domainObject = domainObject;
	}
}
