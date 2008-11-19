// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.security.ldap;

import java.util.ArrayList;
import java.util.List;

/**
 * @see org.openuss.security.ldap.RoleAttributeKey
 */
public class RoleAttributeKeyDaoImpl extends RoleAttributeKeyDaoBase {
	/**
	 * @see org.openuss.security.ldap.RoleAttributeKeyDao#toRoleAttributeKeyInfo(org.openuss.security.ldap.RoleAttributeKey,
	 *      org.openuss.security.ldap.RoleAttributeKeyInfo)
	 */
	public void toRoleAttributeKeyInfo(RoleAttributeKey sourceEntity, RoleAttributeKeyInfo targetVO) {
		super.toRoleAttributeKeyInfo(sourceEntity, targetVO);
	}

	/**
	 * @see org.openuss.security.ldap.RoleAttributeKeyDao#toRoleAttributeKeyInfo(org.openuss.security.ldap.RoleAttributeKey)
	 */
	public RoleAttributeKeyInfo toRoleAttributeKeyInfo(final RoleAttributeKey entity) {
		RoleAttributeKeyInfo roleAttributeKeyInfo = super.toRoleAttributeKeyInfo(entity);

		// set AttributeMapping Ids
		List<Long> attributeMappingIds = new ArrayList<Long>();
		List<AttributeMapping> attributeMappingEntity = entity.getAttributeMappings();
		for (AttributeMapping attributeMapping : attributeMappingEntity) {
			attributeMappingIds.add(attributeMapping.getId());
		}
		roleAttributeKeyInfo.setAttributeMappingIds(attributeMappingIds);
		return roleAttributeKeyInfo;
	}

	/**
	 * Retrieves the entity object that is associated with the specified value
	 * object from the object store. If no such entity object exists in the
	 * object store, a new, blank entity is created
	 */
	private RoleAttributeKey loadRoleAttributeKeyFromRoleAttributeKeyInfo(RoleAttributeKeyInfo roleAttributeKeyInfo) {
		RoleAttributeKey roleAttributeKey = null;
		if (roleAttributeKeyInfo.getId() != null) {
			roleAttributeKey = this.load(roleAttributeKeyInfo.getId());
		}
		if (roleAttributeKey == null) {
			roleAttributeKey = RoleAttributeKey.Factory.newInstance();
		}
		return roleAttributeKey;
	}

	/**
	 * @see org.openuss.security.ldap.RoleAttributeKeyDao#roleAttributeKeyInfoToEntity(org.openuss.security.ldap.RoleAttributeKeyInfo)
	 */
	public RoleAttributeKey roleAttributeKeyInfoToEntity(RoleAttributeKeyInfo roleAttributeKeyInfo) {
		RoleAttributeKey entity = this.loadRoleAttributeKeyFromRoleAttributeKeyInfo(roleAttributeKeyInfo);
		this.roleAttributeKeyInfoToEntity(roleAttributeKeyInfo, entity, true);
		return entity;
	}

	/**
	 * @see org.openuss.security.ldap.RoleAttributeKeyDao#roleAttributeKeyInfoToEntity(org.openuss.security.ldap.RoleAttributeKeyInfo,
	 *      org.openuss.security.ldap.RoleAttributeKey)
	 */
	public void roleAttributeKeyInfoToEntity(RoleAttributeKeyInfo sourceVO, RoleAttributeKey targetEntity, boolean copyIfNull) {
		super.roleAttributeKeyInfoToEntity(sourceVO, targetEntity, copyIfNull);
	}

}