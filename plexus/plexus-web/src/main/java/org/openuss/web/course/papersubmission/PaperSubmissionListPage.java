package org.openuss.web.course.papersubmission;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Property;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.Prerender;
import org.apache.shale.tiger.view.View;
import org.openuss.desktop.DesktopException;
import org.openuss.framework.jsfcontrols.breadcrumbs.BreadCrumb;
import org.openuss.framework.web.jsf.model.AbstractPagedTable;
import org.openuss.framework.web.jsf.model.DataPage;
import org.openuss.lecture.LectureException;
import org.openuss.web.Constants;
import org.openuss.web.course.AbstractCoursePage;

@Bean(name = "views$secured$course$papersubmission$paperlist", scope = Scope.REQUEST)
@View
public class PaperSubmissionListPage extends AbstractCoursePage {
	
	public static final Logger logger = Logger.getLogger(PaperSubmissionListPage.class);
	
	/** The datamodel for all papers. */
	private LocalDataModelPapers dataPapers = new LocalDataModelPapers();

	/** If <code>true</code> the page is in editing mode. */
	private Boolean editing = false;
	
	/** paper that is currently edited. */
	@Property(value="#{"+Constants.PAPERSUBMISSION_PAPER_INFO+"}")
	private PaperInfo paperInfo = null;
	
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
		crumb.setName(i18n("papersubmission_paperlist_header"));
		crumb.setHint(i18n("papersubmission_paperlist_header"));

		breadcrumbs.loadCourseCrumbs(courseInfo);
		breadcrumbs.addCrumb(crumb);
	}
	
	/**
	 * Creates a new PaperInfo object and sets it into session scope
	 * 
	 * @return outcome
	 */
	public String addPaper() {
		editing = true;
		paperInfo = new PaperInfo();
		setSessionBean(Constants.PAPERSUBMISSION_PAPER_INFO, paperInfo);
		return Constants.SUCCESS;
	}
	
	/**
	 * Set selected paper into session scope
	 * 
	 * @return outcome
	 * @throws LectureException
	 */
	public String editPaper() throws LectureException {
		paperInfo = currentPaper();
		if (paperInfo == null) {
			return Constants.FAILURE;
		}
		// TODO: implement find paper...
		//paperInfo = courseTypeService.findCourseType(paperInfo.getId());
		paperInfo = new PaperInfo(1l, 1l, "VOFI mit Steuern", "Bauen Sie einen VOFI mit Steuern!");
		setSessionBean(Constants.PAPERSUBMISSION_PAPER_INFO, paperInfo);
		if (paperInfo == null) {
			addWarning(i18n("error_paper_not_found"));
			return Constants.FAILURE;

		} else {
			logger.debug("selected paperInfo " + paperInfo.getName());
			editing = true;
			return Constants.SUCCESS;
		}
	}
	
	/**
	 * Saves new paper or updates changes to paper and removes current
	 * paper selection from session scope.
	 * 
	 * @return outcome
	 */
	public String savePaper() throws DesktopException, LectureException {
		logger.debug("Starting method savePaper()");
		// TODO implement save/update
		if (paperInfo.getId() == null) {

			//paperInfo.setInstituteId(instituteInfo.getId());
			//courseTypeService.create(courseTypeInfo);

			addMessage(i18n("papersubmission_message_add_paper_succeed"));
		} else {
			//courseTypeService.update(courseTypeInfo);
			addMessage(i18n("papersubmission_message_persist_paper_succeed"));
		}

		removeSessionBean(Constants.PAPERSUBMISSION_PAPER_INFO);
		paperInfo = null;
		editing = false;
		return Constants.SUCCESS;
	}
	
	/**
	 * Cancels editing or adding of current paper
	 * 
	 * @return outcome
	 */
	public String cancelPaper() {
		logger.debug("cancelPaper()");
		removeSessionBean(Constants.PAPERSUBMISSION_PAPER_INFO);
		this.editing = false;
		return Constants.SUCCESS;
	}
	
	/**
	 * Store the selected paper into session scope and go to paper
	 * remove confirmation page. 
	 * 
	 * @return outcome
	 */
	public String selectPaperAndConfirmRemove() {
		logger.debug("Starting method selectPaperAndConfirmRemove");
		PaperInfo currentPaper = currentPaper();
		logger.debug("Returning to method selectPaperAndConfirmRemove");
		logger.debug(currentPaper.getId());
		setSessionBean(Constants.PAPERSUBMISSION_PAPER_INFO, currentPaper);

		return Constants.PAPER_CONFIRM_REMOVE_PAGE;
	}
	
	//// getter/setter methods ////////////////////////////////////////////////

	private PaperInfo currentPaper() {
		PaperInfo paper = this.dataPapers.getRowData();
		return paper;
	}
	
	public Boolean getEditing() {
		return editing;
	}
	public void setEditing(Boolean editing) {
		this.editing = editing;
	}
	

	public PaperInfo getPaperInfo() {
		return paperInfo;
	}
	public void setPaperInfo(PaperInfo paperInfo) {
		this.paperInfo = paperInfo;
	}
	
	public LocalDataModelPapers getDataPapers() {
		return dataPapers;
	}
	public void setDataPapers(LocalDataModelPapers dataPapers) {
		this.dataPapers = dataPapers;
	}
	
	/////// Inner classes ////////////////////////////////////////////////////
	
	private class LocalDataModelPapers extends AbstractPagedTable<PaperInfo> {
		private static final long serialVersionUID = -6289875618529435428L;

		private DataPage<PaperInfo> page;

		@Override
		@SuppressWarnings( { "unchecked" })
		public DataPage<PaperInfo> getDataPage(int startRow, int pageSize) {
			if (page == null) {
				// TODO: implement!
				/*List<CourseTypeInfo> courseTypes = new ArrayList<CourseTypeInfo>(courseTypeService
						.findCourseTypesByInstitute(instituteInfo.getId()));*/
				List<PaperInfo> papers = new ArrayList<PaperInfo>();
				papers.add(new PaperInfo(1l, 1l, "VOFI ohne Steuern", "Vofi ohne Steuern"));
				
				sort(papers);
				page = new DataPage<PaperInfo>(papers.size(), 0, papers);
			}
			return page;
		}
	}
	
}
