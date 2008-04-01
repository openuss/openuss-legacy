package org.openuss.web.collaboration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Property;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.Prerender;
import org.apache.shale.tiger.view.View;
import org.openuss.collaboration.WorkspaceInfo;
import org.openuss.desktop.DesktopException;
import org.openuss.framework.jsfcontrols.breadcrumbs.BreadCrumb;
import org.openuss.framework.web.jsf.model.AbstractPagedTable;
import org.openuss.framework.web.jsf.model.DataPage;
import org.openuss.lecture.LectureException;
import org.openuss.security.Authority;
import org.openuss.security.Group;
import org.openuss.security.UserInfo;
import org.openuss.web.Constants;

/**
 * Controller for the main.xhtml view.
 * 
 * @author  Projektseminar WS 07/08, Team Collaboration
 *
 */
@Bean(name = "views$secured$collaboration$main", scope = Scope.REQUEST)
@View
public class WorkspaceMainPage extends AbstractCollaborationPage {
	
	private static final Logger LOGGER = Logger.getLogger(WorkspaceMainPage.class);
	
	/** The datamodel for all mapped workspaces for this user. */
	private LocalDataModelMappedWorkspaces dataMappedWorkspaces;
	
	/** The datamodel for all workspaces. */
	private LocalDataModelWorkspaces dataWorkspaces;
	
	/** The datamodel for member mapping. */
	private LocalDataModelCourseMembers dataCourseMembers;

	/** If <code>true</code> the page is in editing mode. */
	private Boolean editing = false;
	
	@Property(value = "#{" + Constants.COLLABORATION_WORKSPACE_MEMBER_SELECTION + "}")
	private WorkspaceMemberSelection memberSelection;
	
	/** Prepares the information needed for rendering. 
	 * @throws Exception */
	@Prerender
	@SuppressWarnings( { "unchecked" }) // NOPMD by Administrator on 13.03.08 13:02
	public void prerender() throws Exception { // NOPMD by Administrator on 13.03.08 12:54
		super.prerender();
		
		memberSelection.processSwitch();
		
		addPageCrumbs();
	}

	/** 
	 * Adds an additional breadcrumb to the course-crumbs.
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
	 * Open collaboration main page and remove Folder or Workspace info from session.
	 * 
	 * @return Constants.COLLABORATION_MAIN_PAGE
	 */
	public String open() {
		this.workspaceInfo = null;
		this.currentFolder = null;
		
		return Constants.COLLABORATION_MAIN_PAGE;
	}
	
	/**
	 * Creates a new WorkspaceInfo object and sets it into session scope
	 * @return outcome
	 */
	public String addWorkspace() {
		editing = true;
		workspaceInfo = new WorkspaceInfo();
		return Constants.SUCCESS;
	}
	
	/**
	 * Set selected Workspace into session scope
	 * 
	 * @return outcome
	 * @throws LectureException
	 */
	@SuppressWarnings("unchecked") // NOPMD by Administrator on 13.03.08 13:02
	public String editWorkspace() throws LectureException { // NOPMD by Administrator on 13.03.08 12:55
		workspaceInfo = currentWorkspace();
		if (workspaceInfo == null) {
			return Constants.FAILURE;
		}
		workspaceInfo = workspaceService.getWorkspace(workspaceInfo.getId());
		setSessionBean(Constants.COLLABORATION_WORKSPACE_INFO, workspaceInfo);
		if (workspaceInfo == null) {
			addWarning(i18n("collaboration_error_workspace_not_found"));
			return Constants.FAILURE;
		} else {
			LOGGER.debug("selected workspaceInfo " + workspaceInfo.getName());
			editing = true;
			
			if (this.workspaceInfo.getId() != null) {
				List<UserInfo> members = loadCourseMembers();
				
				List<Long> wsMemberIds = getWorkspaceMemberIds(); // NOPMD by Administrator on 13.03.08 12:55
				
				Map<UserInfo, Boolean> map = new HashMap<UserInfo, Boolean>(members.size());
				for (UserInfo member : members) {
					map.put(member, wsMemberIds.contains(member.getId()) ? Boolean.TRUE : Boolean.FALSE);
				}
				this.memberSelection.setMap(map);
			}
			
			return Constants.SUCCESS;
		}
	}
	
	/**
	 * Saves new Workspace or updates changes to Workspace and removes current
	 * Workspace selection from session scope.
	 * 
	 * @return outcome
	 */
	@SuppressWarnings("unchecked") // NOPMD by Administrator on 13.03.08 13:02
	public String saveWorkspace() throws DesktopException, LectureException { // NOPMD by Administrator on 13.03.08 12:55
		LOGGER.debug("Starting method saveWorkspace()");
		boolean create;
		if (workspaceInfo.getId() == null) {

			workspaceInfo.setDomainId(courseInfo.getId());
			workspaceService.createWorkspace(workspaceInfo);
			
			create = true;
		} else {
			workspaceService.updateWorkspace(workspaceInfo);
			
			create = false;
		}
		
		List<UserInfo> courseMembers = loadCourseMembers();
		List<Long> memberIds = new ArrayList<Long>(courseMembers.size());
		for (UserInfo member : courseMembers) {
			if (this.memberSelection.isSelected(member)) {
				memberIds.add(member.getId());
			}
		}
		workspaceService.updateWorkspaceMembers(memberIds, workspaceInfo.getId());
		
		if (create) {
			addMessage(i18n("collaboration_message_add_workspace_succeed"));
		} else {
			addMessage(i18n("collaboration_message_persist_workspace_succeed"));
		}

		removeSessionBean(Constants.COLLABORATION_WORKSPACE_INFO);
		removeSessionBean(Constants.COLLABORATION_WORKSPACE_MEMBER_SELECTION);
		workspaceInfo = null;
		editing = false;
		return Constants.SUCCESS;
	}
	
	/**
	 * Cancels editing or adding of current Workspace.
	 * 
	 * @return outcome
	 */
	public String cancelWorkspace() {
		LOGGER.debug("cancelWorkspace()");
		removeSessionBean(Constants.COLLABORATION_WORKSPACE_INFO);
		removeSessionBean(Constants.COLLABORATION_WORKSPACE_MEMBER_SELECTION);
		this.editing = false;
		return Constants.SUCCESS;
	}
	
	/**
	 * Store the selected Workspace into session scope and go to Workspace.
	 * remove confirmation page. 
	 * 
	 * @return outcome
	 */
	public String selectWorkspaceAndConfirmRemove() {
		LOGGER.debug("Starting method selectWorkspaceAndConfirmRemove");
		WorkspaceInfo currentWorkspace = currentWorkspace();
		LOGGER.debug("Returning to method selectWorkspaceAndConfirmRemove");
		LOGGER.debug(currentWorkspace.getId());

		return Constants.COLLABORATION_CONFIRM_REMOVE_PAGE;
	}
	
	/**
	 * Store the selected workspace into session scope and go to workspace main page.
	 * 
	 * @return Outcome
	 */
	public String selectWorkspace() {
		LOGGER.debug("Starting method selectWorkspace");
		WorkspaceInfo workspace = currentWorkspace();
		LOGGER.debug("Returning to method selectWorkspace");
		LOGGER.debug(workspace.getId());

		return Constants.COLLABORATION_WORKSPACE_PAGE;
	}

	private WorkspaceInfo currentWorkspace() {
		return this.dataWorkspaces.getRowData();
	}
	
	public Boolean getEditing() {
		return editing;
	}
	public void setEditing(Boolean editing) {
		this.editing = editing;
	}

	public LocalDataModelWorkspaces getDataWorkspaces() {
		if (dataWorkspaces == null) {
			this.dataWorkspaces = new LocalDataModelWorkspaces();
		}
		return dataWorkspaces;
	}
	public void setDataWorkspaces(LocalDataModelWorkspaces dataWorkspaces) {
		this.dataWorkspaces = dataWorkspaces;
	}
	
	public LocalDataModelMappedWorkspaces getDataMappedWorkspaces() {
		if (dataMappedWorkspaces == null) {
			this.dataMappedWorkspaces = new LocalDataModelMappedWorkspaces();
		}
		return dataMappedWorkspaces;
	}
	public void setDataMappedWorkspaces(
			LocalDataModelMappedWorkspaces dataMappedWorkspaces) {
		this.dataMappedWorkspaces = dataMappedWorkspaces;
	}
	
	public WorkspaceMemberSelection getMemberSelection() {
		return memberSelection;
	}
	public void setMemberSelection(WorkspaceMemberSelection memberSelection) {
		this.memberSelection = memberSelection;
	}
	
	public LocalDataModelCourseMembers getDataCourseMembers() {
		if (dataCourseMembers == null) {
			this.dataCourseMembers = new LocalDataModelCourseMembers();
		}
		return dataCourseMembers;
	}
	
	public void setDataCourseMembers(LocalDataModelCourseMembers dataCourseMembers) {
		this.dataCourseMembers = dataCourseMembers;
	}	

	@SuppressWarnings("unchecked") // NOPMD by Administrator on 13.03.08 13:02
	protected List<UserInfo> loadCourseMembers() {
		final Group group = getSecurityService().getGroupByName("GROUP_COURSE_" + this.courseInfo.getId() + "_PARTICIPANTS");
		
		final List<Authority> members = group.getMembers();
		final List<UserInfo> courseMembers = new ArrayList<UserInfo>(members.size());
		for (Authority auth : members) {
			courseMembers.add(getSecurityService().getUser(auth.getId()));
		}
		
		return courseMembers;
	}
	
	@SuppressWarnings("unchecked") // NOPMD by Administrator on 13.03.08 13:02
	private List<Long> getWorkspaceMemberIds() {
		final List<UserInfo> wsMembers = this.workspaceService.findWorkspaceMembers(this.workspaceInfo.getId());
		final List<Long> wsMemberIds = new ArrayList<Long>(wsMembers.size());
		for (UserInfo ui : wsMembers) {
			wsMemberIds.add(ui.getId());
		}
		
		return wsMemberIds;
	}
	
	private class LocalDataModelMappedWorkspaces extends AbstractPagedTable<WorkspaceInfo> {
		
		private static final long serialVersionUID = -6289875618529435428L;

		private DataPage<WorkspaceInfo> page;

		@Override
		@SuppressWarnings( { "unchecked" }) // NOPMD by Administrator on 13.03.08 13:02
		public DataPage<WorkspaceInfo> getDataPage(int startRow, int pageSize) {
			if (page == null) {
				List<WorkspaceInfo> workspaces = new ArrayList<WorkspaceInfo>(getWorkspaceService().findWorkspacesByDomainAndUser(getCourseInfo().getId(), getUser()));
				
				sort(workspaces);
				page = new DataPage<WorkspaceInfo>(workspaces.size(), 0, workspaces);
			}
			
			return page;
		}
	}
	
	private class LocalDataModelWorkspaces extends AbstractPagedTable<WorkspaceInfo> {
		
		private static final long serialVersionUID = -6289875618529435428L;

		private DataPage<WorkspaceInfo> page;

		@Override
		@SuppressWarnings( { "unchecked" }) // NOPMD by Administrator on 13.03.08 13:02
		public DataPage<WorkspaceInfo> getDataPage(int startRow, int pageSize) {
			if (page == null) {
				List<WorkspaceInfo> workspaces = new ArrayList<WorkspaceInfo>(getWorkspaceService().findWorkspacesByDomain(getCourseInfo().getId()));
				
				sort(workspaces);
				page = new DataPage<WorkspaceInfo>(workspaces.size(), 0, workspaces);
			}
			return page;
		}
	}
	
	private class LocalDataModelCourseMembers extends AbstractPagedTable<UserInfo> {
		private static final long serialVersionUID = -6289875618529435428L;

		private DataPage<UserInfo> page;

		@Override
		@SuppressWarnings( { "unchecked" }) // NOPMD by Administrator on 13.03.08 13:02
		public DataPage<UserInfo> getDataPage(int startRow, int pageSize) {
			if (page == null) {
				List<UserInfo> courseMembers = loadCourseMembers();
				
				sort(courseMembers);
				page = new DataPage<UserInfo>(courseMembers.size(), 0, courseMembers);
			}
			return page;
		}

	}

}
