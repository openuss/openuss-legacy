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
