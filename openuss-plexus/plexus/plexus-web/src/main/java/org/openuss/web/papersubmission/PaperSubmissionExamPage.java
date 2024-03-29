package org.openuss.web.papersubmission;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.faces.component.UIData;
import javax.faces.event.ActionEvent;

import org.apache.commons.collections.comparators.ComparatorChain;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.log4j.Logger;
import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Property;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.Prerender;
import org.apache.shale.tiger.view.View;
import org.openuss.desktop.DesktopException;
import org.openuss.documents.DocumentService;
import org.openuss.documents.FileInfo;
import org.openuss.framework.jsfcontrols.breadcrumbs.BreadCrumb;
import org.openuss.framework.web.jsf.model.AbstractPagedTable;
import org.openuss.framework.web.jsf.model.DataPage;
import org.openuss.lecture.LectureException;
import org.openuss.paperSubmission.ExamInfo;
import org.openuss.web.Constants;
import org.openuss.web.upload.UploadFileManager;
import org.springframework.beans.support.PropertyComparator;

/**
 * Backing Bean for examlist.xhtml.
 * @author Projektseminar WS 07/08, Team Collaboration
 *
 */
@Bean(name = "views$secured$papersubmission$examlist", scope = Scope.REQUEST)
@View
public class PaperSubmissionExamPage extends AbstractPaperSubmissionPage {
	
	private static final Logger LOGGER = Logger.getLogger(PaperSubmissionExamPage.class);
	
	/** Data model for active papers. */
	private LocalDataModelActiveExams dataActiveExams = new LocalDataModelActiveExams();
	
	/** Data model for inactive papers. */
	private LocalDataModelInactiveExams dataInactiveExams = new LocalDataModelInactiveExams();
	
	@Property(value = "#{"+Constants.UPLOAD_FILE_MANAGER+"}")
	private UploadFileManager uploadFileManager;

	/** File attachedment list for editing exam */
	private UIData attachmentList;

	/** If <code>true</code> the page is in editing mode. */
	private boolean editing;
	
	/** Prepares the information needed for rendering. 
	 * @throws Exception */
	@Prerender
	public void prerender() throws Exception {
		super.prerender();
		addPageCrumbs();

		if (!editing && examInfo != null && examInfo.getId() != null){
			setExamInfo(paperSubmissionService.getExam(getExamInfo().getId()));
			setBean(Constants.PAPERSUBMISSION_EXAM_INFO, examInfo);
		} 

	}

	/** 
	 * Adds an additional breadcrumb to the course-crumbs.
	 */
	private void addPageCrumbs() {
		BreadCrumb crumb = new BreadCrumb();
		crumb.setLink("");
		crumb.setName(i18n("papersubmission_paperlist_header"));
		crumb.setHint(i18n("papersubmission_paperlist_header"));

		breadcrumbs.loadCourseCrumbs(courseInfo);
		breadcrumbs.addCrumb(crumb);
	}
	
	/**
	 * Creates a new PaperInfo object and sets it into session scope
	 * 
	 * @return outcome
	 */
	public String addExam() {
		editing = true;
		examInfo = new ExamInfo();
		
		Date deadline = DateUtils.truncate(new Date(), Calendar.DATE);
		deadline = DateUtils.addWeeks(deadline, +2);
		deadline = DateUtils.addSeconds(deadline, -1);
		examInfo.setDeadline(deadline);
		
		setBean(Constants.PAPERSUBMISSION_EXAM_INFO, examInfo);
		return Constants.SUCCESS;
	}
	
	/**
	 * Set selected active exam into session scope
	 * 
	 * @return outcome
	 * @throws LectureException
	 */
	public String editActiveExam() throws LectureException {
		examInfo = this.dataActiveExams.getRowData();
		if (examInfo == null) {
			return Constants.FAILURE;
		} else if (examInfo.getId() != null) {
			examInfo = paperSubmissionService.getExam(examInfo.getId());
		}
		setBean(Constants.PAPERSUBMISSION_EXAM_INFO, examInfo);
		
		if (examInfo == null) {
			addWarning(i18n("papersubmission_error_exam_not_found"));
			return Constants.FAILURE;
		} else {
			LOGGER.debug("selected examInfo " + examInfo.getName());
			editing = true;
			return Constants.SUCCESS;
		}
	}
	
	/**
	 * Set selected inactive exam into session scope
	 * 
	 * @return outcome
	 * @throws LectureException
	 */
	public String editInactiveExam() throws LectureException {
		examInfo = this.dataInactiveExams.getRowData();
		if (examInfo == null) {
			return Constants.FAILURE;
		}else if (examInfo.getId()!=null){
			examInfo = paperSubmissionService.getExam(examInfo.getId());
		}
	
		setBean(Constants.PAPERSUBMISSION_EXAM_INFO, examInfo);
		if (examInfo == null) {
			addWarning(i18n("papersubmission_error_exam_not_found"));
			return Constants.FAILURE;
		} else {
			LOGGER.debug("selected examInfo " + examInfo.getName());
			editing = true;
			return Constants.SUCCESS;
		}
	}
	
	/**
	 * Saves new exam or updates changes to paper and removes current
	 * paper selection from session scope.
	 * 
	 * @return outcome
	 */
	public String saveExam() throws DesktopException, LectureException {
		LOGGER.debug("Starting method savePaper()");
		
		if (examInfo.getId() == null) {
			examInfo.setDomainId(courseInfo.getId());
			paperSubmissionService.createExam(examInfo);
			addMessage(i18n("papersubmission_message_add_exam_succeed"));
		} else {
			paperSubmissionService.updateExam(examInfo);
			addMessage(i18n("papersubmission_message_save_exam_succeed"));
		}

		examInfo = null;
		editing = false;
		setBean(Constants.PAPERSUBMISSION_EXAM_INFO, examInfo);

		return Constants.SUCCESS;
	}
	
	/**
	 * Cancels editing or adding of current paper
	 * 
	 * @return outcome
	 */
	public String cancelExam() {
		LOGGER.debug("cancelPaper()");
		examInfo = new ExamInfo();
		editing = false;
		setBean(Constants.PAPERSUBMISSION_EXAM_INFO, examInfo);
		return Constants.SUCCESS;
	}
	
	/**
	 * Store the selected active exam into session scope and go to exam
	 * remove confirmation page. 
	 * 
	 * @return outcome
	 */
	public String selectActiveExamAndConfirmRemove() {
		LOGGER.debug("Starting method selectPaperAndConfirmRemove");
		final ExamInfo currentExam = this.dataActiveExams.getRowData();
		LOGGER.debug("Returning to method selectExamAndConfirmRemove");
		LOGGER.debug(currentExam.getId());
		setBean(Constants.PAPERSUBMISSION_EXAM_INFO, currentExam);

		return Constants.PAPERSUBMISSION_EXAM_REMOVE_PAGE;
	}
	
	/**
	 * Store the selected inactive exam into session scope and go to exam
	 * remove confirmation page. 
	 * 
	 * @return outcome
	 */
	public String selectInactiveExamAndConfirmRemove() {
		LOGGER.debug("Starting method selectPaperAndConfirmRemove");
		final ExamInfo currentExam = this.dataInactiveExams.getRowData();
		LOGGER.debug("Returning to method selectExamAndConfirmRemove");
		LOGGER.debug(currentExam.getId());
		setBean(Constants.PAPERSUBMISSION_EXAM_INFO, currentExam);
		return Constants.PAPERSUBMISSION_EXAM_REMOVE_PAGE;
	}
	
	public String selectActiveExam(){
		LOGGER.debug("Starting method selectExam");
		final ExamInfo currentExam = this.dataActiveExams.getRowData();
		LOGGER.debug("Returning to method selectExam");
		LOGGER.debug(currentExam.getId());
		setBean(Constants.PAPERSUBMISSION_EXAM_INFO, currentExam);
		return Constants.PAPERSUBMISSION_OVERVIEW_PAGE;
	}
	
	public String selectInactiveExam(){
		LOGGER.debug("Starting method selectExam");
		ExamInfo currentExam = this.dataInactiveExams.getRowData();
		LOGGER.debug("Returning to method selectExam");
		LOGGER.debug(currentExam.getId());
		setBean(Constants.PAPERSUBMISSION_EXAM_INFO, currentExam);
		return Constants.PAPERSUBMISSION_OVERVIEW_PAGE;
	}
	
	public String removeAttachment() { 
		LOGGER.debug("exam attachment removed");
		if (examInfo.getAttachments() != null) {
			examInfo.getAttachments().remove((FileInfo) attachmentList.getRowData());
		}
		return Constants.SUCCESS;
	}

	public void addAttachment(ActionEvent actionEvent) throws IOException{
		LOGGER.debug("papersubmission attachment add");
		if (examInfo.getAttachments() == null) {
			examInfo.setAttachments(new ArrayList<FileInfo>());
		}
		
		final FileInfo fileInfo = uploadFileManager.lastUploadAsFileInfo();
		if (fileInfo != null && !examInfo.getAttachments().contains(fileInfo)) {
			if (validFileName(fileInfo.getFileName())) {
				examInfo.getAttachments().add(fileInfo);
			} else {
				addError(i18n("papersubmission_filename_already_exists"));
			}
		}
	}
	
	private boolean validFileName(String fileName) {
		for (FileInfo attachment : examInfo.getAttachments()) {
			if (StringUtils.equalsIgnoreCase(fileName, attachment.getFileName())) {
				return false;
			}
		}
		return true;
	}

	public boolean getEditing() {
		return editing;
	}
	
	public void setEditing(boolean editing) {
		this.editing = editing; 
	}
	
	public DocumentService getDocumentService() {
		return documentService;
	}

	public void setDocumentService(DocumentService documentService) {
		this.documentService = documentService;
	}

	public LocalDataModelInactiveExams getDataInactiveExams() {
		return dataInactiveExams;
	}

	public void setDataInactiveExams(LocalDataModelInactiveExams dataInactiveExams) {
		this.dataInactiveExams = dataInactiveExams;
	}

	public UIData getAttachmentList() {
		return attachmentList;
	}

	public void setAttachmentList(UIData attachmentList) {
		this.attachmentList = attachmentList;
	}

	public UploadFileManager getUploadFileManager() {
		return uploadFileManager;
	}

	public void setUploadFileManager(UploadFileManager uploadFileManager) {
		this.uploadFileManager = uploadFileManager;
	}

	public LocalDataModelActiveExams getDataActiveExams() {
		return dataActiveExams;
	}
	public void setDataActiveExams(LocalDataModelActiveExams dataActiveExams) {
		this.dataActiveExams = dataActiveExams;
	}
	
	private class LocalDataModelActiveExams extends AbstractPagedTable<ExamInfo> {
		private static final long serialVersionUID = -6289875618529435428L;

		private DataPage<ExamInfo> page;

		@SuppressWarnings("unchecked")
		@Override
		public DataPage<ExamInfo> getDataPage(int startRow, int pageSize) {
			if (page == null) {
				
				List<ExamInfo> activeExams = paperSubmissionService.findActiveExamsByDomainId(courseInfo.getId());
				sort(activeExams);
				page = new DataPage<ExamInfo>(activeExams.size(), 0, activeExams);
			}
			return page;
		}
		
		/**
		 * Default property sort method
		 * @param list List of ExamInfo objects.
		 */
		@SuppressWarnings("unchecked") // NOPMD by Administrator on 13.03.08 12:59
		@Override
		protected void sort(List<ExamInfo> list) {
			ComparatorChain chain = new ComparatorChain();
			if (StringUtils.isNotBlank(getSortColumn())) {
				chain.addComparator(new PropertyComparator(getSortColumn(), true, isAscending()));
			} else {
				chain.addComparator(new PropertyComparator("deadline", true, isAscending()));
			}
			Collections.sort(list, chain);
		}	
	}

	
	private class LocalDataModelInactiveExams extends AbstractPagedTable<ExamInfo> {
		private static final long serialVersionUID = -6289875618529435428L;

		private DataPage<ExamInfo> page;

		@Override
		@SuppressWarnings( { "unchecked" }) // NOPMD by Administrator on 13.03.08 12:59
		public DataPage<ExamInfo> getDataPage(int startRow, int pageSize) {
			if (page == null) {
				
				List<ExamInfo> inactiveExams = paperSubmissionService.findInactiveExamsByDomainId(courseInfo.getId());
				sort(inactiveExams);
				page = new DataPage<ExamInfo>(inactiveExams.size(), 0, inactiveExams);
			}
			return page;
		}
		
		/**
		 * Default property sort method
		 * @param list List of ExamInfo objects.
		 */
		@SuppressWarnings("unchecked") // NOPMD by Administrator on 13.03.08 12:59
		@Override
		protected void sort(List<ExamInfo> list) {
			ComparatorChain chain = new ComparatorChain();
			if (StringUtils.isNotBlank(getSortColumn())) {
				chain.addComparator(new PropertyComparator(getSortColumn(), true, isAscending()));
			} else {
				chain.addComparator(new PropertyComparator("deadline", true, isAscending()));
			}
			Collections.sort(list, chain);
		}
	}
}
