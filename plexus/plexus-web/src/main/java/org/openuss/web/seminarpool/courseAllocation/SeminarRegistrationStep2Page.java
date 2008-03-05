package org.openuss.web.seminarpool.courseAllocation;




	import java.util.ArrayList;
import java.util.Collection;
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
import org.openuss.seminarpool.CourseGroupInfo;
import org.openuss.seminarpool.CourseSeminarpoolAllocationInfo;
import org.openuss.seminarpool.SeminarpoolAdministrationService;
import org.openuss.seminarpool.SeminarpoolInfo;
import org.openuss.web.BasePage;
import org.openuss.web.Constants;




	/**
	 * Backing bean for the Course to Seminarpool registration. Is responsible starting the
	 * wizard, binding the values and registrating the Course.
	 * 
	 * @author PS-Seminarplatzvergabe
	 * 
	 */

	@Bean(name = "views$secured$seminarpool$add$courseAllocationStep2", scope = Scope.REQUEST)
	@View
	public class SeminarRegistrationStep2Page extends BasePage {		
		private static final long serialVersionUID = 5069930000478432045L;
				
		private static final Logger logger = Logger.getLogger(SeminarRegistrationStep2Page.class);

		@Prerender
		public void prerender() throws Exception {
			breadcrumbs.init();	
			BreadCrumb newCrumb = new BreadCrumb();
			newCrumb.setName(i18n("seminarpool_add_course_allocation_breadcrumb_step2"));
			newCrumb.setHint(i18n("seminarpool_add_course_allocation_breadcrumb_step2"));
			breadcrumbs.addCrumb(newCrumb);	
		}
			
	}
