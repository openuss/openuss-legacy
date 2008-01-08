// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.group;

import java.util.Set;

/**
 * @see org.openuss.group.WorkingGroup
 */
public class WorkingGroupDaoImpl
    extends org.openuss.group.WorkingGroupDaoBase
{
    /**
     * @see org.openuss.group.WorkingGroupDao#toWorkingGroupInfo(org.openuss.group.WorkingGroup, org.openuss.group.WorkingGroupInfo)
     */
    public void toWorkingGroupInfo(
        org.openuss.group.WorkingGroup sourceEntity,
        org.openuss.group.WorkingGroupInfo targetVO)
    {
        super.toWorkingGroupInfo(sourceEntity, targetVO);
        if (sourceEntity.getAspirants()==null){
        	targetVO.setAspirantsReal(0);        	
        } else if (sourceEntity.getAspirants()!=null){
        	Set<GroupWish> wishes = sourceEntity.getAspirants();
        	int count = 0;
        	for (GroupWish wish:wishes){
        		if (wish.getWeight().intValue()==0){
        			count++;
        		}
        	}        	
        	targetVO.setAspirantsReal(count);
        }
        if (sourceEntity.getMembers()==null){
        	targetVO.setMembersReal(0);        	
        } else if (sourceEntity.getMembers()!=null){
        	targetVO.setMembersReal(sourceEntity.getMembers().size());
        }
    }


    /**
     * @see org.openuss.group.WorkingGroupDao#toWorkingGroupInfo(org.openuss.group.WorkingGroup)
     */
    public org.openuss.group.WorkingGroupInfo toWorkingGroupInfo(final org.openuss.group.WorkingGroup entity)
    {
        WorkingGroupInfo wi = new WorkingGroupInfo();
        toWorkingGroupInfo(entity, wi);
    	return wi;
    }


    /**
     * Retrieves the entity object that is associated with the specified value object
     * from the object store. If no such entity object exists in the object store,
     * a new, blank entity is created
     */
    private org.openuss.group.WorkingGroup loadWorkingGroupFromWorkingGroupInfo(org.openuss.group.WorkingGroupInfo workingGroupInfo)
    {
        if (workingGroupInfo==null||workingGroupInfo.getId()==null){
        	return WorkingGroup.Factory.newInstance();
        }
        return this.load(workingGroupInfo.getId());
    }

    
    /**
     * @see org.openuss.group.WorkingGroupDao#workingGroupInfoToEntity(org.openuss.group.WorkingGroupInfo)
     */
    public org.openuss.group.WorkingGroup workingGroupInfoToEntity(org.openuss.group.WorkingGroupInfo workingGroupInfo)
    {
        WorkingGroup entity = this.loadWorkingGroupFromWorkingGroupInfo(workingGroupInfo);        
        if (entity ==null){
        	entity = WorkingGroup.Factory.newInstance();
        }
        this.workingGroupInfoToEntity(workingGroupInfo, entity, true);
        return entity;
    }


    /**
     * @see org.openuss.group.WorkingGroupDao#workingGroupInfoToEntity(org.openuss.group.WorkingGroupInfo, org.openuss.group.WorkingGroup)
     */
    public void workingGroupInfoToEntity(
        org.openuss.group.WorkingGroupInfo sourceVO,
        org.openuss.group.WorkingGroup targetEntity,
        boolean copyIfNull)
    {
        super.workingGroupInfoToEntity(sourceVO, targetEntity, copyIfNull);
    }

}