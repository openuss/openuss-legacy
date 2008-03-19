package org.openuss.web.lecture;

import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.Prerender;
import org.apache.shale.tiger.view.View;
import org.openuss.framework.jsfcontrols.breadcrumbs.BreadCrumb;
import org.openuss.lecture.LectureException;
import org.openuss.web.Constants;
import org.openuss.web.PageLinks;

/**
 * Period Edit Page Controller
 * 
 * @author Ingo Dueppe
 * @author Weijun Chen
 * @author Kai Stettner
 * @author Sebastian Roekens
 * 
 */
@Bean(name = "views$secured$lecture$universityperiodedit", scope = Scope.REQUEST)
@View
public class PeriodEditPage extends AbstractPeriodPage {

	@Prerender
	public void prerender() throws LectureException {
		super.prerender();
		if (isRedirected()){
			return;
		}
		addPageCrumb();
	}
	
	private void addPageCrumb() {
		BreadCrumb crumb = new BreadCrumb();
		crumb.setLink(PageLinks.UNIVERSITY_PERIODS);
		crumb.setName(i18n("university_command_periods"));
		
		breadcrumbs.loadUniversityCrumbs(universityInfo);
		breadcrumbs.addCrumb(crumb);
	}
		
	/**
	 * Store changes in the business layer.
	 * 
	 * @return outcome
	 * @throws LectureException
	 */
	public String savePeriod() throws LectureException {
		if (periodInfo.getId() == null) {
			periodInfo.setUniversityId(universityInfo.getId());
			universityService.createPeriod(periodInfo);
			addMessage(i18n("message_created_new_period_succeed"));
		} else {
			universityService.update(periodInfo);
			addMessage(i18n("message_save_period_succeed"));
		}
		return Constants.UNIVERSITY_PERIODS_PAGE;
	}

}
