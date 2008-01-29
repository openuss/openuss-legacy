// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.groups;
/**
 * @see org.openuss.groups.Groups
 */
public class GroupsDaoImpl
    extends org.openuss.groups.GroupsDaoBase
{
    /**
     * @see org.openuss.groups.GroupsDao#toGroupInfo(org.openuss.groups.Groups, org.openuss.groups.GroupInfo)
     */
    public void toGroupInfo(
        org.openuss.groups.Groups sourceEntity,
        org.openuss.groups.GroupInfo targetVO)
    {
        // @todo verify behavior of toGroupInfo
        super.toGroupInfo(sourceEntity, targetVO);
    }


    /**
     * @see org.openuss.groups.GroupsDao#toGroupInfo(org.openuss.groups.Groups)
     */
    public org.openuss.groups.GroupInfo toGroupInfo(final org.openuss.groups.Groups entity)
    {
        // @todo verify behavior of toGroupInfo
        return super.toGroupInfo(entity);
    }


    /**
     * Retrieves the entity object that is associated with the specified value object
     * from the object store. If no such entity object exists in the object store,
     * a new, blank entity is created
     */
    private org.openuss.groups.Groups loadGroupsFromGroupInfo(org.openuss.groups.GroupInfo groupInfo)
    {
        // @todo implement loadGroupsFromGroupInfo
        throw new java.lang.UnsupportedOperationException("org.openuss.groups.loadGroupsFromGroupInfo(org.openuss.groups.GroupInfo) not yet implemented.");

        /* A typical implementation looks like this:
        org.openuss.groups.Groups groups = this.load(groupInfo.getId());
        if (groups == null)
        {
            groups = org.openuss.groups.Groups.Factory.newInstance();
        }
        return groups;
        */
    }

    
    /**
     * @see org.openuss.groups.GroupsDao#groupInfoToEntity(org.openuss.groups.GroupInfo)
     */
    public org.openuss.groups.Groups groupInfoToEntity(org.openuss.groups.GroupInfo groupInfo)
    {
        // @todo verify behavior of groupInfoToEntity
        org.openuss.groups.Groups entity = this.loadGroupsFromGroupInfo(groupInfo);
        this.groupInfoToEntity(groupInfo, entity, true);
        return entity;
    }


    /**
     * @see org.openuss.groups.GroupsDao#groupInfoToEntity(org.openuss.groups.GroupInfo, org.openuss.groups.Groups)
     */
    public void groupInfoToEntity(
        org.openuss.groups.GroupInfo sourceVO,
        org.openuss.groups.Groups targetEntity,
        boolean copyIfNull)
    {
        // @todo verify behavior of groupInfoToEntity
        super.groupInfoToEntity(sourceVO, targetEntity, copyIfNull);
    }

}