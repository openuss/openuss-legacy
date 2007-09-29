// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.system;

import org.apache.log4j.Logger;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Collection;

/**
 * @see org.openuss.system.SystemService
 */
public class SystemServiceImpl extends org.openuss.system.SystemServiceBase {
	
	/** Logger for this class */
	private static final Logger logger = Logger.getLogger(SystemServiceImpl.class);

	/**  This is the system instance identity, that must be unique within the cluster */
	private static Long instanceIdentity = 1L;
	
	static {
		try {
			instanceIdentity = ((long)InetAddress.getLocalHost().hashCode()) + System.currentTimeMillis(); 
			
		} catch (UnknownHostException e) {
			logger.error(e);
			instanceIdentity = System.currentTimeMillis();
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
	protected java.util.Collection handleGetProperties() throws java.lang.Exception {
		return getSystemPropertyDao().loadAll();
	}

	/**
	 * @see org.openuss.system.SystemService#getProperty(java.lang.String)
	 */
	protected org.openuss.system.SystemProperty handleGetProperty(java.lang.String name) throws java.lang.Exception {
		return getSystemPropertyDao().findByName(name);
	}

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

}