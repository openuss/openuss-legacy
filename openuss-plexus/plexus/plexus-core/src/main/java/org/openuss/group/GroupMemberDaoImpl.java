// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.group;

import java.util.Iterator;
import java.util.Set;

/**
 * @see org.openuss.group.GroupMember
 */
public class GroupMemberDaoImpl
    extends org.openuss.group.GroupMemberDaoBase
{
    /**
     * @see org.openuss.group.GroupMemberDao#toGroupMemberInfo(org.openuss.group.GroupMember, org.openuss.group.GroupMemberInfo)
     */
    public void toGroupMemberInfo(
        org.openuss.group.GroupMember sourceEntity,
        org.openuss.group.GroupMemberInfo targetVO)
    {
        super.toGroupMemberInfo(sourceEntity, targetVO);
        targetVO.setMemberName(sourceEntity.getUser().getDisplayName());
        if (sourceEntity.getGroupWish()!=null){
        	Set<GroupWish> wishes = sourceEntity.getGroupWish();
        	String wishList = "";
        	for (int i = 0; i < wishes.size(); i++){
        		Iterator<GroupWish> iterator = wishes.iterator();
        		while (iterator.hasNext()){
        			GroupWish wish = iterator.next();
        			if (wish.getWeight().intValue()==i){
        				wishList += wish.getGroup().getGroupName();
        				if (i < (wishes.size()-1)){
        					wishList+=", ";
        				}
        			}
        		}
        	}
        	targetVO.setWishList(wishList);
        }
        if (sourceEntity.getUser().getProfile()!=null){
        	targetVO.setAgeGroup(sourceEntity.getUser().getProfile().getAgeGroup());
        	targetVO.setMatriculation(sourceEntity.getUser().getProfile().getMatriculation());
        	targetVO.setStudies(sourceEntity.getUser().getProfile().getStudies());
        }
    }


    /**
     * @see org.openuss.group.GroupMemberDao#toGroupMemberInfo(org.openuss.group.GroupMember)
     */
    public org.openuss.group.GroupMemberInfo toGroupMemberInfo(final org.openuss.group.GroupMember entity)
    {
    	GroupMemberInfo gi = new GroupMemberInfo();
    	toGroupMemberInfo(entity, gi);
    	return gi;
    }


    /**
     * Retrieves the entity object that is associated with the specified value object
     * from the object store. If no such entity object exists in the object store,
     * a new, blank entity is created
     */
    private org.openuss.group.GroupMember loadGroupMemberFromGroupMemberInfo(org.openuss.group.GroupMemberInfo groupMemberInfo)
    {
        if (groupMemberInfo==null||groupMemberInfo.getId()==null){
        	return GroupMember.Factory.newInstance();
        }
    	return this.load(groupMemberInfo.getId());
    }

    
    /**
     * @see org.openuss.group.GroupMemberDao#groupMemberInfoToEntity(org.openuss.group.GroupMemberInfo)
     */
    public org.openuss.group.GroupMember groupMemberInfoToEntity(org.openuss.group.GroupMemberInfo groupMemberInfo)
    {
    	GroupMember entity = this.loadGroupMemberFromGroupMemberInfo(groupMemberInfo);
    	if (entity==null){
    		entity = GroupMember.Factory.newInstance();
    	}
        this.groupMemberInfoToEntity(groupMemberInfo, entity, true);
        return entity;
    }


    /**
     * @see org.openuss.group.GroupMemberDao#groupMemberInfoToEntity(org.openuss.group.GroupMemberInfo, org.openuss.group.GroupMember)
     */
    public void groupMemberInfoToEntity(
        org.openuss.group.GroupMemberInfo sourceVO,
        org.openuss.group.GroupMember targetEntity,
        boolean copyIfNull)
    {
        super.groupMemberInfoToEntity(sourceVO, targetEntity, copyIfNull);
    }

}