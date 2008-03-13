package org.openuss.web.papersubmission;

import java.io.IOException;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.collections.comparators.ComparatorChain;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Property;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.Prerender;
import org.apache.shale.tiger.view.View;
import org.openuss.documents.FolderEntryInfo;
import org.openuss.documents.FolderInfo;
import org.openuss.framework.jsfcontrols.breadcrumbs.BreadCrumb;
import org.openuss.framework.web.jsf.model.AbstractPagedTable;
import org.openuss.framework.web.jsf.model.DataPage;
import org.openuss.paperSubmission.PaperSubmissionInfo;
import org.openuss.web.Constants;
import org.openuss.web.PageLinks;
import org.springframework.beans.support.PropertyComparator;

@Bean(name = "views$secured$papersubmission$submissionviewlecturer", scope = Scope.REQUEST)
@View
public class PaperSubmissionLecturerViewPage extends AbstractPaperSubmissionPage { 
	
	private static final Logger LOGGER = Logger.getLogger(PaperSubmissionViewPage.class);
	
	/** The data model for all submission files. */
	private LocalDataModelSubmissionFiles dataFiles = new LocalDataModelSubmissionFiles();
	
	@Property(value = "#{" + Constants.PAPERSUBMISSION_FOLDERENTRY_SELECTION + "}")
	private PaperSubmissionFileSelection paperFileSelection;
	
	private List<FolderEntryInfo> entries;

	/** Prepares the information needed for rendering. 
	 * @throws Exception */
	@Prerender
	public void prerender() throws Exception { // NOPMD by Administrator on 13.03.08 13:05
		super.prerender();
		
		paperSubmissionInfo = (PaperSubmissionInfo)getSessionBean(Constants.PAPERSUBMISSION_PAPER_INFO);
		paperFileSelection.processSwitch();
	
		addPageCrumbs();
	}
	
	/** Adds additional breadcrumbs to the course-crumbs.
	 * 
	 */
	private void addPageCrumbs() { // NOPMD by Administrator on 13.03.08 12:57
		breadcrumbs.loadCourseCrumbs(courseInfo);
		
		breadcrumbs.addCrumb(new BreadCrumb(PageLinks.PAPERSUBMISSION_EXAM, i18n("papersubmission_paperlist_header"), i18n("papersubmission_paperlist_header")));

		if (this.examInfo != null && this.examInfo.getId() != null &&
				courseInfo != null && courseInfo.getId() != null) {
			//specific exam crumb
			BreadCrumb crumb = new BreadCrumb(this.examInfo.getName());
			
			crumb.setLink(PageLinks.PAPERSUBMISSION_SUBMISSIONVIEW);
			crumb.addParameter("course", courseInfo.getId());
			crumb.addParameter("exam", this.examInfo.getId());
			breadcrumbs.addCrumb(crumb);
			
			if (paperSubmissionInfo != null && paperSubmissionInfo.getId() != null){
				//crumb of a specific submission
				crumb = new BreadCrumb(paperSubmissionInfo.getDisplayName());
				
				crumb.setLink(PageLinks.PAPERSUBMISSION_SUBMISSIONVIEW);
				crumb.addParameter("course", courseInfo.getId());
				crumb.addParameter("exam", this.examInfo.getId());
				crumb.addParameter("paper", paperSubmissionInfo.getId());
				breadcrumbs.addCrumb(crumb);
			}
		}
	}
	

	private List<FolderEntryInfo> selectedEntries() {
		List<FolderEntryInfo> selected = new ArrayList<FolderEntryInfo>(loadFileEntries());
		CollectionUtils.filter(selected, new Predicate() {
			public boolean evaluate(Object object) {
				return paperFileSelection.isSelected(object);
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
	public String download() throws IOException{
		LOGGER.debug("downloading documents");
		List<FolderEntryInfo> files = documentService.allFileEntries(selectedEntries());
		if (!files.isEmpty()) {
			paperSubmissionInfo = paperSubmissionService.getPaperSubmission(paperSubmissionInfo.getId());
			//Storing the zip file name into the session 
			DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy_HH.mm", Locale.GERMAN);
			String fileName = examInfo.getName() + "_" + paperSubmissionInfo.getFirstName() + "_" + paperSubmissionInfo.getLastName() + "_" + dateFormat.format(new Date());
			setSessionBean(Constants.ZIP_FILE_NAME, fileName);
			
			setSessionBean(Constants.DOCUMENTS_SELECTED_FILEENTRIES, files);
			HttpServletResponse response = getResponse();
			response.sendRedirect(getExternalContext().getRequestContextPath() + Constants.ZIP_DOWNLOAD_URL);
			getFacesContext().responseComplete();
			paperFileSelection.getMap().clear();
		} else {
			addError(i18n("messages_error_no_documents_selected"));
		}
		return Constants.SUCCESS;
	}
	
	//// getter/setter methods ////////////////////////////////////////////////
	public void setDataSubmissionFiles(
			LocalDataModelSubmissionFiles dataSubmissionFiles) {
		this.dataFiles = dataSubmissionFiles;
	}

	public LocalDataModelSubmissionFiles getDataSubmissionFiles() {
		return dataFiles;
	}

	public List<FolderEntryInfo> getEntries() {
		return entries;
	}

	public void setEntries(List<FolderEntryInfo> entries) {
		this.entries = entries;
	}

	public PaperSubmissionFileSelection getPaperFileSelection() {
		return paperFileSelection;
	}

	public void setPaperFileSelection(
			PaperSubmissionFileSelection paperFileSelection) {
		this.paperFileSelection = paperFileSelection;
	}

	/////// Inner classes ////////////////////////////////////////////////////
	
	private static class LocalDataModelSubmissionFiles extends AbstractPagedTable<FolderEntryInfo> implements Serializable {
		private static final long serialVersionUID = -6289875618529435428L;

		private DataPage<FolderEntryInfo> page;

		@Override
		@SuppressWarnings( { "unchecked" }) // NOPMD by Administrator on 13.03.08 13:01
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
			if (StringUtils.isNotBlank(getSortColumn())) {
				chain.addComparator(new PropertyComparator(getSortColumn(), true, isAscending()));
			} else {
				chain.addComparator(new PropertyComparator("name", true, isAscending()));
			}
			Collections.sort(list, chain);
		}	
	}
}
