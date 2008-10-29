package org.openuss.system;

import java.util.Collection;

/**
 * 
 */
public interface SystemService {

	/**
     * 
     */
	public void persistProperties(Collection properties);

	/**
     * 
     */
	public void persistProperty(org.openuss.system.SystemProperty property);

	/**
     * 
     */
	public Collection getProperties();

	/**
     * 
     */
	public org.openuss.system.SystemProperty getProperty(Long id);

	/**
     * 
     */
	public org.openuss.system.SystemProperty getProperty(String name);

	/**
	 * <p>
	 * cluster unique number of the instance. Each instance of the cluster needs
	 * a unique id.
	 * </p>
	 */
	public Long getInstanceIdentity();

	/**
     * 
     */
	public void setInstanceIdentity(Long identity);

}
