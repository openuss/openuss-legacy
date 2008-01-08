// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.group;

import java.util.Date;

/**
 * @see org.openuss.group.GroupAdminInformation
 */
public class GroupAdminInformationDaoImpl
    extends org.openuss.group.GroupAdminInformationDaoBase
{
    /**
     * @see org.openuss.group.GroupAdminInformationDao#toGroupAdminInformationInfo(org.openuss.group.GroupAdminInformation, org.openuss.group.GroupAdminInformationInfo)
     */
    public void toGroupAdminInformationInfo(
        org.openuss.group.GroupAdminInformation sourceEntity,
        org.openuss.group.GroupAdminInformationInfo targetVO)
    {
        super.toGroupAdminInformationInfo(sourceEntity, targetVO);
    }


    /**
     * @see org.openuss.group.GroupAdminInformationDao#toGroupAdminInformationInfo(org.openuss.group.GroupAdminInformation)
     */
    public org.openuss.group.GroupAdminInformationInfo toGroupAdminInformationInfo(final org.openuss.group.GroupAdminInformation entity)
    {
    	GroupAdminInformationInfo gi = new GroupAdminInformationInfo();
    	toGroupAdminInformationInfo(entity, gi);
    	gi.setDeadlineReached(gi.getDeadline().before(new Date(System.currentTimeMillis())));
    	return gi;
    }


    /**
     * Retrieves the entity object that is associated with the specified value object
     * from the object store. If no such entity object exists in the object store,
     * a new, blank entity is created
     */
    private GroupAdminInformation loadGroupAdminInformationFromGroupAdminInformationInfo(GroupAdminInformationInfo groupAdminInformationInfo)
    {
    	if (groupAdminInformationInfo==null||groupAdminInformationInfo.getId()==null){
    		return GroupAdminInformation.Factory.newInstance();
    	}
    	return this.load(groupAdminInformationInfo.getId());
    }

    
    /**
     * @see org.openuss.group.GroupAdminInformationDao#groupAdminInformationInfoToEntity(org.openuss.group.GroupAdminInformationInfo)
     */
    public org.openuss.group.GroupAdminInformation groupAdminInformationInfoToEntity(org.openuss.group.GroupAdminInformationInfo groupAdminInformationInfo)
    {
        GroupAdminInformation entity = this.loadGroupAdminInformationFromGroupAdminInformationInfo(groupAdminInformationInfo);
        if (entity==null){
        	return GroupAdminInformation.Factory.newInstance();
        }
        this.groupAdminInformationInfoToEntity(groupAdminInformationInfo, entity, true);
        return entity;
    }


    /**
     * @see org.openuss.group.GroupAdminInformationDao#groupAdminInformationInfoToEntity(org.openuss.group.GroupAdminInformationInfo, org.openuss.group.GroupAdminInformation)
     */
    public void groupAdminInformationInfoToEntity(
        org.openuss.group.GroupAdminInformationInfo sourceVO,
        org.openuss.group.GroupAdminInformation targetEntity,
        boolean copyIfNull)
    {
        super.groupAdminInformationInfoToEntity(sourceVO, targetEntity, copyIfNull);
    }

}