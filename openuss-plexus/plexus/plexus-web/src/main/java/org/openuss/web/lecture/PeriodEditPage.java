package org.openuss.web.lecture;

import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.View;
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

	private static final long serialVersionUID = -3812508575903167466L;

	/**
	 * Store changes in the business layer.
	 * 
	 * @return outcome
	 * @throws LectureException
	 */
	public String savePeriod() throws LectureException {
		if (period.getId() == null) {
			lectureService.add(faculty.getId(), period);
			addMessage(i18n("message_created_new_period_succeed"));
		} else {
			lectureService.persist(period);
			addMessage(i18n("message_save_period_succeed"));
		}
		return Constants.FACULTY_PERIODS_PAGE;
	}

}
