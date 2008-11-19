// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.system;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Collection;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;

/**
 * @see org.openuss.system.SystemService
 * @author Ingo Dueppe
 */
public class SystemServiceImpl extends SystemServiceBase implements InitializingBean {

	/** Logger for this class */
	private static final Logger logger = Logger.getLogger(SystemServiceImpl.class);

	/**
	 * This is the system instance identity, that must be unique within the
	 * cluster
	 */
	private static volatile Long instanceIdentity = null;

	static {
		String instanceId = System.getProperty(SystemProperties.OPENUSS_INSTANCE_ID);
		if (StringUtils.isNotBlank(instanceId)) {
			try {
				instanceIdentity = Long.valueOf(instanceId);
			} catch (NumberFormatException ex) {
				logger.error("-D" + SystemProperties.OPENUSS_INSTANCE_ID + " must be a number.");
				logger.error(ex);
			}
		}
		if (instanceIdentity == null) {
			try {
				instanceIdentity = ((long) InetAddress.getLocalHost().hashCode()) + System.currentTimeMillis();
			} catch (UnknownHostException e) {
				logger.error(e);
				instanceIdentity = System.currentTimeMillis();
			}
		}
	}

	/**
	 * @see org.openuss.system.SystemService#persistProperty(org.openuss.system.SystemProperty)
	 */
	protected void handlePersistProperty(org.openuss.system.SystemProperty systemProperty) throws java.lang.Exception {
		if (systemProperty.getId() == null) {
			getSystemPropertyDao().create(systemProperty);
		} else {
			getSystemPropertyDao().update(systemProperty);
		}
	}

	/**
	 * @see org.openuss.system.SystemService#getProperties()
	 */
	protected java.util.Collection<SystemProperty> handleGetProperties() throws java.lang.Exception {
		return getSystemPropertyDao().loadAll();
	}

	/**
	 * @see org.openuss.system.SystemService#getProperty(java.lang.String)
	 */
	protected org.openuss.system.SystemProperty handleGetProperty(java.lang.String name) throws java.lang.Exception {
		return getSystemPropertyDao().findByName(name);
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void handlePersistProperties(Collection properties) throws Exception {
		getSystemPropertyDao().update(properties);
	}

	@Override
	protected Long handleGetInstanceIdentity() throws Exception {
		return instanceIdentity;
	}

	@Override
	protected void handleSetInstanceIdentity(Long identity) throws Exception {
		throw new UnsupportedOperationException("Do not manipulate the Instance Id!");
	}

	@Override
	protected SystemProperty handleGetProperty(Long id) throws Exception {
		return getSystemPropertyDao().load(id);
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		getSystemPropertyDao().loadAll();
	}

}