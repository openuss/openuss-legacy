package org.openuss.web.lecture;

import javax.faces.event.ValueChangeEvent;

import org.apache.log4j.Logger;
import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.View;
import org.openuss.lecture.LectureException;
import org.openuss.web.Constants;

/**
 * Enrollment Edit Page Controller
 * @author Ingo Dueppe
 *
 */
@Bean(name="views$secured$lecture$enrollmentoptions",scope=Scope.REQUEST)
@View
public class EnrollmentOptionsPage extends AbstractEnrollmentPage {

	private static final Logger logger = Logger.getLogger(EnrollmentOptionsPage.class);

	private static final long serialVersionUID = 8821048605517398410L;
	
	
	/**
	 * Save changes of the enrollment 
	 * @return outcome
	 * @throws LectureException 
	 */
	public String saveOptions() throws LectureException {
		logger.trace("saving enrollment options");
		lectureService.persist(enrollment);
		return Constants.FACULTY_PERIODS;
	}
	
	/**
	 * Cancel changes to the Options.
	 * Redirect to the previous page
	 * @return outcome
	 */
	public String cancelOptions() {
		// nothing to do - enrollment will be automatically refreshed during prerender phase.
		return Constants.FACULTY_PERIODS;
	}
	
	/**
	 * Value Change Listener to swithc password input text on and off.
	 * @param event
	 */
	public void processAccessTypeChanged(ValueChangeEvent event) {
		Object accessType = event.getNewValue();
		enrollment.setAccessType((Integer) accessType);
	}

}
