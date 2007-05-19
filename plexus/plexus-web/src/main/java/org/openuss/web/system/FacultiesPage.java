package org.openuss.web.system;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.faces.event.ValueChangeEvent;

import org.apache.log4j.Logger;
import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Property;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.View;
import org.openuss.framework.web.jsf.model.AbstractPagedTable;
import org.openuss.framework.web.jsf.model.DataPage;
import org.openuss.lecture.Faculty;
import org.openuss.lecture.FacultyDetails;
import org.openuss.lecture.LectureException;
import org.openuss.lecture.LectureService;
import org.openuss.web.BasePage;
import org.openuss.web.Constants;

/**
 * 
 * @author Ingo Dueppe
 */
@Bean(name="views$secured$system$faculties", scope=Scope.REQUEST)
@View
public class FacultiesPage extends BasePage{

	private static final Logger logger = Logger.getLogger(FacultiesPage.class);

	private static final long serialVersionUID = -3339596434048309514L;
	
	@Property (value="#{lectureService}")
	private LectureService lectureService;
	
	private transient Set<FacultyDetails> changedFaculties = new HashSet<FacultyDetails>();

	private FacultyDataProvider provider = new FacultyDataProvider();

	public String selectFaculty() {
		Faculty faculty = currentFaculty();
		setSessionBean(Constants.FACULTY, faculty);
		return Constants.FACULTY_PAGE;
	}
	
	private Faculty currentFaculty() {
		FacultyDetails details = provider.getRowData();
		Faculty faculty = Faculty.Factory.newInstance();
		faculty.setId(details.getId());
		return faculty;
	}
	
	public void changedFaculty(ValueChangeEvent event) throws LectureException {
		FacultyDetails facultyDetails = provider.getRowData();
		logger.debug("changed state of " + facultyDetails.getName() + " from " + event.getOldValue() + " to " + event.getNewValue());
		changedFaculties.add(facultyDetails);
	}
	
	/**
	 * Save all changed data
	 * @return outcome
	 */
	public String save() {
		for (FacultyDetails facultyDetails : changedFaculties) {
			Faculty faculty = lectureService.getFaculty(facultyDetails.getId());
			faculty.setEnabled(facultyDetails.isEnabled());
			lectureService.persist(faculty);
			if (faculty.isEnabled())
				addMessage(i18n("system_message_faculty_enabled", faculty.getName()));
			else
				addMessage(i18n("system_message_faculty_disabled", faculty.getName()));
		}
		return Constants.SUCCESS;
	}
	
	private class FacultyDataProvider extends AbstractPagedTable<FacultyDetails> {
		
		private static final long serialVersionUID = 8894509911074086603L;
		
		private DataPage<FacultyDetails> page;
		
		@Override
		public DataPage<FacultyDetails> getDataPage(int startRow, int pageSize) {
			if (page == null){
				List<FacultyDetails> faculties = new ArrayList(lectureService.getFaculties(false));
				sort(faculties);
				page = new DataPage(faculties.size(), 0, faculties);
			}
			return page;
		}

	}
	
	
	/* ------------ properties ----------------- */

	public LectureService getLectureService() {
		return lectureService;
	}

	public void setLectureService(LectureService lectureService) {
		this.lectureService = lectureService;
	}

	public FacultyDataProvider getProvider() {
		return provider;
	}

	public void setProvider(FacultyDataProvider provider) {
		this.provider = provider;
	}

}
