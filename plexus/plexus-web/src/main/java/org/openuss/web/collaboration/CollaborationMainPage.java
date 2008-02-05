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
import org.openuss.lecture.CourseMemberInfo;
import org.openuss.lecture.LectureException;
import org.openuss.web.Constants;

@Bean(name = "views$secured$collaboration$main", scope = Scope.REQUEST)
@View
public class CollaborationMainPage extends AbstractCollaborationPage {
	
	public static final Logger logger = Logger.getLogger(CollaborationMainPage.class);
	
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
	@SuppressWarnings("unchecked")
	public String editWorkspace() throws LectureException {
		workspaceInfo = currentWorkspace();
		if (workspaceInfo == null) {
			return Constants.FAILURE;
		}
		workspaceInfo = workspaceService.getWorkspace(workspaceInfo.getId());
		setSessionBean(Constants.COLLABORATION_WORKSPACE_INFO, workspaceInfo);
		if (workspaceInfo == null) {
			addWarning(i18n("error_workspace_not_found"));
			return Constants.FAILURE;
		} else {
			logger.debug("selected workspaceInfo " + workspaceInfo.getName());
			editing = true;
			
			// get mapped users
			List<CourseMemberInfo> courseMembers = courseService.getParticipants(courseInfo);
			List<CourseMemberInfo> workspaceMembers = workspaceService.findWorkspaceMembers(workspaceInfo.getId());
			Map<CourseMemberInfo, Boolean> map = new HashMap<CourseMemberInfo, Boolean>(courseMembers.size());
			for (CourseMemberInfo member : courseMembers) {
				map.put(member, workspaceMembers.contains(member) ? Boolean.TRUE : Boolean.FALSE);
			}
			this.memberSelection.setMap(map);
			
			return Constants.SUCCESS;
		}
	}
	
	/**
	 * Saves new workspace or updates changes to workspace and removes current
	 * workspace selection from session scope.
	 * 
	 * @return outcome
	 */
	@SuppressWarnings("unchecked")
	public String saveWorkspace() throws DesktopException, LectureException {
		logger.debug("Starting method saveWorkspace()");
		if (workspaceInfo.getId() == null) {

			workspaceInfo.setCourseId(courseInfo.getId());
			workspaceService.createWorkspace(workspaceInfo);

			addMessage(i18n("collaboration_message_add_workspace_succeed"));
		} else {
			workspaceService.updateWorkspace(workspaceInfo);
		}
		
		// store mapping
		List<CourseMemberInfo> courseMembers = courseService.getParticipants(courseInfo);
		List<Long> memberIds = new ArrayList<Long>(courseMembers.size());
		for (CourseMemberInfo member : courseMembers) {
			if (this.memberSelection.isSelected(member)) {
				memberIds.add(member.getId());
			}
		}
		workspaceService.updateWorkspaceMembers(memberIds, workspaceInfo.getId());
		
		addMessage(i18n("collaboration_message_persist_workspace_succeed"));

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

		return Constants.COLLABORATION_CONFIRM_REMOVE_PAGE;
	}
	
	/**
	 * Store the selected workspace into session scope and go to workspace main page.
	 * 
	 * @return Outcome
	 */
	public String selectWorkspace() {
		logger.debug("Starting method selectWorkspace");
		WorkspaceInfo workspace = currentWorkspace();
		logger.debug("Returning to method selectWorkspace");
		logger.debug(workspace.getId());
		setSessionBean(Constants.COLLABORATION_WORKSPACE_INFO, workspace);

		return Constants.COLLABORATION_WORKSPACE_PAGE;
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
	
	/////// Inner classes ////////////////////////////////////////////////////
	
	private class LocalDataModelMappedWorkspaces extends AbstractPagedTable<WorkspaceInfo> {
		private static final long serialVersionUID = -6289875618529435428L;

		private DataPage<WorkspaceInfo> page;

		@Override
		@SuppressWarnings( { "unchecked" })
		public DataPage<WorkspaceInfo> getDataPage(int startRow, int pageSize) {
			if (page == null) {
				CourseMemberInfo memberInfo = courseService.getMemberInfo(courseInfo, user);
				List<WorkspaceInfo> workspaces = new ArrayList<WorkspaceInfo>(workspaceService
						.findWorkspacesByCourseAndCourseMember(courseInfo.getId(), 
								memberInfo.getId()));
				
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
		@SuppressWarnings( { "unchecked" })
		public DataPage<WorkspaceInfo> getDataPage(int startRow, int pageSize) {
			if (page == null) {
				List<WorkspaceInfo> workspaces = new ArrayList<WorkspaceInfo>(workspaceService
						.findWorkspacesByCourse(courseInfo.getId()));
				
				sort(workspaces);
				page = new DataPage<WorkspaceInfo>(workspaces.size(), 0, workspaces);
			}
			return page;
		}
	}
	
	private class LocalDataModelCourseMembers extends AbstractPagedTable<CourseMemberInfo> {
		private static final long serialVersionUID = -6289875618529435428L;

		private DataPage<CourseMemberInfo> page;

		@Override
		@SuppressWarnings( { "unchecked" })
		public DataPage<CourseMemberInfo> getDataPage(int startRow, int pageSize) {
			if (page == null) {
				List<CourseMemberInfo> courseMembers = new ArrayList<CourseMemberInfo>(courseService
						.getParticipants(courseInfo));
				
				sort(courseMembers);
				page = new DataPage<CourseMemberInfo>(courseMembers.size(), 0, courseMembers);
			}
			return page;
		}
	}

}
