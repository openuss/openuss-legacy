// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.security.ldap;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @see org.openuss.security.ldap.AttributeMapping
 */
public class AttributeMappingDaoImpl extends AttributeMappingDaoBase {
	/**
	 * @see org.openuss.security.ldap.AttributeMappingDao#toAttributeMappingInfo(org.openuss.security.ldap.AttributeMapping,
	 *      org.openuss.security.ldap.AttributeMappingInfo)
	 */
	public void toAttributeMappingInfo(AttributeMapping sourceEntity, AttributeMappingInfo targetVO) {
		super.toAttributeMappingInfo(sourceEntity, targetVO);
	}

	/**
	 * @see org.openuss.security.ldap.AttributeMappingDao#toAttributeMappingInfo(org.openuss.security.ldap.AttributeMapping)
	 */
	public AttributeMappingInfo toAttributeMappingInfo(final AttributeMapping entity) {

		AttributeMappingInfo attributeMappingInfo = super.toAttributeMappingInfo(entity);

		// set RoleAttributeKey Ids
		List<Long> roleAttributeKeyIds = new ArrayList<Long>();
		List<RoleAttributeKey> roleAttributeKeyList = entity.getRoleAttributeKeys();
		for (RoleAttributeKey roleAttributeKey : roleAttributeKeyList) {
			roleAttributeKeyIds.add(roleAttributeKey.getId());
		}
		attributeMappingInfo.setRoleAttributeKeyIds(roleAttributeKeyIds);

		// set AuthenticationDomain Ids
		List<Long> authenticationDomainIds = new ArrayList<Long>();
		Set<AuthenticationDomain> authenticationDomainSet = entity.getAuthenticationDomains();
		for (AuthenticationDomain authenticationDomain : authenticationDomainSet) {
			authenticationDomainIds.add(authenticationDomain.getId());
		}
		attributeMappingInfo.setAuthenticationDomainIds(authenticationDomainIds);

		return attributeMappingInfo;
	}

	/**
	 * Retrieves the entity object that is associated with the specified value
	 * object from the object store. If no such entity object exists in the
	 * object store, a new, blank entity is created
	 */
	private AttributeMapping loadAttributeMappingFromAttributeMappingInfo(AttributeMappingInfo attributeMappingInfo) {
		AttributeMapping attributeMapping = null;
		if (attributeMappingInfo.getId() != null) {
			attributeMapping = this.load(attributeMappingInfo.getId());
		} 
		if (attributeMapping == null) {
			attributeMapping = AttributeMapping.Factory.newInstance();
		}
		return attributeMapping;

	}

	/**
	 * @see org.openuss.security.ldap.AttributeMappingDao#attributeMappingInfoToEntity(org.openuss.security.ldap.AttributeMappingInfo)
	 */
	public AttributeMapping attributeMappingInfoToEntity(AttributeMappingInfo attributeMappingInfo) {
		AttributeMapping entity = this.loadAttributeMappingFromAttributeMappingInfo(attributeMappingInfo);
		this.attributeMappingInfoToEntity(attributeMappingInfo, entity, true);
		return entity;
	}

	/**
	 * @see org.openuss.security.ldap.AttributeMappingDao#attributeMappingInfoToEntity(org.openuss.security.ldap.AttributeMappingInfo,
	 *      org.openuss.security.ldap.AttributeMapping)
	 */
	public void attributeMappingInfoToEntity(AttributeMappingInfo sourceVO, AttributeMapping targetEntity, boolean copyIfNull) {
		super.attributeMappingInfoToEntity(sourceVO, targetEntity, copyIfNull);
	}

}