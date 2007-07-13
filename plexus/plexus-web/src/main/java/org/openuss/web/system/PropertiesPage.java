package org.openuss.web.system;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Property;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.Prerender;
import org.apache.shale.tiger.view.View;
import org.openuss.lecture.LectureIndex;
import org.openuss.migration.MigrationService;
import org.openuss.system.SystemProperty;
import org.openuss.system.SystemService;
import org.openuss.web.BasePage;
import org.openuss.web.Constants;

/**
 * @author Ingo Dueppe
 *
 */
@Bean(name="views$secured$system$properties", scope=Scope.REQUEST)
@View
public class PropertiesPage extends BasePage  {

	@Property(value="#{propertyList}")
	private PropertyList propertyList;
	
	@Property(value="#{systemService}")
	private SystemService systemService;
	
	
	@Prerender
	public void prerender() {
		propertyList.setData(new ArrayList<SystemProperty>(systemService.getProperties()));
		setSessionBean(Constants.BREADCRUMBS, null);
	}
	
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
		return Constants.SUCCESS;
	}
	
	public String migrate() {
		MigrationService service = (MigrationService) getBean("migrationService");
		service.performMigration();
		return Constants.SUCCESS;
	}
	
	public String recreateLectureIndex() throws Exception {
		LectureIndex lectureIndex = (LectureIndex) getBean("lectureIndex");
		lectureIndex.recreate();
		return Constants.SUCCESS;
	}

}
