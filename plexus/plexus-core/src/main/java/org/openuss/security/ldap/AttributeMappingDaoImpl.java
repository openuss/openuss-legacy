// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.security.ldap;
/**
 * @see org.openuss.security.ldap.AttributeMapping
 */
public class AttributeMappingDaoImpl
    extends org.openuss.security.ldap.AttributeMappingDaoBase
{
    /**
     * @see org.openuss.security.ldap.AttributeMappingDao#toAttributeMappingInfo(org.openuss.security.ldap.AttributeMapping, org.openuss.security.ldap.AttributeMappingInfo)
     */
    public void toAttributeMappingInfo(
        org.openuss.security.ldap.AttributeMapping sourceEntity,
        org.openuss.security.ldap.AttributeMappingInfo targetVO)
    {
        super.toAttributeMappingInfo(sourceEntity, targetVO);
    }


    /**
     * @see org.openuss.security.ldap.AttributeMappingDao#toAttributeMappingInfo(org.openuss.security.ldap.AttributeMapping)
     */
    public org.openuss.security.ldap.AttributeMappingInfo toAttributeMappingInfo(final org.openuss.security.ldap.AttributeMapping entity) {
    	
    	AttributeMappingInfo attributeMappingInfo = super.toAttributeMappingInfo(entity);    	
    	attributeMappingInfo.setRoleAttributeKeys(entity.getRoleAttributeKeys());
    	
    	return attributeMappingInfo;
    }


    /**
     * Retrieves the entity object that is associated with the specified value object
     * from the object store. If no such entity object exists in the object store,
     * a new, blank entity is created
     */
    private org.openuss.security.ldap.AttributeMapping loadAttributeMappingFromAttributeMappingInfo(org.openuss.security.ldap.AttributeMappingInfo attributeMappingInfo)
    {
        
//        throw new java.lang.UnsupportedOperationException("org.openuss.security.ldap.loadAttributeMappingFromAttributeMappingInfo(org.openuss.security.ldap.AttributeMappingInfo) not yet implemented.");

        org.openuss.security.ldap.AttributeMapping attributeMapping = this.load(attributeMappingInfo.getId());
        if (attributeMapping == null)
        {
            attributeMapping = org.openuss.security.ldap.AttributeMapping.Factory.newInstance();
        }
        return attributeMapping;
       
    }

    
    /**
     * @see org.openuss.security.ldap.AttributeMappingDao#attributeMappingInfoToEntity(org.openuss.security.ldap.AttributeMappingInfo)
     */
    public org.openuss.security.ldap.AttributeMapping attributeMappingInfoToEntity(org.openuss.security.ldap.AttributeMappingInfo attributeMappingInfo)
    {
        org.openuss.security.ldap.AttributeMapping entity = this.loadAttributeMappingFromAttributeMappingInfo(attributeMappingInfo);
        this.attributeMappingInfoToEntity(attributeMappingInfo, entity, true);
        return entity;
    }


    /**
     * @see org.openuss.security.ldap.AttributeMappingDao#attributeMappingInfoToEntity(org.openuss.security.ldap.AttributeMappingInfo, org.openuss.security.ldap.AttributeMapping)
     */
    public void attributeMappingInfoToEntity(
        org.openuss.security.ldap.AttributeMappingInfo sourceVO,
        org.openuss.security.ldap.AttributeMapping targetEntity,
        boolean copyIfNull)
    {
        super.attributeMappingInfoToEntity(sourceVO, targetEntity, copyIfNull);
    }

}