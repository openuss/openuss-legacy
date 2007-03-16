package org.openuss.web.lecture;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Property;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.View;
import org.openuss.desktop.DesktopException;
import org.openuss.framework.web.jsf.model.AbstractPagedTable;
import org.openuss.framework.web.jsf.model.DataPage;
import org.openuss.lecture.Enrollment;
import org.openuss.lecture.LectureException;
import org.openuss.news.NewsService;
import org.openuss.web.Constants;

/**
 * @author Ingo Dueppe
 */
@View
@Bean(name = "views$secured$lecture$faculty", scope = Scope.REQUEST)
public class FacultyPage extends AbstractLecturePage {
	private static final long serialVersionUID = -1982354759705300093L;

	@Property(value = "#{newsService}")
	private NewsService newsService;
	
	private EnrollmentDataModel enrollmentData = new EnrollmentDataModel();
	
	private class EnrollmentDataModel extends AbstractPagedTable<Enrollment> {
		private DataPage<Enrollment> page;

		@Override
		public DataPage<Enrollment> getDataPage(int startRow, int pageSize) {
			if (page == null) {
				List<Enrollment> enrollments = new ArrayList(); 
				if (faculty != null) { 
					enrollments.addAll(faculty.getActiveEnrollments());
				}
				sort(enrollments);
				page = new DataPage<Enrollment>(enrollments.size(),0,enrollments);
			}
			return page;
		}
	}
	
	/**
	 * Current news items of faculty
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
}
