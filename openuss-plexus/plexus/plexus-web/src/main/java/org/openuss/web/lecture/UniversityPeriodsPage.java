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
 */
@Bean(name = "views$secured$lecture$universityperiods", scope = Scope.REQUEST)
@View
public class UniversityPeriodsPage extends AbstractUniversityPage {

	private static final Logger logger = Logger.getLogger(PeriodsPage.class);

	private PeriodDataModel periodData = new PeriodDataModel();
	
	@Property(value = "#{sessionScope.periodInfo}")
	private PeriodInfo periodInfo;
	
	private List<PeriodInfo> periods;
	
	@Prerender
	@Override
	public void prerender() throws LectureException {
		super.prerender();
		if (universityInfo != null) {
			// refresh period list
			// TODO ask the lectureService instead of institute and use value objects
			periods = universityService.findPeriodsByUniversity(universityInfo.getId());
			
			periodInfo = (PeriodInfo) getSessionBean(Constants.PERIOD_INFO);
			if (periodInfo != null) {
				if (periodInfo.getId() != null && periods.contains(periodInfo)) {
					periodInfo = universityService.findPeriod(periodInfo.getId());
				} else {
					periodInfo = null;
				}
			}
		}
		
		setSessionBean(Constants.PERIOD_INFO, periodInfo);
		
	
		addPageCrumb();
	}
	
	private void addPageCrumb() {
		BreadCrumb crumb = new BreadCrumb();
		crumb.setLink("");
		crumb.setName(i18n("period_heading"));
		crumb.setHint(i18n("period_heading"));
		crumbs.add(crumb);
		setSessionBean(Constants.BREADCRUMBS, crumbs);
	}	

	/**
	 * Is selected period active?
	 * 
	 * @return outcome
	 */
	public boolean getIsActivePeriod() {
		logger.debug("Starting method isActivePeriod");
		PeriodInfo periodInfo = periodData.getRowData();
		boolean active = universityService.isActivePeriod(periodInfo.getId());
		logger.debug("Active? "+active);
		
		return active;
	}
	
	/**
	 * Adds a new period to institute
	 * @return
	 */
	public String addPeriod() {
		PeriodInfo periodInfo = new PeriodInfo();
		setSessionBean(Constants.PERIOD_INFO, periodInfo);
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
		setSessionBean(Constants.PERIOD_INFO, periodInfo);
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
		setSessionBean(Constants.PERIOD_INFO, periodInfo);
		return Constants.UNIVERSITY_PERIOD_REMOVE_PAGE;
	}

	/**
	 * Forwards to an additional security question
	 * 
	 * @return outcome
	 * @throws LectureException
	 */
	public String removePeriod() throws LectureException {
		if (logger.isDebugEnabled()) {
			logger.debug("Remove Period");
		}
		universityService.removePeriod(periodInfo.getId());
		removeSessionBean(Constants.PERIOD_INFO);
		return Constants.INSTITUTE_PERIODS_PAGE;
	}

	/**
	 * Store changes in the business layer.
	 * 
	 * @return outcome
	 * @throws LectureException
	 */
	public String savePeriod() throws LectureException {
		if (periodInfo.getId() == null) {
			universityService.createPeriod(periodInfo);
		} else {
			universityService.update(periodInfo);
		}
		
		return Constants.INSTITUTE_PERIODS_PAGE;
	}

	

	/**
	 * Validator to check whether the user has accepted the user agreement or
	 * not.
	 * 
	 * @param context
	 * @param toValidate
	 * @param value
	 */
	/*
	public void validateRemoveConfirmation(FacesContext context, UIComponent toValidate, Object value) {
		boolean accept = (Boolean) value;
		if (!accept) {
			((UIInput) toValidate).setValid(false);
			addError(toValidate.getClientId(context), i18n("error_need_to_confirm_removement"), null);
		}
	}
*/

	/* ------------------ data models ------------------- */
	
	private class PeriodDataModel extends AbstractPagedTable<PeriodInfo> {
		
		private static final long serialVersionUID = 1872495569523386246L;
		
		private DataPage<PeriodInfo> page;
		
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
}
	