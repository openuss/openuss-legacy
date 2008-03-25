package org.openuss.web.lecture;

import org.apache.shale.tiger.managed.Property;
import org.apache.shale.tiger.view.Prerender;
import org.openuss.lecture.LectureException;
import org.openuss.lecture.PeriodInfo;
import org.openuss.web.Constants;

/**
 * @author Ingo Dueppe
 * @author Tianyu Wang
 * @author Weijun Chen
 * @author Kai Stettner
 * @author Sebastian Roekens
 * 
 */
public class AbstractPeriodPage extends AbstractUniversityPage {

	private static final long serialVersionUID = -6389099727200151245L;
	
	@Property(value = "#{periodInfo}")
	protected PeriodInfo periodInfo;

	@Prerender
	@Override
	public void prerender() throws LectureException {
		super.prerender();
		if (isRedirected()){
			return;
		}
		if (periodInfo == null) {
			addMessage(i18n("message_error_no_period_selected"));
			redirect(Constants.INSTITUTE_PERIODS_PAGE);
			return;
		}  
		if (periodInfo.getId() != null) {
			periodInfo = universityService.findPeriod(periodInfo.getId());
		}
		setBean(Constants.PERIOD_INFO, periodInfo);
	}


	public PeriodInfo getPeriodInfo() {
		return periodInfo;
	}

	public void setPeriodInfo(PeriodInfo periodInfo) {
		this.periodInfo = periodInfo;
	}

}
