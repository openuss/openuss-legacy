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
              
@Bean(name = "views$secured$papersubmission$paperlist", scope = Scope.REQUEST)
@View
public class PaperSubmissionMainPage extends AbstractPaperSubmissionPage {
	
	public static final Logger logger = Logger.getLogger(PaperSubmissionMainPage.class);
	
	@Property(value = "#{documentService}")
	protected DocumentService documentService;
	
	@Property(value= "#{paperSubmissionService}")
	protected PaperSubmissionService paperSubmissionService;
	
	/** The datamodel for all papers. */
	private LocalDataModelExams dataExams = new LocalDataModelExams();

	/** If <code>true</code> the page is in editing mode. */
	private Boolean editing = false;
	

	
	
	
	/** Prepares the information needed for rendering. 
	 * @throws Exception */
	@Prerender
	@SuppressWarnings( { "unchecked" })
	public void prerender() throws Exception {
		super.prerender();
		addPageCrumbs();
		
		if(examInfo.getDeadline()==null){
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
	 * Set selected paper into session scope
	 * 
	 * @return outcome
	 * @throws LectureException
	 */
	public String editExam() throws LectureException {
		examInfo = currentExam();
		if (examInfo == null) {
			return Constants.FAILURE;
		}
		// TODO: implement find paper...
		//paperInfo = courseTypeService.findCourseType(paperInfo.getId());
		//examInfo = new ExamInfo(1l, 1l, "VOFI mit Steuern", "Bauen Sie einen VOFI mit Steuern!");
		setSessionBean(Constants.PAPERSUBMISSION_EXAM_INFO, examInfo);
		if (examInfo == null) {
			addWarning(i18n("error_paper_not_found"));
			return Constants.FAILURE;

		} else {
			logger.debug("selected examInfo " + examInfo.getName());
			editing = true;
			return Constants.SUCCESS;
		}
	}
	
	/**
	 * Saves new paper or updates changes to paper and removes current
	 * paper selection from session scope.
	 * 
	 * @return outcome
	 */
	public String saveExam() throws DesktopException, LectureException {
		logger.debug("Starting method savePaper()");
		// TODO implement save/update
		if (examInfo.getId() == null) {

			examInfo.setDomainId(courseInfo.getId());
			paperSubmissionService.createExam(examInfo);

			addMessage(i18n("papersubmission_message_add_paper_succeed"));
		} else {
			//courseTypeService.update(courseTypeInfo);
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
	 * Store the selected paper into session scope and go to paper
	 * remove confirmation page. 
	 * 
	 * @return outcome
	 */
	public String selectExamAndConfirmRemove() {
		logger.debug("Starting method selectPaperAndConfirmRemove");
		ExamInfo currentExam = currentExam();
		logger.debug("Returning to method selectExamAndConfirmRemove");
		logger.debug(currentExam.getId());
		setSessionBean(Constants.PAPERSUBMISSION_EXAM_INFO, currentExam);

		return Constants.PAPER_CONFIRM_REMOVE_PAGE;
	}
	
	public String selectExam(){
		logger.debug("Starting method selectExam");
		ExamInfo currentExam = currentExam();
		logger.debug("Returning to method selectExam");
		logger.debug(currentExam.getId());
		setSessionBean(Constants.PAPERSUBMISSION_EXAM_INFO, currentExam);

		return Constants.PAPERSUBMISSION_OVERVIEW_PAGE;
	}
	
	//// getter/setter methods ////////////////////////////////////////////////

	private ExamInfo currentExam() {
		ExamInfo exam = this.dataExams.getRowData();
		return exam;
	}
	
	public Boolean getEditing() {
		return editing;
	}
	public void setEditing(Boolean editing) {
		this.editing = editing;
	}
	

	public LocalDataModelExams getDataExams() {
		return dataExams;
	}
	public void setDataExams(LocalDataModelExams dataExams) {
		this.dataExams = dataExams;
	}
	
	/////// Inner classes ////////////////////////////////////////////////////
	
	private class LocalDataModelExams extends AbstractPagedTable<ExamInfo> {
		private static final long serialVersionUID = -6289875618529435428L;

		private DataPage<ExamInfo> page;

		@Override
		@SuppressWarnings( { "unchecked" })
		public DataPage<ExamInfo> getDataPage(int startRow, int pageSize) {
			if (page == null) {
				// TODO: implement!
				/*List<CourseTypeInfo> courseTypes = new ArrayList<CourseTypeInfo>(courseTypeService
						.findCourseTypesByInstitute(instituteInfo.getId()));*/
				List<ExamInfo> exams = new ArrayList<ExamInfo>();
				exams = paperSubmissionService.findExamsByDomainId(courseInfo.getId());
				
				//exams.add(new ExamInfo(1l, 1l, "VOFI ohne Steuern", "Vofi ohne Steuern"));
				
				sort(exams);
				page = new DataPage<ExamInfo>(exams.size(), 0, exams);
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
	
}
