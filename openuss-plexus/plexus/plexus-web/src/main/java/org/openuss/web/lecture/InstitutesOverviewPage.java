package org.openuss.web.lecture;

import static org.openuss.web.lecture.InstitutesOverviewPage.logger;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Property;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.Prerender;
import org.apache.shale.tiger.view.View;
import org.openuss.desktop.DesktopException;
import org.openuss.framework.web.jsf.model.AbstractPagedTable;
import org.openuss.framework.web.jsf.model.DataPage;
import org.openuss.lecture.DepartmentInfo;
import org.openuss.lecture.InstituteInfo;
import org.openuss.lecture.InstituteService;
import org.openuss.lecture.LectureService;
import org.openuss.web.BasePage;
import org.openuss.web.Constants;

/**
 * 
 * @author Kai Stettner
 *
 */
@Bean(name = "views$public$institute$institutesoverview", scope = Scope.REQUEST)
@View
public class InstitutesOverviewPage extends BasePage{

	protected static final Logger logger = Logger.getLogger(InstitutesOverviewPage.class);

	private static final long serialVersionUID = 5069930000478432045L;
	
	private InstituteTable institutesOverview = new InstituteTable();
	
	@Property(value = "#{lectureService}")
	private LectureService lectureService;
	
	@Property(value = "#{instituteService}")
	private InstituteService instituteService;

	@Prerender
	public void prerender() throws Exception {
	}	
	
	/**
	 * Store the selected institute into session scope and go to institute main page.
	 * @return Outcome
	 */
	public String selectInstitute() {
		logger.debug("Starting method selectInstitute");
		InstituteInfo currentInstitute = currentInstitute();
		logger.debug("Returning to method selectInstitute");
		logger.debug(currentInstitute.getId());	
		//setSessionBean(Constants.INSTITUTE, institute);
		setSessionBean(Constants.INSTITUTE_INFO, currentInstitute);
		
		return Constants.INSTITUTE_PAGE;
	}
	
	public String shortcutInstitute() throws DesktopException {
		logger.debug("Starting method shortcutInstitute");
		InstituteInfo currentInstitute = currentInstitute();
		//desktopService.linkInstitute(desktop, currentInstitute);
		desktopService2.linkInstitute(desktopInfo.getId(), currentInstitute.getId() );
		
		addMessage(i18n("message_institute_shortcut_created"));
		return Constants.SUCCESS;
	}

	private InstituteInfo currentInstitute() {
		logger.debug("Starting method currentInstitute");
		InstituteInfo instituteDetails = institutesOverview.getRowData();
		logger.debug(instituteDetails.getName());
		logger.debug(instituteDetails.getOwnerName());
		logger.debug(instituteDetails.getId());
		//Institute institute = Institute.Factory.newInstance();
		InstituteInfo newInstituteInfo = new InstituteInfo();
		//institute.setId(details.getId());
		newInstituteInfo.setId(instituteDetails.getId());
		return newInstituteInfo;
	}
	
	/**
	 * Disables the chosen institute. This is just evident for the search indexing.
	 * @return Outcome
	 */
	public String disableInstitute() {
		logger.debug("Starting method disableInstitute");
		InstituteInfo currentInstitute = currentInstitute();
		// setOrganisationStatus(true) = Enabled
		// setOrganisationStatus(false) = Disabled
		instituteService.setInstituteStatus(currentInstitute.getId(), false);
		
		addMessage(i18n("message_institute_disabled"));
		return Constants.SUCCESS;
	}
	
	/**
	 * Enables the chosen institute. This is just evident for the search indexing.
	 * @return Outcome
	 */
	public String enableInstitute() {
		logger.debug("Starting method enableInstitute");
		InstituteInfo currentInstitute = currentInstitute();
		// setOrganisationStatus(true) = Enabled
		// setOrganisationStatus(false) = Disabled
		instituteService.setInstituteStatus(currentInstitute.getId(), true);
		
		addMessage(i18n("message_institute_enabled"));
		return Constants.SUCCESS;
	}
	
	private DataPage<InstituteInfo> dataPage;
	
	public DataPage<InstituteInfo> fetchDataPage(int startRow, int pageSize) {
		
		if (dataPage == null) {
			if (logger.isDebugEnabled()) {
				logger.debug("fetch institutes data page at " + startRow + ", "+ pageSize+" sorted by "+institutesOverview.getSortColumn());
			}
			
			
			DepartmentInfo departmentInfo = (DepartmentInfo) getSessionBean(Constants.DEPARTMENT_INFO);			
			// get all institutes. Does not depend whether it is enabled or disabled
			List<InstituteInfo> instituteList = getInstituteService().findInstitutesByDepartmentAndEnabled(departmentInfo.getId(),true);
			sort(instituteList);
			dataPage = new DataPage<InstituteInfo>(instituteList.size(),0,instituteList);

		}
		return dataPage;
	}
	
	private void sort(List<InstituteInfo> instituteList) {
		if (StringUtils.equals("shortcut", institutesOverview.getSortColumn())) {
			Collections.sort(instituteList, new ShortcutComparator());
		} else if (StringUtils.equals("owner", institutesOverview.getSortColumn())){
			Collections.sort(instituteList, new OwnerComparator());
		} else {
			Collections.sort(instituteList, new NameComparator());
		}
	}

	private class InstituteTable extends AbstractPagedTable<InstituteInfo> {

		private static final long serialVersionUID = -6072435481342714879L;
	
		@Override
		public DataPage<InstituteInfo> getDataPage(int startRow, int pageSize) {
			logger.debug("Starting method getDataPage");
			return fetchDataPage(startRow, pageSize);
		}
	}

	public LectureService getLectureService() {
		return lectureService;
	}

	public void setLectureService(LectureService lectureService) {
		this.lectureService = lectureService;
	}
	
	public InstituteService getInstituteService() {
		return instituteService;
	}

	public void setInstituteService(InstituteService instituteService) {
		this.instituteService = instituteService;
	}
	
	public InstituteTable getInstitutesOverview() {
		return institutesOverview;
	}

	public void setInstitutesOverview(InstituteTable institutesOverview) {
		this.institutesOverview = institutesOverview;
	}	

	/* ----------- institute sorting comparators -------------*/
	
	private class NameComparator implements Comparator<InstituteInfo> {
		public int compare(InstituteInfo f1, InstituteInfo f2) {
			if (institutesOverview.isAscending()) {
				return f1.getName().compareToIgnoreCase(f2.getName());
			} else {
				return f2.getName().compareToIgnoreCase(f1.getName());
			}
		}
	}

	private class OwnerComparator implements Comparator<InstituteInfo> {
		public int compare(InstituteInfo f1, InstituteInfo f2) {
			if (institutesOverview.isAscending()) {
				return f1.getOwnerName().compareToIgnoreCase(f2.getOwnerName());
			} else {
				return f2.getOwnerName().compareToIgnoreCase(f1.getOwnerName());
			}
		}
	}

	private class ShortcutComparator implements Comparator<InstituteInfo> {
		public int compare(InstituteInfo f1, InstituteInfo f2) {
			if (institutesOverview.isAscending()) {
				return f1.getShortcut().compareToIgnoreCase(f2.getShortcut());
			} else {
				return f2.getShortcut().compareToIgnoreCase(f1.getShortcut());
			}
		}
	}
}