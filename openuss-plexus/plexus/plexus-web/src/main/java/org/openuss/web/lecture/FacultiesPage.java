package org.openuss.web.lecture;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Property;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.View;
import org.openuss.desktop.Desktop;
import org.openuss.desktop.DesktopException;
import org.openuss.desktop.DesktopService;
import org.openuss.framework.web.jsf.model.AbstractPagedTable;
import org.openuss.framework.web.jsf.model.DataPage;
import org.openuss.lecture.Faculty;
import org.openuss.lecture.FacultyDetails;
import org.openuss.lecture.LectureService;
import org.openuss.web.BasePage;
import org.openuss.web.Constants;

/**
 * 
 * @author Ingo Dueppe
 * @author Sebastian Roekens
 *
 */
@Bean(name = "views$public$faculty$faculties", scope = Scope.REQUEST)
@View
public class FacultiesPage extends BasePage{

	private static final Logger logger = Logger.getLogger(FacultiesPage.class);

	private static final long serialVersionUID = 5069935782478432045L;
	
	private FacultyTable faculties = new FacultyTable();
	
	@Property(value = "#{lectureService}")
	private LectureService lectureService;
	
	@Property(value = "#{desktopService}")
	private DesktopService desktopService;
	
	@Property(value = "#{desktop}")
	private Desktop desktop;
	
	/**
	 * Store the selected faculty into session scope and go to faculty main page.
	 * @return Outcome
	 */
	public String selectFaculty() {
		Faculty faculty = currentFaculty();
		setSessionBean(Constants.FACULTY, faculty);
		return Constants.FACULTY_MAIN;
	}
	
	public String shortcutFaculty() throws DesktopException {
		Faculty faculty = currentFaculty();
		desktopService.linkFaculty(desktop, faculty);
		addMessage(i18n("message_faculty_shortcut_created"));
		return Constants.SUCCESS;
	}

	private Faculty currentFaculty() {
		FacultyDetails details = faculties.getRowData();
		Faculty faculty = Faculty.Factory.newInstance();
		faculty.setId(details.getId());
		return faculty;
	}
	
	private DataPage<FacultyDetails> dataPage;
	
	public DataPage<FacultyDetails> fetchDataPage(int startRow, int pageSize) {
		
		if (dataPage == null) {
			if (logger.isDebugEnabled()) {
				logger.debug("fetch faculties data page at " + startRow + ", "+ pageSize+" sorted by "+faculties.getSortColumn());
			}
			List<FacultyDetails> facultyList = new ArrayList(getLectureService().getFaculties(true));
			sort(facultyList);
			dataPage = new DataPage<FacultyDetails>(facultyList.size(),0,facultyList);
	}
		return dataPage;
	}

	private void sort(List<FacultyDetails> facultyList) {
		if (StringUtils.equals("shortcut", faculties.getSortColumn())) {
			Collections.sort(facultyList, new ShortcutComparator());
		} else if (StringUtils.equals("owner", faculties.getSortColumn())){
			Collections.sort(facultyList, new OwnerComparator());
		} else {
			Collections.sort(facultyList, new NameComparator());
		}
	}

	public FacultyTable getFaculties() {
		return faculties;
	}

	private class FacultyTable extends AbstractPagedTable<FacultyDetails> {

		@Override
		public DataPage<FacultyDetails> getDataPage(int startRow, int pageSize) {
			return fetchDataPage(startRow, pageSize);
		}
		
	}
	
	public LectureService getLectureService() {
		return lectureService;
	}
	
	public void setLectureService(LectureService lectureService) {
		this.lectureService = lectureService;
	}

	
	/* ----------- faculty sorting comparators -------------*/
	
	private class NameComparator implements Comparator<FacultyDetails> {
		public int compare(FacultyDetails f1, FacultyDetails f2) {
			if (faculties.isAscending()) {
				return f1.getName().compareToIgnoreCase(f2.getName());
			} else {
				return f2.getName().compareToIgnoreCase(f1.getName());
			}
		}
	}

	private class OwnerComparator implements Comparator<FacultyDetails> {
		public int compare(FacultyDetails f1, FacultyDetails f2) {
			if (faculties.isAscending()) {
				return f1.getOwnername().compareToIgnoreCase(f2.getOwnername());
			} else {
				return f2.getOwnername().compareToIgnoreCase(f1.getOwnername());
			}
		}
	}

	private class ShortcutComparator implements Comparator<FacultyDetails> {
		public int compare(FacultyDetails f1, FacultyDetails f2) {
			if (faculties.isAscending()) {
				return f1.getShortcut().compareToIgnoreCase(f2.getShortcut());
			} else {
				return f2.getShortcut().compareToIgnoreCase(f1.getShortcut());
			}
		}
	}

	public DesktopService getDesktopService() {
		return desktopService;
	}

	public void setDesktopService(DesktopService desktopService) {
		this.desktopService = desktopService;
	}

	public Desktop getDesktop() {
		return desktop;
	}

	public void setDesktop(Desktop desktop) {
		this.desktop = desktop;
	}

	
}