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
import org.openuss.desktop.DesktopException;
import org.openuss.framework.web.jsf.model.AbstractPagedTable;
import org.openuss.framework.web.jsf.model.DataPage;
import org.openuss.lecture.Institute;
import org.openuss.lecture.InstituteDetails;
import org.openuss.lecture.LectureService;
import org.openuss.web.BasePage;
import org.openuss.web.Constants;

/**
 * 
 * @author Ingo Dueppe
 * @author Sebastian Roekens
 *
 */
@Bean(name = "views$public$institute$institutes", scope = Scope.REQUEST)
@View
public class InstitutesPage extends BasePage{

	private static final Logger logger = Logger.getLogger(InstitutesPage.class);

	private static final long serialVersionUID = 5069935782478432045L;
	
	private InstituteTable institutes = new InstituteTable();
	
	@Property(value = "#{lectureService}")
	private LectureService lectureService;
	
	/**
	 * Store the selected institute into session scope and go to institute main page.
	 * @return Outcome
	 */
	public String selectInstitute() {
		Institute institute = currentInstitute();
		setSessionBean(Constants.INSTITUTE, institute);
		return Constants.INSTITUTE_PAGE;
	}
	
	public String shortcutInstitute() throws DesktopException {
		Institute institute = currentInstitute();
		desktopService.linkInstitute(desktop, institute);
		addMessage(i18n("message_institute_shortcut_created"));
		return Constants.SUCCESS;
	}

	private Institute currentInstitute() {
		InstituteDetails details = institutes.getRowData();
		Institute institute = Institute.Factory.newInstance();
		institute.setId(details.getId());
		return institute;
	}
	
	private DataPage<InstituteDetails> dataPage;
	
	public DataPage<InstituteDetails> fetchDataPage(int startRow, int pageSize) {
		
		if (dataPage == null) {
			if (logger.isDebugEnabled()) {
				logger.debug("fetch institutes data page at " + startRow + ", "+ pageSize+" sorted by "+institutes.getSortColumn());
			}
			List<InstituteDetails> instituteList = new ArrayList(getLectureService().getInstitutes(true));
			sort(instituteList);
			dataPage = new DataPage<InstituteDetails>(instituteList.size(),0,instituteList);
	}
		return dataPage;
	}

	private void sort(List<InstituteDetails> instituteList) {
		if (StringUtils.equals("shortcut", institutes.getSortColumn())) {
			Collections.sort(instituteList, new ShortcutComparator());
		} else if (StringUtils.equals("owner", institutes.getSortColumn())){
			Collections.sort(instituteList, new OwnerComparator());
		} else {
			Collections.sort(instituteList, new NameComparator());
		}
	}

	public InstituteTable getInstitutes() {
		return institutes;
	}

	private class InstituteTable extends AbstractPagedTable<InstituteDetails> {

		private static final long serialVersionUID = -6072435481342714879L;

		@Override
		public DataPage<InstituteDetails> getDataPage(int startRow, int pageSize) {
			return fetchDataPage(startRow, pageSize);
		}
		
	}
	
	public LectureService getLectureService() {
		return lectureService;
	}
	
	public void setLectureService(LectureService lectureService) {
		this.lectureService = lectureService;
	}

	
	/* ----------- institute sorting comparators -------------*/
	
	private class NameComparator implements Comparator<InstituteDetails> {
		public int compare(InstituteDetails f1, InstituteDetails f2) {
			if (institutes.isAscending()) {
				return f1.getName().compareToIgnoreCase(f2.getName());
			} else {
				return f2.getName().compareToIgnoreCase(f1.getName());
			}
		}
	}

	private class OwnerComparator implements Comparator<InstituteDetails> {
		public int compare(InstituteDetails f1, InstituteDetails f2) {
			if (institutes.isAscending()) {
				return f1.getOwnername().compareToIgnoreCase(f2.getOwnername());
			} else {
				return f2.getOwnername().compareToIgnoreCase(f1.getOwnername());
			}
		}
	}

	private class ShortcutComparator implements Comparator<InstituteDetails> {
		public int compare(InstituteDetails f1, InstituteDetails f2) {
			if (institutes.isAscending()) {
				return f1.getShortcut().compareToIgnoreCase(f2.getShortcut());
			} else {
				return f2.getShortcut().compareToIgnoreCase(f1.getShortcut());
			}
		}
	}

	
}