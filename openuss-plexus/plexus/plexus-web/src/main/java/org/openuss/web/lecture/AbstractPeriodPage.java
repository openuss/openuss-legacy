package org.openuss.web.lecture;

import org.apache.shale.tiger.managed.Property;
import org.apache.shale.tiger.view.Prerender;
import org.openuss.framework.jsfcontrols.breadcrumbs.BreadCrumb;
import org.openuss.lecture.LectureException;
import org.openuss.lecture.Period;
import org.openuss.web.Constants;
import org.openuss.web.PageLinks;

/**
 * @author Ingo Dueppe
 */
public class AbstractPeriodPage extends AbstractLecturePage {

	private static final long serialVersionUID = -6389099727200151245L;
	@Property(value = "#{period}")
	protected Period period;

	@Prerender
	@Override
	public void prerender() throws LectureException {
		super.prerender();
		if (period == null) {
			addMessage(i18n("message_error_no_period_selected"));
			redirect(Constants.INSTITUTE_PERIODS_PAGE);
		} else if (period.getId() != null) {
			period = lectureService.getPeriod(period.getId());
			// check security constraint
			// TODO acegi should check this method if user is allow to read or
			// update the period
			if (!institute.getDepartment().getUniversity().getPeriods().contains(period)) {
				period = null;
				addMessage(i18n("message_error_period_does_not_belong_to_selected_institute"));
				redirect(Constants.INSTITUTE_PERIODS_PAGE);
			}
			setBean("period", period);
		}
		addPeriodCrumb();
	}

	private void addPeriodCrumb() {
		BreadCrumb periodCrumb = new BreadCrumb();
		periodCrumb.setName(i18n("institute_command_periods"));
		periodCrumb.setHint(i18n("institute_command_periods"));
		periodCrumb.setLink(PageLinks.INSTITUTE_PERIODS);
		periodCrumb.addParameter("institute", institute.getId());
		crumbs.add(periodCrumb);
	}

	public Period getPeriod() {
		return period;
	}

	public void setPeriod(Period period) {
		this.period = period;
	}

}
