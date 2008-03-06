package org.openuss.web.seminarpool;



import org.apache.log4j.Logger;
import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.Prerender;
import org.apache.shale.tiger.view.View;
import org.openuss.framework.jsfcontrols.breadcrumbs.BreadCrumb;
import org.openuss.web.BasePage;




	/**
	 * Backing bean for seminarpoolcreatestep1.xhtml --> prerender the breadcrumbs
	 * 
	 * @author PS-Seminarplatzvergabe
	 * 
	 */

	@Bean(name = "views$secured$seminarpool$seminarpoolcreatestep1", scope = Scope.REQUEST)
	@View
	public class SeminarpoolCreateStep1Page extends BasePage {		
		private static final long serialVersionUID = 5069930000478432045L;
				
		private static final Logger logger = Logger.getLogger(SeminarpoolCreateStep1Page.class);

		@Prerender
		public void prerender() throws Exception {
			breadcrumbs.init();	
			BreadCrumb newCrumb = new BreadCrumb();
			newCrumb.setName(i18n("seminarpool_add_course_allocation_breadcrumb_step2"));
			newCrumb.setHint(i18n("seminarpool_add_course_allocation_breadcrumb_step2"));
			breadcrumbs.addCrumb(newCrumb);	
		}		
	}
