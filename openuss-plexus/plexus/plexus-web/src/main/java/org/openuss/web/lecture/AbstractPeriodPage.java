package org.openuss.web.lecture;

import org.apache.shale.tiger.managed.Property;
import org.apache.shale.tiger.view.Prerender;
import org.openuss.framework.jsfcontrols.breadcrumbs.BreadCrumb;
import org.openuss.lecture.LectureException;
import org.openuss.lecture.PeriodInfo;
import org.openuss.web.Constants;
import org.openuss.web.PageLinks;

/**
 * @author Ingo Dueppe
 * @author Tianyu Wang
 * @author Weijun Chen
 */
public class AbstractPeriodPage extends AbstractLecturePage {

	private static final long serialVersionUID = -6389099727200151245L;
	@Property(value = "#{periodInfo}")
	protected PeriodInfo periodInfo;

	@Prerender
	@Override
	public void prerender() throws LectureException {
		super.prerender();
		if (periodInfo == null) {
			addMessage(i18n("message_error_no_period_selected"));
			redirect(Constants.INSTITUTE_PERIODS_PAGE);
		} else if (periodInfo.getId() != null) {
			periodInfo = universityService.findPeriod(periodInfo.getId());
			// check security constraint
			// TODO acegi should check this method if user is allow to read or
			// update the period
			
			}
			setBean("periodInfo", periodInfo);
		
//		addPeriodCrumb();
	}

	/*
	private void addPeriodCrumb() {
		BreadCrumb periodCrumb = new BreadCrumb();
		periodCrumb.setName(i18n("institute_command_periods"));
		periodCrumb.setHint(i18n("institute_command_periods"));
		periodCrumb.setLink(PageLinks.INSTITUTE_PERIODS);
		periodCrumb.addParameter("institute", institute.getId());
		crumbs.add(periodCrumb);
	}
*/
	public PeriodInfo getPeriodInfo() {
		return periodInfo;
	}

	public void setPeriodInfo(PeriodInfo periodInfo) {
		this.periodInfo = periodInfo;
	}

}
