package org.openuss.web.lecture;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.Prerender;
import org.apache.shale.tiger.view.View;
import org.openuss.desktop.DesktopException;
import org.openuss.framework.web.jsf.model.AbstractPagedTable;
import org.openuss.framework.web.jsf.model.DataPage;
import org.openuss.lecture.LectureException;
import org.openuss.lecture.Subject;
import org.openuss.web.Constants;

/**
 * @author Ingo Dueppe
 */
@Bean(name = "views$secured$lecture$subjects", scope = Scope.REQUEST)
@View
public class SubjectsPage extends AbstractLecturePage {

	private static final Logger logger = Logger.getLogger(SubjectsPage.class);

	private static final long serialVersionUID = 4667557973921766455L;

	private LocalDataModel data = new LocalDataModel();
	
	@Prerender
	public void prerender() throws LectureException {
		super.prerender();
	}

	/**
	 * Set selected subject into session scope
	 * 
	 * @return outcome
	 * @throws LectureException 
	 */
	public String editSubject() throws LectureException {
		subject = data.getRowData();
		if (subject == null) {
			return Constants.FAILURE;
		}
		subject = lectureService.getSubject(subject.getId());
		setSessionBean(Constants.SUBJECT, subject);
		if (subject == null) {
			addWarning(i18n("error_subject_not_found"));
			return Constants.FAILURE;
			
		} else {
			logger.debug("selected subject "+subject.getName());
			return Constants.SUCCESS;
		}
	}

	/**
	 * Create new subject object and set it into session scope
	 * 
	 * @return outcome
	 */
	public String addSubject() {
		subject = Subject.Factory.newInstance();
		subject.setFaculty(faculty);
		setSessionBean(Constants.SUBJECT, subject);
		return Constants.SUCCESS;
	}

	public String shortcutSubject() {
		subject = data.getRowData();;
		try {
			desktopService.linkSubject(desktop, subject);
			addMessage(i18n("desktop_command_add_subject_succeed"));
			return Constants.SUCCESS;
		} catch (DesktopException e) {
			logger.error(e);
			addError(i18n(e.getMessage()));
			return Constants.FAILURE;
		}
	}

	/**
	 * Sets the selected period into session scope and forward to the period
	 * remove view
	 * 
	 * @return outcome
	 */
	public String confirmRemoveSubject() {
		subject = data.getRowData();;
		setSessionBean(Constants.SUBJECT, subject);
		return Constants.FACULTY_SUBJECT_REMOVE_PAGE;
	}

	/**
	 * Save new subject or update changes to subject Removed current subject
	 * selection from session scope
	 * @return outcome 
	 */
	public String saveSubject() {
		logger.debug("saveSubject()");
		if (subject.getId() == null) {
			lectureService.add(faculty.getId(), subject);
			addMessage(i18n("faculty_message_add_subject_succeed"));
		} else {
			lectureService.persist(subject);
			addMessage(i18n("faculty_message_persist_subject_succeed"));
		}
		removeSessionBean(Constants.SUBJECT);
		subject = null;
		return Constants.SUCCESS;
	}

	/**
	 * Cancel editing or adding of current subject
	 * @return outcome 
	 */
	public String cancelSubject() {
		logger.debug("cancelSubject()");
		removeSessionBean(Constants.SUBJECT);
		return Constants.SUCCESS;
	}

	private class LocalDataModel extends AbstractPagedTable<Subject> {
		
		private static final long serialVersionUID = -6289875618529435428L;
		
		private DataPage<Subject> page;

		@Override
		public DataPage<Subject> getDataPage(int startRow, int pageSize) {
			if (page == null) {
				List<Subject> subjects = new ArrayList(faculty.getSubjects());
				sort(subjects);
				page = new DataPage<Subject>(subjects.size(),0,subjects);
			}
			return page;
		}

	}

	public LocalDataModel getData() {
		return data;
	}

	public void setData(LocalDataModel data) {
		this.data = data;
	}	
}
