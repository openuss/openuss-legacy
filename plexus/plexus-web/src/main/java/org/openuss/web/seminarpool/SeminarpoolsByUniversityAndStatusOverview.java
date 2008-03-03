package org.openuss.web.seminarpool;

import java.util.ArrayList;
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
import org.openuss.lecture.UniversityInfo;
import org.openuss.seminarpool.SeminarpoolAdministrationService;
import org.openuss.seminarpool.SeminarpoolInfo;
import org.openuss.seminarpool.SeminarpoolStatus;
import org.openuss.web.BasePage;
import org.openuss.web.Constants;


@Bean(name = "views$public$institute$seminarpoolsbyuniversityandstatusoverview", scope = Scope.REQUEST)
@View
public class SeminarpoolsByUniversityAndStatusOverview extends BasePage {

	protected static final Logger logger = Logger.getLogger(SeminarpoolsByUniversityAndStatusOverview.class);

	

	private SeminarpoolTable seminarpoolOverview = new SeminarpoolTable();

	@Property(value = "#{seminarpoolAdministrationService}")
	private SeminarpoolAdministrationService seminarpoolAdministrationService;


	@Prerender
	public void prerender() throws Exception {
		logger.debug("Starting method selectInstitute");		
	}

	/**
	 * Store the selected institute into session scope and go to institute main
	 * page.
	 * 
	 * @return Outcome
	 */
	public String selectSeminarpool() {
		logger.debug("Starting method selectInstitute");
		SeminarpoolInfo seminarpoolInfo = currentSeminarpool();
		logger.debug("Returning to method selectInstitute");
		logger.debug(seminarpoolInfo.getId());
		// setSessionBean(Constants.INSTITUTE, institute);
		setSessionBean(Constants.SEMINARPOOL_INFO, seminarpoolInfo);

		return Constants.INSTITUTE_PAGE;
	}

	public String shortcutSeminarpool() throws DesktopException {
		logger.debug("Starting method shortcutSeminarpool");
		SeminarpoolInfo seminarpoolInfo = currentSeminarpool();
		// desktopService.linkInstitute(desktop, currentSeminarpool);
		desktopService2.linkSeminarpool(desktopInfo.getId(), seminarpoolInfo.getId());

		addMessage(i18n("message_seminarpool_shortcut_created"));
		return Constants.SUCCESS;
	}

	public Boolean getBookmarked() {
		try {
			SeminarpoolInfo currentSeminarpool = currentSeminarpool();
			return desktopService2.isSeminarpoolBookmarked(desktopInfo.getId(), currentSeminarpool.getId());
		} catch (Exception e) {
			logger.error(e);
		}

		return false;
	}
	
	
	public String removeShortcut() {
		try {
			SeminarpoolInfo currentSeminarpool = currentSeminarpool();
			desktopService2.unlinkSeminarpool(desktopInfo.getId(), currentSeminarpool.getId());
		} catch (Exception e) {
			addError(i18n("seminarpool_error_remove_shortcut"), e.getMessage());
			return Constants.FAILURE;
		}

		addMessage(i18n("seminarpool_success_remove_shortcut"));
		return Constants.SUCCESS;
	}

	private SeminarpoolInfo currentSeminarpool() {
		logger.debug("Starting method currentSeminarpool");
		SeminarpoolInfo seminarpoolDetails = seminarpoolOverview.getRowData();
		// logger.debug(instituteDetails.getName());
		// logger.debug(instituteDetails.getOwnerName());
		// logger.debug(instituteDetails.getId());
		// Institute institute = Institute.Factory.newInstance();
		SeminarpoolInfo newSeminarpoolInfo = new SeminarpoolInfo();
		// institute.setId(details.getId());
		newSeminarpoolInfo.setId(seminarpoolDetails.getId());
		return newSeminarpoolInfo;
	}

	

	private DataPage<SeminarpoolInfo> dataPage;

	public DataPage<SeminarpoolInfo> fetchDataPage(int startRow, int pageSize) {

		if (dataPage == null) {
			if (logger.isDebugEnabled()) {
				logger.debug("fetch institutes data page at " + startRow + ", " + pageSize + " sorted by "
						+ seminarpoolOverview.getSortColumn());
			}

			UniversityInfo universityInfo = (UniversityInfo) getSessionBean(Constants.UNIVERSITY_INFO);
			// get all institutes. Does not depend whether it is enabled or
			// disabled
			List<SeminarpoolInfo> seminarpoolList = new ArrayList<SeminarpoolInfo>(getSeminarpoolAdministrationService().findSeminarpoolsByUniversityAndStatus(universityInfo.getId(), SeminarpoolStatus.PREPARATIONPHASE));
			sort(seminarpoolList);
			dataPage = new DataPage<SeminarpoolInfo>(seminarpoolList.size(), 0, seminarpoolList);
		}
		return dataPage;
	}

	private void sort(List<SeminarpoolInfo> seminarpoolList) {
		if (StringUtils.equals("shortcut", seminarpoolOverview.getSortColumn())) {
			Collections.sort(seminarpoolList, new ShortcutComparator());
		} 
		else {
			Collections.sort(seminarpoolList, new NameComparator());
		}
	}

	private class SeminarpoolTable extends AbstractPagedTable<SeminarpoolInfo> {

		private static final long serialVersionUID = -6072435481342714879L;

		@Override
		public DataPage<SeminarpoolInfo> getDataPage(int startRow, int pageSize) {
			logger.debug("Starting method getDataPage");
			return fetchDataPage(startRow, pageSize);
		}
	}

	
	public SeminarpoolTable getSeminarpoolOverview() {
		return seminarpoolOverview;
	}

	public void setSeminarpoolOverview(SeminarpoolTable seminarpoolsOverview) {
		this.seminarpoolOverview = seminarpoolsOverview;
	}

	/* ----------- institute sorting comparators ------------- */

	private class NameComparator implements Comparator<SeminarpoolInfo> {
		public int compare(SeminarpoolInfo f1, SeminarpoolInfo f2) {
			if (seminarpoolOverview.isAscending()) {
				return f1.getName().compareToIgnoreCase(f2.getName());
			} else {
				return f2.getName().compareToIgnoreCase(f1.getName());
			}
		}
	}

	

	private class ShortcutComparator implements Comparator<SeminarpoolInfo> {
		public int compare(SeminarpoolInfo f1, SeminarpoolInfo f2) {
			if (seminarpoolOverview.isAscending()) {
				return f1.getShortcut().compareToIgnoreCase(f2.getShortcut());
			} else {
				return f2.getShortcut().compareToIgnoreCase(f1.getShortcut());
			}
		}
	}

	public SeminarpoolAdministrationService getSeminarpoolAdministrationService() {
		return seminarpoolAdministrationService;
	}

	public void setSeminarpoolAdministrationService(
			SeminarpoolAdministrationService seminarpoolAdministrationService) {
		this.seminarpoolAdministrationService = seminarpoolAdministrationService;
	}

	
}