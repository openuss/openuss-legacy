// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.system;

import java.util.Collection;

/**
 * @see org.openuss.system.SystemService
 */
public class SystemServiceImpl extends org.openuss.system.SystemServiceBase {

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

}