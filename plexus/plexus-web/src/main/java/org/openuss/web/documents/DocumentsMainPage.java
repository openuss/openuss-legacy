package org.openuss.web.documents;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

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
import org.openuss.framework.web.jsf.model.AbstractPagedTable;
import org.openuss.framework.web.jsf.model.DataPage;
import org.openuss.web.Constants;
import org.springframework.beans.support.PropertyComparator;

@Bean(name = "views$secured$documents$documents", scope = Scope.REQUEST)
@View
public class DocumentsMainPage extends AbstractDocumentPage {
	private static final Logger logger = Logger.getLogger(DocumentsMainPage.class);

	private DocumentDataProvider data = new DocumentDataProvider();

	@Property(value = "#{" + Constants.DOCUMENTS_FOLDERENTRY_SELECTION + "}")
	private FolderEntrySelection entrySelection;

	private List<FolderEntryInfo> entries;

	@Prerender
	public void prerender() throws Exception {
		super.prerender();
		entrySelection.setEntries(loadFolderEntries());
		entrySelection.processSwitch();
	}

	private List<FolderEntryInfo> loadFolderEntries() {
		if (entries == null) {
			entries = documentService.getFolderEntries(courseInfo, currentFolder);
		}
		return entries;
	}

	private List<FolderEntryInfo> selectedEntries() {
		List<FolderEntryInfo> selected = new ArrayList<FolderEntryInfo>(loadFolderEntries());
		CollectionUtils.filter(selected, new Predicate() {
			public boolean evaluate(Object object) {
				return entrySelection.isSelected(object);
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

	public String download() throws IOException {
		logger.debug("downloading documents");
		List<FileInfo> files = documentService.allFileEntries(selectedEntries());
		if (files.size() > 0) {
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
		if (entries.size() > 0) {
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
		setSessionBean(Constants.DOCUMENTS_SELECTED_FOLDER, new FolderInfo());
		return Constants.DOCUMENTS_EDIT_FOLDER_PAGE;
	}

	public String newFile() {
		logger.debug("create new file");
		setSessionBean(Constants.DOCUMENTS_SELECTED_FILEENTRY, new FileInfo());
		removeSessionBean(Constants.UPLOADED_FILE);
		return Constants.DOCUMENTS_EDIT_FILEENTRY_PAGE;
	}

	public String editFolderEntry() {
		logger.debug("editing folder entry");
		FolderEntryInfo entry = data.getRowData();
		if (entry.isFolder()) {
			FolderInfo selectedFolder = documentService.getFolder(entry);
			setSessionBean(Constants.DOCUMENTS_SELECTED_FOLDER, selectedFolder);
			return Constants.DOCUMENTS_EDIT_FOLDER_PAGE;
		} else {
			FileInfo selectedFile = documentService.getFileEntry(entry.getId(), false);
			setSessionBean(Constants.DOCUMENTS_SELECTED_FILEENTRY, selectedFile);
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

}