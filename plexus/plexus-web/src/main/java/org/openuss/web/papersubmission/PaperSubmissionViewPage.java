package org.openuss.web.papersubmission;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.collections.comparators.ComparatorChain;
import org.apache.commons.collections.comparators.ReverseComparator;
import org.apache.commons.lang.StringUtils;
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
import org.openuss.paperSubmission.ExamInfo;
import org.openuss.paperSubmission.PaperSubmissionInfo;
import org.openuss.web.Constants;
import org.openuss.web.PageLinks;
import org.springframework.beans.support.PropertyComparator;

/**
 * Backing Bean for submissionview.xhtml.
 * @author Projektseminar WS 07/08, Team Collaboration
 *
 */
@Bean(name = "views$secured$papersubmission$submissionview", scope = Scope.REQUEST)
@View
public class PaperSubmissionViewPage extends AbstractPaperSubmissionPage {
	
	private static final Logger LOGGER = Logger.getLogger(PaperSubmissionViewPage.class);
	
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
	public void prerender() throws Exception { // NOPMD by Administrator on 13.03.08 12:57
		super.prerender();
		if (!isPostBack() && examInfo != null && examInfo.getId() != null) {
			setExamInfo(paperSubmissionService.getExam(examInfo.getId()));
			setSessionBean(Constants.PAPERSUBMISSION_EXAM_INFO, examInfo);
		} 
		paperSelection.processSwitch();
		addPageCrumbs();
		paperSubmissionInfo = getCurrentPaperSubmission();
		
	}

	@SuppressWarnings("unchecked") // NOPMD by Administrator on 13.03.08 13:01
	private PaperSubmissionInfo getCurrentPaperSubmission() {
		final List<PaperSubmissionInfo> paperInfos;
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
		
		if (this.examInfo != null) {
			crumb = new BreadCrumb();
			crumb.setName(this.examInfo.getName());
			crumb.setHint(this.examInfo.getName());
			
			if(courseInfo != null && courseInfo.getId() != null 
					&& this.examInfo != null && this.examInfo.getId() != null){
				
				crumb.setLink(PageLinks.PAPERSUBMISSION_SUBMISSIONVIEW);
				crumb.addParameter("course", courseInfo.getId());
				crumb.addParameter("exam", this.examInfo.getId());
			}
			
			breadcrumbs.addCrumb(crumb);
		}
	}
	
	

	private List<FolderEntryInfo> selectedEntries() {
		List<FolderEntryInfo> selected = new ArrayList<FolderEntryInfo>(loadFileEntries());
		CollectionUtils.filter(selected, new Predicate() {
			public boolean evaluate(Object object) {
				return paperSelection.isSelected(object);
			}
		});
		LOGGER.debug("selected " + selected.size() + " files");
		return selected;
	}
	
	@SuppressWarnings("unchecked") // NOPMD by Administrator on 13.03.08 13:01
	private List<FolderEntryInfo> loadFileEntries() {
		if (entries == null) {
			FolderInfo folder = documentService.getFolder(paperSubmissionInfo);
			entries = documentService.getFolderEntries(paperSubmissionInfo, folder);
		}
		return entries;
	}
	
	@SuppressWarnings("unchecked") // NOPMD by Administrator on 13.03.08 13:01
	public String download () throws IOException{
		LOGGER.debug("downloading documents");
		List<FolderEntryInfo> files = documentService.allFileEntries(selectedEntries());
		if (!files.isEmpty()) {
			//Storing the zip file name into the session 
			String fileName = examInfo.getName() + "_" + new SimpleDateFormat("dd.MM.yyyy", 
					Locale.GERMAN).format(paperSubmissionInfo.getDeliverDate());
			setSessionBean(Constants.ZIP_FILE_NAME, fileName);
				
			setSessionBean(Constants.DOCUMENTS_SELECTED_FILEENTRIES, files);
			HttpServletResponse response = getResponse();
			response.sendRedirect(getExternalContext().getRequestContextPath() +  Constants.ZIP_DOWNLOAD_URL );
			getFacesContext().responseComplete();
			paperSelection.getMap().clear();
		} else {
			addError(i18n("messages_error_no_documents_selected")); // NOPMD by Administrator on 13.03.08 13:02
		}
		return Constants.SUCCESS;
	}
	
	public String downloadSubmission () throws IOException{
		LOGGER.debug("Downloading selected Submissions.");
		
		List<PaperSubmissionInfo> submissions = selectedSubmissions();
		processDownloadSubmissions(submissions);
		
		return Constants.SUCCESS;
	}
	
	@SuppressWarnings("unchecked") // NOPMD by Administrator on 13.03.08 13:01
	public String downloadAllSubmissions () throws IOException{
		LOGGER.debug("Downloading all Submissions.");
		
		List<PaperSubmissionInfo> submissions = paperSubmissionService.findPaperSubmissionsByExam(examInfo.getId());
		processDownloadSubmissions(submissions);
		
		return Constants.SUCCESS;
	}
	
	@SuppressWarnings("unchecked") // NOPMD by Administrator on 13.03.08 13:01
	public String downloadInTimeSubmissions () throws IOException{
		LOGGER.debug("Downloading all in time Submissions.");
		
		List<PaperSubmissionInfo> submissions = paperSubmissionService.findInTimePaperSubmissionsByExam(examInfo.getId());
		processDownloadSubmissions(submissions);
		
		return Constants.SUCCESS;
	}
	
	@SuppressWarnings("unchecked") // NOPMD by Administrator on 13.03.08 13:01
	private void processDownloadSubmissions(List<PaperSubmissionInfo> submissions) throws IOException {
		List<FileInfo> files = paperSubmissionService.getPaperSubmissionFiles(submissions);
		for(FileInfo file : files){
			if(examInfo.getDeadline().before(file.getModified())){
				StringBuilder path = new StringBuilder(file.getPath()); // NOPMD by Administrator on 13.03.08 12:57
				path.append(i18n("papersubmission_zip_foldername_notintime"));
				file.setPath(path.toString());
				path.append("/").append(file.getFileName());
				file.setAbsoluteName(path.toString());
			}
		}
		if (!files.isEmpty()) {
			//Storing the zip file name into the session 
			String fileName = examInfo.getName() + "_" + new SimpleDateFormat("dd.MM.yyyy_HH.mm", Locale.GERMAN).format(new Date());
			setSessionBean(Constants.ZIP_FILE_NAME, fileName);
			
			setSessionBean(Constants.DOCUMENTS_SELECTED_FILEENTRIES, files);
			HttpServletResponse response = getResponse();
			response.sendRedirect(getExternalContext().getRequestContextPath() + Constants.ZIP_DOWNLOAD_URL);
			getFacesContext().responseComplete();
			paperSelection.getMap().clear();
		} else {
			addError(i18n("messages_error_no_documents_selected")); // NOPMD by Administrator on 13.03.08 13:01
		}
	}
	
	private List<PaperSubmissionInfo> selectedSubmissions() {
		List<PaperSubmissionInfo> selected = new ArrayList<PaperSubmissionInfo>(loadSubmissions());
		CollectionUtils.filter(selected, new Predicate() {
			public boolean evaluate(Object object) {
				return paperSelection.isSelected(object);
			}
		});
		LOGGER.debug("selected " + selected.size() + " files");
		return selected;
	}
	
	@SuppressWarnings("unchecked") // NOPMD by Administrator on 13.03.08 13:01
	private List<PaperSubmissionInfo> loadSubmissions(){
		if(submissions == null && examInfo != null && examInfo.getId() != null){
			submissions = paperSubmissionService.findPaperSubmissionsByExam(examInfo.getId());
		}
		return submissions;
	}
	
	public String editFolderEntry() {
		LOGGER.debug("editing folder entry");
		FolderEntryInfo entry = dataSubmissionFiles.getRowData();
		FileInfo selectedFile = documentService.getFileEntry(entry.getId(), false);
		setSessionBean(Constants.PAPERSUBMISSION_SELECTED_FILEENTRY, selectedFile);
		return Constants.PAPERSUBMISSION_FILE_EDIT_PAGE;
	}

	public String delete() {
		if ((paperSubmissionInfo == null) || (paperSubmissionInfo.getId() == null)) {
			addError(i18n("messages_error_no_documents_selected")); // NOPMD by Administrator on 13.03.08 13:01
			return Constants.SUCCESS;
		}
		
		List<FolderEntryInfo> entries = selectedEntries();
		if (!entries.isEmpty()) {
			LOGGER.debug("deleting documents:");
			setSessionBean(Constants.PAPERSUBMISSION_FOLDERENTRY_SELECTION, entries);
			paperSelection.getMap().clear();
			return Constants.PAPERSUBMISSION_FILE_REMOVE_PAGE;
		} else {
			addError(i18n("messages_error_no_documents_selected")); // NOPMD by Administrator on 13.03.08 13:01
		}
		return Constants.SUCCESS;
	}
	
	public String addFile() {
		LOGGER.debug("create new file");
		setSessionBean(Constants.PAPERSUBMISSION_SELECTED_FILEENTRY, new FileInfo());
		removeSessionBean(Constants.UPLOADED_FILE);
		return Constants.PAPERSUBMISSION_FILE_EDIT_PAGE;
	}
	
	/**
	 * Store the selected File into session scope and go to remove confirmation page. 
	 * 
	 * @return outcome
	 */
	public String selectFileAndConfirmRemove() {
		LOGGER.debug("Starting method selectFileAndConfirmRemove");
		FolderEntryInfo entry = dataSubmissionFiles.getRowData();
		LOGGER.debug("Returning to method selectFileAndConfirmRemove");
		LOGGER.debug(entry.getId());
		setSessionBean(Constants.PAPERSUBMISSION_FOLDERENTRY_SELECTION, Arrays.asList(new FolderEntryInfo[] {entry}));

		return Constants.PAPERSUBMISSION_FILE_REMOVE_PAGE;
	}
	
	public String selectSubmission(){
		LOGGER.debug("Starting method selectSubmission");
		PaperSubmissionInfo currentSubmission = currentSubmission();
		LOGGER.debug("Returning to method selectSubmission");
		LOGGER.debug(currentSubmission.getId());
		setSessionBean(Constants.PAPERSUBMISSION_PAPER_INFO, currentSubmission);
		
		if(currentSubmission.getId()==null){
			addError(i18n("papersubmission_message_submissiondoesnotexist"));
			return Constants.FAILURE;
		}

		return Constants.PAPERSUBMISSION_LECTURE_OVERVIEW_PAGE;
	}
	
	private PaperSubmissionInfo currentSubmission() {
		return this.dataSubmissions.getRowData();
	}
	
	public LocalDataModelSubmissions getDataSubmissions() {
		return this.dataSubmissions;
	}
	public LocalDataModelSubmissionFiles getDataSubmissionFiles() {
		return this.dataSubmissionFiles;
	}
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
	
	private class LocalDataModelSubmissions extends AbstractPagedTable<PaperSubmissionInfo> {
		private static final long serialVersionUID = -6289875618529435428L;

		private DataPage<PaperSubmissionInfo> page;


		@SuppressWarnings("unchecked") // NOPMD by Administrator on 13.03.08 13:01
		public DataPage<PaperSubmissionInfo> getDataPage(int startRow, int pageSize) {
			if (page == null) {
				
				List<PaperSubmissionInfo> submissions = paperSubmissionService.getMembersAsPaperSubmissionsByExam(examInfo.getId());
				
				sort(submissions);
				page = new DataPage<PaperSubmissionInfo>(submissions.size(), 0, submissions);
			}
			return page;
		}
		
		/**
		 * Default property sort method
		 * @param list List of PaperSubmissionInfo objects.
		 */
		@SuppressWarnings("unchecked") // NOPMD by Administrator on 13.03.08 13:01
		@Override
		protected void sort(List<PaperSubmissionInfo> list) {
			ComparatorChain chain = new ComparatorChain();
			
			if (StringUtils.isNotBlank(getSortColumn()) ) {
				if (getSortColumn().equals("submissionStatus")) {
					chain.addComparator(submissionComparator);
				} else {
					chain.addComparator(new PropertyComparator(getSortColumn(), true, isAscending()));
				}
			} else {
				chain.addComparator(new PropertyComparator("displayName", true, isAscending()));
			}
			Collections.sort(list, chain);
		}
		private final Comparator<PaperSubmissionInfo> submissionComparator = new Comparator<PaperSubmissionInfo>() {
			public int compare(PaperSubmissionInfo info1, PaperSubmissionInfo info2) {
				if (isAscending()) {
					return info1.getSubmissionStatus().compareTo(info2.getSubmissionStatus());
				} else {
					return info2.getSubmissionStatus().compareTo(info1.getSubmissionStatus());
				}
			}
			
		};	
	}
	
	private class LocalDataModelSubmissionFiles extends AbstractPagedTable<FolderEntryInfo> {
		private static final long serialVersionUID = -6289875618529435428L;

		private DataPage<FolderEntryInfo> page;

		public DataPage<FolderEntryInfo> getDataPage(int startRow, int pageSize) {
			if (page == null) {
							
				if(paperSubmissionInfo == null){
					page = new DataPage<FolderEntryInfo>(0,0,null);
				}
				else{
					List<FolderEntryInfo> entries = loadFileEntries();
					
					sort(entries);
					page = new DataPage<FolderEntryInfo>(entries.size(), 0, entries);
				}
			}
			return page;
		}
		
		
		/**
		 * Default property sort method
		 * @param list List of FolderEntryInfo objects.
		 */
		@SuppressWarnings("unchecked") // NOPMD by Administrator on 13.03.08 13:01
		@Override
		protected void sort(List<FolderEntryInfo> list) {
			ComparatorChain chain = new ComparatorChain();
			if (isAscending()) {
				chain.addComparator(folderComparator);
			} else {
				chain.addComparator(new ReverseComparator(folderComparator));
			}
			
			if (StringUtils.isNotBlank(getSortColumn())) {
				chain.addComparator(new PropertyComparator(getSortColumn(), true, isAscending()));
			} else {
				chain.addComparator(new PropertyComparator("name", true, isAscending()));
			}
			Collections.sort(list, chain);
		}
		
		private final Comparator<FolderEntryInfo> folderComparator = new Comparator<FolderEntryInfo>() {
			public int compare(FolderEntryInfo info1, FolderEntryInfo info2) {
				if (info1.isFolder() && info2.isFolder() || !info1.isFolder() && !info2.isFolder()) {
					return 0;
				} else {
					return info1.isFolder()?-1:1;
				}
			}
			
		};	
	}	
}
