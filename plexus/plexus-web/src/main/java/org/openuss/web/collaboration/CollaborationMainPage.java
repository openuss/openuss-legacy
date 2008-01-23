package org.openuss.web.collaboration;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Property;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.Prerender;
import org.apache.shale.tiger.view.View;
import org.openuss.collaboration.WorkspaceInfo;
import org.openuss.collaboration.WorkspaceService;
import org.openuss.desktop.DesktopException;
import org.openuss.framework.jsfcontrols.breadcrumbs.BreadCrumb;
import org.openuss.framework.web.jsf.model.AbstractPagedTable;
import org.openuss.framework.web.jsf.model.DataPage;
import org.openuss.lecture.LectureException;
import org.openuss.web.Constants;
import org.openuss.web.course.AbstractCoursePage;

@Bean(name = "views$secured$collaboration$main", scope = Scope.REQUEST)
@View
public class CollaborationMainPage extends AbstractCoursePage {
	
	public static final Logger logger = Logger.getLogger(CollaborationMainPage.class);
	
	@Property(value = "#{workspaceService}")
	protected WorkspaceService workspaceService;
	
	/** The datamodel for all workspaces. */
	private LocalDataModelWorkspaces dataWorkspaces = new LocalDataModelWorkspaces();

	/** If <code>true</code> the page is in editing mode. */
	private Boolean editing = false;
	
	/** workspace that is currently edited. */
	@Property(value="#{"+Constants.COLLABORATION_WORKSPACE_INFO+"}")
	private WorkspaceInfo workspaceInfo = null;
	
	/** Prepares the information needed for rendering. 
	 * @throws Exception */
	@Prerender
	@SuppressWarnings( { "unchecked" })
	public void prerender() throws Exception {
		super.prerender();
		addPageCrumbs();
	}

	/** Adds an additional breadcrumb to the course-crumbs.
	 * 
	 */
	private void addPageCrumbs() {
		BreadCrumb crumb = new BreadCrumb();
		crumb.setLink("");
		crumb.setName(i18n("collaboration_main_header"));
		crumb.setHint(i18n("collaboration_main_header"));

		breadcrumbs.loadCourseCrumbs(courseInfo);
		breadcrumbs.addCrumb(crumb);
	}
	
	/**
	 * Creates a new WorkspaceInfo object and sets it into session scope
	 * 
	 * @return outcome
	 */
	public String addWorkspace() {
		editing = true;
		workspaceInfo = new WorkspaceInfo();
		setSessionBean(Constants.COLLABORATION_WORKSPACE_INFO, workspaceInfo);
		return Constants.SUCCESS;
	}
	
	/**
	 * Set selected workspace into session scope
	 * 
	 * @return outcome
	 * @throws LectureException
	 */
	public String editWorkspace() throws LectureException {
		workspaceInfo = currentWorkspace();
		if (workspaceInfo == null) {
			return Constants.FAILURE;
		}
		// TODO: implement find workspace...
		workspaceInfo = workspaceService.getWorkspace(workspaceInfo.getId());
		setSessionBean(Constants.COLLABORATION_WORKSPACE_INFO, workspaceInfo);
		if (workspaceInfo == null) {
			addWarning(i18n("error_workspace_not_found"));
			return Constants.FAILURE;

		} else {
			logger.debug("selected workspaceInfo " + workspaceInfo.getName());
			editing = true;
			return Constants.SUCCESS;
		}
	}
	
	/**
	 * Saves new workspace or updates changes to workspace and removes current
	 * workspace selection from session scope.
	 * 
	 * @return outcome
	 */
	public String saveWorkspace() throws DesktopException, LectureException {
		logger.debug("Starting method saveWorkspace()");
		// TODO implement save/update
		if (workspaceInfo.getId() == null) {

			// FIXME: improve WorkspaceService!!
			workspaceInfo.setCourseId(courseInfo.getId());
			workspaceService.createWorkspace(workspaceInfo);

			addMessage(i18n("collaboration_message_add_workspace_succeed"));
		} else {
			workspaceService.updateWorkspace(workspaceInfo);
			addMessage(i18n("collaboration_message_persist_workspace_succeed"));
		}

		removeSessionBean(Constants.COLLABORATION_WORKSPACE_INFO);
		workspaceInfo = null;
		editing = false;
		return Constants.SUCCESS;
	}
	
	/**
	 * Cancels editing or adding of current workspace
	 * 
	 * @return outcome
	 */
	public String cancelWorkspace() {
		logger.debug("cancelWorkspace()");
		removeSessionBean(Constants.COLLABORATION_WORKSPACE_INFO);
		this.editing = false;
		return Constants.SUCCESS;
	}
	
	/**
	 * Store the selected workspace into session scope and go to workspace
	 * remove confirmation page. 
	 * 
	 * @return outcome
	 */
	public String selectWorkspaceAndConfirmRemove() {
		logger.debug("Starting method selectWorkspaceAndConfirmRemove");
		WorkspaceInfo currentWorkspace = currentWorkspace();
		logger.debug("Returning to method selectWorkspaceAndConfirmRemove");
		logger.debug(currentWorkspace.getId());
		setSessionBean(Constants.COLLABORATION_WORKSPACE_INFO, currentWorkspace);

		return Constants.COLLABORATION_WORKSPACE_CONFIRM_REMOVE_PAGE;
	}
	
	//// getter/setter methods ////////////////////////////////////////////////

	private WorkspaceInfo currentWorkspace() {
		WorkspaceInfo workspace = this.dataWorkspaces.getRowData();
		return workspace;
	}
	
	public Boolean getEditing() {
		return editing;
	}
	public void setEditing(Boolean editing) {
		this.editing = editing;
	}

	public WorkspaceInfo getWorkspaceInfo() {
		return workspaceInfo;
	}
	public void setWorkspaceInfo(WorkspaceInfo workspaceInfo) {
		this.workspaceInfo = workspaceInfo;
	}
	
	public LocalDataModelWorkspaces getDataWorkspaces() {
		return dataWorkspaces;
	}
	public void setDataWorkspaces(LocalDataModelWorkspaces dataWorkspaces) {
		this.dataWorkspaces = dataWorkspaces;
	}
	
	public WorkspaceService getWorkspaceService() {
		return workspaceService;
	}
	public void setWorkspaceService(WorkspaceService workspaceService) {
		this.workspaceService = workspaceService;
	}
	
	/////// Inner classes ////////////////////////////////////////////////////
	
	private class LocalDataModelWorkspaces extends AbstractPagedTable<WorkspaceInfo> {
		private static final long serialVersionUID = -6289875618529435428L;

		private DataPage<WorkspaceInfo> page;

		@Override
		@SuppressWarnings( { "unchecked" })
		public DataPage<WorkspaceInfo> getDataPage(int startRow, int pageSize) {
			if (page == null) {
				// TODO: implement!
				List<WorkspaceInfo> workspaces = new ArrayList<WorkspaceInfo>(workspaceService
						.findWorkspacesByCourse(courseInfo.getId()));
				
				sort(workspaces);
				page = new DataPage<WorkspaceInfo>(workspaces.size(), 0, workspaces);
			}
			return page;
		}
	}
	
}
