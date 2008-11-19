package org.openuss.web.lecture;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Property;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.Prerender;
import org.apache.shale.tiger.view.View;
import org.openuss.framework.jsfcontrols.breadcrumbs.BreadCrumb;
import org.openuss.framework.web.jsf.model.AbstractPagedTable;
import org.openuss.framework.web.jsf.model.DataPage;
import org.openuss.lecture.LectureException;
import org.openuss.lecture.PeriodInfo;
import org.openuss.web.Constants;

/**
 * Periods Page Controller
 * 
 * @author Kai Stettner
 * @author Tianyu Wang
 * @author Weijun Chen
 * @author Sebastian Roekens
 * 
 */
@Bean(name = "views$secured$lecture$universityperiods", scope = Scope.REQUEST)
@View
public class UniversityPeriodsPage extends AbstractUniversityPage {

	private static final Logger logger = Logger.getLogger(UniversityPeriodsPage.class);

	private PeriodDataModel periodData = new PeriodDataModel();
	
	@Property(value = "#{periodInfo}")
	private PeriodInfo periodInfo;
	
	private List<PeriodInfo> periods;
	
	@Prerender
	@Override
	@SuppressWarnings( { "unchecked" })
	public void prerender() throws LectureException {
		super.prerender();
		if (isRedirected()){
			return;
		}
		if (universityInfo != null) {
			// refresh period list
			periods = universityService.findPeriodsByUniversity(universityInfo.getId());
			
			if (periodInfo != null) {
				if (periodInfo.getId() != null && periods.contains(periodInfo)) {
					periodInfo = universityService.findPeriod(periodInfo.getId());
				} else {
					periodInfo = null;
				}
			}
		}
		
		setBean(Constants.PERIOD_INFO, periodInfo);
		addPageCrumb();
	}
	
	private void addPageCrumb() {
		BreadCrumb crumb = new BreadCrumb();
		crumb.setLink("");
		crumb.setName(i18n("university_command_periods"));
		crumb.setHint(i18n("university_command_periods"));
		
		breadcrumbs.loadUniversityCrumbs(universityInfo);
		breadcrumbs.addCrumb(crumb);
	}	

	/**
	 * Is selected period active?
	 * 
	 * @return outcome
	 */
	public boolean getIsActivePeriod() {
		logger.debug("Starting method isActivePeriod");
		PeriodInfo periodInfo = periodData.getRowData();
		
		return periodInfo.isActive();
	}
	
	/**
	 * Adds a new period to university
	 * @return
	 */
	public String addPeriod() {
		PeriodInfo periodInfo = new PeriodInfo();
		setBean(Constants.PERIOD_INFO, periodInfo);
		return Constants.UNIVERSITY_PERIOD_ADD_PAGE;
	}
	

	/**
	 * Sets the selected period into session scope and forward to the period
	 * edit view
	 * 
	 * @return outcome
	 */
	public String editPeriod() {
		logger.debug("edit period");
		PeriodInfo periodInfo = periodData.getRowData();
		logger.debug("set sessionBean");
		setBean(Constants.PERIOD_INFO, periodInfo);
		return Constants.UNIVERSITY_PERIOD_EDIT_PAGE;
	}

	/**
	 * Sets the selected period into session scope and forward to the period
	 * remove view
	 * 
	 * @return outcome
	 */
	public String confirmRemovePeriod() {
		PeriodInfo periodInfo = periodData.getRowData();
		setBean(Constants.PERIOD_INFO, periodInfo);
		return Constants.UNIVERSITY_PERIOD_REMOVE_PAGE;
	}
	
	/* -------- properties ---------- */

	public PeriodDataModel getPeriodData() {
		return periodData;
	}

	public void setPeriodData(PeriodDataModel periodData) {
		this.periodData = periodData;
	}
	
	public PeriodInfo getPeriodInfo() {
		return periodInfo;
	}

	public void setPeriodInfo(PeriodInfo periodInfo) {
		logger.trace("setPeriodInfo " + periodInfo);
		this.periodInfo = periodInfo;
	}

	/* ------------------ data models ------------------- */
	
	private class PeriodDataModel extends AbstractPagedTable<PeriodInfo> {
		
		private static final long serialVersionUID = 1872495569523386246L;
		
		private DataPage<PeriodInfo> page;
		
		@SuppressWarnings("unchecked")
		@Override
		public DataPage<PeriodInfo> getDataPage(int startRow, int pageSize) {
			if (page == null) {
				List<PeriodInfo> periods = new ArrayList<PeriodInfo>(universityService.findPeriodsByUniversity(universityInfo.getId()));
				sort(periods);
				page = new DataPage<PeriodInfo>(periods.size(),0,periods);
			}
			return page;
		}
	}
	

}
	