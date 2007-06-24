package org.openuss.web.lecture;

import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.Prerender;
import org.apache.shale.tiger.view.View;
import org.openuss.framework.jsfcontrols.breadcrumbs.BreadCrumb;
import org.openuss.lecture.LectureException;
import org.openuss.web.Constants;

/**
 * Period Edit Page Controller
 * 
 * @author Ingo Dueppe
 */
@Bean(name = "views$secured$lecture$periodedit", scope = Scope.REQUEST)
@View
public class PeriodEditPage extends AbstractPeriodPage {

	@Prerender
	public void prerender() throws LectureException {
		super.prerender();
		addPageCrumb();
	}
	
	private void addPageCrumb() {
		BreadCrumb crumb = new BreadCrumb();
		crumb.setLink("");
		crumb.setName(i18n("period_heading"));
		crumb.setHint(i18n("period_heading"));
		crumbs.add(crumb);
		setSessionBean(Constants.BREADCRUMBS, crumbs);
	}
	
	/**
	 * Store changes in the business layer.
	 * 
	 * @return outcome
	 * @throws LectureException
	 */
	public String savePeriod() throws LectureException {
		if (period.getId() == null) {
			lectureService.add(institute.getId(), period);
			addMessage(i18n("message_created_new_period_succeed"));
		} else {
			lectureService.persist(period);
			addMessage(i18n("message_save_period_succeed"));
		}
		return Constants.INSTITUTE_PERIODS_PAGE;
	}

}
