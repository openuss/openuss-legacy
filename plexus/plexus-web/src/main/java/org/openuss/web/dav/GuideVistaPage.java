package org.openuss.web.dav;



import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.Prerender;
import org.apache.shale.tiger.view.View;
import org.openuss.framework.jsfcontrols.breadcrumbs.BreadCrumb;
import org.openuss.web.BasePage;




	/**
	 * Backing bean for webdav/guide_winvista.xhtml --> prerender the breadcrumbs
	 * @author PS-Integration
	 * 
	 */

	@Bean(name = "views$secured$webdav$guide_winvista", scope = Scope.REQUEST)
	@View
	public class GuideVistaPage extends BasePage {		
				
		@Prerender
		public void prerender() throws Exception {
			breadcrumbs.init();	
			BreadCrumb newCrumb = new BreadCrumb();
			newCrumb.setName(i18n("webdav_guide_win_vista_header"));
			newCrumb.setHint(i18n("webdav_guide_win_vista_header"));
			breadcrumbs.addCrumb(newCrumb);	
		}		
	}
