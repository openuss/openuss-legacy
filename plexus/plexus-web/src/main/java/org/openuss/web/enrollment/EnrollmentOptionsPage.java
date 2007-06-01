package org.openuss.web.enrollment;

import java.util.ArrayList;
import java.util.List;

import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

import org.apache.log4j.Logger;
import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.Prerender;
import org.apache.shale.tiger.view.View;
import org.openuss.lecture.AccessType;
import org.openuss.lecture.Enrollment;
import org.openuss.lecture.LectureException;
import org.openuss.web.Constants;

/**
 * Enrollment Edit Page Controller
 * @author Ingo Dueppe
 *
 */
@Bean(name="views$secured$enrollment$enrollmentoptions",scope=Scope.REQUEST)
@View
public class EnrollmentOptionsPage extends AbstractEnrollmentPage {

	private static final Logger logger = Logger.getLogger(EnrollmentOptionsPage.class);

	private static final long serialVersionUID = 8821048605517398410L;
	
/*	TODO what about this below?
 	@Preprocess
	public void preprocess() {
		if (enrollment != null) {
			logger.debug("---------------> refreshing enrollment.");
			enrollment = getLectureService().getEnrollment(enrollment.getId());
			setSessionBean(Constants.ENROLLMENT, enrollment);
		}
	}
*/
	
	@Prerender
	@Override
	public void prerender() throws LectureException {
		if (enrollment == null) {
			enrollment = (Enrollment) getSessionBean(Constants.ENROLLMENT);
		}
		if (enrollment == null) {
			addMessage(i18n("message_error_enrollment_page"));
			redirect(Constants.OUTCOME_BACKWARD);
		} else {
			if (!isPostBack()) {
				logger.debug("------------------------- is not postback --------------- refreshing enrollment");
				enrollment = enrollmentService.getEnrollment(enrollment);
			}
		}
		setSessionBean(Constants.ENROLLMENT, enrollment);
	}

	/**
	 * Save changes of the enrollment 
	 * @return outcome
	 * @throws LectureException 
	 */
	public String saveOptions() throws LectureException {
		logger.trace("saving enrollment options");
		lectureService.persist(enrollment);
		addMessage(i18n("message_enrollment_options_saved"));
		return Constants.ENROLLMENT_OPTIONS_PAGE;
	}
	
	/**
	 * Cancel changes to the Options.
	 * Redirect to the previous page
	 * @return outcome
	 */
	public String cancelOptions() {
		// nothing to do - enrollment will be automatically refreshed during prerender phase.
		return Constants.ENROLLMENT_PAGE;
	}
	
	/**
	 * Value Change Listener to swithc password input text on and off.
	 * @param event
	 */
	public void processAccessTypeChanged(ValueChangeEvent event) {
		Object accessType = event.getNewValue();
		enrollment.setAccessType((AccessType)accessType);
	}
	
	public List<SelectItem> getAccessTypes() {
		List<SelectItem> items = new ArrayList<SelectItem>();
		items.add(new SelectItem(AccessType.OPEN,i18n("enrollment_options_access_open")));
		items.add(new SelectItem(AccessType.CLOSED,i18n("enrollment_options_access_closed")));
		items.add(new SelectItem(AccessType.PASSWORD,i18n("enrollment_options_access_password")));
		items.add(new SelectItem(AccessType.APPLICATION,i18n("enrollment_options_access_application")));
		return items;
	}

}
