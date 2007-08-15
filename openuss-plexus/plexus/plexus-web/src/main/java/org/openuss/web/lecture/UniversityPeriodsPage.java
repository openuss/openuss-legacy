package org.openuss.web.lecture;

import java.util.ArrayList;
import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import org.apache.log4j.Logger;
import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Property;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.Prerender;
import org.apache.shale.tiger.view.View;
import org.openuss.desktop.DesktopException;
import org.openuss.framework.jsfcontrols.breadcrumbs.BreadCrumb;
import org.openuss.framework.web.jsf.model.AbstractPagedTable;
import org.openuss.framework.web.jsf.model.DataPage;
import org.openuss.lecture.LectureException;
import org.openuss.lecture.UniversityService;
import org.openuss.lecture.PeriodInfo;
import org.openuss.web.Constants;

/**
 * Periods Page Controller
 * 
 * @author Tianyu Wang
 * @author Weijun Chen
 */
@Bean(name = "views$secured$lecture$universityperiods", scope = Scope.REQUEST)
@View
public class UniversityPeriodsPage extends AbstractUniversityPage {

	private static final Logger logger = Logger.getLogger(PeriodsPage.class);

	private PeriodDataModel periodData = new PeriodDataModel();
	
	
	
	

	
	@Property(value = "#{sessionScope.period}")
	private PeriodInfo period;
	
	@Prerender
	@Override
	public void prerender() throws LectureException {
		super.prerender();
		if (universityInfo != null) {
			// refresh period list
			// TODO ask the lectureService instead of institute and use value objects
			List<PeriodInfo> periods = universityService.findPeriodsByUniversity(universityInfo.getId());
			
			period = (PeriodInfo) getSessionBean(Constants.PERIOD);
			if (period != null) {
				if (period.getId() != null && periods.contains(period)) {
					period = universityService.findPeriod(period.getId());
				} else {
					period = null;
				}
			}
		}
		
		setSessionBean(Constants.PERIOD, period);
		
	
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
	 * Change the actual active period.
	 * 
	 * @return outcome
	 * @throws LectureException 
	 */
	public String periodToActive() throws LectureException {
		if (logger.isDebugEnabled()) {
			logger.debug("change active period of line " + periodData.getRowIndex());
		}
		
		PeriodInfo period = periodData.getRowData();
		//universityService.setActivePeriod(university.getId(), period);

		return Constants.SUCCESS;
	}

	/**
	 * Select a period
	 * 
	 * @return outcome
	 */
	public String selectPeriod() {
		if (logger.isDebugEnabled()) {
			logger.debug("select period of line " + periodData.getRowIndex());
		}
		PeriodInfo period = periodData.getRowData();
		setSessionBean(Constants.PERIOD, period);

		return Constants.SUCCESS;
	}

	/**
	 * Adds a new period to institute
	 * @return
	 */
	public String addPeriod() {
		PeriodInfo period = new PeriodInfo();
		setSessionBean(Constants.PERIOD, period);
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
		universityService.removePeriod(period.getId());
		removeSessionBean(Constants.PERIOD);
		return Constants.INSTITUTE_PERIODS_PAGE;
	}

	/**
	 * Store changes in the business layer.
	 * 
	 * @return outcome
	 * @throws LectureException
	 */
	public String savePeriod() throws LectureException {
		if (period.getId() == null) {
			universityService.create(period);
		} else {
			universityService.update(period);
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

	
	public PeriodInfo getPeriod() {
		return period;
	}
	
	public void setPeriod(PeriodInfo period) {
		logger.trace("setPeriod " + period);
		this.period = period;
	}
}
	