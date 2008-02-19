package org.openuss.web.papersubmission;

import java.util.ArrayList;
import java.util.List;

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
import org.openuss.web.course.AbstractCoursePage;
import org.openuss.web.documents.FolderEntrySelection;

@Bean(name = "views$secured$papersubmission$paperview", scope = Scope.REQUEST)
@View
public class PaperSubmissionViewPage extends AbstractPaperSubmissionPage {
	
	public static final Logger logger = Logger.getLogger(PaperSubmissionViewPage.class);
	
	@Property(value = "#{" + Constants.PAPERSUBMISSION_FOLDERENTRY_SELECTION + "}")
	private FolderEntrySelection entrySelection;
	
	/** The datamodel for all submissions. */
	private LocalDataModelSubmissions dataSubmissions = new LocalDataModelSubmissions();
	
	/** The datamodel for all submission files. */
	private LocalDataModelSubmissionFiles dataSubmissionFiles = new LocalDataModelSubmissionFiles();
	
	private List<FolderEntryInfo> entries;
	
//	@Property(value = "#{" + Constants.PAPERSUBMISSION_SUBMISSION_SELECTION + "}")
//	private FolderEntrySelection entrySelection;
	
	/** Prepares the information needed for rendering. 
	 * @throws Exception */
	@Prerender
	public void prerender() throws Exception {
		super.prerender();
		if(user.getId() != -10){
//			getCurrentPaperSubmission();
//	
//			if (currentFolder == null && paperSubmissionInfo == null) {
//				redirect(Constants.OUTCOME_BACKWARD);
//			} else{
//				currentFolder = retrieveActualFolder();
//			}
//				
//			setSessionAttribute(Constants.PAPERSUBMISSION_CURRENT_FOLDER, currentFolder);
//				
//			entrySelection.setEntries(loadFolderEntries());
//			entrySelection.processSwitch();
		}
		
		addPageCrumbs();
	}

	private void getCurrentPaperSubmission() {
		paperSubmissionInfo = new PaperSubmissionInfo();
		List<PaperSubmissionInfo> paperInfos;
		examInfo = (ExamInfo) getSessionBean(Constants.PAPERSUBMISSION_EXAM_INFO);
		//CourseMemberInfo memberInfo = courseService.getMemberInfo(courseInfo, user);
		paperInfos = (List<PaperSubmissionInfo>) paperSubmissionService.findPaperSubmissionsByExamAndUser(examInfo.getId(), user.getId());
		if(paperInfos.isEmpty()){
			paperSubmissionInfo.setExamId(examInfo.getId());
			paperSubmissionInfo.setUserId(user.getId());
			//paperSubmissionInfo.setDeliverDate(System.currentTimeMillis());
			paperSubmissionService.createPaperSubmission(paperSubmissionInfo);
			setSessionBean(Constants.PAPERSUBMISSION_PAPER_INFO, paperSubmissionInfo);
		}
		else {
			paperSubmissionInfo.setDeliverDate(paperInfos.get(0).getDeliverDate());
			paperSubmissionInfo.setId(paperInfos.get(paperInfos.size()-1).getId());
			paperSubmissionInfo.setExamId(examInfo.getId());
			paperSubmissionInfo.setUserId(user.getId());
			setSessionBean(Constants.PAPERSUBMISSION_PAPER_INFO, paperSubmissionInfo);
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
	
//	private List<SubmissionInfo> loadSubmissionEntries() {
//		if (this.entries == null) {
//			this.entries = new ArrayList<SubmissionInfo>();
//			this.entries.add(new SubmissionInfo(1l, "Donald Duck", "late"));
//			this.entries.add(new SubmissionInfo(2l, "Minnie Maus", "missing"));
//			this.entries.add(new SubmissionInfo(3l, "Dagobert Duck", "ok"));
//		}
//		return this.entries;
//	}
	
	public String downloadFile () {
		return "collaboration";
	}
	
	public String addFile() {
		logger.debug("create new file");
		setSessionBean(Constants.PAPERSUBMISSION_SELECTED_FILEENTRY, new FileInfo());
		removeSessionBean(Constants.UPLOADED_FILE);
		return Constants.PAPERSUBMISSION_EDIT_FILEENTRY_PAGE;
	}
	
	@SuppressWarnings("unchecked")
	private List<FolderEntryInfo> loadFolderEntries() {
		if (entries == null) {
			entries = documentService.getFolderEntries(paperSubmissionInfo, currentFolder);
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
	
	private class LocalDataModelSubmissions extends AbstractPagedTable<PaperSubmission> {
		private static final long serialVersionUID = -6289875618529435428L;

		private DataPage<PaperSubmission> page;

		@Override
		@SuppressWarnings( { "unchecked" })
		public DataPage<PaperSubmission> getDataPage(int startRow, int pageSize) {
			if (page == null) {
				// TODO: implement!
				/*List<CourseTypeInfo> courseTypes = new ArrayList<CourseTypeInfo>(courseTypeService
						.findCourseTypesByInstitute(instituteInfo.getId()));*/
				List<PaperSubmission> submissions = paperSubmissionService.findPaperSubmissionsByExam(examInfo.getId());
				
				//sort(entries);
				page = new DataPage<PaperSubmission>(submissions.size(), 0, submissions);
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
				// TODO: implement!
				/*List<CourseTypeInfo> courseTypes = new ArrayList<CourseTypeInfo>(courseTypeService
						.findCourseTypesByInstitute(instituteInfo.getId()));*/
				
				getCurrentPaperSubmission();
				
				if (currentFolder == null && paperSubmissionInfo == null) {
					redirect(Constants.OUTCOME_BACKWARD);
				} else{
					currentFolder = retrieveActualFolder();
				}
					
				setSessionAttribute(Constants.PAPERSUBMISSION_CURRENT_FOLDER, currentFolder);
				
				entrySelection.setEntries(loadFolderEntries());
				entrySelection.processSwitch();
					
				List<FolderEntryInfo> entries = loadFolderEntries();
				
				
				//submissions.add(new SubmissionFileInfo(1l, "Lösung", "pdf", 1234l, "21.01.2008 10:03", "21.01.2008 10:15"));
				
				//sort(submissions);
				page = new DataPage<FolderEntryInfo>(entries.size(), 0, entries);
			}
			return page;
		}
	}
	public FolderEntrySelection getEntrySelection() {
		return entrySelection;
	}

	public void setEntrySelection(FolderEntrySelection entrySelection) {
		this.entrySelection = entrySelection;
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
	
}
