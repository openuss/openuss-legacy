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
import org.openuss.framework.web.jsf.model.AbstractPagedTable;
import org.openuss.framework.web.jsf.model.DataPage;
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

	private PeriodDataModel periodData = new PeriodDataModel();
	
	private EnrollmentDataModel enrollmentData = new EnrollmentDataModel();
	
	private Long subjectId;

	@Property(value = "#{sessionScope.period}")
	private Period period;
	
	@Prerender
	@Override
	public void prerender() throws LectureException {
		super.prerender();
		if (faculty != null) {
			// refresh period list
			// TODO ask the lectureService instead of faculty and use value objects
			List periods = faculty.getPeriods();
			
			period = (Period) getSessionBean(Constants.PERIOD);
			if (period != null) {
				if (period.getId() != null && periods.contains(period)) {
					period = lectureService.getPeriod(period.getId());
				} else {
					period = null;
				}
			}
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
		if (logger.isDebugEnabled()) {
			logger.debug("change active period of line " + periodData.getRowIndex());
		}
		
		Period period = periodData.getRowData();
		lectureService.setActivePeriod(faculty.getId(), period);

		return Constants.SUCCESS;
	}

	/**
	 * Select a period
	 * 
	 * @return outcome
	 */
	public String selectPeriod() {
		if (logger.isDebugEnabled()) {
			logger.debug("select period of line " + periodData.getRowIndex());
		}
		Period period = periodData.getRowData();
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
		return Constants.FACULTY_PERIOD_PAGE;
	}

	/**
	 * Sets the selected period into session scope and forward to the period
	 * edit view
	 * 
	 * @return outcome
	 */
	public String editPeriod() {
		Period period = periodData.getRowData();
		setSessionBean(Constants.PERIOD, period);
		return Constants.FACULTY_PERIOD_PAGE;
	}

	/**
	 * Sets the selected period into session scope and forward to the period
	 * remove view
	 * 
	 * @return outcome
	 */
	public String confirmRemovePeriod() {
		Period period = periodData.getRowData();
		setSessionBean(Constants.PERIOD, period);
		return Constants.FACULTY_PERIOD_REMOVE_PAGE;
	}

	/**
	 * Forwards to an additional security question
	 * 
	 * @return outcome
	 * @throws LectureException
	 */
	public String removePeriod() throws LectureException {
		if (logger.isDebugEnabled()) {
			logger.debug("Remove Period");
		}
		lectureService.removePeriod(period.getId());
		removeSessionBean(Constants.PERIOD);
		return Constants.FACULTY_PERIODS_PAGE;
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
			
		return Constants.FACULTY_PERIODS_PAGE;
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
		return Constants.FACULTY_PERIOD_PAGE;
	}

	/**
	 * List of Subjects of the current faculty
	 * 
	 * @return SelectItem List of Subjects
	 */
	public List<SelectItem> getSubjectSelectItems() {
		if (logger.isDebugEnabled()) {
			logger.debug("select period of line " + periodData.getRowIndex());
		}

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
		Enrollment enrollment = enrollmentData.getRowData();
		getDesktopService().linkEnrollment(getDesktop(), enrollment);
		addMessage(i18n("message_enrollment_shortcut_created"));
		return Constants.FACULTY_PERIODS_PAGE; 
	}

	/**
	 * Confirm the removement of an enrollment
	 * 
	 * @return
	 */
	public String confirmRemoveEnrollment() {
		Enrollment enrollment = enrollmentData.getRowData();
		setSessionBean(Constants.ENROLLMENT, enrollment);
		return Constants.FACULTY_ENROLLMENT_REMOVE_PAGE;
	}

	/**
	 * 
	 * @return Constants
	 */
	public String editEnrollment() {
		logger.debug("edit enrollment");
		Enrollment enrollment = enrollmentData.getRowData();
		setSessionBean(Constants.ENROLLMENT, enrollment);
		return Constants.ENROLLMENT_OPTIONS_PAGE;
	}

	/**
	 * @deprecated
	 * @return
	 */
	public List<Enrollment> getEnrollments() {
		if (period != null && period.getEnrollments() != null && period.getEnrollments().size() > 0) {
			return new ArrayList(period.getEnrollments());
		} else {
			return null;
		}
	}

	/* ------------------ data models ------------------- */
	
	private class PeriodDataModel extends AbstractPagedTable<Period> {
		
		private static final long serialVersionUID = 1872495569523386246L;
		
		private DataPage<Period> page;
		
		@Override
		public DataPage<Period> getDataPage(int startRow, int pageSize) {
			if (page == null) {
				List<Period> periods = new ArrayList(faculty.getPeriods());
				sort(periods);
				page = new DataPage<Period>(periods.size(),0,periods);
			}
			return page;
		}
		
	}
	
	private class EnrollmentDataModel extends AbstractPagedTable<Enrollment> {
		private static final long serialVersionUID = 7931835846045404043L;
		
		private DataPage<Enrollment> page;

		@Override
		public DataPage<Enrollment> getDataPage(int startRow, int pageSize) {
			if (page == null) {
				List<Enrollment> enrollments = new ArrayList(); 
				if (period != null) { 
					enrollments.addAll(period.getEnrollments());
				}
				sort(enrollments);
				page = new DataPage<Enrollment>(enrollments.size(),0,enrollments);
			}
			return page;
		}
	}
	
	/* -------- properties ---------- */

	public PeriodDataModel getPeriodData() {
		return periodData;
	}

	public void setPeriodData(PeriodDataModel periodData) {
		this.periodData = periodData;
	}

	public EnrollmentDataModel getEnrollmentData() {
		return enrollmentData;
	}

	public void setEnrollmentData(EnrollmentDataModel enrollmentData) {
		this.enrollmentData = enrollmentData;
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
}
