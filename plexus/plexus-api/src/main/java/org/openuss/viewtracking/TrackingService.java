package org.openuss.viewtracking;

import java.util.List;

/**
 * 
 */
public interface TrackingService {

	/**
     * 
     */
	public void setNew(Object domainObject);

	/**
     * 
     */
	public void setRead(Object domainObject);

	/**
     * 
     */
	public void setModified(Object domainObject);

	/**
     * 
     */
	public org.openuss.viewtracking.ViewState getViewState(Object domainObject);

	/**
     * 
     */
	public void setViewState(org.openuss.viewtracking.ViewState viewState, Object domainObject);

	/**
     * 
     */
	public void remove(Object domainObject);

	/**
     * 
     */
	public List getTopicViewStates(Long domainIdentifier, Long userId);

}
