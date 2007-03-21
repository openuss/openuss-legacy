package org.openuss.web.documents; 

import java.util.ArrayList;
import java.util.List;

import javax.faces.event.ValueChangeEvent;

import org.apache.log4j.Logger;
import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.Prerender;
import org.apache.shale.tiger.view.View;
import org.openuss.documents.FileEntry;
import org.openuss.documents.Folder;
import org.openuss.documents.FolderEntryInfo;
import org.openuss.framework.web.jsf.model.AbstractPagedTable;
import org.openuss.framework.web.jsf.model.DataPage;
import org.openuss.lecture.LectureException;
import org.openuss.web.Constants;

@Bean(name = "views$secured$documents$documents", scope = Scope.REQUEST)
@View
public class DocumentsMainPage extends AbstractDocumentPage{
	private static final Logger logger = Logger.getLogger(DocumentsMainPage.class);
	
	private DocumentDataProvider data = new DocumentDataProvider();
	
	private List<FolderEntryInfo> selectedEntries = new ArrayList<FolderEntryInfo>();
	
	@Prerender
	public void prerender() throws LectureException {
		super.prerender();
		if (currentFolder == null || currentFolder.getId() == null) {
			currentFolder = documentService.getFolder(enrollment,null);
		}
	}
	
	public void changedSelection(ValueChangeEvent event) throws LectureException {
		FolderEntryInfo entry = data.getRowData();
		if (logger.isDebugEnabled()) {
			logger.debug("changed selection" + entry.getName() + " from " + event.getOldValue() + " to " + event.getNewValue());
		}
		
		if (Boolean.TRUE.equals(event.getNewValue())) {
			selectedEntries.add(entry);
		} else if (Boolean.FALSE.equals(event.getNewValue())) {
			selectedEntries.remove(entry);
		}
	}
	
	private class DocumentDataProvider extends AbstractPagedTable<FolderEntryInfo> {

		private DataPage<FolderEntryInfo> page; 
		
		@Override 
		public DataPage<FolderEntryInfo> getDataPage(int startRow, int pageSize) {
			if (page == null) {
				List<FolderEntryInfo> entries = documentService.getFolderEntries(enrollment, currentFolder);
				sort(entries);
				page = new DataPage<FolderEntryInfo>(entries.size(),0,entries);
			}
			return page;
		}
	}
	
	public String download(){
		logger.debug("document download");
		return Constants.SUCCESS;
	}
	
	public String delete(){
		logger.debug("deleting documents:");
		if (selectedEntries.size() > 0) {
			setSessionBean(Constants.SELECTED_FOLDERENTRIES, selectedEntries);
			return Constants.DOCUMENTS_REMOVE_FOLDERENTRY_PAGE;
		}
		return Constants.SUCCESS;
	}
	
	public String newFolder() {
		logger.debug("create new folder");
		Folder newFolder = Folder.Factory.newInstance();
		setSessionBean(Constants.SELECTED_FOLDER, newFolder);
		return Constants.DOCUMENTS_NEW_FOLDER_PAGE;
	}
	
	public String editFolderEntry() {
		logger.debug("edit folder entry");
		FolderEntryInfo entry = data.getRowData();
		if (entry.isFolder()) {
			Folder selectedFolder = documentService.getFolder(entry);
			setSessionBean(Constants.SELECTED_FOLDER, selectedFolder);
			return Constants.DOCUMENTS_EDIT_FOLDER_PAGE;
		} else {
			FileEntry selectedFile = documentService.getFileEntry(entry);
			setSessionBean(Constants.SELECTED_FILEENTRY, selectedFile);
			return Constants.DOCUMENTS_EDIT_FILEENTRY_PAGE;
		}
	}

	public List<FolderEntryInfo> getSelectedEntries () {
		return selectedEntries;
	}
	
	public DocumentDataProvider getData() {
		return data;
	}


	public void setData(DocumentDataProvider data) {
		this.data = data;
	}



	
}