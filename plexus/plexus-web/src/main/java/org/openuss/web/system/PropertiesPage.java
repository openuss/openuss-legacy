package org.openuss.web.system;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Property;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.Prerender;
import org.apache.shale.tiger.view.View;
import org.openuss.framework.jsfcontrols.breadcrumbs.BreadCrumb;
import org.openuss.lecture.RecreateLectureIndex;
import org.openuss.system.SystemProperty;
import org.openuss.system.SystemService;
import org.openuss.web.BasePage;
import org.openuss.web.Constants;
import org.openuss.web.PageLinks;

/**
 * @author Ingo Dueppe
 * @author Sebastian Roekens
 * 
 */
@Bean(name="views$secured$system$properties", scope=Scope.REQUEST)
@View
public class PropertiesPage extends BasePage  {

	@Property(value="#{propertyList}")
	private PropertyList propertyList;
	
	@Property(value="#{systemService}")
	private SystemService systemService;

	@SuppressWarnings("unchecked")
	@Prerender
	public void prerender() {
		propertyList.setData(new ArrayList<SystemProperty>(systemService.getProperties()));
		setBean(Constants.BREADCRUMBS, null);
		
		BreadCrumb newCrumb = new BreadCrumb();
		newCrumb.setName(i18n("admin_command_properties"));
		newCrumb.setLink(PageLinks.ADMIN_PROPERTIES);
		
		breadcrumbs.loadAdministrationCrumbs();
		breadcrumbs.addCrumb(newCrumb);
	}
	
	@SuppressWarnings("unchecked")
	public Collection<SystemProperty> getProperties() {
		return systemService.getProperties();
	}
	
	public SystemService getSystemService() {
		return systemService;
	}

	public void setSystemService(SystemService systemService) {
		this.systemService = systemService;
	}

	public PropertyList getPropertyList() {
		return propertyList;
	}

	public void setPropertyList(PropertyList propertyList) {
		this.propertyList = propertyList;
	}
	
	public String saveProperties() {
		Collection<SystemProperty> properties = propertyList.getData();
		systemService.persistProperties(properties);
		addMessage(i18n("admin_properties_saved_success"));
		return Constants.SUCCESS;
	}
	
	public String recreateLectureIndex() throws Exception {
		RecreateLectureIndex recreateIndex = (RecreateLectureIndex) getBean("recreateLectureIndex");
		recreateIndex.recreate();
		addMessage(i18n("admin_lectureindex_recreated_success"));
		return Constants.SUCCESS;
	}

}
