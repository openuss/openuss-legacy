package org.openuss.web.seminarpool;

import java.util.ArrayList;
import java.util.List;

import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

import org.apache.log4j.Logger;
import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Property;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.Prerender;
import org.apache.shale.tiger.view.View;
import org.openuss.framework.jsfcontrols.breadcrumbs.BreadCrumb;
import org.openuss.lecture.UniversityInfo;
import org.openuss.lecture.UniversityService;
import org.openuss.seminarpool.SeminarpoolAccessType;
import org.openuss.web.Constants;


/**
 * 
 * @author PS-Seminarplaceallocation
 */

@Bean(name = "views$secured$seminarpool$seminarpoolremoveconfirmation", scope = Scope.REQUEST)
@View 
public class SeminarpoolRemoveConfirmationPage extends AbstractSeminarpoolPage {
	
	private static final long serialVersionUID = -202776319652385870L;

	@Prerender
	public void prerender() throws Exception {
		super.prerender();
		BreadCrumb crumb = new BreadCrumb();
		crumb.setLink("");
		crumb.setName(i18n("seminarpool_command_options_config"));
		crumb.setHint(i18n("seminarpool_command_options_config"));
		breadcrumbs.addCrumb(crumb);
		
		BreadCrumb crumb2 = new BreadCrumb();
		crumb2.setLink("");
		crumb2.setName(i18n("seminarpool_command_remove_hint"));
		crumb2.setHint(i18n("seminarpool_command_remove_hint"));
		breadcrumbs.addCrumb(crumb2);
	}
}
