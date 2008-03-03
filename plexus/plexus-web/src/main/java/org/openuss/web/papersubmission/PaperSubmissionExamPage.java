package org.openuss.web.papersubmission;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Property;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.Prerender;
import org.apache.shale.tiger.view.View;
import org.openuss.desktop.DesktopException;
import org.openuss.documents.DocumentService;
import org.openuss.framework.jsfcontrols.breadcrumbs.BreadCrumb;
import org.openuss.framework.web.jsf.model.AbstractPagedTable;
import org.openuss.framework.web.jsf.model.DataPage;
import org.openuss.lecture.LectureException;
import org.openuss.paperSubmission.ExamInfo;
import org.openuss.paperSubmission.PaperSubmissionService;
import org.openuss.web.Constants;
import org.openuss.web.course.AbstractCoursePage;
              
@Bean(name = "views$secured$papersubmission$examlist", scope = Scope.REQUEST)
@View
public class PaperSubmissionExamPage extends AbstractPaperSubmissionPage {
	
	public static final Logger logger = Logger.getLogger(PaperSubmissionExamPage.class);
	
	@Property(value = "#{documentService}")
	protected DocumentService documentService;
	
	/** The datamodel for all papers. */
	private LocalDataModelActiveExams dataActiveExams = new LocalDataModelActiveExams();
	
	private LocalDataModelInactiveExams dataInactiveExams = new LocalDataModelInactiveExams();

	/** If <code>true</code> the page is in editing mode. */
	private Boolean editing = false;
		
	/** Prepares the information needed for rendering. 
	 * @throws Exception */
	@Prerender
	@SuppressWarnings( { "unchecked" })
	public void prerender() throws Exception {
		super.prerender();
		addPageCrumbs();
		
		if(examInfo!= null && examInfo.getDeadline()==null){
			examInfo.setDeadline(new Date());
		}
	}

	/** Adds an additional breadcrumb to the course-crumbs.
	 * 
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
		setSessionBean(Constants.PAPERSUBMISSION_EXAM_INFO, examInfo);
		return Constants.SUCCESS;
	}
	
	/**
	 * Set selected active exam into session scope
	 * 
	 * @return outcome
	 * @throws LectureException
	 */
	public String editActiveExam() throws LectureException {
		examInfo = currentActiveExam();
		if (examInfo == null) {
			return Constants.FAILURE;
		}
		setSessionBean(Constants.PAPERSUBMISSION_EXAM_INFO, examInfo);
		if (examInfo == null) {
			//TODO change message name
			addWarning(i18n("error_paper_not_found"));
			return Constants.FAILURE;

		} else {
			logger.debug("selected examInfo " + examInfo.getName());
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
		examInfo = currentInactiveExam();
		if (examInfo == null) {
			return Constants.FAILURE;
		}
		setSessionBean(Constants.PAPERSUBMISSION_EXAM_INFO, examInfo);
		if (examInfo == null) {
			//TODO change message name
			addWarning(i18n("error_paper_not_found"));
			return Constants.FAILURE;

		} else {
			logger.debug("selected examInfo " + examInfo.getName());
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
		logger.debug("Starting method savePaper()");
		// TODO implement save/update

		Date currentDate = new Date();
		if (examInfo.getDeadline().before(currentDate)) {
			addError(i18n("papersubmission_illegal_exam_message"));
			return Constants.SUCCESS;
		}

		if (examInfo.getId() == null) {
			examInfo.setDomainId(courseInfo.getId());
			paperSubmissionService.createExam(examInfo);

			addMessage(i18n("papersubmission_message_add_paper_succeed"));
		} else {
			// courseTypeService.update(courseTypeInfo);
			paperSubmissionService.updateExam(examInfo);

			addMessage(i18n("papersubmission_message_persist_paper_succeed"));
		}

		removeSessionBean(Constants.PAPERSUBMISSION_EXAM_INFO);
		examInfo = null;
		editing = false;

		return Constants.SUCCESS;
	}
	
	/**
	 * Cancels editing or adding of current paper
	 * 
	 * @return outcome
	 */
	public String cancelExam() {
		logger.debug("cancelPaper()");
		removeSessionBean(Constants.PAPERSUBMISSION_EXAM_INFO);
		this.editing = false;
		return Constants.SUCCESS;
	}
	
	/**
	 * Store the selected active exam into session scope and go to exam
	 * remove confirmation page. 
	 * 
	 * @return outcome
	 */
	public String selectActiveExamAndConfirmRemove() {
		logger.debug("Starting method selectPaperAndConfirmRemove");
		ExamInfo currentExam = currentActiveExam();
		logger.debug("Returning to method selectExamAndConfirmRemove");
		logger.debug(currentExam.getId());
		setSessionBean(Constants.PAPERSUBMISSION_EXAM_INFO, currentExam);

		return Constants.PAPERSUBMISSION_EXAM_REMOVE_PAGE;
	}
	
	/**
	 * Store the selected inactive exam into session scope and go to exam
	 * remove confirmation page. 
	 * 
	 * @return outcome
	 */
	public String selectInactiveExamAndConfirmRemove() {
		logger.debug("Starting method selectPaperAndConfirmRemove");
		ExamInfo currentExam = currentInactiveExam();
		logger.debug("Returning to method selectExamAndConfirmRemove");
		logger.debug(currentExam.getId());
		setSessionBean(Constants.PAPERSUBMISSION_EXAM_INFO, currentExam);

		return Constants.PAPERSUBMISSION_EXAM_REMOVE_PAGE;
	}
	
	public String selectActiveExam(){
		logger.debug("Starting method selectExam");
		ExamInfo currentExam = currentActiveExam();
		logger.debug("Returning to method selectExam");
		logger.debug(currentExam.getId());
		setSessionBean(Constants.PAPERSUBMISSION_EXAM_INFO, currentExam);

		return Constants.PAPERSUBMISSION_OVERVIEW_PAGE;
	}
	
	public String selectInactiveExam(){
		logger.debug("Starting method selectExam");
		ExamInfo currentExam = currentInactiveExam();
		logger.debug("Returning to method selectExam");
		logger.debug(currentExam.getId());
		setSessionBean(Constants.PAPERSUBMISSION_EXAM_INFO, currentExam);

		return Constants.PAPERSUBMISSION_OVERVIEW_PAGE;
	}
	
	//// getter/setter methods ////////////////////////////////////////////////

	private ExamInfo currentActiveExam() {
		ExamInfo exam = this.dataActiveExams.getRowData();
		
		return exam;
	}
	
	private ExamInfo currentInactiveExam() {
		ExamInfo exam = this.dataInactiveExams.getRowData();
		
		return exam;
	}
	
	public Boolean getEditing() {
		return editing;
	}
	public void setEditing(Boolean editing) {
		this.editing = editing;
	}
	

	public LocalDataModelActiveExams getDataActiveExams() {
		return dataActiveExams;
	}
	public void setDataActiveExams(LocalDataModelActiveExams dataActiveExams) {
		this.dataActiveExams = dataActiveExams;
	}
	
	/////// Inner classes ////////////////////////////////////////////////////
	
	private class LocalDataModelActiveExams extends AbstractPagedTable<ExamInfo> {
		private static final long serialVersionUID = -6289875618529435428L;

		private DataPage<ExamInfo> page;

		@Override
		@SuppressWarnings( { "unchecked" })
		public DataPage<ExamInfo> getDataPage(int startRow, int pageSize) {
			if (page == null) {
				
				List<ExamInfo> activeExams = new ArrayList<ExamInfo>();
				activeExams = paperSubmissionService.findActiveExamsByDomainId(courseInfo.getId());
				
				//exams.add(new ExamInfo(1l, 1l, "VOFI ohne Steuern", "Vofi ohne Steuern"));
				
				setSortColumn("deadline");
				sort(activeExams);
				page = new DataPage<ExamInfo>(activeExams.size(), 0, activeExams);
			}
			return page;
		}
	}

	
	private class LocalDataModelInactiveExams extends AbstractPagedTable<ExamInfo> {
		private static final long serialVersionUID = -6289875618529435428L;

		private DataPage<ExamInfo> page;

		@Override
		@SuppressWarnings( { "unchecked" })
		public DataPage<ExamInfo> getDataPage(int startRow, int pageSize) {
			if (page == null) {
				
				List<ExamInfo> inactiveExams = new ArrayList<ExamInfo>();
				inactiveExams = paperSubmissionService.findInactiveExamsByDomainId(courseInfo.getId());
				
				//exams.add(new ExamInfo(1l, 1l, "VOFI ohne Steuern", "Vofi ohne Steuern"));
				
				setSortColumn("deadline");
				sort(inactiveExams);
				page = new DataPage<ExamInfo>(inactiveExams.size(), 0, inactiveExams);
			}
			return page;
		}
	}
	public DocumentService getDocumentService() {
		return documentService;
	}

	public void setDocumentService(DocumentService documentService) {
		this.documentService = documentService;
	}

	public ExamInfo getExamInfo() {
		return examInfo;
	}

	public void setExamInfo(ExamInfo examInfo) {
		this.examInfo = examInfo;
	}

	public LocalDataModelInactiveExams getDataInactiveExams() {
		return dataInactiveExams;
	}

	public void setDataInactiveExams(LocalDataModelInactiveExams dataInactiveExams) {
		this.dataInactiveExams = dataInactiveExams;
	}
	
}
