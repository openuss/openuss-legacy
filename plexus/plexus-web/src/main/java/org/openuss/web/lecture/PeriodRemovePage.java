package org.openuss.web.lecture;

import org.apache.log4j.Logger;
import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.Preprocess;
import org.apache.shale.tiger.view.Prerender;
import org.apache.shale.tiger.view.View;
import org.openuss.framework.jsfcontrols.breadcrumbs.BreadCrumb;
import org.openuss.lecture.LectureException;
import org.openuss.lecture.UniversityServiceException;
import org.openuss.web.Constants;

/**
 * Periods Remove Page Controller
 * 
 * @author Kai Stettner
 * @author Ingo Dueppe
 * @author Sebastian Roekens
 * 
 */
@Bean(name = "views$secured$lecture$universityperiodremove", scope = Scope.REQUEST)
@View
public class PeriodRemovePage extends AbstractPeriodPage {

	private static final Logger logger = Logger.getLogger(PeriodRemovePage.class);

	@Preprocess
	@Override
	public void preprocess() throws Exception {
		super.preprocess();
		if (periodInfo != null && periodInfo.getId()!=null) {
			periodInfo = universityService.findPeriod(periodInfo.getId());
			setBean(Constants.PERIOD_INFO, periodInfo);
		}
	}

	@Prerender
	@Override
	public void prerender() throws LectureException {
		super.prerender();
		if (isRedirected()){
			return;
		}
		addPageCrumb();
	}
	
	private void addPageCrumb() {
		BreadCrumb crumb = new BreadCrumb();
		crumb.setLink("");
		crumb.setName(i18n("period_remove_header"));
		crumb.setHint(i18n("period_remove_header"));
		
		breadcrumbs.loadUniversityCrumbs(universityInfo);
		breadcrumbs.addCrumb(crumb);
	}	

	/**
	 * Forwards to an additional security question
	 * 
	 * @return outcome
	 * @throws LectureException
	 */
	public String removePeriod() throws Exception {
		if (logger.isDebugEnabled())
			logger.debug("Remove Period");
		try {
			universityService.removePeriod(periodInfo.getId());
			setBean(Constants.PERIOD_INFO, null);
			addMessage(i18n("message_period_removed"));
			return Constants.UNIVERSITY_PERIODS_PAGE;
		} catch (UniversityServiceException e) {
			addMessage(i18n("message_period_cannot_be_removed"));
			return Constants.UNIVERSITY_PERIODS_PAGE;
		}
	}
}
