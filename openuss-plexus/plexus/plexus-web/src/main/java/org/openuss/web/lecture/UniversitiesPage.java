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
import org.openuss.lecture.InstituteInfo;
import org.openuss.lecture.UniversityInfo;
import org.openuss.lecture.UniversityService;
import org.openuss.web.BasePage;
import org.openuss.web.Constants;

/**
 * 
 * @author Tianyu Wang
 * @author Weijun Chen
 * @author Kai Stettner
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
	
	/**
	 * Store the selected university into session scope and go to university main page.
	 * @return Outcome
	 */
	public String selectUniversity() {
		logger.debug("Starting method selectUniversity");
		UniversityInfo currentUniversity = currentUniversity();
		logger.debug("Returning to method selectUniversity");
		logger.debug(currentUniversity.getId());	
		setSessionBean(Constants.UNIVERSITY_INFO, currentUniversity);
		
		return Constants.UNIVERSITY_PAGE;
	}
	
	public String shortcutUniversity() throws DesktopException {
		logger.debug("Starting method shortcutUniversity");
		UniversityInfo currentUniversity = currentUniversity();
		desktopService2.linkInstitute(desktop.getId(), currentUniversity.getId() );
		
		addMessage(i18n("message_university_shortcut_created"));
		return Constants.SUCCESS;
	}
	
	private UniversityInfo currentUniversity() {
		logger.debug("Starting method currentUniversity");
		UniversityInfo universityDetails = universities.getRowData();
		logger.debug(universityDetails.getName());
		logger.debug(universityDetails.getOwnerName());
		logger.debug(universityDetails.getId());
		UniversityInfo newUniversityInfo = new UniversityInfo();
		newUniversityInfo.setId(universityDetails.getId());
		return newUniversityInfo;
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

	private void sort(List<UniversityInfo> universityList) {
		if (StringUtils.equals("shortcut", universities.getSortColumn())) {
			Collections.sort(universityList, new ShortcutComparator());
		} else if (StringUtils.equals("city", universities.getSortColumn())){
			Collections.sort(universityList, new CityComparator());
		} else if (StringUtils.equals("country", universities.getSortColumn())){
			Collections.sort(universityList, new CountryComparator());
		} else {
			Collections.sort(universityList, new NameComparator());
		}
	}

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

	
	/* ----------- university sorting comparators -------------*/
	
	private class NameComparator implements Comparator<UniversityInfo> {
		public int compare(UniversityInfo f1, UniversityInfo f2) {
			if (universities.isAscending()) {
				return f1.getName().compareToIgnoreCase(f2.getName());
			} else {
				return f2.getName().compareToIgnoreCase(f1.getName());
			}
		}
	}

	private class CityComparator implements Comparator<UniversityInfo> {
		public int compare(UniversityInfo f1, UniversityInfo f2) {
			if (universities.isAscending()) {
				return f1.getCity().compareToIgnoreCase(f2.getCity());
			} else {
				return f2.getCity().compareToIgnoreCase(f1.getCity());
			}
		}
	}
	
	private class CountryComparator implements Comparator<UniversityInfo> {
		public int compare(UniversityInfo f1, UniversityInfo f2) {
			if (universities.isAscending()) {
				return f1.getCountry().compareToIgnoreCase(f2.getCountry());
			} else {
				return f2.getCountry().compareToIgnoreCase(f1.getCountry());
			}
		}
	}

	private class ShortcutComparator implements Comparator<UniversityInfo> {
		public int compare(UniversityInfo f1, UniversityInfo f2) {
			if (universities.isAscending()) {
				return f1.getShortcut().compareToIgnoreCase(f2.getShortcut());
			} else {
				return f2.getShortcut().compareToIgnoreCase(f1.getShortcut());
			}
		}
	}
	
	
	

	
	public String editUniversity(){
		UniversityInfo universityInfo = currentUniversity();
		setSessionBean(Constants.UNIVERSITY_INFO, universityInfo);
		
		return Constants.UNIVERSITY_PAGE;
	}
	
	public String confirmRemoveUniversity(){
		UniversityInfo universityInfo = currentUniversity();
		setSessionBean(Constants.UNIVERSITY_INFO, universityInfo);
		return "removed";
	}

	public String manageUniversity(){
		UniversityInfo universityInfo = currentUniversity();
		setSessionBean(Constants.UNIVERSITY_INFO, universityInfo);
		
		return Constants.DEPARTMENT_MANAGEMENT;
	}

}