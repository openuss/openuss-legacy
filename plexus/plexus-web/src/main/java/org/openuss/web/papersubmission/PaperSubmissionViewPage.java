package org.openuss.web.papersubmission;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.log4j.Logger;
import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Property;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.Prerender;
import org.apache.shale.tiger.view.View;
import org.openuss.documents.FileInfo;
import org.openuss.documents.FolderEntryInfo;
import org.openuss.documents.FolderInfo;
import org.openuss.documents.FileEntryDao;
import org.openuss.framework.jsfcontrols.breadcrumbs.BreadCrumb;
import org.openuss.framework.web.jsf.model.AbstractPagedTable;
import org.openuss.framework.web.jsf.model.DataPage;
import org.openuss.lecture.CourseMemberInfo;
import org.openuss.paperSubmission.ExamInfo;
import org.openuss.paperSubmission.PaperSubmission;
import org.openuss.paperSubmission.PaperSubmissionInfo;
import org.openuss.web.Constants;
import org.openuss.web.PageLinks;
import org.openuss.web.collaboration.WorkspaceMemberSelection;
import org.openuss.web.course.AbstractCoursePage;
import org.openuss.web.documents.FolderEntrySelection;

@Bean(name = "views$secured$papersubmission$paperview", scope = Scope.REQUEST)
@View
public class PaperSubmissionViewPage extends AbstractPaperSubmissionPage {
	
	public static final Logger logger = Logger.getLogger(PaperSubmissionViewPage.class);
	
//	@Property(value = "#{" + Constants.PAPERSUBMISSION_FOLDERENTRY_SELECTION + "}")
//	private FolderEntrySelection entrySelection;
	
	/** The datamodel for all submissions. */
	private LocalDataModelSubmissions dataSubmissions = new LocalDataModelSubmissions();
	
	/** The datamodel for all submission files. */
	private LocalDataModelSubmissionFiles dataSubmissionFiles = new LocalDataModelSubmissionFiles();
	
	private List<PaperSubmissionInfo> submissions;
	
	private List<FolderEntryInfo> entries;
	
	@Property(value = "#{" + Constants.PAPERSUBMISSION_SUBMISSION_SELECTION + "}")
	private PaperSubmissionSelection paperSelection;
	
	/** Prepares the information needed for rendering. 
	 * @throws Exception */
	@Prerender
	public void prerender() throws Exception {
		super.prerender();
		paperSubmissionInfo = getCurrentPaperSubmission();
		
		paperSelection.processSwitch();
	
		addPageCrumbs();
	}

	private PaperSubmissionInfo getCurrentPaperSubmission() {
		List<PaperSubmissionInfo> paperInfos;
		examInfo = (ExamInfo) getSessionBean(Constants.PAPERSUBMISSION_EXAM_INFO);
		paperInfos = paperSubmissionService.findPaperSubmissionsByExamAndUser(examInfo.getId(), user.getId());
		
		if(!paperInfos.isEmpty()){
			paperSubmissionInfo = paperInfos.get(paperInfos.size()-1);
			setSessionBean(Constants.PAPERSUBMISSION_PAPER_INFO, paperSubmissionInfo);
			return paperSubmissionInfo;
		}
		else{
			return null;
		}
		
	}

	/** Adds an additional breadcrumb to the course-crumbs.
	 * 
	 */
	private void addPageCrumbs() {
		BreadCrumb crumb = new BreadCrumb();
		crumb.setLink(PageLinks.PAPERSUBMISSION_EXAM);
		crumb.setName(i18n("papersubmission_paperlist_header"));
		crumb.setHint(i18n("papersubmission_paperlist_header"));

		breadcrumbs.loadCourseCrumbs(courseInfo);
		breadcrumbs.addCrumb(crumb);
		
		crumb = new BreadCrumb();
		crumb.setName(examInfo.getName());
		crumb.setHint(examInfo.getName());
		
		if(courseInfo != null && courseInfo.getId() != null 
				&& examInfo != null && examInfo.getId() != null){
			
			crumb.setLink(PageLinks.PAPERSUBMISSION_PAPERVIEW);
			crumb.addParameter("course",courseInfo.getId());
			crumb.addParameter("exam",examInfo.getId());
		}
		
		breadcrumbs.addCrumb(crumb);
	}
	
	

	private List<FolderEntryInfo> selectedEntries() {
		List<FolderEntryInfo> selected = new ArrayList<FolderEntryInfo>(loadFileEntries());
		CollectionUtils.filter(selected, new Predicate() {
			public boolean evaluate(Object object) {
				return paperSelection.isSelected(object);
			}
		});
		logger.debug("selected " + selected.size() + " files");
		return selected;
	}
	
	@SuppressWarnings("unchecked")
	public String download () throws IOException{
		logger.debug("downloading documents");
		List<FolderEntryInfo> files = documentService.allFileEntries(selectedEntries());
		if (files.size() > 0) {
			setSessionBean(Constants.DOCUMENTS_SELECTED_FILEENTRIES, files);
			HttpServletResponse response = getResponse();
			response.sendRedirect(getExternalContext().getRequestContextPath() + Constants.ZIP_DOWNLOAD_URL);
			getFacesContext().responseComplete();
			paperSelection.getMap().clear();
		} else {
			addError(i18n("messages_error_no_documents_selected"));
		}
		return Constants.SUCCESS;
	}
	

	public String downloadSubmission () throws IOException{
		logger.debug("Downloading selected Submissions.");
		
		List<PaperSubmissionInfo> submissions = selectedSubmissions();
		processDownloadSubmissions(submissions);
		
		return Constants.SUCCESS;
	}
	
	@SuppressWarnings("unchecked")
	public String downloadAllSubmissions () throws IOException{
		logger.debug("Downloading all Submissions.");
		
		List<PaperSubmissionInfo> submissions = paperSubmissionService.findPaperSubmissionsByExam(examInfo.getId());
		processDownloadSubmissions(submissions);
		
		return Constants.SUCCESS;
	}
	
	@SuppressWarnings("unchecked")
	public String downloadInTimeSubmissions () throws IOException{
		logger.debug("Downloading all in time Submissions.");
		
		List<PaperSubmissionInfo> submissions = paperSubmissionService.findInTimePaperSubmissionsByExam(examInfo.getId());
		processDownloadSubmissions(submissions);
		
		return Constants.SUCCESS;
	}
	
	@SuppressWarnings("unchecked")
	private void processDownloadSubmissions(List<PaperSubmissionInfo> submissions) throws IOException {
		List<FolderEntryInfo> files = paperSubmissionService.getPaperSubmissions(submissions, examInfo.getId());
		if (files.size() > 0) {
			setSessionBean(Constants.DOCUMENTS_SELECTED_FILEENTRIES, files);
			HttpServletResponse response = getResponse();
			response.sendRedirect(getExternalContext().getRequestContextPath() + Constants.ZIP_DOWNLOAD_URL);
			getFacesContext().responseComplete();
			paperSelection.getMap().clear();
		} else {
			addError(i18n("messages_error_no_documents_selected"));
		}
	}
	
	private List<PaperSubmissionInfo> selectedSubmissions() {
		List<PaperSubmissionInfo> selected = new ArrayList<PaperSubmissionInfo>(loadSubmissions());
		CollectionUtils.filter(selected, new Predicate() {
			public boolean evaluate(Object object) {
				return paperSelection.isSelected(object);
			}
		});
		logger.debug("selected " + selected.size() + " files");
		return selected;
	}

	public String delete() {
		if ((paperSubmissionInfo == null) || (paperSubmissionInfo.getId() == null)) {
			addError(i18n("messages_error_no_documents_selected"));
			return Constants.SUCCESS;
		}
		
		List<FolderEntryInfo> entries = selectedEntries();
		if (entries.size() > 0) {
			logger.debug("deleting documents:");
			setSessionBean(Constants.PAPERSUBMISSION_SELECTED_FILEENTRIES, entries);
			paperSelection.getMap().clear();
			return Constants.PAPERSUBMISSION_REMOVE_FILEENTRY_PAGE;
		} else {
			addError(i18n("messages_error_no_documents_selected"));
		}
		return Constants.SUCCESS;
	}
	
	public String addFile() {
		logger.debug("create new file");
		setSessionBean(Constants.PAPERSUBMISSION_SELECTED_FILEENTRY, new FileInfo());
		removeSessionBean(Constants.UPLOADED_FILE);
		return Constants.PAPERSUBMISSION_EDIT_FILEENTRY_PAGE;
	}
	
	@SuppressWarnings("unchecked")
	private List<FolderEntryInfo> loadFileEntries() {
		if (entries == null) {
			FolderInfo folder = documentService.getFolder(paperSubmissionInfo);
			entries = documentService.getFolderEntries(paperSubmissionInfo, folder);
		}
		return entries;
	}
	
	private List<PaperSubmissionInfo> loadSubmissions(){
		if(submissions == null && examInfo != null && examInfo.getId() != null){
			submissions = paperSubmissionService.findPaperSubmissionsByExam(examInfo.getId());
		}
		return submissions;
	}
	
	public String selectSubmission(){
		logger.debug("Starting method selectSubmission");
		PaperSubmissionInfo currentSubmission = currentSubmission();
		logger.debug("Returning to method selectSubmission");
		logger.debug(currentSubmission.getId());
		setSessionBean(Constants.PAPERSUBMISSION_PAPER_INFO, currentSubmission);
		
		if(currentSubmission.getId()==null){
			addError(i18n("papersubmission_message_submissiondoesnotexist"));
			return Constants.FAILURE;
		}

		return Constants.PAPERSUBMISSION_LECTURE_OVERVIEW_PAGE;
	}
	
	private PaperSubmissionInfo currentSubmission() {
		PaperSubmissionInfo submission = this.dataSubmissions.getRowData();
		return submission;
	}
	
	//// getter/setter methods ////////////////////////////////////////////////
	
	

	public LocalDataModelSubmissions getDataSubmissions() {
		return this.dataSubmissions;
	}
	public LocalDataModelSubmissionFiles getDataSubmissionFiles() {
		return this.dataSubmissionFiles;
	}
	

	
	/////// Inner classes ////////////////////////////////////////////////////
	
	private class LocalDataModelSubmissions extends AbstractPagedTable<PaperSubmissionInfo> {
		private static final long serialVersionUID = -6289875618529435428L;

		private DataPage<PaperSubmissionInfo> page;

		@Override
		@SuppressWarnings( { "unchecked" })
		public DataPage<PaperSubmissionInfo> getDataPage(int startRow, int pageSize) {
			if (page == null) {
				
				List<PaperSubmissionInfo> submissions = paperSubmissionService.getMembersAsPaperSubmissionsByExam(examInfo.getId());
				
				//sort(entries);
				page = new DataPage<PaperSubmissionInfo>(submissions.size(), 0, submissions);
			}
			return page;
		}
	}
	private class LocalDataModelSubmissionFiles extends AbstractPagedTable<FolderEntryInfo> {
		private static final long serialVersionUID = -6289875618529435428L;

		private DataPage<FolderEntryInfo> page;

		@Override
		@SuppressWarnings( { "unchecked" })
		public DataPage<FolderEntryInfo> getDataPage(int startRow, int pageSize) {
			if (page == null) {
							
				if(paperSubmissionInfo == null){
					page = new DataPage<FolderEntryInfo>(0,0,null);
				}
				else{
					List<FolderEntryInfo> entries = loadFileEntries();
					
					//sort(submissions);
					page = new DataPage<FolderEntryInfo>(entries.size(), 0, entries);
				}
			}
			return page;
		}
	}
//	public FolderEntrySelection getEntrySelection() {
//		return entrySelection;
//	}
//
//	public void setEntrySelection(FolderEntrySelection entrySelection) {
//		this.entrySelection = entrySelection;
//	}
	
	
	public PaperSubmissionSelection getPaperSelection() {
		return paperSelection;
	}
	
	public void setPaperSelection(PaperSubmissionSelection paperSelection) {
		this.paperSelection = paperSelection;
	}

	public List<FolderEntryInfo> getEntries() {
		return entries;
	}

	public void setEntries(List<FolderEntryInfo> entries) {
		this.entries = entries;
	}

	public void setDataSubmissions(LocalDataModelSubmissions dataSubmissions) {
		this.dataSubmissions = dataSubmissions;
	}

	public void setDataSubmissionFiles(
			LocalDataModelSubmissionFiles dataSubmissionFiles) {
		this.dataSubmissionFiles = dataSubmissionFiles;
	}

	public List<PaperSubmissionInfo> getSubmissions() {
		return submissions;
	}

	public void setSubmissions(List<PaperSubmissionInfo> submissions) {
		this.submissions = submissions;
	}
	
}
