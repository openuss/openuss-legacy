package org.openuss.web.papersubmission;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
	private LocalDataModelSubmissionFiles dataSubmissionFiles = new LocalDataModelSubmissionFiles();
	
	@Property(value = "#{" + Constants.PAPERSUBMISSION_FOLDERENTRY_SELECTION + "}")
	private PaperSubmissionFileSelection paperFileSelection;
	
	private List<FolderEntryInfo> entries;

	/** Prepares the information needed for rendering. 
	 * @throws Exception */
	@Prerender
	public void prerender() throws Exception {
		super.prerender();
		paperSubmissionInfo = (PaperSubmissionInfo)getSessionBean(Constants.PAPERSUBMISSION_PAPER_INFO);
		paperFileSelection.processSwitch();
	
		addPageCrumbs();
	}
	
	/** Adds additional breadcrumbs to the course-crumbs.
	 * 
	 */
	private void addPageCrumbs() {
		breadcrumbs.loadCourseCrumbs(courseInfo);
		
		BreadCrumb crumb = new BreadCrumb();
		crumb.setLink(PageLinks.PAPERSUBMISSION_EXAM);
		crumb.setName(i18n("papersubmission_paperlist_header"));
		crumb.setHint(i18n("papersubmission_paperlist_header"));		
		breadcrumbs.addCrumb(crumb);
		
		//specific exam crumb
		crumb = new BreadCrumb();
		crumb.setName(examInfo.getName());
		crumb.setHint(examInfo.getName());
		
		if(courseInfo != null && courseInfo.getId() != null 
				&& examInfo != null && examInfo.getId() != null){
			
			crumb.setLink(PageLinks.PAPERSUBMISSION_SUBMISSIONVIEW);
			crumb.addParameter("course",courseInfo.getId());
			crumb.addParameter("exam",examInfo.getId());
		}
		breadcrumbs.addCrumb(crumb);
		
		//crumb of a specific submission
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
		List<FolderEntryInfo> selected = new ArrayList<FolderEntryInfo>(loadFileEntries());
		CollectionUtils.filter(selected, new Predicate() {
			public boolean evaluate(Object object) {
				return paperFileSelection.isSelected(object);
			}
		});
		LOGGER.debug("selected " + selected.size() + " files");
		
		return selected;
	}
	
	@SuppressWarnings("unchecked")
	private List<FolderEntryInfo> loadFileEntries() {
		if (entries == null) {
			FolderInfo folder = documentService.getFolder(paperSubmissionInfo);
			entries = documentService.getFolderEntries(paperSubmissionInfo, folder);
		}
		return entries;
	}
	
	@SuppressWarnings("unchecked")
	public String download() throws IOException{
		LOGGER.debug("downloading documents");
		List<FolderEntryInfo> files = selectedEntries();
		if (files.size() > 0) {
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
		this.dataSubmissionFiles = dataSubmissionFiles;
	}

	public LocalDataModelSubmissionFiles getDataSubmissionFiles() {
		return dataSubmissionFiles;
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
	
	private class LocalDataModelSubmissionFiles extends AbstractPagedTable<FolderEntryInfo> implements Serializable {
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
					
					sort(entries);
					page = new DataPage<FolderEntryInfo>(entries.size(), 0, entries);
				}
			}
			return page;
		}
		
		/**
		 * Default property sort method
		 * 
		 * @param periods
		 */
		@SuppressWarnings("unchecked")
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
