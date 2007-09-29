package org.openuss.web.system;

import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Property;
import org.apache.shale.tiger.managed.Scope;
import org.openuss.system.SystemProperties;
import org.openuss.system.SystemService;
import org.openuss.web.Constants;

@Bean(name = Constants.SYSTEM_PROPERTIES, scope = Scope.REQUEST)
public class SystemPropertiesBean {
	
	@Property(value="#{systemService}")
	private SystemService systemService;
	
	public String getCommunityCourseId() {
		return systemService.getProperty(SystemProperties.COMMUNITY_COURSE_ID).getValue();
	}
	
	public void setSystemService(SystemService systemService) {
		this.systemService = systemService;
	}

}
