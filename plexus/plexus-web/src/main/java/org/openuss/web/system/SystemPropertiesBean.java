package org.openuss.web.system;

import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Property;
import org.apache.shale.tiger.managed.Scope;
import org.openuss.system.SystemProperties;
import org.openuss.system.SystemService;
import org.openuss.web.Constants;

/**
 * @author Ingo Dueppe
 *
 */
@Bean(name = Constants.SYSTEM_PROPERTIES, scope = Scope.REQUEST)
public class SystemPropertiesBean {
	
	@Property(value="#{systemService}")
	private SystemService systemService;
	
	
	public String getDOCUMENTATION_URL() {
		return systemService.getProperty(SystemProperties.DOCUMENTATION_URL).getValue();
	}

	public String getSUPPORT_URL() {
		return systemService.getProperty(SystemProperties.SUPPORT_URL).getValue();
	}

	public String getBUGTRACKING_URL() {
		return systemService.getProperty(SystemProperties.BUGTRACKING_URL).getValue();
	}

	public String getIMPRESSUM_TEXT() {
		return systemService.getProperty(SystemProperties.IMPRESSUM_TEXT).getValue();
	}
	
	public String getPROVIDER_URL() {
		return systemService.getProperty(SystemProperties.PROVIDER_URL).getValue();
	}

	public String getOPENUSS_SERVER_URL() {
		return systemService.getProperty(SystemProperties.OPENUSS_SERVER_URL).getValue();
	}

	public String getGETTING_STARTED() {
		return systemService.getProperty(SystemProperties.GETTING_STARTED).getValue();
	}
	
	public String getOPENUSS_INSTANCE_ID() {
		return systemService.getInstanceIdentity().toString();
	}
	
	public void setSystemService(SystemService systemService) {
		this.systemService = systemService;
	}

}
