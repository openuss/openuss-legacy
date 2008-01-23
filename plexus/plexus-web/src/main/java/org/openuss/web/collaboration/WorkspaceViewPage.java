package org.openuss.web.collaboration;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.Prerender;
import org.apache.shale.tiger.view.View;
import org.openuss.framework.jsfcontrols.breadcrumbs.BreadCrumb;
import org.openuss.framework.web.jsf.model.AbstractPagedTable;
import org.openuss.framework.web.jsf.model.DataPage;

@Bean(name = "views$secured$collaboration$workspaceview", scope = Scope.REQUEST)
@View
public class WorkspaceViewPage extends AbstractCollaborationPage {
	
	public static final Logger logger = Logger.getLogger(WorkspaceViewPage.class);
	
	/** The datamodel for all workspaces. */
	private LocalDataModelFiles dataWorkspaces = new LocalDataModelFiles();
	
	/** Prepares the information needed for rendering. 
	 * @throws Exception */
	@Prerender
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
	
	//// getter/setter methods ////////////////////////////////////////////////
	
	public LocalDataModelFiles getDataWorkspaces() {
		return dataWorkspaces;
	}

	public void setDataWorkspaces(LocalDataModelFiles dataWorkspaces) {
		this.dataWorkspaces = dataWorkspaces;
	}
	
	/////// Inner classes ////////////////////////////////////////////////////
		
	private class LocalDataModelFiles extends AbstractPagedTable<WorkspaceEntryInfo> {
		private static final long serialVersionUID = -6289875618529435428L;

		private DataPage<WorkspaceEntryInfo> page;

		@Override
		@SuppressWarnings( { "unchecked" })
		public DataPage<WorkspaceEntryInfo> getDataPage(int startRow, int pageSize) {
			if (page == null) {
				// TODO: implement!
				/*List<CourseTypeInfo> courseTypes = new ArrayList<CourseTypeInfo>(courseTypeService
						.findCourseTypesByInstitute(instituteInfo.getId()));*/
				List<WorkspaceEntryInfo> workspaces = new ArrayList<WorkspaceEntryInfo>();
				workspaces.add(new WorkspaceEntryInfo(1l, "VOFI", "vofi.pdf", "VOFI", "pdf", new Date(), new Date(0), false));
				
				sort(workspaces);
				page = new DataPage<WorkspaceEntryInfo>(workspaces.size(), 0, workspaces);
			}
			return page;
		}
		
	}	
	
}
