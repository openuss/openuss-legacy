package org.openuss.web.lecture;

import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Property;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.Prerender;
import org.apache.shale.tiger.view.View;
import org.openuss.documents.DocumentService;
import org.openuss.framework.jsfcontrols.breadcrumbs.BreadCrumb;
import org.openuss.lecture.LectureException;
import org.openuss.security.SecurityService;
import org.openuss.web.Constants;
import org.openuss.web.upload.UploadFileManager;

/**
 * 
 * @author Kai Stettner
 * @author Malte Stockmann
 */
@Bean(name = "views$secured$lecture$departmentoptions", scope = Scope.REQUEST)
@View
public class DepartmentOptionsPage extends AbstractDepartmentPage {

	private static final long serialVersionUID = -202799999652385870L;
	
	@Property (value="#{securityService}")
	private SecurityService securityService;
	
	@Property (value="#{documentService}")
	private DocumentService documentService;
	
	@Property(value = "#{uploadFileManager}")
	private UploadFileManager uploadFileManager;
	
	@Prerender
	public void prerender() throws LectureException {
		super.prerender();
		addPageCrumb();
	}
	
	private void addPageCrumb() {
		BreadCrumb crumb = new BreadCrumb();
		crumb.setLink("");
		crumb.setName(i18n("department_command_settings"));
		crumb.setHint(i18n("department_command_settings"));
		crumbs.add(crumb);
		setSessionBean(Constants.BREADCRUMBS, crumbs);
	}	
	
	/**
	 * Save department options.
	 * @return outcome
	 * @throws LectureException
	 */
	public String saveDepartment() throws LectureException {
		departmentService.update(departmentInfo);
		addMessage(i18n("department_message_command_save_succeed"));
		return Constants.SUCCESS;
	}
	
	public DocumentService getDocumentService() {
		return documentService;
	}

	public void setDocumentService(DocumentService documentService) {
		this.documentService = documentService;
	}

	public UploadFileManager getUploadFileManager() {
		return uploadFileManager;
	}

	public void setUploadFileManager(UploadFileManager uploadFileManager) {
		this.uploadFileManager = uploadFileManager;
	}

	public SecurityService getSecurityService() {
		return securityService;
	}

	public void setSecurityService(SecurityService securityService) {
		this.securityService = securityService;
	}

}