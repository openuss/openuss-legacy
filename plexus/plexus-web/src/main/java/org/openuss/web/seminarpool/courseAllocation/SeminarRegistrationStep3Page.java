package org.openuss.web.seminarpool.courseAllocation;

import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;

import org.apache.log4j.Logger;
import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.Init;
import org.apache.shale.tiger.view.Prerender;
import org.apache.shale.tiger.view.View;
import org.openuss.framework.jsfcontrols.breadcrumbs.BreadCrumb;
import org.openuss.seminarpool.DayOfWeek;
import org.openuss.web.BasePage;
import org.openuss.web.Constants;

@Bean(name = "views$secured$seminarpool$add$courseAllocationStep3", scope = Scope.REQUEST)
@View
public class SeminarRegistrationStep3Page extends BasePage {
		
	private static final Logger logger = Logger.getLogger(SeminarRegistrationStep3Page.class);
	
	private List<SelectItem> weekDays;
	
	@Prerender
	public void prerender() throws Exception  {
		breadcrumbs.init();	
		BreadCrumb newCrumb = new BreadCrumb();
		newCrumb.setName(i18n("seminarpool_add_course_allocation_breadcrumb_step3"));
		newCrumb.setHint(i18n("seminarpool_add_course_allocation_breadcrumb_step3"));
		breadcrumbs.addCrumb(newCrumb);	
	}
	
	public List<SelectItem> getWeekDays(){
		if ( weekDays == null ) {
			weekDays = new ArrayList<SelectItem>();
//				SelectItem item = new SelectItem();
//				item.setLabel(i18n(Constants.SEMINARPOOL_DAY_OF_WEEK_PREFIX + DayOfWeek.SUNDAY.getValue()));
//				item.setDisabled(false);
//				weekDays.add(item);
			weekDays.add(new SelectItem(DayOfWeek.SUNDAY.getValue(), i18n(Constants.SEMINARPOOL_DAY_OF_WEEK_PREFIX + DayOfWeek.SUNDAY.getValue())));
			weekDays.add(new SelectItem(DayOfWeek.MONDAY.getValue(), i18n(Constants.SEMINARPOOL_DAY_OF_WEEK_PREFIX + DayOfWeek.MONDAY.getValue())));
			weekDays.add(new SelectItem(DayOfWeek.TUESDAY.getValue(), i18n(Constants.SEMINARPOOL_DAY_OF_WEEK_PREFIX + DayOfWeek.TUESDAY.getValue())));
			weekDays.add(new SelectItem(DayOfWeek.WEDNESDAY.getValue(), i18n(Constants.SEMINARPOOL_DAY_OF_WEEK_PREFIX + DayOfWeek.WEDNESDAY.getValue())));
			weekDays.add(new SelectItem(DayOfWeek.THURSDAY.getValue(), i18n(Constants.SEMINARPOOL_DAY_OF_WEEK_PREFIX + DayOfWeek.THURSDAY.getValue())));
			weekDays.add(new SelectItem(DayOfWeek.FRIDAY.getValue(), i18n(Constants.SEMINARPOOL_DAY_OF_WEEK_PREFIX + DayOfWeek.FRIDAY.getValue())));
			weekDays.add(new SelectItem(DayOfWeek.SATURDAY.getValue(), i18n(Constants.SEMINARPOOL_DAY_OF_WEEK_PREFIX + DayOfWeek.SATURDAY.getValue())));
		}
		return weekDays;
	}
	
	public void setWeekDays(List<SelectItem> weekDays) {
		this.weekDays = weekDays;
	}
	
}
