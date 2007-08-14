package org.openuss.web.lecture;

import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.Prerender;
import org.apache.shale.tiger.view.View;
import org.openuss.framework.jsfcontrols.breadcrumbs.BreadCrumb;
import org.openuss.lecture.LectureException;
import org.openuss.web.Constants;
import java.util.Date;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Period Edit Page Controller
 * 
 * @author Ingo Dueppe
 * @author Weijun Chen
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
		if (periodInfo.getId() == null) {
			//Create Startdate
			Calendar cal = new GregorianCalendar();
			cal.set(2007, 10, 1);
			Date startdate = new Date(cal.getTimeInMillis());
			
			//Create Enddate
			cal = new GregorianCalendar();
			cal.set(2008, 3, 31);
			Date enddate = new Date(cal.getTimeInMillis());
			periodInfo.setStartdate(startdate);
			periodInfo.setEnddate(enddate);
			periodInfo.setUniversityId(universityInfo.getId());
			universityService.create(periodInfo);
			addMessage(i18n("message_created_new_period_succeed"));
		} else {
			universityService.update(periodInfo);
			addMessage(i18n("message_save_period_succeed"));
		}
		return Constants.SUCCESS;
	}

}
