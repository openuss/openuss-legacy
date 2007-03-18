package org.openuss.web.lecture;

import org.apache.shale.tiger.managed.Property;
import org.apache.shale.tiger.view.Prerender;
import org.openuss.lecture.LectureException;
import org.openuss.lecture.Period;
import org.openuss.web.Constants;

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
			redirect(Constants.FACULTY_PERIODS_PAGE);
		} else if (period.getId() != null) {
			period = lectureService.getPeriod(period.getId());
			// check security constraint 
			// TODO acegi should check this method if user is allow to read or update the period
			if (!faculty.getPeriods().contains(period)) {
				period = null;
				addMessage(i18n("message_error_period_does_not_belong_to_selected_faculty"));
				redirect(Constants.FACULTY_PERIODS_PAGE);
			}
			setBean("period",period);
		}
	}

	public Period getPeriod() {
		return period;
	}

	public void setPeriod(Period period) {
		this.period = period;
	}

}
