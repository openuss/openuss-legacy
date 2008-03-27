package org.openuss.web.documents;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.model.SelectItem;
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
import org.openuss.documents.DocumentApplicationException;
import org.openuss.documents.FileInfo;
import org.openuss.documents.FolderEntryInfo;
import org.openuss.documents.FolderInfo;
import org.openuss.framework.web.jsf.model.AbstractPagedTable;
import org.openuss.framework.web.jsf.model.DataPage;
import org.openuss.web.Constants;
import org.springframework.beans.support.PropertyComparator;

@Bean(name = "views$secured$documents$documents", scope = Scope.REQUEST)
@View
public class DocumentsMainPage extends AbstractDocumentPage {
	private static final Logger logger = Logger.getLogger(DocumentsMainPage.class);

	private static Map<String, Boolean> avaliableExtensions;
	
	private DocumentDataProvider data = new DocumentDataProvider();

	@Property(value = "#{" + Constants.DOCUMENTS_FOLDERENTRY_SELECTION + "}")
	private FolderEntrySelection entrySelection;
	/*
	 * Target Folder to which selected Entries will be moved
	 */
	private FolderInfo targetFolder;
		
		/*
		 * Target folder list
		 */
	private List<SelectItem> folderList;
	 
	private boolean moveMode = false;

	private List<FolderEntryInfo> entries;

	@Prerender
	public void prerender() throws Exception {
		super.prerender();
		if (isRedirected()){
			return;
		}
		entrySelection.setEntries(loadFolderEntries());
		entrySelection.processSwitch();
	}

	@SuppressWarnings("unchecked")
	protected List<FolderEntryInfo> loadFolderEntries() {
		if (entries == null) {
			entries = documentService.getFolderEntries(courseInfo, currentFolder);
		}
		return entries;
	}

	private List<FolderEntryInfo> selectedEntries() {
		List<FolderEntryInfo> selected = new ArrayList<FolderEntryInfo>(loadFolderEntries());
		CollectionUtils.filter(selected, new Predicate() {
			public boolean evaluate(Object object) {
				return getEntrySelection().isSelected(object);
			}
		});
		logger.debug("selected " + selected.size() + " files");
		return selected;
	}

	private class DocumentDataProvider extends AbstractPagedTable<FolderEntryInfo> {

		private static final long serialVersionUID = -1886479086904372812L;

		private DataPage<FolderEntryInfo> page;

		@Override
		public DataPage<FolderEntryInfo> getDataPage(int startRow, int pageSize) {
			if (page == null) {
				List<FolderEntryInfo> entries = loadFolderEntries();
				sort(entries);
				page = new DataPage<FolderEntryInfo>(entries.size(), 0, entries);
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
			if (isAscending()) {
				chain.addComparator(new FolderEntryInfoComparator());
			} else {
				chain.addComparator(new ReverseComparator(new FolderEntryInfoComparator()));
			}

			if (StringUtils.isNotBlank(getSortColumn())) {
				chain.addComparator(new PropertyComparator(getSortColumn(), true, isAscending()));
			} else {
				chain.addComparator(new PropertyComparator("name", true, isAscending()));
			}
			Collections.sort(list, chain);
		}

	}

	private static final class FolderEntryInfoComparator implements Comparator<FolderEntryInfo>, Serializable {
		private static final long serialVersionUID = -2966816757063701789L;

		public int compare(FolderEntryInfo info1, FolderEntryInfo info2) {
			if (info1.isFolder() && info2.isFolder() || !info1.isFolder() && !info2.isFolder()) {
				return 0;
			} else {
				return info1.isFolder() ? -1 : 1;
			}
		}
	}
	
	public String cancelMove() {
		moveMode = false;
		return Constants.SUCCESS;
	}

	public String download() throws IOException {
		logger.debug("downloading documents");
		List<FileInfo> files = documentService.allFileEntries(selectedEntries());
		if (!files.isEmpty()) {
			//Storing the zip file name into the session 
			String fileName = courseInfo.getName() + "_documents_" + new SimpleDateFormat("dd.MM.yyyy").format(new Date());
			setSessionBean(Constants.ZIP_FILE_NAME, fileName);
			
			setSessionBean(Constants.DOCUMENTS_SELECTED_FILEENTRIES, files);
			HttpServletResponse response = getResponse();
			response.sendRedirect(getExternalContext().getRequestContextPath() + Constants.ZIP_DOWNLOAD_URL);
			getFacesContext().responseComplete();
			entrySelection.getMap().clear();
		} else {
			addError(i18n("messages_error_no_documents_selected"));
		}
		return Constants.SUCCESS;
	}

	public String delete() {
		List<FolderEntryInfo> entries = selectedEntries();
		if (!entries.isEmpty()) {
			logger.debug("deleting documents:");
			setSessionBean(Constants.DOCUMENTS_SELECTED_FOLDERENTRIES, entries);
			entrySelection.getMap().clear();
			return Constants.DOCUMENTS_REMOVE_FOLDERENTRY_PAGE;
		} else {
			addError(i18n("messages_error_no_documents_selected"));
		}
		return Constants.SUCCESS;
	}

	public String newFolder() {
		logger.debug("create new folder");
		setBean(Constants.DOCUMENTS_SELECTED_FOLDER, new FolderInfo());
		return Constants.DOCUMENTS_EDIT_FOLDER_PAGE;
	}

	public String newFile() {
		logger.debug("create new file");
		setBean(Constants.DOCUMENTS_SELECTED_FILEENTRY, new FileInfo());
		removeSessionBean(Constants.UPLOADED_FILE);
		return Constants.DOCUMENTS_EDIT_FILEENTRY_PAGE;
	}

	public String editFolderEntry() {
		logger.debug("editing folder entry");
		FolderEntryInfo entry = data.getRowData();
		if (entry.isFolder()) {
			FolderInfo selectedFolder = documentService.getFolder(entry);
			setBean(Constants.DOCUMENTS_SELECTED_FOLDER, selectedFolder);
			return Constants.DOCUMENTS_EDIT_FOLDER_PAGE;
		} else {
			FileInfo selectedFile = documentService.getFileEntry(entry.getId(), false);
			setBean(Constants.DOCUMENTS_SELECTED_FILEENTRY, selectedFile);
			return Constants.DOCUMENTS_EDIT_FILEENTRY_PAGE;
		}
	}

	public DocumentDataProvider getData() {
		return data;
	}

	public void setData(DocumentDataProvider data) {
		this.data = data;
	}

	public FolderEntrySelection getEntrySelection() {
		return entrySelection;
	}

	public void setEntrySelection(FolderEntrySelection selectedEntries) {
		this.entrySelection = selectedEntries;
	}

	public Map<String, Boolean> getAvailableExtensions() {
		if (avaliableExtensions == null) {
			synchronized (DocumentsMainPage.class) {
				avaliableExtensions = new HashMap<String, Boolean>();
				File extImgFolder = new File(getServletContext().getRealPath("/images/filetypes"));
				File files[] = extImgFolder.listFiles();
				for (File file : files) {
					String name = file.getName();
					name = name.substring(0, name.indexOf('.'));
					avaliableExtensions.put(name, true);
				}
			}
		}
		return avaliableExtensions;
	}
	

	public List<SelectItem> getFolderList() {
		if(folderList == null){
			//Get folder list from document service
			List<FolderInfo> allFolderInfos = documentService.getAllSubfolders(courseInfo);
			
			folderList = new ArrayList<SelectItem>();
			for(FolderInfo info: allFolderInfos) {
				if (info != null) {
					//check depth
					StringBuilder depth = new StringBuilder();
					List path = documentService.getFolderPath(info);
					for(int i = 0; i < path.size(); i++) {
						depth = depth.append("/ ");
					}
					String name = info.isRoot() ? i18n("documents_root_folder") : info.getName();
					folderList.add(new SelectItem(info,depth + name ));
				} else {
					SelectItem item = new SelectItem("--");
					item.setDisabled(true);
					folderList.add(item);
				}
			}
		}
		return folderList;
	}
	
	/**
	 * Moves selected FolderEntries to target
	 * Uses documentService.moveFolderEntries();
	 * @return success
	 * @throws DocumentApplicationException 
	 */
	public String moveFolderEntriesToTarget() throws Exception{
		logger.debug("moving file entries");
		List<FolderEntryInfo> selection = selectedEntries();
		if (selection.isEmpty()) {
			addError(i18n("messages_error_no_documents_selected"));
		} else {
			documentService.moveFolderEntries(courseInfo, targetFolder, selectedEntries() );
			addMessage(i18n("documents_move_files"));
			moveMode = false;
			entries = null; 
		}
		return Constants.DOCUMENTS_MAIN_PAGE;
	}

	
	public String switchToMoveMode(){
		setMoveMode(true);
		return Constants.SUCCESS;
	}

	public void setFolderList(List<SelectItem> folderList) {
		this.folderList = folderList;
	}

	public boolean getMoveMode() {
		return moveMode;
	}

	public void setMoveMode(boolean moveMode) {
		this.moveMode = moveMode;
	}
	
	public FolderInfo getTargetFolder() {
		return targetFolder;
	}

	public void setTargetFolder(FolderInfo targetFolder) {
		this.targetFolder = targetFolder;
	}
}
