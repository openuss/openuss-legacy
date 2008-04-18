package org.openuss.web.papersubmission;

import java.util.List;

import org.apache.shale.tiger.managed.Property;
import org.openuss.documents.DocumentService;
import org.openuss.paperSubmission.ExamInfo;
import org.openuss.paperSubmission.PaperSubmissionInfo;
import org.openuss.paperSubmission.PaperSubmissionService;
import org.openuss.paperSubmission.SubmissionStatus;
import org.openuss.security.SecurityService;
import org.openuss.web.Constants;
import org.openuss.web.course.AbstractCoursePage;

public abstract class AbstractPaperSubmissionPage extends AbstractCoursePage {
	
	@Property(value = "#{documentService}")
	protected DocumentService documentService;
	
	@Property(value= "#{paperSubmissionService}")
	protected PaperSubmissionService paperSubmissionService;

	@Property (value="#{securityService}")
	private SecurityService securityService;

	/** paper that is currently selected paper. */
	@Property(value="#{"+Constants.PAPERSUBMISSION_PAPER_INFO+"}")
	protected PaperSubmissionInfo paperSubmissionInfo;
	
	/** exam that is currently selected exam */
	@Property(value="#{"+Constants.PAPERSUBMISSION_EXAM_INFO+"}")
	protected ExamInfo examInfo;
	
	@Override
	public void prerender() throws Exception { 
		super.prerender();
	}

	protected void refreshExamInfoBean() {
		if (examInfo != null && examInfo.getId() != null) {
			examInfo = paperSubmissionService.getExam(examInfo.getId());
			setBean(Constants.PAPERSUBMISSION_EXAM_INFO, examInfo);
		}
	}

	protected void refreshPaperInfoBean() {
		if(paperSubmissionInfo!=null && paperSubmissionInfo.getId() != null){
			paperSubmissionInfo = paperSubmissionService.getPaperSubmission(paperSubmissionInfo.getId());
			setBean(Constants.PAPERSUBMISSION_PAPER_INFO, paperSubmissionInfo);
		}
	}
	
	@SuppressWarnings("unchecked")
	protected PaperSubmissionInfo loadPaperSubmission() {
		examInfo = (ExamInfo) getBean(Constants.PAPERSUBMISSION_EXAM_INFO);
				
		final List<PaperSubmissionInfo> paperInfos = (List<PaperSubmissionInfo>) paperSubmissionService.findPaperSubmissionsByExamAndUser(examInfo.getId(), user.getId());
		
		if (paperInfos.isEmpty()){
			//Create a submission, if the user doesn't have any
			final PaperSubmissionInfo SubmissionInfo = new PaperSubmissionInfo();
			SubmissionInfo.setExamId(examInfo.getId());
			SubmissionInfo.setUserId(user.getId());
			paperSubmissionService.createPaperSubmission(SubmissionInfo);
			
			//load the submission which has just been created into the session
			paperSubmissionInfo = paperSubmissionService.getPaperSubmission(SubmissionInfo.getId());
			setBean(Constants.PAPERSUBMISSION_PAPER_INFO, paperSubmissionInfo);
		} else{
			//either update or creating a new submission
			paperSubmissionInfo = paperSubmissionService.updatePaperSubmission(paperInfos.get(paperInfos.size()-1), true);
			paperSubmissionInfo = paperSubmissionService.getPaperSubmission(paperSubmissionInfo.getId());
			setBean(Constants.PAPERSUBMISSION_PAPER_INFO, paperSubmissionInfo);
		}
		return paperSubmissionInfo;
	}
	
	protected void checkSubmissionStatus(PaperSubmissionInfo paperSubmissionInfo) {
		if(SubmissionStatus.IN_TIME.equals(paperSubmissionInfo.getSubmissionStatus())){
			addMessage(i18n("papersubmission_message_papersubmission_intime"));
		} else{
			addWarning(i18n("papersubmission_message_papersubmission_notintime"));
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

	public void setPaperSubmissionService(PaperSubmissionService paperSubmissionService) {
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

}
