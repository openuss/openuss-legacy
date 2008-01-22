package org.openuss.web.papersubmission;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Property;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.Prerender;
import org.apache.shale.tiger.view.View;
import org.openuss.framework.jsfcontrols.breadcrumbs.BreadCrumb;
import org.openuss.framework.web.jsf.model.AbstractPagedTable;
import org.openuss.framework.web.jsf.model.DataPage;
import org.openuss.web.Constants;
import org.openuss.web.course.AbstractCoursePage;

@Bean(name = "views$secured$papersubmission$paperview", scope = Scope.REQUEST)
@View
public class PaperSubmissionViewPage extends AbstractCoursePage {
	
	public static final Logger logger = Logger.getLogger(PaperSubmissionViewPage.class);
	
	/** The datamodel for all submissions. */
	private LocalDataModelSubmissions dataSubmissions = new LocalDataModelSubmissions();
	
	/** The datamodel for all submission files. */
	private LocalDataModelSubmissionFiles dataSubmissionFiles = new LocalDataModelSubmissionFiles();
	
	private List<SubmissionInfo> entries;
	
	@Property(value = "#{" + Constants.PAPERSUBMISSION_SUBMISSION_SELECTION + "}")
	private SubmissionSelection entrySelection;
	
	/** Prepares the information needed for rendering. 
	 * @throws Exception */
	@Prerender
	public void prerender() throws Exception {
		super.prerender();
		addPageCrumbs();
		
		this.entrySelection.setEntries(loadSubmissionEntries());
		this.entrySelection.processSwitch();
	}

	/** Adds an additional breadcrumb to the course-crumbs.
	 * 
	 */
	private void addPageCrumbs() {
		BreadCrumb crumb = new BreadCrumb();
		crumb.setLink("");
		crumb.setName(i18n("papersubmission_paper_header"));
		crumb.setHint(i18n("papersubmission_paper_header"));

		breadcrumbs.loadCourseCrumbs(courseInfo);
		breadcrumbs.addCrumb(crumb);
	}
	
	private List<SubmissionInfo> loadSubmissionEntries() {
		if (this.entries == null) {
			this.entries = new ArrayList<SubmissionInfo>();
			this.entries.add(new SubmissionInfo(1l, "Donald Duck", "late"));
			this.entries.add(new SubmissionInfo(2l, "Minnie Maus", "missing"));
			this.entries.add(new SubmissionInfo(3l, "Dagobert Duck", "ok"));
		}
		return this.entries;
	}
	
	//// getter/setter methods ////////////////////////////////////////////////
	
	public LocalDataModelSubmissions getDataSubmissions() {
		return this.dataSubmissions;
	}
	public LocalDataModelSubmissionFiles getDataSubmissionFiles() {
		return this.dataSubmissionFiles;
	}
	
	public SubmissionSelection getEntrySelection() {
		return entrySelection;
	}
	public void setEntrySelection(SubmissionSelection entrySelection) {
		this.entrySelection = entrySelection;
	}
	
	/////// Inner classes ////////////////////////////////////////////////////
	
	private class LocalDataModelSubmissions extends AbstractPagedTable<SubmissionInfo> {
		private static final long serialVersionUID = -6289875618529435428L;

		private DataPage<SubmissionInfo> page;

		@Override
		@SuppressWarnings( { "unchecked" })
		public DataPage<SubmissionInfo> getDataPage(int startRow, int pageSize) {
			if (page == null) {
				// TODO: implement!
				/*List<CourseTypeInfo> courseTypes = new ArrayList<CourseTypeInfo>(courseTypeService
						.findCourseTypesByInstitute(instituteInfo.getId()));*/
				List<SubmissionInfo> submissions = loadSubmissionEntries();
				
				sort(submissions);
				page = new DataPage<SubmissionInfo>(submissions.size(), 0, submissions);
			}
			return page;
		}
	}
	private class LocalDataModelSubmissionFiles extends AbstractPagedTable<SubmissionFileInfo> {
		private static final long serialVersionUID = -6289875618529435428L;

		private DataPage<SubmissionFileInfo> page;

		@Override
		@SuppressWarnings( { "unchecked" })
		public DataPage<SubmissionFileInfo> getDataPage(int startRow, int pageSize) {
			if (page == null) {
				// TODO: implement!
				/*List<CourseTypeInfo> courseTypes = new ArrayList<CourseTypeInfo>(courseTypeService
						.findCourseTypesByInstitute(instituteInfo.getId()));*/
				List<SubmissionFileInfo> submissions = new ArrayList<SubmissionFileInfo>();
				submissions.add(new SubmissionFileInfo(1l, "Lösung", "pdf", 1234l, "21.01.2008 10:03", "21.01.2008 10:15"));
				
				sort(submissions);
				page = new DataPage<SubmissionFileInfo>(submissions.size(), 0, submissions);
			}
			return page;
		}
	}
	
}
