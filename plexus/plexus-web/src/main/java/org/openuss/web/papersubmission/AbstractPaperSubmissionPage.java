package org.openuss.web.papersubmission;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.shale.tiger.managed.Property;
import org.openuss.paperSubmission.ExamInfo;
import org.openuss.paperSubmission.PaperSubmissionInfo;
import org.openuss.paperSubmission.PaperSubmissionService;
import org.openuss.collaboration.WorkspaceInfo;
import org.openuss.collaboration.WorkspaceService;
import org.openuss.documents.DocumentService;
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
	

	/** paper that is currently edited. */
	@Property(value="#{"+Constants.PAPERSUBMISSION_PAPER_INFO+"}")
	protected PaperSubmissionInfo paperSubmissionInfo = null;
	
	/** exam that is currently edited. */
	@Property(value="#{"+Constants.PAPERSUBMISSION_EXAM_INFO+"}")
	protected ExamInfo examInfo = null;
	
	@Property(value = "#{papersubmission_current_folder}")
	protected FolderInfo currentFolder;
	
	
	@Override
	public void prerender() throws Exception {
		super.prerender();
		
//		if (this.examInfo != null && this.examInfo.getId() != null) {
//			this.examInfo = paperSubmissionService.getExam(examInfo.getId());
//		}
	}
	public DocumentService getDocumentService() {
		return documentService;
	}
	public void setDocumentService(DocumentService documentService) {
		this.documentService = documentService;
	}
	
//	public FolderInfo getCurrentFolder() {
//		return currentFolder;
//	}
//	public void setCurrentFolder(FolderInfo currentFolder) {
//		this.currentFolder = currentFolder;
//	}
	
	
	
//	@SuppressWarnings("unchecked")
//	public List<FolderInfo> getCurrentPath() {
//		logger.debug("getting current path");
//		if (currentFolder != null && currentFolder.getId() != null) {
//			return documentService.getFolderPath(retrieveActualFolder());
//		} else {
//			return new ArrayList<FolderInfo>();
//		}
//	}
	
	protected FolderInfo retrieveActualFolder() {
		return documentService.getFolder(paperSubmissionInfo, currentFolder);
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
	public FolderInfo getCurrentFolder() {
		return currentFolder;
	}
	public void setCurrentFolder(FolderInfo currentFolder) {
		this.currentFolder = currentFolder;
	}
}
