package org.openuss.web.dav;



import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Property;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.Prerender;
import org.apache.shale.tiger.view.View;
import org.openuss.framework.jsfcontrols.breadcrumbs.BreadCrumb;
import org.openuss.web.BasePage;
import org.openuss.web.Constants;
import org.openuss.web.system.SystemPropertiesBean;




	/**
	 * Backing bean for webdav/guide_macosx.xhtml --> prerender the breadcrumbs
	 * @author PS-Integration
	 * 
	 */

	@Bean(name = "views$secured$webdav$guide_macosx", scope = Scope.REQUEST)
	@View
	public class GuideMacOsxPage extends BasePage {		
		
		@Property(value = "#{"+Constants.SYSTEM_PROPERTIES+"}")
		private SystemPropertiesBean systemPropertiesBean;
		
		public String getWebdavGuideMac2() {
			return i18n("webdav_guide_mac_2", systemPropertiesBean.getOPENUSS_SERVER_URL());
		}
		
		@Prerender
		public void prerender() throws Exception {
			breadcrumbs.init();	
			BreadCrumb newCrumb = new BreadCrumb();
			newCrumb.setName(i18n("webdav_guide_mac_header"));
			newCrumb.setHint(i18n("webdav_guide_mac_header"));
			breadcrumbs.addCrumb(newCrumb);	
		}

		public SystemPropertiesBean getSystemPropertiesBean() {
			return systemPropertiesBean;
		}

		public void setSystemPropertiesBean(SystemPropertiesBean systemPropertiesBean) {
			this.systemPropertiesBean = systemPropertiesBean;
		}		
	}
