// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.groups;
/**
 * @see org.openuss.groups.GroupMember
 */
public class GroupMemberDaoImpl
    extends org.openuss.groups.GroupMemberDaoBase
{
    /**
     * @see org.openuss.groups.GroupMemberDao#toGroupMemberInfo(org.openuss.groups.GroupMember, org.openuss.groups.GroupMemberInfo)
     */
    public void toGroupMemberInfo(
        org.openuss.groups.GroupMember sourceEntity,
        org.openuss.groups.GroupMemberInfo targetVO)
    {
        // @todo verify behavior of toGroupMemberInfo
        super.toGroupMemberInfo(sourceEntity, targetVO);
    }


    /**
     * @see org.openuss.groups.GroupMemberDao#toGroupMemberInfo(org.openuss.groups.GroupMember)
     */
    public org.openuss.groups.GroupMemberInfo toGroupMemberInfo(final org.openuss.groups.GroupMember entity)
    {
        // @todo verify behavior of toGroupMemberInfo
        return super.toGroupMemberInfo(entity);
    }


    /**
     * Retrieves the entity object that is associated with the specified value object
     * from the object store. If no such entity object exists in the object store,
     * a new, blank entity is created
     */
    private org.openuss.groups.GroupMember loadGroupMemberFromGroupMemberInfo(org.openuss.groups.GroupMemberInfo groupMemberInfo)
    {
        // @todo implement loadGroupMemberFromGroupMemberInfo
        throw new java.lang.UnsupportedOperationException("org.openuss.groups.loadGroupMemberFromGroupMemberInfo(org.openuss.groups.GroupMemberInfo) not yet implemented.");

        /* A typical implementation looks like this:
        org.openuss.groups.GroupMember groupMember = this.load(groupMemberInfo.getId());
        if (groupMember == null)
        {
            groupMember = org.openuss.groups.GroupMember.Factory.newInstance();
        }
        return groupMember;
        */
    }

    
    /**
     * @see org.openuss.groups.GroupMemberDao#groupMemberInfoToEntity(org.openuss.groups.GroupMemberInfo)
     */
    public org.openuss.groups.GroupMember groupMemberInfoToEntity(org.openuss.groups.GroupMemberInfo groupMemberInfo)
    {
        // @todo verify behavior of groupMemberInfoToEntity
        org.openuss.groups.GroupMember entity = this.loadGroupMemberFromGroupMemberInfo(groupMemberInfo);
        this.groupMemberInfoToEntity(groupMemberInfo, entity, true);
        return entity;
    }


    /**
     * @see org.openuss.groups.GroupMemberDao#groupMemberInfoToEntity(org.openuss.groups.GroupMemberInfo, org.openuss.groups.GroupMember)
     */
    public void groupMemberInfoToEntity(
        org.openuss.groups.GroupMemberInfo sourceVO,
        org.openuss.groups.GroupMember targetEntity,
        boolean copyIfNull)
    {
        // @todo verify behavior of groupMemberInfoToEntity
        super.groupMemberInfoToEntity(sourceVO, targetEntity, copyIfNull);
    }

}