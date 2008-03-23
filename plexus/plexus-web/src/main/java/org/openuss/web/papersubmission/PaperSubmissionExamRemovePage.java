package org.openuss.web.papersubmission;

import org.apache.log4j.Logger;
import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.Prerender;
import org.apache.shale.tiger.view.View;
import org.openuss.framework.jsfcontrols.breadcrumbs.BreadCrumb;
import org.openuss.web.Constants;

/**
 * Backing Bean for examremove.xhtml.
 * @author Projektseminar WS 07/08, Team Collaboration
 *
 */
@Bean(name = "views$secured$papersubmission$examremove", scope = Scope.REQUEST)
@View
public class PaperSubmissionExamRemovePage extends AbstractPaperSubmissionPage {

	private static final Logger LOGGER = Logger.getLogger(PaperSubmissionExamRemovePage.class);
	
	@Prerender
	public void prerender() throws Exception { // NOPMD by Administrator on 13.03.08 12:56
		super.prerender();
		addPageCrumb();		
	}
	
	private void addPageCrumb() {
		breadcrumbs.loadCourseCrumbs(courseInfo);
		
		BreadCrumb crumb = new BreadCrumb();
		crumb.setName(i18n("papersubmission_remove_header"));
		crumb.setHint(i18n("papersubmission_remove_header"));
		breadcrumbs.addCrumb(crumb);
	}	
	
	/**
	 * Delete exam including all data
	 * @return outcome
	 **/
	public String removeExam() {
		try {
			paperSubmissionService.removeExam(examInfo.getId());
			setSessionBean(Constants.PAPERSUBMISSION_EXAM_INFO, null);
			setSessionBean(Constants.PAPERSUBMISSION_PAPER_INFO, null);
			addMessage(i18n("papersubmission_removed_succeed"));
			return Constants.PAPERSUBMISSION_EXAMLIST_PAGE;
		} catch (Exception e) {
			LOGGER.error("Removing exam failed.", e);
			addMessage(i18n("papersubmission_exam_cannot_be_removed"));
			return Constants.PAPERSUBMISSION_EXAMLIST_PAGE;
		}
	}

	
}
