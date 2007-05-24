package org.openuss.web.lecture;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.faces.event.ValueChangeEvent;

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
import org.openuss.news.NewsService;
import org.openuss.web.Constants;

/**
 * @author Ingo Dueppe
 */
@View
@Bean(name = "views$secured$lecture$faculty", scope = Scope.REQUEST)
public class FacultyPage extends AbstractLecturePage {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(FacultyPage.class);

	private static final long serialVersionUID = -1982354759705300093L;

	@Property(value = "#{newsService}")
	private NewsService newsService;

	@Property(value = "#{sessionScope.period}")
	private Period period;

	private EnrollmentDataModel enrollmentData = new EnrollmentDataModel();

	/**
	 * Refreshing faculty entity
	 * 
	 * @throws Exception
	 */
	@Prerender
	public void prerender() throws LectureException {
		super.prerender();
		if (period == null && faculty != null || faculty != null && !faculty.getPeriods().contains(period)) {
			period = faculty.getActivePeriod();
			if (period == null && faculty.getPeriods().size() > 0) {
				period = faculty.getPeriods().get(0);
			}
		} else {
			period = lectureService.getPeriod(period.getId());
		}
		setSessionBean(Constants.PERIOD, period);
	}

	/**
	 * Value Change Listener to swithc password input text on and off.
	 * 
	 * @param event
	 */
	public void processPeriodSelectChanged(ValueChangeEvent event) {
		final Long periodId = (Long) event.getNewValue();
		period = lectureService.getPeriod(periodId);
		setSessionBean(Constants.PERIOD, period);
	}

	private class EnrollmentDataModel extends AbstractPagedTable<Enrollment> {

		private static final long serialVersionUID = 3682383483634321520L;

		private DataPage<Enrollment> page;

		@Override
		public DataPage<Enrollment> getDataPage(int startRow, int pageSize) {
			if (page == null) {
				List<Enrollment> enrollments = new ArrayList();
				if (period != null) {
					period = lectureService.getPeriod(period.getId());
					enrollments.addAll(period.getEnrollments());
				} else {
					logger.error("faculty page - no period selected!");
				}
				sort(enrollments);
				page = new DataPage<Enrollment>(enrollments.size(), 0, enrollments);
			}
			return page;
		}
	}
	
	/**
	 * Apply for Membership.
	 * @return outcome
	 * @throws LectureException 
	 */
	public String applyForMembership() throws LectureException {
		if (user != null && faculty != null) {
			lectureService.addFacultyAspirant(user.getId(), faculty.getId());
			addMessage(i18n("faculty_message_application_of_membership_send"));
		}
		return Constants.SUCCESS;
	}

	/**
	 * Current news items of faculty
	 * 
	 * @return
	 * @throws LectureException
	 */
	public Collection getCurrentNewsItems() {
		return newsService.getCurrentNewsItems(faculty, 10);
	}

	public NewsService getNewsService() {
		return newsService;
	}

	public void setNewsService(NewsService newsService) {
		this.newsService = newsService;
	}

	public String showApplication() {
		return Constants.DESKTOP;
	}

	public EnrollmentDataModel getEnrollmentData() {
		return enrollmentData;
	}

	public void setEnrollmentData(EnrollmentDataModel enrollmentData) {
		this.enrollmentData = enrollmentData;
	}

	public String shortcutEnrollment() throws DesktopException {
		Enrollment enrollment = enrollmentData.getRowData();
		desktopService.linkEnrollment(desktop, enrollment);
		addMessage(i18n("message_enrollment_shortcut_created"));
		return Constants.FACULTY_PAGE;
	}

	public Period getPeriod() {
		return period;
	}

	public void setPeriod(Period period) {
		this.period = period;
	}
}
