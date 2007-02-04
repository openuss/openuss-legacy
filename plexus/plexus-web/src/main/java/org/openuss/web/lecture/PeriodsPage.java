package org.openuss.web.lecture;

import java.util.ArrayList;
import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import org.apache.log4j.Logger;
import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Property;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.Prerender;
import org.apache.shale.tiger.view.View;
import org.openuss.desktop.DesktopException;
import org.openuss.lecture.Enrollment;
import org.openuss.lecture.LectureException;
import org.openuss.lecture.Period;
import org.openuss.lecture.Subject;
import org.openuss.web.Constants;

/**
 * Periods Page Controller
 * 
 * @author Ingo Dueppe
 */
@Bean(name = "views$secured$lecture$periods", scope = Scope.REQUEST)
@View
public class PeriodsPage extends AbstractLecturePage {

	private static final long serialVersionUID = -3812508575903167466L;

	private static final Logger logger = Logger.getLogger(PeriodsPage.class);

	@Property(value = "#{enrollmentList}")
	private EnrollmentList enrollmentList;

	private Long subjectId;

	@Property(value = "#{sessionScope.period}")
	private Period period;
	
	@Property(value = "#{periodList}")
	private PeriodList periodList;

	@Prerender
	@Override
	public void prerender() throws LectureException {
		super.prerender();
		if (faculty != null) {
			// refresh period list
			// TODO ask the lectureService instead of faculty and use value objects
			List periods = faculty.getPeriods();
			periodList.setData(periods);
			
			period = (Period) getSessionBean(Constants.PERIOD);
			if (period != null) {
				if (period.getId() != null && periods.contains(period)) {
					period = lectureService.getPeriod(period.getId());
				} else {
					period = null;
				}
			}
		}
		
		periodList.setSelectedData(period);
		
		if (period != null) {
			enrollmentList.setData(period.getEnrollments());
		}		
		setSessionBean(Constants.PERIOD, period);
		
		// check if subject should be removed TODO Why?
		if (period == null) {
			removeSessionBean(Constants.SUBJECT);
		}
		
	}

	/**
	 * Change the actuall active period.
	 * 
	 * @return outcome
	 * @throws LectureException 
	 */
	public String periodToActive() throws LectureException {
		if (logger.isDebugEnabled())
			logger.debug("change active period of line " + periodList.getSelectedRowIndex());
		
		Period period = periodList.getSelectedData();
		lectureService.setActivePeriod(faculty.getId(), period);

		return Constants.SUCCESS;
	}

	/**
	 * Select a period
	 * 
	 * @return outcome
	 */
	public String selectPeriod() {
		if (logger.isDebugEnabled())
			logger.debug("select period of line " + periodList.getSelectedRowIndex());
		Period period = periodList.getSelectedData();
		setSessionBean(Constants.PERIOD, period);

		return Constants.SUCCESS;
	}

	/**
	 * Adds a new period to faculty
	 * @return
	 */
	public String addPeriod() {
		Period period = Period.Factory.newInstance();
		setSessionBean(Constants.PERIOD, period);
		return Constants.FACULTY_PERIOD;
	}

	/**
	 * Sets the selected period into session scope and forward to the period
	 * edit view
	 * 
	 * @return outcome
	 */
	public String editPeriod() {
		Period period = periodList.getSelectedData();
		setSessionBean(Constants.PERIOD, period);
		return Constants.FACULTY_PERIOD;
	}

	/**
	 * Sets the selected period into session scope and forward to the period
	 * remove view
	 * 
	 * @return outcome
	 */
	public String confirmRemovePeriod() {
		Period period = periodList.getSelectedData();
		setSessionBean(Constants.PERIOD, period);
		return Constants.FACULTY_PERIOD_REMOVE;
	}

	/**
	 * Forwards to an additional security question
	 * 
	 * @return outcome
	 * @throws LectureException
	 */
	public String removePeriod() throws LectureException {
		if (logger.isDebugEnabled())
			logger.debug("Remove Period");
		lectureService.removePeriod(period.getId());
		removeSessionBean(Constants.PERIOD);
		return Constants.FACULTY_PERIODS;
	}

	/**
	 * Store changes in the business layer.
	 * 
	 * @return outcome
	 * @throws LectureException
	 */
	public String savePeriod() throws LectureException {
		if (period.getId() == null) {
			lectureService.add(faculty.getId(), period);
		} else {
			lectureService.persist(period);
		}
			
		return Constants.FACULTY_PERIODS;
	}

	/**
	 * Creates a new period object instance and stores it into session. Forwards
	 * to the period view.
	 * 
	 * @return outcome - faculty_period
	 */
	public String createPeriod() {
		Period period = Period.Factory.newInstance();
		setSessionBean(Constants.PERIOD, period);
		return Constants.FACULTY_PERIOD;
	}

	/**
	 * List of Subjects of the current faculty
	 * 
	 * @return SelectItem List of Subjects
	 */
	public List<SelectItem> getSubjectSelectItems() {
		if (logger.isDebugEnabled())
			logger.debug("select period of line " + periodList.getSelectedRowIndex());

		List<SelectItem> items = new ArrayList();
		for (Subject subject : faculty.getSubjects()) {
			final SelectItem item = new SelectItem();
			item.setValue(subject.getId());
			item.setLabel(subject.getName() + "(" + subject.getShortcut() + ")");
			item.setDescription(subject.getDescription());
			items.add(item);
		}
		return items;
	}

	/**
	 * Validator to check wether the user has accepted the user agreement or
	 * not.
	 * 
	 * @param context
	 * @param toValidate
	 * @param value
	 */
	public void validateRemoveConfirmation(FacesContext context, UIComponent toValidate, Object value) {
		boolean accept = (Boolean) value;
		if (!accept) {
			((UIInput) toValidate).setValid(false);
			addError(toValidate.getClientId(context), i18n("error_need_to_confirm_removement"), null);
		}
	}

	/**
	 * Creates a new Enrollment and adds it to the selected period
	 * 
	 * @return outcome
	 */
	public String addEnrollment() {
		Subject subject = Subject.Factory.newInstance();
		subject.setId(subjectId);
		lectureService.createEnrollment(subject.getId(), period.getId());
		return Constants.SUCCESS;
	}
	
	public String shortcutEnrollment() throws DesktopException {
		Enrollment enrollment = enrollmentList.getSelectedData();
		desktopService.linkEnrollment(desktop, enrollment);
		addMessage(i18n("message_enrollment_shortcut_created"));
		return Constants.FACULTY_PERIODS; 
	}

	/**
	 * Confirm the removement of an enrollment
	 * 
	 * @return
	 */
	public String confirmRemoveEnrollment() {
		Enrollment enrollment = enrollmentList.getSelectedData();
		setSessionBean(Constants.ENROLLMENT, enrollment);
		return Constants.FACULTY_ENROLLMENT_REMOVE;
	}

	/**
	 * 
	 * @return Constants
	 */
	public String editEnrollment() {
		logger.debug("edit enrollment");
		Enrollment enrollment = enrollmentList.getSelectedData();
		setSessionBean(Constants.ENROLLMENT, enrollment);
		return Constants.ENROLLMENT_OPTIONS;
	}

	public List<Enrollment> getEnrollments() {
		if (period != null && period.getEnrollments() != null && period.getEnrollments().size() > 0) {
			return new ArrayList(period.getEnrollments());
		} else {
			return null;
		}
	}

	public Long getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(Long subjectID) {
		this.subjectId = subjectID;
	}

	public Period getPeriod() {
		return period;
	}

	public void setPeriod(Period period) {
		logger.trace("setPeriod " + period);
		this.period = period;
	}

	public PeriodList getPeriodList() {
		return periodList;
	}

	public void setPeriodList(PeriodList periodList) {
		this.periodList = periodList;
	}

	public EnrollmentList getEnrollmentList() {
		return enrollmentList;
	}

	public void setEnrollmentList(EnrollmentList enrollmentList) {
		this.enrollmentList = enrollmentList;
	}

}
