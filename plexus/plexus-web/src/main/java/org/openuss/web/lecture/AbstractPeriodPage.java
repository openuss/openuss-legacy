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
	@Property(value = "#{sessionScope.period}")
	protected Period period;

	@Prerender
	@Override
	public void prerender() throws LectureException {
		super.prerender();
		if (period == null) {
			addMessage(i18n("message_error_no_period_selected"));
			redirect(Constants.FACULTY_PERIODS_PAGE);
		}
	}

	public Period getPeriod() {
		return period;
	}

	public void setPeriod(Period period) {
		this.period = period;
	}

}
