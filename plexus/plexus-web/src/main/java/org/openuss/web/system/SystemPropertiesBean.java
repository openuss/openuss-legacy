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
	
	public String getDOCUMENTATION_URL() { //NOPMD
		return value(SystemProperties.DOCUMENTATION_URL);
	}

	public String getSUPPORT_URL() { //NOPMD
		return value(SystemProperties.SUPPORT_URL);
	}

	public String getBUGTRACKING_URL() { //NOPMD
		return value(SystemProperties.BUGTRACKING_URL);
	}

	public String getIMPRESSUM_TEXT() { //NOPMD
		return value(SystemProperties.IMPRESSUM_TEXT);
	}
	
	public String getPROVIDER_URL() { //NOPMD
		return value(SystemProperties.PROVIDER_URL);
	}

	public String getOPENUSS_SERVER_URL() { //NOPMD
		return value(SystemProperties.OPENUSS_SERVER_URL);
	}

	public String getGETTING_STARTED() { //NOPMD
		return value(SystemProperties.GETTING_STARTED);
	}
	
	public String getOPENUSS_INSTANCE_ID() { //NOPMD
		try {
			return systemService.getInstanceIdentity().toString();
		} catch (Exception e) {
			return "not available";
		}
	}
	
	public void setSystemService(SystemService systemService) {
		this.systemService = systemService;
	}

	private String value(String propertyKey) {
		try {
			return systemService.getProperty(propertyKey).getValue();
		} catch (Exception ex) {
			logger.error(ex);
			return "";
		}
	}
}

