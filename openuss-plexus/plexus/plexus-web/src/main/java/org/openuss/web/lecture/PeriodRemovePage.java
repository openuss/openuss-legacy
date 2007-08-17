package org.openuss.web.lecture;

import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;
import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.Preprocess;
import org.apache.shale.tiger.view.Prerender;
import org.apache.shale.tiger.view.View;
import org.openuss.framework.jsfcontrols.breadcrumbs.BreadCrumb;
import org.openuss.lecture.LectureException;
import org.openuss.web.Constants;

/**
 * Periods Remove Page Controller
 * 
 * @author Ingo Dueppe
 * @author Kai Stettner
 */
@Bean(name = "views$secured$lecture$periodremove", scope = Scope.REQUEST)
@View
public class PeriodRemovePage extends AbstractPeriodPage {

	private static final Logger logger = Logger.getLogger(PeriodsPage.class);

	@Preprocess
	@Override
	public void preprocess() throws Exception {
		super.preprocess();
		if (periodInfo != null) {
			periodInfo = universityService.findPeriod(periodInfo.getId());
			setSessionBean(Constants.PERIOD_INFO, periodInfo);
		}
	}

	@Prerender
	@Override
	public void prerender() throws LectureException {
		super.prerender();
		if (periodInfo != null) {
			periodInfo = universityService.findPeriod(periodInfo.getId());
			setSessionBean(Constants.PERIOD_INFO, periodInfo);
		}
		addPageCrumb();
	}
	
	private void addPageCrumb() {
		BreadCrumb crumb = new BreadCrumb();
		crumb.setLink("");
		crumb.setName(i18n("period_remove_header"));
		crumb.setHint(i18n("period_remove_header"));
		crumbs.add(crumb);
		setSessionBean(Constants.BREADCRUMBS, crumbs);
	}	

	/**
	 * Forwards to an additional security question
	 * 
	 * @return outcome
	 * @throws LectureException
	 */
	//FIXME This is only possible, if no Course had been added to the Period
	// You don't have the right to remove Courses!
	public String removePeriod() throws LectureException {
		if (logger.isDebugEnabled())
			logger.debug("Remove Period");
		universityService.removePeriod(periodInfo.getId());
		removeSessionBean(Constants.PERIOD_INFO);
		return Constants.UNIVERSITY_PERIODS_PAGE;
	}

	/**
	 * Validator to check weather the user has accepted the user agreement or
	 * not.
	 * 
	 * @param context
	 * @param toValidate
	 * @param value
	 */
	public void validateRemoveConfirmation(FacesContext context, UIComponent toValidate, Object value) {
		boolean accept = (Boolean) value;
		if (!accept) {
			((UIInput) toValidate).setValid(false);
			addError(toValidate.getClientId(context), i18n("error_need_to_confirm_removement"), null);
		}
	}

}
