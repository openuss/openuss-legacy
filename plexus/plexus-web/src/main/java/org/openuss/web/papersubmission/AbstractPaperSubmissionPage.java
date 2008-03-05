package org.openuss.web.papersubmission;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.shale.tiger.managed.Property;
import org.openuss.paperSubmission.ExamInfo;
import org.openuss.paperSubmission.PaperSubmissionInfo;
import org.openuss.paperSubmission.PaperSubmissionService;
import org.openuss.paperSubmission.SubmissionStatus;
import org.openuss.security.Roles;
import org.openuss.security.SecurityService;
import org.openuss.security.acl.LectureAclEntry;
import org.openuss.collaboration.WorkspaceInfo;
import org.openuss.collaboration.WorkspaceService;
import org.openuss.documents.DocumentService;
import org.openuss.documents.FileInfo;
import org.openuss.documents.FolderInfo;
import org.openuss.web.Constants;
import org.openuss.web.collaboration.AbstractCollaborationPage;
import org.openuss.web.course.AbstractCoursePage;


public abstract class AbstractPaperSubmissionPage extends AbstractCoursePage {

	public static final Logger logger = Logger.getLogger(AbstractPaperSubmissionPage.class);
	
	@Property(value = "#{documentService}")
	protected DocumentService documentService;
	
	@Property(value= "#{paperSubmissionService}")
	protected PaperSubmissionService paperSubmissionService;

	@Property (value="#{securityService}")
	private SecurityService securityService;

	/** paper that is currently edited. */
	@Property(value="#{"+Constants.PAPERSUBMISSION_PAPER_INFO+"}")
	protected PaperSubmissionInfo paperSubmissionInfo = null;
	
	/** exam that is currently edited. */
	@Property(value="#{"+Constants.PAPERSUBMISSION_EXAM_INFO+"}")
	protected ExamInfo examInfo = null;
	
//	@Property(value = "#{papersubmission_current_folder}")
//	protected FolderInfo currentFolder;
	
	
	@Override
	public void prerender() throws Exception {
		if (courseInfo == null || courseInfo.getId() == null) {
			addError(i18n("message_error_course_page"));
			redirect(Constants.OUTCOME_BACKWARD);
			return;
		}
		
		super.prerender();
		
		if(this.paperSubmissionInfo!=null && this.paperSubmissionInfo.getId() != null){
			this.paperSubmissionInfo = paperSubmissionService.getPaperSubmission(paperSubmissionInfo.getId());
		}
		
		if (this.examInfo != null && this.examInfo.getId() != null) {
			this.examInfo = paperSubmissionService.getExam(examInfo.getId());
		}
		
		
	}
	
	protected PaperSubmissionInfo loadPaperSubmission(){
		//paperSubmissionInfo = new PaperSubmissionInfo();
		List<PaperSubmissionInfo> paperInfos;
		examInfo = (ExamInfo) getSessionBean(Constants.PAPERSUBMISSION_EXAM_INFO);
				
		paperInfos = (List<PaperSubmissionInfo>) paperSubmissionService.findPaperSubmissionsByExamAndUser(examInfo.getId(), user.getId());
		if(paperInfos.isEmpty()){
			PaperSubmissionInfo SubmissionInfo = new PaperSubmissionInfo();
			SubmissionInfo.setExamId(examInfo.getId());
			SubmissionInfo.setUserId(user.getId());
			paperSubmissionService.createPaperSubmission(SubmissionInfo);
			paperSubmissionInfo = paperSubmissionService.getPaperSubmission(SubmissionInfo.getId());
			
			setSessionBean(Constants.PAPERSUBMISSION_PAPER_INFO, paperSubmissionInfo);
			
			if(SubmissionStatus.IN_TIME.equals(paperSubmissionInfo.getSubmissionStatus())){
				addMessage(i18n("papersubmission_message_papersubmission_intime"));
			} else{
				addWarning(i18n("papersubmission_message_papersubmission_notintime"));
			}
			
			return paperSubmissionInfo;
		}
		else{
			paperSubmissionInfo = paperSubmissionService.updatePaperSubmission(paperInfos.get(paperInfos.size()-1));
			paperSubmissionInfo = paperSubmissionService.getPaperSubmission(paperSubmissionInfo.getId());
			
			if(SubmissionStatus.IN_TIME.equals(paperSubmissionInfo.getSubmissionStatus())){
				addMessage(i18n("papersubmission_message_papersubmission_intime"));
			} else {
				addError(i18n("papersubmission_message_papersubmission_notintime"));
			}

			setSessionBean(Constants.PAPERSUBMISSION_PAPER_INFO, paperSubmissionInfo);
			return paperSubmissionInfo;
		}
}
	
	public DocumentService getDocumentService() {
		return documentService;
	}
	public void setDocumentService(DocumentService documentService) {
		this.documentService = documentService;
	}
	

	public PaperSubmissionService getPaperSubmissionService() {
		return paperSubmissionService;
	}

	public void setPaperSubmissionService(
			PaperSubmissionService paperSubmissionService) {
		this.paperSubmissionService = paperSubmissionService;
	}
	public ExamInfo getExamInfo() {
		return examInfo;
	}
	public void setExamInfo(ExamInfo examInfo) {
		this.examInfo = examInfo;
	}
	public PaperSubmissionInfo getPaperSubmissionInfo() {
		return paperSubmissionInfo;
	}
	public void setPaperSubmissionInfo(PaperSubmissionInfo paperSubmissionInfo) {
		this.paperSubmissionInfo = paperSubmissionInfo;
	}
	
	public SecurityService getSecurityService() {
		return securityService;
	}
	public void setSecurityService(SecurityService securityService) {
		this.securityService = securityService;
	}

	protected void permitRolesImageReadPermission(FileInfo imageFile) {
		// TODO should be done within the business layer
		securityService.setPermissions(Roles.ANONYMOUS, imageFile, LectureAclEntry.READ);
		securityService.setPermissions(Roles.USER, imageFile, LectureAclEntry.READ);
	}
}
