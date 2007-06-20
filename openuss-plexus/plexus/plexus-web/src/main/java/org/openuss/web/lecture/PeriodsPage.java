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
import org.openuss.lecture.Course;
import org.openuss.lecture.LectureException;
import org.openuss.lecture.Period;
import org.openuss.lecture.CourseType;
import org.openuss.web.Constants;

/**
 * Periods Page Controller
 * 
 * @author Ingo Dueppe
 */
@Bean(name = "views$secured$lecture$periods", scope = Scope.REQUEST)
@View
public class PeriodsPage extends AbstractLecturePage {

	private static final Logger logger = Logger.getLogger(PeriodsPage.class);

	private PeriodDataModel periodData = new PeriodDataModel();
	
	private CourseDataModel courseData = new CourseDataModel();
	
	private Long courseTypeId;

	@Property(value = "#{sessionScope.period}")
	private Period period;
	
	@Prerender
	@Override
	public void prerender() throws LectureException {
		super.prerender();
		if (institute != null) {
			// refresh period list
			// TODO ask the lectureService instead of institute and use value objects
			List periods = institute.getPeriods();
			
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
		
		// check if courseType should be removed TODO Why?
		if (period == null) {
			removeSessionBean(Constants.COURSE_TYPE);
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
		lectureService.setActivePeriod(institute.getId(), period);

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
	 * Adds a new period to institute
	 * @return
	 */
	public String addPeriod() {
		Period period = Period.Factory.newInstance();
		setSessionBean(Constants.PERIOD, period);
		return Constants.INSTITUTE_PERIOD_PAGE;
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
		return Constants.INSTITUTE_PERIOD_PAGE;
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
		return Constants.INSTITUTE_PERIOD_REMOVE_PAGE;
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
		return Constants.INSTITUTE_PERIODS_PAGE;
	}

	/**
	 * Store changes in the business layer.
	 * 
	 * @return outcome
	 * @throws LectureException
	 */
	public String savePeriod() throws LectureException {
		if (period.getId() == null) {
			lectureService.add(institute.getId(), period);
		} else {
			lectureService.persist(period);
		}
			
		return Constants.INSTITUTE_PERIODS_PAGE;
	}

	/**
	 * Creates a new period object instance and stores it into session. Forwards
	 * to the period view.
	 * 
	 * @return outcome - institute_period
	 */
	public String createPeriod() {
		Period period = Period.Factory.newInstance();
		setSessionBean(Constants.PERIOD, period);
		return Constants.INSTITUTE_PERIOD_PAGE;
	}

	/**
	 * List of CourseTypes of the current institute
	 * 
	 * @return SelectItem List of CourseTypes
	 */
	public List<SelectItem> getCourseTypeSelectItems() {
		if (logger.isDebugEnabled()) {
			logger.debug("select period of line " + periodData.getRowIndex());
		}

		List<SelectItem> items = new ArrayList();
		for (CourseType courseType : institute.getCourseTypes()) {
			final SelectItem item = new SelectItem();
			item.setValue(courseType.getId());
			item.setLabel(courseType.getName() + "(" + courseType.getShortcut() + ")");
			item.setDescription(courseType.getDescription());
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
	 * Creates a new Course and adds it to the selected period
	 * 
	 * @return outcome
	 */
	public String addCourse() {
		CourseType courseType = CourseType.Factory.newInstance();
		courseType.setId(courseTypeId);
		lectureService.createCourse(courseType.getId(), period.getId());
		return Constants.SUCCESS;
	}
	
	public String shortcutCourse() throws DesktopException {
		Course course = courseData.getRowData();
		getDesktopService().linkCourse(getDesktop(), course);
		addMessage(i18n("message_course_shortcut_created"));
		return Constants.INSTITUTE_PERIODS_PAGE; 
	}

	/**
	 * Confirm the removement of an course
	 * 
	 * @return
	 */
	public String confirmRemoveCourse() {
		Course course = courseData.getRowData();
		setSessionBean(Constants.COURSE, course);
		return Constants.INSTITUTE_COURSE_REMOVE_PAGE;
	}

	/**
	 * 
	 * @return Constants
	 */
	public String editCourse() {
		logger.debug("edit course");
		Course course = courseData.getRowData();
		setSessionBean(Constants.COURSE, course);
		return Constants.COURSE_OPTIONS_PAGE;
	}

	/**
	 * @deprecated
	 * @return
	 */
	public List<Course> getCourses() {
		if (period != null && period.getCourses() != null && period.getCourses().size() > 0) {
			return new ArrayList(period.getCourses());
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
				List<Period> periods = new ArrayList(institute.getPeriods());
				sort(periods);
				page = new DataPage<Period>(periods.size(),0,periods);
			}
			return page;
		}
		
	}
	
	private class CourseDataModel extends AbstractPagedTable<Course> {
		private static final long serialVersionUID = 7931835846045404043L;
		
		private DataPage<Course> page;

		@Override
		public DataPage<Course> getDataPage(int startRow, int pageSize) {
			if (page == null) {
				List<Course> courses = new ArrayList(); 
				if (period != null) { 
					courses.addAll(period.getCourses());
				}
				sort(courses);
				page = new DataPage<Course>(courses.size(),0,courses);
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

	public CourseDataModel getCourseData() {
		return courseData;
	}

	public void setCourseData(CourseDataModel courseData) {
		this.courseData = courseData;
	}

	public Long getCourseTypeId() {
		return courseTypeId;
	}
	
	public void setCourseTypeId(Long courseTypeID) {
		this.courseTypeId = courseTypeID;
	}
	
	public Period getPeriod() {
		return period;
	}
	
	public void setPeriod(Period period) {
		logger.trace("setPeriod " + period);
		this.period = period;
	}
}
