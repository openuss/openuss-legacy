package org.openuss.web.system;

import org.apache.log4j.Logger;

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
@Bean(name = Constants.SYSTEM_PROPERTIES, scope = Scope.APPLICATION)
public class SystemPropertiesBean {
	
	/** Logger for this class */
	private static final Logger logger = Logger.getLogger(SystemPropertiesBean.class);
	
	@Property(value="#{systemService}")
	private SystemService systemService;
	
	public String getDOCUMENTATION_URL() {
		return value(SystemProperties.DOCUMENTATION_URL_ID);
	}

	public String getSUPPORT_URL() {
		return value(SystemProperties.SUPPORT_URL_ID);
	}

	public String getBUGTRACKING_URL() {
		return value(SystemProperties.BUGTRACKING_URL_ID);
	}

	public String getIMPRESSUM_TEXT() {
		return value(SystemProperties.IMPRESSUM_TEXT_ID);
	}
	
	public String getPROVIDER_URL() {
		return value(SystemProperties.PROVIDER_URL_ID);
	}

	public String getOPENUSS_SERVER_URL() {
		return value(SystemProperties.OPENUSS_SERVER_URL_ID);
	}

	public String getGETTING_STARTED() {
		return value(SystemProperties.GETTING_STARTED_ID);
	}
	
	public String getOPENUSS_INSTANCE_ID() {
		try {
			return systemService.getInstanceIdentity().toString();
		} catch (Exception e) {
			return "not available";
		}
	}
	
	public void setSystemService(SystemService systemService) {
		this.systemService = systemService;
	}

	private String value(Long propertyId) {
		try {
			return systemService.getProperty(propertyId).getValue();
		} catch (Exception ex) {
			logger.error(ex);
			return "";
		}
	}
}

