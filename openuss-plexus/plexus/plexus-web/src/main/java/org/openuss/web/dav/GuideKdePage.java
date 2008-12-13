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
	 * Backing bean for webdav/guide_kde.xhtml --> prerender the breadcrumbs
	 * @author PS-Integration
	 * 
	 */

	@Bean(name = "views$secured$webdav$guide_kde", scope = Scope.REQUEST)
	@View
	public class GuideKdePage extends BasePage {		
		
		@Property(value = "#{"+Constants.SYSTEM_PROPERTIES+"}")
		private SystemPropertiesBean systemPropertiesBean;
		
		public String getWebdavGuideKde1() {
			return i18n("webdav_guide_kde1", systemPropertiesBean.getOPENUSS_SERVER_URL().replaceAll("^http://", "webdav://"));
		}

		@Prerender
		public void prerender() throws Exception {
			breadcrumbs.init();	
			BreadCrumb newCrumb = new BreadCrumb();
			newCrumb.setName(i18n("webdav_guide_kde_header"));
			newCrumb.setHint(i18n("webdav_guide_kde_header"));
			breadcrumbs.addCrumb(newCrumb);	
		}

		public SystemPropertiesBean getSystemPropertiesBean() {
			return systemPropertiesBean;
		}

		public void setSystemPropertiesBean(SystemPropertiesBean systemPropertiesBean) {
			this.systemPropertiesBean = systemPropertiesBean;
		}		
	}