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
import org.openuss.paperSubmission.SubmissionStatus;
import org.openuss.web.Constants;
import org.openuss.web.PageLinks;
import org.openuss.web.collaboration.WorkspaceMemberSelection;
import org.openuss.web.course.AbstractCoursePage;
import org.openuss.web.documents.FolderEntrySelection;

@Bean(name = "views$secured$papersubmission$submissionviewlecturer", scope = Scope.REQUEST)
@View
public class PaperSubmissionLecturerViewPage extends AbstractPaperSubmissionPage {
	
	public static final Logger logger = Logger.getLogger(PaperSubmissionViewPage.class);
	
//	@Property(value = "#{" + Constants.PAPERSUBMISSION_FOLDERENTRY_SELECTION + "}")
//	private FolderEntrySelection entrySelection;
	
	/** The datamodel for all submission files. */
	private LocalDataModelSubmissionFiles dataSubmissionFiles = new LocalDataModelSubmissionFiles();
	
	private List<ExtendedFolderEntryInfo> entries;
	
	public class ExtendedFolderEntryInfo {
		
		private FolderEntryInfo entry;
		
		private SubmissionStatus submissionStatus;
		
		public ExtendedFolderEntryInfo(FolderEntryInfo entry) {
			this.entry = entry;
			if(entry.getModified().before(examInfo.getDeadline())){
				this.submissionStatus = SubmissionStatus.IN_TIME;
			}else{
				this.submissionStatus = SubmissionStatus.NOT_IN_TIME;
			}
		}

		public FolderEntryInfo getEntry() {
			return entry;
		}

		public SubmissionStatus getSubmissionStatus() {
			return submissionStatus;
		}
	}
	
	@Property(value = "#{" + Constants.PAPERSUBMISSION_SUBMISSION_SELECTION + "}")
	private PaperSubmissionSelection paperSelection;
	
	/** Prepares the information needed for rendering. 
	 * @throws Exception */
	@Prerender
	public void prerender() throws Exception {
		super.prerender();
		paperSubmissionInfo = (PaperSubmissionInfo)getSessionBean(Constants.PAPERSUBMISSION_PAPER_INFO);
		
		paperSelection.processSwitch();
	
		addPageCrumbs();
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
			
			crumb.setLink(PageLinks.PAPERSUBMISSION_SUBMISSIONVIEW);
			crumb.addParameter("course",courseInfo.getId());
			crumb.addParameter("exam",examInfo.getId());
			//crumb.addParameter("paper",paperSubmissionInfo.getId());
		}
		breadcrumbs.addCrumb(crumb);
		
		crumb = new BreadCrumb();
		crumb.setName(paperSubmissionInfo.getDisplayName());
		crumb.setHint(paperSubmissionInfo.getDisplayName());
		
		if(courseInfo != null && courseInfo.getId() != null 
				&& examInfo != null && examInfo.getId() != null
				&& paperSubmissionInfo != null && paperSubmissionInfo.getId() != null){
			
			crumb.setLink(PageLinks.PAPERSUBMISSION_SUBMISSIONVIEW);
			crumb.addParameter("course",courseInfo.getId());
			crumb.addParameter("exam",examInfo.getId());
			crumb.addParameter("paper",paperSubmissionInfo.getId());
		}
		breadcrumbs.addCrumb(crumb);
	}
	

	private List<FolderEntryInfo> selectedEntries() {
		List<ExtendedFolderEntryInfo> selectedExtendedList = new ArrayList<ExtendedFolderEntryInfo>(loadFileEntries());
		List<FolderEntryInfo> selected = new ArrayList<FolderEntryInfo>();
		for(ExtendedFolderEntryInfo selectedExtended : selectedExtendedList){
			selected.add(selectedExtended.getEntry());
		}
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
	

	@SuppressWarnings("unchecked")
	private List<ExtendedFolderEntryInfo> loadFileEntries() {
		if (entries == null) {
			FolderInfo folder = documentService.getFolder(paperSubmissionInfo);
			List<FolderEntryInfo> normalEntries = documentService.getFolderEntries(paperSubmissionInfo, folder);
			entries = new ArrayList<ExtendedFolderEntryInfo>();
			for(FolderEntryInfo entry : normalEntries){
				entries.add(new ExtendedFolderEntryInfo(entry));
			}
		}
		return entries;
	}
	
	
	
	
	//// getter/setter methods ////////////////////////////////////////////////
	
	

	
	/////// Inner classes ////////////////////////////////////////////////////
	
	private class LocalDataModelSubmissionFiles extends AbstractPagedTable<ExtendedFolderEntryInfo> {
		private static final long serialVersionUID = -6289875618529435428L;

		private DataPage<ExtendedFolderEntryInfo> page;

		@Override
		@SuppressWarnings( { "unchecked" })
		public DataPage<ExtendedFolderEntryInfo> getDataPage(int startRow, int pageSize) {
			if (page == null) {
							
				if(paperSubmissionInfo == null){
					page = new DataPage<ExtendedFolderEntryInfo>(0,0,null);
				}
				else{
					List<ExtendedFolderEntryInfo> entries = loadFileEntries();
					
					sort(entries);
					page = new DataPage<ExtendedFolderEntryInfo>(entries.size(), 0, entries);
				}
			}
			return page;
		}
	}
	
	public PaperSubmissionSelection getPaperSelection() {
		return paperSelection;
	}
	
	public void setPaperSelection(PaperSubmissionSelection paperSelection) {
		this.paperSelection = paperSelection;
	}

	public List<ExtendedFolderEntryInfo> getEntries() {
		return entries;
	}

	public void setEntries(List<ExtendedFolderEntryInfo> entries) {
		this.entries = entries;
	}

	public void setDataSubmissionFiles(
			LocalDataModelSubmissionFiles dataSubmissionFiles) {
		this.dataSubmissionFiles = dataSubmissionFiles;
	}



	public LocalDataModelSubmissionFiles getDataSubmissionFiles() {
		return dataSubmissionFiles;
	}
}
