package org.openuss.web.collaboration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

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
import org.openuss.framework.jsfcontrols.breadcrumbs.BreadCrumb;
import org.openuss.framework.web.jsf.model.AbstractPagedTable;
import org.openuss.framework.web.jsf.model.DataPage;
import org.openuss.web.Constants;
import org.openuss.web.PageLinks;
import org.openuss.web.documents.FolderEntrySelection;
import org.springframework.beans.support.PropertyComparator;

/**
 * Controller for the workspaceview.xhtml view.
 * 
 * @author Projektseminar WS 07/08, Team Collaboration
 * 
 */
@Bean(name = "views$secured$collaboration$workspaceview", scope = Scope.REQUEST)
@View
public class WorkspaceViewPage extends AbstractCollaborationPage {

	private static final Logger LOGGER = Logger.getLogger(WorkspaceViewPage.class);

	private DocumentDataProvider data = new DocumentDataProvider();

	@Property(value = "#{" + Constants.COLLABORATION_FOLDERENTRY_SELECTION + "}")
	private FolderEntrySelection entrySelection;

	/*
	 * Target Folder to which selected Entries will be moved
	 */
	private FolderInfo targetFolder;

	/*
	 * Target folder list
	 */
	private List<SelectItem> folderList;

	private transient List<FolderEntryInfo> entries;

	private boolean moveMode = false;

	/**
	 * Prepares the information needed for rendering.
	 * 
	 * @throws Exception
	 * @throws Exception
	 */
	@Prerender
	public void prerender() throws Exception {
		super.prerender();

		if (currentFolder == null && workspaceInfo == null) {
			redirect(Constants.OUTCOME_BACKWARD);
		} else {
			currentFolder = retrieveActualFolder();
		}
		setSessionAttribute(Constants.COLLABORATION_CURRENT_FOLDER, currentFolder);

		entrySelection.setEntries(loadFolderEntries());
		entrySelection.processSwitch();

		addPageCrumbs();
	}

	/**
	 * Adds an additional breadcrumb to the course-crumbs.
	 */
	private void addPageCrumbs() {
		BreadCrumb crumb = new BreadCrumb();
		crumb.setLink(PageLinks.COLLABORATION_MAIN);
		crumb.setName(i18n("collaboration_main_header"));
		crumb.setHint(i18n("collaboration_main_header"));
		crumb.addParameter("course", courseInfo.getId());

		breadcrumbs.loadCourseCrumbs(courseInfo);
		breadcrumbs.addCrumb(crumb);

		for (FolderInfo folder : getCurrentPath()) {
			if (folder.isRoot()) {
				crumb = new BreadCrumb(workspaceInfo.getName()); 
			} else {
				crumb = new BreadCrumb(folder.getName(), folder.getDescription()); 
			}
			crumb.setLink(PageLinks.COLLABORATION_WORKSPACE);
			crumb.addParameter("workspace", workspaceInfo.getId());
			crumb.addParameter("folder", folder.getId());
			breadcrumbs.addCrumb(crumb);
		}
	}

	@SuppressWarnings("unchecked")
	protected List<FolderEntryInfo> loadFolderEntries() {
		if (entries == null) {
			entries = documentService.getFolderEntries(workspaceInfo, currentFolder);
		}
		return entries;
	}

	/**
	 * Downloads the selected documents.
	 * 
	 * @return success
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	public String download() throws IOException {
		LOGGER.debug("downloading documents");
		final List<FileInfo> files = documentService.allFileEntries(selectedEntries());

		if (!files.isEmpty()) {
			this.workspaceInfo = workspaceService.getWorkspace(workspaceInfo.getId());
			final String fileName = this.workspaceInfo.getName();
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

	/**
	 * Deletes the selected entries
	 * 
	 * @return success
	 */
	public String delete() {
		final List<FolderEntryInfo> entries = selectedEntries();
		if (!entries.isEmpty()) {
			LOGGER.debug("deleting documents:");
			setSessionBean(Constants.COLLABORATION_SELECTED_FOLDERENTRIES, entries);
			entrySelection.getMap().clear();
			return Constants.COLLABORATION_REMOVE_FOLDERENTRY_PAGE;
		} else {
			addError(i18n("messages_error_no_documents_selected"));
		}

		return Constants.SUCCESS;
	}

	/**
	 * Moves selected FolderEntries to target
	 * 
	 * @return success
	 * @throws DocumentApplicationException
	 */
	public String moveFolderEntriesToTarget() throws DocumentApplicationException {
		List<FolderEntryInfo> selection = selectedEntries();
		if (selection.isEmpty()) {
			addError(i18n("messages_error_no_documents_selected"));
		} else {
			documentService.moveFolderEntries(workspaceInfo, targetFolder, selectedEntries() );
			addMessage(i18n("documents_move_files"));
			entries = null;
			moveMode = false;
		}
		return Constants.COLLABORATION_MAIN_PAGE;
	}

	/**
	 * Creates a new Folder.
	 * 
	 * @return COLLABORATION_EDIT_FOLDER_PAGE
	 */
	public String newFolder() {
		LOGGER.debug("create new folder");
		setSessionBean(Constants.COLLABORATION_SELECTED_FOLDER, new FolderInfo());
		return Constants.COLLABORATION_EDIT_FOLDER_PAGE;
	}

	/**
	 * Creates a new File.
	 * 
	 * @return COLLABORATION_EDIT_FILEENTRY_PAGE
	 */
	public String newFile() {
		LOGGER.debug("create new file");
		setSessionBean(Constants.COLLABORATION_SELECTED_FILEENTRY, new FileInfo());
		removeSessionBean(Constants.UPLOADED_FILE);
		return Constants.COLLABORATION_EDIT_FILEENTRY_PAGE;
	}

	/**
	 * edits a folder entry
	 * 
	 * @return COLLABORATION_EDIT_FOLDER_PAGE if a folder,
	 *         COLLABORATION_EDIT_FILEENTRY_PAGE if file
	 */
	public String editFolderEntry() {
		LOGGER.debug("editing folder entry");
		final FolderEntryInfo entry = data.getRowData();
		if (entry.isFolder()) {
			FolderInfo selectedFolder = documentService.getFolder(entry);
			setSessionBean(Constants.COLLABORATION_SELECTED_FOLDER, selectedFolder);
			return Constants.COLLABORATION_EDIT_FOLDER_PAGE;
		} else {
			FileInfo selectedFile = documentService.getFileEntry(entry.getId(), false);
			setSessionBean(Constants.COLLABORATION_SELECTED_FILEENTRY, selectedFile);
			return Constants.COLLABORATION_EDIT_FILEENTRY_PAGE;
		}
	}

	public String switchToMoveMode() {
		setMoveMode(true);
		return Constants.SUCCESS;
	}

	private List<FolderEntryInfo> selectedEntries() {
		final List<FolderEntryInfo> selected = new ArrayList<FolderEntryInfo>(loadFolderEntries());
		CollectionUtils.filter(selected, new Predicate() {
			public boolean evaluate(Object object) {
				return getEntrySelection().isSelected(object);
			}
		});
		LOGGER.debug("selected " + selected.size() + " files");
		return selected;
	}

	/**
	 * Store the selected FolderEntry into session scope and go to Workspace
	 * remove confirmation page.
	 * 
	 * @return outcome
	 */
	public String selectFileAndConfirmRemove() {
		LOGGER.debug("Starting method selectFileAndConfirmRemove");
		FolderEntryInfo entry = data.getRowData();
		LOGGER.debug("Returning to method selectFileAndConfirmRemove");
		LOGGER.debug(entry.getId());
		setSessionBean(Constants.COLLABORATION_SELECTED_FOLDERENTRIES, Arrays.asList(new FolderEntryInfo[] { entry }));

		return Constants.COLLABORATION_REMOVE_FOLDERENTRY_PAGE;
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

	public FolderInfo getTargetFolder() {
		return targetFolder;
	}

	public void setTargetFolder(FolderInfo targetFolder) {
		this.targetFolder = targetFolder;
	}

	/**
	 * List of a subfolders of the workspace
	 * 
	 * @return List of all subfolders
	 */
	public List<SelectItem> getFolderList() {
		if (folderList == null) {
			// Get folder list from document service.
			List<FolderInfo> allFolderInfos = documentService.getAllSubfolders(workspaceInfo);
			folderList = new ArrayList<SelectItem>();
			for (FolderInfo info : allFolderInfos) {
				if (info != null) {
					List path = documentService.getFolderPath(info);
					String name = info.isRoot() ? i18n("documents_root_folder") : info.getName();
					folderList.add(new SelectItem(info, pathDepth(path) + name));
				} else {
					SelectItem item = new SelectItem("--");
					item.setDisabled(true);
					folderList.add(item);
				}
			}
		}
		return folderList;
	}

	private String pathDepth(List path) {
		final StringBuilder depth = new StringBuilder();
		for (int i = 0; i < path.size(); i++) {
			depth.append("/ ");
		}
		return depth.toString();
	}

	public void setFolderList(List<SelectItem> folderList) {
		this.folderList = folderList;
	}

	public boolean isMoveMode() {
		return moveMode;
	}

	public void setMoveMode(boolean moveMode) {
		this.moveMode = moveMode;
	}

	/**
	 * DataProvider for the datatable which lists the Folders or Files.
	 */
	private class DocumentDataProvider extends AbstractPagedTable<FolderEntryInfo> {

		private static final long serialVersionUID = -1886479086904372812L;

		private DataPage<FolderEntryInfo> page;

		@Override
		public DataPage<FolderEntryInfo> getDataPage(int startRow, int pageSize) {
			if (page == null) {
				final List<FolderEntryInfo> entries = loadFolderEntries();
				sort(entries);
				page = new DataPage<FolderEntryInfo>(entries.size(), 0, entries);
			}
			return page;
		}

		/**
		 * Default property sort method.
		 * 
		 * @param list
		 *            List of FolderEntryInfo objects.
		 */
		@SuppressWarnings("unchecked")
		@Override
		protected void sort(List<FolderEntryInfo> list) {
			final ComparatorChain chain = new ComparatorChain();
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
					return info1.isFolder() ? -1 : 1;
				}
			}

		};
	}

	/**
	 * Cancel Move Mode
	 * @return SUCCESS
	 */
	public String cancelMove() {
		moveMode = false;
		return Constants.SUCCESS;
	}



}
