package org.openuss.web.papersubmission;

import java.util.List;

import org.apache.shale.tiger.managed.Property;
import org.openuss.documents.DocumentService;
import org.openuss.documents.FileInfo;
import org.openuss.paperSubmission.ExamInfo;
import org.openuss.paperSubmission.PaperSubmissionInfo;
import org.openuss.paperSubmission.PaperSubmissionService;
import org.openuss.paperSubmission.SubmissionStatus;
import org.openuss.security.Roles;
import org.openuss.security.SecurityService;
import org.openuss.security.acl.LectureAclEntry;
import org.openuss.web.Constants;
import org.openuss.web.course.AbstractCoursePage;

public abstract class AbstractPaperSubmissionPage extends AbstractCoursePage {
	
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
	
	@Override
	public void prerender() throws Exception {
		super.prerender();
		
		if(this.paperSubmissionInfo!=null && this.paperSubmissionInfo.getId() != null && paperSubmissionInfo.getExamId() != null){
			this.paperSubmissionInfo = paperSubmissionService.getPaperSubmission(paperSubmissionInfo.getId());
		}
		
		if (this.examInfo != null && this.examInfo.getId() != null) {
			this.examInfo = paperSubmissionService.getExam(examInfo.getId());
		}
		
		
	}
	
	@SuppressWarnings("unchecked")
	protected PaperSubmissionInfo loadPaperSubmission(){
		
		examInfo = (ExamInfo) getSessionBean(Constants.PAPERSUBMISSION_EXAM_INFO);
				
		final List<PaperSubmissionInfo> paperInfos = (List<PaperSubmissionInfo>) paperSubmissionService.findPaperSubmissionsByExamAndUser(examInfo.getId(), user.getId());
		
		if(paperInfos.isEmpty()){
			//Create a submission, if the user doesn't have any
			PaperSubmissionInfo SubmissionInfo = new PaperSubmissionInfo();
			SubmissionInfo.setExamId(examInfo.getId());
			SubmissionInfo.setUserId(user.getId());
			paperSubmissionService.createPaperSubmission(SubmissionInfo);
			
			//load the submission which has just been created into the session
			paperSubmissionInfo = paperSubmissionService.getPaperSubmission(SubmissionInfo.getId());
			setSessionBean(Constants.PAPERSUBMISSION_PAPER_INFO, paperSubmissionInfo);
			
			if(SubmissionStatus.IN_TIME.equals(paperSubmissionInfo.getSubmissionStatus())){
				addMessage(i18n("papersubmission_message_papersubmission_intime"));
			} else{
				addWarning(i18n("papersubmission_message_papersubmission_notintime"));
			}
		} else{
			//either update or creating a new submission
			paperSubmissionInfo = paperSubmissionService.updatePaperSubmission(paperInfos.get(paperInfos.size()-1));
			paperSubmissionInfo = paperSubmissionService.getPaperSubmission(paperSubmissionInfo.getId());
			
			if(SubmissionStatus.IN_TIME.equals(paperSubmissionInfo.getSubmissionStatus())){
				addMessage(i18n("papersubmission_message_papersubmission_intime"));
			} else {
				addError(i18n("papersubmission_message_papersubmission_notintime"));
			}

			setSessionBean(Constants.PAPERSUBMISSION_PAPER_INFO, paperSubmissionInfo);
		}
		return paperSubmissionInfo;
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
