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
import org.openuss.framework.jsfcontrols.breadcrumbs.BreadCrumb;
import org.openuss.framework.web.jsf.model.AbstractPagedTable;
import org.openuss.framework.web.jsf.model.DataPage;
import org.openuss.lecture.CourseMemberInfo;
import org.openuss.paperSubmission.ExamInfo;
import org.openuss.paperSubmission.PaperSubmission;
import org.openuss.paperSubmission.PaperSubmissionInfo;
import org.openuss.web.Constants;
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
	
	private List<FileInfo> entries;
	
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
		//paperSubmissionInfo = new PaperSubmissionInfo();
		List<PaperSubmissionInfo> paperInfos;
		examInfo = (ExamInfo) getSessionBean(Constants.PAPERSUBMISSION_EXAM_INFO);
		//CourseMemberInfo memberInfo = courseService.getMemberInfo(courseInfo, user);
		paperInfos = paperSubmissionService.findPaperSubmissionsByExamAndUser(examInfo.getId(), user.getId());
		/*if(paperInfos.isEmpty()){
			paperSubmissionInfo.setExamId(examInfo.getId());
			paperSubmissionInfo.setUserId(user.getId());
			//paperSubmissionInfo.setDeliverDate(System.currentTimeMillis());
			paperSubmissionService.createPaperSubmission(paperSubmissionInfo);
			setSessionBean(Constants.PAPERSUBMISSION_PAPER_INFO, paperSubmissionInfo);
		}
		else {*/
		if(!paperInfos.isEmpty()){
//			paperSubmissionInfo.setDeliverDate(paperInfos.get(0).getDeliverDate());
//			paperSubmissionInfo.setId(paperInfos.get(paperInfos.size()-1).getId());
//			paperSubmissionInfo.setExamId(examInfo.getId());
//			paperSubmissionInfo.setUserId(user.getId());
			setSessionBean(Constants.PAPERSUBMISSION_PAPER_INFO, paperInfos.get(paperInfos.size()-1));
			return paperInfos.get(paperInfos.size()-1);
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
		crumb.setLink("");
		crumb.setName(i18n("papersubmission_paper_header"));
		crumb.setHint(i18n("papersubmission_paper_header"));

		breadcrumbs.loadCourseCrumbs(courseInfo);
		breadcrumbs.addCrumb(crumb);
	}
	

	private List<FileInfo> selectedEntries() {
		List<FileInfo> selected = new ArrayList<FileInfo>(loadFileEntries());
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
		List<FileInfo> files = documentService.allFileEntries(selectedEntries());
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
	
	public String delete() {
		List<FileInfo> entries = selectedEntries();
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
	private List<FileInfo> loadFileEntries() {
		if (entries == null) {
			entries = documentService.getFileEntries(paperSubmissionInfo);
		}
		return entries;
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
				
				List<PaperSubmissionInfo> submissions = paperSubmissionService.findPaperSubmissionsByExam(examInfo.getId());
				
				//sort(entries);
				page = new DataPage<PaperSubmissionInfo>(submissions.size(), 0, submissions);
			}
			return page;
		}
	}
	private class LocalDataModelSubmissionFiles extends AbstractPagedTable<FileInfo> {
		private static final long serialVersionUID = -6289875618529435428L;

		private DataPage<FileInfo> page;

		@Override
		@SuppressWarnings( { "unchecked" })
		public DataPage<FileInfo> getDataPage(int startRow, int pageSize) {
			if (page == null) {
							
				if(paperSubmissionInfo == null){
					page = new DataPage<FileInfo>(0,0,null);
				}
				else{
					List<FileInfo> entries = loadFileEntries();
					
					//sort(submissions);
					page = new DataPage<FileInfo>(entries.size(), 0, entries);
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

	public List<FileInfo> getEntries() {
		return entries;
	}

	public void setEntries(List<FileInfo> entries) {
		this.entries = entries;
	}

	public void setDataSubmissions(LocalDataModelSubmissions dataSubmissions) {
		this.dataSubmissions = dataSubmissions;
	}

	public void setDataSubmissionFiles(
			LocalDataModelSubmissionFiles dataSubmissionFiles) {
		this.dataSubmissionFiles = dataSubmissionFiles;
	}
	
}
