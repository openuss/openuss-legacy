// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.viewtracking;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.apache.commons.lang.Validate;
import org.openuss.framework.utilities.DomainObjectUtility;
import org.openuss.security.UserInfo;

/**
 * @see org.openuss.viewtracking.TrackingService
 */
public class TrackingServiceImpl extends org.openuss.viewtracking.TrackingServiceBase {

	/**
	 * @see org.openuss.viewtracking.TrackingService#setViewState(org.openuss.viewtracking.ViewState,
	 *      java.lang.Object)
	 */
	protected void handleSetViewState(ViewState viewState, Object domainObject) throws java.lang.Exception {
		Validate.notNull(viewState, "Parameter viewState must not be null!");
		Validate.notNull(domainObject, "Parameter domainObject must not be null!");
		
		DomainViewStatePK pk = generatePrimaryKey(domainObject);
		
		DomainViewState entity = loadDomainViewState(pk, viewState); 
		persistDomainViewState(viewState, entity);
	}


	/**
	 * @see org.openuss.viewtracking.TrackingService#getViewState(java.lang.Object)
	 */
	protected ViewState handleGetViewState(Object domainObject) throws java.lang.Exception {
		Validate.notNull(domainObject, "Parameter domainObject must not be null!");
		
		DomainViewStatePK pk = generatePrimaryKey(domainObject);
		return loadDomainViewState(pk, ViewState.NEW).getViewState();
	}

	@Override
	protected void handleSetModified(Object domainObject) throws Exception {
		Long domainIdentifier = obtainDomainIdentifier(domainObject);
		getDomainViewStateDao().updateAllToModified(domainIdentifier);
	}

	@Override
	protected void handleSetNew(Object domainObject) throws Exception {
		setViewState(ViewState.NEW, domainObject);
	}

	@Override
	protected void handleSetRead(Object domainObject) throws Exception {
		setViewState(ViewState.READ, domainObject);

	}

	private DomainViewStatePK generatePrimaryKey(Object domainObject) throws IllegalAccessException, InvocationTargetException {
		Long domainIdentifier = DomainObjectUtility.identifierFromObject(domainObject);
		UserInfo user = getSecurityService().getCurrentUser();
		
		DomainViewStatePK pk = new DomainViewStatePK();
		pk.setDomainIdentifier(domainIdentifier);
		pk.setUserIdentifier(user.getId());
		return pk;
	}

	private void persistDomainViewState(ViewState viewState, DomainViewState entity) {
		entity.setViewState(viewState);
		getDomainViewStateDao().update(entity);
	}
	
	private DomainViewState loadDomainViewState(DomainViewStatePK pk, ViewState defaultViewState) {
		DomainViewState entity = getDomainViewStateDao().load(pk);
		if (entity == null) {
			entity = new DomainViewStateImpl();
			entity.setViewState(defaultViewState);
			entity.setDomainViewStatePk(pk);
			getDomainViewStateDao().create(entity);
		}
		return entity;
	}

	@Override
	protected void handleRemove(Object domainObject) throws Exception {
		Long domainIdentifier = obtainDomainIdentifier(domainObject);
		getDomainViewStateDao().removeAllByDomain(domainIdentifier);
	}


	private Long obtainDomainIdentifier(Object domainObject) {
		Validate.notNull(domainObject, "Parameter domainObject must not be null");
		Long domainIdentifier = DomainObjectUtility.identifierFromObject(domainObject);
		Validate.notNull(domainIdentifier, "Parameter domainObject must contain an identifier");
		return domainIdentifier;
	}


	@Override
	protected List handleGetTopicViewStates(Long domainIdentifier, Long userId) throws Exception {
		Validate.notNull(domainIdentifier);
		Validate.notNull(userId);	
		return getDomainViewStateDao().getTopicViewStates(domainIdentifier, userId);
	}
}