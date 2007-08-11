package org.openuss.web.lecture;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Property;
import org.apache.shale.tiger.view.Prerender;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.View;
import org.openuss.desktop.DesktopException;
import org.openuss.framework.web.jsf.model.AbstractPagedTable;
import org.openuss.framework.web.jsf.model.DataPage;
import org.openuss.lecture.Institute;
import org.openuss.lecture.UniversityInfo;
import org.openuss.lecture.UniversityService;
import org.openuss.web.BasePage;
import org.openuss.web.Constants;



/**
 * 
 * @author Tianyu Wang
 * @author Weijun Chen
 *
 */
@Bean(name = "views$public$university$universities", scope = Scope.REQUEST)
@View
public class UniversitiesPage extends BasePage{

	private static final Logger logger = Logger.getLogger(InstitutesPage.class);

	private static final long serialVersionUID = 5069935767478432045L;
	
	private UniversityTable universities = new UniversityTable();
	
	@Property(value = "#{universityService}")
	private UniversityService universityService;
	
	@Prerender
	public void prerender() throws Exception {
		crumbs.clear();
	}	
	
	private UniversityInfo currentUniversity() {
		
		UniversityInfo university = universities.getRowData();
		
		return university;
	}
	
	private DataPage<UniversityInfo> dataPage;
	
	public DataPage<UniversityInfo> fetchDataPage(int startRow, int pageSize) {
		
		if (dataPage == null) {
			if (logger.isDebugEnabled()) {
				logger.debug("fetch institutes data page at " + startRow + ", "+ pageSize+" sorted by "+universities.getSortColumn());
			}
			List<UniversityInfo> universityList = new ArrayList<UniversityInfo>(getUniversityService().findAllUniversities());
			//sort(instituteList);
			dataPage = new DataPage<UniversityInfo>(universityList.size(),0,universityList);
	}
		return dataPage;
	}

/*private void sort(List<InstituteDetails> instituteList) {
		if (StringUtils.equals("shortcut", universities.getSortColumn())) {
			Collections.sort(instituteList, new ShortcutComparator());
		} else if (StringUtils.equals("owner", universities.getSortColumn())){
			Collections.sort(instituteList, new OwnerComparator());
		} else {
			Collections.sort(instituteList, new NameComparator());
		}
	}*/

	public UniversityTable getUniversities() {
		return universities;
	}

	private class UniversityTable extends AbstractPagedTable<UniversityInfo> {

		private static final long serialVersionUID = -6072435481342714879L;

		@Override
		public DataPage<UniversityInfo> getDataPage(int startRow, int pageSize) {
			return fetchDataPage(startRow, pageSize);
		}
		
	}
	
	public UniversityService getUniversityService() {
		return universityService;
	}
	
	public void setUniversityService(UniversityService universityService) {
		this.universityService = universityService;
	}

	
	/* ----------- institute sorting comparators -------------*/
	
	/*private class NameComparator implements Comparator<UniversityInfo> {
		public int compare(InstituteDetails f1, InstituteDetails f2) {
			if (universities.isAscending()) {
				return f1.getName().compareToIgnoreCase(f2.getName());
			} else {
				return f2.getName().compareToIgnoreCase(f1.getName());
			}
		}
	}

	private class OwnerComparator implements Comparator<InstituteDetails> {
		public int compare(InstituteDetails f1, InstituteDetails f2) {
			if (universities.isAscending()) {
				return f1.getOwnername().compareToIgnoreCase(f2.getOwnername());
			} else {
				return f2.getOwnername().compareToIgnoreCase(f1.getOwnername());
			}
		}
	}

	private class ShortcutComparator implements Comparator<InstituteDetails> {
		public int compare(InstituteDetails f1, InstituteDetails f2) {
			if (universities.isAscending()) {
				return f1.getShortcut().compareToIgnoreCase(f2.getShortcut());
			} else {
				return f2.getShortcut().compareToIgnoreCase(f1.getShortcut());
			}
		}
	}*/

	
	public String editUniversity(){
		UniversityInfo university = currentUniversity();
		setSessionBean(Constants.UNIVERSITY, university);
		
		return Constants.UNIVERSITY_PAGE;
	}
	
	public String confirmRemoveUniversity(){
		UniversityInfo university = currentUniversity();
		setSessionBean(Constants.UNIVERSITY, university);
		return "removed";
	}


}